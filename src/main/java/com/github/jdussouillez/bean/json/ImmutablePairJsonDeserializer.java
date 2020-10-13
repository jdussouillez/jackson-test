package com.github.jdussouillez.bean.json;

import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class ImmutablePairJsonDeserializer
    extends PairJsonDeserializer<ImmutablePair<?, ?>> implements ContextualDeserializer {

    public ImmutablePairJsonDeserializer() {
        super(ImmutablePair.class, true);
    }

    @Override
    protected PairJsonDeserializer<ImmutablePair<?, ?>> newInstance() {
        return new ImmutablePairJsonDeserializer();
    }

    @Override
    protected ImmutablePair<?, ?> newPairInstance(final Object left, final Object right) {
        return ImmutablePair.of(left, right);
    }
}
