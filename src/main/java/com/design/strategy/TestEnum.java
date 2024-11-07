package com.design.strategy;

import lombok.Getter;

@Getter
public enum TestEnum {

    S_1(1, "Strategy1"),

    S_2(2, "Strategy2"),
    S_3(3, "Strategy2");

    private final Integer code;
    private final String desc;

    TestEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
