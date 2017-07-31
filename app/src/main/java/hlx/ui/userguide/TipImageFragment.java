package hlx.ui.userguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.t;

public class TipImageFragment extends Fragment {
    private static final String cgt = "ARG_IMAGE";
    private String cgu;

    public static TipImageFragment hO(String imgUrl) {
        TipImageFragment fragment = new TipImageFragment();
        Bundle args = new Bundle();
        args.putString(cgt, imgUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.cgu = getArguments().getString(cgt);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PaintView imageView = new PaintView(getActivity());
        imageView.setScaleType(ScaleType.CENTER_CROP);
        t.b(imageView, this.cgu, 0.0f);
        return imageView;
    }
}
