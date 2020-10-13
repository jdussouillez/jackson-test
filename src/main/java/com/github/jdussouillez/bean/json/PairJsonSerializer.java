package com.github.jdussouillez.bean.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.apache.commons.lang3.tuple.Pair;

public class PairJsonSerializer extends StdSerializer<Pair<?, ?>> {

    public PairJsonSerializer() {
        super(Pair.class, true);
    }

    @Override
    public void serialize(final Pair<?, ?> value, final JsonGenerator gen, final SerializerProvider provider)
        throws IOException {
        gen.writeStartArray(2);
        gen.writeObject(value.getLeft());
        gen.writeObject(value.getRight());
        gen.writeEndArray();
    }
}
