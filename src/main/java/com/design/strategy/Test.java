package com.design.strategy;

import com.design.handler.OrderHandlerFactory;
import com.design.handler.OrderStatusEnum;
import com.design.handler.TyeEnum;
import com.design.responsibility.ChainHandler;
import com.design.responsibility.Param;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Test implements CommandLineRunner {
    @Resource
    private StrategyFactory strategyFactory;
    @Resource
    private ChainHandler chainHandler;
    @Resource
    private OrderHandlerFactory orderHandlerFactory;

    @Override
    public void run(String... args) throws Exception {
        strategyFactory.run(TestEnum.S_1.getCode());
        strategyFactory.run(TestEnum.S_2.getCode());

        chainHandler.executionChain(Param.builder().i(1).build());

        orderHandlerFactory.handle(TyeEnum.S_1.getCode(), OrderStatusEnum.CREATE.getCode(), null);
        orderHandlerFactory.handle(TyeEnum.S_1.getCode(), OrderStatusEnum.CANAL.getCode(), null);
        orderHandlerFactory.handle(TyeEnum.S_1.getCode(), OrderStatusEnum.CUSTOM.getCode(), null);

        orderHandlerFactory.handle(TyeEnum.S_2.getCode(), OrderStatusEnum.CREATE.getCode(), null);
        orderHandlerFactory.handle(TyeEnum.S_2.getCode(), OrderStatusEnum.CANAL.getCode(), null);
        orderHandlerFactory.handle(TyeEnum.S_2.getCode(), OrderStatusEnum.CUSTOM.getCode(), null);

    }
}
