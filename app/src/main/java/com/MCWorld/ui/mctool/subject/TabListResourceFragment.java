package com.MCWorld.ui.mctool.subject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.MCWorld.framework.base.widget.pager.PagerFragment;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.mctool.ResourceCommonListLayout;
import com.MCWorld.ui.mctool.ResourceCommonListLayout.a;

public class TabListResourceFragment extends PagerFragment {
    private ResourceCommonListLayout bcU;
    private DownAdapter bcV = null;
    private a bcW = null;
    private Activity mContext;
    private String title;

    public static TabListResourceFragment aA(int tagId, int pos) {
        TabListResourceFragment fragment = new TabListResourceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tagId", tagId);
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void a(DownAdapter da, a rp) {
        this.bcV = da;
        this.bcW = rp;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        int tagId = getArguments().getInt("tagId");
        int pos = getArguments().getInt("pos");
        if (this.mContext instanceof CommonListActivity) {
            CommonListActivity ca = this.mContext;
            this.bcV = ca.az(tagId, pos);
            this.bcW = ca.kV(tagId);
            this.bcU = new ResourceCommonListLayout(getActivity(), this.bcV, this.bcW);
            this.bcU.ce(true);
        }
        return this.bcU;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.bcU.ce(false);
    }

    public void onResume() {
        super.onResume();
    }
}
