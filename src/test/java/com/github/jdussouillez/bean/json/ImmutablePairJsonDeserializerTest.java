package com.github.jdussouillez.bean.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.jdussouillez.bean.BaseTest;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ImmutablePairJsonDeserializerTest extends BaseTest {

    @Test
    public void testDeserialize() throws JsonProcessingException {
        BaseTest.TestObj<ImmutablePair<String, BaseTest.Entity>> entity = jsonMapper.readValue(
            testObjJson,
            new TypeReference<BaseTest.TestObj<ImmutablePair<String, BaseTest.Entity>>>(){}
        );
        assertEquals(testObjImmutable, entity);
        assertTrue(entity.value() instanceof ImmutablePair);
    }

    @Test
    public void testDeserializePairBaseClass() throws JsonProcessingException {
        BaseTest.TestObj<Pair<String, BaseTest.Entity>> entity = jsonMapper.readValue(
            testObjJson,
            new TypeReference<BaseTest.TestObj<Pair<String, BaseTest.Entity>>>(){}
        );
        assertEquals(testObj, entity);
        assertTrue(entity.value() instanceof ImmutablePair); // Pair default implementation is ImmutablePair
    }
}
