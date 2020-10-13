package com.github.jdussouillez.bean.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.jdussouillez.bean.BaseTest;
import org.apache.commons.lang3.tuple.MutablePair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class MutablePairJsonDeserializerTest extends BaseTest {

    @Test
    public void testDeserialize() throws JsonProcessingException {
        TestObj<MutablePair<String, Entity>> entity = jsonMapper.readValue(
            testObjJson,
            new TypeReference<TestObj<MutablePair<String, Entity>>>(){}
        );
        assertEquals(testObjMutable, entity);
        assertTrue(entity.value() instanceof MutablePair);
    }
}
