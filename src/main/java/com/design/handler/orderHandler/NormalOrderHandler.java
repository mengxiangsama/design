package com.design.handler.orderHandler;

import com.design.handler.BaseOrderHandler;
import com.design.handler.TyeEnum;
import org.springframework.stereotype.Component;

@Component
public abstract class NormalOrderHandler extends BaseOrderHandler {
    @Override
    public Integer getOrderType() {
        return TyeEnum.S_1.getCode();
    }

}
