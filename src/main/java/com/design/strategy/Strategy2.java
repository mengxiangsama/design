package com.design.strategy;

import org.springframework.stereotype.Component;

@Component
public class Strategy2 extends StrategyAbstract{
    @Override
    public Integer getType() {
        return 2;
    }

    @Override
    public void test(Object obj) {
        System.out.println("Strategy2");
    }
}
