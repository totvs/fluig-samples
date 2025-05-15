package com.talent.rest.dto.ai;

import java.util.List;

public class AnalyzeRequestDTO {

    private List<ResumeDTO> resumes;

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
