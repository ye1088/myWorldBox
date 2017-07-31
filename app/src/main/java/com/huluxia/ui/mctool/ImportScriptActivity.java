package com.huluxia.ui.mctool;

import android.os.Bundle;
import android.widget.Toast;
import com.huluxia.data.js.JsItem;
import com.huluxia.framework.R;
import com.huluxia.module.i;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ai;
import com.huluxia.utils.aq;
import com.huluxia.utils.j;
import hlx.data.localstore.a;

public class ImportScriptActivity extends ImportActivity {
    public static final String SCRIPTS_DIR = j.cT(true);

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.bba.setText(R.string.script_import_confirm);
    }

    protected void Ix() {
        String newPath = SCRIPTS_DIR + this.mFile.getName();
        if (UtilsFile.copyFile(this.mFile.getPath(), SCRIPTS_DIR + this.mFile.getName())) {
            Toast.makeText(this, R.string.script_imported, 1).show();
            W(this.mFile.getName(), newPath);
            setResult(-1);
        } else {
            Toast.makeText(this, R.string.manage_patches_import_error, 1).show();
            setResult(0);
        }
        finish();
    }

    private void W(String name, String path) {
        JsItem js = new JsItem();
        String temp = name.toLowerCase();
        if (temp.endsWith(a.bJY)) {
            temp = temp.substring(0, temp.indexOf(a.bJY));
        }
        js.name = temp;
        js.path = path;
        js.state = 1;
        js.ver = aq.MC();
        ai.b(js);
        i.DB().L(j.getFileMD5(path), name);
    }
}
