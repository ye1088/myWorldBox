package com.MCWorld.ui.itemadapter.category;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.j;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsNetwork;
import com.MCWorld.framework.base.widget.dialog.DialogManager;
import com.MCWorld.http.bbs.category.e;
import com.MCWorld.t;
import com.MCWorld.ui.bbs.CategoryVoteActivity;
import java.util.ArrayList;
import java.util.Locale;

public class CategoryVoteItemAdapter extends ArrayAdapter {
    private CategoryVoteActivity aSA;
    private final DialogManager aSB = new DialogManager(this.aSA);

    private class a implements OnClickListener {
        private TopicCategory aSC = null;
        final /* synthetic */ CategoryVoteItemAdapter aSD;
        private View mView;

        public a(CategoryVoteItemAdapter categoryVoteItemAdapter, View view, TopicCategory categroy) {
            this.aSD = categoryVoteItemAdapter;
            this.mView = view;
            this.aSC = categroy;
        }

        public void onClick(View v) {
            if (!UtilsNetwork.isNetworkConnected(this.aSD.aSA)) {
                t.n(this.aSD.aSA, "没有网络,请检查网络设置。");
            } else if (!j.ep().ey()) {
                this.aSD.aSB.showOkCancelDialog(hlx.data.localstore.a.bKA_TIPS, (CharSequence) "登录后才能进行投票操作,请点击登录。", (CharSequence) "登录", hlx.data.localstore.a.bKB_bt_cancel, true, new 1(this));
            } else if (this.aSC == null) {
                HLog.error("VoteOnClickListener.onClick", "mCategroy is null", new Object[0]);
            } else {
                TextView vote = (TextView) this.mView.findViewById(g.vote);
                if (this.aSC.getIsVoted() == 0) {
                    this.aSD.aSA.cn(true);
                    e categoryVoteRequest = new e();
                    categoryVoteRequest.v(this.aSC.getCategoryID());
                    categoryVoteRequest.execute();
                    categoryVoteRequest.a(new 2(this, vote));
                }
            }
        }
    }

    public CategoryVoteItemAdapter(CategoryVoteActivity context, ArrayList<Object> objects) {
        super(context, i.item_category_vote_activity, g.title, objects);
        this.aSA = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TopicCategory data = (TopicCategory) getItem(position);
        View view = super.getView(position, convertView, parent);
        TextView mTitle = (TextView) view.findViewById(g.title);
        PaintView mImageView = (PaintView) view.findViewById(g.image);
        TextView mDescription = (TextView) view.findViewById(g.description);
        ProgressBar mProgressBar = (ProgressBar) view.findViewById(g.progress);
        Button mVote = (Button) view.findViewById(g.vote);
        TextView mVoteCount = (TextView) view.findViewById(g.vote_count);
        if (data != null) {
            if (data.getIsVoted() == 1) {
                mVote.setText(m.voted_btn);
                mVote.setEnabled(false);
            } else {
                mVote.setText(m.vote_btn);
                mVote.setEnabled(true);
            }
            mTitle.setText(data.getTitle());
            int total = data.totle == 0 ? 310000 : data.totle;
            mProgressBar.setMax(total);
            float percent = ((float) data.getVoteCount()) / ((float) total);
            if (((double) percent) >= 0.02d || percent <= 0.0f) {
                mProgressBar.setProgress((int) data.getVoteCount());
            } else {
                mProgressBar.setProgress((int) (((double) total) * 0.02d));
            }
            mVoteCount.setText(String.format(Locale.getDefault(), "%d/%d", new Object[]{Integer.valueOf((int) data.getVoteCount()), Integer.valueOf(total)}));
            t.b(mImageView, data.getIcon(), (float) t.dipToPx(this.aSA, 5));
            mDescription.setText(data.getDescription());
            mVote.setOnClickListener(new a(this, view, data));
        } else {
            HLog.error("CategoryVoteItemAdapter.getView", "TopicCategory  data is null", new Object[0]);
        }
        return view;
    }
}
