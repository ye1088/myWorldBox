package com.baidu.frontia.api;

import android.net.Uri;
import com.baidu.android.pushservice.internal.BasicPushNotificationBuilder;
import com.baidu.android.pushservice.internal.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.internal.PushNotificationBuilder;
import com.baidu.frontia.module.push.FrontiaPushUtilImpl.AndroidContentImpl;
import com.baidu.frontia.module.push.FrontiaPushUtilImpl.DefaultNotificationStyleImpl;
import com.baidu.frontia.module.push.FrontiaPushUtilImpl.IOSContentImpl;
import com.baidu.frontia.module.push.FrontiaPushUtilImpl.MessageContentImpl;
import com.baidu.frontia.module.push.FrontiaPushUtilImpl.NotificationContentImpl;
import com.baidu.frontia.module.push.FrontiaPushUtilImpl.TriggerImpl;
import org.json.JSONObject;

public class FrontiaPushUtil {

    public static class AndroidContent {
        private AndroidContentImpl a;

        public AndroidContent() {
            this.a = new AndroidContentImpl();
        }

        AndroidContent(AndroidContentImpl androidContentImpl) {
            this.a = androidContentImpl;
        }

        AndroidContentImpl a() {
            return this.a;
        }

        public int getBuilderId() {
            return this.a.getBuilderId();
        }

        public DefaultNotificationStyle getNotificationStyle() {
            return new DefaultNotificationStyle(this.a.getNotificationStyle());
        }

        public String getPKGContent() {
            return this.a.getPKGContent();
        }

        public String getUrl() {
            return this.a.getUrl();
        }

        public void setBuilderId(int i) {
            this.a.setBuilderId(i);
        }

        public void setNotificationStyle(DefaultNotificationStyle defaultNotificationStyle) {
            this.a.setNotificationStyle(defaultNotificationStyle.a());
        }

        public void setPKGContent(String str) {
            this.a.setPKGContent(str);
        }

        public void setUrl(String str) {
            this.a.setUrl(str);
        }
    }

    public static class DefaultNotificationStyle {
        private DefaultNotificationStyleImpl a;

        public DefaultNotificationStyle() {
            this.a = new DefaultNotificationStyleImpl();
        }

        DefaultNotificationStyle(DefaultNotificationStyleImpl defaultNotificationStyleImpl) {
            this.a = defaultNotificationStyleImpl;
        }

        DefaultNotificationStyleImpl a() {
            return this.a;
        }

        public void disableAlert() {
            this.a.disableAlert();
        }

        public void disableDismissible() {
            this.a.disableDismissible();
        }

        public void disableVibration() {
            this.a.disableVibration();
        }

        public void enableAlert() {
            this.a.enableAlert();
        }

        public void enableDismissible() {
            this.a.enableDismissible();
        }

        public void enableVibration() {
            this.a.enableVibration();
        }

        public boolean isAlertEnabled() {
            return this.a.isAlertEnabled();
        }

        public boolean isDismissible() {
            return this.a.isDismissible();
        }

        public boolean isVibrationEnabled() {
            return this.a.isVibrationEnabled();
        }
    }

    public enum DeployStatus {
        DEVELOPE,
        PRODUCTION
    }

    static abstract class a {
        a() {
        }

        abstract PushNotificationBuilder a();

        public abstract void setNotificationDefaults(int i);

        public abstract void setNotificationFlags(int i);

        public abstract void setNotificationSound(Uri uri);

        public abstract void setNotificationText(String str);

        public abstract void setNotificationTitle(String str);

        public abstract void setNotificationVibrate(long[] jArr);

        public abstract void setStatusbarIcon(int i);
    }

    public static class FrontiaPushBasicNotificationBuilder extends a {
        private BasicPushNotificationBuilder a = new BasicPushNotificationBuilder();

        PushNotificationBuilder a() {
            return this.a;
        }

        public void setNotificationDefaults(int i) {
            this.a.setNotificationDefaults(i);
        }

        public void setNotificationFlags(int i) {
            this.a.setNotificationFlags(i);
        }

        public void setNotificationSound(Uri uri) {
            this.a.setNotificationSound(uri);
        }

        public void setNotificationText(String str) {
            this.a.setNotificationText(str);
        }

        public void setNotificationTitle(String str) {
            this.a.setNotificationTitle(str);
        }

        public void setNotificationVibrate(long[] jArr) {
            this.a.setNotificationVibrate(jArr);
        }

        public void setStatusbarIcon(int i) {
            this.a.setStatusbarIcon(i);
        }
    }

    public static class FrontiaPushCustomNotificationBuilder extends a {
        private CustomPushNotificationBuilder a = null;

        public FrontiaPushCustomNotificationBuilder(int i, int i2, int i3, int i4) {
            this.a = new CustomPushNotificationBuilder(i, i2, i3, i4);
        }

        PushNotificationBuilder a() {
            return this.a;
        }

        public void setNotificationDefaults(int i) {
            this.a.setNotificationDefaults(i);
        }

        public void setNotificationFlags(int i) {
            this.a.setNotificationFlags(i);
        }

        public void setNotificationSound(Uri uri) {
            this.a.setNotificationSound(uri);
        }

        public void setNotificationText(String str) {
            this.a.setNotificationText(str);
        }

        public void setNotificationTitle(String str) {
            this.a.setNotificationTitle(str);
        }

        public void setNotificationVibrate(long[] jArr) {
            this.a.setNotificationVibrate(jArr);
        }

        public void setStatusbarIcon(int i) {
            this.a.setStatusbarIcon(i);
        }
    }

    public static class IOSContent {
        private IOSContentImpl a;

        public IOSContent() {
            this.a = new IOSContentImpl();
        }

        IOSContent(IOSContentImpl iOSContentImpl) {
            this.a = iOSContentImpl;
        }

        IOSContentImpl a() {
            return this.a;
        }

        public String getAlertMsg() {
            return this.a.getAlertMsg();
        }

        public int getBadge() {
            return this.a.getBadge();
        }

        public String getSoundFile() {
            return this.a.getSoundFile();
        }

        public void setAlertMsg(String str) {
            this.a.setAlertMsg(str);
        }

        public void setBadge(int i) {
            this.a.setBadge(i);
        }

        public void setSoundFile(String str) {
            this.a.setSoundFile(str);
        }
    }

    public static class MessageContent {
        private MessageContentImpl a;
        private int b;

        MessageContent(MessageContentImpl messageContentImpl) {
            this.a = messageContentImpl;
        }

        public MessageContent(String str, DeployStatus deployStatus) {
            if (deployStatus == DeployStatus.DEVELOPE) {
                this.b = 1;
            } else {
                this.b = 2;
            }
            this.a = new MessageContentImpl(str, this.b);
        }

        public static MessageContent createNotificationMessage(String str, String str2, String str3) {
            return new MessageContent(MessageContentImpl.createNotificationMessage(str, str2, str3));
        }

        public static MessageContent createStringMessage(String str, String str2) {
            return new MessageContent(MessageContentImpl.createStringMessage(str, str2));
        }

        MessageContentImpl a() {
            return this.a;
        }

        public DeployStatus getDeployStatus() {
            return this.a.getDeployStatus() == 1 ? DeployStatus.DEVELOPE : DeployStatus.PRODUCTION;
        }

        public String getMessage() {
            return this.a.getMesssage();
        }

        public String getMessageKeys() {
            return this.a.getMessageKeys();
        }

        public NotificationContent getNotification() {
            return new NotificationContent(this.a.getNotificationContent());
        }

        public void setMessage(String str) {
            this.a.setMessage(str);
        }

        public void setNotification(NotificationContent notificationContent) {
            this.a.setNotification(notificationContent.a());
        }
    }

    public static class NotificationContent {
        private NotificationContentImpl a;

        NotificationContent(NotificationContentImpl notificationContentImpl) {
            this.a = notificationContentImpl;
        }

        public NotificationContent(String str, String str2) {
            this.a = new NotificationContentImpl(str, str2);
        }

        NotificationContentImpl a() {
            return this.a;
        }

        public void addAndroidContent(AndroidContent androidContent) {
            this.a.addAndroidContent(androidContent.a());
        }

        public void addCustomContent(String str, String str2) {
            this.a.addCustomContent(str, str2);
        }

        public void addIOSContent(IOSContent iOSContent) {
            this.a.addIOSContent(iOSContent.a());
        }

        public AndroidContent getAndroidContent() {
            return new AndroidContent(this.a.getAndroidContent());
        }

        public JSONObject getCustomContent() {
            return this.a.getCustomContent();
        }

        public String getDescription() {
            return this.a.getDescription();
        }

        public IOSContent getIOSContent() {
            return new IOSContent(this.a.getIOSContent());
        }

        public String getTitle() {
            return this.a.getTitle();
        }
    }

    public static class Trigger {
        private TriggerImpl a;

        public Trigger() {
            this.a = new TriggerImpl();
        }

        Trigger(TriggerImpl triggerImpl) {
            this.a = triggerImpl;
        }

        TriggerImpl a() {
            return this.a;
        }

        public String getCrontab() {
            return this.a.getCrontab();
        }

        public String getTime() {
            return this.a.getTime();
        }

        public void setCrontab(String str) {
            this.a.setCrontab(str);
        }

        public void setTime(String str) {
            this.a.setTime(str);
        }
    }
}
