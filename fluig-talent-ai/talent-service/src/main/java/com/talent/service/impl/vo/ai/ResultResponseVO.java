package com.talent.service.impl.vo.ai;

import java.io.Serializable;

public class ResultResponseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
