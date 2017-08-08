package com.MCWorld.widget.menudrawer;

import android.util.SparseArray;

public enum Position {
    LEFT(0),
    TOP(1),
    RIGHT(2),
    BOTTOM(3),
    START(4),
    END(5);
    
    private static final SparseArray<Position> STRING_MAPPING = null;
    final int mValue;

    static {
        STRING_MAPPING = new SparseArray();
        Position[] values = values();
        int length = values.length;
        int i;
        while (i < length) {
            Position via = values[i];
            STRING_MAPPING.put(via.mValue, via);
            i++;
        }
    }

    private Position(int value) {
        this.mValue = value;
    }

    public static Position fromValue(int value) {
        return (Position) STRING_MAPPING.get(value);
    }
}
