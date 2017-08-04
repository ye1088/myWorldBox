package com.huluxia.service;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.m;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.t;
import com.huluxia.utils.au;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.util.ArrayList;
import org.json.JSONObject;

/* compiled from: QQshareService */
public class j {
    private static j aDB;
    private static int aDC = 1;
    private static long aDD = 0;
    private static Activity mActivity;
    private final String APP_ID = "100580922";
    private Tencent aDA = Tencent.createInstance("100580922", HTApplication.getAppContext());
    private Handler mHandler;

    private j(Context context) {
        HandlerThread handlerThread = new HandlerThread("qqshare-" + SystemClock.elapsedRealtime());
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    public static j m(Activity activity) {
        mActivity = activity;
        if (aDB == null) {
            aDB = new j(HTApplication.getAppContext());
        }
        return aDB;
    }

    public void a(TopicItem data, String tipUrl) {
        Bundle params = new Bundle();
        params.putInt("req_type", aDC);
        params.putString("title", data.getTitle());
        params.putString("summary", data.getDetail());
        params.putString("targetUrl", tipUrl);
        if (data.getImages().size() > 0) {
            ArrayList<String> imageUrls = new ArrayList();
            imageUrls.addAll(data.getImages());
            params.putStringArrayList("imageUrl", imageUrls);
        }
        a(0, params);
    }

    public void a(long gameId, String title, String desc, String icon, String tipUrl) {
        Bundle params = new Bundle();
        params.putInt("req_type", aDC);
        params.putString("title", title);
        params.putString("summary", desc);
        params.putString("targetUrl", tipUrl);
        ArrayList<String> imageUrls = new ArrayList();
        imageUrls.add(icon);
        params.putStringArrayList("imageUrl", imageUrls);
        a(gameId, params);
    }

    public void b(TopicItem data, String tipUrl) {
        Bundle params = new Bundle();
        params.putInt("req_type", 1);
        params.putString("title", data.getTitle());
        params.putString("summary", data.getDetail());
        params.putString("targetUrl", tipUrl);
        params.putString("appName", mActivity.getResources().getString(m.app_name));
        if (data.getImages().size() > 0) {
            params.putString("imageUrl", (String) data.getImages().get(0));
        }
        b(params);
    }

    public void e(String title, String desc, String icon, String tipUrl) {
        Bundle params = new Bundle();
        params.putInt("req_type", 1);
        params.putString("title", title);
        params.putString("summary", desc);
        params.putString("targetUrl", tipUrl);
        params.putString("appName", mActivity.getResources().getString(m.app_name));
        params.putString("imageUrl", icon);
        b(params);
    }

    private void a(final long gameId, final Bundle params) {
        this.mHandler.post(new Runnable(this) {
            final /* synthetic */ j aDG;

            public void run() {
                this.aDG.aDA.shareToQzone(j.mActivity, params, new IUiListener(this) {
                    final /* synthetic */ AnonymousClass1 aDH;

                    {
                        this.aDH = this$1;
                    }

                    public void onCancel() {
                    }

                    public void onComplete(Object response) {
                        if (response == null) {
                            j.mActivity.runOnUiThread(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 aDI;

                                {
                                    this.aDI = this$2;
                                }

                                public void run() {
                                    t.show_toast(HTApplication.getAppContext(), "分享失败");
                                }
                            });
                            return;
                        }
                        JSONObject jsonResponse = (JSONObject) response;
                        if (jsonResponse == null || jsonResponse.length() != 0) {
                            j.mActivity.runOnUiThread(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 aDI;

                                {
                                    this.aDI = this$2;
                                }

                                public void run() {
                                    t.o(HTApplication.getAppContext(), "成功分享到QQ空间");
                                }
                            });
                            if (gameId != 0) {
                                au.e(gameId, true);
                                return;
                            }
                            return;
                        }
                        j.mActivity.runOnUiThread(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 aDI;

                            {
                                this.aDI = this$2;
                            }

                            public void run() {
                                t.show_toast(HTApplication.getAppContext(), "分享失败");
                            }
                        });
                    }

                    public void onError(UiError e) {
                        j.mActivity.runOnUiThread(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 aDI;

                            {
                                this.aDI = this$2;
                            }

                            public void run() {
                                t.show_toast(HTApplication.getAppContext(), "无法分享到QQ空间");
                            }
                        });
                    }
                });
            }
        });
    }

    private void b(final Bundle params) {
        this.mHandler.post(new Runnable(this) {
            final /* synthetic */ j aDG;

            public void run() {
                this.aDG.aDA.shareToQQ(j.mActivity, params, new IUiListener(this) {
                    final /* synthetic */ AnonymousClass2 aDJ;

                    {
                        this.aDJ = this$1;
                    }

                    public void onCancel() {
                    }

                    public void onComplete(Object response) {
                        if (response == null) {
                            j.mActivity.runOnUiThread(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 aDK;

                                {
                                    this.aDK = this$2;
                                }

                                public void run() {
                                    t.show_toast(HTApplication.getAppContext(), "分享失败");
                                }
                            });
                            return;
                        }
                        JSONObject jsonResponse = (JSONObject) response;
                        if (jsonResponse == null || jsonResponse.length() != 0) {
                            j.mActivity.runOnUiThread(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 aDK;

                                {
                                    this.aDK = this$2;
                                }

                                public void run() {
                                    t.o(HTApplication.getAppContext(), "成功分享给QQ好友");
                                }
                            });
                        } else {
                            j.mActivity.runOnUiThread(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 aDK;

                                {
                                    this.aDK = this$2;
                                }

                                public void run() {
                                    t.show_toast(HTApplication.getAppContext(), "分享失败");
                                }
                            });
                        }
                    }

                    public void onError(UiError e) {
                        j.mActivity.runOnUiThread(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 aDK;

                            {
                                this.aDK = this$2;
                            }

                            public void run() {
                                t.show_toast(HTApplication.getAppContext(), "无法分享给QQ好友");
                            }
                        });
                    }
                });
            }
        });
    }
}
