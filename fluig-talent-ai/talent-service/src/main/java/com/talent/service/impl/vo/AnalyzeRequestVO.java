package com.talent.service.impl.vo;


import java.io.Serializable;
import java.util.List;

public class AnalyzeRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;


    private List<ResumeVO> resumes;
    private ChatCompletionsCreateVO completionsCreateVO;

    public AnalyzeRequestVO() {
    }

    public AnalyzeRequestVO(List<ResumeVO> resumes, ChatCompletionsCreateVO completionsCreateVO) {
        this.resumes = resumes;
        this.completionsCreateVO = completionsCreateVO;
    }

    public List<ResumeVO> getResumes() {
        return resumes;
    }

    public void setResumes(List<ResumeVO> resumes) {
        this.resumes = resumes;
    }

    public ChatCompletionsCreateVO getCompletionsCreateVO() {
        return completionsCreateVO;
    }

    public void setCompletionsCreateVO(ChatCompletionsCreateVO completionsCreateVO) {
        this.completionsCreateVO = completionsCreateVO;
    }
}
