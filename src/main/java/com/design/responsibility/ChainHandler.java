package com.design.responsibility;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ChainHandler {
    @Resource
    private List<Abstract> chain;

    private Abstract firstHandler;


    private @PostConstruct void constructChain() {
        if (chain == null || chain.size() == 0) {
            throw new RuntimeException("未找到责任链信息");
        }
        firstHandler = chain.get(0);
        for (int i = 0; i < chain.size(); i++) {
            if (i == chain.size() - 1) {
                chain.get(i).setNextHandler(null);
            } else {
                chain.get(i).setNextHandler(chain.get(i + 1));
            }
        }
    }

    public Param executionChain(Param param) {
        return firstHandler.doFilter(param);
    }
}
