package com.design.order;

/** 订单状态。正向履约和逆向售后共用同一台状态机。 */
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
