package com.MCWorld.framework.base.widget.status.state;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.framework.base.widget.status.AbsStatusFragment;

public class LoadingFragment extends AbsStatusFragment<LoadingStatement> {
    public static LoadingFragment newInstance() {
        return newInstance(null);
    }

    public static LoadingFragment newInstance(LoadingStatement statement) {
        Bundle bundle = new Bundle();
        if (statement == null) {
            statement = LoadingStatement.generateDefault();
        }
        bundle.putParcelable("STATEMENT", statement);
        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (((LoadingStatement) this.mStatement).layoutId > 0) {
            return LayoutInflater.from(getActivity()).inflate(((LoadingStatement) this.mStatement).layoutId, container, false);
        }
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_loading, container, false);
        if (((LoadingStatement) this.mStatement).generalImg <= 0) {
            ((LoadingStatement) this.mStatement).generalImg = R.drawable.common_loading3;
        }
        if (((LoadingStatement) this.mStatement).background > 0) {
            view.setBackgroundResource(((LoadingStatement) this.mStatement).background);
        }
        TextView textView = (TextView) view.findViewById(R.id.loading_text);
        textView.setVisibility(((LoadingStatement) this.mStatement).generalSubtitle > 0 ? 0 : 8);
        if (((LoadingStatement) this.mStatement).generalSubtitle > 0) {
            textView.setText(getString(((LoadingStatement) this.mStatement).generalSubtitle));
        }
        if (((LoadingStatement) this.mStatement).generalSubtitleBackground > 0) {
            textView.setBackgroundDrawable(getResources().getDrawable(((LoadingStatement) this.mStatement).generalSubtitleBackground));
        }
        if (((LoadingStatement) this.mStatement).generalSubtitleSize > 0) {
            textView.setTextSize((float) ((LoadingStatement) this.mStatement).generalSubtitleSize);
        }
        if (((LoadingStatement) this.mStatement).generalSubtitleColor > 0) {
            textView.setTextColor(getResources().getColor(((LoadingStatement) this.mStatement).generalSubtitleColor));
        }
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        if (((LoadingStatement) this.mStatement).generalImg <= 0) {
            return view;
        }
        progressBar.setIndeterminateDrawable(getResources().getDrawable(((LoadingStatement) this.mStatement).generalImg));
        if (((LoadingStatement) this.mStatement).gerneralImgSize == null) {
            return view;
        }
        progressBar.getLayoutParams().width = UtilsScreen.dipToPx(getActivity(), ((LoadingStatement) this.mStatement).gerneralImgSize.width);
        progressBar.getLayoutParams().height = UtilsScreen.dipToPx(getActivity(), ((LoadingStatement) this.mStatement).gerneralImgSize.height);
        return view;
    }
}
