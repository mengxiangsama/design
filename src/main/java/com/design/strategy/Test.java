package com.design.strategy;

import com.design.responsibility.ChainHandler;
import com.design.responsibility.Param;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Test  implements CommandLineRunner {
    @Resource
    private StrategyFactory strategyFactory;
    @Resource
    private ChainHandler chainHandler;
        @Override
        public void run(String... args) throws Exception {
            strategyFactory.run(TestEnum.S_1.getCode());
            strategyFactory.run(TestEnum.S_2.getCode());
            
            chainHandler.executionChain(Param.builder().i(1).build());
        }
}
