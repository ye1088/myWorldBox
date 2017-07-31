package com.huluxia.ui.bbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.huluxia.bbs.b.i;

public class VoteTitle extends LinearLayout {
    public VoteTitle(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_vote_top, this);
    }
}
