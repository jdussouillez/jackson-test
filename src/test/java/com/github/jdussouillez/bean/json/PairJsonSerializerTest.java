package com.github.jdussouillez.bean.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jdussouillez.bean.BaseTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PairJsonSerializerTest extends BaseTest {

    @Test
    public void testSerialize() throws JsonProcessingException {
        assertEquals(
            "{\"value\":[\"foo\",{\"id\":\"bar\"}]}",
            jsonMapper.writeValueAsString(testObj)
        );
        assertEquals(
            "{\"value\":[\"foo\",{\"id\":\"bar\"}]}",
            jsonMapper.writeValueAsString(testObjImmutable)
        );
        assertEquals(
            "{\"value\":[\"foo\",{\"id\":\"bar\"}]}",
            jsonMapper.writeValueAsString(testObjMutable)
        );
    }
}
