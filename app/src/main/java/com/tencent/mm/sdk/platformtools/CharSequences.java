package com.tencent.mm.sdk.platformtools;

public class CharSequences {

    final class AnonymousClass1 implements CharSequence {
        final /* synthetic */ byte[] u;

        AnonymousClass1(byte[] bArr) {
            this.u = bArr;
        }

        public final char charAt(int i) {
            return (char) this.u[i];
        }

        public final int length() {
            return this.u.length;
        }

        public final CharSequence subSequence(int i, int i2) {
            return CharSequences.forAsciiBytes(this.u, i, i2);
        }

        public final String toString() {
            return new String(this.u);
        }
    }

    final class AnonymousClass2 implements CharSequence {
        final /* synthetic */ byte[] u;
        final /* synthetic */ int v;
        final /* synthetic */ int w;

        AnonymousClass2(byte[] bArr, int i, int i2) {
            this.u = bArr;
            this.v = i;
            this.w = i2;
        }

        public final char charAt(int i) {
            return (char) this.u[this.v + i];
        }

        public final int length() {
            return this.w - this.v;
        }

        public final CharSequence subSequence(int i, int i2) {
            int i3 = i - this.v;
            int i4 = i2 - this.v;
            CharSequences.a(i3, i4, length());
            return CharSequences.forAsciiBytes(this.u, i3, i4);
        }

        public final String toString() {
            return new String(this.u, this.v, length());
        }
    }

    static void a(int i, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i2 < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i2 > i3) {
            throw new IndexOutOfBoundsException();
        } else if (i > i2) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static int compareToIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        int i = 0;
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i2 = length < length2 ? length : length2;
        int i3 = 0;
        while (i3 < i2) {
            int i4 = i3 + 1;
            char toLowerCase = Character.toLowerCase(charSequence.charAt(i3));
            i3 = i + 1;
            i = toLowerCase - Character.toLowerCase(charSequence2.charAt(i));
            if (i != 0) {
                return i;
            }
            i = i3;
            i3 = i4;
        }
        return length - length2;
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence.length() != charSequence2.length()) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static CharSequence forAsciiBytes(byte[] bArr) {
        return new AnonymousClass1(bArr);
    }

    public static CharSequence forAsciiBytes(byte[] bArr, int i, int i2) {
        a(i, i2, bArr.length);
        return new AnonymousClass2(bArr, i, i2);
    }
}
