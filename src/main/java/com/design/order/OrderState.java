package com.design.order;

public enum OrderState {
    WAIT_PAY("待支付"),
    PAID("已支付"),
    PROCESSING("处理中"),
    COMPLETED("已完成"),
    REFUNDING("退款中"),
    REFUNDED("已退款"),
    RETURN_REFUNDING("退货退款中"),
    RETURN_REFUNDED("已退货退款"),
    CANCELLED("已取消");

    private final String description;

    OrderState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
