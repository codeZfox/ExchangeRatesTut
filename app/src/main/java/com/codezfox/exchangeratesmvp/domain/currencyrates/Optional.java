package com.codezfox.exchangeratesmvp.domain.currencyrates;


public final class Optional<T> {
    private static final Optional<?> EMPTY = new Optional<>();
    private final T value;

    private Optional() {
        this.value = null;
    }

    private Optional(T value) {
        if (value == null)
            throw new NullPointerException();
        this.value = value;
    }

    public static <T> Optional<T> empty() {
        @SuppressWarnings("unchecked") Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? (Optional<T>) empty() : of(value);
    }

    public T get() {
        return value;
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Optional)) {
            return false;
        }
        Optional<?> other = (Optional<?>) obj;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value != null ? String.format("Optional[%s]", value) : "Optional.empty";
    }
}