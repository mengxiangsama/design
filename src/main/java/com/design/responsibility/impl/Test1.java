package com.design.responsibility.impl;

import com.design.responsibility.Abstract;
import com.design.responsibility.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Order(1)
public class Test1 extends Abstract {

    @Override
    protected Param doFilter(Param Param) {
        System.out.println("responsibility -> test1");
        return doNextHandler(Param);
    }
}
