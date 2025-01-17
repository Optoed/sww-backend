package com.ece.bot.common.http;

public enum PARAMS {
    INVITE("invite");

    private String value;
    PARAMS(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
