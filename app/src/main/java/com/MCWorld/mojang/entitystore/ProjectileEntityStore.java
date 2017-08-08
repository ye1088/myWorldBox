package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.entity.Projectile;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

public class ProjectileEntityStore<T extends Projectile> extends EntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        String str = paramTag.getName();
        if (str.equals("inGround")) {
            if (((ByteTag) paramTag).getValue().byteValue() != (byte) 0) {
                paramT.setInGround(((ByteTag) paramTag).getBooleanValue());
            } else if (str.equals("inTile")) {
                paramT.setInBlock(((ByteTag) paramTag).getValue().byteValue());
            } else if (str.equals("shake")) {
                paramT.setShake(((ByteTag) paramTag).getValue().byteValue());
            } else if (str.equals("xTile")) {
                paramT.setXTile(((ShortTag) paramTag).getValue().shortValue());
            } else if (str.equals("yTile")) {
                paramT.setYTile(((ShortTag) paramTag).getValue().shortValue());
            } else if (str.equals("zTile")) {
                paramT.setZTile(((ShortTag) paramTag).getValue().shortValue());
            }
        }
        super.loadTag(paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save(paramT);
        if (paramT.isInGround()) {
            localList.add(new ByteTag("inGround", true));
            localList.add(new ByteTag("inTile", paramT.getInBlock()));
            localList.add(new ByteTag("shake", paramT.getShake()));
            localList.add(new ShortTag("xTile", paramT.getXTile()));
            localList.add(new ShortTag("yTile", paramT.getYTile()));
            localList.add(new ShortTag("zTile", paramT.getZTile()));
        }
        return localList;
    }
}
