package com.huluxia.widget.gif;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.v4.app.FragmentTransaction;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* compiled from: GifDecoder */
public class b {
    public static final int byN = 0;
    public static final int byO = 1;
    public static final int byP = 2;
    public static final int byQ = -1;
    private static final int bzs = 4096;
    private boolean aaA = false;
    private int bgColor;
    private byte[] block = new byte[256];
    private int blockSize = 0;
    private boolean byR;
    private int byS;
    private int byT = 1;
    private int[] byU;
    private int[] byV;
    private int[] byW;
    private int byX;
    private int byY;
    private int byZ;
    private boolean bza;
    private boolean bzb;
    private int bzc;
    private int bzd;
    private int bze;
    private int bzf;
    private int bzg;
    private int bzh;
    private int bzi;
    private int bzj;
    private int bzk;
    private Bitmap bzl;
    private Bitmap bzm;
    private c bzn = null;
    private int bzo = 0;
    private int bzp = 0;
    private boolean bzq = false;
    private int bzr;
    private short[] bzt;
    private byte[] bzu;
    private byte[] bzv;
    private byte[] bzw;
    private c bzx;
    private int bzy;
    private int delay = 0;
    public int height;
    private InputStream in;
    private int status;
    public int width;

    public void free() {
        c fg = this.bzx;
        while (fg != null) {
            fg.bzl = null;
            this.bzx = this.bzx.bzz;
            fg = this.bzx;
        }
        if (this.in != null) {
            try {
                this.in.close();
            } catch (Exception e) {
            }
            this.in = null;
        }
    }

    public int getStatus() {
        return this.status;
    }

    public boolean Oy() {
        return this.status == -1;
    }

    public int lZ(int n) {
        this.delay = -1;
        if (n >= 0 && n < this.bzy) {
            c f = mb(n);
            if (f != null) {
                this.delay = f.delay;
            }
        }
        return this.delay;
    }

    public int[] Oz() {
        c f = this.bzx;
        int[] d = new int[this.bzy];
        int i = 0;
        while (f != null && i < this.bzy) {
            d[i] = f.delay;
            f = f.bzz;
            i++;
        }
        return d;
    }

    public int getFrameCount() {
        return this.bzy;
    }

    public Bitmap OA() {
        return ma(0);
    }

    public int OB() {
        return this.byT;
    }

    private void OC() {
        int c;
        int i;
        int k;
        int[] dest = new int[(this.width * this.height)];
        if (this.bzp > 0) {
            if (this.bzp == 3) {
                int n = this.bzy - 2;
                if (n > 0) {
                    this.bzm = ma(n - 1);
                } else {
                    this.bzm = null;
                }
            }
            if (this.bzm != null) {
                this.bzm.getPixels(dest, 0, this.width, 0, 0, this.width, this.height);
                if (this.bzp == 2) {
                    c = 0;
                    if (!this.bzq) {
                        c = this.byY;
                    }
                    for (i = 0; i < this.bzk; i++) {
                        int n1 = ((this.bzi + i) * this.width) + this.bzh;
                        int n2 = n1 + this.bzj;
                        for (k = n1; k < n2; k++) {
                            dest[k] = c;
                        }
                    }
                }
            }
        }
        int pass = 1;
        int inc = 8;
        int iline = 0;
        for (i = 0; i < this.bzg; i++) {
            int line = i;
            if (this.bzb) {
                if (iline >= this.bzg) {
                    pass++;
                    switch (pass) {
                        case 2:
                            iline = 4;
                            break;
                        case 3:
                            iline = 2;
                            inc = 4;
                            break;
                        case 4:
                            iline = 1;
                            inc = 2;
                            break;
                    }
                }
                line = iline;
                iline += inc;
            }
            line += this.bze;
            if (line < this.height) {
                k = line * this.width;
                int dx = k + this.bzd;
                int dlim = dx + this.bzf;
                if (this.width + k < dlim) {
                    dlim = k + this.width;
                }
                int sx = i * this.bzf;
                while (dx < dlim) {
                    int sx2 = sx + 1;
                    c = this.byW[this.bzw[sx] & 255];
                    if (c != 0) {
                        dest[dx] = c;
                    }
                    dx++;
                    sx = sx2;
                }
            }
        }
        this.bzl = Bitmap.createBitmap(dest, this.width, this.height, Config.ARGB_4444);
    }

    public Bitmap ma(int n) {
        c frame = mb(n);
        if (frame == null) {
            return null;
        }
        return frame.bzl;
    }

    public c OD() {
        return this.bzn;
    }

    public c mb(int n) {
        c frame = this.bzx;
        int i = 0;
        while (frame != null) {
            if (i == n) {
                return frame;
            }
            frame = frame.bzz;
            i++;
        }
        return null;
    }

    public void reset() {
        this.bzn = this.bzx;
    }

    public c OE() {
        if (this.aaA) {
            if (this.status != 0) {
                this.bzn = this.bzn.bzz;
                if (this.bzn == null) {
                    this.bzn = this.bzx;
                }
            } else if (this.bzn.bzz != null) {
                this.bzn = this.bzn.bzz;
            }
            return this.bzn;
        }
        this.aaA = true;
        return this.bzx;
    }

    public int read(byte[] data) {
        this.in = new ByteArrayInputStream(data);
        return OF();
    }

    private int OF() {
        init();
        if (this.in != null) {
            OL();
            if (!OH()) {
                OJ();
                if (this.bzy < 0) {
                    this.status = 1;
                } else {
                    this.status = -1;
                }
            }
            try {
                this.in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.status = 2;
        }
        return this.status;
    }

    private void OG() {
        int code;
        int npix = this.bzf * this.bzg;
        if (this.bzw == null || this.bzw.length < npix) {
            this.bzw = new byte[npix];
        }
        if (this.bzt == null) {
            this.bzt = new short[4096];
        }
        if (this.bzu == null) {
            this.bzu = new byte[4096];
        }
        if (this.bzv == null) {
            this.bzv = new byte[FragmentTransaction.TRANSIT_FRAGMENT_OPEN];
        }
        int data_size = read();
        int clear = 1 << data_size;
        int end_of_information = clear + 1;
        int available = clear + 2;
        int old_code = -1;
        int code_size = data_size + 1;
        int code_mask = (1 << code_size) - 1;
        for (code = 0; code < clear; code++) {
            this.bzt[code] = (short) 0;
            this.bzu[code] = (byte) code;
        }
        int bi = 0;
        int first = 0;
        int count = 0;
        int bits = 0;
        int datum = 0;
        int i = 0;
        int pi = 0;
        int top = 0;
        while (i < npix) {
            int i2;
            if (top == 0) {
                if (bits >= code_size) {
                    code = datum & code_mask;
                    datum >>= code_size;
                    bits -= code_size;
                    if (code > available) {
                        break;
                    } else if (code == end_of_information) {
                        i2 = top;
                        break;
                    } else if (code == clear) {
                        code_size = data_size + 1;
                        code_mask = (1 << code_size) - 1;
                        available = clear + 2;
                        old_code = -1;
                    } else if (old_code == -1) {
                        i2 = top + 1;
                        this.bzv[top] = this.bzu[code];
                        old_code = code;
                        first = code;
                        top = i2;
                    } else {
                        int in_code = code;
                        if (code == available) {
                            i2 = top + 1;
                            this.bzv[top] = (byte) first;
                            code = old_code;
                            top = i2;
                        }
                        while (code > clear) {
                            i2 = top + 1;
                            this.bzv[top] = this.bzu[code];
                            code = this.bzt[code];
                            top = i2;
                        }
                        first = this.bzu[code] & 255;
                        if (available >= 4096) {
                            i2 = top;
                            break;
                        }
                        i2 = top + 1;
                        this.bzv[top] = (byte) first;
                        this.bzt[available] = (short) old_code;
                        this.bzu[available] = (byte) first;
                        available++;
                        if ((available & code_mask) == 0 && available < 4096) {
                            code_size++;
                            code_mask += available;
                        }
                        old_code = in_code;
                    }
                } else {
                    if (count == 0) {
                        count = OI();
                        if (count <= 0) {
                            i2 = top;
                            break;
                        }
                        bi = 0;
                    }
                    datum += (this.block[bi] & 255) << bits;
                    bits += 8;
                    bi++;
                    count--;
                }
            } else {
                i2 = top;
            }
            i2--;
            int pi2 = pi + 1;
            this.bzw[pi] = this.bzv[i2];
            i++;
            pi = pi2;
            top = i2;
        }
        for (i = pi; i < npix; i++) {
            this.bzw[i] = (byte) 0;
        }
    }

    private boolean OH() {
        return this.status != 0;
    }

    private void init() {
        this.status = 0;
        this.bzy = 0;
        this.bzx = null;
        this.byU = null;
        this.byV = null;
    }

    private int read() {
        int curByte = 0;
        try {
            curByte = this.in.read();
        } catch (Exception e) {
            this.status = 1;
        }
        return curByte;
    }

    private int OI() {
        this.blockSize = read();
        int n = 0;
        if (this.blockSize > 0) {
            while (n < this.blockSize) {
                try {
                    int count = this.in.read(this.block, n, this.blockSize - n);
                    if (count == -1) {
                        break;
                    }
                    n += count;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (n < this.blockSize) {
                this.status = 1;
            }
        }
        return n;
    }

    private int[] mc(int ncolors) {
        int nbytes = ncolors * 3;
        int[] tab = null;
        byte[] c = new byte[nbytes];
        int n = 0;
        try {
            n = this.in.read(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (n < nbytes) {
            this.status = 1;
        } else {
            tab = new int[256];
            int j = 0;
            int i = 0;
            while (i < ncolors) {
                int j2 = j + 1;
                int r = c[j] & 255;
                j = j2 + 1;
                int g = c[j2] & 255;
                j2 = j + 1;
                int i2 = i + 1;
                tab[i] = ((-16777216 | (r << 16)) | (g << 8)) | (c[j] & 255);
                j = j2;
                i = i2;
            }
        }
        return tab;
    }

    private void OJ() {
        boolean done = false;
        while (!done && !OH()) {
            switch (read()) {
                case 0:
                    break;
                case 33:
                    switch (read()) {
                        case 249:
                            OK();
                            break;
                        case 255:
                            OI();
                            String app = "";
                            for (int i = 0; i < 11; i++) {
                                app = app + ((char) this.block[i]);
                            }
                            if (!app.equals("NETSCAPE2.0")) {
                                OR();
                                break;
                            } else {
                                OO();
                                break;
                            }
                        default:
                            OR();
                            break;
                    }
                case 44:
                    OM();
                    break;
                case 59:
                    done = true;
                    break;
                default:
                    this.status = 1;
                    break;
            }
        }
    }

    private void OK() {
        boolean z = true;
        read();
        int packed = read();
        this.bzo = (packed & 28) >> 2;
        if (this.bzo == 0) {
            this.bzo = 1;
        }
        if ((packed & 1) == 0) {
            z = false;
        }
        this.bzq = z;
        this.delay = OP();
        this.delay = this.delay < 5 ? 100 : this.delay * 10;
        this.bzr = read();
        read();
    }

    private void OL() {
        String id = "";
        for (int i = 0; i < 6; i++) {
            id = id + ((char) read());
        }
        if (id.startsWith("GIF")) {
            ON();
            if (this.byR && !OH()) {
                this.byU = mc(this.byS);
                this.bgColor = this.byU[this.byX];
                return;
            }
            return;
        }
        this.status = 1;
    }

    private void OM() {
        boolean z;
        this.bzd = OP();
        this.bze = OP();
        this.bzf = OP();
        this.bzg = OP();
        int packed = read();
        if ((packed & 128) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.bza = z;
        if ((packed & 64) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.bzb = z;
        this.bzc = 2 << (packed & 7);
        if (this.bza) {
            this.byV = mc(this.bzc);
            this.byW = this.byV;
        } else {
            this.byW = this.byU;
            if (this.byX == this.bzr) {
                this.bgColor = 0;
            }
        }
        int save = 0;
        if (this.bzq) {
            save = this.byW[this.bzr];
            this.byW[this.bzr] = 0;
        }
        if (this.byW == null) {
            this.status = 1;
        }
        if (!OH()) {
            OG();
            OR();
            if (!OH()) {
                this.bzy++;
                this.bzl = Bitmap.createBitmap(this.width, this.height, Config.ARGB_4444);
                OC();
                if (this.bzx == null) {
                    this.bzx = new c(this.bzl, this.delay);
                    this.bzn = this.bzx;
                } else {
                    c f = this.bzx;
                    while (f.bzz != null) {
                        f = f.bzz;
                    }
                    f.bzz = new c(this.bzl, this.delay);
                }
                if (this.bzq) {
                    this.byW[this.bzr] = save;
                }
                OQ();
            }
        }
    }

    private void ON() {
        this.width = OP();
        this.height = OP();
        int packed = read();
        this.byR = (packed & 128) != 0;
        this.byS = 2 << (packed & 7);
        this.byX = read();
        this.byZ = read();
    }

    private void OO() {
        do {
            OI();
            if (this.block[0] == (byte) 1) {
                this.byT = ((this.block[2] & 255) << 8) | (this.block[1] & 255);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!OH());
    }

    private int OP() {
        return read() | (read() << 8);
    }

    private void OQ() {
        this.bzp = this.bzo;
        this.bzh = this.bzd;
        this.bzi = this.bze;
        this.bzj = this.bzf;
        this.bzk = this.bzg;
        this.bzm = this.bzl;
        this.byY = this.bgColor;
        this.bzo = 0;
        this.bzq = false;
        this.delay = 0;
        this.byV = null;
    }

    private void OR() {
        do {
            OI();
            if (this.blockSize <= 0) {
                return;
            }
        } while (!OH());
    }
}
