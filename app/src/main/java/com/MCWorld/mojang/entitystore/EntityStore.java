package com.MCWorld.mojang.entitystore;

import com.MCWorld.mojang.converter.NBTConverter;
import com.MCWorld.mojang.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import org.spout.nbt.ByteTag;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.FloatTag;
import org.spout.nbt.IntTag;
import org.spout.nbt.ListTag;
import org.spout.nbt.ShortTag;
import org.spout.nbt.Tag;

public class EntityStore<T extends Entity> {
    public void load(T paramT, CompoundTag paramCompoundTag) {
        for (Tag loadTag : paramCompoundTag.getValue()) {
            loadTag(paramT, loadTag);
        }
    }

    public void loadTag(T paramT, Tag paramTag) {
        String str = paramTag.getName();
        if (str.equals("Pos")) {
            paramT.setLocation(NBTConverter.readVector((ListTag) paramTag));
        }
        if (str.equals("Motion")) {
            paramT.setVelocity(NBTConverter.readVector((ListTag) paramTag));
        } else if (str.equals("Air")) {
            paramT.setAirTicks(((ShortTag) paramTag).getValue().shortValue());
        } else if (str.equals("Fire")) {
            paramT.setFireTicks(((ShortTag) paramTag).getValue().shortValue());
        } else if (str.equals("FallDistance")) {
            paramT.setFallDistance(((FloatTag) paramTag).getValue().floatValue());
        } else if (str.equals("Riding")) {
            paramT.setRiding(NBTConverter.readSingleEntity((CompoundTag) paramTag));
        } else if (str.equals("Rotation")) {
            List localList = ((ListTag) paramTag).getValue();
            paramT.setYaw(((FloatTag) localList.get(0)).getValue().floatValue());
            paramT.setPitch(((FloatTag) localList.get(1)).getValue().floatValue());
        } else if (str.equals("OnGround")) {
            paramT.setOnGround(((ByteTag) paramTag).getValue().byteValue() > (byte) 0);
        } else if (!str.equals("id")) {
        }
    }

    public List<Tag> save(T paramT) {
        ArrayList localArrayList1 = new ArrayList();
        localArrayList1.add(new ShortTag("Air", paramT.getAirTicks()));
        localArrayList1.add(new FloatTag("FallDistance", paramT.getFallDistance()));
        localArrayList1.add(new ShortTag("Fire", paramT.getFireTicks()));
        localArrayList1.add(NBTConverter.writeVector(paramT.getVelocity(), "Motion"));
        localArrayList1.add(NBTConverter.writeVector(paramT.getLocation(), "Pos"));
        if (paramT.getRiding() != null) {
            localArrayList1.add(NBTConverter.writeEntity(paramT.getRiding(), "Riding"));
        }
        ArrayList localArrayList2 = new ArrayList(2);
        localArrayList2.add(new FloatTag("", paramT.getYaw()));
        localArrayList2.add(new FloatTag("", paramT.getPitch()));
        localArrayList1.add(new ListTag("Rotation", FloatTag.class, localArrayList2));
        localArrayList1.add(new ByteTag("OnGround", paramT.isOnGround()));
        localArrayList1.add(new IntTag("id", paramT.getEntityTypeId()));
        return localArrayList1;
    }
}
