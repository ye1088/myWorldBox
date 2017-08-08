package com.MCWorld.framework.base.widget.status.state;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.utils.UtilsNetwork;
import com.MCWorld.framework.base.widget.status.AbsStatusFragment;

public class NoDataFragment extends AbsStatusFragment<NoDataStatement> {
    private OnClickListener mSelfListener = new OnClickListener() {
        public void onClick(View v) {
            if (!UtilsNetwork.isNetworkStrictlyAvailable(NoDataFragment.this.getActivity())) {
                NoDataFragment.this.checkNetToast();
            } else if (NoDataFragment.this.mNoDataListener != null) {
                NoDataFragment.this.mNoDataListener.onClick(v);
            }
        }
    };

    public static NoDataFragment newInstance() {
        return newInstance(null);
    }

    public static NoDataFragment newInstance(NoDataStatement statement) {
        Bundle bundle = new Bundle();
        if (statement == null) {
            statement = NoDataStatement.generateDefault();
        }
        bundle.putParcelable("STATEMENT", statement);
        NoDataFragment fragment = new NoDataFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        int i = 0;
        if (((NoDataStatement) this.mStatement).layoutId <= 0) {
            int i2;
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_clickable_state, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.net_err_icon);
            TextView subtitle = (TextView) view.findViewById(R.id.error_text);
            TextView button = (TextView) view.findViewById(R.id.reload_text);
            if (((NoDataStatement) this.mStatement).generalImg > 0) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            imageView.setVisibility(i2);
            if (((NoDataStatement) this.mStatement).generalSubtitle > 0) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            subtitle.setVisibility(i2);
            if (((NoDataStatement) this.mStatement).buttonText <= 0) {
                i = 8;
            }
            button.setVisibility(i);
            imageView.setImageDrawable(getResources().getDrawable(((NoDataStatement) this.mStatement).generalImg));
            if (((NoDataStatement) this.mStatement).gerneralImgSize != null) {
                imageView.getLayoutParams().width = ((NoDataStatement) this.mStatement).gerneralImgSize.width;
                imageView.getLayoutParams().height = ((NoDataStatement) this.mStatement).gerneralImgSize.height;
            }
            if (((NoDataStatement) this.mStatement).generalSubtitleSize > 0) {
                subtitle.setTextSize((float) ((NoDataStatement) this.mStatement).generalSubtitleSize);
            }
            if (((NoDataStatement) this.mStatement).generalSubtitleColor > 0) {
                subtitle.setTextColor(getResources().getColor(((NoDataStatement) this.mStatement).generalSubtitleColor));
            }
            if (((NoDataStatement) this.mStatement).generalSubtitle > 0) {
                subtitle.setText(getResources().getString(((NoDataStatement) this.mStatement).generalSubtitle));
            }
            if (((NoDataStatement) this.mStatement).generalSubtitleBackground > 0) {
                subtitle.setBackgroundDrawable(getResources().getDrawable(((NoDataStatement) this.mStatement).generalSubtitleBackground));
            }
            if (((NoDataStatement) this.mStatement).buttonTextSize > 0) {
                button.setTextSize((float) ((NoDataStatement) this.mStatement).buttonTextSize);
            }
            if (((NoDataStatement) this.mStatement).buttonTextColor > 0) {
                button.setTextColor(getResources().getColor(((NoDataStatement) this.mStatement).buttonTextColor));
            }
            if (((NoDataStatement) this.mStatement).buttonText > 0) {
                button.setText(getResources().getString(((NoDataStatement) this.mStatement).buttonText));
            }
            if (((NoDataStatement) this.mStatement).buttonBackground > 0) {
                button.setBackgroundDrawable(getResources().getDrawable(((NoDataStatement) this.mStatement).buttonBackground));
            }
            if (button.getVisibility() == 0) {
                button.setOnClickListener(this.mSelfListener);
            } else {
                view.setOnClickListener(this.mSelfListener);
            }
        } else {
            view = LayoutInflater.from(getActivity()).inflate(((NoDataStatement) this.mStatement).layoutId, container, false);
            View clickZone = view.findViewById(((NoDataStatement) this.mStatement).clickableId);
            if (clickZone != null) {
                clickZone.setOnClickListener(this.mSelfListener);
            }
        }
        return view;
    }
}
