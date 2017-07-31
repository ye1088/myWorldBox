package com.huluxia.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.huluxia.HTApplication;
import com.huluxia.data.topic.SimpleTopicItem;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;

/* compiled from: UtilsReadingHistory */
public class ao {
    private static final int bmM = 300;
    private static ao bmN = new ao();

    private ao() {
    }

    public static ao Mx() {
        return bmN;
    }

    private static SharedPreferences My() {
        return HTApplication.getAppContext().getSharedPreferences("readinghistory", 0);
    }

    public static void k(String key, Object vaule) {
        IOException e;
        SharedPreferences sp = My();
        Editor preEd = null;
        if (sp != null) {
            preEd = sp.edit();
        }
        if (preEd != null) {
            if (vaule != null) {
                SimpleTopicItem info1 = (SimpleTopicItem) vaule;
                info1.activeTime = MA();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    ObjectOutputStream objectOutputStream;
                    try {
                        oos.writeObject(info1);
                        objectOutputStream = oos;
                    } catch (IOException e2) {
                        e = e2;
                        objectOutputStream = oos;
                        e.printStackTrace();
                        preEd.putString(key, new String(Base64.encodeBase64(baos.toByteArray())));
                        preEd.commit();
                    }
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                    preEd.putString(key, new String(Base64.encodeBase64(baos.toByteArray())));
                    preEd.commit();
                }
                preEd.putString(key, new String(Base64.encodeBase64(baos.toByteArray())));
            }
            preEd.commit();
        }
    }

    public static <T> List<T> by(long delPostId) {
        SharedPreferences sp = My();
        Set<Long> idDelSet = new HashSet();
        Map<String, ?> maps = sp.getAll();
        List<T> oblist = new ArrayList();
        Iterator it = maps.values().iterator();
        while (it.hasNext()) {
            try {
                T tempobj = new ObjectInputStream(new ByteArrayInputStream(Base64.decodeBase64(((String) it.next()).getBytes()))).readObject();
                SimpleTopicItem item = (SimpleTopicItem) tempobj;
                if (item.postID == delPostId) {
                    idDelSet.add(Long.valueOf(item.postID));
                    it.remove();
                } else {
                    oblist.add(tempobj);
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (ClassNotFoundException e3) {
                e3.printStackTrace();
            }
        }
        ArrayList<SimpleTopicItem> tempserviceInfo = new ArrayList();
        tempserviceInfo.addAll(oblist);
        Collections.sort(tempserviceInfo, new a());
        oblist.clear();
        oblist.addAll(tempserviceInfo);
        if (oblist.size() > 300) {
            while (300 < oblist.size()) {
                idDelSet.add(Long.valueOf(((SimpleTopicItem) oblist.get(300)).postID));
                oblist.remove(300);
            }
        }
        Editor preEd = null;
        if (sp != null) {
            preEd = sp.edit();
        }
        if (preEd != null) {
            for (Long longValue : idDelSet) {
                preEd.remove(String.valueOf(longValue.longValue()));
            }
            preEd.commit();
        }
        return oblist;
    }

    public void Mz() {
        SharedPreferences sp = My();
        Editor preEd = null;
        if (sp != null) {
            preEd = sp.edit();
        }
        if (preEd != null) {
            preEd.clear();
            preEd.commit();
        }
    }

    public static void ar(String per_name, String key) {
        SharedPreferences sp = My();
        Editor preEd = null;
        if (sp != null) {
            preEd = sp.edit();
        }
        preEd.remove(key);
        preEd.commit();
    }

    public static long MA() {
        return new Date().getTime();
    }

    public static void b(String per_name, String key, Object vaule) {
        ObjectOutputStream objectOutputStream;
        IOException e;
        SharedPreferences sp = My();
        Editor preEd = null;
        if (sp != null) {
            preEd = sp.edit();
        }
        if (preEd != null) {
            if (vaule != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    try {
                        oos.writeObject(vaule);
                        objectOutputStream = oos;
                    } catch (IOException e2) {
                        e = e2;
                        objectOutputStream = oos;
                        e.printStackTrace();
                        preEd.putString(key, new String(Base64.encodeBase64(baos.toByteArray())));
                        preEd.commit();
                    }
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                    preEd.putString(key, new String(Base64.encodeBase64(baos.toByteArray())));
                    preEd.commit();
                }
                preEd.putString(key, new String(Base64.encodeBase64(baos.toByteArray())));
            }
            preEd.commit();
        }
    }

    public static <T> List<T> gj(String per_name) {
        Map<String, ?> maps = My().getAll();
        List<T> oblist = new ArrayList();
        for (String base64Str : maps.values()) {
            try {
                oblist.add(new ObjectInputStream(new ByteArrayInputStream(Base64.decodeBase64(base64Str.getBytes()))).readObject());
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (ClassNotFoundException e3) {
                e3.printStackTrace();
            }
        }
        return oblist;
    }
}
