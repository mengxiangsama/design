package com.design.handler;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    CREATE(1, "create"),
    CANAL(2, "canal"),
    CUSTOM(3, "自定义");

    private final Integer code;
    private final String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
