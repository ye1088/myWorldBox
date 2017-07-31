package net.zhuoweizhang.mcpelauncher.patch;

import com.joshuahuelsman.patchtool.a;
import com.mojang.minecraftpe.MainActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import net.zhuoweizhang.mcpelauncher.MaraudersMap;
import net.zhuoweizhang.mcpelauncher.b;

/* compiled from: PatchUtils */
public final class c {
    public static b cmI = null;

    private c() {
    }

    private static ByteBuffer a(ByteBuffer buf, int addr) {
        if (buf == MainActivity.minecraftLibBuffer && addr >= 0 && addr < MaraudersMap.aon.capacity()) {
            buf = MaraudersMap.aon;
        }
        buf.position(addr);
        return buf;
    }

    public static void a(ByteBuffer buf, a patch) {
        net.zhuoweizhang.mcpelauncher.b.c translator = cmI.cmo;
        patch.count = 0;
        while (patch.count < patch.QJ()) {
            int addr = patch.QN();
            if (translator != null) {
                addr = translator.get(addr);
            }
            a(buf, addr).put(patch.QO());
            patch.count++;
        }
    }

    public static void a(ByteBuffer buf, byte[] original, a patch) {
        net.zhuoweizhang.mcpelauncher.b.c translator = cmI.cmo;
        ByteBuffer originalBuf = ByteBuffer.wrap(original);
        patch.count = 0;
        while (patch.count < patch.QJ()) {
            int addr = patch.QN();
            if (translator != null) {
                addr = translator.get(addr);
            }
            ByteBuffer newBuf = a(buf, addr);
            originalBuf.position(addr);
            byte[] nextData = new byte[patch.QP()];
            originalBuf.get(nextData);
            newBuf.put(nextData);
            patch.count++;
        }
    }

    public static void f(File from, File to) throws IOException {
        InputStream is = new FileInputStream(from);
        byte[] data = new byte[((int) from.length())];
        is.read(data);
        is.close();
        OutputStream os = new FileOutputStream(to);
        os.write(data);
        os.close();
    }

    public static boolean O(File file) throws IOException {
        net.zhuoweizhang.mcpelauncher.b.c translator = cmI.cmo;
        a patch = new a();
        patch.z(file);
        patch.count = 0;
        while (patch.count < patch.QJ()) {
            int address = patch.QN();
            if (translator != null) {
                address = translator.get(address);
            }
            if (address >= cmI.cmf) {
                return false;
            }
            patch.count++;
        }
        return true;
    }

    public static byte[] bx(int rd, int imm) {
        long instr = ((((4064280576L | ((long) (rd << 8))) | ((long) (imm & 255))) | ((long) (((imm >> 8) & 7) << 12))) | ((long) (((imm >> 11) & 1) << 26))) | ((long) (((imm >> 12) & 15) << 16));
        byte[] finalByte = cj(instr);
        System.out.println("Port patch: " + Long.toString(instr, 16));
        return finalByte;
    }

    public static final byte[] cj(long value) {
        return new byte[]{(byte) ((int) (value >>> 16)), (byte) ((int) (value >>> 24)), (byte) ((int) value), (byte) ((int) (value >>> 8))};
    }
}
