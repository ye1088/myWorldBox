package com.MCWorld.mojang.entity;

import java.io.Serializable;

public class EntityItem implements Serializable {
    private Entity entity;
    private int num;

    public EntityItem(int paramInt, Entity paramEntity) {
        this.num = paramInt;
        this.entity = paramEntity;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
