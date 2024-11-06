package com.design.strategy;

import org.springframework.stereotype.Component;

@Component
public class Strategy1 extends StrategyAbstract {

    @Override
    public Integer getType() {
        return 1;
    }

    @Override
    public void test(Object obj) {
        System.out.println("Strategy1");
    }
}
