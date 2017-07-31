package hlx.mcstorymode.storyselect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.HTApplication;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.R;
import com.huluxia.r;
import com.huluxia.service.i;
import com.huluxia.ui.base.BaseThemeFragment;
import com.huluxia.utils.UtilsFile;
import com.huluxia.widget.title.TitleBar;
import hlx.mcstorymode.e;
import hlx.mcstorymode.storyselect.StorySelectAdapter.a;
import hlx.utils.c;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StoryPageFragment extends BaseThemeFragment {
    private static final boolean DEBUG = false;
    private static final String TAG = "StoryPageFragment";
    private OnItemClickListener Pz = new 3(this);
    private List<a> aab;
    private TitleBar bPX;
    private MsgtipReciver bXm;
    private ClearMsgReciver bXn;
    private ImageView bXo;
    private ListView bXp;
    private ImageView bXq;
    private TextView bXr;
    private StorySelectAdapter bXs;
    private int bXt;
    private a bXu;
    private int bXv = 0;
    private a bXw = new 5(this);
    private Activity mActivity;
    private OnClickListener mClickListener = new 2(this);

    public static StoryPageFragment TM() {
        return new StoryPageFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_fragment_story, null);
        this.bPX = (TitleBar) view.findViewById(R.id.title_bar);
        this.bPX.setVisibility(8);
        this.bPX.setLeftLayout(R.layout.home_left_btn);
        this.bPX.setRightLayout(R.layout.home_right_btn);
        this.bXm = new MsgtipReciver(this);
        this.bXn = new ClearMsgReciver(this);
        i.e(this.bXm);
        i.f(this.bXn);
        this.bPX.findViewById(R.id.img_msg).setOnClickListener(new 1(this));
        Sw();
        N(view);
        Fo();
        return view;
    }

    private void Fo() {
        for (a _tmp : this.aab) {
            _tmp.bXi = false;
        }
        new CheckGameDataAsnycTask(this).execute(new String[0]);
        this.bXv = 0;
        this.bXq.setBackgroundResource(R.mipmap.ico_story_page_trash);
        this.bXs.ni(this.bXv);
    }

    private void Sw() {
        this.mActivity = getActivity();
        this.bXs = new StorySelectAdapter(this.mActivity, this.bXw);
        this.aab = new ArrayList();
        this.aab.add(new a(1, true, 0, false, true, false));
        this.aab.add(new a(1, false, 2, false, true, false));
        this.aab.add(new a(1, false, 4, false, false, false));
        this.aab.add(new a(2, true, 0, false, true, false));
        this.aab.add(new a(2, false, 5, false, true, false));
        this.aab.add(new a(2, false, 3, false, true, false));
        this.aab.add(new a(2, false, 6, false, false, false));
        this.aab.add(new a(2, false, 7, false, false, false));
        this.aab.add(new a(3, true, 0, false, true, false));
        this.aab.add(new a(3, false, 1, false, false, false));
        this.bXs.setData(this.aab);
    }

    private void N(View view) {
        view.findViewById(R.id.ivStoryPageBack).setOnClickListener(this.mClickListener);
        this.bXo = (ImageView) view.findViewById(R.id.ivStoryFeedback);
        this.bXo.setOnClickListener(this.mClickListener);
        view.findViewById(R.id.ivStoryContribute).setOnClickListener(this.mClickListener);
        this.bXr = (TextView) view.findViewById(R.id.tvStoryResCheckTips);
        this.bXq = (ImageView) view.findViewById(R.id.ivStoryResDel);
        this.bXq.setOnClickListener(this.mClickListener);
        this.bXp = (ListView) view.findViewById(R.id.lvStoryChapterSelected);
        this.bXp.setAdapter(this.bXs);
        this.bXp.setOnItemClickListener(this.Pz);
    }

    private void a(a item) {
        this.bXu = item;
        bU(this.mActivity);
    }

    private void bU(Context context) {
        c dia = new c((Activity) context, new 4(this));
        dia.az(hlx.data.localstore.a.bKA, hlx.data.localstore.a.bKK);
        dia.aA(hlx.data.localstore.a.bKB, hlx.data.localstore.a.bKC);
        dia.showDialog();
    }

    private void b(a item) {
        UtilsFile.deleteFile(e.mY(item.bXg));
        switch (item.bXg) {
            case 1:
                TN();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                UtilsFile.deleteFile(e.ne(item.bXg));
                break;
        }
        item.bXi = false;
        this.bXs.update();
    }

    private void TN() {
        String _Path = e.ne(1);
        for (File file : new File(_Path).listFiles()) {
            if (!file.isDirectory()) {
                UtilsFile.deleteFile(_Path + File.separator + file.getName());
            }
        }
    }

    private void TO() {
        c mDownTipsDlg = new c(this.mActivity, new b(this, null));
        mDownTipsDlg.az(hlx.data.localstore.a.bKA, e.na(this.bXt));
        mDownTipsDlg.aA(hlx.data.localstore.a.bKB, hlx.data.localstore.a.bKC);
    }

    private void nh(int chapterIndex) {
        String _tmpTongji = null;
        switch (chapterIndex) {
            case 1:
                _tmpTongji = hlx.data.tongji.a.bOa;
                break;
            case 2:
                _tmpTongji = hlx.data.tongji.a.bOb;
                break;
            case 3:
                _tmpTongji = hlx.data.tongji.a.bOc;
                break;
            case 4:
                _tmpTongji = hlx.data.tongji.a.bOd;
                break;
            case 5:
                _tmpTongji = hlx.data.tongji.a.bOe;
                break;
            case 6:
                _tmpTongji = hlx.data.tongji.a.bOf;
                break;
            case 7:
                _tmpTongji = hlx.data.tongji.a.bOg;
                break;
        }
        if (_tmpTongji != null) {
            r.ck().K(_tmpTongji);
        }
    }

    protected void Fr() {
        MsgCounts msgCounts = HTApplication.bM();
        long allCount = msgCounts == null ? 0 : msgCounts.getAll();
        TextView tv = (TextView) this.bPX.findViewById(R.id.tv_msg);
        if (allCount > 0) {
            tv.setVisibility(0);
            if (allCount > 99) {
                tv.setText("99+");
                return;
            } else {
                tv.setText(String.valueOf(msgCounts.getAll()));
                return;
            }
        }
        tv.setVisibility(8);
    }

    protected void Fs() {
        ((TextView) this.bPX.findViewById(R.id.tv_msg)).setVisibility(8);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.bXm != null) {
            i.unregisterReceiver(this.bXm);
            this.bXm = null;
        }
        if (this.bXn != null) {
            i.unregisterReceiver(this.bXn);
            this.bXn = null;
        }
    }
}
