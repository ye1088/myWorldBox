package hlx.mcstorymode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.t;
import java.io.File;
import java.util.List;
import java.util.Map;

public class StoryListViewAdapter extends BaseAdapter {
    private List<com.huluxia.data.storymode.a> aab;
    private a bWE;
    private boolean bWI;
    private String bWJ;
    private boolean bWK;
    private OnCheckedChangeListener bWL = new OnCheckedChangeListener(this) {
        final /* synthetic */ StoryListViewAdapter bWM;

        {
            this.bWM = this$0;
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            com.huluxia.data.storymode.a _tmpItem = (com.huluxia.data.storymode.a) group.getTag();
            this.bWM.bWI = false;
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    ((com.huluxia.data.storymode.b) _tmpItem.qj.get(i)).qD = true;
                    if (_tmpItem.qm != null) {
                        this.bWM.bWk.put(_tmpItem.qm, this.bWM.ho(((com.huluxia.data.storymode.b) _tmpItem.qj.get(i)).mValue));
                    }
                    this.bWM.bWE.k(((com.huluxia.data.storymode.b) _tmpItem.qj.get(i)).qC, hlx.mcstorymode.storyutils.b.ad(_tmpItem.qk, 1));
                }
                group.getChildAt(i).setEnabled(false);
            }
            this.bWM.notifyDataSetChanged();
        }
    };
    private Map<String, String> bWk;
    private Context mContext;

    public static abstract class a {
        public abstract void k(String str, long j);
    }

    public class b {
        final /* synthetic */ StoryListViewAdapter bWM;
        public TextView bWN;
        public LinearLayout bWO;
        public TextView bWP;
        public PaintView bWQ;
        public PaintView bWR;
        public TextView bWS;
        public RadioGroup bWT;
        public RelativeLayout bWU;
        public PaintView bWV;
        public TextView bWW;

        public b(StoryListViewAdapter this$0) {
            this.bWM = this$0;
        }
    }

    public StoryListViewAdapter(Context context, a cb, String imagePath, boolean canReadLocalImg) {
        this.mContext = context;
        this.bWE = cb;
        this.bWJ = imagePath;
        this.bWK = canReadLocalImg;
    }

    public void a(List<com.huluxia.data.storymode.a> data, Map<String, String> map) {
        this.aab = data;
        this.bWk = map;
        this.bWI = true;
    }

    public void a(com.huluxia.data.storymode.a data) {
        this.aab.add(data);
        this.bWI = true;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return this.aab.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.itm_story_mode, null);
            holder = new b(this);
            holder.bWN = (TextView) convertView.findViewById(R.id.tvCtrl);
            holder.bWO = (LinearLayout) convertView.findViewById(R.id.llyMessage);
            holder.bWP = (TextView) convertView.findViewById(R.id.tvName);
            holder.bWQ = (PaintView) convertView.findViewById(R.id.rivStoryAvatarLeft);
            holder.bWR = (PaintView) convertView.findViewById(R.id.rivStoryAvatarRight);
            holder.bWS = (TextView) convertView.findViewById(R.id.tvMessage);
            holder.bWT = (RadioGroup) convertView.findViewById(R.id.rdogrpSentences);
            holder.bWU = (RelativeLayout) convertView.findViewById(R.id.llyStoryImage);
            holder.bWV = (PaintView) convertView.findViewById(R.id.ivStoryItemImg);
            holder.bWW = (TextView) convertView.findViewById(R.id.tvStoryImageText);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        a(holder, position, (com.huluxia.data.storymode.a) this.aab.get(position));
        return convertView;
    }

    public void a(b viewHolder, int position, com.huluxia.data.storymode.a item) {
        viewHolder.bWN.setVisibility(8);
        viewHolder.bWO.setVisibility(8);
        viewHolder.bWT.setVisibility(8);
        viewHolder.bWU.setVisibility(8);
        String str = item.qi;
        Object obj = -1;
        switch (str.hashCode()) {
            case -2100373168:
                if (str.equals(c.bVC)) {
                    obj = 3;
                    break;
                }
                break;
            case -511944928:
                if (str.equals(c.bVA)) {
                    obj = 2;
                    break;
                }
                break;
            case 1456583191:
                if (str.equals(c.bVz)) {
                    obj = 1;
                    break;
                }
                break;
            case 1508926075:
                if (str.equals(c.bVy)) {
                    obj = null;
                    break;
                }
                break;
        }
        String _localPath;
        String _netPath;
        switch (obj) {
            case null:
                String _tempCtrlValue;
                viewHolder.bWN.setVisibility(0);
                if (item.qn == null) {
                    _tempCtrlValue = ((com.huluxia.data.storymode.b) item.qj.get(0)).mValue;
                } else {
                    _tempCtrlValue = ((com.huluxia.data.storymode.b) item.qj.get(0)).mValue.replace("#-" + item.qn + "-#", (CharSequence) this.bWk.get(item.qn));
                    ((com.huluxia.data.storymode.b) item.qj.get(0)).mValue = _tempCtrlValue;
                }
                if (((com.huluxia.data.storymode.b) item.qj.get(0)).qE != null) {
                    viewHolder.bWN.setTextColor(Color.parseColor(((com.huluxia.data.storymode.b) item.qj.get(0)).qE));
                } else {
                    viewHolder.bWN.setTextColor(-11612160);
                }
                viewHolder.bWN.setText(_tempCtrlValue);
                return;
            case 1:
                String _tempMesValue;
                viewHolder.bWO.setVisibility(0);
                viewHolder.bWP.setText(((com.huluxia.data.storymode.b) item.qj.get(0)).mName + "：");
                String _tmpRoleAvatarPath = a((com.huluxia.data.storymode.b) item.qj.get(0));
                if (_tmpRoleAvatarPath != null) {
                    if (_tmpRoleAvatarPath.contains("#")) {
                        _localPath = _tmpRoleAvatarPath.substring(0, _tmpRoleAvatarPath.indexOf("#"));
                        _netPath = ((String) this.bWk.get("image_linkHeader")) + _tmpRoleAvatarPath.substring(_tmpRoleAvatarPath.indexOf("#") + 1);
                    } else {
                        _localPath = _tmpRoleAvatarPath;
                        _netPath = null;
                    }
                    Bitmap _tmpAvatarBitmap = hn(_localPath);
                    if (_tmpAvatarBitmap != null) {
                        if (a(((com.huluxia.data.storymode.b) item.qj.get(0)).mName, (com.huluxia.data.storymode.b) item.qj.get(0))) {
                            viewHolder.bWQ.setVisibility(0);
                            viewHolder.bWQ.setImageBitmap(_tmpAvatarBitmap);
                            viewHolder.bWR.setVisibility(8);
                        } else {
                            viewHolder.bWQ.setVisibility(8);
                            viewHolder.bWR.setVisibility(0);
                            viewHolder.bWR.setImageBitmap(_tmpAvatarBitmap);
                        }
                    } else if (_netPath != null) {
                        HLog.verbose("TAG", "LSPrint netPath [%s]", _netPath);
                        if (a(((com.huluxia.data.storymode.b) item.qj.get(0)).mName, (com.huluxia.data.storymode.b) item.qj.get(0))) {
                            viewHolder.bWQ.setVisibility(0);
                            viewHolder.bWR.setVisibility(8);
                            t.b(viewHolder.bWQ, _netPath, 0.0f);
                        } else {
                            viewHolder.bWQ.setVisibility(8);
                            viewHolder.bWR.setVisibility(0);
                            t.b(viewHolder.bWR, _netPath, 0.0f);
                        }
                    }
                } else {
                    viewHolder.bWQ.setVisibility(8);
                    viewHolder.bWR.setVisibility(8);
                }
                if (item.qn == null) {
                    _tempMesValue = ((com.huluxia.data.storymode.b) item.qj.get(0)).mValue;
                } else {
                    _tempMesValue = ((com.huluxia.data.storymode.b) item.qj.get(0)).mValue.replace("#-" + item.qn + "-#", (CharSequence) this.bWk.get(item.qn));
                    ((com.huluxia.data.storymode.b) item.qj.get(0)).mValue = _tempMesValue;
                }
                if (((com.huluxia.data.storymode.b) item.qj.get(0)).qE != null) {
                    viewHolder.bWN.setTextColor(Color.parseColor(((com.huluxia.data.storymode.b) item.qj.get(0)).qE));
                } else {
                    viewHolder.bWN.setTextColor(-1);
                }
                viewHolder.bWS.setText(_tempMesValue);
                return;
            case 2:
                int i;
                viewHolder.bWT.setVisibility(0);
                viewHolder.bWT.setTag(item);
                viewHolder.bWT.setOnCheckedChangeListener(null);
                viewHolder.bWT.clearCheck();
                for (i = item.qj.size(); i < viewHolder.bWT.getChildCount(); i++) {
                    viewHolder.bWT.getChildAt(i).setVisibility(8);
                }
                int _sentenceSize = item.qj.size();
                int _radbtnSize = viewHolder.bWT.getChildCount();
                boolean _canClick = (position == getCount() + -1) && this.bWI;
                for (i = 0; i < _sentenceSize && _sentenceSize <= _radbtnSize; i++) {
                    RadioButton _rdobtn = (RadioButton) viewHolder.bWT.getChildAt(i);
                    _rdobtn.setVisibility(0);
                    _rdobtn.setChecked(((com.huluxia.data.storymode.b) item.qj.get(i)).qD);
                    _rdobtn.setEnabled(_canClick);
                    String _tmpStr = ((com.huluxia.data.storymode.b) item.qj.get(i)).mValue;
                    if (item.qm != null) {
                        _tmpStr = hp(_tmpStr);
                    }
                    _rdobtn.setText(_tmpStr);
                    if (((com.huluxia.data.storymode.b) item.qj.get(i)).qE != null) {
                        _rdobtn.setTextColor(Color.parseColor(((com.huluxia.data.storymode.b) item.qj.get(i)).qE));
                    } else {
                        viewHolder.bWN.setTextColor(-1);
                    }
                }
                viewHolder.bWT.setOnCheckedChangeListener(_canClick ? this.bWL : null);
                return;
            case 3:
                viewHolder.bWU.setVisibility(0);
                if (item.qo != null) {
                    if (item.qo.contains("#")) {
                        _localPath = item.qo.substring(0, item.qo.indexOf("#"));
                        _netPath = ((String) this.bWk.get("image_linkHeader")) + item.qo.substring(item.qo.indexOf("#") + 1);
                    } else {
                        _localPath = item.qo;
                        _netPath = null;
                    }
                    Bitmap _tmpBitmap = hn(_localPath);
                    if (_tmpBitmap != null) {
                        viewHolder.bWV.setImageBitmap(_tmpBitmap);
                        viewHolder.bWV.setVisibility(0);
                    } else if (_netPath != null) {
                        HLog.verbose("TAG", "LSPrint netPath [%s]", _netPath);
                        t.b(viewHolder.bWV, _netPath, 0.0f);
                        viewHolder.bWV.setVisibility(0);
                    } else {
                        viewHolder.bWV.setVisibility(8);
                    }
                } else {
                    viewHolder.bWV.setVisibility(8);
                }
                if (((com.huluxia.data.storymode.b) item.qj.get(0)).qE != null) {
                    viewHolder.bWW.setTextColor(Color.parseColor(((com.huluxia.data.storymode.b) item.qj.get(0)).qE));
                } else {
                    viewHolder.bWN.setTextColor(-11612160);
                }
                viewHolder.bWW.setText(((com.huluxia.data.storymode.b) item.qj.get(0)).mValue);
                return;
            default:
                HLog.verbose("StoryListViewAdapter", "出现了新类型", new Object[0]);
                return;
        }
    }

    private String a(com.huluxia.data.storymode.b sentence) {
        if (this.bWk.containsKey(sentence.mName + "_avatarPath")) {
            return (String) this.bWk.get(sentence.mName + "_avatarPath");
        }
        return null;
    }

    private boolean a(String roleName, com.huluxia.data.storymode.b sentence) {
        if (this.bWk.containsKey(roleName + "_isLeadingRole")) {
            return ((String) this.bWk.get(roleName + "_isLeadingRole")).equals("true");
        }
        String _tmpLeadingRole = sentence.qF;
        boolean _tmpFlag = _tmpLeadingRole != null && _tmpLeadingRole.toLowerCase().equals("true");
        return _tmpFlag;
    }

    private Bitmap hn(String path) {
        if (!this.bWK) {
            return null;
        }
        String ABPath = this.bWJ + File.separator + path;
        if (new File(ABPath).exists()) {
            return BitmapFactory.decodeFile(ABPath);
        }
        return null;
    }

    private String ho(String objectStr) {
        int start = objectStr.indexOf("#-") + 2;
        return objectStr.substring(start, objectStr.indexOf("-#", start));
    }

    private String hp(String objectStr) {
        int start = objectStr.indexOf("#-");
        int end = objectStr.indexOf("-#", start);
        return objectStr.replace(objectStr.substring(start, end + 2), objectStr.substring(start + 2, end));
    }
}
