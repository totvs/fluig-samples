package com.talent.service.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseTextConfig;
import com.talent.service.TalentAiService;

@Remote(TalentAiService.class)
@Stateless(mappedName = TalentAiService.JNDI_NAME, name = TalentAiService.JNDI_NAME)
public class TalentAiServiceImpl implements TalentAiService {

	private OpenAIClient client;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String get(long id) {

		OpenAIClient openAIClient = buildOpenAiClient();

		ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
				.addUserMessage("Conte de 1 a 5 em alemão")
				.model(ChatModel.O3_MINI)
				.build();
		ChatCompletion chatCompletion = client.chat().completions().create(params);

		ResponseCreateParams build = ResponseCreateParams.builder()
				.input("Conte de 1 a 5 em alemão")
				.model(ChatModel.GPT_4O)
				.build();

		Response response = client.responses().create(build);
		Optional<ResponseTextConfig> text = response.text();
		if(!text.isEmpty()){
			ResponseTextConfig responseTextConfig = text.get();
			return responseTextConfig.toString();
		}

		return "Fluig Talent AI";
	}

	private OpenAIClient buildOpenAiClient(){
		return OpenAIOkHttpClient.builder()
				// Configures using the `OPENAI_API_KEY`, `OPENAI_ORG_ID` and `OPENAI_PROJECT_ID` environment variables
				.fromEnv()
				.baseUrl("")
				.apiKey("")
				.build();
	}

}