package com.huluxia.framework.base.notification;

import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import com.huluxia.framework.AppConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Hashtable;
import java.util.Map;

public class EventNotifyCenter {
    private static Map<Message, Long> mFrequencyMessage = new Hashtable();
    private static final EventNotifier mNotifier = new EventNotifier();

    private static class Message implements Parcelable {
        public static final Creator<Message> CREATOR = new 1();
        Class clz;
        long interval;
        int message;

        public boolean equals(Object o) {
            boolean z = true;
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Message message1 = (Message) o;
            if (this.message != message1.message || this.interval != message1.interval) {
                return false;
            }
            if (this.clz != null) {
                z = this.clz.equals(message1.clz);
            } else if (message1.clz != null) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return ((((this.clz != null ? this.clz.hashCode() : 0) * 31) + this.message) * 31) + ((int) (this.interval ^ (this.interval >>> 32)));
        }

        public String toString() {
            return "Message{clz=" + this.clz + ", message=" + this.message + ", interval=" + this.interval + '}';
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSerializable(this.clz);
            dest.writeInt(this.message);
            dest.writeLong(this.interval);
        }

        protected Message(Parcel in) {
            this.clz = (Class) in.readSerializable();
            this.message = in.readInt();
            this.interval = in.readLong();
        }
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MessageHandler {
        int message();
    }

    public static void add(Class<?> callerCls, Object receiver) {
        ICallback callback = getCallback(receiver);
        if (callback != null) {
            mNotifier.add(callerCls, callback);
        }
    }

    private static ICallback getCallback(Object receiver) {
        if (receiver instanceof ICallback) {
            return (ICallback) receiver;
        }
        CallbackWrapper wrapper = new CallbackWrapper(receiver);
        if (wrapper.isValid()) {
            return wrapper;
        }
        return null;
    }

    public static void remove(Object receiver) {
        ICallback callback = getCallback(receiver);
        if (callback != null) {
            mNotifier.remove(callback);
        }
    }

    public static void notifyEvent(Object caller, int message, Object... params) {
        if (caller == null) {
            return;
        }
        if (caller instanceof Class) {
            mNotifier.notifyCallbacks(caller, message, params);
        } else {
            mNotifier.notifyCallbacks(caller.getClass(), message, params);
        }
    }

    public static void notifyEvenFrequency(Object caller, int message, long interval, Object... params) {
        if (caller != null) {
            Message msg = new Message();
            Class clz = caller instanceof Class ? (Class) caller : caller.getClass();
            msg.clz = clz;
            msg.message = message;
            msg.interval = interval;
            long current = SystemClock.elapsedRealtime();
            if (!mFrequencyMessage.keySet().contains(msg)) {
                mFrequencyMessage.put(msg, Long.valueOf(current));
                mNotifier.notifyCallbacks(clz, message, params);
            } else if (current - ((Long) mFrequencyMessage.get(msg)).longValue() > interval) {
                mFrequencyMessage.put(msg, Long.valueOf(current));
                mNotifier.notifyCallbacks(clz, message, params);
            }
        }
    }

    public static void notifyEventUiThreadFrequency(Object caller, int message, long interval, Object... params) {
        if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
            notifyEvenFrequency(caller, message, interval, params);
            return;
        }
        final Object obj = caller;
        final int i = message;
        final long j = interval;
        final Object[] objArr = params;
        AppConfig.getInstance().getUiHandler().post(new Runnable() {
            public void run() {
                EventNotifyCenter.notifyEvenFrequency(obj, i, j, objArr);
            }
        });
    }

    public static void notifyEventUiThread(final Object caller, final int message, final Object... params) {
        if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
            notifyEvent(caller, message, params);
        } else {
            AppConfig.getInstance().getUiHandler().post(new Runnable() {
                public void run() {
                    EventNotifyCenter.notifyEvent(caller, message, params);
                }
            });
        }
    }
}
