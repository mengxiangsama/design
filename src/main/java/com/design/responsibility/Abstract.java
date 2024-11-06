package com.design.responsibility;

import org.springframework.stereotype.Component;

@Component
public abstract class Abstract {

    private Abstract nextHandler;

    abstract protected Param doFilter(Param cybeerOrderParam);

    protected Param doNextHandler(Param param) {
        if (nextHandler == null) {
            return param;
        }
        return nextHandler.doFilter(param);
    }

    public void setNextHandler(Abstract nextHandler) {
        this.nextHandler = nextHandler;
    }

}
