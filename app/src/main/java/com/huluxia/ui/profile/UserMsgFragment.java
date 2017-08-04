package com.huluxia.ui.profile;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.message.UserMsgItem;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsSystem;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.module.account.e;
import com.huluxia.module.h;
import com.huluxia.module.w;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.ui.itemadapter.message.MessageItemAdapter;
import com.huluxia.utils.UtilsMenu;
import com.huluxia.utils.UtilsMenu.MENU_VALUE;
import com.huluxia.utils.aa;
import com.huluxia.utils.aa.a;
import com.huluxia.utils.ab;
import com.huluxia.utils.ad;
import com.huluxia.utils.ah;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class UserMsgFragment extends BaseLoadingFragment {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "UserMsgFragment";
    private static final String aKw = "PARA_TOPIC";
    private static final String biQ = "USER_MSG_DATA";
    private static final String bjg = "PARA_COMMENT";
    private CommonMenuDialog aHI;
    private aa aHb;
    private TopicItem aMT;
    private OnClickListener aNI = new OnClickListener(this) {
        final /* synthetic */ UserMsgFragment bjp;

        {
            this.bjp = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.num1) {
                this.bjp.aNK.setSelected(true);
                this.bjp.aNL.setSelected(false);
                this.bjp.aNM.setSelected(false);
                this.bjp.aNN.setSelected(false);
                this.bjp.GG();
                this.bjp.aNJ = "1";
            } else if (id == g.num2) {
                this.bjp.aNK.setSelected(false);
                this.bjp.aNL.setSelected(true);
                this.bjp.aNM.setSelected(false);
                this.bjp.aNN.setSelected(false);
                this.bjp.GG();
                this.bjp.aNJ = "2";
            } else if (id == g.num5) {
                this.bjp.aNK.setSelected(false);
                this.bjp.aNL.setSelected(false);
                this.bjp.aNM.setSelected(true);
                this.bjp.aNN.setSelected(false);
                this.bjp.GG();
                this.bjp.aNJ = "5";
            }
            this.bjp.GF();
        }
    };
    private String aNJ;
    private RadioButton aNK;
    private RadioButton aNL;
    private RadioButton aNM;
    private EditText aNN;
    private boolean aPg = false;
    private PullToRefreshListView aQB;
    private int biU = 0;
    private final String bjh = "PARA_CONTENTTYPE";
    private final String bji = "PARA_USER";
    private MessageItemAdapter bjj;
    private UserMsgFragment bjk;
    private e bjl;
    private CommentItem bjm;
    private UserBaseInfo bjn;
    private int bjo;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ UserMsgFragment bjp;

        {
            this.bjp = this$0;
        }

        @MessageHandler(message = 646)
        public void onRecvMsg(e info, String start) {
            this.bjp.aQB.onRefreshComplete();
            if (this.bjp.bjj == null || info == null || !info.isSucc()) {
                this.bjp.aHb.KN();
                if (info != null && info.code == 103) {
                    ((MessageHistoryActivity) this.bjp.bjk.getActivity()).eJ("登录信息过期，请重新登录");
                    return;
                } else if (this.bjp.getCurrentPage() == 0) {
                    this.bjp.FB();
                    return;
                } else {
                    t.n(this.bjp.getActivity(), this.bjp.getResources().getString(m.loading_failed_please_retry));
                    return;
                }
            }
            this.bjp.aHb.onLoadComplete();
            if (this.bjp.getCurrentPage() == 0) {
                this.bjp.FC();
            }
            if (start == null || start.equals("0")) {
                this.bjp.bjl = info;
                EventNotifyCenter.notifyEvent(h.class, 770, new Object[0]);
            } else {
                this.bjp.bjl.start = info.start;
                this.bjp.bjl.more = info.more;
                this.bjp.bjl.datas.addAll(info.datas);
            }
            this.bjp.bjj.setData(this.bjp.bjl.datas);
        }

        @MessageHandler(message = 644)
        public void onRecvTransferRet(boolean succ, w info) {
            if (info != null && info.isSucc()) {
                t.o(this.bjp.bjk.getActivity(), "赠送成功");
            } else if (info != null) {
                t.show_toast(this.bjp.bjk.getActivity(), ab.n(info.code, info.msg));
            } else {
                t.n(this.bjp.bjk.getActivity(), "赠送失败，请稍后重试");
            }
        }

        @MessageHandler(message = 648)
        public void onRecConf(com.huluxia.module.topic.g info, String tag, boolean next, Object context) {
            HLog.verbose(UserMsgFragment.TAG, "info " + info, new Object[0]);
            if (this.bjp.aPg) {
                this.bjp.aPg = false;
                if (tag == null || !tag.equals(UserMsgFragment.TAG) || !next) {
                    return;
                }
                if (info == null || !info.isSucc()) {
                    if (info != null) {
                        t.n(this.bjp.bjk.getActivity(), ab.n(info.code, info.msg));
                        return;
                    }
                    this.bjp.a(this.bjp.aMT, this.bjp.bjm, this.bjp.bjo, this.bjp.bjn);
                } else if (info.ispower == 1) {
                    this.bjp.a(this.bjp.aMT, this.bjp.bjm, this.bjp.bjo, this.bjp.bjn);
                } else {
                    this.bjp.T(info.title, info.message);
                }
            }
        }
    };

    public static UserMsgFragment Kh() {
        return new UserMsgFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HLog.verbose(this, "UserMsgFragMent create", new Object[0]);
        this.bjk = this;
        EventNotifyCenter.add(h.class, this.mCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    public void onResume() {
        super.onResume();
        if (this.bjj != null) {
            this.bjj.notifyDataSetChanged();
        }
    }

    public void JZ() {
        if (this.aQB != null && this.aQB.getRefreshableView() != null) {
            this.aQB.scrollTo(0, 0);
            ((ListView) this.aQB.getRefreshableView()).setSelection(0);
        }
    }

    protected void kj(int newTheme) {
        super.kj(newTheme);
        if (this.bjj != null) {
            this.bjj.notifyDataSetChanged();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(i.activity_message_history, container, false);
        this.aQB = (PullToRefreshListView) view.findViewById(g.list);
        ((ListView) this.aQB.getRefreshableView()).setSelector(d.transparent);
        this.bjj = new MessageItemAdapter(getActivity());
        this.aQB.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ UserMsgFragment bjp;

            {
                this.bjp = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bjp.reload();
            }
        });
        this.aQB.setAdapter(this.bjj);
        this.aHb = new aa((ListView) this.aQB.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ UserMsgFragment bjp;

            {
                this.bjp = this$0;
            }

            public void onLoadData() {
                com.huluxia.module.account.a.DU().M(this.bjp.bjl == null ? "0" : this.bjp.bjl.start, 20);
            }

            public boolean shouldLoadData() {
                if (this.bjp.bjl == null) {
                    this.bjp.aHb.onLoadComplete();
                    return false;
                } else if (this.bjp.bjl.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aQB.setOnScrollListener(this.aHb);
        this.aQB.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ UserMsgFragment bjp;

            {
                this.bjp = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                UserMsgItem clickItem = (UserMsgItem) parent.getAdapter().getItem(position);
                if (clickItem != null && clickItem.getOperateType() == 0) {
                    this.bjp.aMT = clickItem.getContent().getTopicItem();
                    this.bjp.bjm = clickItem.getContent();
                    this.bjp.aMT.setCategoryName(clickItem.getContent().getTopicCategory().getTitle());
                    this.bjp.bjo = clickItem.getContentType();
                    this.bjp.bjn = clickItem.getContent().getUserInfo();
                    this.bjp.a(clickItem);
                }
            }
        });
        if (savedInstanceState != null) {
            this.bjl = (e) savedInstanceState.getParcelable(biQ);
            if (this.bjl != null) {
                this.bjj.setData(this.bjl.datas);
            }
            this.aMT = (TopicItem) savedInstanceState.getParcelable(aKw);
            this.bjm = (CommentItem) savedInstanceState.getParcelable(bjg);
            this.bjo = savedInstanceState.getInt("PARA_CONTENTTYPE");
            this.bjn = (UserBaseInfo) savedInstanceState.getParcelable("PARA_USER");
        }
        cq(false);
        return view;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(biQ, this.bjl);
        outState.putParcelable(aKw, this.aMT);
        outState.putParcelable(bjg, this.bjm);
        outState.putInt("PARA_CONTENTTYPE", this.bjo);
        outState.putParcelable("PARA_USER", this.bjn);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (builder != null && this.bjj != null && this.aQB != null) {
            k setter = new j((ViewGroup) this.aQB.getRefreshableView());
            setter.a(this.bjj);
            builder.a(setter);
        }
    }

    protected void EX() {
        super.EX();
        reload();
    }

    public void reload() {
        com.huluxia.module.account.a.DU().M("0", 20);
        HTApplication.a(null);
        com.huluxia.service.h.EC().ED();
        com.huluxia.service.i.EI();
    }

    public void Ka() {
        int i = this.biU;
        this.biU = i + 1;
        if (i < 1) {
            Fy();
            reload();
        }
    }

    private void GF() {
        Drawable o;
        RadioButton radioButton = this.aNK;
        if (this.aNK.isSelected()) {
            o = com.simple.colorful.d.o(this.bjk.getActivity(), c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.bjk.getActivity(), c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        radioButton = this.aNL;
        if (this.aNL.isSelected()) {
            o = com.simple.colorful.d.o(this.bjk.getActivity(), c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.bjk.getActivity(), c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        radioButton = this.aNM;
        if (this.aNM.isSelected()) {
            o = com.simple.colorful.d.o(this.bjk.getActivity(), c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.bjk.getActivity(), c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        EditText editText = this.aNN;
        if (this.aNN.isSelected()) {
            o = com.simple.colorful.d.o(this.bjk.getActivity(), c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.bjk.getActivity(), c.drawableTopicSendhulu);
        }
        editText.setBackgroundDrawable(o);
    }

    private void GG() {
        this.aNN.clearFocus();
        this.aNN.getEditableText().clear();
        this.aNN.getEditableText().clearSpans();
        this.aNN.setText("");
    }

    private void GT() {
        boolean isTopic;
        long id;
        if (this.bjo == 203) {
            isTopic = true;
        } else {
            isTopic = false;
        }
        if (isTopic && this.aMT != null) {
            id = this.aMT.getPostID();
        } else if (this.bjm != null) {
            id = this.bjm.getCommentID();
        } else {
            return;
        }
        final long post_id = id;
        final Dialog dialog = new Dialog(this.bjk.getActivity(), com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this.bjk.getActivity()).inflate(i.include_credit_send, null);
        this.aNK = (RadioButton) layout.findViewById(g.num1);
        this.aNL = (RadioButton) layout.findViewById(g.num2);
        this.aNM = (RadioButton) layout.findViewById(g.num5);
        this.aNK.setSelected(true);
        this.aNJ = "1";
        this.aNN = (EditText) layout.findViewById(g.other_num);
        this.aNN.setVisibility(8);
        GF();
        if (com.huluxia.data.j.ep().ey()) {
            com.huluxia.data.g info = com.huluxia.data.j.ep().ew();
            HLog.info(TAG, "isgold %d", Integer.valueOf(info.isgold));
            if (info != null && info.isgold == 1) {
                this.aNN.setVisibility(0);
            }
        }
        this.aNK.setOnClickListener(this.aNI);
        this.aNL.setOnClickListener(this.aNI);
        this.aNM.setOnClickListener(this.aNI);
        this.aNN.setOnFocusChangeListener(new OnFocusChangeListener(this) {
            final /* synthetic */ UserMsgFragment bjp;

            {
                this.bjp = this$0;
            }

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    this.bjp.aNN.setSelected(true);
                    this.bjp.aNK.setSelected(false);
                    this.bjp.aNL.setSelected(false);
                    this.bjp.aNM.setSelected(false);
                }
                this.bjp.GF();
            }
        });
        final EditText reason = (EditText) layout.findViewById(g.content_text);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ UserMsgFragment bjp;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ UserMsgFragment bjp;

            public void onClick(View arg0) {
                if (this.bjp.aNN.isSelected()) {
                    String input = this.bjp.aNN.getText().toString();
                    if (ad.empty((CharSequence) input)) {
                        this.bjp.aNJ = "";
                    } else {
                        this.bjp.aNJ = input;
                    }
                }
                if (this.bjp.aNJ.length() <= 0 || "0".equals(this.bjp.aNJ)) {
                    t.n(this.bjp.getActivity(), "请填入正确数字");
                    return;
                }
                String score_txt = reason.getText() == null ? "" : reason.getText().toString();
                if (score_txt.trim().length() < 5) {
                    t.n(this.bjp.bjk.getActivity(), "理由不能少于5个字符");
                    return;
                }
                com.huluxia.module.account.a.DU().a(post_id, isTopic, this.bjp.aNJ, score_txt);
                dialog.dismiss();
            }
        });
    }

    private void a(TopicItem topicItem, CommentItem commentItem, int conntentType, UserBaseInfo user) {
        if (com.huluxia.data.j.ep().ey()) {
            long mCat = 0;
            if (!(commentItem == null || commentItem.getTopicCategory() == null)) {
                mCat = commentItem.getTopicCategory().categoryID;
            }
            if (mCat == 0) {
                b(topicItem, commentItem, conntentType, user);
                return;
            } else if (!this.aPg) {
                com.huluxia.module.topic.g info = ah.KZ().bu(com.huluxia.data.j.ep().getUserid());
                String nowHour = UtilsSystem.getHourStr();
                HLog.verbose(TAG, "nowHour " + nowHour + " CreatePowerInfo " + info, new Object[0]);
                if (info != null && info.commentCats != null && info.commentCats.contains(Long.valueOf(mCat)) && info.commentHours != null && info.commentHours.containsKey(Long.valueOf(mCat)) && info.commentHours.get(Long.valueOf(mCat)) != null && ((String) info.commentHours.get(Long.valueOf(mCat))).equals(nowHour)) {
                    b(topicItem, commentItem, conntentType, user);
                    b(this.bjk.getActivity(), mCat, false);
                    return;
                } else if (info == null || info.commentTipMsg == null || info.commentTipTitle == null || info.commentHours == null || !info.commentHours.containsKey(Long.valueOf(mCat)) || info.commentHours.get(Long.valueOf(mCat)) == null || !((String) info.commentHours.get(Long.valueOf(mCat))).equals(nowHour)) {
                    b(this.bjk.getActivity(), mCat, true);
                    return;
                } else {
                    T(info.commentTipTitle, info.commentTipMsg);
                    b(this.bjk.getActivity(), mCat, false);
                    return;
                }
            } else {
                return;
            }
        }
        t.an(this.bjk.getActivity());
    }

    private void b(TopicItem topicItem, CommentItem commentItem, int contentType, UserBaseInfo user) {
        boolean isReplyTopic = false;
        if (contentType == 203) {
            isReplyTopic = true;
        }
        if (isReplyTopic) {
            t.a(this.bjk.getActivity(), topicItem, user);
        } else {
            t.a(this.bjk.getActivity(), topicItem, commentItem, false);
        }
    }

    private void T(String title, String msg) {
        com.huluxia.widget.dialog.i dia = new com.huluxia.widget.dialog.i(this.bjk.getActivity(), null);
        dia.az(title, msg);
        dia.gK("朕知道了");
        dia.showDialog();
    }

    private void b(Activity activity, long cat_id, boolean isnext) {
        if (!this.aPg) {
            this.aPg = true;
            com.huluxia.module.topic.k.Ej().b(activity, cat_id, TAG, isnext, null);
        }
    }

    private void a(final UserMsgItem clickItem) {
        this.aHI = UtilsMenu.a(this.bjk.getActivity(), clickItem.getContent(), new CommonMenuDialogListener(this) {
            final /* synthetic */ UserMsgFragment bjp;

            public void pressMenuById(int inIndex, Object inItem) {
                if (this.bjp.aHI != null) {
                    this.bjp.aHI.dismissDialog();
                    if (inIndex == MENU_VALUE.VIEW_TOPIC.ordinal()) {
                        t.a(this.bjp.bjk.getActivity(), this.bjp.aMT, clickItem.getContent().getTopicCategory().categoryID);
                    } else if (inIndex == MENU_VALUE.REPLY.ordinal()) {
                        this.bjp.a(this.bjp.aMT, this.bjp.bjm, this.bjp.bjo, this.bjp.bjn);
                    } else if (inIndex != MENU_VALUE.SEND_HULU.ordinal()) {
                    } else {
                        if (com.huluxia.data.j.ep().ey()) {
                            this.bjp.GT();
                        } else {
                            t.an(this.bjp.bjk.getActivity());
                        }
                    }
                }
            }
        });
        this.aHI.updateCurFocusIndex(-1);
        this.aHI.showMenu(null, null);
    }
}
