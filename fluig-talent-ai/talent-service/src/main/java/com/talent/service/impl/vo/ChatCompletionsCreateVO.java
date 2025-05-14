package com.talent.service.impl.vo;

import java.io.Serializable;
import java.util.Map;

public class ChatCompletionsCreateVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, String> completions;

    public Map<String, String> getCompletions() {
        return completions;
    }

    public void setCompletions(Map<String, String> completions) {
        this.completions = completions;
    }
}
