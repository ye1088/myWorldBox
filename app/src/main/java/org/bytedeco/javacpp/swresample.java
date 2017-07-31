package org.bytedeco.javacpp;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.Opaque;
import org.bytedeco.javacpp.avutil.AVClass;
import org.bytedeco.javacpp.avutil.AVFrame;

public class swresample extends org.bytedeco.javacpp.presets.swresample {
    public static final int SWR_CH_MAX = 32;
    public static final int SWR_DITHER_NB = 72;
    public static final int SWR_DITHER_NONE = 0;
    public static final int SWR_DITHER_NS = 64;
    public static final int SWR_DITHER_NS_F_WEIGHTED = 66;
    public static final int SWR_DITHER_NS_HIGH_SHIBATA = 71;
    public static final int SWR_DITHER_NS_IMPROVED_E_WEIGHTED = 68;
    public static final int SWR_DITHER_NS_LIPSHITZ = 65;
    public static final int SWR_DITHER_NS_LOW_SHIBATA = 70;
    public static final int SWR_DITHER_NS_MODIFIED_E_WEIGHTED = 67;
    public static final int SWR_DITHER_NS_SHIBATA = 69;
    public static final int SWR_DITHER_RECTANGULAR = 1;
    public static final int SWR_DITHER_TRIANGULAR = 2;
    public static final int SWR_DITHER_TRIANGULAR_HIGHPASS = 3;
    public static final int SWR_ENGINE_NB = 2;
    public static final int SWR_ENGINE_SOXR = 1;
    public static final int SWR_ENGINE_SWR = 0;
    public static final int SWR_FILTER_TYPE_BLACKMAN_NUTTALL = 1;
    public static final int SWR_FILTER_TYPE_CUBIC = 0;
    public static final int SWR_FILTER_TYPE_KAISER = 2;
    public static final int SWR_FLAG_RESAMPLE = 1;

    @Opaque
    public static class SwrContext extends Pointer {
        public SwrContext(Pointer p) {
            super(p);
        }
    }

    public static native SwrContext swr_alloc();

    public static native SwrContext swr_alloc_set_opts(SwrContext swrContext, long j, @Cast({"AVSampleFormat"}) int i, int i2, long j2, @Cast({"AVSampleFormat"}) int i3, int i4, int i5, Pointer pointer);

    public static native void swr_close(SwrContext swrContext);

    public static native int swr_config_frame(SwrContext swrContext, @Const AVFrame aVFrame, @Const AVFrame aVFrame2);

    public static native int swr_convert(SwrContext swrContext, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, int i, @ByPtrPtr @Cast({"const uint8_t**"}) ByteBuffer byteBuffer2, int i2);

    public static native int swr_convert(SwrContext swrContext, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"const uint8_t**"}) BytePointer bytePointer2, int i2);

    public static native int swr_convert(SwrContext swrContext, @Cast({"uint8_t**"}) PointerPointer pointerPointer, int i, @Cast({"const uint8_t**"}) PointerPointer pointerPointer2, int i2);

    public static native int swr_convert(SwrContext swrContext, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int i, @ByPtrPtr @Cast({"const uint8_t**"}) byte[] bArr2, int i2);

    public static native int swr_convert_frame(SwrContext swrContext, AVFrame aVFrame, @Const AVFrame aVFrame2);

    public static native int swr_drop_output(SwrContext swrContext, int i);

    public static native void swr_free(@Cast({"SwrContext**"}) PointerPointer pointerPointer);

    public static native void swr_free(@ByPtrPtr SwrContext swrContext);

    @Const
    public static native AVClass swr_get_class();

    public static native long swr_get_delay(SwrContext swrContext, long j);

    public static native int swr_get_out_samples(SwrContext swrContext, int i);

    public static native int swr_init(SwrContext swrContext);

    public static native int swr_inject_silence(SwrContext swrContext, int i);

    public static native int swr_is_initialized(SwrContext swrContext);

    public static native long swr_next_pts(SwrContext swrContext, long j);

    public static native int swr_set_channel_mapping(SwrContext swrContext, @Const IntBuffer intBuffer);

    public static native int swr_set_channel_mapping(SwrContext swrContext, @Const IntPointer intPointer);

    public static native int swr_set_channel_mapping(SwrContext swrContext, @Const int[] iArr);

    public static native int swr_set_compensation(SwrContext swrContext, int i, int i2);

    public static native int swr_set_matrix(SwrContext swrContext, @Const DoubleBuffer doubleBuffer, int i);

    public static native int swr_set_matrix(SwrContext swrContext, @Const DoublePointer doublePointer, int i);

    public static native int swr_set_matrix(SwrContext swrContext, @Const double[] dArr, int i);

    @Cast({"const char*"})
    public static native BytePointer swresample_configuration();

    @Cast({"const char*"})
    public static native BytePointer swresample_license();

    @Cast({"unsigned"})
    public static native int swresample_version();

    static {
        Loader.load();
    }
}
