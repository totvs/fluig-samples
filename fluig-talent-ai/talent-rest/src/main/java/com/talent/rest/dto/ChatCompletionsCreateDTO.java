package com.talent.rest.dto;

import java.util.Map;

public class ChatCompletionsCreateDTO {

    private Map<String, String> completions;

    public Map<String, String> getCompletions() {
        return completions;
    }

    public void setCompletions(Map<String, String> completions) {
        this.completions = completions;
    }
}
