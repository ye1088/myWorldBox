package com.MCWorld.ui.bbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.MCWorld.bbs.b.i;

public class VoteTitle extends LinearLayout {
    public VoteTitle(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_vote_top, this);
    }
}
