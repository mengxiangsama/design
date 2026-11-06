package com.design.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderStateMachineConfig.class)
class OrderStateMachineServiceTest {
    @Autowired
    private StateMachineFactory<OrderState, OrderEvent> factory;
    private OrderStateMachineService service;

    @BeforeEach
    void setUp() {
        service = new OrderStateMachineService(factory);
    }

    @Test
    void completesAnOrder() {
        assertEquals(OrderState.WAIT_PAY, service.getState(1L));
        assertEquals(OrderState.PAID, service.sendEventAndGetState(1L, OrderEvent.PAY));
        assertEquals(OrderState.PROCESSING, service.sendEventAndGetState(1L, OrderEvent.START_PROCESSING));
        assertEquals(OrderState.COMPLETED, service.sendEventAndGetState(1L, OrderEvent.COMPLETE));
    }

    @Test
    void cancelsAnUnpaidOrder() {
        assertEquals(OrderState.CANCELLED, service.sendEventAndGetState(1L, OrderEvent.CANCEL));
    }

    @ParameterizedTest
    @EnumSource(value = OrderState.class, names = {"PAID", "PROCESSING", "COMPLETED"})
    void refundsFromEachSupportedState(OrderState state) {
        advanceTo(state);
        assertEquals(OrderState.REFUNDING, service.sendEventAndGetState(1L, OrderEvent.APPLY_REFUND));
        assertEquals(OrderState.REFUNDED, service.sendEventAndGetState(1L, OrderEvent.REFUND_SUCCESS));
    }

    @ParameterizedTest
    @EnumSource(value = OrderState.class, names = {"PAID", "PROCESSING", "COMPLETED"})
    void returnsAndRefundsFromEachSupportedState(OrderState state) {
        advanceTo(state);
        assertEquals(OrderState.RETURN_REFUNDING, service.sendEventAndGetState(1L, OrderEvent.APPLY_RETURN_REFUND));
        assertEquals(OrderState.RETURN_REFUNDED, service.sendEventAndGetState(1L, OrderEvent.RETURN_REFUND_SUCCESS));
    }

    @ParameterizedTest
    @EnumSource(value = OrderEvent.class, names = {"PAY", "CANCEL"}, mode = EnumSource.Mode.EXCLUDE)
    void rejectsIllegalEventsWithoutChangingState(OrderEvent event) {
        assertFalse(service.sendEvent(1L, event));
        assertThrows(IllegalStateException.class, () -> service.sendEventAndGetState(1L, event));
        assertEquals(OrderState.WAIT_PAY, service.getState(1L));
    }

    @Test
    void rejectsDuplicatePaymentAndKeepsOrdersIndependent() {
        service.sendEventAndGetState(1L, OrderEvent.PAY);
        assertFalse(service.sendEvent(1L, OrderEvent.PAY));
        assertEquals(OrderState.PAID, service.getState(1L));
        assertEquals(OrderState.WAIT_PAY, service.getState(2L));
    }

    @Test
    void rejectsNullOrderId() {
        assertThrows(IllegalArgumentException.class, () -> service.getState(null));
        assertThrows(IllegalArgumentException.class, () -> service.sendEvent(null, OrderEvent.PAY));
        assertThrows(IllegalArgumentException.class, () -> service.sendEventAndGetState(null, OrderEvent.PAY));
    }

    private void advanceTo(OrderState state) {
        service.sendEventAndGetState(1L, OrderEvent.PAY);
        if (state == OrderState.PROCESSING || state == OrderState.COMPLETED) {
            service.sendEventAndGetState(1L, OrderEvent.START_PROCESSING);
        }
        if (state == OrderState.COMPLETED) {
            service.sendEventAndGetState(1L, OrderEvent.COMPLETE);
        }
    }
}
