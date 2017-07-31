package com.huluxia.widget.listview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.huluxia.bbs.b.f;

public class MessageListView extends ListView implements OnScrollListener {
    private ImageView bzV;
    private int bzW = 0;
    private boolean bzX = false;
    private c bzY = null;
    private a bzZ = null;

    private static class a implements Runnable {
        private ListView aZh = null;

        public a(ListView listView) {
            this.aZh = listView;
        }

        public void run() {
            this.aZh.setSelection(this.aZh.getCount() - 1);
            Log.i("LastSelect onLayout", Integer.toString(this.aZh.getCount() - 1));
        }
    }

    private class b extends AsyncTask<String, Integer, String> {
        final /* synthetic */ MessageListView bAa;

        private b(MessageListView messageListView) {
            this.bAa = messageListView;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            this.bAa.OY();
            this.bAa.bzX = false;
        }

        protected String c(String... params) {
            SystemClock.sleep(800);
            return null;
        }

        protected void ce(String result) {
            this.bAa.bzX = true;
            this.bAa.OX();
            this.bAa.bzY.Pb();
        }
    }

    public interface c {
        void Pb();
    }

    public MessageListView(Context context) {
        super(context);
        init(context);
    }

    public MessageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void OX() {
        this.bzV.setPadding(0, this.bzW * -1, 0, 0);
    }

    public void OY() {
        this.bzV.setPadding(0, this.bzW, 0, 15);
    }

    private void init(Context context) {
        this.bzZ = new a(this);
        setDividerHeight(0);
        this.bzV = new ImageView(context);
        AnimationDrawable animationDrawable = (AnimationDrawable) context.getResources().getDrawable(f.rotate_progress_big);
        this.bzV.setImageDrawable(animationDrawable);
        animationDrawable.start();
        w(this.bzV);
        this.bzW = this.bzV.getMeasuredHeight() + 15;
        OX();
        addHeaderView(this.bzV, null, false);
        setOnScrollListener(this);
        setAdapter(null);
    }

    private void w(View child) {
        int childHeightSpec;
        LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public boolean OZ() {
        return this.bzX;
    }

    public void setMoreData(boolean isMoreData) {
        this.bzX = isMoreData;
    }

    public c getOnLoadMoreListener() {
        return this.bzY;
    }

    public void setOnLoadMoreListener(c onLoadMoreListener) {
        this.bzY = onLoadMoreListener;
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0 && this.bzX) {
            new b().execute(new String[]{""});
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            Pa();
        }
    }

    public void Pa() {
        post(this.bzZ);
    }
}
