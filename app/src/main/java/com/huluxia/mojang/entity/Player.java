package com.huluxia.mojang.entity;

import com.huluxia.mojang.converter.InventorySlot;
import com.huluxia.mojang.util.Vector3f;
import java.util.List;

public interface Player {
    PlayerAbilities getAbilities();

    List<InventorySlot> getInventory();

    Vector3f getPos();

    void setAbilities(PlayerAbilities playerAbilities);

    void setInventory(List list);

    void setPos(Vector3f vector3f);
}
