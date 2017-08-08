package com.MCWorld.image.core.common.util;

public enum TriState {
    YES,
    NO,
    UNSET;

    public boolean isSet() {
        return this != UNSET;
    }

    public static TriState valueOf(boolean bool) {
        return bool ? YES : NO;
    }

    public static TriState valueOf(Boolean bool) {
        return bool != null ? valueOf(bool.booleanValue()) : UNSET;
    }

    public boolean asBoolean() {
        switch (1.yR[ordinal()]) {
            case 1:
                return true;
            case 2:
                return false;
            case 3:
                throw new IllegalStateException("No boolean equivalent for UNSET");
            default:
                throw new IllegalStateException("Unrecognized TriState value: " + this);
        }
    }

    public boolean asBoolean(boolean defaultValue) {
        switch (1.yR[ordinal()]) {
            case 1:
                return true;
            case 2:
                return false;
            case 3:
                return defaultValue;
            default:
                throw new IllegalStateException("Unrecognized TriState value: " + this);
        }
    }

    public Boolean asBooleanObject() {
        switch (1.yR[ordinal()]) {
            case 1:
                return Boolean.TRUE;
            case 2:
                return Boolean.FALSE;
            case 3:
                return null;
            default:
                throw new IllegalStateException("Unrecognized TriState value: " + this);
        }
    }

    public int getDbValue() {
        switch (1.yR[ordinal()]) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 3;
        }
    }

    public static TriState fromDbValue(int value) {
        switch (value) {
            case 1:
                return YES;
            case 2:
                return NO;
            default:
                return UNSET;
        }
    }
}
