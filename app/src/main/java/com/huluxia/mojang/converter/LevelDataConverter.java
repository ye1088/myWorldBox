package com.huluxia.mojang.converter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.stream.NBTInputStream;
import org.spout.nbt.stream.NBTOutputStream;

public final class LevelDataConverter {
    public static final byte[] header = new byte[]{(byte) 4, (byte) 0, (byte) 0, (byte) 0};

    public static void main(String[] paramArrayOfString) throws Exception {
    }

    public static Level readV010(File paramFile) throws IOException {
        BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
        localBufferedInputStream.skip(8);
        LevelV010 localLevel = NBTConverter.readLevel((CompoundTag) new NBTInputStream(localBufferedInputStream, false, true).readTag());
        localBufferedInputStream.close();
        File localFile = new File(paramFile.getParentFile(), "db");
        if (localFile.exists()) {
            LevelDBConverter.readLevel(localLevel, localFile);
        }
        return localLevel;
    }

    public static Level readV0110(File paramFile) throws IOException {
        BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
        localBufferedInputStream.skip(8);
        LevelV0110 localLevel = NBTConverter.readLevelV0110((CompoundTag) new NBTInputStream(localBufferedInputStream, false, true).readTag());
        localBufferedInputStream.close();
        File localFile = new File(paramFile.getParentFile(), "db");
        if (localFile.exists()) {
            LevelDBConverter.readLevelV0110(localLevel, localFile);
        }
        return localLevel;
    }

    public static void writeV010(LevelV010 paramLevel, File paramFile) throws IOException {
        boolean bool = true;
        File localFile = new File(paramFile.getParentFile(), "db");
        if (!localFile.exists()) {
            bool = false;
        }
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        new NBTOutputStream(localByteArrayOutputStream, false, true).writeTag(NBTConverter.writeLevel(paramLevel, bool));
        DataOutputStream localDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(paramFile)));
        int i = localByteArrayOutputStream.size();
        localDataOutputStream.write(header);
        localDataOutputStream.writeInt(Integer.reverseBytes(i));
        localByteArrayOutputStream.writeTo(localDataOutputStream);
        localDataOutputStream.close();
        if (bool) {
            LevelDBConverter.writeLevel(paramLevel, localFile);
        }
    }

    public static void writeV0110(LevelV0110 paramLevel, File paramFile) throws IOException {
        File localFile = new File(paramFile.getParentFile(), "db");
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        new NBTOutputStream(localByteArrayOutputStream, false, true).writeTag(NBTConverter.writeLevelV0110(paramLevel));
        DataOutputStream localDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(paramFile)));
        int i = localByteArrayOutputStream.size();
        localDataOutputStream.write(header);
        localDataOutputStream.writeInt(Integer.reverseBytes(i));
        localByteArrayOutputStream.writeTo(localDataOutputStream);
        localDataOutputStream.close();
        LevelDBConverter.writeLevelV0110(paramLevel, localFile);
    }
}
