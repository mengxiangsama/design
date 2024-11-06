package com.design.strategy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Test  implements CommandLineRunner {
    @Resource
    private StrategyFactory strategyFactory;
        @Override
        public void run(String... args) throws Exception {
            // 1 or 2 枚举
            strategyFactory.run(1);
            strategyFactory.run(2);
        }
}
