package com.MCWorld.image.core.common.util;

import javax.annotation.Nullable;

/* compiled from: HashCodeUtil */
public class a {
    private static final int X = 31;

    public static int hashCode(@Nullable Object o1) {
        int i;
        if (o1 == null) {
            i = 0;
        } else {
            i = o1.hashCode();
        }
        return hashCode(i);
    }

    public static int b(@Nullable Object o1, @Nullable Object o2) {
        int i;
        int i2 = 0;
        if (o1 == null) {
            i = 0;
        } else {
            i = o1.hashCode();
        }
        if (o2 != null) {
            i2 = o2.hashCode();
        }
        return hashCode(i, i2);
    }

    public static int a(@Nullable Object o1, @Nullable Object o2, @Nullable Object o3) {
        int i;
        int i2;
        int i3 = 0;
        if (o1 == null) {
            i = 0;
        } else {
            i = o1.hashCode();
        }
        if (o2 == null) {
            i2 = 0;
        } else {
            i2 = o2.hashCode();
        }
        if (o3 != null) {
            i3 = o3.hashCode();
        }
        return c(i, i2, i3);
    }

    public static int a(@Nullable Object o1, @Nullable Object o2, @Nullable Object o3, @Nullable Object o4) {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        if (o1 == null) {
            i = 0;
        } else {
            i = o1.hashCode();
        }
        if (o2 == null) {
            i2 = 0;
        } else {
            i2 = o2.hashCode();
        }
        if (o3 == null) {
            i3 = 0;
        } else {
            i3 = o3.hashCode();
        }
        if (o4 != null) {
            i4 = o4.hashCode();
        }
        return b(i, i2, i3, i4);
    }

    public static int a(@Nullable Object o1, @Nullable Object o2, @Nullable Object o3, @Nullable Object o4, @Nullable Object o5) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        if (o1 == null) {
            i = 0;
        } else {
            i = o1.hashCode();
        }
        if (o2 == null) {
            i2 = 0;
        } else {
            i2 = o2.hashCode();
        }
        if (o3 == null) {
            i3 = 0;
        } else {
            i3 = o3.hashCode();
        }
        if (o4 == null) {
            i4 = 0;
        } else {
            i4 = o4.hashCode();
        }
        if (o5 != null) {
            i5 = o5.hashCode();
        }
        return a(i, i2, i3, i4, i5);
    }

    public static int a(@Nullable Object o1, @Nullable Object o2, @Nullable Object o3, @Nullable Object o4, @Nullable Object o5, @Nullable Object o6) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        if (o1 == null) {
            i = 0;
        } else {
            i = o1.hashCode();
        }
        if (o2 == null) {
            i2 = 0;
        } else {
            i2 = o2.hashCode();
        }
        if (o3 == null) {
            i3 = 0;
        } else {
            i3 = o3.hashCode();
        }
        if (o4 == null) {
            i4 = 0;
        } else {
            i4 = o4.hashCode();
        }
        if (o5 == null) {
            i5 = 0;
        } else {
            i5 = o5.hashCode();
        }
        if (o6 != null) {
            i6 = o6.hashCode();
        }
        return a(i, i2, i3, i4, i5, i6);
    }

    public static int hashCode(int i1) {
        return i1 + 31;
    }

    public static int hashCode(int i1, int i2) {
        return ((i1 + 31) * 31) + i2;
    }

    public static int c(int i1, int i2, int i3) {
        return ((((i1 + 31) * 31) + i2) * 31) + i3;
    }

    public static int b(int i1, int i2, int i3, int i4) {
        return ((((((i1 + 31) * 31) + i2) * 31) + i3) * 31) + i4;
    }

    public static int a(int i1, int i2, int i3, int i4, int i5) {
        return ((((((((i1 + 31) * 31) + i2) * 31) + i3) * 31) + i4) * 31) + i5;
    }

    public static int a(int i1, int i2, int i3, int i4, int i5, int i6) {
        return ((((((((((i1 + 31) * 31) + i2) * 31) + i3) * 31) + i4) * 31) + i5) * 31) + i6;
    }
}
