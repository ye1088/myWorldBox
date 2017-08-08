package com.MCWorld.module.topic;

public enum TopicModule$ComplaintType {
    Type_user(1),
    Type_topic(2),
    Type_comment(3);
    
    private int m_val;

    private TopicModule$ComplaintType(int val) {
        this.m_val = val;
    }

    public int Value() {
        return this.m_val;
    }
}
