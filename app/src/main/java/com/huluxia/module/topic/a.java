package com.huluxia.module.topic;

import com.huluxia.data.TableList;
import com.huluxia.data.TableListParc;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.module.ab;
import com.huluxia.module.n;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: BbsModule */
public class a {
    private static a aCz;

    public static synchronized a Ed() {
        a aVar;
        synchronized (a.class) {
            if (aCz == null) {
                aCz = new a();
            }
            aVar = aCz;
        }
        return aVar;
    }

    private a() {
    }

    public void Ee() {
        HttpMgr.getInstance().performStringRequest(ab.ayB, new HashMap(), new Listener<String>(this) {
            final /* synthetic */ a aCA;

            {
                this.aCA = this$0;
            }

            public void onResponse(String response) {
                try {
                    TableListParc data = TableListParc.parseJsonResponse(response);
                    EventNotifyCenter.notifyEvent(n.class, 293, new Object[]{Boolean.valueOf(true), data});
                } catch (Exception e) {
                    EventNotifyCenter.notifyEvent(n.class, 293, new Object[]{Boolean.valueOf(false), null});
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ a aCA;

            {
                this.aCA = this$0;
            }

            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEvent(n.class, 293, new Object[]{Boolean.valueOf(false), null});
            }
        });
    }

    public void a(long cateId, long tagId, long start, int count, int sortBy) {
        Map<String, String> param = new HashMap();
        param.put("cat_id", String.valueOf(cateId));
        param.put("tag_id", String.valueOf(tagId));
        param.put("start", String.valueOf(start));
        param.put("count", String.valueOf(count));
        param.put("sort_by", String.valueOf(sortBy));
        HttpMgr instance = HttpMgr.getInstance();
        String str = ab.azQ;
        final long j = start;
        final long j2 = tagId;
        final int i = sortBy;
        Listener anonymousClass3 = new Listener<String>(this) {
            final /* synthetic */ a aCA;

            public void onResponse(String response) {
                try {
                    TableList tableList = TableList.parseCateTopicListResponse(response);
                    EventNotifyCenter.notifyEvent(n.class, 296, new Object[]{Boolean.valueOf(true), tableList, Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)});
                } catch (Exception e) {
                    EventNotifyCenter.notifyEvent(n.class, 296, new Object[]{Boolean.valueOf(false), null, Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)});
                }
            }
        };
        j2 = start;
        final long j3 = tagId;
        final int i2 = sortBy;
        instance.performStringRequest(str, param, anonymousClass3, new ErrorListener(this) {
            final /* synthetic */ a aCA;

            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEvent(n.class, 296, new Object[]{Boolean.valueOf(false), null, Long.valueOf(j2), Long.valueOf(j3), Integer.valueOf(i2)});
            }
        });
    }

    public void e(long userId, long categoryId) {
        Map<String, String> param = new HashMap();
        param.put("user_id", String.valueOf(userId));
        param.put("cat_id", String.valueOf(categoryId));
        HttpMgr.getInstance().performStringRequest(ab.aze, param, new Listener<String>(this) {
            final /* synthetic */ a aCA;

            {
                this.aCA = this$0;
            }

            public void onResponse(String response) {
                try {
                    boolean z;
                    int signIn = ((JSONObject) new JSONTokener(response).nextValue()).optInt("signin");
                    Class cls = n.class;
                    Object[] objArr = new Object[2];
                    objArr[0] = Boolean.valueOf(true);
                    if (signIn == 1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    objArr[1] = Boolean.valueOf(z);
                    EventNotifyCenter.notifyEvent(cls, 294, objArr);
                } catch (Exception e) {
                    EventNotifyCenter.notifyEvent(n.class, 294, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false)});
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ a aCA;

            {
                this.aCA = this$0;
            }

            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEvent(n.class, 294, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false)});
            }
        });
    }

    public void f(long userId, long categoryId) {
        Map<String, String> param = new HashMap();
        param.put("cat_id", String.valueOf(categoryId));
        param.put("user_id", String.valueOf(userId));
        HttpMgr.getInstance().performStringRequest(ab.azf, param, new Listener<String>(this) {
            final /* synthetic */ a aCA;

            {
                this.aCA = this$0;
            }

            public void onResponse(String response) {
                try {
                    EventNotifyCenter.notifyEvent(n.class, 295, new Object[]{Boolean.valueOf(true)});
                } catch (Exception e) {
                    EventNotifyCenter.notifyEvent(n.class, 295, new Object[]{Boolean.valueOf(false)});
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ a aCA;

            {
                this.aCA = this$0;
            }

            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEvent(n.class, 295, new Object[]{Boolean.valueOf(false)});
            }
        });
    }
}
