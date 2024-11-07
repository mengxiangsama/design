package com.design.handler;

import lombok.Data;

@Data
public class OrderModel {
    private Long orderId;
    private Long saleId;
    private Long goodsId;
    private Integer orderType;
}
