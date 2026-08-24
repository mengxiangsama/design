package com.design.order;

public enum OrderEvent {
    PAY("支付"),
    START_PROCESSING("开始处理"),
    COMPLETE("完成"),
    CANCEL("取消"),
    APPLY_REFUND("申请退款"),
    REFUND_SUCCESS("退款成功"),
    APPLY_RETURN_REFUND("申请退货退款"),
    RETURN_REFUND_SUCCESS("退货退款成功");

    private final String description;

    OrderEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
