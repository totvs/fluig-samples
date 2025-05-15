package com.talent.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.talent.service.TalentAiService;
import com.talent.service.impl.vo.ai.ChatCompletionsCreateVO;
import com.talent.service.impl.vo.ai.ResultResponseVO;
import com.talent.service.impl.vo.ai.ResumeVO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.ejb.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Remote(TalentAiService.class)
@Stateless(mappedName = TalentAiService.JNDI_NAME, name = TalentAiService.JNDI_NAME)
public class TalentAiServiceImpl implements TalentAiService {

	public static final String API_KEY = "";
	public static final String BASE_URL = "https://proxy.dta.totvs.ai";
	public static final String MODEL_NAME = "gpt-4o-mini";
	public static final double TEMPERATURE = 0.5;

	private static final Gson gson = new Gson();

	private static final OkHttpClient client = new OkHttpClient.Builder()
			.connectTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.build();

	@Asynchronous
	public Future<List<ResultResponseVO>> resumeAnalyze(String criteria, ResumeVO resume, ChatCompletionsCreateVO completions) throws IOException {

		List<ResultResponseVO> results = new ArrayList<>();

		String chatResponse = callDTAApi(criteria, resume.getResume(), completions.getCompletions());

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("response", chatResponse);
		ResultResponseVO resultResponse = new ResultResponseVO();
		resultResponse.setResult(chatResponse);
		results.add(resultResponse);


		return CompletableFuture.completedFuture(results);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String get(long id) {

/*		String aiResponse;
		try {
			//aiResponse = callDTAApi("deve ser um modelo semanal");
			System.out.println("Received response from OpenAI API");
		} catch (IOException e) {
			return "Error calling OpenAI API";
		}*/

		JsonObject jsonResponse = new JsonObject();

		//	jsonResponse.addProperty("response", aiResponse);

		System.out.println("Sending response back to client");
		return gson.toJson(jsonResponse);
	}

	private static String callDTAApi(String criteria, String resumeText, Map<String, String> completions) throws IOException {
		System.out.println("Preparing request to DTA API");
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("model", MODEL_NAME);
		requestBody.addProperty("temperature", TEMPERATURE);

		StringBuilder systemMessageTemplate = new StringBuilder();
		systemMessageTemplate.append("Você é um analisador de currículos especializado em encontrar vagas que atendam aos critérios: ");
		systemMessageTemplate.append(criteria);
		systemMessageTemplate.append("Sua tarefa é analisar o currículo fornecido e retornar um JSON com a seguinte estrutura: ");
		systemMessageTemplate.append("{");
		List<String> keys = new ArrayList<>(completions.keySet());
		for (String key : keys) {
			systemMessageTemplate.append(key).append(":").append(completions.get(key)).append(",");
		}
		systemMessageTemplate.append("}");
		systemMessageTemplate.append("Analise o currículo abaixo e identifique se o candidato atende a esses critérios e gere a resposta no formato JSON fornecido: ");
		systemMessageTemplate.append(resumeText);

		JsonArray messages = new JsonArray();
		messages.add(createMessage("system", systemMessageTemplate.toString()));

		//TODO
		//mensagem personalizada que user enviar via 'chat'
		messages.add(createMessage("user", "se houver, priorize candidatos que informaram softskills em seus curriculos"));
		requestBody.add("messages", messages);

		String jsonRequestBody = gson.toJson(requestBody);
		System.out.println("Request body: " + jsonRequestBody);

		RequestBody body = RequestBody.create(
				MediaType.parse("application/json"), jsonRequestBody);

		String url = BASE_URL + "/v1/chat/completions";
		String authHeader = "Bearer " + API_KEY;

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Authorization", authHeader)
				.addHeader("Content-Type", "application/json")
				.build();

		System.out.println("Full request:");
		System.out.println("URL: " + request.url());
		System.out.println("Method: " + request.method());
		System.out.println("Headers:");
		request.headers().forEach(header -> System.out.println(header.getFirst() + ": " + header.getSecond()));
		System.out.println("Body: " + jsonRequestBody);

		System.out.println("Sending request to DTA API");
		try (Response response = client.newCall(request).execute()) {
			String responseBody = response.body().string();
			System.out.println("Received response from OpenAI API: " + responseBody);

			if (!response.isSuccessful()) {
				System.err.println("DTA API returned error code: " + response.code());
				throw new IOException("Unexpected code " + response);
			}

			JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
			return jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject()
					.getAsJsonObject("message").get("content").getAsString();
		}
	}

	private static JsonObject createMessage(String role, String content) {
		JsonObject message = new JsonObject();
		message.addProperty("role", role);
		message.addProperty("content", content);
		return message;
	}
}