package com.huluxia.mojang.converter;

import com.huluxia.mojang.entity.Entity;
import com.huluxia.mojang.tileentity.TileEntity;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EntityDataConverter {
    public static final byte[] header = new byte[]{(byte) 69, (byte) 78, (byte) 84, (byte) 0, (byte) 1, (byte) 0, (byte) 0, (byte) 0};

    public static class EntityData {
        public List<Entity> entities;
        public List<TileEntity> tileEntities;

        EntityData(List<Entity> entities, List<TileEntity> tileEntities) {
            this.entities = entities;
            this.tileEntities = tileEntities;
        }

        public String toString() {
            return "EntityData [entities = " + this.entities + ", tileEntities = " + this.tileEntities + " ]";
        }
    }

    public static void main(String[] paramArrayOfString) throws Exception {
        EntityData localEntityData = read(new File(paramArrayOfString[0]));
        System.out.println(localEntityData);
        write(localEntityData.entities, localEntityData.tileEntities, new File(paramArrayOfString[1]));
    }

    public static EntityData read(File paramFile) throws IOException {
        return LevelDBConverter.readAllEntities(new File(paramFile.getParentFile(), "db"));
    }

    public static void write(List<Entity> paramList, List<TileEntity> list, File paramFile) throws IOException {
        LevelDBConverter.writeAllEntities(paramList, new File(paramFile.getParentFile(), "db"));
    }
}
