package com.MCWorld.mconline.activity.widget;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.simple.colorful.d;
import java.util.HashMap;
import java.util.Map;

/* compiled from: RadioDialog */
public class a extends Dialog {
    private Drawable akN;
    private a akX = null;
    private a akY = this;
    private TextView akZ;
    private TextView ala;
    private TextView alb;
    private TextView alc;
    private TextView ald;
    private TextView ale;
    private TextView alf;
    private TextView alg;
    private TextView alh;
    private TextView ali;
    private TextView alj;
    private TextView alk;
    private Object alm;
    private Map<Integer, Object> aln;
    private Map<Integer, String> alo;
    private Map<Integer, TextView> alp = new HashMap();
    private Map<Integer, View> alq = new HashMap();
    private TextView eN;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ a alr;

        {
            this.alr = this$0;
        }

        public void onClick(View view) {
            if (view.getId() == R.id.tv_cancle) {
                this.alr.AQ();
                return;
            }
            int index = ((Integer) view.getTag()).intValue();
            Object val = this.alr.aln.get(Integer.valueOf(index));
            String name = (String) this.alr.alo.get(Integer.valueOf(index));
            this.alr.hH(index);
            if (this.alr.akX != null) {
                this.alr.akX.g(val, name);
            }
            this.alr.AQ();
        }
    };
    private Activity mContext = null;

    /* compiled from: RadioDialog */
    public interface a {
        void g(Object obj, String str);

        void rb();
    }

    public a(Activity context, Object initVal, Map<Integer, Object> mapIndex2Val, Map<Integer, String> mapIndex2Name, a callback) {
        super(context, d.RD());
        this.mContext = context;
        this.akX = callback;
        this.alm = initVal;
        this.aln = mapIndex2Val;
        this.alo = mapIndex2Name;
        if (this.aln != null && mapIndex2Name != null && mapIndex2Val.size() == mapIndex2Name.size() && this.mContext != null && !this.mContext.isFinishing()) {
            show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mconline_dialog_radio);
        findViewById(R.id.tv_cancle).setOnClickListener(this.mClickListener);
        this.eN = (TextView) findViewById(R.id.tv_title);
        this.akZ = (TextView) findViewById(R.id.txt1);
        this.ala = (TextView) findViewById(R.id.txt2);
        this.alb = (TextView) findViewById(R.id.txt3);
        this.alc = (TextView) findViewById(R.id.txt4);
        this.ald = (TextView) findViewById(R.id.txt5);
        this.ale = (TextView) findViewById(R.id.txt6);
        this.alf = (TextView) findViewById(R.id.txt7);
        this.alg = (TextView) findViewById(R.id.txt8);
        this.alh = (TextView) findViewById(R.id.txt9);
        this.ali = (TextView) findViewById(R.id.txt10);
        this.alj = (TextView) findViewById(R.id.txt11);
        this.alk = (TextView) findViewById(R.id.txt12);
        this.akZ.setTag(Integer.valueOf(1));
        this.ala.setTag(Integer.valueOf(2));
        this.alb.setTag(Integer.valueOf(3));
        this.alc.setTag(Integer.valueOf(4));
        this.ald.setTag(Integer.valueOf(5));
        this.ale.setTag(Integer.valueOf(6));
        this.alf.setTag(Integer.valueOf(7));
        this.alg.setTag(Integer.valueOf(8));
        this.alh.setTag(Integer.valueOf(9));
        this.ali.setTag(Integer.valueOf(10));
        this.alj.setTag(Integer.valueOf(11));
        this.alk.setTag(Integer.valueOf(12));
        this.akZ.setOnClickListener(this.mClickListener);
        this.ala.setOnClickListener(this.mClickListener);
        this.alb.setOnClickListener(this.mClickListener);
        this.alc.setOnClickListener(this.mClickListener);
        this.ald.setOnClickListener(this.mClickListener);
        this.ale.setOnClickListener(this.mClickListener);
        this.alf.setOnClickListener(this.mClickListener);
        this.alg.setOnClickListener(this.mClickListener);
        this.alh.setOnClickListener(this.mClickListener);
        this.ali.setOnClickListener(this.mClickListener);
        this.alj.setOnClickListener(this.mClickListener);
        this.alk.setOnClickListener(this.mClickListener);
        this.alp.put(Integer.valueOf(1), this.akZ);
        this.alp.put(Integer.valueOf(2), this.ala);
        this.alp.put(Integer.valueOf(3), this.alb);
        this.alp.put(Integer.valueOf(4), this.alc);
        this.alp.put(Integer.valueOf(5), this.ald);
        this.alp.put(Integer.valueOf(6), this.ale);
        this.alp.put(Integer.valueOf(7), this.alf);
        this.alp.put(Integer.valueOf(8), this.alg);
        this.alp.put(Integer.valueOf(9), this.alh);
        this.alp.put(Integer.valueOf(10), this.ali);
        this.alp.put(Integer.valueOf(11), this.alj);
        this.alp.put(Integer.valueOf(12), this.alk);
        this.alq.put(Integer.valueOf(1), findViewById(R.id.split1));
        this.alq.put(Integer.valueOf(2), findViewById(R.id.split2));
        this.alq.put(Integer.valueOf(3), findViewById(R.id.split3));
        this.alq.put(Integer.valueOf(4), findViewById(R.id.split4));
        this.alq.put(Integer.valueOf(5), findViewById(R.id.split5));
        this.alq.put(Integer.valueOf(6), findViewById(R.id.split6));
        this.alq.put(Integer.valueOf(7), findViewById(R.id.split7));
        this.alq.put(Integer.valueOf(8), findViewById(R.id.split8));
        this.alq.put(Integer.valueOf(9), findViewById(R.id.split9));
        this.alq.put(Integer.valueOf(10), findViewById(R.id.split10));
        this.alq.put(Integer.valueOf(11), findViewById(R.id.split11));
        this.alq.put(Integer.valueOf(12), findViewById(R.id.split12));
        for (Integer intValue : this.alp.keySet()) {
            int index = intValue.intValue();
            if (!this.aln.containsKey(Integer.valueOf(index))) {
                ((TextView) this.alp.get(Integer.valueOf(index))).setVisibility(8);
                ((View) this.alq.get(Integer.valueOf(index))).setVisibility(8);
            }
        }
        this.akN = this.mContext.getResources().getDrawable(R.drawable.ic_common_check);
        this.akN.setBounds(0, 0, this.akN.getMinimumWidth(), this.akN.getMinimumHeight());
        an(this.alm);
    }

    public void showDialog() {
    }

    public void dismiss() {
        super.dismiss();
    }

    private void AQ() {
        if (this.mContext != null && !this.mContext.isFinishing()) {
            this.akY.dismiss();
        }
    }

    private void hH(int index) {
        TextView view;
        for (Integer intValue : this.alp.keySet()) {
            int key = intValue.intValue();
            view = (TextView) this.alp.get(Integer.valueOf(key));
            view.setCompoundDrawables(null, null, null, null);
            view.setText((String) this.alo.get(Integer.valueOf(key)));
        }
        view = (TextView) this.alp.get(Integer.valueOf(index));
        if (view != null) {
            view.setCompoundDrawables(null, null, this.akN, null);
        }
    }

    private void an(Object val) {
        for (Integer intValue : this.alp.keySet()) {
            int key = intValue.intValue();
            TextView view = (TextView) this.alp.get(Integer.valueOf(key));
            view.setCompoundDrawables(null, null, null, null);
            view.setText((String) this.alo.get(Integer.valueOf(key)));
        }
        for (Integer intValue2 : this.aln.keySet()) {
            int index = intValue2.intValue();
            if (this.aln.get(Integer.valueOf(index)).equals(val)) {
                view = (TextView) this.alp.get(Integer.valueOf(index));
                if (view != null) {
                    view.setCompoundDrawables(null, null, this.akN, null);
                    return;
                }
                return;
            }
        }
    }
}
