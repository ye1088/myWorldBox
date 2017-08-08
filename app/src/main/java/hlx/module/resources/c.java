package hlx.module.resources;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.module.m;
import com.MCWorld.module.n;
import com.MCWorld.module.o;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: ResourceModuleWrapper */
public class c {
    private int axr = 1;
    private String bcC = "";

    public c(int resType, String strVerFilter) {
        this.axr = resType;
        this.bcC = strVerFilter;
    }

    public void hs(String strVerFilter) {
        this.bcC = strVerFilter;
    }

    public void DK() {
        HttpMgr.getInstance().performStringRequest(m.axZ, new Listener<String>(this) {
            final /* synthetic */ c bXK;

            {
                this.bXK = this$0;
            }

            public void onResponse(String response) {
                try {
                    b info = (b) Json.parseJsonObject(response, b.class);
                    if (info == null || !info.isSucc()) {
                        String msg = info == null ? "加载资源分类失败" : info.msg;
                        EventNotifyCenter.notifyEventUiThread(n.class, n.awJ, new Object[]{Boolean.valueOf(false), null, msg});
                        return;
                    }
                    ArrayList<a> list;
                    switch (this.bXK.axr) {
                        case 2:
                            list = info.jsCategoryList;
                            break;
                        case 3:
                            list = info.skinCategoryList;
                            break;
                        case 4:
                            list = info.woodCategoryList;
                            break;
                        default:
                            list = info.mapCategoryList;
                            break;
                    }
                    EventNotifyCenter.notifyEventUiThread(n.class, n.awJ, new Object[]{Boolean.valueOf(true), list, null});
                } catch (Exception e) {
                    EventNotifyCenter.notifyEventUiThread(n.class, n.awJ, new Object[]{Boolean.valueOf(false), null, "加载资源分类失败"});
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ c bXK;

            {
                this.bXK = this$0;
            }

            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEventUiThread(n.class, n.awJ, new Object[]{Boolean.valueOf(false), null, "加载资源分类失败"});
            }
        });
    }

    public void ae(int requestCode, int start, int page) {
        switch (this.axr) {
            case 2:
                o.b(requestCode, this.bcC, start, page);
                return;
            case 3:
                o.h(requestCode, this.bcC, start, page);
                return;
            case 4:
                o.e(requestCode, this.bcC, start, page);
                return;
            default:
                o.R(requestCode, start, page);
                return;
        }
    }

    public void af(int requestCode, int start, int page) {
        switch (this.axr) {
            case 2:
                o.c(requestCode, this.bcC, start, page);
                return;
            case 3:
                o.i(requestCode, this.bcC, start, page);
                return;
            case 4:
                o.f(requestCode, this.bcC, start, page);
                return;
            default:
                o.Q(requestCode, start, page);
                return;
        }
    }

    public void ag(int requestCode, int start, int page) {
        switch (this.axr) {
            case 2:
                o.a(requestCode, this.bcC, start, page);
                return;
            case 3:
                o.g(requestCode, this.bcC, start, page);
                return;
            case 4:
                o.d(requestCode, this.bcC, start, page);
                return;
            default:
                o.P(requestCode, start, page);
                return;
        }
    }

    public void y(int requestCode, int cateId, int start, int page) {
        switch (this.axr) {
            case 2:
                o.a(requestCode, this.bcC, cateId, start, page);
                return;
            case 3:
                o.c(requestCode, this.bcC, cateId, start, page);
                return;
            case 4:
                o.b(requestCode, this.bcC, cateId, start, page);
                return;
            default:
                o.q(requestCode, cateId, start, page);
                return;
        }
    }

    public void ah(final int requestCode, int start, int count) {
        String uri;
        switch (this.axr) {
            case 2:
                uri = m.auI;
                break;
            case 3:
                uri = m.auK;
                break;
            case 4:
                uri = m.auJ;
                break;
            default:
                uri = m.auH;
                break;
        }
        HashMap<String, String> params = new HashMap();
        params.put("start", String.valueOf(start));
        params.put("count", String.valueOf(count));
        HttpMgr.getInstance().performStringRequest(uri, params, new Listener<String>(this) {
            final /* synthetic */ c bXK;

            public void onResponse(String response) {
                try {
                    d info = (d) Json.parseJsonObject(response, d.class);
                    if (info == null || !info.isSucc()) {
                        String msg = info == null ? "加载专题列表失败" : info.msg;
                        EventNotifyCenter.notifyEvent(n.class, n.awK, new Object[]{Boolean.valueOf(false), Integer.valueOf(requestCode), null, msg});
                        return;
                    }
                    EventNotifyCenter.notifyEvent(n.class, n.awK, new Object[]{Boolean.valueOf(true), Integer.valueOf(requestCode), info, null});
                } catch (Exception e) {
                    EventNotifyCenter.notifyEvent(n.class, n.awK, new Object[]{Boolean.valueOf(false), Integer.valueOf(requestCode), null, "加载专题列表失败"});
                }
            }
        }, new ErrorListener(this) {
            final /* synthetic */ c bXK;

            public void onErrorResponse(VolleyError error) {
                EventNotifyCenter.notifyEvent(n.class, n.awK, new Object[]{Boolean.valueOf(false), Integer.valueOf(requestCode), null, "加载专题列表失败"});
            }
        });
    }
}
