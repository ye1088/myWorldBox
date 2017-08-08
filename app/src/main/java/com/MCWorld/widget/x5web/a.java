package com.MCWorld.widget.x5web;

import android.content.Context;
import java.util.Map;

/* compiled from: SecurityJsBridgeBundle */
public abstract class a {
    public static final String METHOD = "method";
    private static final String bHk = "JsBridge";
    public static final String bHl = "block";
    public static final String bHm = "callback";
    public static final String bHn = "local_js_bridge::";
    private String bHo;
    private String bHp;
    private Context mContext;

    public abstract void QE();

    public a(String JsBlockName, String methodName) throws Exception {
        if (methodName == null) {
            throw new Exception("methodName can not be null!");
        } else if (JsBlockName != null) {
            this.bHo = JsBlockName;
        } else {
            this.bHo = bHk;
        }
    }

    public String getMethodName() {
        return this.bHp;
    }

    public String QF() {
        return this.bHo;
    }

    private void d(Map<String, Object> data) {
        if (data != null) {
            String injectCode = "javascript:(function JsAddJavascriptInterface_(){ if (typeof(window.jsInterface)!='undefined') {console.log('window.jsInterface_js_interface_name is exist!!');}   else {" + data.get(bHl) + data.get("method") + "window.jsBridge = {" + "onButtonClick:function(arg0) {" + "return prompt('MyApp:'+JSON.stringify({obj:'jsInterface',func:'onButtonClick',args:[arg0]}));" + "}," + "onImageClick:function(arg0,arg1,arg2) {" + "prompt('MyApp:'+JSON.stringify({obj:'jsInterface',func:'onImageClick',args:[arg0,arg1,arg2]}));" + "}," + "};" + "}" + "}" + ")()";
        }
    }

    private static String QG() {
        return null;
    }
}
