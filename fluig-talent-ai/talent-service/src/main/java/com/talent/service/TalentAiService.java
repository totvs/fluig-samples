package com.talent.service;

import com.talent.service.impl.vo.ChatCompletionsCreateVO;
import com.talent.service.impl.vo.ResultResponseVO;
import com.talent.service.impl.vo.ResumeVO;

import javax.ejb.Remote;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

@Remote
public interface TalentAiService {

    public static final String JNDI_NAME = "service/talent-ai";
    public static final String JNDI_REMOTE_NAME = "java:global/fluig/" + JNDI_NAME;

    String get(long id);

    Future<List<ResultResponseVO>> resumeAnalyze(String criteria, ResumeVO resume, ChatCompletionsCreateVO completions) throws IOException;
}