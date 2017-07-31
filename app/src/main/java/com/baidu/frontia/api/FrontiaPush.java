package com.baidu.frontia.api;

import android.content.Context;
import com.baidu.android.pushservice.internal.PushManager;
import com.baidu.android.pushservice.internal.PushSettings;
import com.baidu.frontia.FrontiaQuery;
import com.baidu.frontia.api.FrontiaPushListener.CommonMessageListener;
import com.baidu.frontia.api.FrontiaPushListener.DescribeMessageListener;
import com.baidu.frontia.api.FrontiaPushListener.ListMessageListener;
import com.baidu.frontia.api.FrontiaPushListener.PushMessageListener;
import com.baidu.frontia.api.FrontiaPushUtil.MessageContent;
import com.baidu.frontia.api.FrontiaPushUtil.Trigger;
import com.baidu.frontia.base.stat.StatUtils;
import com.baidu.frontia.framework.IModule;
import com.baidu.frontia.module.push.FrontiaPushImpl;
import com.baidu.frontia.module.push.FrontiaPushListenerImpl.CommonMessageListenerImpl;
import com.baidu.frontia.module.push.FrontiaPushListenerImpl.DescribeMessageListenerImpl;
import com.baidu.frontia.module.push.FrontiaPushListenerImpl.DescribeMessageResult;
import com.baidu.frontia.module.push.FrontiaPushListenerImpl.ListMessageListenerImpl;
import com.baidu.frontia.module.push.FrontiaPushListenerImpl.PushMessageListenerImpl;
import java.util.ArrayList;
import java.util.List;

public class FrontiaPush implements IModule {
    private static FrontiaPush a = null;
    private FrontiaPushImpl b;
    private Context c;

    class a {
        CommonMessageListenerImpl a = new CommonMessageListenerImpl(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void onFailure(int i, String str) {
                if (this.a.c != null) {
                    this.a.c.onFailure(i, str);
                }
            }

            public void onSuccess() {
                if (this.a.c != null) {
                    this.a.c.onSuccess();
                }
            }
        };
        final /* synthetic */ FrontiaPush b;
        private CommonMessageListener c;

        a(FrontiaPush frontiaPush, CommonMessageListener commonMessageListener) {
            this.b = frontiaPush;
            this.c = commonMessageListener;
        }

        CommonMessageListenerImpl a() {
            return this.a;
        }
    }

    class b {
        DescribeMessageListenerImpl a = new DescribeMessageListenerImpl(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }

            public void onFailure(int i, String str) {
                if (this.a.c != null) {
                    this.a.c.onFailure(i, str);
                }
            }

            public void onSuccess(DescribeMessageResult describeMessageResult) {
                if (this.a.c != null) {
                    this.a.c.onSuccess(new FrontiaPushListener.DescribeMessageResult(describeMessageResult));
                }
            }
        };
        final /* synthetic */ FrontiaPush b;
        private DescribeMessageListener c;

        b(FrontiaPush frontiaPush, DescribeMessageListener describeMessageListener) {
            this.b = frontiaPush;
            this.c = describeMessageListener;
        }

        DescribeMessageListenerImpl a() {
            return this.a;
        }
    }

    class c {
        ListMessageListenerImpl a = new ListMessageListenerImpl(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }

            public void onFailure(int i, String str) {
                if (this.a.c != null) {
                    this.a.c.onFailure(i, str);
                }
            }

            public void onSuccess(List<DescribeMessageResult> list) {
                if (this.a.c != null) {
                    List arrayList = new ArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        arrayList.add(new FrontiaPushListener.DescribeMessageResult((DescribeMessageResult) list.get(i)));
                    }
                    this.a.c.onSuccess(arrayList);
                }
            }
        };
        final /* synthetic */ FrontiaPush b;
        private ListMessageListener c;

        c(FrontiaPush frontiaPush, ListMessageListener listMessageListener) {
            this.b = frontiaPush;
            this.c = listMessageListener;
        }

        ListMessageListenerImpl a() {
            return this.a;
        }
    }

    class d {
        PushMessageListenerImpl a = new PushMessageListenerImpl(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onFailure(int i, String str) {
                if (this.a.c != null) {
                    this.a.c.onFailure(i, str);
                }
            }

            public void onSuccess(String str) {
                if (this.a.c != null) {
                    this.a.c.onSuccess(str);
                }
            }
        };
        final /* synthetic */ FrontiaPush b;
        private PushMessageListener c;

        d(FrontiaPush frontiaPush, PushMessageListener pushMessageListener) {
            this.b = frontiaPush;
            this.c = pushMessageListener;
        }

        PushMessageListenerImpl a() {
            return this.a;
        }
    }

    private FrontiaPush(Context context) {
        this.c = context;
        this.b = new FrontiaPushImpl(context);
    }

    public static FrontiaPush newInstance(Context context) {
        if (context == null) {
            return null;
        }
        if (a == null) {
            synchronized (FrontiaPush.class) {
                if (a == null) {
                    a = new FrontiaPush(context);
                }
            }
        }
        return a;
    }

    FrontiaPushImpl a() {
        return this.b;
    }

    public void deleteTags(List<String> list) {
        this.b.deleteTags(list);
    }

    public void describeMessage(String str, DescribeMessageListener describeMessageListener) {
        this.b.describeMessage(str, new b(this, describeMessageListener).a());
    }

    public void disableLbs() {
        this.b.disableLbs();
    }

    public void enableLbs() {
        this.b.enableLbs();
    }

    public void init(String str) {
        this.b.init(str);
    }

    public boolean isPushWorking() {
        return PushManager.isPushEnabled(this.c);
    }

    public void listMessage(FrontiaQuery frontiaQuery, ListMessageListener listMessageListener) {
        this.b.listMessage(frontiaQuery.toJSONObject(), new c(this, listMessageListener).a());
    }

    public void listTags() {
        this.b.listTags();
    }

    public void pushMessage(MessageContent messageContent, PushMessageListener pushMessageListener) {
        this.b.pushMessage(messageContent.a(), new d(this, pushMessageListener).a());
    }

    public void pushMessage(Trigger trigger, MessageContent messageContent, PushMessageListener pushMessageListener) {
        this.b.pushMessage(trigger.a(), messageContent.a(), new d(this, pushMessageListener).a());
    }

    public void pushMessage(String str, MessageContent messageContent, PushMessageListener pushMessageListener) {
        this.b.pushMessage(str, messageContent.a(), new d(this, pushMessageListener).a());
    }

    public void pushMessage(String str, Trigger trigger, MessageContent messageContent, PushMessageListener pushMessageListener) {
        this.b.pushMessage(str, trigger.a(), messageContent.a(), new d(this, pushMessageListener).a());
    }

    public void pushMessage(String str, String str2, MessageContent messageContent, PushMessageListener pushMessageListener) {
        this.b.pushMessage(str, str2, messageContent.a(), new d(this, pushMessageListener).a());
    }

    public void pushMessage(String str, String str2, Trigger trigger, MessageContent messageContent, PushMessageListener pushMessageListener) {
        d dVar = new d(this, pushMessageListener);
        this.b.pushMessage(str, str2, trigger.a(), messageContent.a(), dVar.a());
    }

    public void removeMessage(String str, CommonMessageListener commonMessageListener) {
        this.b.removeMessage(str, new a(this, commonMessageListener).a());
    }

    public void replaceMessage(String str, Trigger trigger, MessageContent messageContent, CommonMessageListener commonMessageListener) {
        this.b.replaceMessage(str, trigger.a(), messageContent.a(), new a(this, commonMessageListener).a());
    }

    public void replaceMessage(String str, String str2, Trigger trigger, MessageContent messageContent, CommonMessageListener commonMessageListener) {
        a aVar = new a(this, commonMessageListener);
        this.b.replaceMessage(str, str2, trigger.a(), messageContent.a(), aVar.a());
    }

    public void replaceMessage(String str, String str2, String str3, Trigger trigger, MessageContent messageContent, CommonMessageListener commonMessageListener) {
        a aVar = new a(this, commonMessageListener);
        this.b.replaceMessage(str, str2, str3, trigger.a(), messageContent.a(), aVar.a());
    }

    public void resume() {
        PushManager.resumeWork(this.c);
    }

    public void setDebugModeEnabled(boolean z) {
        PushSettings.enableDebugMode(this.c, z);
    }

    public void setNotificationBuilder(int i, a aVar) {
        if (aVar != null) {
            PushManager.setNotificationBuilder(this.c, i, aVar.a());
        }
    }

    public void setTags(List<String> list) {
        this.b.setTags(list);
    }

    public void start() {
        this.b.start();
    }

    public void start(String str) {
        this.b.start(str);
    }

    public void stop() {
        PushManager.stopWork(this.c);
        StatUtils.insertBehavior(this.c, "010702", 0, "", "", System.currentTimeMillis());
    }
}
