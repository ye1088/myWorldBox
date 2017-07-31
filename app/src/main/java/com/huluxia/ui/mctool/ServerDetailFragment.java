package com.huluxia.ui.mctool;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huluxia.data.server.a.a;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.l;
import com.huluxia.module.n;
import com.huluxia.ui.base.BaseThemeFragment;
import com.huluxia.utils.e;
import java.util.ArrayList;
import java.util.List;

public class ServerDetailFragment extends BaseThemeFragment {
    private static final String TAG = "ServerDetailFragment";
    private static final String bci = "info";
    private CallbackHandler bbZ = new 1(this);
    private TextView bce;
    private TextView bcf;
    private TextView bcg;
    private a bch;
    private PagerFragment bcj;
    private ViewPager mPager;

    public static class PhotoAdapter extends FragmentPagerAdapter {
        private Context context;
        private List<String> images = new ArrayList();

        public PhotoAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        public void setImages(List<String> images) {
            if (!e.empty(images)) {
                this.images.clear();
                this.images.addAll(images);
                notifyDataSetChanged();
            }
        }

        public Fragment getItem(int position) {
            return PhotoFragment.eH((String) this.images.get(position));
        }

        public int getCount() {
            return this.images.size();
        }
    }

    public static class PhotoFragment extends Fragment {
        private PaintView aOJ;
        private String url;

        public static PhotoFragment eH(String url) {
            PhotoFragment fragment = new PhotoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("image", url);
            fragment.setArguments(bundle);
            return fragment;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_image, container, false);
            this.aOJ = (PaintView) view;
            this.url = getArguments().getString("image");
            this.aOJ.setImageUrl(this.url, l.cb().getImageLoader());
            this.aOJ.setClickable(true);
            return view;
        }

        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("image", this.url);
        }
    }

    public static ServerDetailFragment b(a info) {
        ServerDetailFragment fragment = new ServerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", info);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(n.class, this.bbZ);
        this.bcj = this;
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.bbZ);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server_detail, container, false);
        this.mPager = (ViewPager) view.findViewById(R.id.map_viewpager);
        this.bce = (TextView) view.findViewById(R.id.map_desc);
        this.bcf = (TextView) view.findViewById(R.id.map_src);
        this.bcg = (TextView) view.findViewById(R.id.map_create_time);
        this.bch = (a) getArguments().getParcelable("info");
        return view;
    }
}
