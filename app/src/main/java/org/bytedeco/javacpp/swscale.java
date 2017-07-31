package org.bytedeco.javacpp;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Opaque;
import org.bytedeco.javacpp.avutil.AVClass;

public class swscale extends org.bytedeco.javacpp.presets.swscale {
    public static final int SWS_ACCURATE_RND = 262144;
    public static final int SWS_AREA = 32;
    public static final int SWS_BICUBIC = 4;
    public static final int SWS_BICUBLIN = 64;
    public static final int SWS_BILINEAR = 2;
    public static final int SWS_BITEXACT = 524288;
    public static final int SWS_CPU_CAPS_3DNOW = 1073741824;
    public static final int SWS_CPU_CAPS_ALTIVEC = 268435456;
    public static final int SWS_CPU_CAPS_BFIN = 16777216;
    public static final int SWS_CPU_CAPS_MMX = Integer.MIN_VALUE;
    public static final int SWS_CPU_CAPS_MMX2 = 536870912;
    public static final int SWS_CPU_CAPS_MMXEXT = 536870912;
    public static final int SWS_CPU_CAPS_SSE2 = 33554432;
    public static final int SWS_CS_DEFAULT = 5;
    public static final int SWS_CS_FCC = 4;
    public static final int SWS_CS_ITU601 = 5;
    public static final int SWS_CS_ITU624 = 5;
    public static final int SWS_CS_ITU709 = 1;
    public static final int SWS_CS_SMPTE170M = 5;
    public static final int SWS_CS_SMPTE240M = 7;
    public static final int SWS_DIRECT_BGR = 32768;
    public static final int SWS_ERROR_DIFFUSION = 8388608;
    public static final int SWS_FAST_BILINEAR = 1;
    public static final int SWS_FULL_CHR_H_INP = 16384;
    public static final int SWS_FULL_CHR_H_INT = 8192;
    public static final int SWS_GAUSS = 128;
    public static final int SWS_LANCZOS = 512;
    public static final double SWS_MAX_REDUCE_CUTOFF = 0.002d;
    public static final int SWS_PARAM_DEFAULT = 123456;
    public static final int SWS_POINT = 16;
    public static final int SWS_PRINT_INFO = 4096;
    public static final int SWS_SINC = 256;
    public static final int SWS_SPLINE = 1024;
    public static final int SWS_SRC_V_CHR_DROP_MASK = 196608;
    public static final int SWS_SRC_V_CHR_DROP_SHIFT = 16;
    public static final int SWS_X = 8;

    @Opaque
    public static class SwsContext extends Pointer {
        public SwsContext(Pointer p) {
            super(p);
        }
    }

    public static native void sws_addVec(SwsVector swsVector, SwsVector swsVector2);

    public static native SwsVector sws_allocVec(int i);

    public static native SwsContext sws_alloc_context();

    public static native SwsVector sws_cloneVec(SwsVector swsVector);

    public static native void sws_convVec(SwsVector swsVector, SwsVector swsVector2);

    public static native void sws_convertPalette8ToPacked24(@Cast({"const uint8_t*"}) ByteBuffer byteBuffer, @Cast({"uint8_t*"}) ByteBuffer byteBuffer2, int i, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer3);

    public static native void sws_convertPalette8ToPacked24(@Cast({"const uint8_t*"}) BytePointer bytePointer, @Cast({"uint8_t*"}) BytePointer bytePointer2, int i, @Cast({"const uint8_t*"}) BytePointer bytePointer3);

    public static native void sws_convertPalette8ToPacked24(@Cast({"const uint8_t*"}) byte[] bArr, @Cast({"uint8_t*"}) byte[] bArr2, int i, @Cast({"const uint8_t*"}) byte[] bArr3);

    public static native void sws_convertPalette8ToPacked32(@Cast({"const uint8_t*"}) ByteBuffer byteBuffer, @Cast({"uint8_t*"}) ByteBuffer byteBuffer2, int i, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer3);

    public static native void sws_convertPalette8ToPacked32(@Cast({"const uint8_t*"}) BytePointer bytePointer, @Cast({"uint8_t*"}) BytePointer bytePointer2, int i, @Cast({"const uint8_t*"}) BytePointer bytePointer3);

    public static native void sws_convertPalette8ToPacked32(@Cast({"const uint8_t*"}) byte[] bArr, @Cast({"uint8_t*"}) byte[] bArr2, int i, @Cast({"const uint8_t*"}) byte[] bArr3);

    public static native void sws_freeContext(SwsContext swsContext);

    public static native void sws_freeFilter(SwsFilter swsFilter);

    public static native void sws_freeVec(SwsVector swsVector);

    public static native SwsContext sws_getCachedContext(SwsContext swsContext, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, @Cast({"AVPixelFormat"}) int i6, int i7, SwsFilter swsFilter, SwsFilter swsFilter2, @Const DoubleBuffer doubleBuffer);

    public static native SwsContext sws_getCachedContext(SwsContext swsContext, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, @Cast({"AVPixelFormat"}) int i6, int i7, SwsFilter swsFilter, SwsFilter swsFilter2, @Const DoublePointer doublePointer);

    public static native SwsContext sws_getCachedContext(SwsContext swsContext, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, @Cast({"AVPixelFormat"}) int i6, int i7, SwsFilter swsFilter, SwsFilter swsFilter2, @Const double[] dArr);

    @Const
    public static native IntPointer sws_getCoefficients(int i);

    public static native int sws_getColorspaceDetails(SwsContext swsContext, @ByPtrPtr IntBuffer intBuffer, IntBuffer intBuffer2, @ByPtrPtr IntBuffer intBuffer3, IntBuffer intBuffer4, IntBuffer intBuffer5, IntBuffer intBuffer6, IntBuffer intBuffer7);

    public static native int sws_getColorspaceDetails(SwsContext swsContext, @ByPtrPtr IntPointer intPointer, IntPointer intPointer2, @ByPtrPtr IntPointer intPointer3, IntPointer intPointer4, IntPointer intPointer5, IntPointer intPointer6, IntPointer intPointer7);

    public static native int sws_getColorspaceDetails(SwsContext swsContext, @Cast({"int**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"int**"}) PointerPointer pointerPointer2, IntPointer intPointer2, IntPointer intPointer3, IntPointer intPointer4, IntPointer intPointer5);

    public static native int sws_getColorspaceDetails(SwsContext swsContext, @ByPtrPtr int[] iArr, int[] iArr2, @ByPtrPtr int[] iArr3, int[] iArr4, int[] iArr5, int[] iArr6, int[] iArr7);

    public static native SwsVector sws_getConstVec(double d, int i);

    public static native SwsContext sws_getContext(int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, @Cast({"AVPixelFormat"}) int i6, int i7, SwsFilter swsFilter, SwsFilter swsFilter2, @Const DoubleBuffer doubleBuffer);

    public static native SwsContext sws_getContext(int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, @Cast({"AVPixelFormat"}) int i6, int i7, SwsFilter swsFilter, SwsFilter swsFilter2, @Const DoublePointer doublePointer);

    public static native SwsContext sws_getContext(int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, @Cast({"AVPixelFormat"}) int i6, int i7, SwsFilter swsFilter, SwsFilter swsFilter2, @Const double[] dArr);

    public static native SwsFilter sws_getDefaultFilter(float f, float f2, float f3, float f4, float f5, float f6, int i);

    public static native SwsVector sws_getGaussianVec(double d, double d2);

    public static native SwsVector sws_getIdentityVec();

    @Const
    public static native AVClass sws_get_class();

    public static native int sws_init_context(SwsContext swsContext, SwsFilter swsFilter, SwsFilter swsFilter2);

    public static native int sws_isSupportedEndiannessConversion(@Cast({"AVPixelFormat"}) int i);

    public static native int sws_isSupportedInput(@Cast({"AVPixelFormat"}) int i);

    public static native int sws_isSupportedOutput(@Cast({"AVPixelFormat"}) int i);

    public static native void sws_normalizeVec(SwsVector swsVector, double d);

    public static native void sws_printVec2(SwsVector swsVector, AVClass aVClass, int i);

    public static native int sws_scale(SwsContext swsContext, @ByPtrPtr @Cast({"const uint8_t*const*"}) ByteBuffer byteBuffer, @Const IntBuffer intBuffer, int i, int i2, @ByPtrPtr @Cast({"uint8_t*const*"}) ByteBuffer byteBuffer2, @Const IntBuffer intBuffer2);

    public static native int sws_scale(SwsContext swsContext, @ByPtrPtr @Cast({"const uint8_t*const*"}) BytePointer bytePointer, @Const IntPointer intPointer, int i, int i2, @ByPtrPtr @Cast({"uint8_t*const*"}) BytePointer bytePointer2, @Const IntPointer intPointer2);

    public static native int sws_scale(SwsContext swsContext, @Cast({"const uint8_t*const*"}) PointerPointer pointerPointer, @Const IntPointer intPointer, int i, int i2, @Cast({"uint8_t*const*"}) PointerPointer pointerPointer2, @Const IntPointer intPointer2);

    public static native int sws_scale(SwsContext swsContext, @ByPtrPtr @Cast({"const uint8_t*const*"}) byte[] bArr, @Const int[] iArr, int i, int i2, @ByPtrPtr @Cast({"uint8_t*const*"}) byte[] bArr2, @Const int[] iArr2);

    public static native void sws_scaleVec(SwsVector swsVector, double d);

    public static native int sws_setColorspaceDetails(SwsContext swsContext, @Const IntBuffer intBuffer, int i, @Const IntBuffer intBuffer2, int i2, int i3, int i4, int i5);

    public static native int sws_setColorspaceDetails(SwsContext swsContext, @Const IntPointer intPointer, int i, @Const IntPointer intPointer2, int i2, int i3, int i4, int i5);

    public static native int sws_setColorspaceDetails(SwsContext swsContext, @Const int[] iArr, int i, @Const int[] iArr2, int i2, int i3, int i4, int i5);

    public static native void sws_shiftVec(SwsVector swsVector, int i);

    public static native void sws_subVec(SwsVector swsVector, SwsVector swsVector2);

    @Cast({"const char*"})
    public static native BytePointer swscale_configuration();

    @Cast({"const char*"})
    public static native BytePointer swscale_license();

    @Cast({"unsigned"})
    public static native int swscale_version();

    static {
        Loader.load();
    }
}
