package com.design.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public abstract class AbstractOrderHandler implements CustomService {

    public abstract Integer getOrderType();

    private final Map<Integer, Consumer<OrderModel>> map = new HashMap<>();

    public AbstractOrderHandler() {
        map.put(OrderStatusEnum.CREATE.getCode(), this::handleCreate);
        map.put(OrderStatusEnum.CANAL.getCode(), this::handleCanal);
        map.put(OrderStatusEnum.CUSTOM.getCode(), this::custom);
    }

    protected void handler(Integer i, OrderModel orderModel) {
        map.get(i).accept(orderModel);
    }


    /**
     * 处理订单创建
     *
     * @return void
     */
    protected abstract void handleCreate(OrderModel orderModel);

    /**
     * 处理订单关闭
     *
     * @return void
     */
    protected abstract void handleCanal(OrderModel orderModel);
}
