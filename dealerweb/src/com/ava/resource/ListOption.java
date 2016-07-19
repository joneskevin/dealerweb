package com.ava.resource;

import java.io.Serializable;

public class ListOption implements Serializable{

	private String optionText;

    private String optionValue;

    private Integer integerOptionValue;

    public ListOption() {
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public Integer getIntegerOptionValue() {
        return integerOptionValue;
    }

    public void setIntegerOptionValue(Integer integerOptionValue) {
        this.integerOptionValue = integerOptionValue;
    }
}
