package com.MCWorld.utils;

public enum UtilsMenu$COMPLAINT_VALUE {
    AD(201),
    EROTIC(202),
    NULLED(203),
    INSULT(204),
    IMAGE(205);
    
    private int m_val;

    private UtilsMenu$COMPLAINT_VALUE(int _val) {
        this.m_val = _val;
    }

    public int Value() {
        return this.m_val;
    }
}
