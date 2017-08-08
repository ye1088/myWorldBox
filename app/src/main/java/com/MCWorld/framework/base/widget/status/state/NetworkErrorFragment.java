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

public class NetworkErrorFragment extends AbsStatusFragment<NetworkErrorStatement> {
    private OnClickListener mSelfListener = new OnClickListener() {
        public void onClick(View v) {
            if (!UtilsNetwork.isNetworkStrictlyAvailable(NetworkErrorFragment.this.getActivity())) {
                NetworkErrorFragment.this.checkNetToast();
            } else if (NetworkErrorFragment.this.mLoadListener != null) {
                NetworkErrorFragment.this.mLoadListener.onClick(v);
            }
        }
    };

    public static NetworkErrorFragment newInstance() {
        return newInstance(null);
    }

    public static NetworkErrorFragment newInstance(NetworkErrorStatement statement) {
        Bundle bundle = new Bundle();
        if (statement == null) {
            statement = NetworkErrorStatement.generateDefault();
        }
        bundle.putParcelable("STATEMENT", statement);
        NetworkErrorFragment fragment = new NetworkErrorFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        int i = 0;
        if (((NetworkErrorStatement) this.mStatement).layoutId <= 0) {
            int i2;
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_clickable_state, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.net_err_icon);
            TextView subtitle = (TextView) view.findViewById(R.id.error_text);
            TextView button = (TextView) view.findViewById(R.id.reload_text);
            if (((NetworkErrorStatement) this.mStatement).generalImg > 0) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            imageView.setVisibility(i2);
            if (((NetworkErrorStatement) this.mStatement).generalSubtitle > 0) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            subtitle.setVisibility(i2);
            if (((NetworkErrorStatement) this.mStatement).buttonText <= 0) {
                i = 8;
            }
            button.setVisibility(i);
            imageView.setImageDrawable(getResources().getDrawable(((NetworkErrorStatement) this.mStatement).generalImg));
            if (((NetworkErrorStatement) this.mStatement).gerneralImgSize != null) {
                imageView.getLayoutParams().width = ((NetworkErrorStatement) this.mStatement).gerneralImgSize.width;
                imageView.getLayoutParams().height = ((NetworkErrorStatement) this.mStatement).gerneralImgSize.height;
            }
            if (((NetworkErrorStatement) this.mStatement).generalSubtitleSize > 0) {
                subtitle.setTextSize((float) ((NetworkErrorStatement) this.mStatement).generalSubtitleSize);
            }
            if (((NetworkErrorStatement) this.mStatement).generalSubtitleColor > 0) {
                subtitle.setTextColor(getResources().getColor(((NetworkErrorStatement) this.mStatement).generalSubtitleColor));
            }
            if (((NetworkErrorStatement) this.mStatement).generalSubtitle > 0) {
                subtitle.setText(getResources().getString(((NetworkErrorStatement) this.mStatement).generalSubtitle));
            }
            if (((NetworkErrorStatement) this.mStatement).generalSubtitleBackground > 0) {
                subtitle.setBackgroundDrawable(getResources().getDrawable(((NetworkErrorStatement) this.mStatement).generalSubtitleBackground));
            }
            if (((NetworkErrorStatement) this.mStatement).buttonTextSize > 0) {
                button.setTextSize((float) ((NetworkErrorStatement) this.mStatement).buttonTextSize);
            }
            if (((NetworkErrorStatement) this.mStatement).buttonTextColor > 0) {
                button.setTextColor(getResources().getColor(((NetworkErrorStatement) this.mStatement).buttonTextColor));
            }
            if (((NetworkErrorStatement) this.mStatement).buttonText > 0) {
                button.setText(getResources().getString(((NetworkErrorStatement) this.mStatement).buttonText));
            }
            if (((NetworkErrorStatement) this.mStatement).buttonBackground > 0) {
                button.setBackgroundDrawable(getResources().getDrawable(((NetworkErrorStatement) this.mStatement).buttonBackground));
            }
            if (button.getVisibility() == 0) {
                button.setOnClickListener(this.mSelfListener);
            } else {
                view.setOnClickListener(this.mSelfListener);
            }
        } else {
            view = LayoutInflater.from(getActivity()).inflate(((NetworkErrorStatement) this.mStatement).layoutId, container, false);
            View clickZone = view.findViewById(((NetworkErrorStatement) this.mStatement).clickableId);
            if (clickZone != null) {
                clickZone.setOnClickListener(this.mSelfListener);
            }
        }
        return view;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
