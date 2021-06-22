package com.example.isibpmn.data.model;

import com.example.isibpmn.data.model.LesInputs;

import java.util.List;

public class ListeInputs {

    private List<LesInputs> inputs;
    private List<Object> constraints;

    public List<LesInputs> getInputs() {
        return inputs;
    }

    public void setInputs(List<LesInputs> inputs) {
        this.inputs = inputs;
    }
}
