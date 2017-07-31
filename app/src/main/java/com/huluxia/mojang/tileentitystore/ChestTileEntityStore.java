package com.huluxia.mojang.tileentitystore;

import com.huluxia.mojang.tileentity.ChestTileEntity;
import com.huluxia.mojang.tileentity.ContainerTileEntity;
import java.util.List;
import org.spout.nbt.IntTag;
import org.spout.nbt.Tag;

public class ChestTileEntityStore<T extends ChestTileEntity> extends ContainerTileEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        String str = paramTag.getName();
        if (str.equals("pairx")) {
            paramT.setPairX(((IntTag) paramTag).getValue().intValue());
        } else if (str.equals("pairz")) {
            paramT.setPairZ(((IntTag) paramTag).getValue().intValue());
        }
        super.loadTag((ContainerTileEntity) paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save((ContainerTileEntity) paramT);
        if (paramT.getPairX() != -65535) {
            localList.add(new IntTag("pairx", paramT.getPairX()));
            localList.add(new IntTag("pairz", paramT.getPairZ()));
        }
        return localList;
    }
}
