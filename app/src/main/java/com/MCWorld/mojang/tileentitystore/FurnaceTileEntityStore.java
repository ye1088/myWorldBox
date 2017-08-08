package com.MCWorld.mojang.tileentitystore;

import com.MCWorld.mojang.tileentity.ContainerTileEntity;
import com.MCWorld.mojang.tileentity.FurnaceTileEntity;
import java.util.List;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

public class FurnaceTileEntityStore<T extends FurnaceTileEntity> extends ContainerTileEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        String str = paramTag.getName();
        if (str.equals("BurnTime")) {
            paramT.setBurnTime(((ShortTag) paramTag).getValue().shortValue());
        }
        if (str.equals("CookTime")) {
            paramT.setCookTime(((ShortTag) paramTag).getValue().shortValue());
        }
        super.loadTag((ContainerTileEntity) paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save((ContainerTileEntity) paramT);
        localList.add(new ShortTag("BurnTime", paramT.getBurnTime()));
        localList.add(new ShortTag("CookTime", paramT.getCookTime()));
        return localList;
    }
}
