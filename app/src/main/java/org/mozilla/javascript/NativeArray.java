package org.mozilla.javascript;

import android.support.v4.media.session.PlaybackStateCompat;
import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.mozilla.javascript.TopLevel.Builtins;

public class NativeArray extends IdScriptableObject implements List {
    private static final Object ARRAY_TAG = "Array";
    private static final int ConstructorId_concat = -13;
    private static final int ConstructorId_every = -17;
    private static final int ConstructorId_filter = -18;
    private static final int ConstructorId_find = -22;
    private static final int ConstructorId_findIndex = -23;
    private static final int ConstructorId_forEach = -19;
    private static final int ConstructorId_indexOf = -15;
    private static final int ConstructorId_isArray = -26;
    private static final int ConstructorId_join = -5;
    private static final int ConstructorId_lastIndexOf = -16;
    private static final int ConstructorId_map = -20;
    private static final int ConstructorId_pop = -9;
    private static final int ConstructorId_push = -8;
    private static final int ConstructorId_reduce = -24;
    private static final int ConstructorId_reduceRight = -25;
    private static final int ConstructorId_reverse = -6;
    private static final int ConstructorId_shift = -10;
    private static final int ConstructorId_slice = -14;
    private static final int ConstructorId_some = -21;
    private static final int ConstructorId_sort = -7;
    private static final int ConstructorId_splice = -12;
    private static final int ConstructorId_unshift = -11;
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final double GROW_FACTOR = 1.5d;
    private static final int Id_concat = 13;
    private static final int Id_constructor = 1;
    private static final int Id_every = 17;
    private static final int Id_filter = 18;
    private static final int Id_find = 22;
    private static final int Id_findIndex = 23;
    private static final int Id_forEach = 19;
    private static final int Id_indexOf = 15;
    private static final int Id_join = 5;
    private static final int Id_lastIndexOf = 16;
    private static final int Id_length = 1;
    private static final int Id_map = 20;
    private static final int Id_pop = 9;
    private static final int Id_push = 8;
    private static final int Id_reduce = 24;
    private static final int Id_reduceRight = 25;
    private static final int Id_reverse = 6;
    private static final int Id_shift = 10;
    private static final int Id_slice = 14;
    private static final int Id_some = 21;
    private static final int Id_sort = 7;
    private static final int Id_splice = 12;
    private static final int Id_toLocaleString = 3;
    private static final int Id_toSource = 4;
    private static final int Id_toString = 2;
    private static final int Id_unshift = 11;
    private static final int MAX_INSTANCE_ID = 1;
    private static final int MAX_PRE_GROW_SIZE = 1431655764;
    private static final int MAX_PROTOTYPE_ID = 25;
    private static final Integer NEGATIVE_ONE = Integer.valueOf(-1);
    private static int maximumInitialCapacity = 10000;
    static final long serialVersionUID = 7331366857676127338L;
    private Object[] dense;
    private boolean denseOnly;
    private long length;
    private int lengthAttr;

    static void init(Scriptable scope, boolean sealed) {
        new NativeArray(0).exportAsJSClass(25, scope, sealed);
    }

    static int getMaximumInitialCapacity() {
        return maximumInitialCapacity;
    }

    static void setMaximumInitialCapacity(int maximumInitialCapacity) {
        maximumInitialCapacity = maximumInitialCapacity;
    }

    public NativeArray(long lengthArg) {
        this.lengthAttr = 6;
        this.denseOnly = lengthArg <= ((long) maximumInitialCapacity);
        if (this.denseOnly) {
            int intLength = (int) lengthArg;
            if (intLength < 10) {
                intLength = 10;
            }
            this.dense = new Object[intLength];
            Arrays.fill(this.dense, Scriptable.NOT_FOUND);
        }
        this.length = lengthArg;
    }

    public NativeArray(Object[] array) {
        this.lengthAttr = 6;
        this.denseOnly = true;
        this.dense = array;
        this.length = (long) array.length;
    }

    public String getClassName() {
        return "Array";
    }

    protected int getMaxInstanceId() {
        return 1;
    }

    protected void setInstanceIdAttributes(int id, int attr) {
        if (id == 1) {
            this.lengthAttr = attr;
        }
    }

    protected int findInstanceIdInfo(String s) {
        if (s.equals("length")) {
            return instanceIdInfo(this.lengthAttr, 1);
        }
        return super.findInstanceIdInfo(s);
    }

    protected String getInstanceIdName(int id) {
        if (id == 1) {
            return "length";
        }
        return super.getInstanceIdName(id);
    }

    protected Object getInstanceIdValue(int id) {
        if (id == 1) {
            return ScriptRuntime.wrapNumber((double) this.length);
        }
        return super.getInstanceIdValue(id);
    }

    protected void setInstanceIdValue(int id, Object value) {
        if (id == 1) {
            setLength(value);
        } else {
            super.setInstanceIdValue(id, value);
        }
    }

    protected void fillConstructorProperties(IdFunctionObject ctor) {
        addIdFunctionProperty(ctor, ARRAY_TAG, -5, "join", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, -6, "reverse", 0);
        addIdFunctionProperty(ctor, ARRAY_TAG, -7, "sort", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, -8, "push", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, -9, "pop", 0);
        addIdFunctionProperty(ctor, ARRAY_TAG, -10, "shift", 0);
        addIdFunctionProperty(ctor, ARRAY_TAG, -11, "unshift", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, -12, "splice", 2);
        addIdFunctionProperty(ctor, ARRAY_TAG, -13, "concat", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, -14, "slice", 2);
        addIdFunctionProperty(ctor, ARRAY_TAG, -15, "indexOf", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, -16, "lastIndexOf", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_every, "every", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_filter, "filter", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_forEach, "forEach", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_map, "map", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_some, "some", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_find, "find", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_findIndex, "findIndex", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_reduce, "reduce", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_reduceRight, "reduceRight", 1);
        addIdFunctionProperty(ctor, ARRAY_TAG, ConstructorId_isArray, "isArray", 1);
        super.fillConstructorProperties(ctor);
    }

    protected void initPrototypeId(int id) {
        int arity;
        String s;
        switch (id) {
            case 1:
                arity = 1;
                s = "constructor";
                break;
            case 2:
                arity = 0;
                s = "toString";
                break;
            case 3:
                arity = 0;
                s = "toLocaleString";
                break;
            case 4:
                arity = 0;
                s = "toSource";
                break;
            case 5:
                arity = 1;
                s = "join";
                break;
            case 6:
                arity = 0;
                s = "reverse";
                break;
            case 7:
                arity = 1;
                s = "sort";
                break;
            case 8:
                arity = 1;
                s = "push";
                break;
            case 9:
                arity = 0;
                s = "pop";
                break;
            case 10:
                arity = 0;
                s = "shift";
                break;
            case 11:
                arity = 1;
                s = "unshift";
                break;
            case 12:
                arity = 2;
                s = "splice";
                break;
            case 13:
                arity = 1;
                s = "concat";
                break;
            case 14:
                arity = 2;
                s = "slice";
                break;
            case 15:
                arity = 1;
                s = "indexOf";
                break;
            case 16:
                arity = 1;
                s = "lastIndexOf";
                break;
            case 17:
                arity = 1;
                s = "every";
                break;
            case 18:
                arity = 1;
                s = "filter";
                break;
            case 19:
                arity = 1;
                s = "forEach";
                break;
            case 20:
                arity = 1;
                s = "map";
                break;
            case 21:
                arity = 1;
                s = "some";
                break;
            case 22:
                arity = 1;
                s = "find";
                break;
            case 23:
                arity = 1;
                s = "findIndex";
                break;
            case 24:
                arity = 1;
                s = "reduce";
                break;
            case 25:
                arity = 1;
                s = "reduceRight";
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(ARRAY_TAG, id, s, arity);
    }

    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        boolean z = true;
        if (!f.hasTag(ARRAY_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        while (true) {
            switch (id) {
                case ConstructorId_isArray /*-26*/:
                    if (args.length <= 0 || !js_isArray(args[0])) {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                case ConstructorId_reduceRight /*-25*/:
                case ConstructorId_reduce /*-24*/:
                case ConstructorId_findIndex /*-23*/:
                case ConstructorId_find /*-22*/:
                case ConstructorId_some /*-21*/:
                case ConstructorId_map /*-20*/:
                case ConstructorId_forEach /*-19*/:
                case ConstructorId_filter /*-18*/:
                case ConstructorId_every /*-17*/:
                case -16:
                case -15:
                case -14:
                case -13:
                case -12:
                case -11:
                case -10:
                case -9:
                case -8:
                case -7:
                case -6:
                case -5:
                    if (args.length > 0) {
                        thisObj = ScriptRuntime.toObject(cx, scope, args[0]);
                        Object[] newArgs = new Object[(args.length - 1)];
                        for (int i = 0; i < newArgs.length; i++) {
                            newArgs[i] = args[i + 1];
                        }
                        args = newArgs;
                    }
                    id = -id;
                case 1:
                    boolean inNewExpr;
                    if (thisObj == null) {
                        inNewExpr = true;
                    } else {
                        inNewExpr = false;
                    }
                    if (inNewExpr) {
                        return jsConstructor(cx, scope, args);
                    }
                    return f.construct(cx, scope, args);
                case 2:
                    return toStringHelper(cx, scope, thisObj, cx.hasFeature(4), false);
                case 3:
                    return toStringHelper(cx, scope, thisObj, false, true);
                case 4:
                    return toStringHelper(cx, scope, thisObj, true, false);
                case 5:
                    return js_join(cx, thisObj, args);
                case 6:
                    return js_reverse(cx, thisObj, args);
                case 7:
                    return js_sort(cx, scope, thisObj, args);
                case 8:
                    return js_push(cx, thisObj, args);
                case 9:
                    return js_pop(cx, thisObj, args);
                case 10:
                    return js_shift(cx, thisObj, args);
                case 11:
                    return js_unshift(cx, thisObj, args);
                case 12:
                    return js_splice(cx, scope, thisObj, args);
                case 13:
                    return js_concat(cx, scope, thisObj, args);
                case 14:
                    return js_slice(cx, thisObj, args);
                case 15:
                    return js_indexOf(cx, thisObj, args);
                case 16:
                    return js_lastIndexOf(cx, thisObj, args);
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    return iterativeMethod(cx, id, scope, thisObj, args);
                case 24:
                case 25:
                    return reduceMethod(cx, id, scope, thisObj, args);
                default:
                    throw new IllegalArgumentException(String.valueOf(id));
            }
        }
    }

    public Object get(int index, Scriptable start) {
        if (!this.denseOnly && isGetterOrSetter(null, index, false)) {
            return super.get(index, start);
        }
        if (this.dense == null || index < 0 || index >= this.dense.length) {
            return super.get(index, start);
        }
        return this.dense[index];
    }

    public boolean has(int index, Scriptable start) {
        if (!this.denseOnly && isGetterOrSetter(null, index, false)) {
            return super.has(index, start);
        }
        if (this.dense == null || index < 0 || index >= this.dense.length) {
            return super.has(index, start);
        }
        if (this.dense[index] != NOT_FOUND) {
            return true;
        }
        return false;
    }

    private static long toArrayIndex(Object id) {
        if (id instanceof String) {
            return toArrayIndex((String) id);
        }
        if (id instanceof Number) {
            return toArrayIndex(((Number) id).doubleValue());
        }
        return -1;
    }

    private static long toArrayIndex(String id) {
        long index = toArrayIndex(ScriptRuntime.toNumber(id));
        return Long.toString(index).equals(id) ? index : -1;
    }

    private static long toArrayIndex(double d) {
        if (d == d) {
            long index = ScriptRuntime.toUint32(d);
            if (((double) index) == d && index != 4294967295L) {
                return index;
            }
        }
        return -1;
    }

    private static int toDenseIndex(Object id) {
        long index = toArrayIndex(id);
        return (0 > index || index >= 2147483647L) ? -1 : (int) index;
    }

    public void put(String id, Scriptable start, Object value) {
        super.put(id, start, value);
        if (start == this) {
            long index = toArrayIndex(id);
            if (index >= this.length) {
                this.length = 1 + index;
                this.denseOnly = false;
            }
        }
    }

    private boolean ensureCapacity(int capacity) {
        if (capacity > this.dense.length) {
            if (capacity > MAX_PRE_GROW_SIZE) {
                this.denseOnly = false;
                return false;
            }
            Object[] newDense = new Object[Math.max(capacity, (int) (((double) this.dense.length) * GROW_FACTOR))];
            System.arraycopy(this.dense, 0, newDense, 0, this.dense.length);
            Arrays.fill(newDense, this.dense.length, newDense.length, Scriptable.NOT_FOUND);
            this.dense = newDense;
        }
        return true;
    }

    public void put(int index, Scriptable start, Object value) {
        if (start == this && !isSealed() && this.dense != null && index >= 0 && (this.denseOnly || !isGetterOrSetter(null, index, true))) {
            if (!isExtensible() && this.length <= ((long) index)) {
                return;
            }
            if (index < this.dense.length) {
                this.dense[index] = value;
                if (this.length <= ((long) index)) {
                    this.length = ((long) index) + 1;
                    return;
                }
                return;
            } else if (this.denseOnly && ((double) index) < ((double) this.dense.length) * GROW_FACTOR && ensureCapacity(index + 1)) {
                this.dense[index] = value;
                this.length = ((long) index) + 1;
                return;
            } else {
                this.denseOnly = false;
            }
        }
        super.put(index, start, value);
        if (start == this && (this.lengthAttr & 1) == 0 && this.length <= ((long) index)) {
            this.length = ((long) index) + 1;
        }
    }

    public void delete(int index) {
        if (this.dense == null || index < 0 || index >= this.dense.length || isSealed() || (!this.denseOnly && isGetterOrSetter(null, index, true))) {
            super.delete(index);
        } else {
            this.dense[index] = NOT_FOUND;
        }
    }

    public Object[] getIds() {
        Object[] superIds = super.getIds();
        if (this.dense == null) {
            return superIds;
        }
        int N = this.dense.length;
        long currentLength = this.length;
        if (((long) N) > currentLength) {
            N = (int) currentLength;
        }
        if (N == 0) {
            return superIds;
        }
        int superLength = superIds.length;
        Object[] ids = new Object[(N + superLength)];
        int presentCount = 0;
        for (int i = 0; i != N; i++) {
            if (this.dense[i] != NOT_FOUND) {
                ids[presentCount] = Integer.valueOf(i);
                presentCount++;
            }
        }
        if (presentCount != N) {
            Object[] tmp = new Object[(presentCount + superLength)];
            System.arraycopy(ids, 0, tmp, 0, presentCount);
            ids = tmp;
        }
        System.arraycopy(superIds, 0, ids, presentCount, superLength);
        return ids;
    }

    public Object[] getAllIds() {
        Set<Object> allIds = new LinkedHashSet(Arrays.asList(getIds()));
        allIds.addAll(Arrays.asList(super.getAllIds()));
        return allIds.toArray();
    }

    public Integer[] getIndexIds() {
        Object[] ids = getIds();
        List<Integer> indices = new ArrayList(ids.length);
        for (Object id : ids) {
            int int32Id = ScriptRuntime.toInt32(id);
            if (int32Id >= 0 && ScriptRuntime.toString((double) int32Id).equals(ScriptRuntime.toString(id))) {
                indices.add(Integer.valueOf(int32Id));
            }
        }
        return (Integer[]) indices.toArray(new Integer[indices.size()]);
    }

    public Object getDefaultValue(Class<?> hint) {
        if (hint == ScriptRuntime.NumberClass && Context.getContext().getLanguageVersion() == 120) {
            return Long.valueOf(this.length);
        }
        return super.getDefaultValue(hint);
    }

    private ScriptableObject defaultIndexPropertyDescriptor(Object value) {
        Scriptable scope = getParentScope();
        if (scope == null) {
            scope = this;
        }
        ScriptableObject desc = new NativeObject();
        ScriptRuntime.setBuiltinProtoAndParent(desc, scope, Builtins.Object);
        desc.defineProperty("value", value, 0);
        desc.defineProperty("writable", Boolean.valueOf(true), 0);
        desc.defineProperty("enumerable", Boolean.valueOf(true), 0);
        desc.defineProperty("configurable", Boolean.valueOf(true), 0);
        return desc;
    }

    public int getAttributes(int index) {
        if (this.dense == null || index < 0 || index >= this.dense.length || this.dense[index] == NOT_FOUND) {
            return super.getAttributes(index);
        }
        return 0;
    }

    protected ScriptableObject getOwnPropertyDescriptor(Context cx, Object id) {
        if (this.dense != null) {
            int index = toDenseIndex(id);
            if (index >= 0 && index < this.dense.length && this.dense[index] != NOT_FOUND) {
                return defaultIndexPropertyDescriptor(this.dense[index]);
            }
        }
        return super.getOwnPropertyDescriptor(cx, id);
    }

    protected void defineOwnProperty(Context cx, Object id, ScriptableObject desc, boolean checkValid) {
        if (this.dense != null) {
            Object[] values = this.dense;
            this.dense = null;
            this.denseOnly = false;
            for (int i = 0; i < values.length; i++) {
                if (values[i] != NOT_FOUND) {
                    put(i, (Scriptable) this, values[i]);
                }
            }
        }
        long index = toArrayIndex(id);
        if (index >= this.length) {
            this.length = 1 + index;
        }
        super.defineOwnProperty(cx, id, desc, checkValid);
    }

    private static Object jsConstructor(Context cx, Scriptable scope, Object[] args) {
        if (args.length == 0) {
            return new NativeArray(0);
        }
        if (cx.getLanguageVersion() == 120) {
            return new NativeArray(args);
        }
        Object arg0 = args[0];
        if (args.length > 1 || !(arg0 instanceof Number)) {
            return new NativeArray(args);
        }
        long len = ScriptRuntime.toUint32(arg0);
        if (((double) len) == ((Number) arg0).doubleValue()) {
            return new NativeArray(len);
        }
        throw ScriptRuntime.constructError("RangeError", ScriptRuntime.getMessage0("msg.arraylength.bad"));
    }

    public long getLength() {
        return this.length;
    }

    @Deprecated
    public long jsGet_length() {
        return getLength();
    }

    void setDenseOnly(boolean denseOnly) {
        if (!denseOnly || this.denseOnly) {
            this.denseOnly = denseOnly;
            return;
        }
        throw new IllegalArgumentException();
    }

    private void setLength(Object val) {
        if ((this.lengthAttr & 1) == 0) {
            double d = ScriptRuntime.toNumber(val);
            long longVal = ScriptRuntime.toUint32(d);
            if (((double) longVal) != d) {
                throw ScriptRuntime.constructError("RangeError", ScriptRuntime.getMessage0("msg.arraylength.bad"));
            }
            if (this.denseOnly) {
                if (longVal < this.length) {
                    Arrays.fill(this.dense, (int) longVal, this.dense.length, NOT_FOUND);
                    this.length = longVal;
                    return;
                } else if (longVal >= 1431655764 || ((double) longVal) >= ((double) this.length) * GROW_FACTOR || !ensureCapacity((int) longVal)) {
                    this.denseOnly = false;
                } else {
                    this.length = longVal;
                    return;
                }
            }
            if (longVal < this.length) {
                if (this.length - longVal > PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
                    Object[] e = getIds();
                    for (String id : e) {
                        if (id instanceof String) {
                            String strId = id;
                            if (toArrayIndex(strId) >= longVal) {
                                delete(strId);
                            }
                        } else {
                            int index = ((Integer) id).intValue();
                            if (((long) index) >= longVal) {
                                delete(index);
                            }
                        }
                    }
                } else {
                    for (long i = longVal; i < this.length; i++) {
                        deleteElem(this, i);
                    }
                }
            }
            this.length = longVal;
        }
    }

    static long getLengthProperty(Context cx, Scriptable obj) {
        if (obj instanceof NativeString) {
            return (long) ((NativeString) obj).getLength();
        }
        if (obj instanceof NativeArray) {
            return ((NativeArray) obj).getLength();
        }
        Object len = ScriptableObject.getProperty(obj, "length");
        if (len == Scriptable.NOT_FOUND) {
            return 0;
        }
        return ScriptRuntime.toUint32(len);
    }

    private static Object setLengthProperty(Context cx, Scriptable target, long length) {
        Object len = ScriptRuntime.wrapNumber((double) length);
        ScriptableObject.putProperty(target, "length", len);
        return len;
    }

    private static void deleteElem(Scriptable target, long index) {
        int i = (int) index;
        if (((long) i) == index) {
            target.delete(i);
        } else {
            target.delete(Long.toString(index));
        }
    }

    private static Object getElem(Context cx, Scriptable target, long index) {
        Object elem = getRawElem(target, index);
        return elem != Scriptable.NOT_FOUND ? elem : Undefined.instance;
    }

    private static Object getRawElem(Scriptable target, long index) {
        if (index > 2147483647L) {
            return ScriptableObject.getProperty(target, Long.toString(index));
        }
        return ScriptableObject.getProperty(target, (int) index);
    }

    private static void defineElem(Context cx, Scriptable target, long index, Object value) {
        if (index > 2147483647L) {
            target.put(Long.toString(index), target, value);
        } else {
            target.put((int) index, target, value);
        }
    }

    private static void setElem(Context cx, Scriptable target, long index, Object value) {
        if (index > 2147483647L) {
            ScriptableObject.putProperty(target, Long.toString(index), value);
        } else {
            ScriptableObject.putProperty(target, (int) index, value);
        }
    }

    private static void setRawElem(Context cx, Scriptable target, long index, Object value) {
        if (value == NOT_FOUND) {
            deleteElem(target, index);
        } else {
            setElem(cx, target, index, value);
        }
    }

    private static String toStringHelper(Context cx, Scriptable scope, Scriptable thisObj, boolean toSource, boolean toLocale) {
        String separator;
        boolean toplevel;
        boolean iterating;
        long length = getLengthProperty(cx, thisObj);
        StringBuilder result = new StringBuilder(256);
        if (toSource) {
            result.append('[');
            separator = ", ";
        } else {
            separator = MiPushClient.ACCEPT_TIME_SEPARATOR;
        }
        boolean haslast = false;
        long i = 0;
        if (cx.iterating == null) {
            toplevel = true;
            iterating = false;
            cx.iterating = new ObjToIntMap(31);
        } else {
            toplevel = false;
            iterating = cx.iterating.has(thisObj);
        }
        if (!iterating) {
            cx.iterating.put(thisObj, 0);
            boolean skipUndefinedAndNull = !toSource || cx.getLanguageVersion() < 150;
            i = 0;
            while (i < length) {
                if (i > 0) {
                    result.append(separator);
                }
                Object elem = getRawElem(thisObj, i);
                if (elem == NOT_FOUND || (skipUndefinedAndNull && (elem == null || elem == Undefined.instance))) {
                    haslast = false;
                } else {
                    haslast = true;
                    if (toSource) {
                        try {
                            result.append(ScriptRuntime.uneval(cx, scope, elem));
                        } catch (Throwable th) {
                            if (toplevel) {
                                cx.iterating = null;
                            }
                        }
                    } else if (elem instanceof String) {
                        String s = (String) elem;
                        if (toSource) {
                            result.append(StringUtil.DOUBLE_QUOTE);
                            result.append(ScriptRuntime.escapeString(s));
                            result.append(StringUtil.DOUBLE_QUOTE);
                        } else {
                            result.append(s);
                        }
                    } else {
                        if (toLocale) {
                            elem = ScriptRuntime.getPropFunctionAndThis(elem, "toLocaleString", cx, scope).call(cx, scope, ScriptRuntime.lastStoredScriptable(cx), ScriptRuntime.emptyArgs);
                        }
                        result.append(ScriptRuntime.toString(elem));
                    }
                }
                i++;
            }
        }
        if (toplevel) {
            cx.iterating = null;
        }
        if (toSource) {
            if (haslast || i <= 0) {
                result.append(']');
            } else {
                result.append(", ]");
            }
        }
        return result.toString();
    }

    private static String js_join(Context cx, Scriptable thisObj, Object[] args) {
        long llength = getLengthProperty(cx, thisObj);
        int length = (int) llength;
        if (llength != ((long) length)) {
            throw Context.reportRuntimeError1("msg.arraylength.too.big", String.valueOf(llength));
        }
        String separator;
        StringBuilder sb;
        int i;
        Object temp;
        if (args.length < 1 || args[0] == Undefined.instance) {
            separator = MiPushClient.ACCEPT_TIME_SEPARATOR;
        } else {
            separator = ScriptRuntime.toString(args[0]);
        }
        if (thisObj instanceof NativeArray) {
            NativeArray na = (NativeArray) thisObj;
            if (na.denseOnly) {
                sb = new StringBuilder();
                for (i = 0; i < length; i++) {
                    if (i != 0) {
                        sb.append(separator);
                    }
                    if (i < na.dense.length) {
                        temp = na.dense[i];
                        if (!(temp == null || temp == Undefined.instance || temp == Scriptable.NOT_FOUND)) {
                            sb.append(ScriptRuntime.toString(temp));
                        }
                    }
                }
                return sb.toString();
            }
        }
        if (length == 0) {
            return "";
        }
        String[] buf = new String[length];
        int total_size = 0;
        for (i = 0; i != length; i++) {
            temp = getElem(cx, thisObj, (long) i);
            if (!(temp == null || temp == Undefined.instance)) {
                String str = ScriptRuntime.toString(temp);
                total_size += str.length();
                buf[i] = str;
            }
        }
        sb = new StringBuilder(total_size + ((length - 1) * separator.length()));
        for (i = 0; i != length; i++) {
            if (i != 0) {
                sb.append(separator);
            }
            str = buf[i];
            if (str != null) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    private static Scriptable js_reverse(Context cx, Scriptable thisObj, Object[] args) {
        if (thisObj instanceof NativeArray) {
            NativeArray na = (NativeArray) thisObj;
            if (na.denseOnly) {
                int i = 0;
                for (int j = ((int) na.length) - 1; i < j; j--) {
                    Object temp = na.dense[i];
                    na.dense[i] = na.dense[j];
                    na.dense[j] = temp;
                    i++;
                }
                return thisObj;
            }
        }
        long len = getLengthProperty(cx, thisObj);
        long half = len / 2;
        for (long i2 = 0; i2 < half; i2++) {
            long j2 = (len - i2) - 1;
            Object temp1 = getRawElem(thisObj, i2);
            setRawElem(cx, thisObj, i2, getRawElem(thisObj, j2));
            setRawElem(cx, thisObj, j2, temp1);
        }
        return thisObj;
    }

    private static Scriptable js_sort(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        Comparator<Object> comparator;
        if (args.length <= 0 || Undefined.instance == args[0]) {
            comparator = new 2();
        } else {
            comparator = new 1(new Object[2], ScriptRuntime.getValueFunctionAndThis(args[0], cx), cx, scope, ScriptRuntime.lastStoredScriptable(cx));
        }
        long llength = getLengthProperty(cx, thisObj);
        int length = (int) llength;
        if (llength != ((long) length)) {
            throw Context.reportRuntimeError1("msg.arraylength.too.big", String.valueOf(llength));
        }
        int i;
        Object[] working = new Object[length];
        for (i = 0; i != length; i++) {
            working[i] = getRawElem(thisObj, (long) i);
        }
        Arrays.sort(working, comparator);
        for (i = 0; i < length; i++) {
            setRawElem(cx, thisObj, (long) i, working[i]);
        }
        return thisObj;
    }

    private static Object js_push(Context cx, Scriptable thisObj, Object[] args) {
        int i;
        if (thisObj instanceof NativeArray) {
            NativeArray na = (NativeArray) thisObj;
            if (na.denseOnly && na.ensureCapacity(((int) na.length) + args.length)) {
                for (Object obj : args) {
                    Object[] objArr = na.dense;
                    long j = na.length;
                    na.length = 1 + j;
                    objArr[(int) j] = obj;
                }
                return ScriptRuntime.wrapNumber((double) na.length);
            }
        }
        long length = getLengthProperty(cx, thisObj);
        for (i = 0; i < args.length; i++) {
            setElem(cx, thisObj, ((long) i) + length, args[i]);
        }
        Object lengthObj = setLengthProperty(cx, thisObj, length + ((long) args.length));
        if (cx.getLanguageVersion() == 120) {
            return args.length == 0 ? Undefined.instance : args[args.length - 1];
        } else {
            return lengthObj;
        }
    }

    private static Object js_pop(Context cx, Scriptable thisObj, Object[] args) {
        Object result;
        if (thisObj instanceof NativeArray) {
            NativeArray na = (NativeArray) thisObj;
            if (na.denseOnly && na.length > 0) {
                na.length--;
                result = na.dense[(int) na.length];
                na.dense[(int) na.length] = NOT_FOUND;
                return result;
            }
        }
        long length = getLengthProperty(cx, thisObj);
        if (length > 0) {
            length--;
            result = getElem(cx, thisObj, length);
            deleteElem(thisObj, length);
        } else {
            result = Undefined.instance;
        }
        setLengthProperty(cx, thisObj, length);
        return result;
    }

    private static Object js_shift(Context cx, Scriptable thisObj, Object[] args) {
        Object result;
        if (thisObj instanceof NativeArray) {
            NativeArray na = (NativeArray) thisObj;
            if (na.denseOnly && na.length > 0) {
                na.length--;
                result = na.dense[0];
                System.arraycopy(na.dense, 1, na.dense, 0, (int) na.length);
                na.dense[(int) na.length] = NOT_FOUND;
                return result == NOT_FOUND ? Undefined.instance : result;
            }
        }
        long length = getLengthProperty(cx, thisObj);
        if (length > 0) {
            length--;
            result = getElem(cx, thisObj, 0);
            if (length > 0) {
                for (long i = 1; i <= length; i++) {
                    setRawElem(cx, thisObj, i - 1, getRawElem(thisObj, i));
                }
            }
            deleteElem(thisObj, length);
        } else {
            result = Undefined.instance;
        }
        setLengthProperty(cx, thisObj, length);
        return result;
    }

    private static Object js_unshift(Context cx, Scriptable thisObj, Object[] args) {
        int i;
        if (thisObj instanceof NativeArray) {
            NativeArray na = (NativeArray) thisObj;
            if (na.denseOnly && na.ensureCapacity(((int) na.length) + args.length)) {
                System.arraycopy(na.dense, 0, na.dense, args.length, (int) na.length);
                for (i = 0; i < args.length; i++) {
                    na.dense[i] = args[i];
                }
                na.length += (long) args.length;
                return ScriptRuntime.wrapNumber((double) na.length);
            }
        }
        long length = getLengthProperty(cx, thisObj);
        int argc = args.length;
        if (args.length > 0) {
            if (length > 0) {
                for (long last = length - 1; last >= 0; last--) {
                    Context context = cx;
                    Scriptable scriptable = thisObj;
                    setRawElem(context, scriptable, ((long) argc) + last, getRawElem(thisObj, last));
                }
            }
            for (i = 0; i < args.length; i++) {
                setElem(cx, thisObj, (long) i, args[i]);
            }
        }
        return setLengthProperty(cx, thisObj, length + ((long) args.length));
    }

    private static Object js_splice(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        NativeArray na = null;
        boolean denseMode = false;
        if (thisObj instanceof NativeArray) {
            na = (NativeArray) thisObj;
            denseMode = na.denseOnly;
        }
        scope = getTopLevelScope(scope);
        int argc = args.length;
        if (argc == 0) {
            return cx.newArray(scope, 0);
        }
        long count;
        Object elem;
        long last;
        long length = getLengthProperty(cx, thisObj);
        long begin = toSliceIndex(ScriptRuntime.toInteger(args[0]), length);
        argc--;
        if (args.length == 1) {
            count = length - begin;
        } else {
            double dcount = ScriptRuntime.toInteger(args[1]);
            if (dcount < 0.0d) {
                count = 0;
            } else if (dcount > ((double) (length - begin))) {
                count = length - begin;
            } else {
                count = (long) dcount;
            }
            argc--;
        }
        long end = begin + count;
        if (count != 0) {
            if (count == 1 && cx.getLanguageVersion() == 120) {
                elem = getElem(cx, thisObj, begin);
            } else if (denseMode) {
                int intLen = (int) (end - begin);
                Object[] copy = new Object[intLen];
                System.arraycopy(na.dense, (int) begin, copy, 0, intLen);
                elem = cx.newArray(scope, copy);
            } else {
                Scriptable resultArray = cx.newArray(scope, 0);
                for (last = begin; last != end; last++) {
                    Object temp = getRawElem(thisObj, last);
                    if (temp != NOT_FOUND) {
                        setElem(cx, resultArray, last - begin, temp);
                    }
                }
                setLengthProperty(cx, resultArray, end - begin);
                Scriptable scriptable = resultArray;
            }
        } else if (cx.getLanguageVersion() == 120) {
            elem = Undefined.instance;
        } else {
            elem = cx.newArray(scope, 0);
        }
        long delta = ((long) argc) - count;
        if (denseMode && length + delta < 2147483647L) {
            if (na.ensureCapacity((int) (length + delta))) {
                System.arraycopy(na.dense, (int) end, na.dense, (int) (((long) argc) + begin), (int) (length - end));
                if (argc > 0) {
                    System.arraycopy(args, 2, na.dense, (int) begin, argc);
                }
                if (delta < 0) {
                    Arrays.fill(na.dense, (int) (length + delta), (int) length, NOT_FOUND);
                }
                na.length = length + delta;
                return elem;
            }
        }
        if (delta > 0) {
            for (last = length - 1; last >= end; last--) {
                setRawElem(cx, thisObj, last + delta, getRawElem(thisObj, last));
            }
        } else if (delta < 0) {
            for (last = end; last < length; last++) {
                setRawElem(cx, thisObj, last + delta, getRawElem(thisObj, last));
            }
            for (long k = length + delta; k < length; k++) {
                deleteElem(thisObj, k);
            }
        }
        int argoffset = args.length - argc;
        for (int i = 0; i < argc; i++) {
            setElem(cx, thisObj, ((long) i) + begin, args[i + argoffset]);
        }
        setLengthProperty(cx, thisObj, length + delta);
        return elem;
    }

    private static Scriptable js_concat(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        int i;
        long length;
        long slot;
        Object temp;
        Scriptable result = cx.newArray(getTopLevelScope(scope), 0);
        if ((thisObj instanceof NativeArray) && (result instanceof NativeArray)) {
            NativeArray denseThis = (NativeArray) thisObj;
            NativeArray denseResult = (NativeArray) result;
            if (denseThis.denseOnly && denseResult.denseOnly) {
                NativeArray arg;
                boolean canUseDense = true;
                int length2 = (int) denseThis.length;
                for (i = 0; i < args.length && canUseDense; i++) {
                    if (args[i] instanceof NativeArray) {
                        arg = args[i];
                        canUseDense = arg.denseOnly;
                        length2 = (int) (((long) length2) + arg.length);
                    } else {
                        length2++;
                    }
                }
                if (canUseDense && denseResult.ensureCapacity(length2)) {
                    System.arraycopy(denseThis.dense, 0, denseResult.dense, 0, (int) denseThis.length);
                    int cursor = (int) denseThis.length;
                    for (i = 0; i < args.length && canUseDense; i++) {
                        if (args[i] instanceof NativeArray) {
                            arg = (NativeArray) args[i];
                            System.arraycopy(arg.dense, 0, denseResult.dense, cursor, (int) arg.length);
                            cursor += (int) arg.length;
                        } else {
                            int cursor2 = cursor + 1;
                            denseResult.dense[cursor] = args[i];
                            cursor = cursor2;
                        }
                    }
                    denseResult.length = (long) length2;
                    return result;
                }
            }
        }
        if (js_isArray(thisObj)) {
            length = getLengthProperty(cx, thisObj);
            slot = 0;
            while (slot < length) {
                temp = getRawElem(thisObj, slot);
                if (temp != NOT_FOUND) {
                    defineElem(cx, result, slot, temp);
                }
                slot++;
            }
        } else {
            long slot2 = 0 + 1;
            defineElem(cx, result, 0, thisObj);
            slot = slot2;
        }
        for (i = 0; i < args.length; i++) {
            if (js_isArray(args[i])) {
                Scriptable arg2 = args[i];
                length = getLengthProperty(cx, arg2);
                long j = 0;
                while (j < length) {
                    temp = getRawElem(arg2, j);
                    if (temp != NOT_FOUND) {
                        defineElem(cx, result, slot, temp);
                    }
                    j++;
                    slot++;
                }
            } else {
                slot2 = slot + 1;
                defineElem(cx, result, slot, args[i]);
                slot = slot2;
            }
        }
        setLengthProperty(cx, result, slot);
        return result;
    }

    private Scriptable js_slice(Context cx, Scriptable thisObj, Object[] args) {
        long begin;
        long end;
        Scriptable result = cx.newArray(getTopLevelScope(this), 0);
        long length = getLengthProperty(cx, thisObj);
        if (args.length == 0) {
            begin = 0;
            end = length;
        } else {
            begin = toSliceIndex(ScriptRuntime.toInteger(args[0]), length);
            if (args.length == 1 || args[1] == Undefined.instance) {
                end = length;
            } else {
                end = toSliceIndex(ScriptRuntime.toInteger(args[1]), length);
            }
        }
        for (long slot = begin; slot < end; slot++) {
            Object temp = getRawElem(thisObj, slot);
            if (temp != NOT_FOUND) {
                defineElem(cx, result, slot - begin, temp);
            }
        }
        setLengthProperty(cx, result, Math.max(0, end - begin));
        return result;
    }

    private static long toSliceIndex(double value, long length) {
        if (value < 0.0d) {
            if (((double) length) + value < 0.0d) {
                return 0;
            }
            return (long) (((double) length) + value);
        } else if (value > ((double) length)) {
            return length;
        } else {
            return (long) value;
        }
    }

    private static Object js_indexOf(Context cx, Scriptable thisObj, Object[] args) {
        long start;
        Object val;
        Object compareTo = args.length > 0 ? args[0] : Undefined.instance;
        long length = getLengthProperty(cx, thisObj);
        if (args.length < 2) {
            start = 0;
        } else {
            start = (long) ScriptRuntime.toInteger(args[1]);
            if (start < 0) {
                start += length;
                if (start < 0) {
                    start = 0;
                }
            }
            if (start > length - 1) {
                return NEGATIVE_ONE;
            }
        }
        if (thisObj instanceof NativeArray) {
            NativeArray na = (NativeArray) thisObj;
            if (na.denseOnly) {
                Scriptable proto = na.getPrototype();
                for (int i = (int) start; ((long) i) < length; i++) {
                    val = na.dense[i];
                    if (val == NOT_FOUND && proto != null) {
                        val = ScriptableObject.getProperty(proto, i);
                    }
                    if (val != NOT_FOUND && ScriptRuntime.shallowEq(val, compareTo)) {
                        return Long.valueOf((long) i);
                    }
                }
                return NEGATIVE_ONE;
            }
        }
        for (long i2 = start; i2 < length; i2++) {
            val = getRawElem(thisObj, i2);
            if (val != NOT_FOUND && ScriptRuntime.shallowEq(val, compareTo)) {
                return Long.valueOf(i2);
            }
        }
        return NEGATIVE_ONE;
    }

    private static Object js_lastIndexOf(Context cx, Scriptable thisObj, Object[] args) {
        long start;
        Object val;
        Object compareTo = args.length > 0 ? args[0] : Undefined.instance;
        long length = getLengthProperty(cx, thisObj);
        if (args.length < 2) {
            start = length - 1;
        } else {
            start = (long) ScriptRuntime.toInteger(args[1]);
            if (start >= length) {
                start = length - 1;
            } else if (start < 0) {
                start += length;
            }
            if (start < 0) {
                return NEGATIVE_ONE;
            }
        }
        if (thisObj instanceof NativeArray) {
            NativeArray na = (NativeArray) thisObj;
            if (na.denseOnly) {
                Scriptable proto = na.getPrototype();
                for (int i = (int) start; i >= 0; i--) {
                    val = na.dense[i];
                    if (val == NOT_FOUND && proto != null) {
                        val = ScriptableObject.getProperty(proto, i);
                    }
                    if (val != NOT_FOUND && ScriptRuntime.shallowEq(val, compareTo)) {
                        return Long.valueOf((long) i);
                    }
                }
                return NEGATIVE_ONE;
            }
        }
        for (long i2 = start; i2 >= 0; i2--) {
            val = getRawElem(thisObj, i2);
            if (val != NOT_FOUND && ScriptRuntime.shallowEq(val, compareTo)) {
                return Long.valueOf(i2);
            }
        }
        return NEGATIVE_ONE;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object iterativeMethod(org.mozilla.javascript.Context r24, int r25, org.mozilla.javascript.Scriptable r26, org.mozilla.javascript.Scriptable r27, java.lang.Object[] r28) {
        /*
        r0 = r24;
        r1 = r27;
        r16 = getLengthProperty(r0, r1);
        r0 = r28;
        r0 = r0.length;
        r21 = r0;
        if (r21 <= 0) goto L_0x0020;
    L_0x000f:
        r21 = 0;
        r5 = r28[r21];
    L_0x0013:
        if (r5 == 0) goto L_0x001b;
    L_0x0015:
        r0 = r5 instanceof org.mozilla.javascript.Function;
        r21 = r0;
        if (r21 != 0) goto L_0x0023;
    L_0x001b:
        r21 = org.mozilla.javascript.ScriptRuntime.notFunctionError(r5);
        throw r21;
    L_0x0020:
        r5 = org.mozilla.javascript.Undefined.instance;
        goto L_0x0013;
    L_0x0023:
        r21 = 22;
        r0 = r25;
        r1 = r21;
        if (r0 == r1) goto L_0x0033;
    L_0x002b:
        r21 = 23;
        r0 = r25;
        r1 = r21;
        if (r0 != r1) goto L_0x003e;
    L_0x0033:
        r0 = r5 instanceof org.mozilla.javascript.NativeFunction;
        r21 = r0;
        if (r21 != 0) goto L_0x003e;
    L_0x0039:
        r21 = org.mozilla.javascript.ScriptRuntime.notFunctionError(r5);
        throw r21;
    L_0x003e:
        r7 = r5;
        r7 = (org.mozilla.javascript.Function) r7;
        r11 = org.mozilla.javascript.ScriptableObject.getTopLevelScope(r7);
        r0 = r28;
        r0 = r0.length;
        r21 = r0;
        r22 = 2;
        r0 = r21;
        r1 = r22;
        if (r0 < r1) goto L_0x0064;
    L_0x0052:
        r21 = 1;
        r21 = r28[r21];
        if (r21 == 0) goto L_0x0064;
    L_0x0058:
        r21 = 1;
        r21 = r28[r21];
        r22 = org.mozilla.javascript.Undefined.instance;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x0084;
    L_0x0064:
        r20 = r11;
    L_0x0066:
        r21 = 22;
        r0 = r21;
        r1 = r25;
        if (r0 == r1) goto L_0x0076;
    L_0x006e:
        r21 = 23;
        r0 = r21;
        r1 = r25;
        if (r0 != r1) goto L_0x0093;
    L_0x0076:
        r0 = r20;
        r1 = r27;
        if (r0 != r1) goto L_0x0093;
    L_0x007c:
        r21 = "Array.prototype method called on null or undefined";
        r21 = org.mozilla.javascript.ScriptRuntime.typeError(r21);
        throw r21;
    L_0x0084:
        r21 = 1;
        r21 = r28[r21];
        r0 = r24;
        r1 = r26;
        r2 = r21;
        r20 = org.mozilla.javascript.ScriptRuntime.toObject(r0, r1, r2);
        goto L_0x0066;
    L_0x0093:
        r4 = 0;
        r21 = 18;
        r0 = r25;
        r1 = r21;
        if (r0 == r1) goto L_0x00a4;
    L_0x009c:
        r21 = 20;
        r0 = r25;
        r1 = r21;
        if (r0 != r1) goto L_0x00bb;
    L_0x00a4:
        r21 = 20;
        r0 = r25;
        r1 = r21;
        if (r0 != r1) goto L_0x00dd;
    L_0x00ac:
        r0 = r16;
        r0 = (int) r0;
        r19 = r0;
    L_0x00b1:
        r0 = r24;
        r1 = r26;
        r2 = r19;
        r4 = r0.newArray(r1, r2);
    L_0x00bb:
        r12 = 0;
        r8 = 0;
        r14 = r12;
    L_0x00c0:
        r21 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1));
        if (r21 >= 0) goto L_0x0145;
    L_0x00c4:
        r21 = 3;
        r0 = r21;
        r10 = new java.lang.Object[r0];
        r0 = r27;
        r6 = getRawElem(r0, r8);
        r21 = org.mozilla.javascript.Scriptable.NOT_FOUND;
        r0 = r21;
        if (r6 != r0) goto L_0x00e0;
    L_0x00d6:
        r12 = r14;
    L_0x00d7:
        r22 = 1;
        r8 = r8 + r22;
        r14 = r12;
        goto L_0x00c0;
    L_0x00dd:
        r19 = 0;
        goto L_0x00b1;
    L_0x00e0:
        r21 = 0;
        r10[r21] = r6;
        r21 = 1;
        r22 = java.lang.Long.valueOf(r8);
        r10[r21] = r22;
        r21 = 2;
        r10[r21] = r27;
        r0 = r24;
        r1 = r20;
        r18 = r7.call(r0, r11, r1, r10);
        switch(r25) {
            case 17: goto L_0x00fd;
            case 18: goto L_0x0106;
            case 19: goto L_0x011c;
            case 20: goto L_0x011e;
            case 21: goto L_0x0127;
            case 22: goto L_0x0130;
            case 23: goto L_0x0137;
            default: goto L_0x00fb;
        };
    L_0x00fb:
        r12 = r14;
        goto L_0x00d7;
    L_0x00fd:
        r21 = org.mozilla.javascript.ScriptRuntime.toBoolean(r18);
        if (r21 != 0) goto L_0x00fb;
    L_0x0103:
        r6 = java.lang.Boolean.FALSE;
    L_0x0105:
        return r6;
    L_0x0106:
        r21 = org.mozilla.javascript.ScriptRuntime.toBoolean(r18);
        if (r21 == 0) goto L_0x00fb;
    L_0x010c:
        r22 = 1;
        r12 = r14 + r22;
        r21 = 0;
        r21 = r10[r21];
        r0 = r24;
        r1 = r21;
        defineElem(r0, r4, r14, r1);
        goto L_0x00d7;
    L_0x011c:
        r12 = r14;
        goto L_0x00d7;
    L_0x011e:
        r0 = r24;
        r1 = r18;
        defineElem(r0, r4, r8, r1);
        r12 = r14;
        goto L_0x00d7;
    L_0x0127:
        r21 = org.mozilla.javascript.ScriptRuntime.toBoolean(r18);
        if (r21 == 0) goto L_0x00fb;
    L_0x012d:
        r6 = java.lang.Boolean.TRUE;
        goto L_0x0105;
    L_0x0130:
        r21 = org.mozilla.javascript.ScriptRuntime.toBoolean(r18);
        if (r21 == 0) goto L_0x00fb;
    L_0x0136:
        goto L_0x0105;
    L_0x0137:
        r21 = org.mozilla.javascript.ScriptRuntime.toBoolean(r18);
        if (r21 == 0) goto L_0x00fb;
    L_0x013d:
        r0 = (double) r8;
        r22 = r0;
        r6 = org.mozilla.javascript.ScriptRuntime.wrapNumber(r22);
        goto L_0x0105;
    L_0x0145:
        switch(r25) {
            case 17: goto L_0x014b;
            case 18: goto L_0x014e;
            case 19: goto L_0x0148;
            case 20: goto L_0x014e;
            case 21: goto L_0x0150;
            case 22: goto L_0x0148;
            case 23: goto L_0x0153;
            default: goto L_0x0148;
        };
    L_0x0148:
        r6 = org.mozilla.javascript.Undefined.instance;
        goto L_0x0105;
    L_0x014b:
        r6 = java.lang.Boolean.TRUE;
        goto L_0x0105;
    L_0x014e:
        r6 = r4;
        goto L_0x0105;
    L_0x0150:
        r6 = java.lang.Boolean.FALSE;
        goto L_0x0105;
    L_0x0153:
        r22 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r6 = org.mozilla.javascript.ScriptRuntime.wrapNumber(r22);
        goto L_0x0105;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeArray.iterativeMethod(org.mozilla.javascript.Context, int, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[]):java.lang.Object");
    }

    private static Object reduceMethod(Context cx, int id, Scriptable scope, Scriptable thisObj, Object[] args) {
        long length = getLengthProperty(cx, thisObj);
        Function callbackArg = args.length > 0 ? args[0] : Undefined.instance;
        if (callbackArg == null || !(callbackArg instanceof Function)) {
            throw ScriptRuntime.notFunctionError(callbackArg);
        }
        Function f = callbackArg;
        Scriptable parent = ScriptableObject.getTopLevelScope(f);
        boolean movingLeft = id == 24;
        Object value = args.length > 1 ? args[1] : Scriptable.NOT_FOUND;
        long i = 0;
        while (i < length) {
            Object elem = getRawElem(thisObj, movingLeft ? i : (length - 1) - i);
            if (elem != Scriptable.NOT_FOUND) {
                if (value == Scriptable.NOT_FOUND) {
                    value = elem;
                } else {
                    value = f.call(cx, parent, parent, new Object[]{value, elem, Long.valueOf(index), thisObj});
                }
            }
            i++;
        }
        if (value != Scriptable.NOT_FOUND) {
            return value;
        }
        throw ScriptRuntime.typeError0("msg.empty.array.reduce");
    }

    private static boolean js_isArray(Object o) {
        if (o instanceof Scriptable) {
            return "Array".equals(((Scriptable) o).getClassName());
        }
        return false;
    }

    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }

    public Object[] toArray() {
        return toArray(ScriptRuntime.emptyArgs);
    }

    public Object[] toArray(Object[] a) {
        long longLen = this.length;
        if (longLen > 2147483647L) {
            throw new IllegalStateException();
        }
        Object[] array;
        int len = (int) longLen;
        if (a.length >= len) {
            array = a;
        } else {
            array = (Object[]) Array.newInstance(a.getClass().getComponentType(), len);
        }
        for (int i = 0; i < len; i++) {
            array[i] = get(i);
        }
        return array;
    }

    public boolean containsAll(Collection c) {
        for (Object aC : c) {
            if (!contains(aC)) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        long longLen = this.length;
        if (longLen <= 2147483647L) {
            return (int) longLen;
        }
        throw new IllegalStateException();
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public Object get(long index) {
        if (index < 0 || index >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        Object value = getRawElem(this, index);
        if (value == Scriptable.NOT_FOUND || value == Undefined.instance) {
            return null;
        }
        if (value instanceof Wrapper) {
            return ((Wrapper) value).unwrap();
        }
        return value;
    }

    public Object get(int index) {
        return get((long) index);
    }

    public int indexOf(Object o) {
        long longLen = this.length;
        if (longLen > 2147483647L) {
            throw new IllegalStateException();
        }
        int len = (int) longLen;
        int i;
        if (o == null) {
            for (i = 0; i < len; i++) {
                if (get(i) == null) {
                    return i;
                }
            }
        } else {
            for (i = 0; i < len; i++) {
                if (o.equals(get(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        long longLen = this.length;
        if (longLen > 2147483647L) {
            throw new IllegalStateException();
        }
        int len = (int) longLen;
        int i;
        if (o == null) {
            for (i = len - 1; i >= 0; i--) {
                if (get(i) == null) {
                    return i;
                }
            }
        } else {
            for (i = len - 1; i >= 0; i--) {
                if (o.equals(get(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Iterator iterator() {
        return listIterator(0);
    }

    public ListIterator listIterator() {
        return listIterator(0);
    }

    public ListIterator listIterator(int start) {
        long longLen = this.length;
        if (longLen > 2147483647L) {
            throw new IllegalStateException();
        }
        int len = (int) longLen;
        if (start >= 0 && start <= len) {
            return new 3(this, start, len);
        }
        throw new IndexOutOfBoundsException("Index: " + start);
    }

    public boolean add(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void add(int index, Object element) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException();
    }

    public Object set(int index, Object element) {
        throw new UnsupportedOperationException();
    }

    public Object remove(int index) {
        throw new UnsupportedOperationException();
    }

    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    protected int findPrototypeId(String s) {
        int id = 0;
        String X = null;
        int c;
        switch (s.length()) {
            case 3:
                c = s.charAt(0);
                if (c == 109) {
                    if (s.charAt(2) == 'p' && s.charAt(1) == 'a') {
                        return 20;
                    }
                } else if (c == 112 && s.charAt(2) == 'p' && s.charAt(1) == 'o') {
                    return 9;
                }
            case 4:
                switch (s.charAt(2)) {
                    case 'i':
                        X = "join";
                        id = 5;
                        break;
                    case 'm':
                        X = "some";
                        id = 21;
                        break;
                    case 'n':
                        X = "find";
                        id = 22;
                        break;
                    case 'r':
                        X = "sort";
                        id = 7;
                        break;
                    case 's':
                        X = "push";
                        id = 8;
                        break;
                    default:
                        break;
                }
            case 5:
                c = s.charAt(1);
                if (c != 104) {
                    if (c != 108) {
                        if (c == 118) {
                            X = "every";
                            id = 17;
                            break;
                        }
                    }
                    X = "slice";
                    id = 14;
                    break;
                }
                X = "shift";
                id = 10;
                break;
                break;
            case 6:
                switch (s.charAt(0)) {
                    case 'c':
                        X = "concat";
                        id = 13;
                        break;
                    case 'f':
                        X = "filter";
                        id = 18;
                        break;
                    case 'r':
                        X = "reduce";
                        id = 24;
                        break;
                    case 's':
                        X = "splice";
                        id = 12;
                        break;
                    default:
                        break;
                }
            case 7:
                switch (s.charAt(0)) {
                    case 'f':
                        X = "forEach";
                        id = 19;
                        break;
                    case 'i':
                        X = "indexOf";
                        id = 15;
                        break;
                    case 'r':
                        X = "reverse";
                        id = 6;
                        break;
                    case 'u':
                        X = "unshift";
                        id = 11;
                        break;
                    default:
                        break;
                }
            case 8:
                c = s.charAt(3);
                if (c != 111) {
                    if (c == 116) {
                        X = "toString";
                        id = 2;
                        break;
                    }
                }
                X = "toSource";
                id = 4;
                break;
                break;
            case 9:
                X = "findIndex";
                id = 23;
                break;
            case 11:
                c = s.charAt(0);
                if (c != 99) {
                    if (c != 108) {
                        if (c == 114) {
                            X = "reduceRight";
                            id = 25;
                            break;
                        }
                    }
                    X = "lastIndexOf";
                    id = 16;
                    break;
                }
                X = "constructor";
                id = 1;
                break;
                break;
            case 14:
                X = "toLocaleString";
                id = 3;
                break;
        }
        if (X == null || X == s || X.equals(s)) {
            return id;
        }
        return 0;
    }
}
