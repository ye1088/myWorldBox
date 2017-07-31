package com.huluxia.image.base.imageutils;

import android.support.v4.view.MotionEventCompat;
import android.util.Pair;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

/* compiled from: WebpUtil */
public class e {
    private static final String xN = "VP8 ";
    private static final String xO = "VP8L";
    private static final String xP = "VP8X";

    private e() {
    }

    @Nullable
    public static Pair<Integer, Integer> m(InputStream is) {
        Pair<Integer, Integer> pair = null;
        byte[] headerBuffer = new byte[4];
        try {
            is.read(headerBuffer);
            if (c(headerBuffer, "RIFF")) {
                q(is);
                is.read(headerBuffer);
                if (c(headerBuffer, "WEBP")) {
                    is.read(headerBuffer);
                    String headerAsString = m(headerBuffer);
                    if (xN.equals(headerAsString)) {
                        pair = n(is);
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (xO.equals(headerAsString)) {
                        pair = o(is);
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    } else if (xP.equals(headerAsString)) {
                        pair = p(is);
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                    } else if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                } else if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e2222) {
                        e2222.printStackTrace();
                    }
                }
            } else if (is != null) {
                try {
                    is.close();
                } catch (IOException e22222) {
                    e22222.printStackTrace();
                }
            }
        } catch (IOException e222222) {
            e222222.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2222222) {
                    e2222222.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e22222222) {
                    e22222222.printStackTrace();
                }
            }
        }
        return pair;
    }

    private static Pair<Integer, Integer> n(InputStream is) throws IOException {
        is.skip(7);
        short sign1 = t(is);
        short sign2 = t(is);
        short sign3 = t(is);
        if (sign1 == (short) 157 && sign2 == (short) 1 && sign3 == (short) 42) {
            return new Pair(Integer.valueOf(r(is)), Integer.valueOf(r(is)));
        }
        return null;
    }

    private static Pair<Integer, Integer> o(InputStream is) throws IOException {
        q(is);
        if (u(is) != (byte) 47) {
            return null;
        }
        int data2 = ((byte) is.read()) & 255;
        int data4 = ((byte) is.read()) & 255;
        return new Pair(Integer.valueOf((((data2 & 63) << 8) | (((byte) is.read()) & 255)) + 1), Integer.valueOf(((((data4 & 15) << 10) | ((((byte) is.read()) & 255) << 2)) | ((data2 & 192) >> 6)) + 1));
    }

    private static Pair<Integer, Integer> p(InputStream is) throws IOException {
        is.skip(8);
        return new Pair(Integer.valueOf(s(is) + 1), Integer.valueOf(s(is) + 1));
    }

    private static boolean c(byte[] what, String with) {
        if (what.length != with.length()) {
            return false;
        }
        for (int i = 0; i < what.length; i++) {
            if (with.charAt(i) != what[i]) {
                return false;
            }
        }
        return true;
    }

    private static String m(byte[] header) {
        StringBuilder str = new StringBuilder();
        for (byte b : header) {
            str.append((char) b);
        }
        return str.toString();
    }

    private static int q(InputStream is) throws IOException {
        return ((((((byte) is.read()) << 24) & -16777216) | ((((byte) is.read()) << 16) & 16711680)) | ((((byte) is.read()) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((byte) is.read()) & 255);
    }

    public static int r(InputStream is) throws IOException {
        return ((((byte) is.read()) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (((byte) is.read()) & 255);
    }

    private static int s(InputStream is) throws IOException {
        byte byte1 = u(is);
        return (((u(is) << 16) & 16711680) | ((u(is) << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (byte1 & 255);
    }

    private static short t(InputStream is) throws IOException {
        return (short) (is.read() & 255);
    }

    private static byte u(InputStream is) throws IOException {
        return (byte) (is.read() & 255);
    }

    private static boolean c(byte input, int bitIndex) {
        return ((input >> (bitIndex % 8)) & 1) == 1;
    }
}
