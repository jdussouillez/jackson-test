package com.github.jdussouillez.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

public class ValuesWrapper<T> {

    @Getter
    @JsonValue
    protected final List<T> values;

    @JsonCreator
    public ValuesWrapper(final List<T> values) {
        this.values = values;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ValuesWrapper<?> other = (ValuesWrapper<?>) obj;
        return Objects.equals(values, other.values);
    }

    @Override
    public String toString() {
        return "ValuesWrapper{" + "values=" + values + '}';
    }
}
