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
            // 1 or 2 枚举
            strategyFactory.run(1);
            strategyFactory.run(2);
            chainHandler.executionChain(Param.builder().i(1).build());
        }
}
