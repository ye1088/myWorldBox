package com.joshuahuelsman.patchtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* compiled from: PTPatch */
public class a {
    public static final byte[] bHI = new byte[]{(byte) -1, (byte) 80, (byte) 84, (byte) 80};
    public static final byte[] bHJ = new byte[]{(byte) -86, (byte) -35, (byte) -18};
    private byte[] bHK;
    a bHL = new a(this);
    public int count;
    public String name;

    public void C(byte[] patch_array) {
        this.bHK = patch_array;
        this.bHL.bHM = QI();
        this.bHL.bHN = QJ();
        this.bHL.bHO = QK();
        this.count = 0;
    }

    public void z(File patchf) throws IOException {
        this.bHK = new byte[((int) patchf.length())];
        InputStream is = new FileInputStream(patchf);
        is.read(this.bHK);
        is.close();
        this.bHL.bHM = QI();
        this.bHL.bHN = QJ();
        this.bHL.bHO = QK();
        this.count = 0;
    }

    public int QI() {
        return this.bHK[4];
    }

    public int QJ() {
        return this.bHK[5];
    }

    public byte[] QK() {
        byte[] ret = new byte[(this.bHL.bHN * 4)];
        for (int i = 0; i < this.bHL.bHN * 4; i++) {
            ret[i] = this.bHK[i + 6];
        }
        return ret;
    }

    public boolean QL() {
        if (this.bHK[0] == bHI[0] && this.bHK[1] == bHI[1] && this.bHK[2] == bHI[2] && this.bHK[3] == bHI[3]) {
            return true;
        }
        return false;
    }

    public void QM() {
    }

    public int QN() {
        int index = E(new byte[]{this.bHL.bHO[this.count * 4], this.bHL.bHO[(this.count * 4) + 1], this.bHL.bHO[(this.count * 4) + 2], this.bHL.bHO[(this.count * 4) + 3]});
        return E(new byte[]{this.bHK[index], this.bHK[index + 1], this.bHK[index + 2], this.bHK[index + 3]});
    }

    public int getCurrentIndex() {
        return E(new byte[]{this.bHL.bHO[this.count * 4], this.bHL.bHO[(this.count * 4) + 1], this.bHL.bHO[(this.count * 4) + 2], this.bHL.bHO[(this.count * 4) + 3]});
    }

    public byte[] QO() {
        byte[] array = new byte[QP()];
        int index = getCurrentIndex();
        int i2 = 0;
        for (int i = 0; i < QP(); i++) {
            array[i2] = this.bHK[(index + 4) + i];
            i2++;
        }
        return array;
    }

    public int QP() {
        int end;
        byte[] i = new byte[]{this.bHL.bHO[this.count * 4], this.bHL.bHO[(this.count * 4) + 1], this.bHL.bHO[(this.count * 4) + 2], this.bHL.bHO[(this.count * 4) + 3]};
        if (this.count != this.bHL.bHN - 1) {
            end = E(new byte[]{this.bHL.bHO[(this.count + 1) * 4], this.bHL.bHO[((this.count + 1) * 4) + 1], this.bHL.bHO[((this.count + 1) * 4) + 2], this.bHL.bHO[((this.count + 1) * 4) + 3]});
        } else {
            end = this.bHK.length;
        }
        return end - (E(i) + 4);
    }

    public void A(File f) throws IOException {
        byte[] barray = new byte[((int) f.length())];
        InputStream is = new FileInputStream(f);
        is.read(barray);
        is.close();
        ByteBuffer buf = ByteBuffer.wrap(barray);
        this.count = 0;
        while (this.count < this.bHL.bHN) {
            buf.position(QN());
            buf.put(QO());
            this.count++;
        }
        f.delete();
        OutputStream os = new FileOutputStream(f);
        os.write(buf.array());
        os.close();
    }

    public void D(byte[] barray) throws IOException {
        g(ByteBuffer.wrap(barray));
    }

    public void g(ByteBuffer buf) throws IOException {
        this.count = 0;
        while (this.count < this.bHL.bHN) {
            buf.position(QN());
            buf.put(QO());
            this.count++;
        }
    }

    public void a(ByteBuffer buf, byte[] original) {
        ByteBuffer originalBuf = ByteBuffer.wrap(original);
        this.count = 0;
        while (this.count < this.bHL.bHN) {
            int nextAddr = QN();
            buf.position(nextAddr);
            originalBuf.position(nextAddr);
            byte[] nextData = new byte[QP()];
            originalBuf.get(nextData);
            buf.put(nextData);
            this.count++;
        }
    }

    public byte[] QQ() {
        this.count = 0;
        int metaDataStart = (this.bHL.bHN * 4) + 6;
        byte[] retval = new byte[(getCurrentIndex() - metaDataStart)];
        System.arraycopy(this.bHK, metaDataStart, retval, 0, retval.length);
        return retval;
    }

    public String getDescription() {
        try {
            return new String(QQ(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final byte[] mA(int value) {
        return new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
    }

    public static final int E(byte[] b) {
        return (((b[0] << 24) + ((b[1] & 255) << 16)) + ((b[2] & 255) << 8)) + (b[3] & 255);
    }

    public static byte[] gU(String patch) throws IOException {
        byte[] ret = new byte[((int) new File(patch).length())];
        InputStream is = new FileInputStream(patch);
        is.read(ret, 0, ret.length);
        is.close();
        return ret;
    }
}
