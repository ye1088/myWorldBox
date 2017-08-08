package com.MCWorld.widget.gif;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import com.MCWorld.http.base.e;
import com.MCWorld.utils.UtilsFile;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: GifManager */
public class d {
    private static Map<String, SoftReference<b>> bzA = Collections.synchronizedMap(new HashMap());
    private static List<String> bzB = Collections.synchronizedList(new ArrayList());
    private static List<WeakReference<GifView>> bzC = Collections.synchronizedList(new LinkedList());
    private static d bzD = null;
    private static final Handler bzE = new Handler() {
        public void handleMessage(Message msg) {
            Iterator<WeakReference<GifView>> it = d.bzC.iterator();
            while (it.hasNext()) {
                GifView key = (GifView) ((WeakReference) it.next()).get();
                if (key != null) {
                    key.OW();
                } else {
                    it.remove();
                }
            }
        }
    };
    private b bzF = null;

    /* compiled from: GifManager */
    private class a extends AsyncTask<String, Integer, String> {
        final /* synthetic */ d bzG;

        private a(d dVar) {
            this.bzG = dVar;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
        }

        protected String c(String... params) {
            String url = params[0];
            String filePath = UtilsFile.eW(url);
            try {
                byte[] data = UtilsFile.getBytesFromSD(filePath);
                if (data == null) {
                    Log.i("download", url);
                    data = e.a(url, null);
                    UtilsFile.saveBytesToSD(filePath, data);
                }
                b decoder = new b();
                if (decoder.read(data) == -1) {
                    d.bzA.put(url, new SoftReference(decoder));
                } else {
                    UtilsFile.deleteFile(filePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            d.bzB.remove(url);
            return url;
        }

        protected void ce(String result) {
            if (d.bzA.containsKey(result)) {
                this.bzG.gP(result);
            }
        }
    }

    /* compiled from: GifManager */
    private class b extends Thread {
        final /* synthetic */ d bzG;

        private b(d dVar) {
            this.bzG = dVar;
        }

        public void run() {
            Log.i("gif thread", "start");
            while (!Thread.currentThread().isInterrupted()) {
                if (d.bzC.isEmpty()) {
                    SystemClock.sleep(100);
                } else {
                    d.bzE.sendMessage(d.bzE.obtainMessage());
                    SystemClock.sleep(100);
                }
            }
            Log.i("gif thread", "stop");
        }
    }

    public static d OS() {
        if (bzD == null) {
            bzD = new d();
        }
        return bzD;
    }

    private d() {
    }

    public void start() {
        if (this.bzF == null) {
            this.bzF = new b();
            this.bzF.start();
        }
    }

    public void stop() {
        bzB.clear();
        bzC.clear();
        for (SoftReference<b> item : bzA.values()) {
            b decoder = null;
            if (item != null) {
                decoder = (b) item.get();
            }
            if (decoder != null) {
                decoder.free();
            }
        }
        bzA.clear();
        if (this.bzF != null) {
            this.bzF.interrupt();
            this.bzF = null;
        }
        System.gc();
    }

    private void a(GifView view) {
        boolean isExist = false;
        for (WeakReference<GifView> it : bzC) {
            if (it.get() == view) {
                isExist = true;
            }
        }
        if (!isExist) {
            bzC.add(new WeakReference(view));
        }
    }

    public void a(String url, GifView view) {
        view.setExtendTag(url);
        a(view);
        if (bzA.containsKey(url)) {
            b gifDecoder = (b) ((SoftReference) bzA.get(url)).get();
            if (gifDecoder != null) {
                view.setGifDecoder(gifDecoder);
                view.setExtendTag("");
                return;
            }
        }
        if (!bzB.contains(url)) {
            bzB.add(url);
            new a().execute(new String[]{url});
        }
    }

    private void gP(String url) {
        for (WeakReference weakReference : bzC) {
            GifView key = (GifView) weakReference.get();
            if (key != null && key.getExtendTag().equals(url)) {
                if (bzA.containsKey(url)) {
                    key.setGifDecoder((b) ((SoftReference) bzA.get(url)).get());
                }
                key.setExtendTag("");
            }
        }
    }
}
