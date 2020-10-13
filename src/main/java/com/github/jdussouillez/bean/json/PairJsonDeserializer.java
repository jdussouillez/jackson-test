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
        System.out.println(">> PairJsonDeserialize::createContextual - type = " + type
            + " (raw = " + type.getRawClass() + ")");

        // Added this because it's called with the "List" type (see the ValuesWrapper class)
        // Why is that necessary ?
        if (!Pair.class.isAssignableFrom(type.getRawClass())) {
            System.out.println(">> PairJsonDeserialize::createContextual - The type is not a Pair!");
            return ctx.findRootValueDeserializer(type);
            // return ctx.findContextualValueDeserializer(type, property);
            // return ctx.findNonContextualValueDeserializer(type);
            // return ctx.handlePrimaryContextualization(this, property, type);
            // return ctx.handleSecondaryContextualization(this, property, type);
        }

        return newInstance()
            .leftValueType(type.containedType(0))
            .rightValueType(type.containedType(1));
    }

    protected abstract PairJsonDeserializer<P> newInstance();

    protected abstract P newPairInstance(final Object left, final Object right);
}
