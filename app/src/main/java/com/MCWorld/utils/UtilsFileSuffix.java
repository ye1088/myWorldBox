package com.MCWorld.utils;

import android.content.Context;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.video.recorder.a;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class UtilsFileSuffix {
    public static String[] blQ = new String[]{"", ".hpk", ".apk", ".iso", ".cso", ".rmvb", a.boV};
    public static final HashMap<String, FileSuffix> blR = new HashMap();

    public enum FileSuffix {
        other,
        hpk,
        apk,
        iso,
        cso,
        rmvb,
        mp4
    }

    static {
        blR.put("4D5A9000", FileSuffix.hpk);
        blR.put("504B0304", FileSuffix.apk);
        blR.put("2E524D46", FileSuffix.rmvb);
        blR.put("000001BA", FileSuffix.mp4);
        blR.put("00000018", FileSuffix.mp4);
        blR.put("4349534F", FileSuffix.cso);
        blR.put("4344303031", FileSuffix.iso);
    }

    public static FileSuffix L(Context ctx, String filePath) {
        FileSuffix suffix = (FileSuffix) blR.get(M(ctx, filePath));
        if (suffix == null) {
            suffix = (FileSuffix) blR.get(fT(filePath));
        }
        HLog.verbose("", "getFileType suffix = " + suffix, new Object[0]);
        if (suffix == null) {
            return FileSuffix.other;
        }
        return suffix;
    }

    public static String M(Context ctx, String filePath) {
        Exception e;
        Throwable th;
        FileInputStream is = null;
        String value = null;
        try {
            FileInputStream is2 = new FileInputStream(filePath);
            try {
                byte[] b = new byte[4];
                is2.read(b, 0, b.length);
                value = bytesToHexString(b);
                if (is2 != null) {
                    try {
                        is2.close();
                        is = is2;
                    } catch (IOException e2) {
                        is = is2;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                is = is2;
                try {
                    e.printStackTrace();
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e4) {
                        }
                    }
                    return value;
                } catch (Throwable th2) {
                    th = th2;
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e5) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                is = is2;
                if (is != null) {
                    is.close();
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            e.printStackTrace();
            if (is != null) {
                is.close();
            }
            return value;
        }
        return value;
    }

    public static String fT(String filePath) {
        Exception e;
        FileInputStream is = null;
        String value = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream is2 = new FileInputStream(file);
                try {
                    is2.skip(32769);
                    byte[] b = new byte[5];
                    is2.read(b, 0, b.length);
                    value = bytesToHexString(b);
                    if (!value.equals("4344303031")) {
                        is2.skip(2043);
                        b = new byte[5];
                        is2.read(b, 0, b.length);
                        value = bytesToHexString(b);
                        if (!value.equals("4344303031")) {
                            is2.skip(2043);
                            b = new byte[5];
                            is2.read(b, 0, b.length);
                            value = bytesToHexString(b);
                            if (is2 != null) {
                                try {
                                    is2.close();
                                    is = is2;
                                } catch (IOException e2) {
                                    is = is2;
                                }
                            }
                        } else if (is2 != null) {
                            try {
                                is2.close();
                                is = is2;
                            } catch (IOException e3) {
                                is = is2;
                            }
                        }
                    } else if (is2 != null) {
                        try {
                            is2.close();
                            is = is2;
                        } catch (IOException e4) {
                            is = is2;
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                    is = is2;
                    try {
                        HLog.error("", "getIsoFileHeader with path = " + filePath, e, new Object[0]);
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e6) {
                            }
                        }
                    } catch (Throwable th) {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e7) {
                            }
                        }
                        return value;
                    }
                    return value;
                } catch (Throwable th2) {
                    is = is2;
                    if (is != null) {
                        is.close();
                    }
                    return value;
                }
            }
            HLog.error("", "getIsoFileHeader with path = " + filePath + ", but file not found", new Object[0]);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e8) {
                }
            }
        } catch (Exception e9) {
            e = e9;
            HLog.error("", "getIsoFileHeader with path = " + filePath, e, new Object[0]);
            if (is != null) {
                is.close();
            }
            return value;
        }
        return value;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 255).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
}
