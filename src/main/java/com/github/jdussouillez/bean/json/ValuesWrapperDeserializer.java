package com.github.jdussouillez.bean.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.jdussouillez.bean.ValuesWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class ValuesWrapperDeserializer
    extends StdDeserializer<ValuesWrapper<?>> implements ContextualDeserializer {

    @Setter
    protected JavaType valueType;

    public ValuesWrapperDeserializer() {
        super(ValuesWrapper.class);
    }

    @Override
    public ValuesWrapper<?> deserialize(final JsonParser jsonParser, final DeserializationContext ctx)
        throws IOException {
        TreeNode root = jsonParser.readValueAsTree();
        List<?> nodes = new ArrayList<>();
        ObjectMapper jsonMapper = (ObjectMapper) jsonParser.getCodec();
        for (int i = 0; i < root.size(); i++) {
            nodes.add(jsonMapper.convertValue(root.get(i), valueType));
        }
        return new ValuesWrapper<>(nodes);
    }

    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext ctx, final BeanProperty property)
        throws JsonMappingException {
        JavaType type = property != null
            ? property.getType()
            : ctx.getContextualType();
        return new ValuesWrapperDeserializer()
            .valueType(type.containedType(0));
    }
}
