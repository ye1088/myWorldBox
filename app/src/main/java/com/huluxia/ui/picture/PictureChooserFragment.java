package com.huluxia.ui.picture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.utils.UtilsCamera;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.module.picture.PictureInfo;
import com.huluxia.module.picture.a;
import com.huluxia.ui.base.BaseThemeFragment;
import com.huluxia.ui.itemadapter.picture.PictureAdapter;
import com.huluxia.utils.ad;
import com.huluxia.utils.at;
import java.util.ArrayList;

public class PictureChooserFragment extends BaseThemeFragment {
    private static final String bdM = "ARG_CHOICE_MODE";
    private static final String bdN = "ARG_MAX_SELECTED_COUNT";
    private static final String bdO = "ARG_SHOW_CAMERA";
    public static final int bdP = 0;
    public static final int bdQ = 1;
    private static final String bdR = "ARG_CURRENT_SELECTION";
    private PopupWindow ZY;
    private boolean aUZ;
    private ArrayList<com.huluxia.module.picture.b> aVc;
    private b aVd;
    private ViewAnimator aaf;
    private int bdS;
    private GridView bdT;
    private PictureAdapter bdU;
    private long bdV = -1;
    private TextView bdW;
    private a bdX;
    private int bdY = 0;
    private Runnable bdZ = new 9(this);
    private int mChoiceMode;

    public interface b {
        void IS();

        void IT();

        void b(com.huluxia.module.picture.b bVar);

        void c(com.huluxia.module.picture.b bVar);

        void d(com.huluxia.module.picture.b bVar);

        void kX(int i);
    }

    public static PictureChooserFragment a(int choiceMode, int maxSelectedCount, boolean showCamera, ArrayList<com.huluxia.module.picture.b> currentSelection) {
        PictureChooserFragment fragment = new PictureChooserFragment();
        Bundle args = new Bundle();
        args.putInt(bdM, choiceMode);
        args.putInt(bdN, maxSelectedCount);
        args.putBoolean(bdO, showCamera);
        args.putParcelableArrayList(bdR, currentSelection);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            Bundle args = getArguments();
            this.mChoiceMode = args.getInt(bdM);
            this.bdS = args.getInt(bdN);
            this.aUZ = args.getBoolean(bdO);
            this.aVc = args.getParcelableArrayList(bdR);
        }
        this.bdY = (at.getScreenWidth(getActivity()) - (at.dipToPx(getActivity(), 5) * 4)) / 3;
    }

    public void onDestroy() {
        super.onDestroy();
        AsyncTaskCenter.getInstance().cancel(this.bdZ);
        this.bdZ = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(i.fragment_picture_chooser, container, false);
        this.bdT = (GridView) view.findViewById(g.grid);
        this.bdW = (TextView) view.findViewById(g.text_spinner);
        this.aaf = (ViewAnimator) view.findViewById(g.animator);
        view.findViewById(g.btn_back).setOnClickListener(new 1(this));
        IW();
        return view;
    }

    public void onActivityCreated(@z Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IZ();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CharSequence mShotPath = UtilsCamera.onPickResult(resultCode, requestCode, data, getActivity());
        if (!ad.empty(mShotPath) && UtilsFile.isExist(mShotPath)) {
            com.huluxia.module.picture.b unit = new com.huluxia.module.picture.b();
            unit.id = (long) mShotPath.hashCode();
            unit.localPath = mShotPath;
            this.bdU.a(unit);
            if (this.aVc.size() < this.bdS) {
                this.aVc.add(unit);
                if (this.aVd != null) {
                    this.aVd.b(unit);
                }
            } else if (this.aVd != null) {
                this.aVd.kX(this.bdS);
            }
        }
    }

    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            this.aVd = (b) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement PictureSelectorListener");
        }
    }

    public void onDetach() {
        super.onDetach();
        this.aVd = null;
    }

    private void IW() {
        boolean z = true;
        this.bdU = new PictureAdapter(getActivity(), this.aUZ, this.bdS, this.aVc);
        PictureAdapter pictureAdapter = this.bdU;
        if (this.mChoiceMode != 1) {
            z = false;
        }
        pictureAdapter.cL(z);
        this.bdU.a(this.aVd);
        this.bdT.setAdapter(this.bdU);
        this.bdT.setOnItemClickListener(new 2(this));
        this.bdT.getViewTreeObserver().addOnGlobalLayoutListener(new 3(this));
        this.bdT.setOnScrollListener(new 4(this));
    }

    private void IX() {
        ListView list = (ListView) LayoutInflater.from(getActivity()).inflate(i.include_popup_list, null);
        this.bdX = new a(this, getActivity());
        list.setAdapter(this.bdX);
        this.bdW.setText(m.all);
        this.ZY = new PopupWindow(list, -1, at.dipToPx(getActivity(), com.huluxia.video.recorder.b.bpd));
        this.ZY.setOutsideTouchable(true);
        this.ZY.setFocusable(true);
        this.ZY.setBackgroundDrawable(new ColorDrawable(0));
        this.bdW.setOnTouchListener(new 5(this));
        list.setOnItemClickListener(new 6(this));
    }

    private void IY() {
        UtilsCamera.fromCamera(getActivity());
    }

    private void IZ() {
        if (PictureInfo.getInstance().isEmpty()) {
            if (this.aVd != null) {
                this.aVd.IS();
            }
            showLoading();
            this.bdT.requestFocus();
            this.bdT.setSelection(0);
            AsyncTaskCenter.getInstance().execute(this.bdZ, new 7(this));
            return;
        }
        Ja();
    }

    private void showLoading() {
        this.aaf.setDisplayedChild(0);
    }

    private void Ja() {
        IX();
        Jb();
        this.aaf.setDisplayedChild(1);
        if (this.aVd != null) {
            this.aVd.IT();
        }
    }

    public void Jb() {
        Jc();
        this.bdU.c(PictureInfo.getInstance().allPictures, true);
        this.bdV = -1;
    }

    public void a(a bucket) {
        Jc();
        if (bucket != null && bucket.bucketId != this.bdV) {
            this.bdV = bucket.bucketId;
            this.bdU.c(bucket.pictures, true);
        }
    }

    private void Jc() {
        this.bdT.post(new 8(this));
    }

    public ArrayList<com.huluxia.module.picture.b> Jd() {
        return this.aVc;
    }

    public void Je() {
        this.bdU.notifyDataSetChanged();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(g.title_bar, c.backgroundTitleBar).ab(g.btn_back, c.drawableTitleBack, 1).aZ(g.btn_back, c.backgroundTitleBarButton);
    }
}
