package com.talent.rest;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.talent.rest.dto.AnalyzeRequestDTO;
import com.talent.rest.dto.ResultResponseDTO;
import com.talent.rest.dto.ResumeDTO;
import com.talent.service.impl.vo.ChatCompletionsCreateVO;
import com.talent.service.impl.vo.ResultResponseVO;
import com.talent.service.impl.vo.ResumeVO;
import com.talent.util.SimpleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.UserService;
import com.talent.service.TalentAiService;
import com.totvs.technology.foundation.common.EncodedMediaType;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Path("/dta")
public class TalentDtaRest {
	private final Logger log = LoggerFactory.getLogger(TalentDtaRest.class);

	@EJB
	TalentAiService talentAiService;

	@GET
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response get(@PathParam("id") Long id) throws Exception {
		log.info("---- App Request | GET getById");
		String s = "TESTE";
		return Response.status(Response.Status.NOT_FOUND).entity("No App found for ID: " + id).build();
	}


	@POST
	@Path("/analyze")
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response analyzeResumes(AnalyzeRequestDTO request) throws Exception {
		try {
			List<ResultResponseDTO> results = processResumesAnalysis(request);
			return Response.ok(results).build();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new CustomAnalysisException("Análise de currículos interrompida", e);
		} catch (ExecutionException e) {
			throw new CustomAnalysisException("Erro durante a análise de currículos", e.getCause());
		}
	}

	private List<ResultResponseDTO> processResumesAnalysis(AnalyzeRequestDTO request)
			throws InterruptedException, ExecutionException {

		List<List<ResultResponseDTO>> analysisResults = createAnalysisFutures(request);

		return analysisResults.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}


	private List<List<ResultResponseDTO>> createAnalysisFutures(AnalyzeRequestDTO request) {
		return request.getResumes().stream()
				.map(resume -> {
                    try {
                        return analyzeSingleResume(request.getCriteria(), request, resume);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
				.collect(Collectors.toList());
	}

	private List<ResultResponseDTO> analyzeSingleResume(String criteria,
			AnalyzeRequestDTO request, ResumeDTO resume) throws IOException {

		ResumeVO resumeVO = convertToVO(resume, ResumeVO.class);
		ChatCompletionsCreateVO completionsVO = convertToVO(request.getCompletionsCreateDTO(), ChatCompletionsCreateVO.class);
		Future<List<ResultResponseVO>> listFuture = talentAiService.resumeAnalyze(criteria, resumeVO, completionsVO);

		try {
			List<ResultResponseVO> voList = listFuture.get();

			return voList.stream()
					.map(vo -> {
						try {
							return SimpleMapper.convert(vo, ResultResponseDTO.class);
						} catch (Exception e) {
							throw new ConversionException("Falha ao converter ResultResponseVO para DTO", e);
						}
					})
					.collect(Collectors.toList());

		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Erro ao processar análise de currículo", e);
		}
	}

	private <T> T convertToVO(Object source, Class<T> targetClass) {
		try {
			return SimpleMapper.convert(source, targetClass);
		} catch (Exception e) {
			throw new ConversionException(
                    "Falha ao converter objeto para " + targetClass.getSimpleName(), e
            );
		}
	}

	static class CustomAnalysisException extends RuntimeException {
		public CustomAnalysisException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	static class ConversionException extends RuntimeException {
		public ConversionException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	private UserService getUserServiceSDK() throws SDKException {
		return new FluigAPI().getUserService();
	}

}
