package com.design.responsibility.impl;

import com.design.responsibility.Abstract;
import com.design.responsibility.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Order(2)
public class Test2 extends Abstract {

    @Override
    protected Param doFilter(Param Param) {
        System.out.println("responsibility ->  test2");
        return doNextHandler(Param);
    }
}
