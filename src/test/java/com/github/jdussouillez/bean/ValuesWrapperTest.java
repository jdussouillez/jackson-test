package com.github.jdussouillez.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValuesWrapperTest extends BaseTest {

    protected ValuesWrapper<Pair<String, Entity>> pairWrapper;

    @BeforeEach
    @Override
    public void beforeEach() {
        super.beforeEach();
        pairWrapper = new ValuesWrapper<>(
            List.of(
                Pair.of("foo", new Entity().id("bar")),
                Pair.of("baz", new Entity().id("qux"))
            )
        );
    }

    @Test
    public void testSerializeDeserialize() throws JsonProcessingException {
        String json = "["
            + "[\"foo\",{\"id\":\"bar\"}],"
            + "[\"baz\",{\"id\":\"qux\"}]"
            + "]";
        assertEquals(json, jsonMapper.writeValueAsString(pairWrapper));

        // ---> Issue is here
        ValuesWrapper<Pair<String, Entity>> deserialized = jsonMapper.readValue(
            json,
            new TypeReference<ValuesWrapper<Pair<String, Entity>>>() {}
        );
        assertEquals(pairWrapper, deserialized);
    }
}
