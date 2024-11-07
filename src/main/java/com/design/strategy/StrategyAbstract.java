package com.design.strategy;

import org.springframework.stereotype.Component;

@Component
public abstract class StrategyAbstract {


    abstract public Integer getType();

    abstract public void test(Object obj);
}
