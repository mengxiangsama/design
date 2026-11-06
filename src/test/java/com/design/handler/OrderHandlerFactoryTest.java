package com.design.handler;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Arrays;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class OrderHandlerFactoryTest {
    @Test
    void dispatchesEveryStatusToTheSelectedOrderType() {
        RecordingHandler normal = new RecordingHandler(1);
        RecordingHandler pre = new RecordingHandler(2);
        OrderHandlerFactory factory = new OrderHandlerFactory();
        Map<?, ?> registry = (Map<?, ?>) ReflectionTestUtils.getField(OrderHandlerFactory.class, "SERVICE_MAP");
        registry.clear();
        try {
            ReflectionTestUtils.setField(factory, "abstracts", Arrays.asList(normal, pre));
            ReflectionTestUtils.invokeMethod(factory, "init");
            OrderModel model = new OrderModel();
            for (RecordingHandler selected : Arrays.asList(normal, pre)) {
                RecordingHandler other = selected == normal ? pre : normal;
                for (OrderStatusEnum status : OrderStatusEnum.values()) {
                    other.lastStatus = null;
                    factory.handle(selected.getOrderType(), status.getCode(), model);
                    assertEquals(status, selected.lastStatus);
                    assertSame(model, selected.lastModel);
                    assertNull(other.lastStatus);
                }
            }
        } finally {
            registry.clear();
        }
    }

    private static class RecordingHandler extends AbstractOrderHandler {
        private final int type;
        private OrderStatusEnum lastStatus;
        private OrderModel lastModel;
        RecordingHandler(int type) { this.type = type; }
        public Integer getOrderType() { return type; }
        protected void handleCreate(OrderModel model) { record(OrderStatusEnum.CREATE, model); }
        protected void handleCanal(OrderModel model) { record(OrderStatusEnum.CANAL, model); }
        public void custom(OrderModel model) { record(OrderStatusEnum.CUSTOM, model); }
        private void record(OrderStatusEnum status, OrderModel model) {
            lastStatus = status;
            lastModel = model;
        }
    }
}
