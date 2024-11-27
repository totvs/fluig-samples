package com.samplerunner.service;

import java.util.List;

import javax.ejb.Remote;

import com.fluig.sdk.api.group.GroupVO;

@Remote
public interface GroupService {

    public static final String JNDI_NAME = "service/sample-runner";
    public static final String JNDI_REMOTE_NAME = "java:global/fluig/" + JNDI_NAME;

    List<GroupVO> listGroups() throws Exception;

    GroupVO createGroup(GroupVO vo) throws Exception;
}
