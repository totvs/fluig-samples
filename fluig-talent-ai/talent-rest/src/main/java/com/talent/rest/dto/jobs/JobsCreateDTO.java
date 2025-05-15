package com.talent.rest.dto.jobs;

public class JobsCreateDTO {

    private String name;
    private String description;
    private Long widgetInstanceId;

    public JobsCreateDTO(){}

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
}
