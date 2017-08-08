package com.MCWorld.mojang.tileentitystore;

import com.MCWorld.mojang.tileentity.NetherReactorTileEntity;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

public class NetherReactorTileEntityStore<T extends NetherReactorTileEntity> extends TileEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        if (paramTag.getName().equals("HasFinished")) {
            if (((ByteTag) paramTag).getValue().byteValue() != (byte) 0) {
                paramT.setFinished(true);
            } else {
                paramT.setFinished(false);
            }
        } else if (paramTag.getName().equals("IsInitialized")) {
            if (((ByteTag) paramTag).getValue().byteValue() != (byte) 0) {
                paramT.setInitialized(true);
            } else {
                paramT.setInitialized(false);
            }
        } else if (paramTag.getName().equals("Progress")) {
            paramT.setProgress(((ShortTag) paramTag).getValue().shortValue());
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        if (paramT.hasFinished()) {
            localList.add(new ByteTag("HasFinished", true));
        } else if (!paramT.isInitialized()) {
            localList.add(new ByteTag("IsInitialized", false));
        }
        localList.add(new ShortTag("Progress", paramT.getProgress()));
        return localList;
    }
}
