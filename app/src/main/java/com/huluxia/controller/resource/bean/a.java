package com.huluxia.controller.resource.bean;

/* compiled from: ResTaskHelper */
public class a {
    private static String mJ;
    private static int mK;

    public static ResTaskInfo dM() {
        ResTaskInfo taskInfo = new ResTaskInfo();
        taskInfo.mT = mJ;
        taskInfo.mK = mK;
        return taskInfo;
    }

    public static <T extends ResTaskInfo> T b(Class<T> c) {
        T info = null;
        try {
            info = (ResTaskInfo) c.newInstance();
            info.mT = mJ;
            info.mK = mK;
            return info;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return info;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return info;
        }
    }

    public static void ai(String pendingIntentclass) {
        mJ = pendingIntentclass;
    }

    public static void F(int notificationIcon) {
        mK = notificationIcon;
    }
}
