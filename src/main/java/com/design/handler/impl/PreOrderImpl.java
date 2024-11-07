package com.design.handler.impl;

import com.design.handler.OrderModel;
import com.design.handler.orderHandler.PreOrderHandler;
import org.springframework.stereotype.Service;

@Service
public class PreOrderImpl extends PreOrderHandler {

    @Override
    protected void handleCanal(OrderModel orderModel) {
        System.out.println("pre canal");
    }

}
