package com.mojang.minecraftpe;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"DefaultLocale"})
public class HardwareInformation {
    private static String cpuFeatures = "unknown";
    private static a cpuInfo;
    private static String cpuName = "unknown";
    private static int numCores = 1;

    public static class a {
        private final Map<String, String> bIf;
        private final int bIg;

        public a(Map<String, String> paramMap, int paramInt) {
            this.bIf = paramMap;
            this.bIg = paramInt;
        }

        String gX(String paramString) {
            if (this.bIf.containsKey(paramString)) {
                return (String) this.bIf.get(paramString);
            }
            return "";
        }

        int Rn() {
            return this.bIg;
        }
    }

    static {
        initHardwareInformation();
    }

    public static String getAndroidVersion() {
        return "Android " + VERSION.RELEASE;
    }

    public static String getCPUFeatures() {
        return cpuFeatures;
    }

    public static String getCPUName() {
        return cpuName;
    }

    public static String getCPUType() {
        return Build.CPU_ABI;
    }

    public static String getDeviceModelName() {
        return Build.MANUFACTURER.toUpperCase() + " " + Build.MODEL;
    }

    public static int getNumCores() {
        return numCores;
    }

    private static int getNumCoresReal() {
        return Math.max(1, Runtime.getRuntime().availableProcessors());
    }

    public static void initHardwareInformation() {
        try {
            cpuInfo = getCPUInfo();
            numCores = getNumCoresReal();
            parseCpuInfo();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private static void parseCpuInfo() throws IOException {
        cpuName = cpuInfo.gX("Hardware");
        cpuFeatures = cpuInfo.gX("Features");
    }

    public static a getCPUInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        int i = 0;
        if (new File("/proc/cpuinfo").exists()) {
            try {
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
        BufferedReader localBufferedReader = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
        Pattern localPattern = Pattern.compile("(\\w*)\\s*:\\s([^\\n]*)");
        while (true) {
            String str = localBufferedReader.readLine();
            if (str == null) {
                break;
            }
            Matcher localMatcher = localPattern.matcher(str);
            if (localMatcher.find() && localMatcher.groupCount() == 2) {
                if (!localHashMap.containsKey(localMatcher.group(1))) {
                    localHashMap.put(localMatcher.group(1), localMatcher.group(2));
                }
                if (localMatcher.group(1).contentEquals("processor")) {
                    i++;
                }
            }
        }
        if (localBufferedReader != null) {
            localBufferedReader.close();
        }
        return new a(localHashMap, i);
    }
}
