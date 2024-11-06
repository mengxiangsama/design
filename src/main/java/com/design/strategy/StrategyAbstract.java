package com.design.strategy;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public abstract class StrategyAbstract implements StrategyService{

    protected Map<Integer, Consumer<Object>> punishmentMap = new HashMap<>();

    abstract public Integer getType();

    @Override
    public void test(Object obj) {
        System.out.println("default");
    }

    @PostConstruct
    public void init() {
        punishmentMap.put(1, this::test);
    }
}
