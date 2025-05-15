package com.talent.service.impl;

import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.PageService;
import com.google.gson.Gson;
import com.talent.service.JobsService;
import com.talent.service.impl.vo.jobs.JobsCreateVO;
import com.talent.service.impl.vo.jobs.JobsUpdateVO;
import com.talent.service.impl.vo.jobs.JobsVO;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Remote(JobsService.class)
@Stateless(mappedName = JobsService.JNDI_NAME, name = JobsService.JNDI_NAME)
public class JobsServiceImpl implements JobsService {

    //private static final String KEY_JOBS_WIDGET = "talentwidgetregister";
    private static final Gson gson = new Gson();

    @Override
    public JobsVO create(JobsCreateVO vo) throws SDKException {
        
        Map<String, Object> preferences = new HashMap<>();
        
        String name = vo.getName();
        String description = vo.getDescription();
        String state = vo.getState();
        Long widgetInstanceId = vo.getWidgetInstanceId();

        preferences.put("name", name);
        preferences.put("description", description);
        preferences.put("state", state);

        Gson gson = new Gson();
        String json = gson.toJson(preferences);

        try {
            getPageService().setWidgetPreference(widgetInstanceId, "talentwidgetregister", json);
            return new JobsVO(name, description, widgetInstanceId, state);
        } catch (SDKException e){
            throw new SDKException(e);
        }
    }

    @Override
    public JobsVO update(JobsUpdateVO vo) {
        return null;
    }

    @Override
    public List<JobsVO> getAll() {
        return List.of();
    }

    private PageService getPageService() throws SDKException {
        return new FluigAPI().getPageService();
    }
}
