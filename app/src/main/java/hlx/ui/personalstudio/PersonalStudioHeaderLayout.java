package hlx.ui.personalstudio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.MCWorld.data.profile.e;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.t;
import com.MCWorld.utils.y;
import com.MCWorld.widget.listview.GridViewNotScroll;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.c;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import hlx.module.resources.a;
import java.util.List;

public class PersonalStudioHeaderLayout extends LinearLayout implements c {
    private PaintView aKq;
    private PaintView bio;
    private EmojiTextView cdE;
    private TextView cdF;
    private TextView cdG;
    private TextView cdH;
    private TextView cdI;
    private TextView cdJ;
    private TextView cdK;
    private TextView cdL;
    private EmojiTextView cdM;
    private TextView cdN;
    private GridViewNotScroll cdO;
    private a cdP;
    private com.MCWorld.data.profile.c cdQ;
    private Context mContext;

    public PersonalStudioHeaderLayout(Context context) {
        this(context, null);
    }

    public PersonalStudioHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOrientation(1);
        LayoutInflater.from(context).inflate(R.layout.header_view_personal_studio, this);
        initView();
        EQ();
    }

    private void initView() {
        this.cdE = (EmojiTextView) findViewById(R.id.tv_title);
        this.cdL = (TextView) findViewById(R.id.tv_apply_status);
        this.cdM = (EmojiTextView) findViewById(R.id.tv_introduce);
        this.cdO = (GridViewNotScroll) findViewById(R.id.gv_member_list);
        this.bio = (PaintView) findViewById(R.id.niv_cover);
        this.aKq = (PaintView) findViewById(R.id.avatar);
        this.cdF = (TextView) findViewById(R.id.tv_studio_hot);
        this.cdG = (TextView) findViewById(R.id.tv_qq_group_number);
        this.cdH = (TextView) findViewById(R.id.tv_studio_tag1);
        this.cdI = (TextView) findViewById(R.id.tv_studio_tag2);
        this.cdJ = (TextView) findViewById(R.id.tv_studio_tag3);
        this.cdK = (TextView) findViewById(R.id.tv_studio_tag4);
        this.cdN = (TextView) findViewById(R.id.tv_studio_member_count);
    }

    private void EQ() {
        this.cdP = new a(this, this.mContext);
        this.cdO.setAdapter(this.cdP);
        this.cdN.setOnClickListener(new 1(this));
    }

    public void setmStudio(com.MCWorld.data.profile.c mStudio) {
        this.cdQ = mStudio;
        Vn();
    }

    private void Vn() {
        if (this.cdQ != null && this.cdQ.studioInfo != null) {
            if (UtilsFunction.empty(this.cdQ.studioInfo.coverImg)) {
                this.bio.setVisibility(8);
            } else {
                this.bio.setVisibility(0);
                t.b(this.bio, this.cdQ.studioInfo.coverImg, 0.0f);
            }
            t.b(this.aKq, this.cdQ.studioInfo.icon, (float) t.dipToPx(this.mContext, 7));
            this.cdE.setText(this.cdQ.studioInfo.name);
            this.cdM.setText(this.cdQ.studioInfo.description);
            this.cdF.setText(this.cdQ.studioInfo.integral + "");
            this.cdG.setText(this.cdQ.studioInfo.qqgroup);
            if (UtilsFunction.empty(this.cdQ.cates)) {
                this.cdH.setVisibility(4);
                this.cdI.setVisibility(4);
                this.cdJ.setVisibility(4);
                this.cdK.setVisibility(4);
            } else {
                TextView[] viewList = new TextView[]{this.cdH, this.cdI, this.cdJ, this.cdK};
                for (int i = 0; i < 4; i++) {
                    if (i < this.cdQ.cates.size()) {
                        String name = ((a) this.cdQ.cates.get(i)).catename;
                        viewList[i].setText(name);
                        viewList[i].setTextColor(y.C(this.mContext, name));
                        viewList[i].setBackgroundDrawable(y.K(this.mContext, name));
                        viewList[i].setVisibility(0);
                    } else {
                        viewList[i].setVisibility(4);
                    }
                }
            }
            this.cdN.setText(this.cdQ.studioInfo.count + "人");
        }
    }

    public TextView getTvApplyStatus() {
        return this.cdL;
    }

    public void setData(List<e.a> data) {
        this.cdP.setData(data);
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        k setter = new j(this.cdO);
        setter.a(this.cdP);
        builder.a(setter).a(this.cdE, 16842806).a(this.cdL, R.attr.studio_apply_status).j(this.cdL, R.attr.bg_studio_apply_enter).j(this.cdL, R.attr.bg_studio_applied).j(this.cdL, R.attr.bg_studio_apply_exit).ba(R.id.tv_introduce, 16842808).aY(R.id.split_1, R.attr.splitColor).aY(R.id.split_2, R.attr.splitColor).aY(R.id.split_3, R.attr.splitColor).c(this.aKq, R.attr.default_discover_pic).a(this.cdF, R.attr.studio_hot_text_color).a(this.cdF, R.attr.studio_hot_icon, 1).ba(R.id.tv_qq_group, 16842808).a(this.cdG, 16842808).ba(R.id.tv_studio_member, R.attr.studio_me_in_member_list).ba(R.id.tv_studio_member_count, 16843282).aY(R.id.tv_studio_member_count, R.attr.listSelector).ab(R.id.tv_studio_member_count, R.attr.choice_arrow, 2);
        return builder;
    }

    public void FG() {
        if (this.cdP != null) {
            this.cdP.notifyDataSetChanged();
        }
        if (this.cdQ != null && this.cdQ.studioInfo != null) {
            this.cdM.setText(this.cdQ.studioInfo.description);
        }
    }
}
