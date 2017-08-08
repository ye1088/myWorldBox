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

public class ReloadFragment extends AbsStatusFragment<ReloadStatement> {
    private OnClickListener mSelfListener = new OnClickListener() {
        public void onClick(View v) {
            if (!UtilsNetwork.isNetworkStrictlyAvailable(ReloadFragment.this.getActivity())) {
                ReloadFragment.this.checkNetToast();
            } else if (ReloadFragment.this.mLoadListener != null) {
                ReloadFragment.this.mLoadListener.onClick(v);
            }
        }
    };

    public static ReloadFragment newInstance() {
        return newInstance(null);
    }

    public static ReloadFragment newInstance(ReloadStatement statement) {
        Bundle bundle = new Bundle();
        if (statement == null) {
            statement = ReloadStatement.generateDefault();
        }
        bundle.putParcelable("STATEMENT", statement);
        ReloadFragment fragment = new ReloadFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        int i = 0;
        if (((ReloadStatement) this.mStatement).layoutId <= 0) {
            int i2;
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_clickable_state, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.net_err_icon);
            TextView subtitle = (TextView) view.findViewById(R.id.error_text);
            TextView button = (TextView) view.findViewById(R.id.reload_text);
            if (((ReloadStatement) this.mStatement).generalImg > 0) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            imageView.setVisibility(i2);
            if (((ReloadStatement) this.mStatement).generalSubtitle > 0) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            subtitle.setVisibility(i2);
            if (((ReloadStatement) this.mStatement).buttonText <= 0) {
                i = 8;
            }
            button.setVisibility(i);
            imageView.setImageDrawable(getResources().getDrawable(((ReloadStatement) this.mStatement).generalImg));
            if (((ReloadStatement) this.mStatement).background > 0) {
                view.setBackgroundResource(((ReloadStatement) this.mStatement).background);
            }
            if (((ReloadStatement) this.mStatement).gerneralImgSize != null) {
                imageView.getLayoutParams().width = ((ReloadStatement) this.mStatement).gerneralImgSize.width;
                imageView.getLayoutParams().height = ((ReloadStatement) this.mStatement).gerneralImgSize.height;
            }
            if (((ReloadStatement) this.mStatement).generalSubtitleSize > 0) {
                subtitle.setTextSize((float) ((ReloadStatement) this.mStatement).generalSubtitleSize);
            }
            if (((ReloadStatement) this.mStatement).generalSubtitleColor > 0) {
                subtitle.setTextColor(getResources().getColor(((ReloadStatement) this.mStatement).generalSubtitleColor));
            }
            if (((ReloadStatement) this.mStatement).generalSubtitle > 0) {
                subtitle.setText(getResources().getString(((ReloadStatement) this.mStatement).generalSubtitle));
            }
            if (((ReloadStatement) this.mStatement).generalSubtitleBackground > 0) {
                subtitle.setBackgroundDrawable(getResources().getDrawable(((ReloadStatement) this.mStatement).generalSubtitleBackground));
            }
            if (((ReloadStatement) this.mStatement).buttonTextSize > 0) {
                button.setTextSize((float) ((ReloadStatement) this.mStatement).buttonTextSize);
            }
            if (((ReloadStatement) this.mStatement).buttonTextColor > 0) {
                button.setTextColor(getResources().getColor(((ReloadStatement) this.mStatement).buttonTextColor));
            }
            if (((ReloadStatement) this.mStatement).buttonText > 0) {
                button.setText(getResources().getString(((ReloadStatement) this.mStatement).buttonText));
            }
            if (((ReloadStatement) this.mStatement).buttonBackground > 0) {
                button.setBackgroundDrawable(getResources().getDrawable(((ReloadStatement) this.mStatement).buttonBackground));
            }
            if (button.getVisibility() == 0) {
                button.setOnClickListener(this.mSelfListener);
            } else {
                view.setOnClickListener(this.mSelfListener);
            }
        } else {
            view = LayoutInflater.from(getActivity()).inflate(((ReloadStatement) this.mStatement).layoutId, container, false);
            View clickZone = view.findViewById(((ReloadStatement) this.mStatement).clickableId);
            if (clickZone != null) {
                clickZone.setOnClickListener(this.mSelfListener);
            }
        }
        return view;
    }
}
