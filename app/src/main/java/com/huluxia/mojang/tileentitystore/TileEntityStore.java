package com.huluxia.mojang.tileentitystore;

import com.huluxia.mojang.tileentity.TileEntity;
import java.util.ArrayList;
import java.util.List;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

public class TileEntityStore<T extends TileEntity> {
    public void load(T paramT, CompoundTag paramCompoundTag) {
        for (Tag loadTag : paramCompoundTag.getValue()) {
            loadTag(paramT, loadTag);
        }
    }

    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("x")) {
            paramT.setX(((IntTag) paramTag).getValue().intValue());
        } else if (paramTag.getName().equals("y")) {
            paramT.setY(((IntTag) paramTag).getValue().intValue());
        } else if (paramTag.getName().equals("z")) {
            paramT.setZ(((IntTag) paramTag).getValue().intValue());
        } else if (paramTag.getName().equals("id")) {
            paramT.setId(((StringTag) paramTag).getValue());
        }
    }

    public List<Tag> save(T paramT) {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new StringTag("id", paramT.getId()));
        localArrayList.add(new IntTag("x", paramT.getX()));
        localArrayList.add(new IntTag("y", paramT.getY()));
        localArrayList.add(new IntTag("z", paramT.getZ()));
        return localArrayList;
    }
}
