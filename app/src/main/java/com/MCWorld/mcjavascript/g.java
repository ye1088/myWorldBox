package com.MCWorld.mcjavascript;

import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

/* compiled from: DTJSState */
public class g {
    private boolean ajC = false;
    public int errors = 0;
    public String name;
    public Scriptable scope;
    public Script script;

    protected g(Script paramScript, Scriptable paramScriptable, String paramString) {
        this.script = paramScript;
        this.scope = paramScriptable;
        this.name = paramString;
        bW(true);
    }

    public boolean Ak() {
        return this.ajC;
    }

    public void bW(boolean bEnabled) {
        this.ajC = bEnabled;
    }
}
