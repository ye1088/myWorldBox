package com.MCWorld.ui.mctool;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.MCWorld.framework.R;
import java.io.File;

public abstract class ImportActivity extends Activity implements OnClickListener {
    public Button baX;
    public Button baY;
    public TextView baZ;
    public TextView bba;
    public File mFile = null;

    protected abstract void Ix();

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.import_confirm);
        this.baX = (Button) findViewById(R.id.ok_button);
        this.baY = (Button) findViewById(R.id.cancel_button);
        this.baX.setOnClickListener(this);
        this.baY.setOnClickListener(this);
        this.baZ = (TextView) findViewById(R.id.app_name);
        this.bba = (TextView) findViewById(R.id.install_confirm_question);
        if (getIntent() == null) {
            finish();
            return;
        }
        this.mFile = y(getIntent().getData());
        if (this.mFile == null || !this.mFile.canRead()) {
            finish();
            return;
        }
        this.baZ.setText(this.mFile.getName());
        Log.d("TAG", "HAHAHAHAHAHAHA ImportActivity file : " + this.mFile.getName() + "; path : " + this.mFile.getPath());
        setResult(0);
    }

    public void onClick(View v) {
        if (v.equals(this.baY)) {
            finish();
        } else if (v.equals(this.baX)) {
            this.baX.setEnabled(false);
            this.baY.setEnabled(false);
            Ix();
        }
    }

    public static File y(Uri uri) {
        if (uri != null) {
            String filepath = uri.getPath();
            if (filepath != null) {
                return new File(filepath);
            }
        }
        return null;
    }
}
