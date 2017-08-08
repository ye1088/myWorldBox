package com.MCWorld.framework.base.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

public class UtilsProc {
    public static String getAppNameByPid(Context context, int pid) {
        Collection<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (UtilsFunction.empty((Collection) runningAppProcesses)) {
            return "";
        }
        for (RunningAppProcessInfo processInfo : runningAppProcesses) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    public static String readProcessName() {
        IOException e;
        UnsupportedEncodingException e2;
        Throwable th;
        FileNotFoundException e3;
        BufferedReader cmdlineReader = null;
        try {
            BufferedReader cmdlineReader2 = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/cmdline"), "iso-8859-1"));
            try {
                StringBuilder processName = new StringBuilder();
                while (true) {
                    int c = cmdlineReader2.read();
                    if (c <= 0) {
                        break;
                    }
                    processName.append((char) c);
                }
                String stringBuilder = processName.toString();
                if (cmdlineReader2 == null) {
                    return stringBuilder;
                }
                try {
                    cmdlineReader2.close();
                    return stringBuilder;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return stringBuilder;
                }
            } catch (UnsupportedEncodingException e5) {
                e2 = e5;
                cmdlineReader = cmdlineReader2;
                try {
                    e2.printStackTrace();
                    if (cmdlineReader != null) {
                        try {
                            cmdlineReader.close();
                        } catch (IOException e42) {
                            e42.printStackTrace();
                        }
                    }
                    return "";
                } catch (Throwable th2) {
                    th = th2;
                    if (cmdlineReader != null) {
                        try {
                            cmdlineReader.close();
                        } catch (IOException e422) {
                            e422.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e6) {
                e3 = e6;
                cmdlineReader = cmdlineReader2;
                e3.printStackTrace();
                if (cmdlineReader != null) {
                    try {
                        cmdlineReader.close();
                    } catch (IOException e4222) {
                        e4222.printStackTrace();
                    }
                }
                return "";
            } catch (IOException e7) {
                e4222 = e7;
                cmdlineReader = cmdlineReader2;
                e4222.printStackTrace();
                if (cmdlineReader != null) {
                    try {
                        cmdlineReader.close();
                    } catch (IOException e42222) {
                        e42222.printStackTrace();
                    }
                }
                return "";
            } catch (Throwable th3) {
                th = th3;
                cmdlineReader = cmdlineReader2;
                if (cmdlineReader != null) {
                    cmdlineReader.close();
                }
                throw th;
            }
        } catch (UnsupportedEncodingException e8) {
            e2 = e8;
            e2.printStackTrace();
            if (cmdlineReader != null) {
                cmdlineReader.close();
            }
            return "";
        } catch (FileNotFoundException e9) {
            e3 = e9;
            e3.printStackTrace();
            if (cmdlineReader != null) {
                cmdlineReader.close();
            }
            return "";
        } catch (IOException e10) {
            e42222 = e10;
            e42222.printStackTrace();
            if (cmdlineReader != null) {
                cmdlineReader.close();
            }
            return "";
        }
    }

    public static boolean isMainProcess(Context context) {
        int myPid = Process.myPid();
        String mainProcessName = context.getPackageName();
        if (mainProcessName.equals(getAppNameByPid(context, myPid)) || mainProcessName.equals(readProcessName())) {
            return true;
        }
        return false;
    }
}
