package com.design.handler;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderHandlerFactory {

    @Resource
    private List<AbstractOrderHandler> abstracts;

    @PostConstruct
    private void init() {
        abstracts.forEach(item -> {
            SERVICE_MAP.put(item.getOrderType(), item);
        });
    }

    private static final Map<Integer, AbstractOrderHandler> SERVICE_MAP = new HashMap<>();

    public void handle(Integer i, Integer status, OrderModel orderModel) {
        SERVICE_MAP.get(i).handler(status, orderModel);
    }
}
