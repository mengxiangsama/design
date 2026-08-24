package com.design.order;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class OrderStateMachineConfig
        extends EnumStateMachineConfigurerAdapter<OrderState, OrderEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states)
            throws Exception {
        states.withStates()
                .initial(OrderState.WAIT_PAY)
                .states(EnumSet.allOf(OrderState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions)
            throws Exception {
        transitions
                .withExternal().source(OrderState.WAIT_PAY).target(OrderState.PAID)
                    .event(OrderEvent.PAY).and()
                .withExternal().source(OrderState.WAIT_PAY).target(OrderState.CANCELLED)
                    .event(OrderEvent.CANCEL).and()
                .withExternal().source(OrderState.PAID).target(OrderState.PROCESSING)
                    .event(OrderEvent.START_PROCESSING).and()
                .withExternal().source(OrderState.PROCESSING).target(OrderState.COMPLETED)
                    .event(OrderEvent.COMPLETE).and()
                .withExternal().source(OrderState.PAID).target(OrderState.REFUNDING)
                    .event(OrderEvent.APPLY_REFUND).and()
                .withExternal().source(OrderState.PROCESSING).target(OrderState.REFUNDING)
                    .event(OrderEvent.APPLY_REFUND).and()
                .withExternal().source(OrderState.COMPLETED).target(OrderState.REFUNDING)
                    .event(OrderEvent.APPLY_REFUND).and()
                .withExternal().source(OrderState.REFUNDING).target(OrderState.REFUNDED)
                    .event(OrderEvent.REFUND_SUCCESS).and()
                .withExternal().source(OrderState.PAID).target(OrderState.RETURN_REFUNDING)
                    .event(OrderEvent.APPLY_RETURN_REFUND).and()
                .withExternal().source(OrderState.PROCESSING).target(OrderState.RETURN_REFUNDING)
                    .event(OrderEvent.APPLY_RETURN_REFUND).and()
                .withExternal().source(OrderState.COMPLETED).target(OrderState.RETURN_REFUNDING)
                    .event(OrderEvent.APPLY_RETURN_REFUND).and()
                .withExternal().source(OrderState.RETURN_REFUNDING)
                    .target(OrderState.RETURN_REFUNDED)
                    .event(OrderEvent.RETURN_REFUND_SUCCESS);
    }
}
