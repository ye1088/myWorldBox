package com.MCWorld.mojang.entity;

import com.MCWorld.mojang.converter.InventorySlot;
import com.MCWorld.mojang.util.Vector3f;
import java.util.List;

public interface Player {
    PlayerAbilities getAbilities();

    List<InventorySlot> getInventory();

    Vector3f getPos();

    void setAbilities(PlayerAbilities playerAbilities);

    void setInventory(List list);

    void setPos(Vector3f vector3f);
}
