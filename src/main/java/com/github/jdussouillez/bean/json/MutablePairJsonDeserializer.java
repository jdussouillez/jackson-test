package com.github.jdussouillez.bean.json;

import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.apache.commons.lang3.tuple.MutablePair;

public class MutablePairJsonDeserializer
    extends PairJsonDeserializer<MutablePair<?, ?>> implements ContextualDeserializer {

    public MutablePairJsonDeserializer() {
        super(MutablePair.class, true);
    }

    @Override
    protected PairJsonDeserializer<MutablePair<?, ?>> newInstance() {
        return new MutablePairJsonDeserializer();
    }

    @Override
    protected MutablePair<?, ?> newPairInstance(final Object left, final Object right) {
        return MutablePair.of(left, right);
    }
}
