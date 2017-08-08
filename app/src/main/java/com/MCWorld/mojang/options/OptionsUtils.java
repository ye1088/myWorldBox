package com.MCWorld.mojang.options;

import android.os.Environment;
import android.util.Log;
import com.MCWorld.framework.base.log.HLog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class OptionsUtils {
    public static final String OPTIONS_FILE_PATH = "games/com.mojang/minecraftpe/options.txt";

    public static Options readVersion() {
        FileNotFoundException localFileNotFoundException;
        IOException localIOException;
        Throwable th;
        Options localOptions = new Options();
        BufferedReader localBufferedReader = null;
        try {
            File localFile = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/options.txt");
            if (localFile.exists()) {
                BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(localFile)));
                while (true) {
                    try {
                        String str = localBufferedReader2.readLine();
                        if (str == null) {
                            break;
                        }
                        try {
                            buildOptions(str, localOptions);
                        } catch (Exception e) {
                            HLog.error("Options", "build options error, str = " + str, e, new Object[0]);
                        }
                    } catch (FileNotFoundException e2) {
                        localFileNotFoundException = e2;
                        localBufferedReader = localBufferedReader2;
                    } catch (IOException e3) {
                        localIOException = e3;
                        localBufferedReader = localBufferedReader2;
                    } catch (Throwable th2) {
                        th = th2;
                        localBufferedReader = localBufferedReader2;
                    }
                }
                localBufferedReader = localBufferedReader2;
            }
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e4) {
                    Log.e("Options", "close bufferreader error", e4);
                }
            }
        } catch (FileNotFoundException e5) {
            localFileNotFoundException = e5;
            try {
                localFileNotFoundException.printStackTrace();
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e42) {
                        Log.e("Options", "close bufferreader error", e42);
                    }
                }
                return localOptions;
            } catch (Throwable th3) {
                th = th3;
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e422) {
                        Log.e("Options", "close bufferreader error", e422);
                    }
                }
                throw th;
            }
        } catch (IOException e6) {
            localIOException = e6;
            localIOException.printStackTrace();
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e4222) {
                    Log.e("Options", "close bufferreader error", e4222);
                }
            }
            return localOptions;
        }
        return localOptions;
    }

    public static Options getOptions() {
        Options options = readVersion();
        try {
            if (options.getOld_game_version_major().intValue() != 0) {
                return options;
            }
            if (options.getOld_game_version_minor().intValue() <= 10) {
                return getOptionsV010();
            }
            return getOptionsV0110();
        } catch (Exception e) {
            return getOptionsV0110();
        }
    }

    public static OptionsV0110 getOptionsV0110() {
        FileNotFoundException localFileNotFoundException;
        IOException localIOException;
        Throwable th;
        OptionsV0110 localOptions = new OptionsV0110();
        BufferedReader localBufferedReader = null;
        try {
            File localFile = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/options.txt");
            if (localFile.exists()) {
                BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(localFile)));
                while (true) {
                    try {
                        String str = localBufferedReader2.readLine();
                        if (str == null) {
                            break;
                        }
                        try {
                            buildOptions(str, localOptions);
                        } catch (Exception e) {
                            HLog.error("Options", "build options error, str = " + str, e, new Object[0]);
                        }
                    } catch (FileNotFoundException e2) {
                        localFileNotFoundException = e2;
                        localBufferedReader = localBufferedReader2;
                    } catch (IOException e3) {
                        localIOException = e3;
                        localBufferedReader = localBufferedReader2;
                    } catch (Throwable th2) {
                        th = th2;
                        localBufferedReader = localBufferedReader2;
                    }
                }
                localBufferedReader = localBufferedReader2;
            }
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e4) {
                    Log.e("Options", "close bufferreader error", e4);
                }
            }
        } catch (FileNotFoundException e5) {
            localFileNotFoundException = e5;
            try {
                localFileNotFoundException.printStackTrace();
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e42) {
                        Log.e("Options", "close bufferreader error", e42);
                    }
                }
                return localOptions;
            } catch (Throwable th3) {
                th = th3;
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e422) {
                        Log.e("Options", "close bufferreader error", e422);
                    }
                }
                throw th;
            }
        } catch (IOException e6) {
            localIOException = e6;
            localIOException.printStackTrace();
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e4222) {
                    Log.e("Options", "close bufferreader error", e4222);
                }
            }
            return localOptions;
        }
        return localOptions;
    }

    public static OptionsV010 getOptionsV010() {
        FileNotFoundException localFileNotFoundException;
        IOException localIOException;
        Throwable th;
        OptionsV010 localOptions = new OptionsV010();
        BufferedReader localBufferedReader = null;
        try {
            File localFile = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/options.txt");
            if (localFile.exists()) {
                BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(localFile)));
                while (true) {
                    try {
                        String str = localBufferedReader2.readLine();
                        if (str == null) {
                            break;
                        }
                        try {
                            buildOptions(str, localOptions);
                        } catch (Exception e) {
                            HLog.error("Options", "build options error, str = " + str, e, new Object[0]);
                        }
                    } catch (FileNotFoundException e2) {
                        localFileNotFoundException = e2;
                        localBufferedReader = localBufferedReader2;
                    } catch (IOException e3) {
                        localIOException = e3;
                        localBufferedReader = localBufferedReader2;
                    } catch (Throwable th2) {
                        th = th2;
                        localBufferedReader = localBufferedReader2;
                    }
                }
                localBufferedReader = localBufferedReader2;
            }
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e4) {
                    Log.e("Options", "close bufferreader error", e4);
                }
            }
        } catch (FileNotFoundException e5) {
            localFileNotFoundException = e5;
            try {
                localFileNotFoundException.printStackTrace();
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e42) {
                        Log.e("Options", "close bufferreader error", e42);
                    }
                }
                return localOptions;
            } catch (Throwable th3) {
                th = th3;
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e422) {
                        Log.e("Options", "close bufferreader error", e422);
                    }
                }
                throw th;
            }
        } catch (IOException e6) {
            localIOException = e6;
            localIOException.printStackTrace();
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e4222) {
                    Log.e("Options", "close bufferreader error", e4222);
                }
            }
            return localOptions;
        }
        return localOptions;
    }

    public static void buildOptions(String paramString, Options paramOptions) {
        if (paramString != null && paramOptions != null) {
            Field[] arrayOfField = paramOptions.getClass().getDeclaredFields();
            String[] arrayOfString = paramString.split(":");
            String str1 = arrayOfString[0];
            String str2 = "";
            if (arrayOfString.length >= 2) {
                str2 = arrayOfString[1];
            }
            for (Field field : arrayOfField) {
                if (str1.equals(field.getName())) {
                    Class clz1 = field.getType();
                    try {
                        Method method = paramOptions.getClass().getDeclaredMethod("set" + str1.substring(0, 1).toUpperCase() + str1.substring(1), new Class[]{clz1});
                        if (clz1.getSimpleName().equalsIgnoreCase("Integer")) {
                            method.invoke(paramOptions, new Object[]{Integer.valueOf(Integer.parseInt(str2))});
                        }
                        if (clz1.getSimpleName().equalsIgnoreCase("String")) {
                            method.invoke(paramOptions, new Object[]{str2});
                        }
                        if (clz1.getSimpleName().equalsIgnoreCase("Float")) {
                            method.invoke(paramOptions, new Object[]{Float.valueOf(Float.parseFloat(str2))});
                        }
                    } catch (Exception e) {
                        HLog.error("options", "build options errorr, field = " + field.getName(), new Object[0]);
                    }
                }
            }
        }
    }

    public static void writeOption(Options paramOptions) {
        if (paramOptions != null) {
            Map localMap = conventObjectToMap(paramOptions);
            try {
                BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/options.txt"))));
                for (Entry localEntry : localMap.entrySet()) {
                    String str = (String) localEntry.getKey();
                    Object localObject = localEntry.getValue();
                    if (localObject == null) {
                        Log.e("Options", "key = " + str + " is null");
                    } else {
                        localBufferedWriter.write(str + ":" + localObject.toString());
                        localBufferedWriter.newLine();
                    }
                }
                localBufferedWriter.flush();
                localBufferedWriter.close();
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
        }
    }

    public static Map conventObjectToMap(Options options) {
        HashMap localHashMap = new HashMap();
        Method[] arrayOfMethod = options.getClass().getDeclaredMethods();
        for (Method localMethod : arrayOfMethod) {
            String methodName = localMethod.getName();
            String fieldName = null;
            if (methodName.startsWith("get")) {
                String name = methodName.substring(3);
                fieldName = name.substring(0, 1).toLowerCase() + name.substring(1);
            }
            try {
                localHashMap.put(fieldName, localMethod.invoke(options, null));
            } catch (Exception e) {
                HLog.error("options", "conventObjectToMap erorr, method = " + methodName + ", fieldname = " + fieldName + ", error = " + e, new Object[0]);
            }
        }
        return localHashMap;
    }
}
