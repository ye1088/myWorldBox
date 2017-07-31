package com.huluxia.mojang.entitystore;

import com.huluxia.mojang.entity.Animal;
import com.huluxia.mojang.entity.LivingEntity;
import java.util.List;
import org.spout.nbt.IntTag;
import org.spout.nbt.Tag;

public class AnimalEntityStore<T extends Animal> extends LivingEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("Age")) {
            paramT.setAge(((IntTag) paramTag).getValue().intValue());
        } else if (paramTag.getName().equals("InLove")) {
            paramT.setInLove(((IntTag) paramTag).getValue().intValue());
        }
        super.loadTag((LivingEntity) paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save((LivingEntity) paramT);
        localList.add(new IntTag("Age", paramT.getAge()));
        localList.add(new IntTag("InLove", paramT.getInLove()));
        return localList;
    }
}
