package hlx.mcstorymode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.data.storymode.b;
import com.huluxia.data.storymode.c;
import com.huluxia.data.storymode.d;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.mctool.e;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.j;
import com.huluxia.widget.h;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tools.ant.taskdefs.optional.ejb.EjbJar.CMPVersion;

@SuppressLint({"HandlerLeak"})
public class MCStoryModeActivity extends Activity {
    private static final boolean DEBUG = false;
    private static final String TAG = "MCStoryModeActivity";
    private static float[] bWB = new float[]{0.25f, 0.5f, 1.0f, 2.0f, 3.0f};
    private static String[] bWC = new String[]{"0.25", "0.5", "1.0", CMPVersion.CMP2_0, "3.0"};
    private static final int bWc = 0;
    private static final int bWd = 1;
    private static final int bWe = 2;
    private static final int bWf = 3;
    private static final int bWg = 4;
    private static final int bWh = 1;
    private static final boolean bWi = false;
    protected Handler Vo = new Handler(this) {
        final /* synthetic */ MCStoryModeActivity bWF;

        {
            this.bWF = this$0;
        }

        public void handleMessage(Message msg) {
            if (this.bWF.aMn != null && !this.bWF.aMn.isFinishing()) {
                switch (msg.what) {
                    case 0:
                        if (!this.bWF.bWq) {
                            this.bWF.bWy = this.bWF.bWy + 1;
                            if (this.bWF.bWy == 5) {
                                e.Dk().iL(5);
                                this.bWF.bWy = 0;
                            }
                            this.bWF.Vo.sendEmptyMessageDelayed(0, 1000);
                            return;
                        }
                        return;
                    case 1:
                        this.bWF.bWt.setVisibility(8);
                        this.bWF.Tv();
                        this.bWF.Vo.sendEmptyMessageDelayed(0, 1000);
                        return;
                    case 2:
                        if (this.bWF.bWq) {
                            this.bWF.bWp = true;
                            return;
                        }
                        this.bWF.bWr = "原始标志";
                        this.bWF.bWo.setVisibility(8);
                        this.bWF.hk((String) msg.obj);
                        return;
                    case 3:
                        new LoadDataAsnycTask(this.bWF).execute(new String[0]);
                        return;
                    case 4:
                        if (this.bWF.bRk) {
                            this.bWF.bRk = false;
                            i.i(this.bWF.bWx);
                            String _tmpZipPath = e.mY(this.bWF.bWw);
                            String _tmpMCStoryDirPath = e.ne(this.bWF.bWw);
                            File _tmpFile = new File(_tmpMCStoryDirPath);
                            if (!_tmpFile.exists() || _tmpFile.isDirectory()) {
                                UtilsFile.mkdir(_tmpMCStoryDirPath);
                            } else {
                                j.deleteFile(_tmpMCStoryDirPath);
                                UtilsFile.mkdir(_tmpMCStoryDirPath);
                            }
                            h.NV().t("unzipStoryZip", _tmpZipPath, _tmpMCStoryDirPath);
                            return;
                        }
                        t.n(this.bWF.aMn, "抱歉，请切换版本重试.");
                        this.bWF.aMn.finish();
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private Activity aMn;
    private List<com.huluxia.data.storymode.a> aab;
    private boolean bRk = true;
    private TextView bWA;
    private hlx.utils.e bWD = new hlx.utils.e(this) {
        final /* synthetic */ MCStoryModeActivity bWF;

        {
            this.bWF = this$0;
        }

        public void c(View v) {
            boolean z = true;
            switch (v.getId()) {
                case R.id.tvStoryPause:
                    if (this.bWF.bWr == null || !this.bWF.bWr.equals("原始标志")) {
                        if (this.bWF.bWq) {
                            Message _tmpMessage = new Message();
                            _tmpMessage.what = 2;
                            _tmpMessage.obj = this.bWF.bWr;
                            if (this.bWF.bWp) {
                                this.bWF.Vo.sendMessage(_tmpMessage);
                            }
                            v.setBackgroundColor(-1996488705);
                            ((TextView) v).setText("暂停");
                        } else {
                            this.bWF.bWp = false;
                            v.setBackgroundColor(-2004318072);
                            ((TextView) v).setText("恢复");
                        }
                        MCStoryModeActivity mCStoryModeActivity = this.bWF;
                        if (this.bWF.bWq) {
                            z = false;
                        }
                        mCStoryModeActivity.bWq = z;
                        return;
                    }
                    return;
                case R.id.tvStorySpeed:
                    this.bWF.bWz = (this.bWF.bWz + 1) % MCStoryModeActivity.bWB.length;
                    this.bWF.bWA.setText("X" + MCStoryModeActivity.bWC[this.bWF.bWz]);
                    t.l(this.bWF.aMn, this.bWF.getString(R.string.story_mode_speed_changed, new Object[]{MCStoryModeActivity.bWC[this.bWF.bWz]}));
                    return;
                default:
                    return;
            }
        }
    };
    private hlx.mcstorymode.StoryListViewAdapter.a bWE = new hlx.mcstorymode.StoryListViewAdapter.a(this) {
        final /* synthetic */ MCStoryModeActivity bWF;

        {
            this.bWF = this$0;
        }

        public void k(String id, long time) {
            Message message = new Message();
            message.obj = id;
            message.what = 2;
            this.bWF.bWr = id;
            this.bWF.bWo.setVisibility(0);
            this.bWF.Tz();
            this.bWF.Vo.sendMessageDelayed(message, time);
        }
    };
    private c bWj;
    private Map<String, String> bWk;
    private MediaPlayer bWl;
    private StoryListViewAdapter bWm;
    private ListView bWn;
    private LinearLayout bWo;
    private boolean bWp = false;
    private boolean bWq = false;
    private String bWr = "原始标志";
    private TextView bWs;
    private LinearLayout bWt;
    private TextView bWu;
    private String bWv;
    private int bWw;
    private BroadcastReceiver bWx;
    private int bWy;
    private int bWz = 2;

    public class CheckDataAsnycTask extends AsyncTask<String, Integer, String> {
        final /* synthetic */ MCStoryModeActivity bWF;

        public CheckDataAsnycTask(MCStoryModeActivity this$0) {
            this.bWF = this$0;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
        }

        protected String c(String... params) {
            String retVal = "success";
            com.huluxia.mcinterface.h.t(this.bWF.aMn.getApplicationContext(), this.bWF.aMn.getApplicationContext().getFilesDir().getAbsolutePath());
            if (e.nf(this.bWF.bWw)) {
                this.bWF.Vo.sendEmptyMessage(3);
            } else {
                this.bWF.Vo.sendEmptyMessage(4);
            }
            return retVal;
        }

        protected void ce(String result) {
        }
    }

    public class LoadDataAsnycTask extends AsyncTask<String, Integer, String> {
        final /* synthetic */ MCStoryModeActivity bWF;

        public LoadDataAsnycTask(MCStoryModeActivity this$0) {
            this.bWF = this$0;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
        }

        protected String c(String... params) {
            String retVal = "success";
            d.TC().mX(this.bWF.bWw);
            this.bWF.bWj = d.TC().hl(e.nc(this.bWF.bWw));
            return retVal;
        }

        protected void ce(String result) {
            this.bWF.Vo.sendEmptyMessage(1);
        }
    }

    private class a extends BroadcastReceiver {
        final /* synthetic */ MCStoryModeActivity bWF;

        private a(MCStoryModeActivity mCStoryModeActivity) {
            this.bWF = mCStoryModeActivity;
        }

        public void onReceive(Context context, Intent intent) {
            String taskId = intent.getStringExtra("taskid");
            int status = intent.getIntExtra("success", 0);
            if (taskId != null && status == 1 && this.bWF.aMn != null && !this.bWF.aMn.isFinishing() && taskId.equals("unzipStoryZip")) {
                new CheckDataAsnycTask(this.bWF).execute(new String[0]);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_storymode);
        Ts();
        Tt();
        Tu();
    }

    private void Ts() {
        this.aMn = this;
        this.bWy = 0;
        this.bWw = getIntent().getIntExtra("storyIndex", 0);
        this.bWm = new StoryListViewAdapter(this.aMn, this.bWE, e.ne(this.bWw), true);
        this.bWx = new a();
    }

    private void Tt() {
        this.bWl = new MediaPlayer();
        try {
            AssetFileDescriptor _afd = this.aMn.getAssets().openFd("calm2.mp3");
            this.bWl = new MediaPlayer();
            this.bWl.setDataSource(_afd.getFileDescriptor(), _afd.getStartOffset(), _afd.getLength());
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT", new Object[0]);
        }
        try {
            this.bWl.prepare();
            this.bWl.start();
            this.bWl.setLooping(true);
        } catch (Exception e2) {
            HLog.verbose("Exception", "GET IT,%s", e2.toString());
        }
    }

    private void Tu() {
        this.bWn = (ListView) findViewById(R.id.lvStoryMode);
        this.bWn.setAdapter(this.bWm);
        this.bWo = (LinearLayout) findViewById(R.id.llyStoryModeLoading);
        this.bWo.setVisibility(8);
        this.bWt = (LinearLayout) findViewById(R.id.llyStoryMGameLoading);
        this.bWu = (TextView) findViewById(R.id.tvStoryMGameTips);
        this.bWu.setText(hlx.tips.a.E(this));
        this.bWu.setSelected(true);
        this.bWs = (TextView) findViewById(R.id.tvStoryPause);
        this.bWs.setOnClickListener(this.bWD);
        this.bWA = (TextView) findViewById(R.id.tvStorySpeed);
        this.bWA.setText(bWC[this.bWz]);
        this.bWA.setOnClickListener(this.bWD);
    }

    protected void onResume() {
        super.onResume();
        if (this.aab != null) {
            Message _tmpMessage = new Message();
            _tmpMessage.what = 2;
            _tmpMessage.obj = this.bWr;
            if (this.bWp && (this.bWr == null || !this.bWr.equals("原始标志"))) {
                this.Vo.sendMessage(_tmpMessage);
            } else if (this.bWp && this.bWr.equals("原始标志") && this.aab.size() > 0 && !((com.huluxia.data.storymode.a) this.aab.get(this.aab.size() - 1)).equals(c.bVA)) {
                this.Vo.sendMessage(_tmpMessage);
            }
            this.bWq = false;
            Tt();
            this.bWs.setBackgroundColor(143654911);
            this.bWs.setText("暂停");
            return;
        }
        new CheckDataAsnycTask(this).execute(new String[0]);
    }

    private void Tv() {
        Tw();
        Tx();
    }

    private void Tw() {
        d _archive = b.hj(e.nc(this.bWw));
        if (_archive != null) {
            this.aab = _archive.data;
            this.bWk = _archive.qM;
        }
    }

    private void Tx() {
        if (this.aab == null || this.aab.size() == 0) {
            this.aab = new ArrayList();
            this.bWk = new HashMap();
            this.bWm.a(this.aab, this.bWk);
            hk(this.bWj.qI);
        } else if (((com.huluxia.data.storymode.a) this.aab.get(this.aab.size() - 1)).mId.equals(this.bWj.qK)) {
            this.bWm.a(this.aab, this.bWk);
            Tz();
            Ty();
        } else if (this.bWj.qJ == null || ((b) ((com.huluxia.data.storymode.a) this.aab.get(this.aab.size() - 1)).qj.get(0)).qC == null || !((b) ((com.huluxia.data.storymode.a) this.aab.get(this.aab.size() - 1)).qj.get(0)).qC.equals(this.bWj.qJ)) {
            String _tmpId = ((com.huluxia.data.storymode.a) this.aab.get(this.aab.size() - 1)).mId;
            this.aab.remove(this.aab.size() - 1);
            this.bWm.a(this.aab, this.bWk);
            Tz();
            hk(_tmpId);
        } else {
            this.bWm.a(this.aab, this.bWk);
            Tz();
            dE(false);
        }
    }

    private void Ty() {
        hlx.utils.c dia = new hlx.utils.c(this.aMn, new hlx.utils.c.a(this) {
            final /* synthetic */ MCStoryModeActivity bWF;

            {
                this.bWF = this$0;
            }

            public void sM() {
                this.bWF.bWm.a(this.bWF.aab, this.bWF.bWk);
                this.bWF.Tz();
            }

            public void ra() {
            }

            public void rd() {
                this.bWF.aab.clear();
                this.bWF.bWk.clear();
                this.bWF.bWm.a(this.bWF.aab, this.bWF.bWk);
                this.bWF.bWm.notifyDataSetChanged();
                this.bWF.hk(this.bWF.bWj.qI);
            }

            public void rb() {
                this.bWF.bWm.a(this.bWF.aab, this.bWF.bWk);
                this.bWF.Tz();
            }
        });
        dia.az(hlx.data.localstore.a.bKA, "您已通关，是否重玩以体验更多剧情？");
        dia.aA("回顾过去", "重新体验");
        dia.showDialog();
    }

    private void dE(boolean isReaded) {
        hlx.utils.c dia = new hlx.utils.c(this.aMn, new hlx.utils.c.a(this) {
            final /* synthetic */ MCStoryModeActivity bWF;

            {
                this.bWF = this$0;
            }

            public void sM() {
                this.bWF.bWm.a(this.bWF.aab, this.bWF.bWk);
                this.bWF.Tz();
            }

            public void ra() {
            }

            public void rd() {
                this.bWF.aab.clear();
                this.bWF.bWk.clear();
                this.bWF.bWm.a(this.bWF.aab, this.bWF.bWk);
                this.bWF.bWm.notifyDataSetChanged();
                this.bWF.hk(this.bWF.bWj.qI);
            }

            public void rb() {
                this.bWF.bWm.a(this.bWF.aab, this.bWF.bWk);
                this.bWF.Tz();
            }
        });
        dia.az(hlx.data.localstore.a.bKA, "欲知后事如何，请听下回分解！敬请期待喔！^_^");
        dia.aA("回顾过去", isReaded ? null : "重新体验");
        dia.showDialog();
    }

    private void hk(String nextId) {
        com.huluxia.data.storymode.a _ItemStructure = d.TC().hm(nextId);
        if (_ItemStructure == null || _ItemStructure.qi == null) {
            HLog.verbose(TAG, "LSPrint 无法读取下一节点！", new Object[0]);
        } else if (_ItemStructure.qi.equals(c.bVB)) {
            message = new Message();
            message.obj = null;
            if (_ItemStructure.ql != null) {
                this.bWk.put(_ItemStructure.ql, ((b) _ItemStructure.qj.get(0)).mValue);
                message.obj = ((b) _ItemStructure.qj.get(0)).qC;
            } else if (_ItemStructure.qm != null) {
                this.bWk.put(_ItemStructure.qm, ((b) _ItemStructure.qj.get(0)).mValue);
                message.obj = ((b) _ItemStructure.qj.get(0)).qC;
            } else if (_ItemStructure.qp != null) {
                this.bWk.put(_ItemStructure.qp, String.valueOf(Float.parseFloat(((b) _ItemStructure.qj.get(0)).mValue) + Float.parseFloat((String) this.bWk.get(_ItemStructure.qp))));
                message.obj = ((b) _ItemStructure.qj.get(0)).qC;
            } else if (_ItemStructure.qr != null) {
                this.bWk.put(_ItemStructure.qr, String.valueOf(Float.parseFloat((String) this.bWk.get(_ItemStructure.qr)) - Float.parseFloat(((b) _ItemStructure.qj.get(0)).mValue)));
                message.obj = ((b) _ItemStructure.qj.get(0)).qC;
            } else if (_ItemStructure.qs != null) {
                this.bWk.put(_ItemStructure.qs, String.valueOf(Float.parseFloat(((b) _ItemStructure.qj.get(0)).mValue) * Float.parseFloat((String) this.bWk.get(_ItemStructure.qs))));
                message.obj = ((b) _ItemStructure.qj.get(0)).qC;
            } else if (_ItemStructure.qt != null) {
                for (int i = _ItemStructure.qj.size() - 1; i >= 0; i--) {
                    if (!((b) _ItemStructure.qj.get(i)).mValue.contains(SimpleComparison.EQUAL_TO_OPERATION)) {
                        if (!((b) _ItemStructure.qj.get(i)).mValue.startsWith(SimpleComparison.LESS_THAN_OPERATION)) {
                            if (!((b) _ItemStructure.qj.get(i)).mValue.startsWith(SimpleComparison.GREATER_THAN_OPERATION)) {
                                message.obj = null;
                                break;
                            } else if (Float.parseFloat((String) this.bWk.get(_ItemStructure.qt)) > Float.parseFloat(((b) _ItemStructure.qj.get(i)).mValue.substring(1))) {
                                message.obj = ((b) _ItemStructure.qj.get(i)).qC;
                                break;
                            }
                        } else if (Float.parseFloat((String) this.bWk.get(_ItemStructure.qt)) < Float.parseFloat(((b) _ItemStructure.qj.get(i)).mValue.substring(1))) {
                            message.obj = ((b) _ItemStructure.qj.get(i)).qC;
                            break;
                        }
                    } else if (!((b) _ItemStructure.qj.get(i)).mValue.startsWith(SimpleComparison.LESS_THAN_OPERATION)) {
                        if (!((b) _ItemStructure.qj.get(i)).mValue.startsWith(SimpleComparison.GREATER_THAN_OPERATION)) {
                            float _tmp = Float.parseFloat((String) this.bWk.get(_ItemStructure.qt)) - Float.parseFloat(((b) _ItemStructure.qj.get(i)).mValue.substring(2));
                            if (0.0f <= Math.abs(_tmp) && ((double) Math.abs(_tmp)) < 1.0E-5d) {
                                message.obj = ((b) _ItemStructure.qj.get(i)).qC;
                                break;
                            }
                        } else if (Float.parseFloat((String) this.bWk.get(_ItemStructure.qt)) >= Float.parseFloat(((b) _ItemStructure.qj.get(i)).mValue.substring(2))) {
                            message.obj = ((b) _ItemStructure.qj.get(i)).qC;
                            break;
                        }
                    } else if (Float.parseFloat((String) this.bWk.get(_ItemStructure.qt)) <= Float.parseFloat(((b) _ItemStructure.qj.get(i)).mValue.substring(2))) {
                        message.obj = ((b) _ItemStructure.qj.get(i)).qC;
                        break;
                    }
                }
            }
            message.what = 2;
            this.bWr = (String) message.obj;
            this.Vo.sendMessageDelayed(message, hlx.mcstorymode.storyutils.b.a(_ItemStructure.qk, 1, bWB[this.bWz]));
        } else {
            this.bWm.a(_ItemStructure);
            Tz();
            if (!_ItemStructure.qi.equals(c.bVA)) {
                if (((b) _ItemStructure.qj.get(0)).qC != null && ((b) _ItemStructure.qj.get(0)).qC.equals(this.bWj.qJ)) {
                    dE(true);
                } else if (_ItemStructure.mId == null || !_ItemStructure.mId.equals(this.bWj.qK)) {
                    this.bWo.setVisibility(0);
                    message = new Message();
                    message.obj = ((b) _ItemStructure.qj.get(0)).qC;
                    message.what = 2;
                    this.bWr = (String) message.obj;
                    this.Vo.sendMessageDelayed(message, hlx.mcstorymode.storyutils.b.a(_ItemStructure.qk, 1, bWB[this.bWz]));
                } else {
                    t.l(this.aMn, c.bWb);
                }
            }
        }
    }

    private void Tz() {
        this.bWn.setAdapter(this.bWm);
        this.bWn.setSelection(this.bWm.getCount() - 1);
        this.bWm.notifyDataSetChanged();
    }

    protected void onPause() {
        super.onPause();
        b.a(e.nc(this.bWw), "", this.aab, this.bWk);
        this.bWq = true;
        if (this.bWl != null) {
            this.bWl.stop();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            ((AnimationDrawable) ((LinearLayout) findViewById(R.id.llyStoryMGameLoading)).getBackground()).start();
            ((AnimationDrawable) ((ImageView) findViewById(R.id.ivStoryLoading)).getBackground()).start();
        }
    }
}
