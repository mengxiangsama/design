package com.design.handler.impl;

import com.design.handler.OrderModel;
import com.design.handler.orderHandler.NormalOrderHandler;
import org.springframework.stereotype.Service;

@Service
public class NormalOrderImpl extends NormalOrderHandler {

    @Override
    protected void handleCreate(OrderModel orderModel) {
        System.out.println("Normal normal");
    }

    @Override
    public void custom(OrderModel orderModel) {
        System.out.println("自定义实现在实现");
    }
}
