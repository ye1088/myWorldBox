package com.MCWorld.mcgame;

import android.os.Environment;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.util.EncodingUtils;

/* compiled from: DTServerManager */
public class ad {
    private static final boolean DEBUG = false;
    private static final String agq = "games/com.mojang/minecraftpe/external_servers.txt";
    private static boolean agr = false;

    public static boolean d(String name, String ip, String port) {
        return e(name, ip, port);
    }

    public static void yE() {
        yG();
    }

    public static boolean yF() {
        return agr;
    }

    public static void bH(boolean intoServerFlag) {
        agr = intoServerFlag;
    }

    public static void yG() {
        try {
            File localFile = new File(Environment.getExternalStorageDirectory(), agq);
            if (localFile.exists()) {
                u(localFile.getPath(), SpecilApiUtil.LINE_SEP);
            }
        } catch (Exception e) {
        }
    }

    private static boolean a(int inputIndex, List<ac> inputServerItemList, String inputServerRecordLine) {
        String[] sourceStrArray = inputServerRecordLine.split(":");
        if (sourceStrArray.length != 4) {
            return false;
        }
        for (int i = 0; i < sourceStrArray.length; i++) {
        }
        inputServerItemList.add(new ac(inputIndex, sourceStrArray[1], sourceStrArray[2], sourceStrArray[3]));
        return true;
    }

    private static boolean e(String name, String ip, String port) {
        try {
            File localFile = new File(Environment.getExternalStorageDirectory(), agq);
            if (!localFile.exists() && localFile.createNewFile()) {
                localFile.setReadable(true);
                localFile.setWritable(true);
            }
            String serverFilePath = localFile.getPath();
            List<ac> mServerItemList = new ArrayList(cT(serverFilePath));
            String fileContext = cS(serverFilePath);
            boolean bContainIPFlag = fileContext.contains(ip);
            boolean bContainPortFlag = fileContext.contains(port);
            StringBuffer sServerTextBuffer = new StringBuffer();
            int record_index_count = 1;
            if (bContainIPFlag && bContainPortFlag) {
                String[] sourceStrArray = fileContext.split(SpecilApiUtil.LINE_SEP);
                for (int i = 0; i < sourceStrArray.length; i++) {
                    boolean bContainIP = sourceStrArray[i].contains(ip);
                    boolean bContainPort = sourceStrArray[i].contains(port);
                    if (bContainIP && bContainPort) {
                        int hlx_record_server = i;
                    } else {
                        if (true == a(record_index_count, mServerItemList, sourceStrArray[i])) {
                            record_index_count++;
                        }
                    }
                }
            }
            sServerTextBuffer.append(SpecilApiUtil.LINE_SEP);
            for (ac _tmpServerItem : mServerItemList) {
                if (_tmpServerItem != null) {
                    sServerTextBuffer.append(String.format("%s:%s:%s:%s", new Object[]{Integer.valueOf(((ac) r17.next()).id), ((ac) r17.next()).name, ((ac) r17.next()).ago, ((ac) r17.next()).agp}));
                    sServerTextBuffer.append(SpecilApiUtil.LINE_SEP);
                }
            }
            sServerTextBuffer.append(String.format("%s:%s:%s:%s", new Object[]{Integer.valueOf(record_index_count), name, ip, port}));
            sServerTextBuffer.append(SpecilApiUtil.LINE_SEP);
            u(serverFilePath, sServerTextBuffer.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean f(String name, String ip, String port) {
        try {
            File localFile = new File(Environment.getExternalStorageDirectory(), agq);
            if (!localFile.exists() && localFile.createNewFile()) {
                localFile.setReadable(true);
                localFile.setWritable(true);
            }
            String serverFilePath = localFile.getPath();
            int serverIndex = cT(serverFilePath);
            String fileContext = cS(serverFilePath);
            boolean bContainFlag = fileContext.contains(ip);
            boolean bContainIPFlag = fileContext.contains(port);
            if (!bContainFlag) {
                fileContext = fileContext + SpecilApiUtil.LINE_SEP + (serverIndex + 1) + ":" + name + ":" + ip + ":" + port;
            } else if (bContainFlag && !bContainIPFlag) {
                fileContext = fileContext + SpecilApiUtil.LINE_SEP + (serverIndex + 1) + ":" + name + ":" + ip + ":" + port;
            }
            u(serverFilePath, fileContext);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void u(String fileName, String write_str) throws IOException {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            fout.write(write_str.getBytes());
            fout.flush();
            fout.flush();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String cS(String fileName) throws IOException {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
    }

    private static int cT(String filename) {
        Exception ex;
        Throwable th;
        int cnt = 0;
        InputStream is = null;
        try {
            InputStream is2 = new BufferedInputStream(new FileInputStream(filename));
            try {
                byte[] c = new byte[1024];
                while (true) {
                    int readChars = is2.read(c);
                    if (readChars != -1) {
                        for (int i = 0; i < readChars; i++) {
                            if (c[i] == (byte) 10) {
                                cnt++;
                            }
                        }
                    } else {
                        try {
                            break;
                        } catch (Exception ex2) {
                            ex2.printStackTrace();
                            is = is2;
                        }
                    }
                }
                is2.close();
                is = is2;
            } catch (Exception e) {
                ex2 = e;
                is = is2;
                cnt = -1;
                try {
                    ex2.printStackTrace();
                    try {
                        is.close();
                    } catch (Exception ex22) {
                        ex22.printStackTrace();
                    }
                    return cnt;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        is.close();
                    } catch (Exception ex222) {
                        ex222.printStackTrace();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                is = is2;
                is.close();
                throw th;
            }
        } catch (Exception e2) {
            ex222 = e2;
            cnt = -1;
            ex222.printStackTrace();
            is.close();
            return cnt;
        }
        return cnt;
    }
}
