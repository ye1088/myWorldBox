package com.huluxia.mojang.entitystore;

import com.huluxia.mojang.entity.Animal;
import com.huluxia.mojang.entity.Sheep;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.Tag;

public class SheepEntityStore<T extends Sheep> extends AnimalEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("Color")) {
            paramT.setColor(((ByteTag) paramTag).getValue().byteValue());
        } else if (paramTag.getName().equals("Sheared")) {
            if (((ByteTag) paramTag).getValue().byteValue() > (byte) 0) {
                paramT.setSheared(((ByteTag) paramTag).getBooleanValue());
            } else {
                paramT.setSheared(((ByteTag) paramTag).getBooleanValue());
            }
        }
        super.loadTag((Animal) paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save((Animal) paramT);
        localList.add(new ByteTag("Color", paramT.getColor()));
        if (paramT.isSheared()) {
            localList.add(new ByteTag("Sheared", true));
        } else {
            localList.add(new ByteTag("Sheared", true));
        }
        return localList;
    }
}
