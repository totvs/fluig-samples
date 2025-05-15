package com.talent.service.impl.vo.jobs;

import java.io.Serializable;

public class JobsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private Long widgetInstanceId;
    private String state;

    public JobsVO(){}

    public JobsVO(String name, String description, Long widgetInstanceId, String state) {
        this.name = name;
        this.description = description;
        this.widgetInstanceId = widgetInstanceId;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getWidgetInstanceId() {
        return widgetInstanceId;
    }

    public void setWidgetInstanceId(Long widgetInstanceId) {
        this.widgetInstanceId = widgetInstanceId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
