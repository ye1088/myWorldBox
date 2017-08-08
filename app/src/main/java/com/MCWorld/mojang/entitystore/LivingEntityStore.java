package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.entity.LivingEntity;
import java.util.List;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

public class LivingEntityStore<T extends LivingEntity> extends EntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("AttackTime")) {
            paramT.setAttackTime(((ShortTag) paramTag).getValue().shortValue());
        } else if (paramTag.getName().equals("DeathTime")) {
            paramT.setDeathTime(((ShortTag) paramTag).getValue().shortValue());
        } else if (paramTag.getName().equals("Health")) {
            paramT.setHealth(((ShortTag) paramTag).getValue().shortValue());
        } else if (paramTag.getName().equals("HurtTime")) {
            paramT.setHurtTime(((ShortTag) paramTag).getValue().shortValue());
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        localList.add(new ShortTag("AttackTime", paramT.getAttackTime()));
        localList.add(new ShortTag("DeathTime", paramT.getDeathTime()));
        localList.add(new ShortTag("Health", paramT.getHealth()));
        localList.add(new ShortTag("HurtTime", paramT.getHurtTime()));
        return localList;
    }
}
