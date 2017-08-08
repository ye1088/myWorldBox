package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.entity.LivingEntity;
import com.MCWorld.mojang.entity.PigZombie;
import java.util.List;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

public class PigZombieEntityStore<T extends PigZombie> extends LivingEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("Anger")) {
            paramT.setAnger(((ShortTag) paramTag).getValue().shortValue());
        }
        super.loadTag((LivingEntity) paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save((LivingEntity) paramT);
        localList.add(new ShortTag("Anger", paramT.getAnger()));
        return localList;
    }
}
