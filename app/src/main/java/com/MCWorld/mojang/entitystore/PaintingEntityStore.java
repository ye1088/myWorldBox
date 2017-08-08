package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.entity.Painting;
import com.MCWorld.mojang.util.Vector3f;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.StringTag;
import org.spout.nbt.Tag;

public class PaintingEntityStore<T extends Painting> extends EntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        String str = paramTag.getName();
        if (str.equals("Dir")) {
            paramT.setDirection(((ByteTag) paramTag).getValue().byteValue());
        } else if (str.equals("Motive")) {
            paramT.setArt(((StringTag) paramTag).getValue());
        } else if (str.equals("TileX")) {
            paramT.getBlockCoordinates().setX((float) ((IntTag) paramTag).getValue().intValue());
        } else if (str.equals("TileY")) {
            paramT.getBlockCoordinates().setY((float) ((IntTag) paramTag).getValue().intValue());
        } else if (str.equals("TileZ")) {
            paramT.getBlockCoordinates().setZ((float) ((IntTag) paramTag).getValue().intValue());
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        localList.add(new ByteTag("Dir", paramT.getDirection()));
        localList.add(new StringTag("Motive", paramT.getArt()));
        Vector3f localVector3f = paramT.getBlockCoordinates();
        localList.add(new IntTag("TileX", localVector3f.getBlockX()));
        localList.add(new IntTag("TileY", localVector3f.getBlockY()));
        localList.add(new IntTag("TileZ", localVector3f.getBlockZ()));
        return localList;
    }
}
