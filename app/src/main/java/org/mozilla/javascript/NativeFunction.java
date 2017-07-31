package org.mozilla.javascript;

import org.mozilla.javascript.debug.DebuggableScript;

public abstract class NativeFunction extends BaseFunction {
    static final long serialVersionUID = 8713897114082216401L;

    protected abstract int getLanguageVersion();

    protected abstract int getParamAndVarCount();

    protected abstract int getParamCount();

    protected abstract String getParamOrVarName(int i);

    public final void initScriptFunction(Context cx, Scriptable scope) {
        ScriptRuntime.setFunctionProtoAndParent(this, scope);
    }

    final String decompile(int indent, int flags) {
        String encodedSource = getEncodedSource();
        if (encodedSource == null) {
            return super.decompile(indent, flags);
        }
        UintMap properties = new UintMap(1);
        properties.put(1, indent);
        return Decompiler.decompile(encodedSource, flags, properties);
    }

    public int getLength() {
        int paramCount = getParamCount();
        if (getLanguageVersion() != 120) {
            return paramCount;
        }
        NativeCall activation = ScriptRuntime.findFunctionActivation(Context.getContext(), this);
        return activation != null ? activation.originalArgs.length : paramCount;
    }

    public int getArity() {
        return getParamCount();
    }

    @Deprecated
    public String jsGet_name() {
        return getFunctionName();
    }

    public String getEncodedSource() {
        return null;
    }

    public DebuggableScript getDebuggableView() {
        return null;
    }

    public Object resumeGenerator(Context cx, Scriptable scope, int operation, Object state, Object value) {
        throw new EvaluatorException("resumeGenerator() not implemented");
    }

    protected boolean getParamOrVarConst(int index) {
        return false;
    }
}
