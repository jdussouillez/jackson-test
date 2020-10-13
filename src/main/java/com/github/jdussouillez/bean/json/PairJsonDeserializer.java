package com.github.jdussouillez.bean.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

public abstract class PairJsonDeserializer<P extends Pair<?, ?>>
    extends StdDeserializer<P> implements ContextualDeserializer {

    @Setter
    protected JavaType leftValueType;

    @Setter
    protected JavaType rightValueType;

    public PairJsonDeserializer(final Class<P> clazz) {
        super(clazz);
    }

    @SuppressWarnings("unchecked")
    protected PairJsonDeserializer(final Class<?> clazz, final boolean unsafe) {
        // Based on https://fasterxml.github.io/jackson-databind/javadoc/2.8/com/fasterxml/jackson/databind/ser/std/StdSerializer.html#StdSerializer(java.lang.Class,%20boolean)
        this((Class<P>) clazz);
    }

    @Override
    public P deserialize(final JsonParser jsonParser, final DeserializationContext ctx)
        throws IOException {
        System.out.println(">> PairJsonDeserialize::deserialize - left type = " + leftValueType);
        System.out.println(">> PairJsonDeserialize::deserialize - right type = " + rightValueType);
        TreeNode node = jsonParser.readValueAsTree();
        var mapper = (ObjectMapper) jsonParser.getCodec();
        return newPairInstance(
            mapper.convertValue(node.get(0), leftValueType),
            mapper.convertValue(node.get(1), rightValueType)
        );
    }

    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext ctx, final BeanProperty property)
        throws JsonMappingException {
        JavaType type = property != null
            ? property.getType()
            : ctx.getContextualType();
        System.out.println(">> PairJsonDeserialize::createContextual - type = " + type);
        if (!type.hasRawClass(Pair.class)) {
            System.out.println(">> PairJsonDeserialize::createContextual - The type is not a Pair!");
            return ctx.findRootValueDeserializer(type);
        }
        var deserializer = newInstance();
        deserializer.leftValueType(type.containedType(0));
        deserializer.rightValueType(type.containedType(1));
        return deserializer;
    }

    protected abstract PairJsonDeserializer<P> newInstance();

    protected abstract P newPairInstance(final Object left, final Object right);
}
