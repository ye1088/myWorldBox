package com.tencent.mm.sdk.platformtools;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FilesCopy {
    private FilesCopy() {
    }

    public static boolean copy(String str, String str2, boolean z) {
        int i = 0;
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        File file2 = new File(str2);
        if (file.isFile()) {
            if (!file2.isFile() && file2.exists()) {
                return false;
            }
            copyFile(str, str2);
            if (z) {
                file.delete();
            }
        } else if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdir();
            }
            if (!file2.isDirectory()) {
                return false;
            }
            String[] list = file.list();
            while (i < list.length) {
                copy(str + "/" + list[i], str2 + "/" + list[i], z);
                i++;
            }
        }
        return true;
    }

    public static boolean copyAssets(Context context, String str, String str2) {
        FileOutputStream fileOutputStream;
        Exception e;
        Throwable th;
        try {
            InputStream open = context.getAssets().open(str);
            fileOutputStream = new FileOutputStream(str2);
            try {
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = open.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                File file = new File(str2);
                open.close();
                boolean z = file.exists() && ((long) context.getAssets().open(str).available()) == file.length();
                try {
                    fileOutputStream.close();
                    return z;
                } catch (IOException e2) {
                    Log.e("FilesCopy", null, e2);
                    return false;
                }
            } catch (Exception e3) {
                e = e3;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        return false;
                    }
                    try {
                        fileOutputStream.close();
                        return false;
                    } catch (IOException e22) {
                        Log.e("FilesCopy", null, e22);
                        return false;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            Log.e("FilesCopy", null, e4);
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e5) {
            e = e5;
            fileOutputStream = null;
            e.printStackTrace();
            if (fileOutputStream != null) {
                return false;
            }
            fileOutputStream.close();
            return false;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    public static boolean copyFile(String str, String str2) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        Exception e;
        Throwable th;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream = new FileOutputStream(str2);
                try {
                    byte[] bArr = new byte[16384];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    boolean z = true;
                    try {
                        fileInputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        z = false;
                    }
                    try {
                        fileOutputStream.close();
                        return z;
                    } catch (IOException e22) {
                        e22.printStackTrace();
                        return false;
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileInputStream2 = fileInputStream;
                    try {
                        e.printStackTrace();
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            return false;
                        }
                        try {
                            fileOutputStream.close();
                            return false;
                        } catch (IOException e2222) {
                            e2222.printStackTrace();
                            return false;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e42) {
                                e42.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                fileOutputStream = null;
                fileInputStream2 = fileInputStream;
                e.printStackTrace();
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                if (fileOutputStream != null) {
                    return false;
                }
                fileOutputStream.close();
                return false;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            fileOutputStream = null;
            e.printStackTrace();
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (fileOutputStream != null) {
                return false;
            }
            fileOutputStream.close();
            return false;
        } catch (Throwable th5) {
            th = th5;
            fileOutputStream = null;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }
}
