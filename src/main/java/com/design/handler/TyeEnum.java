package com.design.handler;

import lombok.Getter;

@Getter
public enum TyeEnum {

    S_1(1, "normal"),

    S_2(2, "pre");

    private final Integer code;
    private final String desc;

    TyeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
