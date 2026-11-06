package com.design.strategy;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Arrays;
import java.util.Map;
import static org.mockito.Mockito.*;

class StrategyFactoryTest {
    @Test
    void dispatchesRegisteredStrategiesAndIgnoresUnknownTypes() {
        StrategyAbstract first = mock(StrategyAbstract.class);
        StrategyAbstract second = mock(StrategyAbstract.class);
        when(first.getType()).thenReturn(1);
        when(second.getType()).thenReturn(2);
        StrategyFactory factory = new StrategyFactory();
        Map<?, ?> registry = (Map<?, ?>) ReflectionTestUtils.getField(StrategyFactory.class, "SERVICE_MAP");
        registry.clear();
        try {
            ReflectionTestUtils.setField(factory, "abstracts", Arrays.asList(first, second));
            ReflectionTestUtils.invokeMethod(factory, "init");
            clearInvocations(first, second);
            factory.run(1);
            verify(first).test(1);
            verifyNoInteractions(second);
            factory.run(2);
            verify(second).test(2);
            factory.run(999);
            verifyNoMoreInteractions(first, second);
        } finally {
            registry.clear();
        }
    }
}
