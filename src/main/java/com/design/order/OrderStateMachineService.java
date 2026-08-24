package com.design.order;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** 通过 StateMachineFactory 获取并驱动订单状态机。 */
@Service
public class OrderStateMachineService {

    private final StateMachineFactory<OrderState, OrderEvent> stateMachineFactory;
    private final Map<Long, StateMachine<OrderState, OrderEvent>> machines =
            new ConcurrentHashMap<>();

    public OrderStateMachineService(
            StateMachineFactory<OrderState, OrderEvent> stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

    public OrderState getState(Long orderId) {
        return machine(orderId).getState().getId();
    }

    public boolean sendEvent(Long orderId, OrderEvent event) {
        return machine(orderId).sendEvent(event);
    }

    public OrderState sendEventAndGetState(Long orderId, OrderEvent event) {
        StateMachine<OrderState, OrderEvent> machine = machine(orderId);
        if (!machine.sendEvent(event)) {
            throw new IllegalStateException("订单状态不允许执行事件: "
                    + machine.getState().getId().getDescription() + " -> "
                    + event.getDescription());
        }
        return machine.getState().getId();
    }

    private StateMachine<OrderState, OrderEvent> machine(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("orderId 不能为空");
        }
        return machines.computeIfAbsent(orderId, id -> {
            StateMachine<OrderState, OrderEvent> stateMachine =
                    stateMachineFactory.getStateMachine(String.valueOf(id));
            stateMachine.start();
            return stateMachine;
        });
    }
}
