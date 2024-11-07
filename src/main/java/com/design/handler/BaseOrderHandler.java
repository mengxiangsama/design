package com.design.handler;

import org.springframework.stereotype.Component;

@Component
public abstract class BaseOrderHandler extends AbstractOrderHandler {

    @Override
    protected void handleCreate(OrderModel orderModel) {
        System.out.println("default base handler");
    }

    @Override
    protected void handleCanal(OrderModel orderModel) {
        System.out.println("default canal");
    }

    @Override
    public void custom(OrderModel orderModel) {
        System.out.println("自定义实现");
    }
}
