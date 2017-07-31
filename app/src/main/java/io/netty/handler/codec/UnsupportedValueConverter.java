package io.netty.handler.codec;

public final class UnsupportedValueConverter<V> implements ValueConverter<V> {
    private static final UnsupportedValueConverter INSTANCE = new UnsupportedValueConverter();

    private UnsupportedValueConverter() {
    }

    public static <V> UnsupportedValueConverter<V> instance() {
        return INSTANCE;
    }

    public V convertObject(Object value) {
        throw new UnsupportedOperationException();
    }

    public V convertBoolean(boolean value) {
        throw new UnsupportedOperationException();
    }

    public boolean convertToBoolean(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertByte(byte value) {
        throw new UnsupportedOperationException();
    }

    public byte convertToByte(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertChar(char value) {
        throw new UnsupportedOperationException();
    }

    public char convertToChar(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertShort(short value) {
        throw new UnsupportedOperationException();
    }

    public short convertToShort(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertInt(int value) {
        throw new UnsupportedOperationException();
    }

    public int convertToInt(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertLong(long value) {
        throw new UnsupportedOperationException();
    }

    public long convertToLong(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertTimeMillis(long value) {
        throw new UnsupportedOperationException();
    }

    public long convertToTimeMillis(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertFloat(float value) {
        throw new UnsupportedOperationException();
    }

    public float convertToFloat(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertDouble(double value) {
        throw new UnsupportedOperationException();
    }

    public double convertToDouble(V v) {
        throw new UnsupportedOperationException();
    }
}
