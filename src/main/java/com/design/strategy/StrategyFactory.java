package com.design.strategy;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StrategyFactory {
    @Resource
    private List<StrategyAbstract> abstracts;

    @PostConstruct
    private void init() {
        abstracts.forEach(item -> {
            SERVICE_MAP.put(item.getType(), item);
        });
    }
    private static final Map<Integer, StrategyAbstract> SERVICE_MAP = new HashMap<>();

    public void run(Integer i){
        if(SERVICE_MAP.containsKey(i)){
            SERVICE_MAP.get(i).test(i);
        }
    }

}
