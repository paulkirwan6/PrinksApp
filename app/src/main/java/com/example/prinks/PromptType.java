package com.example.prinks;

import java.io.Serializable;

public class PromptType implements Serializable {
    private String json;
    private boolean on;

    public PromptType(String json, boolean on) {
        this.json = json;
        this.on = on;
    }

    public String getJson() {
        return json;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
