package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.entity.FallingBlock;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.Tag;

public class FallingBlockEntityStore<T extends FallingBlock> extends EntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        String str = paramTag.getName();
        if (str.equals("Tile")) {
            paramT.setBlockId(((ByteTag) paramTag).getValue().byteValue() & 255);
        } else if (str.equals("Data")) {
            paramT.setBlockData(((ByteTag) paramTag).getValue().byteValue());
        } else if (str.equals("Time")) {
            paramT.setTime(((ByteTag) paramTag).getValue().byteValue() & 255);
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        localList.add(new ByteTag("Data", paramT.getBlockData()));
        localList.add(new ByteTag("Tile", (byte) paramT.getBlockId()));
        localList.add(new ByteTag("Time", (byte) paramT.getTime()));
        return localList;
    }
}
