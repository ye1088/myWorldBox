package com.mojang.minecraftpe;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.ClipboardManager;
import com.mojang.minecraftpe.MainActivity.4;
import java.io.StringWriter;

class MainActivity$4$1 implements OnClickListener {
    final /* synthetic */ StringWriter bIn;
    final /* synthetic */ 4 bIo;

    MainActivity$4$1(4 this$1, StringWriter stringWriter) {
        this.bIo = this$1;
        this.bIn = stringWriter;
    }

    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
        ((ClipboardManager) this.bIo.bIh.getSystemService("clipboard")).setText(this.bIn.toString());
    }
}
