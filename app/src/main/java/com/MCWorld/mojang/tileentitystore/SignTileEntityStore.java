package com.MCWorld.mojang.tileentitystore;

import com.MCWorld.mojang.tileentity.SignTileEntity;
import java.util.List;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

public class SignTileEntityStore<T extends SignTileEntity> extends TileEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("Text1")) {
            paramT.setLine(0, ((StringTag) paramTag).getValue());
        } else if (paramTag.getName().equals("Text2")) {
            paramT.setLine(1, ((StringTag) paramTag).getValue());
        } else if (paramTag.getName().equals("Text3")) {
            paramT.setLine(2, ((StringTag) paramTag).getValue());
        } else if (paramTag.getName().equals("Text4")) {
            paramT.setLine(3, ((StringTag) paramTag).getValue());
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        String[] arrayOfString = paramT.getLines();
        for (int i = 0; i < arrayOfString.length; i++) {
            localList.add(new StringTag("Text" + (i + 1), arrayOfString[i]));
        }
        return localList;
    }
}
