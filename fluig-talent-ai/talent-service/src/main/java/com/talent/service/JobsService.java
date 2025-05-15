package com.talent.service;

import com.fluig.sdk.api.common.SDKException;
import com.talent.service.impl.vo.jobs.JobsCreateVO;
import com.talent.service.impl.vo.jobs.JobsUpdateVO;
import com.talent.service.impl.vo.jobs.JobsVO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface JobsService {

    public static final String JNDI_NAME = "service/jobs";
    public static final String JNDI_REMOTE_NAME = "java:global/fluig/" + JNDI_NAME;

    JobsVO create(JobsCreateVO vo) throws SDKException;

    JobsVO update(JobsUpdateVO vo);

    List<JobsVO> getAll();

}
