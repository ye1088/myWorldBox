package com.MCWorld.image.pipeline.decoder;

import com.MCWorld.framework.base.utils.Closeables;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Throwables;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.base.imagepipeline.memory.a;
import com.MCWorld.image.base.imagepipeline.memory.c;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ProgressiveJpegParser */
public class e {
    private static final int BUFFER_SIZE = 16384;
    private static final int GV = 0;
    private static final int GW = 1;
    private static final int GX = 2;
    private static final int GY = 3;
    private static final int GZ = 4;
    private static final int Ha = 5;
    private static final int Hb = 6;
    private int Hc = 0;
    private int Hd = 0;
    private int He = 0;
    private int Hf = 0;
    private int Hg = 0;
    private int Hh = 0;
    private final a xl;

    public e(a byteArrayPool) {
        this.xl = (a) Preconditions.checkNotNull(byteArrayPool);
    }

    public boolean f(d encodedImage) {
        if (this.Hc == 6) {
            return false;
        }
        if (encodedImage.getSize() <= this.He) {
            return false;
        }
        boolean z = (byte[]) this.xl.get(16384);
        InputStream bufferedDataStream = new c(encodedImage.getInputStream(), z, this.xl);
        try {
            com.MCWorld.image.core.common.util.d.a(bufferedDataStream, (long) this.He);
            z = w(bufferedDataStream);
            return z;
        } catch (IOException ioe) {
            Throwables.propagate(ioe);
            return false;
        } finally {
            Closeables.closeQuietly(bufferedDataStream);
        }
    }

    private boolean w(InputStream inputStream) {
        int oldBestScanNumber = this.Hg;
        while (this.Hc != 6) {
            int nextByte = inputStream.read();
            if (nextByte != -1) {
                this.He++;
                switch (this.Hc) {
                    case 0:
                        if (nextByte != 255) {
                            this.Hc = 6;
                            break;
                        }
                        try {
                            this.Hc = 1;
                            break;
                        } catch (IOException ioe) {
                            Throwables.propagate(ioe);
                            break;
                        }
                    case 1:
                        if (nextByte != 216) {
                            this.Hc = 6;
                            break;
                        }
                        this.Hc = 2;
                        break;
                    case 2:
                        if (nextByte == 255) {
                            this.Hc = 3;
                            break;
                        }
                        break;
                    case 3:
                        if (nextByte != 255) {
                            if (nextByte != 0) {
                                if (nextByte == 218 || nextByte == 217) {
                                    cq(this.He - 2);
                                }
                                if (!cp(nextByte)) {
                                    this.Hc = 2;
                                    break;
                                }
                                this.Hc = 4;
                                break;
                            }
                            this.Hc = 2;
                            break;
                        }
                        this.Hc = 3;
                        break;
                    case 4:
                        this.Hc = 5;
                        break;
                    case 5:
                        int bytesToSkip = ((this.Hd << 8) + nextByte) - 2;
                        com.MCWorld.image.core.common.util.d.a(inputStream, (long) bytesToSkip);
                        this.He += bytesToSkip;
                        this.Hc = 2;
                        break;
                    default:
                        Preconditions.checkState(false);
                        break;
                }
                this.Hd = nextByte;
            } else if (this.Hc != 6 || this.Hg == oldBestScanNumber) {
                return false;
            } else {
                return true;
            }
        }
        if (this.Hc != 6) {
        }
        return false;
    }

    private static boolean cp(int markerSecondByte) {
        boolean z = true;
        if (markerSecondByte == 1) {
            return false;
        }
        if (markerSecondByte >= 208 && markerSecondByte <= 215) {
            return false;
        }
        if (markerSecondByte == 217 || markerSecondByte == 216) {
            z = false;
        }
        return z;
    }

    private void cq(int offset) {
        if (this.Hf > 0) {
            this.Hh = offset;
        }
        int i = this.Hf;
        this.Hf = i + 1;
        this.Hg = i;
    }

    public boolean nF() {
        return this.He > 1 && this.Hc != 6;
    }

    public int nG() {
        return this.Hh;
    }

    public int nH() {
        return this.Hg;
    }
}
