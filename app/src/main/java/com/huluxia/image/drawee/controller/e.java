package com.huluxia.image.drawee.controller;

import android.graphics.drawable.Animatable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: ForwardingControllerListener */
public class e<INFO> implements c<INFO> {
    private static final String TAG = "FdingControllerListener";
    private final List<c<? super INFO>> mListeners = new ArrayList(2);

    public static <INFO> e<INFO> jJ() {
        return new e();
    }

    public static <INFO> e<INFO> d(c<? super INFO> listener) {
        e<INFO> forwarder = jJ();
        forwarder.e(listener);
        return forwarder;
    }

    public static <INFO> e<INFO> b(c<? super INFO> listener1, c<? super INFO> listener2) {
        e<INFO> forwarder = jJ();
        forwarder.e(listener1);
        forwarder.e(listener2);
        return forwarder;
    }

    public synchronized void e(c<? super INFO> listener) {
        this.mListeners.add(listener);
    }

    public synchronized void f(c<? super INFO> listener) {
        this.mListeners.remove(listener);
    }

    public synchronized void jK() {
        this.mListeners.clear();
    }

    private synchronized void e(String message, Throwable t) {
        Log.e(TAG, message, t);
    }

    public synchronized void i(String id, Object callerContext) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.mListeners.get(i)).i(id, callerContext);
            } catch (Exception exception) {
                e("InternalListener exception in onSubmit", exception);
            }
        }
    }

    public synchronized void a(String id, @Nullable INFO imageInfo, @Nullable Animatable animatable) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.mListeners.get(i)).a(id, imageInfo, animatable);
            } catch (Exception exception) {
                e("InternalListener exception in onFinalImageSet", exception);
            }
        }
    }

    public void j(String id, @Nullable INFO imageInfo) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.mListeners.get(i)).j(id, imageInfo);
            } catch (Exception exception) {
                e("InternalListener exception in onIntermediateImageSet", exception);
            }
        }
    }

    public void c(String id, Throwable throwable) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.mListeners.get(i)).c(id, throwable);
            } catch (Exception exception) {
                e("InternalListener exception in onIntermediateImageFailed", exception);
            }
        }
    }

    public synchronized void d(String id, Throwable throwable) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.mListeners.get(i)).d(id, throwable);
            } catch (Exception exception) {
                e("InternalListener exception in onFailure", exception);
            }
        }
    }

    public synchronized void bw(String id) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.mListeners.get(i)).bw(id);
            } catch (Exception exception) {
                e("InternalListener exception in onRelease", exception);
            }
        }
    }

    public void a(String id, float progress) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.mListeners.get(i)).a(id, progress);
            } catch (Exception exception) {
                e("InternalListener exception in onProgressUpdate", exception);
            }
        }
    }
}
