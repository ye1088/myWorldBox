package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.entity.Arrow;
import com.MCWorld.mojang.entity.Projectile;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.Tag;

public class ArrowEntityStore<T extends Arrow> extends ProjectileEntityStore<T> {
    public void loadTag(T paramT, Tag paramTag) {
        String str = paramTag.getName();
        if (str.equals("inData")) {
            paramT.setInData(((ByteTag) paramTag).getValue().byteValue());
        } else if (str.equals("player")) {
            if (((ByteTag) paramTag).getValue().byteValue() != (byte) 0) {
                paramT.setShotByPlayer(true);
            } else {
                paramT.setShotByPlayer(true);
            }
        }
        super.loadTag((Projectile) paramT, paramTag);
    }

    public List<Tag> save(T paramT) {
        List localList = super.save((Projectile) paramT);
        localList.add(new ByteTag("inData", paramT.getInData()));
        if (paramT.isShotByPlayer()) {
            localList.add(new ByteTag("player", true));
        }
        return localList;
    }
}
