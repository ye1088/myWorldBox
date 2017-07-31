package org.mozilla.javascript;

import java.security.AccessController;
import org.mozilla.javascript.xml.XMLLib.Factory;

public class ContextFactory {
    private static ContextFactory global = new ContextFactory();
    private static volatile boolean hasCustomGlobal;
    private ClassLoader applicationClassLoader;
    private boolean disabledListening;
    private volatile Object listeners;
    private final Object listenersLock = new Object();
    private volatile boolean sealed;

    public static ContextFactory getGlobal() {
        return global;
    }

    public static boolean hasExplicitGlobal() {
        return hasCustomGlobal;
    }

    public static synchronized void initGlobal(ContextFactory factory) {
        synchronized (ContextFactory.class) {
            if (factory == null) {
                throw new IllegalArgumentException();
            } else if (hasCustomGlobal) {
                throw new IllegalStateException();
            } else {
                hasCustomGlobal = true;
                global = factory;
            }
        }
    }

    public static synchronized GlobalSetter getGlobalSetter() {
        GlobalSetter org_mozilla_javascript_ContextFactory_1GlobalSetterImpl;
        synchronized (ContextFactory.class) {
            if (hasCustomGlobal) {
                throw new IllegalStateException();
            }
            hasCustomGlobal = true;
            org_mozilla_javascript_ContextFactory_1GlobalSetterImpl = new 1GlobalSetterImpl();
        }
        return org_mozilla_javascript_ContextFactory_1GlobalSetterImpl;
    }

    protected Context makeContext() {
        return new Context(this);
    }

    protected boolean hasFeature(Context cx, int featureIndex) {
        boolean z = true;
        int version;
        switch (featureIndex) {
            case 1:
                version = cx.getLanguageVersion();
                if (version == 100 || version == 110 || version == 120) {
                    return true;
                }
                return false;
            case 2:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                return false;
            case 3:
                return true;
            case 4:
                if (cx.getLanguageVersion() != 120) {
                    z = false;
                }
                return z;
            case 5:
                return true;
            case 6:
                version = cx.getLanguageVersion();
                if (version == 0 || version >= 160) {
                    return true;
                }
                return false;
            case 14:
                return true;
            default:
                throw new IllegalArgumentException(String.valueOf(featureIndex));
        }
    }

    private boolean isDom3Present() {
        Class<?> nodeClass = Kit.classOrNull("org.w3c.dom.Node");
        if (nodeClass == null) {
            return false;
        }
        try {
            nodeClass.getMethod("getUserData", new Class[]{String.class});
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    protected Factory getE4xImplementationFactory() {
        if (isDom3Present()) {
            return Factory.create("org.mozilla.javascript.xmlimpl.XMLLibImpl");
        }
        return null;
    }

    protected GeneratedClassLoader createClassLoader(ClassLoader parent) {
        return (GeneratedClassLoader) AccessController.doPrivileged(new 1(this, parent));
    }

    public final ClassLoader getApplicationClassLoader() {
        return this.applicationClassLoader;
    }

    public final void initApplicationClassLoader(ClassLoader loader) {
        if (loader == null) {
            throw new IllegalArgumentException("loader is null");
        } else if (!Kit.testIfCanLoadRhinoClasses(loader)) {
            throw new IllegalArgumentException("Loader can not resolve Rhino classes");
        } else if (this.applicationClassLoader != null) {
            throw new IllegalStateException("applicationClassLoader can only be set once");
        } else {
            checkNotSealed();
            this.applicationClassLoader = loader;
        }
    }

    protected Object doTopCall(Callable callable, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        Object result = callable.call(cx, scope, thisObj, args);
        return result instanceof ConsString ? result.toString() : result;
    }

    protected void observeInstructionCount(Context cx, int instructionCount) {
    }

    protected void onContextCreated(Context cx) {
        Object listeners = this.listeners;
        int i = 0;
        while (true) {
            Listener l = (Listener) Kit.getListener(listeners, i);
            if (l != null) {
                l.contextCreated(cx);
                i++;
            } else {
                return;
            }
        }
    }

    protected void onContextReleased(Context cx) {
        Object listeners = this.listeners;
        int i = 0;
        while (true) {
            Listener l = (Listener) Kit.getListener(listeners, i);
            if (l != null) {
                l.contextReleased(cx);
                i++;
            } else {
                return;
            }
        }
    }

    public final void addListener(Listener listener) {
        checkNotSealed();
        synchronized (this.listenersLock) {
            if (this.disabledListening) {
                throw new IllegalStateException();
            }
            this.listeners = Kit.addListener(this.listeners, listener);
        }
    }

    public final void removeListener(Listener listener) {
        checkNotSealed();
        synchronized (this.listenersLock) {
            if (this.disabledListening) {
                throw new IllegalStateException();
            }
            this.listeners = Kit.removeListener(this.listeners, listener);
        }
    }

    final void disableContextListening() {
        checkNotSealed();
        synchronized (this.listenersLock) {
            this.disabledListening = true;
            this.listeners = null;
        }
    }

    public final boolean isSealed() {
        return this.sealed;
    }

    public final void seal() {
        checkNotSealed();
        this.sealed = true;
    }

    protected final void checkNotSealed() {
        if (this.sealed) {
            throw new IllegalStateException();
        }
    }

    public final Object call(ContextAction action) {
        try {
            return Context.call(this, action);
        } catch (Exception e) {
            return null;
        }
    }

    public Context enterContext() {
        return enterContext(null);
    }

    @Deprecated
    public final Context enter() {
        return enterContext(null);
    }

    @Deprecated
    public final void exit() {
        Context.exit();
    }

    public final Context enterContext(Context cx) {
        return Context.enter(cx, this);
    }
}
