package com.integralblue.httpresponsecache.compat.java.util;

import com.integralblue.httpresponsecache.compat.java.lang.ArrayIndexOutOfBoundsException;
import java.lang.reflect.Array;

public class Arrays {
    public static void checkOffsetAndCount(int arrayLength, int offset, int count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException(arrayLength, offset, count);
        }
    }

    public static boolean[] copyOfRange(boolean[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        boolean[] result = new boolean[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static byte[] copyOfRange(byte[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        byte[] result = new byte[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static char[] copyOfRange(char[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        char[] result = new char[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static double[] copyOfRange(double[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        double[] result = new double[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static float[] copyOfRange(float[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        float[] result = new float[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static int[] copyOfRange(int[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        int[] result = new int[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static long[] copyOfRange(long[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        long[] result = new long[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static short[] copyOfRange(short[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        short[] result = new short[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static <T> T[] copyOfRange(T[] original, int start, int end) {
        int originalLength = original.length;
        if (start > end) {
            throw new IllegalArgumentException();
        } else if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            int resultLength = end - start;
            Object[] result = (Object[]) ((Object[]) Array.newInstance(original.getClass().getComponentType(), resultLength));
            System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
            return result;
        }
    }

    public static <T, U> T[] copyOfRange(U[] original, int start, int end, Class<? extends T[]> newType) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        Object[] result = (Object[]) ((Object[]) Array.newInstance(newType.getComponentType(), resultLength));
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }
}
