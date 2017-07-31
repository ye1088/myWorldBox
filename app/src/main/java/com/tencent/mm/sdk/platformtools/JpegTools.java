package com.tencent.mm.sdk.platformtools;

import com.tencent.connect.common.Constants;
import org.apache.tools.tar.TarConstants;

public class JpegTools {
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final String TAG = "MicroMsg.JpegTools";
    private MBuf B;
    private int C;
    private boolean D;

    public JpegTools(byte[] bArr) {
        this.B = null;
        this.C = -1;
        this.D = true;
        this.B = new MBuf();
        this.B.setBuffer(bArr);
    }

    private void a(int i) {
        this.B.getBuffer().position(this.B.getBuffer().position() + i);
    }

    public static String byte2HexString(byte b) {
        String str = "";
        String toHexString = Integer.toHexString(b & 255);
        if (toHexString.length() == 1) {
            toHexString = "0" + toHexString;
        }
        return str + toHexString.toUpperCase();
    }

    public int getOreiValue() {
        if (this.C == -1) {
            return -1;
        }
        switch (this.C) {
            case 1:
                return 0;
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return -1;
        }
    }

    public int parserJpeg() {
        Object obj = null;
        try {
            Object obj2 = (byte2HexString(this.B.getBuffer().get()).equals("FF") && byte2HexString(this.B.getBuffer().get()).equals("D8")) ? 1 : null;
            if (obj2 == null) {
                Log.w(TAG, "this is not jpeg or no exif data!!!");
                return -1;
            }
            byte b;
            byte b2;
            byte b3;
            int i = 0;
            do {
                b = this.B.getBuffer().get();
                b2 = this.B.getBuffer().get();
                this.B.getBuffer().get();
                b3 = this.B.getBuffer().get();
                if (byte2HexString(b).equals("FF")) {
                    if (!byte2HexString(b).equals("FF") || !byte2HexString(b2).equals("E1")) {
                        if (byte2HexString(b).equals("FF") && byte2HexString(b2).equals("D9")) {
                            i = -1;
                            break;
                        }
                        this.B.getBuffer().position((this.B.getOffset() + b3) - 2);
                        i++;
                    } else {
                        i = (b3 & 255) - 2;
                        break;
                    }
                }
                i = -1;
                break;
            } while (i <= 100);
            Log.e(TAG, "error while!");
            i = -1;
            if (i < 0) {
                Log.w(TAG, "datalen is error ");
                return -1;
            }
            if ((new StringBuilder().append((char) this.B.getBuffer().get()).append((char) this.B.getBuffer().get()).append((char) this.B.getBuffer().get()).append((char) this.B.getBuffer().get()).toString().equals("Exif") ? 1 : null) == null) {
                Log.w(TAG, "checkExifTag is error");
                return -1;
            }
            a(2);
            byte b4 = this.B.getBuffer().get();
            b = this.B.getBuffer().get();
            String str = (((char) b4) == 'M' && ((char) b) == 'M') ? "MM" : (((char) b4) == 'I' && ((char) b) == 'I') ? "II" : "";
            if (str.equals("MM") || str.equals("II")) {
                this.D = str.equals("MM");
                boolean z = this.D;
                b = this.B.getBuffer().get();
                b2 = this.B.getBuffer().get();
                if (z && byte2HexString(b).equals(TarConstants.VERSION_POSIX) && byte2HexString(b2).equals("2A")) {
                    obj2 = 1;
                } else if (byte2HexString(b).equals("2A") && byte2HexString(b2).equals(TarConstants.VERSION_POSIX)) {
                    i = 1;
                } else {
                    Log.w(TAG, "checkTiffTag: " + byte2HexString(b) + " " + byte2HexString(b2));
                    obj2 = null;
                }
                if (obj2 == null) {
                    Log.w(TAG, "checkTiffTag  is error ");
                    return -1;
                }
                a(4);
                b4 = this.B.getBuffer().get();
                b = this.B.getBuffer().get();
                i = b4 & 255;
                if (this.D) {
                    i = b & 255;
                }
                int i2 = 0;
                while (i2 < i && i2 < 255) {
                    b2 = this.B.getBuffer().get();
                    b3 = this.B.getBuffer().get();
                    if (this.D && byte2HexString(b2).equals("01") && byte2HexString(b3).equals(Constants.VIA_REPORT_TYPE_SET_AVATAR)) {
                        obj = 1;
                    } else if (!this.D && byte2HexString(b2).equals(Constants.VIA_REPORT_TYPE_SET_AVATAR) && byte2HexString(b3).equals("01")) {
                        int i3 = 1;
                    }
                    a(2);
                    a(4);
                    if (obj != null) {
                        z = this.D;
                        byte b5 = this.B.getBuffer().get();
                        byte b6 = this.B.getBuffer().get();
                        a(2);
                        this.C = z ? b6 & 255 : b5 & 255;
                    } else {
                        a(4);
                        i2++;
                    }
                }
                Log.d(TAG, "orei " + this.C);
                return getOreiValue();
            }
            Log.w(TAG, "byteOrder  is error " + str);
            return -1;
        } catch (Exception e) {
            Log.e(TAG, "parser jpeg error");
            return -1;
        }
    }
}
