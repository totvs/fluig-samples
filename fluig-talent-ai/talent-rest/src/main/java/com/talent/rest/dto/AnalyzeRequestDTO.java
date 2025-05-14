package com.talent.rest.dto;

import java.util.List;

public class AnalyzeRequestDTO {

    //todos os curriculos
    private List<ResumeDTO> resumes;

    //criterios da vaga definido na widget
    private String criteria;

    //retorno definido pelo desenvolvedor
    //TODO opcional - montar padrão.
    private ChatCompletionsCreateDTO completionsCreateDTO;

    public AnalyzeRequestDTO() {
    }

    public AnalyzeRequestDTO(List<ResumeDTO> resumes, ChatCompletionsCreateDTO completionsCreateDTO) {
        this.resumes = resumes;
        this.completionsCreateDTO = completionsCreateDTO;
    }

    public List<ResumeDTO> getResumes() {
        return resumes;
    }

    public void setResumes(List<ResumeDTO> resumes) {
        this.resumes = resumes;
    }

    public ChatCompletionsCreateDTO getCompletionsCreateDTO() {
        return completionsCreateDTO;
    }

    public void setCompletionsCreateDTO(ChatCompletionsCreateDTO completionsCreateDTO) {
        this.completionsCreateDTO = completionsCreateDTO;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
