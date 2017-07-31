package com.huluxia.ui.profile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.image.Config;
import com.huluxia.framework.base.image.PipelineView;
import com.huluxia.framework.base.image.PipelineView.Callback;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.module.profile.h;
import com.huluxia.t;
import com.huluxia.ui.base.BaseThemeFragment;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.simple.colorful.d;

public class SpaceStyleDetailFragment extends BaseThemeFragment {
    private static final String bhC = "EXTRA_FROM_HOME";
    private static final String bhM = "EXTRA_SPACE_STYLE";
    private View aIC;
    private boolean aLf;
    private DialogManager aSB;
    private View bhN;
    private PipelineView bhO;
    private ImageView bhP;
    private TextView bhQ;
    private ViewSwitcher bhR;
    private TextView bhS;
    private EditText bhT;
    private TextView bhU;
    private h bhV;
    private ObjectAnimator bhW;
    private boolean bhX = false;
    private TextView bhY;
    private CallbackHandler mCallback = new 3(this);
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ SpaceStyleDetailFragment bhZ;

        {
            this.bhZ = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.iv_space_background) {
                if (!this.bhZ.bhX) {
                    this.bhZ.JJ();
                }
            } else if (id == g.save) {
                if (this.bhZ.bhV.isuse == 1) {
                    this.bhZ.cs(true);
                    this.bhZ.bhU.setEnabled(false);
                    com.huluxia.module.profile.g.Eb().b(this.bhZ.bhV.id, this.bhZ.getActivity());
                } else if (this.bhZ.bhV.model == 1) {
                    this.bhZ.JK();
                }
            } else if (id == g.tv_exchanged) {
                String code = this.bhZ.bhT.getText().toString();
                if (!UtilsFunction.empty(code)) {
                    this.bhZ.cs(true);
                    this.bhZ.bhS.setEnabled(false);
                    UtilsScreen.hideInputMethod(this.bhZ.bhT);
                    com.huluxia.module.profile.g.Eb().a(this.bhZ.bhV.id, code, this.bhZ.getActivity());
                }
            }
        }
    };
    private Handler mHandler = new Handler(this) {
        final /* synthetic */ SpaceStyleDetailFragment bhZ;

        {
            this.bhZ = this$0;
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == 0) {
                this.bhZ.bhO.setClickable(false);
                this.bhZ.bhY.setVisibility(0);
            } else if (what == 1) {
                int pos = ((Integer) msg.obj).intValue();
                this.bhZ.bhY.setText(AppConfig.getInstance().getAppContext().getResources().getString(m.format_photo_progress, new Object[]{Integer.valueOf(pos)}));
            } else if (what == 2) {
                this.bhZ.bhY.setVisibility(8);
                this.bhZ.bhP.setVisibility(0);
                this.bhZ.bhU.setEnabled(true);
                if (this.bhZ.bhW != null) {
                    this.bhZ.bhW.start();
                }
                this.bhZ.bhX = true;
                this.bhZ.bhO.setClickable(true);
            } else {
                this.bhZ.bhX = false;
                this.bhZ.bhY.setVisibility(8);
                t.n(AppConfig.getInstance().getAppContext(), AppConfig.getInstance().getAppContext().getResources().getString(m.load_image_failed));
                this.bhZ.bhO.setClickable(true);
            }
        }
    };

    public static SpaceStyleDetailFragment a(boolean isFromHome, h mSpaceStyle) {
        Bundle args = new Bundle();
        args.putParcelable(bhM, mSpaceStyle);
        args.putBoolean("EXTRA_FROM_HOME", isFromHome);
        SpaceStyleDetailFragment fragment = new SpaceStyleDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        View view = inflater.inflate(i.fragment_space_style_detail, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.bhV = (h) arguments.getParcelable(bhM);
            this.aLf = arguments.getBoolean("EXTRA_FROM_HOME");
        }
        this.aIC = view.findViewById(g.loading);
        this.aIC.setVisibility(8);
        this.bhO = (PipelineView) view.findViewById(g.iv_space_background);
        this.bhO.setClickable(false);
        this.bhO.setOnClickListener(this.mClickListener);
        this.bhN = view.findViewById(g.container_preview);
        this.bhP = (ImageView) view.findViewById(g.iv_space_profile);
        this.bhQ = (TextView) view.findViewById(g.condition);
        this.bhR = (ViewSwitcher) view.findViewById(g.switcher_bottom);
        this.bhT = (EditText) this.bhR.findViewById(g.code);
        this.bhS = (TextView) this.bhR.findViewById(g.tv_exchanged);
        this.bhS.setOnClickListener(this.mClickListener);
        this.bhU = (TextView) this.bhR.findViewById(g.save);
        this.bhU.setOnClickListener(this.mClickListener);
        this.bhU.setEnabled(false);
        this.bhY = (TextView) view.findViewById(g.tv_progress);
        this.bhY.setVisibility(8);
        this.bhP.setVisibility(8);
        EventNotifyCenter.add(com.huluxia.module.h.class, this.mCallback);
        this.bhN.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ SpaceStyleDetailFragment bhZ;

            {
                this.bhZ = this$0;
            }

            public void onGlobalLayout() {
                int height = this.bhZ.bhN.getHeight();
                LayoutParams lp = (LayoutParams) this.bhZ.bhN.getLayoutParams();
                lp.width = (int) ((((double) (UtilsScreen.getScreenWidth(this.bhZ.getActivity()) * height)) * 1.0d) / ((double) UtilsScreen.getScreenHeight(this.bhZ.getActivity())));
                this.bhZ.bhN.setLayoutParams(lp);
                this.bhZ.kZ(height);
                this.bhZ.bhN.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        JI();
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventNotifyCenter.remove(this.mCallback);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private void JI() {
        if (this.bhV != null) {
            JJ();
            if (this.bhV.isuse == 1 || this.bhV.model != 2) {
                this.bhR.setDisplayedChild(0);
            } else {
                this.bhR.setDisplayedChild(1);
            }
            if (this.bhV.model == 0) {
                this.bhQ.setText(getActivity().getString(m.space_style_free));
            } else if (this.bhV.model == 1) {
                this.bhQ.setText(getActivity().getString(m.space_style_integral, new Object[]{this.bhV.integralNick, Integer.valueOf(this.bhV.price)}));
            } else {
                this.bhQ.setText(this.bhV.desc);
            }
        }
    }

    private void JJ() {
        if (this.bhV != null) {
            this.bhO.setUri(UtilUri.getUriOrNull(this.bhV.imgurl), Config.defaultConfig(), new Callback(this) {
                final /* synthetic */ SpaceStyleDetailFragment bhZ;

                {
                    this.bhZ = this$0;
                }

                public void onSucc(Bitmap bitmap) {
                    this.bhZ.mHandler.sendMessage(this.bhZ.mHandler.obtainMessage(2));
                }

                public void onFailed() {
                    this.bhZ.mHandler.sendMessage(this.bhZ.mHandler.obtainMessage(3));
                }

                public void onProgressUpdate(float progress) {
                    Message message = this.bhZ.mHandler.obtainMessage(1);
                    message.obj = Integer.valueOf((int) (100.0f * progress));
                    this.bhZ.mHandler.sendMessage(message);
                }
            });
            this.mHandler.sendMessage(this.mHandler.obtainMessage(0));
        }
    }

    public void onUnSelected(int position) {
        super.onUnSelected(position);
        if (this.bhW != null) {
            this.bhW.end();
        }
    }

    public void onSelected(int position) {
        super.onSelected(position);
        startAnimation();
    }

    private void startAnimation() {
        if (this.bhW != null && this.bhX && !this.bhW.isStarted()) {
            this.bhW.start();
        }
    }

    private void kZ(int height) {
        ViewHelper.setTranslationY(this.bhP, (float) (((double) height) * 0.42d));
        this.bhW = ObjectAnimator.ofFloat(this.bhP, "translationY", startY, 0.0f);
        this.bhW.setDuration(1000);
        this.bhW.setInterpolator(new DecelerateInterpolator());
        this.bhW.setRepeatCount(1);
        this.bhW.setRepeatMode(2);
        this.bhW.setStartDelay(500);
    }

    private void JK() {
        View customView = LayoutInflater.from(getActivity()).inflate(i.dialog_buy_space_style, null);
        ((TextView) customView.findViewById(g.tv_tip)).setText(getActivity().getString(m.sure_to_buy_space_style, new Object[]{this.bhV.integralNick, Integer.valueOf(this.bhV.price)}));
        int colorGreen = d.getColor(getActivity(), c.textColorGreen);
        int colorGray = d.getColor(getActivity(), c.textColorGreen);
        if (this.aSB == null) {
            this.aSB = new DialogManager(getActivity());
        }
        this.aSB.showOkCancelColorDialog("温馨提示", colorGreen, customView, getString(m.btn_commit), colorGreen, getString(m.btn_cancel), colorGray, true, new OkCancelDialogListener(this) {
            final /* synthetic */ SpaceStyleDetailFragment bhZ;

            {
                this.bhZ = this$0;
            }

            public void onCancel() {
            }

            public void onOk() {
                this.bhZ.cs(true);
                this.bhZ.bhU.setEnabled(false);
                com.huluxia.module.profile.g.Eb().c(this.bhZ.bhV.id, this.bhZ.getActivity());
            }
        });
    }

    public void cs(boolean show) {
        this.aIC.setVisibility(show ? 0 : 8);
    }
}
