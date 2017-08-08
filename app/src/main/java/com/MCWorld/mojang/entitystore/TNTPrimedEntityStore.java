package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.entity.TNTPrimed;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.Tag;

public class TNTPrimedEntityStore<T extends TNTPrimed> extends EntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("Fuse")) {
            paramT.setFuseTicks(((ByteTag) paramTag).getValue().byteValue());
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        localList.add(new ByteTag("Fuse", paramT.getFuseTicks()));
        return localList;
    }
}
