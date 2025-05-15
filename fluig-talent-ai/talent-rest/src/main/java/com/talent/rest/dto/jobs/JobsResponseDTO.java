package com.talent.rest.dto.jobs;

public class JobsResponseDTO {

    private String name;
    private String description;
    private Long widgetInstanceId;
    private String state;

    public JobsResponseDTO(){}

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
