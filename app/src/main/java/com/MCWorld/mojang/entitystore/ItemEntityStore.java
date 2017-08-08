package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.converter.NBTConverter;
import com.MCWorld.mojang.entity.Item;
import hlx.mcstorymode.c;
import java.util.List;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

public class ItemEntityStore<T extends Item> extends EntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("Age")) {
            paramT.setAge(((ShortTag) paramTag).getValue().shortValue());
        } else if (paramTag.getName().equals("Health")) {
            paramT.setHealth(((ShortTag) paramTag).getValue().shortValue());
        } else if (paramTag.getName().equals(c.bVI)) {
            paramT.setItemStack(NBTConverter.readItemStack((CompoundTag) paramTag));
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        localList.add(new ShortTag("Age", paramT.getAge()));
        localList.add(new ShortTag("Health", paramT.getHealth()));
        localList.add(NBTConverter.writeItemStack(paramT.getItemStack(), c.bVI));
        return localList;
    }
}
