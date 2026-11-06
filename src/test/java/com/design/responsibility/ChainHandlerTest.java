package com.design.responsibility;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChainHandlerTest {
    @Test
    void executesNodesInOrderAndReturnsTheSameParameter() {
        List<Integer> visited = new ArrayList<>();
        Abstract first = node(1, visited);
        Abstract second = node(2, visited);
        ChainHandler handler = new ChainHandler();
        ReflectionTestUtils.setField(handler, "chain", Arrays.asList(first, second));
        ReflectionTestUtils.invokeMethod(handler, "constructChain");
        Param param = Param.builder().i(7).build();
        assertSame(param, handler.executionChain(param));
        assertEquals(Arrays.asList(1, 2), visited);
    }

    @Test
    void rejectsAnEmptyChain() {
        ChainHandler handler = new ChainHandler();
        ReflectionTestUtils.setField(handler, "chain", Collections.emptyList());
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> ReflectionTestUtils.invokeMethod(handler, "constructChain"));
        assertEquals("未找到责任链信息", error.getMessage());
    }

    private Abstract node(int id, List<Integer> visited) {
        return new Abstract() {
            @Override
            protected Param doFilter(Param param) {
                visited.add(id);
                return doNextHandler(param);
            }
        };
    }
}
