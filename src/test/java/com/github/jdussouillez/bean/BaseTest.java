package com.github.jdussouillez.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.github.jdussouillez.bean.json.ImmutablePairJsonDeserializer;
import com.github.jdussouillez.bean.json.MutablePairJsonDeserializer;
import com.github.jdussouillez.bean.json.PairJsonSerializer;
import com.github.jdussouillez.bean.json.ValuesWrapperDeserializer;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected final String testObjJson = "{"
        + "\"value\":["
        + "    \"foo\","
        + "    {\"id\": \"bar\"}"
        + "]"
        + "}";

    protected ObjectMapper jsonMapper;

    protected TestObj<Pair<String, Entity>> testObj;

    protected TestObj<ImmutablePair<String, Entity>> testObjImmutable;

    protected TestObj<MutablePair<String, Entity>> testObjMutable;

    @BeforeEach
    public void beforeEach() {
        jsonMapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(getPairModule());

        Entity e = new Entity().id("bar");
        testObj = new TestObj<Pair<String, Entity>>()
            .value(Pair.of("foo", e));
        testObjImmutable = new TestObj<ImmutablePair<String, Entity>>()
            .value(ImmutablePair.of("foo", e));
        testObjMutable = new TestObj<MutablePair<String, Entity>>()
            .value(MutablePair.of("foo", e));
    }

    private static SimpleModule getPairModule() {
        var module = new SimpleModule(
            "Pair",
            Version.unknownVersion()
        );
        module.addSerializer(new PairJsonSerializer());
        module.addDeserializer(Pair.class, new ImmutablePairJsonDeserializer()); // Default implementation = immutable
        module.addDeserializer(ImmutablePair.class, new ImmutablePairJsonDeserializer());
        module.addDeserializer(MutablePair.class, new MutablePairJsonDeserializer());

        // If you remove this, the deserialization of a values wrapper containing pairs will fail
        module.addDeserializer(ValuesWrapper.class, new ValuesWrapperDeserializer());

        return module;
    }

    protected static class Entity {

        @Getter
        @Setter
        @JsonProperty
        protected String id;

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final Entity other = (Entity) obj;
            return Objects.equals(id, other.id);
        }

        @Override
        public String toString() {
            return "Entity{" + "id=" + id + '}';
        }
    }

    protected static class TestObj<P extends Pair<String, Entity>> {

        @Getter
        @Setter
        @JsonProperty
        protected P value;

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final TestObj<?> other = (TestObj<?>) obj;
            return Objects.equals(value, other.value);
        }

        @Override
        public String toString() {
            return "TestObj{" + "value=" + value + '}';
        }
    }
}
