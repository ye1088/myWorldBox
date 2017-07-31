package com.huluxia.mojang.tileentitystore;

import com.huluxia.mojang.converter.NBTConverter;
import com.huluxia.mojang.tileentity.ContainerTileEntity;
import java.util.List;
import org.spout.nbt.ListTag;
import org.spout.nbt.Tag;

public class ContainerTileEntityStore<T extends ContainerTileEntity> extends TileEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("Items")) {
            paramT.setItems(NBTConverter.readInventory((ListTag) paramTag));
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        localList.add(NBTConverter.writeInventory(paramT.getItems(), "Items"));
        return localList;
    }
}
