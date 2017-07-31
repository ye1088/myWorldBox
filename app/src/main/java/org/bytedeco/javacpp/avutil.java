package org.bytedeco.javacpp;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.NoOffset;
import org.bytedeco.javacpp.annotation.Opaque;

public class avutil extends org.bytedeco.javacpp.presets.avutil {
    public static final int AVCHROMA_LOC_BOTTOM = 6;
    public static final int AVCHROMA_LOC_BOTTOMLEFT = 5;
    public static final int AVCHROMA_LOC_CENTER = 2;
    public static final int AVCHROMA_LOC_LEFT = 1;
    public static final int AVCHROMA_LOC_NB = 7;
    public static final int AVCHROMA_LOC_TOP = 4;
    public static final int AVCHROMA_LOC_TOPLEFT = 3;
    public static final int AVCHROMA_LOC_UNSPECIFIED = 0;
    public static final int AVCOL_PRI_BT2020 = 9;
    public static final int AVCOL_PRI_BT470BG = 5;
    public static final int AVCOL_PRI_BT470M = 4;
    public static final int AVCOL_PRI_BT709 = 1;
    public static final int AVCOL_PRI_FILM = 8;
    public static final int AVCOL_PRI_NB = 10;
    public static final int AVCOL_PRI_RESERVED = 3;
    public static final int AVCOL_PRI_RESERVED0 = 0;
    public static final int AVCOL_PRI_SMPTE170M = 6;
    public static final int AVCOL_PRI_SMPTE240M = 7;
    public static final int AVCOL_PRI_UNSPECIFIED = 2;
    public static final int AVCOL_RANGE_JPEG = 2;
    public static final int AVCOL_RANGE_MPEG = 1;
    public static final int AVCOL_RANGE_NB = 3;
    public static final int AVCOL_RANGE_UNSPECIFIED = 0;
    public static final int AVCOL_SPC_BT2020_CL = 10;
    public static final int AVCOL_SPC_BT2020_NCL = 9;
    public static final int AVCOL_SPC_BT470BG = 5;
    public static final int AVCOL_SPC_BT709 = 1;
    public static final int AVCOL_SPC_FCC = 4;
    public static final int AVCOL_SPC_NB = 11;
    public static final int AVCOL_SPC_RESERVED = 3;
    public static final int AVCOL_SPC_RGB = 0;
    public static final int AVCOL_SPC_SMPTE170M = 6;
    public static final int AVCOL_SPC_SMPTE240M = 7;
    public static final int AVCOL_SPC_UNSPECIFIED = 2;
    public static final int AVCOL_SPC_YCGCO = 8;
    public static final int AVCOL_SPC_YCOCG = 8;
    public static final int AVCOL_TRC_BT1361_ECG = 12;
    public static final int AVCOL_TRC_BT2020_10 = 14;
    public static final int AVCOL_TRC_BT2020_12 = 15;
    public static final int AVCOL_TRC_BT709 = 1;
    public static final int AVCOL_TRC_GAMMA22 = 4;
    public static final int AVCOL_TRC_GAMMA28 = 5;
    public static final int AVCOL_TRC_IEC61966_2_1 = 13;
    public static final int AVCOL_TRC_IEC61966_2_4 = 11;
    public static final int AVCOL_TRC_LINEAR = 8;
    public static final int AVCOL_TRC_LOG = 9;
    public static final int AVCOL_TRC_LOG_SQRT = 10;
    public static final int AVCOL_TRC_NB = 16;
    public static final int AVCOL_TRC_RESERVED = 3;
    public static final int AVCOL_TRC_RESERVED0 = 0;
    public static final int AVCOL_TRC_SMPTE170M = 6;
    public static final int AVCOL_TRC_SMPTE240M = 7;
    public static final int AVCOL_TRC_UNSPECIFIED = 2;
    public static final int AVERROR_BSF_NOT_FOUND = AVERROR_BSF_NOT_FOUND();
    public static final int AVERROR_BUFFER_TOO_SMALL = AVERROR_BUFFER_TOO_SMALL();
    public static final int AVERROR_BUG = AVERROR_BUG();
    public static final int AVERROR_BUG2 = AVERROR_BUG2();
    public static final int AVERROR_DECODER_NOT_FOUND = AVERROR_DECODER_NOT_FOUND();
    public static final int AVERROR_DEMUXER_NOT_FOUND = AVERROR_DEMUXER_NOT_FOUND();
    public static final int AVERROR_ENCODER_NOT_FOUND = AVERROR_ENCODER_NOT_FOUND();
    public static final int AVERROR_EOF = AVERROR_EOF();
    public static final int AVERROR_EXIT = AVERROR_EXIT();
    public static final int AVERROR_EXPERIMENTAL = -733130664;
    public static final int AVERROR_EXTERNAL = AVERROR_EXTERNAL();
    public static final int AVERROR_FILTER_NOT_FOUND = AVERROR_FILTER_NOT_FOUND();
    public static final int AVERROR_HTTP_BAD_REQUEST = AVERROR_HTTP_BAD_REQUEST();
    public static final int AVERROR_HTTP_FORBIDDEN = AVERROR_HTTP_FORBIDDEN();
    public static final int AVERROR_HTTP_NOT_FOUND = AVERROR_HTTP_NOT_FOUND();
    public static final int AVERROR_HTTP_OTHER_4XX = AVERROR_HTTP_OTHER_4XX();
    public static final int AVERROR_HTTP_SERVER_ERROR = AVERROR_HTTP_SERVER_ERROR();
    public static final int AVERROR_HTTP_UNAUTHORIZED = AVERROR_HTTP_UNAUTHORIZED();
    public static final int AVERROR_INPUT_CHANGED = -1668179713;
    public static final int AVERROR_INVALIDDATA = AVERROR_INVALIDDATA();
    public static final int AVERROR_MUXER_NOT_FOUND = AVERROR_MUXER_NOT_FOUND();
    public static final int AVERROR_OPTION_NOT_FOUND = AVERROR_OPTION_NOT_FOUND();
    public static final int AVERROR_OUTPUT_CHANGED = -1668179714;
    public static final int AVERROR_PATCHWELCOME = AVERROR_PATCHWELCOME();
    public static final int AVERROR_PROTOCOL_NOT_FOUND = AVERROR_PROTOCOL_NOT_FOUND();
    public static final int AVERROR_STREAM_NOT_FOUND = AVERROR_STREAM_NOT_FOUND();
    public static final int AVERROR_UNKNOWN = AVERROR_UNKNOWN();
    public static final int AVMEDIA_TYPE_ATTACHMENT = 4;
    public static final int AVMEDIA_TYPE_AUDIO = 1;
    public static final int AVMEDIA_TYPE_DATA = 2;
    public static final int AVMEDIA_TYPE_NB = 5;
    public static final int AVMEDIA_TYPE_SUBTITLE = 3;
    public static final int AVMEDIA_TYPE_UNKNOWN = -1;
    public static final int AVMEDIA_TYPE_VIDEO = 0;
    public static final int AVPALETTE_COUNT = 256;
    public static final int AVPALETTE_SIZE = 1024;
    public static final int AV_AFD_14_9 = 11;
    public static final int AV_AFD_16_9 = 10;
    public static final int AV_AFD_16_9_SP_14_9 = 14;
    public static final int AV_AFD_4_3 = 9;
    public static final int AV_AFD_4_3_SP_14_9 = 13;
    public static final int AV_AFD_SAME = 8;
    public static final int AV_AFD_SP_4_3 = 15;
    public static final int AV_BUFFER_FLAG_READONLY = 1;
    public static final int AV_CH_BACK_CENTER = 256;
    public static final int AV_CH_BACK_LEFT = 16;
    public static final int AV_CH_BACK_RIGHT = 32;
    public static final int AV_CH_FRONT_CENTER = 4;
    public static final int AV_CH_FRONT_LEFT = 1;
    public static final int AV_CH_FRONT_LEFT_OF_CENTER = 64;
    public static final int AV_CH_FRONT_RIGHT = 2;
    public static final int AV_CH_FRONT_RIGHT_OF_CENTER = 128;
    public static final int AV_CH_LAYOUT_2POINT1 = 11;
    public static final int AV_CH_LAYOUT_2_1 = 259;
    public static final int AV_CH_LAYOUT_2_2 = 1539;
    public static final int AV_CH_LAYOUT_3POINT1 = 15;
    public static final int AV_CH_LAYOUT_4POINT0 = 263;
    public static final int AV_CH_LAYOUT_4POINT1 = 271;
    public static final int AV_CH_LAYOUT_5POINT0 = 1543;
    public static final int AV_CH_LAYOUT_5POINT0_BACK = 55;
    public static final int AV_CH_LAYOUT_5POINT1 = 1551;
    public static final int AV_CH_LAYOUT_5POINT1_BACK = 63;
    public static final int AV_CH_LAYOUT_6POINT0 = 1799;
    public static final int AV_CH_LAYOUT_6POINT0_FRONT = 1731;
    public static final int AV_CH_LAYOUT_6POINT1 = 1807;
    public static final int AV_CH_LAYOUT_6POINT1_BACK = 319;
    public static final int AV_CH_LAYOUT_6POINT1_FRONT = 1739;
    public static final int AV_CH_LAYOUT_7POINT0 = 1591;
    public static final int AV_CH_LAYOUT_7POINT0_FRONT = 1735;
    public static final int AV_CH_LAYOUT_7POINT1 = 1599;
    public static final int AV_CH_LAYOUT_7POINT1_WIDE = 1743;
    public static final int AV_CH_LAYOUT_7POINT1_WIDE_BACK = 255;
    public static final int AV_CH_LAYOUT_HEXAGONAL = 311;
    public static final int AV_CH_LAYOUT_MONO = 4;
    public static final long AV_CH_LAYOUT_NATIVE = Long.MIN_VALUE;
    public static final int AV_CH_LAYOUT_OCTAGONAL = 1847;
    public static final int AV_CH_LAYOUT_QUAD = 51;
    public static final int AV_CH_LAYOUT_STEREO = 3;
    public static final int AV_CH_LAYOUT_STEREO_DOWNMIX = 1610612736;
    public static final int AV_CH_LAYOUT_SURROUND = 7;
    public static final int AV_CH_LOW_FREQUENCY = 8;
    public static final long AV_CH_LOW_FREQUENCY_2 = 34359738368L;
    public static final int AV_CH_SIDE_LEFT = 512;
    public static final int AV_CH_SIDE_RIGHT = 1024;
    public static final int AV_CH_STEREO_LEFT = 536870912;
    public static final int AV_CH_STEREO_RIGHT = 1073741824;
    public static final long AV_CH_SURROUND_DIRECT_LEFT = 8589934592L;
    public static final long AV_CH_SURROUND_DIRECT_RIGHT = 17179869184L;
    public static final int AV_CH_TOP_BACK_CENTER = 65536;
    public static final int AV_CH_TOP_BACK_LEFT = 32768;
    public static final int AV_CH_TOP_BACK_RIGHT = 131072;
    public static final int AV_CH_TOP_CENTER = 2048;
    public static final int AV_CH_TOP_FRONT_CENTER = 8192;
    public static final int AV_CH_TOP_FRONT_LEFT = 4096;
    public static final int AV_CH_TOP_FRONT_RIGHT = 16384;
    public static final long AV_CH_WIDE_LEFT = 2147483648L;
    public static final long AV_CH_WIDE_RIGHT = 4294967296L;
    public static final int AV_CLASS_CATEGORY_BITSTREAM_FILTER = 8;
    public static final int AV_CLASS_CATEGORY_DECODER = 6;
    public static final int AV_CLASS_CATEGORY_DEMUXER = 4;
    public static final int AV_CLASS_CATEGORY_DEVICE_AUDIO_INPUT = 43;
    public static final int AV_CLASS_CATEGORY_DEVICE_AUDIO_OUTPUT = 42;
    public static final int AV_CLASS_CATEGORY_DEVICE_INPUT = 45;
    public static final int AV_CLASS_CATEGORY_DEVICE_OUTPUT = 44;
    public static final int AV_CLASS_CATEGORY_DEVICE_VIDEO_INPUT = 41;
    public static final int AV_CLASS_CATEGORY_DEVICE_VIDEO_OUTPUT = 40;
    public static final int AV_CLASS_CATEGORY_ENCODER = 5;
    public static final int AV_CLASS_CATEGORY_FILTER = 7;
    public static final int AV_CLASS_CATEGORY_INPUT = 1;
    public static final int AV_CLASS_CATEGORY_MUXER = 3;
    public static final int AV_CLASS_CATEGORY_NA = 0;
    public static final int AV_CLASS_CATEGORY_NB = 46;
    public static final int AV_CLASS_CATEGORY_OUTPUT = 2;
    public static final int AV_CLASS_CATEGORY_SWRESAMPLER = 10;
    public static final int AV_CLASS_CATEGORY_SWSCALER = 9;
    public static final int AV_CPU_FLAG_3DNOW = 4;
    public static final int AV_CPU_FLAG_3DNOWEXT = 32;
    public static final int AV_CPU_FLAG_ALTIVEC = 1;
    public static final int AV_CPU_FLAG_ARMV5TE = 1;
    public static final int AV_CPU_FLAG_ARMV6 = 2;
    public static final int AV_CPU_FLAG_ARMV6T2 = 4;
    public static final int AV_CPU_FLAG_ARMV8 = 64;
    public static final int AV_CPU_FLAG_ATOM = 268435456;
    public static final int AV_CPU_FLAG_AVX = 16384;
    public static final int AV_CPU_FLAG_AVX2 = 32768;
    public static final int AV_CPU_FLAG_AVXSLOW = 134217728;
    public static final int AV_CPU_FLAG_BMI1 = 131072;
    public static final int AV_CPU_FLAG_BMI2 = 262144;
    public static final int AV_CPU_FLAG_CMOV = 16781312;
    public static final int AV_CPU_FLAG_FMA3 = 65536;
    public static final int AV_CPU_FLAG_FMA4 = 2048;
    public static final int AV_CPU_FLAG_FORCE = Integer.MIN_VALUE;
    public static final int AV_CPU_FLAG_MMX = 1;
    public static final int AV_CPU_FLAG_MMX2 = 2;
    public static final int AV_CPU_FLAG_MMXEXT = 2;
    public static final int AV_CPU_FLAG_NEON = 32;
    public static final int AV_CPU_FLAG_POWER8 = 4;
    public static final int AV_CPU_FLAG_SETEND = 65536;
    public static final int AV_CPU_FLAG_SSE = 8;
    public static final int AV_CPU_FLAG_SSE2 = 16;
    public static final int AV_CPU_FLAG_SSE2SLOW = 1073741824;
    public static final int AV_CPU_FLAG_SSE3 = 64;
    public static final int AV_CPU_FLAG_SSE3SLOW = 536870912;
    public static final int AV_CPU_FLAG_SSE4 = 256;
    public static final int AV_CPU_FLAG_SSE42 = 512;
    public static final int AV_CPU_FLAG_SSSE3 = 128;
    public static final int AV_CPU_FLAG_VFP = 8;
    public static final int AV_CPU_FLAG_VFPV3 = 16;
    public static final int AV_CPU_FLAG_VSX = 2;
    public static final int AV_CPU_FLAG_XOP = 1024;
    public static final int AV_DICT_APPEND = 32;
    public static final int AV_DICT_DONT_OVERWRITE = 16;
    public static final int AV_DICT_DONT_STRDUP_KEY = 4;
    public static final int AV_DICT_DONT_STRDUP_VAL = 8;
    public static final int AV_DICT_IGNORE_SUFFIX = 2;
    public static final int AV_DICT_MATCH_CASE = 1;
    public static final int AV_DOWNMIX_TYPE_DPLII = 3;
    public static final int AV_DOWNMIX_TYPE_LORO = 1;
    public static final int AV_DOWNMIX_TYPE_LTRT = 2;
    public static final int AV_DOWNMIX_TYPE_NB = 4;
    public static final int AV_DOWNMIX_TYPE_UNKNOWN = 0;
    public static final int AV_ERROR_MAX_STRING_SIZE = 64;
    public static final int AV_FRAME_DATA_A53_CC = 1;
    public static final int AV_FRAME_DATA_AFD = 7;
    public static final int AV_FRAME_DATA_AUDIO_SERVICE_TYPE = 10;
    public static final int AV_FRAME_DATA_DISPLAYMATRIX = 6;
    public static final int AV_FRAME_DATA_DOWNMIX_INFO = 4;
    public static final int AV_FRAME_DATA_MATRIXENCODING = 3;
    public static final int AV_FRAME_DATA_MOTION_VECTORS = 8;
    public static final int AV_FRAME_DATA_PANSCAN = 0;
    public static final int AV_FRAME_DATA_REPLAYGAIN = 5;
    public static final int AV_FRAME_DATA_SKIP_SAMPLES = 9;
    public static final int AV_FRAME_DATA_STEREO3D = 2;
    public static final int AV_LOG_DEBUG = 48;
    public static final int AV_LOG_ERROR = 16;
    public static final int AV_LOG_FATAL = 8;
    public static final int AV_LOG_INFO = 32;
    public static final int AV_LOG_MAX_OFFSET = 56;
    public static final int AV_LOG_PANIC = 0;
    public static final int AV_LOG_PRINT_LEVEL = 2;
    public static final int AV_LOG_QUIET = -8;
    public static final int AV_LOG_SKIP_REPEATED = 1;
    public static final int AV_LOG_TRACE = 56;
    public static final int AV_LOG_VERBOSE = 40;
    public static final int AV_LOG_WARNING = 24;
    public static final int AV_MATRIX_ENCODING_DOLBY = 1;
    public static final int AV_MATRIX_ENCODING_DOLBYEX = 5;
    public static final int AV_MATRIX_ENCODING_DOLBYHEADPHONE = 6;
    public static final int AV_MATRIX_ENCODING_DPLII = 2;
    public static final int AV_MATRIX_ENCODING_DPLIIX = 3;
    public static final int AV_MATRIX_ENCODING_DPLIIZ = 4;
    public static final int AV_MATRIX_ENCODING_NB = 7;
    public static final int AV_MATRIX_ENCODING_NONE = 0;
    public static final long AV_NOPTS_VALUE = AV_NOPTS_VALUE();
    public static final int AV_OPT_FLAG_IMPLICIT_KEY = 1;
    public static final int AV_OPT_MULTI_COMPONENT_RANGE = 4096;
    public static final int AV_OPT_SEARCH_CHILDREN = 1;
    public static final int AV_OPT_SEARCH_FAKE_OBJ = 2;
    public static final int AV_OPT_SERIALIZE_OPT_FLAGS_EXACT = 2;
    public static final int AV_OPT_SERIALIZE_SKIP_DEFAULTS = 1;
    public static final int AV_OPT_TYPE_BINARY = 7;
    public static final int AV_OPT_TYPE_CHANNEL_LAYOUT = AV_OPT_TYPE_CHANNEL_LAYOUT();
    public static final int AV_OPT_TYPE_COLOR = AV_OPT_TYPE_COLOR();
    public static final int AV_OPT_TYPE_CONST = 128;
    public static final int AV_OPT_TYPE_DICT = 8;
    public static final int AV_OPT_TYPE_DOUBLE = 3;
    public static final int AV_OPT_TYPE_DURATION = AV_OPT_TYPE_DURATION();
    public static final int AV_OPT_TYPE_FLAGS = 0;
    public static final int AV_OPT_TYPE_FLOAT = 4;
    public static final int AV_OPT_TYPE_IMAGE_SIZE = AV_OPT_TYPE_IMAGE_SIZE();
    public static final int AV_OPT_TYPE_INT = 1;
    public static final int AV_OPT_TYPE_INT64 = 2;
    public static final int AV_OPT_TYPE_PIXEL_FMT = AV_OPT_TYPE_PIXEL_FMT();
    public static final int AV_OPT_TYPE_RATIONAL = 6;
    public static final int AV_OPT_TYPE_SAMPLE_FMT = AV_OPT_TYPE_SAMPLE_FMT();
    public static final int AV_OPT_TYPE_STRING = 5;
    public static final int AV_OPT_TYPE_VIDEO_RATE = AV_OPT_TYPE_VIDEO_RATE();
    public static final int AV_PICTURE_TYPE_B = 3;
    public static final int AV_PICTURE_TYPE_BI = 7;
    public static final int AV_PICTURE_TYPE_I = 1;
    public static final int AV_PICTURE_TYPE_NONE = 0;
    public static final int AV_PICTURE_TYPE_P = 2;
    public static final int AV_PICTURE_TYPE_S = 4;
    public static final int AV_PICTURE_TYPE_SI = 5;
    public static final int AV_PICTURE_TYPE_SP = 6;
    public static final int AV_PIX_FMT_0BGR = 297;
    public static final int AV_PIX_FMT_0BGR32 = AV_PIX_FMT_0BGR32();
    public static final int AV_PIX_FMT_0RGB = 295;
    public static final int AV_PIX_FMT_0RGB32 = AV_PIX_FMT_0RGB32();
    public static final int AV_PIX_FMT_ABGR = 29;
    public static final int AV_PIX_FMT_ARGB = 27;
    public static final int AV_PIX_FMT_BAYER_BGGR16 = AV_PIX_FMT_BAYER_BGGR16();
    public static final int AV_PIX_FMT_BAYER_BGGR16BE = 326;
    public static final int AV_PIX_FMT_BAYER_BGGR16LE = 325;
    public static final int AV_PIX_FMT_BAYER_BGGR8 = 321;
    public static final int AV_PIX_FMT_BAYER_GBRG16 = AV_PIX_FMT_BAYER_GBRG16();
    public static final int AV_PIX_FMT_BAYER_GBRG16BE = 330;
    public static final int AV_PIX_FMT_BAYER_GBRG16LE = 329;
    public static final int AV_PIX_FMT_BAYER_GBRG8 = 323;
    public static final int AV_PIX_FMT_BAYER_GRBG16 = AV_PIX_FMT_BAYER_GRBG16();
    public static final int AV_PIX_FMT_BAYER_GRBG16BE = 332;
    public static final int AV_PIX_FMT_BAYER_GRBG16LE = 331;
    public static final int AV_PIX_FMT_BAYER_GRBG8 = 324;
    public static final int AV_PIX_FMT_BAYER_RGGB16 = AV_PIX_FMT_BAYER_RGGB16();
    public static final int AV_PIX_FMT_BAYER_RGGB16BE = 328;
    public static final int AV_PIX_FMT_BAYER_RGGB16LE = 327;
    public static final int AV_PIX_FMT_BAYER_RGGB8 = 322;
    public static final int AV_PIX_FMT_BGR0 = 298;
    public static final int AV_PIX_FMT_BGR24 = 3;
    public static final int AV_PIX_FMT_BGR32 = AV_PIX_FMT_BGR32();
    public static final int AV_PIX_FMT_BGR32_1 = AV_PIX_FMT_BGR32_1();
    public static final int AV_PIX_FMT_BGR4 = 20;
    public static final int AV_PIX_FMT_BGR444 = AV_PIX_FMT_BGR444();
    public static final int AV_PIX_FMT_BGR444BE = 65;
    public static final int AV_PIX_FMT_BGR444LE = 64;
    public static final int AV_PIX_FMT_BGR48 = AV_PIX_FMT_BGR48();
    public static final int AV_PIX_FMT_BGR48BE = 67;
    public static final int AV_PIX_FMT_BGR48LE = 68;
    public static final int AV_PIX_FMT_BGR4_BYTE = 21;
    public static final int AV_PIX_FMT_BGR555 = AV_PIX_FMT_BGR555();
    public static final int AV_PIX_FMT_BGR555BE = 49;
    public static final int AV_PIX_FMT_BGR555LE = 50;
    public static final int AV_PIX_FMT_BGR565 = AV_PIX_FMT_BGR565();
    public static final int AV_PIX_FMT_BGR565BE = 47;
    public static final int AV_PIX_FMT_BGR565LE = 48;
    public static final int AV_PIX_FMT_BGR8 = 19;
    public static final int AV_PIX_FMT_BGRA = 30;
    public static final int AV_PIX_FMT_BGRA64 = AV_PIX_FMT_BGRA64();
    public static final int AV_PIX_FMT_BGRA64BE = 293;
    public static final int AV_PIX_FMT_BGRA64BE_LIBAV = 117;
    public static final int AV_PIX_FMT_BGRA64LE = 294;
    public static final int AV_PIX_FMT_BGRA64LE_LIBAV = 118;
    public static final int AV_PIX_FMT_D3D11VA_VLD = 128;
    public static final int AV_PIX_FMT_DXVA2_VLD = 61;
    public static final int AV_PIX_FMT_FLAG_ALPHA = 128;
    public static final int AV_PIX_FMT_FLAG_BE = 1;
    public static final int AV_PIX_FMT_FLAG_BITSTREAM = 4;
    public static final int AV_PIX_FMT_FLAG_HWACCEL = 8;
    public static final int AV_PIX_FMT_FLAG_PAL = 2;
    public static final int AV_PIX_FMT_FLAG_PLANAR = 16;
    public static final int AV_PIX_FMT_FLAG_PSEUDOPAL = 64;
    public static final int AV_PIX_FMT_FLAG_RGB = 32;
    public static final int AV_PIX_FMT_GBR24P = 82;
    public static final int AV_PIX_FMT_GBRAP = 317;
    public static final int AV_PIX_FMT_GBRAP16 = AV_PIX_FMT_GBRAP16();
    public static final int AV_PIX_FMT_GBRAP16BE = 318;
    public static final int AV_PIX_FMT_GBRAP16BE_LIBAV = 124;
    public static final int AV_PIX_FMT_GBRAP16LE = 319;
    public static final int AV_PIX_FMT_GBRAP16LE_LIBAV = 125;
    public static final int AV_PIX_FMT_GBRAP_LIBAV = 123;
    public static final int AV_PIX_FMT_GBRP = 82;
    public static final int AV_PIX_FMT_GBRP10 = AV_PIX_FMT_GBRP10();
    public static final int AV_PIX_FMT_GBRP10BE = 85;
    public static final int AV_PIX_FMT_GBRP10LE = 86;
    public static final int AV_PIX_FMT_GBRP12 = AV_PIX_FMT_GBRP12();
    public static final int AV_PIX_FMT_GBRP12BE = 313;
    public static final int AV_PIX_FMT_GBRP12LE = 314;
    public static final int AV_PIX_FMT_GBRP14 = AV_PIX_FMT_GBRP14();
    public static final int AV_PIX_FMT_GBRP14BE = 315;
    public static final int AV_PIX_FMT_GBRP14LE = 316;
    public static final int AV_PIX_FMT_GBRP16 = AV_PIX_FMT_GBRP16();
    public static final int AV_PIX_FMT_GBRP16BE = 87;
    public static final int AV_PIX_FMT_GBRP16LE = 88;
    public static final int AV_PIX_FMT_GBRP9 = AV_PIX_FMT_GBRP9();
    public static final int AV_PIX_FMT_GBRP9BE = 83;
    public static final int AV_PIX_FMT_GBRP9LE = 84;
    public static final int AV_PIX_FMT_GRAY16 = AV_PIX_FMT_GRAY16();
    public static final int AV_PIX_FMT_GRAY16BE = 31;
    public static final int AV_PIX_FMT_GRAY16LE = 32;
    public static final int AV_PIX_FMT_GRAY8 = 8;
    public static final int AV_PIX_FMT_GRAY8A = 66;
    public static final int AV_PIX_FMT_MMAL = 127;
    public static final int AV_PIX_FMT_MONOBLACK = 10;
    public static final int AV_PIX_FMT_MONOWHITE = 9;
    public static final int AV_PIX_FMT_NB = 337;
    public static final int AV_PIX_FMT_NONE = -1;
    public static final int AV_PIX_FMT_NV12 = 25;
    public static final int AV_PIX_FMT_NV16 = 112;
    public static final int AV_PIX_FMT_NV20 = AV_PIX_FMT_NV20();
    public static final int AV_PIX_FMT_NV20BE = 114;
    public static final int AV_PIX_FMT_NV20LE = 113;
    public static final int AV_PIX_FMT_NV21 = 26;
    public static final int AV_PIX_FMT_PAL8 = 11;
    public static final int AV_PIX_FMT_QSV = 126;
    public static final int AV_PIX_FMT_RGB0 = 296;
    public static final int AV_PIX_FMT_RGB24 = 2;
    public static final int AV_PIX_FMT_RGB32 = AV_PIX_FMT_RGB32();
    public static final int AV_PIX_FMT_RGB32_1 = AV_PIX_FMT_RGB32_1();
    public static final int AV_PIX_FMT_RGB4 = 23;
    public static final int AV_PIX_FMT_RGB444 = AV_PIX_FMT_RGB444();
    public static final int AV_PIX_FMT_RGB444BE = 63;
    public static final int AV_PIX_FMT_RGB444LE = 62;
    public static final int AV_PIX_FMT_RGB48 = AV_PIX_FMT_RGB48();
    public static final int AV_PIX_FMT_RGB48BE = 41;
    public static final int AV_PIX_FMT_RGB48LE = 42;
    public static final int AV_PIX_FMT_RGB4_BYTE = 24;
    public static final int AV_PIX_FMT_RGB555 = AV_PIX_FMT_RGB555();
    public static final int AV_PIX_FMT_RGB555BE = 45;
    public static final int AV_PIX_FMT_RGB555LE = 46;
    public static final int AV_PIX_FMT_RGB565 = AV_PIX_FMT_RGB565();
    public static final int AV_PIX_FMT_RGB565BE = 43;
    public static final int AV_PIX_FMT_RGB565LE = 44;
    public static final int AV_PIX_FMT_RGB8 = 22;
    public static final int AV_PIX_FMT_RGBA = 28;
    public static final int AV_PIX_FMT_RGBA64 = AV_PIX_FMT_RGBA64();
    public static final int AV_PIX_FMT_RGBA64BE = 291;
    public static final int AV_PIX_FMT_RGBA64BE_LIBAV = 115;
    public static final int AV_PIX_FMT_RGBA64LE = 292;
    public static final int AV_PIX_FMT_RGBA64LE_LIBAV = 116;
    public static final int AV_PIX_FMT_UYVY422 = 17;
    public static final int AV_PIX_FMT_UYYVYY411 = 18;
    public static final int AV_PIX_FMT_VAAPI_IDCT = 52;
    public static final int AV_PIX_FMT_VAAPI_MOCO = 51;
    public static final int AV_PIX_FMT_VAAPI_VLD = 53;
    public static final int AV_PIX_FMT_VDA = 120;
    public static final int AV_PIX_FMT_VDA_VLD = 81;
    public static final int AV_PIX_FMT_VDPAU = 109;
    public static final int AV_PIX_FMT_VDPAU_H264 = 36;
    public static final int AV_PIX_FMT_VDPAU_MPEG1 = 37;
    public static final int AV_PIX_FMT_VDPAU_MPEG2 = 38;
    public static final int AV_PIX_FMT_VDPAU_MPEG4 = 60;
    public static final int AV_PIX_FMT_VDPAU_VC1 = 40;
    public static final int AV_PIX_FMT_VDPAU_WMV3 = 39;
    public static final int AV_PIX_FMT_XVMC = 16;
    public static final int AV_PIX_FMT_XVMC_MPEG2_IDCT = 16;
    public static final int AV_PIX_FMT_XVMC_MPEG2_MC = 15;
    public static final int AV_PIX_FMT_XYZ12 = AV_PIX_FMT_XYZ12();
    public static final int AV_PIX_FMT_XYZ12BE = 111;
    public static final int AV_PIX_FMT_XYZ12LE = 110;
    public static final int AV_PIX_FMT_Y400A = 66;
    public static final int AV_PIX_FMT_YA16 = AV_PIX_FMT_YA16();
    public static final int AV_PIX_FMT_YA16BE = 121;
    public static final int AV_PIX_FMT_YA16LE = 122;
    public static final int AV_PIX_FMT_YA8 = 66;
    public static final int AV_PIX_FMT_YUV410P = 6;
    public static final int AV_PIX_FMT_YUV411P = 7;
    public static final int AV_PIX_FMT_YUV420P = 0;
    public static final int AV_PIX_FMT_YUV420P10 = AV_PIX_FMT_YUV420P10();
    public static final int AV_PIX_FMT_YUV420P10BE = 71;
    public static final int AV_PIX_FMT_YUV420P10LE = 72;
    public static final int AV_PIX_FMT_YUV420P12 = AV_PIX_FMT_YUV420P12();
    public static final int AV_PIX_FMT_YUV420P12BE = 301;
    public static final int AV_PIX_FMT_YUV420P12LE = 302;
    public static final int AV_PIX_FMT_YUV420P14 = AV_PIX_FMT_YUV420P14();
    public static final int AV_PIX_FMT_YUV420P14BE = 303;
    public static final int AV_PIX_FMT_YUV420P14LE = 304;
    public static final int AV_PIX_FMT_YUV420P16 = AV_PIX_FMT_YUV420P16();
    public static final int AV_PIX_FMT_YUV420P16BE = 55;
    public static final int AV_PIX_FMT_YUV420P16LE = 54;
    public static final int AV_PIX_FMT_YUV420P9 = AV_PIX_FMT_YUV420P9();
    public static final int AV_PIX_FMT_YUV420P9BE = 69;
    public static final int AV_PIX_FMT_YUV420P9LE = 70;
    public static final int AV_PIX_FMT_YUV422P = 4;
    public static final int AV_PIX_FMT_YUV422P10 = AV_PIX_FMT_YUV422P10();
    public static final int AV_PIX_FMT_YUV422P10BE = 73;
    public static final int AV_PIX_FMT_YUV422P10LE = 74;
    public static final int AV_PIX_FMT_YUV422P12 = AV_PIX_FMT_YUV422P12();
    public static final int AV_PIX_FMT_YUV422P12BE = 305;
    public static final int AV_PIX_FMT_YUV422P12LE = 306;
    public static final int AV_PIX_FMT_YUV422P14 = AV_PIX_FMT_YUV422P14();
    public static final int AV_PIX_FMT_YUV422P14BE = 307;
    public static final int AV_PIX_FMT_YUV422P14LE = 308;
    public static final int AV_PIX_FMT_YUV422P16 = AV_PIX_FMT_YUV422P16();
    public static final int AV_PIX_FMT_YUV422P16BE = 57;
    public static final int AV_PIX_FMT_YUV422P16LE = 56;
    public static final int AV_PIX_FMT_YUV422P9 = AV_PIX_FMT_YUV422P9();
    public static final int AV_PIX_FMT_YUV422P9BE = 79;
    public static final int AV_PIX_FMT_YUV422P9LE = 80;
    public static final int AV_PIX_FMT_YUV440P = 33;
    public static final int AV_PIX_FMT_YUV440P10 = AV_PIX_FMT_YUV440P10();
    public static final int AV_PIX_FMT_YUV440P10BE = 334;
    public static final int AV_PIX_FMT_YUV440P10LE = 333;
    public static final int AV_PIX_FMT_YUV440P12 = AV_PIX_FMT_YUV440P12();
    public static final int AV_PIX_FMT_YUV440P12BE = 336;
    public static final int AV_PIX_FMT_YUV440P12LE = 335;
    public static final int AV_PIX_FMT_YUV444P = 5;
    public static final int AV_PIX_FMT_YUV444P10 = AV_PIX_FMT_YUV444P10();
    public static final int AV_PIX_FMT_YUV444P10BE = 77;
    public static final int AV_PIX_FMT_YUV444P10LE = 78;
    public static final int AV_PIX_FMT_YUV444P12 = AV_PIX_FMT_YUV444P12();
    public static final int AV_PIX_FMT_YUV444P12BE = 309;
    public static final int AV_PIX_FMT_YUV444P12LE = 310;
    public static final int AV_PIX_FMT_YUV444P14 = AV_PIX_FMT_YUV444P14();
    public static final int AV_PIX_FMT_YUV444P14BE = 311;
    public static final int AV_PIX_FMT_YUV444P14LE = 312;
    public static final int AV_PIX_FMT_YUV444P16 = AV_PIX_FMT_YUV444P16();
    public static final int AV_PIX_FMT_YUV444P16BE = 59;
    public static final int AV_PIX_FMT_YUV444P16LE = 58;
    public static final int AV_PIX_FMT_YUV444P9 = AV_PIX_FMT_YUV444P9();
    public static final int AV_PIX_FMT_YUV444P9BE = 75;
    public static final int AV_PIX_FMT_YUV444P9LE = 76;
    public static final int AV_PIX_FMT_YUVA420P = 35;
    public static final int AV_PIX_FMT_YUVA420P10 = AV_PIX_FMT_YUVA420P10();
    public static final int AV_PIX_FMT_YUVA420P10BE = 97;
    public static final int AV_PIX_FMT_YUVA420P10LE = 98;
    public static final int AV_PIX_FMT_YUVA420P16 = AV_PIX_FMT_YUVA420P16();
    public static final int AV_PIX_FMT_YUVA420P16BE = 103;
    public static final int AV_PIX_FMT_YUVA420P16LE = 104;
    public static final int AV_PIX_FMT_YUVA420P9 = AV_PIX_FMT_YUVA420P9();
    public static final int AV_PIX_FMT_YUVA420P9BE = 91;
    public static final int AV_PIX_FMT_YUVA420P9LE = 92;
    public static final int AV_PIX_FMT_YUVA422P = 300;
    public static final int AV_PIX_FMT_YUVA422P10 = AV_PIX_FMT_YUVA422P10();
    public static final int AV_PIX_FMT_YUVA422P10BE = 99;
    public static final int AV_PIX_FMT_YUVA422P10LE = 100;
    public static final int AV_PIX_FMT_YUVA422P16 = AV_PIX_FMT_YUVA422P16();
    public static final int AV_PIX_FMT_YUVA422P16BE = 105;
    public static final int AV_PIX_FMT_YUVA422P16LE = 106;
    public static final int AV_PIX_FMT_YUVA422P9 = AV_PIX_FMT_YUVA422P9();
    public static final int AV_PIX_FMT_YUVA422P9BE = 93;
    public static final int AV_PIX_FMT_YUVA422P9LE = 94;
    public static final int AV_PIX_FMT_YUVA422P_LIBAV = 89;
    public static final int AV_PIX_FMT_YUVA444P = 299;
    public static final int AV_PIX_FMT_YUVA444P10 = AV_PIX_FMT_YUVA444P10();
    public static final int AV_PIX_FMT_YUVA444P10BE = 101;
    public static final int AV_PIX_FMT_YUVA444P10LE = 102;
    public static final int AV_PIX_FMT_YUVA444P16 = AV_PIX_FMT_YUVA444P16();
    public static final int AV_PIX_FMT_YUVA444P16BE = 107;
    public static final int AV_PIX_FMT_YUVA444P16LE = 108;
    public static final int AV_PIX_FMT_YUVA444P9 = AV_PIX_FMT_YUVA444P9();
    public static final int AV_PIX_FMT_YUVA444P9BE = 95;
    public static final int AV_PIX_FMT_YUVA444P9LE = 96;
    public static final int AV_PIX_FMT_YUVA444P_LIBAV = 90;
    public static final int AV_PIX_FMT_YUVJ411P = 320;
    public static final int AV_PIX_FMT_YUVJ420P = 12;
    public static final int AV_PIX_FMT_YUVJ422P = 13;
    public static final int AV_PIX_FMT_YUVJ440P = 34;
    public static final int AV_PIX_FMT_YUVJ444P = 14;
    public static final int AV_PIX_FMT_YUYV422 = 1;
    public static final int AV_PIX_FMT_YVYU422 = 119;
    public static final int AV_ROUND_DOWN = 2;
    public static final int AV_ROUND_INF = 1;
    public static final int AV_ROUND_NEAR_INF = 5;
    public static final int AV_ROUND_PASS_MINMAX = 8192;
    public static final int AV_ROUND_UP = 3;
    public static final int AV_ROUND_ZERO = 0;
    public static final int AV_SAMPLE_FMT_DBL = 4;
    public static final int AV_SAMPLE_FMT_DBLP = 9;
    public static final int AV_SAMPLE_FMT_FLT = 3;
    public static final int AV_SAMPLE_FMT_FLTP = 8;
    public static final int AV_SAMPLE_FMT_NB = 10;
    public static final int AV_SAMPLE_FMT_NONE = -1;
    public static final int AV_SAMPLE_FMT_S16 = 1;
    public static final int AV_SAMPLE_FMT_S16P = 6;
    public static final int AV_SAMPLE_FMT_S32 = 2;
    public static final int AV_SAMPLE_FMT_S32P = 7;
    public static final int AV_SAMPLE_FMT_U8 = 0;
    public static final int AV_SAMPLE_FMT_U8P = 5;
    public static final int AV_STEREO3D_2D = 0;
    public static final int AV_STEREO3D_CHECKERBOARD = 4;
    public static final int AV_STEREO3D_COLUMNS = 7;
    public static final int AV_STEREO3D_FLAG_INVERT = 1;
    public static final int AV_STEREO3D_FRAMESEQUENCE = 3;
    public static final int AV_STEREO3D_LINES = 6;
    public static final int AV_STEREO3D_SIDEBYSIDE = 1;
    public static final int AV_STEREO3D_SIDEBYSIDE_QUINCUNX = 5;
    public static final int AV_STEREO3D_TOPBOTTOM = 2;
    public static final int AV_TIME_BASE = 1000000;
    public static final String FFMPEG_VERSION = "2.7.1";
    public static final int FF_LAMBDA_MAX = 32767;
    public static final int FF_LAMBDA_SCALE = 128;
    public static final int FF_LAMBDA_SHIFT = 7;
    public static final int FF_LOSS_ALPHA = 8;
    public static final int FF_LOSS_CHROMA = 32;
    public static final int FF_LOSS_COLORQUANT = 16;
    public static final int FF_LOSS_COLORSPACE = 4;
    public static final int FF_LOSS_DEPTH = 2;
    public static final int FF_LOSS_RESOLUTION = 1;
    public static final int FF_OPT_TYPE_BINARY = 7;
    public static final int FF_OPT_TYPE_CONST = 128;
    public static final int FF_OPT_TYPE_DOUBLE = 3;
    public static final int FF_OPT_TYPE_FLAGS = 0;
    public static final int FF_OPT_TYPE_FLOAT = 4;
    public static final int FF_OPT_TYPE_INT = 1;
    public static final int FF_OPT_TYPE_INT64 = 2;
    public static final int FF_OPT_TYPE_RATIONAL = 6;
    public static final int FF_OPT_TYPE_STRING = 5;
    public static final int FF_QP2LAMBDA = 118;
    public static final int FF_QUALITY_SCALE = 128;
    public static final int INFINITY = INFINITY();
    public static final double M_E = 2.718281828459045d;
    public static final double M_LN10 = 2.302585092994046d;
    public static final double M_LN2 = 0.6931471805599453d;
    public static final double M_LOG2_10 = 3.321928094887362d;
    public static final double M_PHI = 1.618033988749895d;
    public static final double M_PI = 3.141592653589793d;
    public static final double M_PI_2 = 1.5707963267948966d;
    public static final double M_SQRT1_2 = 0.7071067811865476d;
    public static final double M_SQRT2 = 1.4142135623730951d;
    public static final int NAN = NAN();
    public static final int PIX_FMT_0BGR32 = AV_PIX_FMT_0BGR32;
    public static final int PIX_FMT_0RGB32 = AV_PIX_FMT_0RGB32;
    public static final int PIX_FMT_ALPHA = 128;
    public static final int PIX_FMT_BE = 1;
    public static final int PIX_FMT_BGR32 = AV_PIX_FMT_BGR32;
    public static final int PIX_FMT_BGR32_1 = AV_PIX_FMT_BGR32_1;
    public static final int PIX_FMT_BGR444 = AV_PIX_FMT_BGR444;
    public static final int PIX_FMT_BGR48 = AV_PIX_FMT_BGR48;
    public static final int PIX_FMT_BGR555 = AV_PIX_FMT_BGR555;
    public static final int PIX_FMT_BGR565 = AV_PIX_FMT_BGR565;
    public static final int PIX_FMT_BGRA64 = AV_PIX_FMT_BGRA64;
    public static final int PIX_FMT_BITSTREAM = 4;
    public static final int PIX_FMT_GBR24P = 82;
    public static final int PIX_FMT_GBRP10 = AV_PIX_FMT_GBRP10;
    public static final int PIX_FMT_GBRP12 = AV_PIX_FMT_GBRP12;
    public static final int PIX_FMT_GBRP14 = AV_PIX_FMT_GBRP14;
    public static final int PIX_FMT_GBRP16 = AV_PIX_FMT_GBRP16;
    public static final int PIX_FMT_GBRP9 = AV_PIX_FMT_GBRP9;
    public static final int PIX_FMT_GRAY16 = AV_PIX_FMT_GRAY16;
    public static final int PIX_FMT_HWACCEL = 8;
    public static final int PIX_FMT_PAL = 2;
    public static final int PIX_FMT_PLANAR = 16;
    public static final int PIX_FMT_PSEUDOPAL = 64;
    public static final int PIX_FMT_RGB = 32;
    public static final int PIX_FMT_RGB32 = AV_PIX_FMT_RGB32;
    public static final int PIX_FMT_RGB32_1 = AV_PIX_FMT_RGB32_1;
    public static final int PIX_FMT_RGB444 = AV_PIX_FMT_RGB444;
    public static final int PIX_FMT_RGB48 = AV_PIX_FMT_RGB48;
    public static final int PIX_FMT_RGB555 = AV_PIX_FMT_RGB555;
    public static final int PIX_FMT_RGB565 = AV_PIX_FMT_RGB565;
    public static final int PIX_FMT_RGBA64 = AV_PIX_FMT_RGBA64;
    public static final int PIX_FMT_Y400A = 66;
    public static final int PIX_FMT_YUV420P10 = AV_PIX_FMT_YUV420P10;
    public static final int PIX_FMT_YUV420P12 = AV_PIX_FMT_YUV420P12;
    public static final int PIX_FMT_YUV420P14 = AV_PIX_FMT_YUV420P14;
    public static final int PIX_FMT_YUV420P16 = AV_PIX_FMT_YUV420P16;
    public static final int PIX_FMT_YUV420P9 = AV_PIX_FMT_YUV420P9;
    public static final int PIX_FMT_YUV422P10 = AV_PIX_FMT_YUV422P10;
    public static final int PIX_FMT_YUV422P12 = AV_PIX_FMT_YUV422P12;
    public static final int PIX_FMT_YUV422P14 = AV_PIX_FMT_YUV422P14;
    public static final int PIX_FMT_YUV422P16 = AV_PIX_FMT_YUV422P16;
    public static final int PIX_FMT_YUV422P9 = AV_PIX_FMT_YUV422P9;
    public static final int PIX_FMT_YUV444P10 = AV_PIX_FMT_YUV444P10;
    public static final int PIX_FMT_YUV444P12 = AV_PIX_FMT_YUV444P12;
    public static final int PIX_FMT_YUV444P14 = AV_PIX_FMT_YUV444P14;
    public static final int PIX_FMT_YUV444P16 = AV_PIX_FMT_YUV444P16;
    public static final int PIX_FMT_YUV444P9 = AV_PIX_FMT_YUV444P9;

    @Opaque
    public static class AVBPrint extends Pointer {
        public AVBPrint(Pointer p) {
            super(p);
        }
    }

    @Opaque
    public static class AVBuffer extends Pointer {
        public AVBuffer(Pointer p) {
            super(p);
        }
    }

    @Opaque
    public static class AVBufferPool extends Pointer {
        public AVBufferPool(Pointer p) {
            super(p);
        }
    }

    public static class AVBufferRef extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native AVBuffer buffer();

        public native AVBufferRef buffer(AVBuffer aVBuffer);

        @Cast({"uint8_t*"})
        public native BytePointer data();

        public native AVBufferRef data(BytePointer bytePointer);

        public native int size();

        public native AVBufferRef size(int i);

        static {
            Loader.load();
        }

        public AVBufferRef() {
            allocate();
        }

        public AVBufferRef(int size) {
            allocateArray(size);
        }

        public AVBufferRef(Pointer p) {
            super(p);
        }

        public AVBufferRef position(int position) {
            return (AVBufferRef) super.position(position);
        }
    }

    public static class AVClass extends Pointer {

        public static class Child_class_next_AVClass extends FunctionPointer {
            private native void allocate();

            @Const
            public native AVClass call(@Const AVClass aVClass);

            static {
                Loader.load();
            }

            public Child_class_next_AVClass(Pointer p) {
                super(p);
            }

            protected Child_class_next_AVClass() {
                allocate();
            }
        }

        public static class Get_category_Pointer extends FunctionPointer {
            private native void allocate();

            @Cast({"AVClassCategory"})
            public native int call(Pointer pointer);

            static {
                Loader.load();
            }

            public Get_category_Pointer(Pointer p) {
                super(p);
            }

            protected Get_category_Pointer() {
                allocate();
            }
        }

        public static class Item_name_Pointer extends FunctionPointer {
            private native void allocate();

            @Cast({"const char*"})
            public native BytePointer call(Pointer pointer);

            static {
                Loader.load();
            }

            public Item_name_Pointer(Pointer p) {
                super(p);
            }

            protected Item_name_Pointer() {
                allocate();
            }
        }

        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"AVClassCategory"})
        public native int category();

        public native AVClass category(int i);

        @MemberGetter
        public native Child_class_next_AVClass child_class_next();

        public native Child_next_Pointer_Pointer child_next();

        public native AVClass child_next(Child_next_Pointer_Pointer child_next_Pointer_Pointer);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer class_name();

        public native Get_category_Pointer get_category();

        public native AVClass get_category(Get_category_Pointer get_category_Pointer);

        @MemberGetter
        public native Item_name_Pointer item_name();

        public native int log_level_offset_offset();

        public native AVClass log_level_offset_offset(int i);

        @MemberGetter
        @Const
        public native AVOption option();

        public native int parent_log_context_offset();

        public native AVClass parent_log_context_offset(int i);

        public native Query_ranges_PointerPointer_Pointer_BytePointer_int query_ranges();

        public native AVClass query_ranges(Query_ranges_PointerPointer_Pointer_BytePointer_int query_ranges_PointerPointer_Pointer_BytePointer_int);

        public native int version();

        public native AVClass version(int i);

        static {
            Loader.load();
        }

        public AVClass() {
            allocate();
        }

        public AVClass(int size) {
            allocateArray(size);
        }

        public AVClass(Pointer p) {
            super(p);
        }

        public AVClass position(int position) {
            return (AVClass) super.position(position);
        }
    }

    public static class AVComponentDescriptor extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native AVComponentDescriptor depth_minus1(short s);

        @Cast({"uint16_t"})
        @NoOffset
        public native short depth_minus1();

        public native AVComponentDescriptor offset_plus1(short s);

        @Cast({"uint16_t"})
        @NoOffset
        public native short offset_plus1();

        public native AVComponentDescriptor plane(short s);

        @Cast({"uint16_t"})
        @NoOffset
        public native short plane();

        public native AVComponentDescriptor shift(short s);

        @Cast({"uint16_t"})
        @NoOffset
        public native short shift();

        public native AVComponentDescriptor step_minus1(short s);

        @Cast({"uint16_t"})
        @NoOffset
        public native short step_minus1();

        static {
            Loader.load();
        }

        public AVComponentDescriptor() {
            allocate();
        }

        public AVComponentDescriptor(int size) {
            allocateArray(size);
        }

        public AVComponentDescriptor(Pointer p) {
            super(p);
        }

        public AVComponentDescriptor position(int position) {
            return (AVComponentDescriptor) super.position(position);
        }
    }

    @Opaque
    public static class AVDictionary extends Pointer {
        public AVDictionary(Pointer p) {
            super(p);
        }
    }

    public static class AVDictionaryEntry extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"char*"})
        public native BytePointer key();

        public native AVDictionaryEntry key(BytePointer bytePointer);

        @Cast({"char*"})
        public native BytePointer value();

        public native AVDictionaryEntry value(BytePointer bytePointer);

        static {
            Loader.load();
        }

        public AVDictionaryEntry() {
            allocate();
        }

        public AVDictionaryEntry(int size) {
            allocateArray(size);
        }

        public AVDictionaryEntry(Pointer p) {
            super(p);
        }

        public AVDictionaryEntry position(int position) {
            return (AVDictionaryEntry) super.position(position);
        }
    }

    public static class AVDownmixInfo extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native double center_mix_level();

        public native AVDownmixInfo center_mix_level(double d);

        public native double center_mix_level_ltrt();

        public native AVDownmixInfo center_mix_level_ltrt(double d);

        public native double lfe_mix_level();

        public native AVDownmixInfo lfe_mix_level(double d);

        @Cast({"AVDownmixType"})
        public native int preferred_downmix_type();

        public native AVDownmixInfo preferred_downmix_type(int i);

        public native double surround_mix_level();

        public native AVDownmixInfo surround_mix_level(double d);

        public native double surround_mix_level_ltrt();

        public native AVDownmixInfo surround_mix_level_ltrt(double d);

        static {
            Loader.load();
        }

        public AVDownmixInfo() {
            allocate();
        }

        public AVDownmixInfo(int size) {
            allocateArray(size);
        }

        public AVDownmixInfo(Pointer p) {
            super(p);
        }

        public AVDownmixInfo position(int position) {
            return (AVDownmixInfo) super.position(position);
        }
    }

    public static class AVFrame extends Pointer {
        public static final int AV_FRAME_FLAG_CORRUPT = 1;
        public static final int AV_NUM_DATA_POINTERS = 8;
        public static final int FF_DECODE_ERROR_INVALID_BITSTREAM = 1;
        public static final int FF_DECODE_ERROR_MISSING_REFERENCE = 2;

        private native void allocate();

        private native void allocateArray(int i);

        @Deprecated
        @Cast({"uint8_t*"})
        public native BytePointer base(int i);

        @MemberGetter
        @Deprecated
        @Cast({"uint8_t**"})
        public native PointerPointer base();

        public native AVFrame base(int i, BytePointer bytePointer);

        public native long best_effort_timestamp();

        public native AVFrame best_effort_timestamp(long j);

        @MemberGetter
        @Cast({"AVBufferRef**"})
        public native PointerPointer buf();

        public native AVBufferRef buf(int i);

        public native AVFrame buf(int i, AVBufferRef aVBufferRef);

        @Deprecated
        public native int buffer_hints();

        public native AVFrame buffer_hints(int i);

        @Cast({"uint64_t"})
        public native long channel_layout();

        public native AVFrame channel_layout(long j);

        public native int channels();

        public native AVFrame channels(int i);

        @Cast({"AVChromaLocation"})
        public native int chroma_location();

        public native AVFrame chroma_location(int i);

        public native int coded_picture_number();

        public native AVFrame coded_picture_number(int i);

        @Cast({"AVColorPrimaries"})
        public native int color_primaries();

        public native AVFrame color_primaries(int i);

        @Cast({"AVColorRange"})
        public native int color_range();

        public native AVFrame color_range(int i);

        @Cast({"AVColorTransferCharacteristic"})
        public native int color_trc();

        public native AVFrame color_trc(int i);

        @Cast({"AVColorSpace"})
        public native int colorspace();

        public native AVFrame colorspace(int i);

        @Cast({"uint8_t*"})
        public native BytePointer data(int i);

        @MemberGetter
        @Cast({"uint8_t**"})
        public native PointerPointer data();

        public native AVFrame data(int i, BytePointer bytePointer);

        @Deprecated
        public native ShortPointer dct_coeff();

        public native AVFrame dct_coeff(ShortPointer shortPointer);

        public native int decode_error_flags();

        public native AVFrame decode_error_flags(int i);

        public native int display_picture_number();

        public native AVFrame display_picture_number(int i);

        @Cast({"uint64_t"})
        public native long error(int i);

        @MemberGetter
        @Cast({"uint64_t*"})
        public native LongPointer error();

        public native AVFrame error(int i, long j);

        @MemberGetter
        @Cast({"AVBufferRef**"})
        public native PointerPointer extended_buf();

        public native AVBufferRef extended_buf(int i);

        public native AVFrame extended_buf(int i, AVBufferRef aVBufferRef);

        @Cast({"uint8_t*"})
        public native BytePointer extended_data(int i);

        @MemberGetter
        @Cast({"uint8_t**"})
        public native PointerPointer extended_data();

        public native AVFrame extended_data(int i, BytePointer bytePointer);

        public native int flags();

        public native AVFrame flags(int i);

        public native int format();

        public native AVFrame format(int i);

        public native int height();

        public native AVFrame height(int i);

        @Deprecated
        public native Pointer hwaccel_picture_private();

        public native AVFrame hwaccel_picture_private(Pointer pointer);

        public native int interlaced_frame();

        public native AVFrame interlaced_frame(int i);

        public native int key_frame();

        public native AVFrame key_frame(int i);

        public native int linesize(int i);

        @MemberGetter
        public native IntPointer linesize();

        public native AVFrame linesize(int i, int i2);

        @Deprecated
        @Cast({"uint32_t*"})
        public native IntPointer mb_type();

        public native AVFrame mb_type(IntPointer intPointer);

        @Deprecated
        @Cast({"uint8_t*"})
        public native BytePointer mbskip_table();

        public native AVFrame mbskip_table(BytePointer bytePointer);

        public native AVDictionary metadata();

        public native AVFrame metadata(AVDictionary aVDictionary);

        @Cast({"uint8_t"})
        public native byte motion_subsample_log2();

        public native AVFrame motion_subsample_log2(byte b);

        @MemberGetter
        @Cast({"int16_t(*)[2]"})
        public native ShortPointer motion_val();

        public native AVFrame motion_val(int i, int i2, int i3, short s);

        public native short motion_val(int i, int i2, int i3);

        public native int nb_extended_buf();

        public native AVFrame nb_extended_buf(int i);

        public native int nb_samples();

        public native AVFrame nb_samples(int i);

        public native int nb_side_data();

        public native AVFrame nb_side_data(int i);

        public native Pointer opaque();

        public native AVFrame opaque(Pointer pointer);

        @Deprecated
        @Cast({"AVCodecContext*"})
        public native Pointer owner();

        public native AVFrame owner(Pointer pointer);

        public native int palette_has_changed();

        public native AVFrame palette_has_changed(int i);

        @Deprecated
        @Cast({"AVPanScan*"})
        public native Pointer pan_scan();

        public native AVFrame pan_scan(Pointer pointer);

        @Cast({"AVPictureType"})
        public native int pict_type();

        public native AVFrame pict_type(int i);

        public native long pkt_dts();

        public native AVFrame pkt_dts(long j);

        public native long pkt_duration();

        public native AVFrame pkt_duration(long j);

        public native long pkt_pos();

        public native AVFrame pkt_pos(long j);

        public native long pkt_pts();

        public native AVFrame pkt_pts(long j);

        public native int pkt_size();

        public native AVFrame pkt_size(int i);

        public native long pts();

        public native AVFrame pts(long j);

        public native AVBufferRef qp_table_buf();

        public native AVFrame qp_table_buf(AVBufferRef aVBufferRef);

        @Deprecated
        public native BytePointer qscale_table();

        public native AVFrame qscale_table(BytePointer bytePointer);

        @Deprecated
        public native int qscale_type();

        public native AVFrame qscale_type(int i);

        @Deprecated
        public native int qstride();

        public native AVFrame qstride(int i);

        public native int quality();

        public native AVFrame quality(int i);

        @Deprecated
        public native BytePointer ref_index(int i);

        @MemberGetter
        @Deprecated
        @Cast({"int8_t**"})
        public native PointerPointer ref_index();

        public native AVFrame ref_index(int i, BytePointer bytePointer);

        @Deprecated
        public native int reference();

        public native AVFrame reference(int i);

        public native long reordered_opaque();

        public native AVFrame reordered_opaque(long j);

        public native int repeat_pict();

        public native AVFrame repeat_pict(int i);

        public native AVFrame sample_aspect_ratio(AVRational aVRational);

        @ByRef
        public native AVRational sample_aspect_ratio();

        public native int sample_rate();

        public native AVFrame sample_rate(int i);

        @MemberGetter
        @Cast({"AVFrameSideData**"})
        public native PointerPointer side_data();

        public native AVFrame side_data(int i, AVFrameSideData aVFrameSideData);

        public native AVFrameSideData side_data(int i);

        @Deprecated
        public native Pointer thread_opaque();

        public native AVFrame thread_opaque(Pointer pointer);

        public native int top_field_first();

        public native AVFrame top_field_first(int i);

        @Deprecated
        public native int type();

        public native AVFrame type(int i);

        public native int width();

        public native AVFrame width(int i);

        static {
            Loader.load();
        }

        public AVFrame() {
            allocate();
        }

        public AVFrame(int size) {
            allocateArray(size);
        }

        public AVFrame(Pointer p) {
            super(p);
        }

        public AVFrame position(int position) {
            return (AVFrame) super.position(position);
        }
    }

    public static class AVFrameSideData extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native AVBufferRef buf();

        public native AVFrameSideData buf(AVBufferRef aVBufferRef);

        @Cast({"uint8_t*"})
        public native BytePointer data();

        public native AVFrameSideData data(BytePointer bytePointer);

        public native AVDictionary metadata();

        public native AVFrameSideData metadata(AVDictionary aVDictionary);

        public native int size();

        public native AVFrameSideData size(int i);

        @Cast({"AVFrameSideDataType"})
        public native int type();

        public native AVFrameSideData type(int i);

        static {
            Loader.load();
        }

        public AVFrameSideData() {
            allocate();
        }

        public AVFrameSideData(int size) {
            allocateArray(size);
        }

        public AVFrameSideData(Pointer p) {
            super(p);
        }

        public AVFrameSideData position(int position) {
            return (AVFrameSideData) super.position(position);
        }
    }

    public static class AVOption extends Pointer {
        public static final int AV_OPT_FLAG_AUDIO_PARAM = 8;
        public static final int AV_OPT_FLAG_DECODING_PARAM = 2;
        public static final int AV_OPT_FLAG_ENCODING_PARAM = 1;
        public static final int AV_OPT_FLAG_EXPORT = 64;
        public static final int AV_OPT_FLAG_FILTERING_PARAM = 65536;
        public static final int AV_OPT_FLAG_METADATA = 4;
        public static final int AV_OPT_FLAG_READONLY = 128;
        public static final int AV_OPT_FLAG_SUBTITLE_PARAM = 32;
        public static final int AV_OPT_FLAG_VIDEO_PARAM = 16;

        private native void allocate();

        private native void allocateArray(int i);

        @Name({"default_val.dbl"})
        public native double default_val_dbl();

        public native AVOption default_val_dbl(double d);

        @Name({"default_val.i64"})
        public native long default_val_i64();

        public native AVOption default_val_i64(long j);

        public native AVOption default_val_q(AVRational aVRational);

        @ByRef
        @Name({"default_val.q"})
        public native AVRational default_val_q();

        @MemberGetter
        @Cast({"const char*"})
        @Name({"default_val.str"})
        public native BytePointer default_val_str();

        public native int flags();

        public native AVOption flags(int i);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer help();

        public native double max();

        public native AVOption max(double d);

        public native double min();

        public native AVOption min(double d);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        public native int offset();

        public native AVOption offset(int i);

        @Cast({"AVOptionType"})
        public native int type();

        public native AVOption type(int i);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer unit();

        static {
            Loader.load();
        }

        public AVOption() {
            allocate();
        }

        public AVOption(int size) {
            allocateArray(size);
        }

        public AVOption(Pointer p) {
            super(p);
        }

        public AVOption position(int position) {
            return (AVOption) super.position(position);
        }
    }

    public static class AVOptionRange extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native double component_max();

        public native AVOptionRange component_max(double d);

        public native double component_min();

        public native AVOptionRange component_min(double d);

        public native int is_range();

        public native AVOptionRange is_range(int i);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer str();

        public native double value_max();

        public native AVOptionRange value_max(double d);

        public native double value_min();

        public native AVOptionRange value_min(double d);

        static {
            Loader.load();
        }

        public AVOptionRange() {
            allocate();
        }

        public AVOptionRange(int size) {
            allocateArray(size);
        }

        public AVOptionRange(Pointer p) {
            super(p);
        }

        public AVOptionRange position(int position) {
            return (AVOptionRange) super.position(position);
        }
    }

    public static class AVOptionRanges extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native int nb_components();

        public native AVOptionRanges nb_components(int i);

        public native int nb_ranges();

        public native AVOptionRanges nb_ranges(int i);

        @MemberGetter
        @Cast({"AVOptionRange**"})
        public native PointerPointer range();

        public native AVOptionRange range(int i);

        public native AVOptionRanges range(int i, AVOptionRange aVOptionRange);

        static {
            Loader.load();
        }

        public AVOptionRanges() {
            allocate();
        }

        public AVOptionRanges(int size) {
            allocateArray(size);
        }

        public AVOptionRanges(Pointer p) {
            super(p);
        }

        public AVOptionRanges position(int position) {
            return (AVOptionRanges) super.position(position);
        }
    }

    public static class AVPixFmtDescriptor extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer alias();

        @MemberGetter
        public native AVComponentDescriptor comp();

        @ByRef
        public native AVComponentDescriptor comp(int i);

        public native AVPixFmtDescriptor comp(int i, AVComponentDescriptor aVComponentDescriptor);

        @Cast({"uint8_t"})
        public native byte flags();

        public native AVPixFmtDescriptor flags(byte b);

        @Cast({"uint8_t"})
        public native byte log2_chroma_h();

        public native AVPixFmtDescriptor log2_chroma_h(byte b);

        @Cast({"uint8_t"})
        public native byte log2_chroma_w();

        public native AVPixFmtDescriptor log2_chroma_w(byte b);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        @Cast({"uint8_t"})
        public native byte nb_components();

        public native AVPixFmtDescriptor nb_components(byte b);

        static {
            Loader.load();
        }

        public AVPixFmtDescriptor() {
            allocate();
        }

        public AVPixFmtDescriptor(int size) {
            allocateArray(size);
        }

        public AVPixFmtDescriptor(Pointer p) {
            super(p);
        }

        public AVPixFmtDescriptor position(int position) {
            return (AVPixFmtDescriptor) super.position(position);
        }
    }

    public static class AVStereo3D extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native int flags();

        public native AVStereo3D flags(int i);

        @Cast({"AVStereo3DType"})
        public native int type();

        public native AVStereo3D type(int i);

        static {
            Loader.load();
        }

        public AVStereo3D() {
            allocate();
        }

        public AVStereo3D(int size) {
            allocateArray(size);
        }

        public AVStereo3D(Pointer p) {
            super(p);
        }

        public AVStereo3D position(int position) {
            return (AVStereo3D) super.position(position);
        }
    }

    @MemberGetter
    public static native int AVERROR_BSF_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_BUFFER_TOO_SMALL();

    @MemberGetter
    public static native int AVERROR_BUG();

    @MemberGetter
    public static native int AVERROR_BUG2();

    @MemberGetter
    public static native int AVERROR_DECODER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_DEMUXER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_ENCODER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_EOF();

    @MemberGetter
    public static native int AVERROR_EXIT();

    @MemberGetter
    public static native int AVERROR_EXTERNAL();

    @MemberGetter
    public static native int AVERROR_FILTER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_HTTP_BAD_REQUEST();

    @MemberGetter
    public static native int AVERROR_HTTP_FORBIDDEN();

    @MemberGetter
    public static native int AVERROR_HTTP_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_HTTP_OTHER_4XX();

    @MemberGetter
    public static native int AVERROR_HTTP_SERVER_ERROR();

    @MemberGetter
    public static native int AVERROR_HTTP_UNAUTHORIZED();

    @MemberGetter
    public static native int AVERROR_INVALIDDATA();

    @MemberGetter
    public static native int AVERROR_MUXER_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_OPTION_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_PATCHWELCOME();

    @MemberGetter
    public static native int AVERROR_PROTOCOL_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_STREAM_NOT_FOUND();

    @MemberGetter
    public static native int AVERROR_UNKNOWN();

    @MemberGetter
    public static native long AV_NOPTS_VALUE();

    @MemberGetter
    public static native int AV_OPT_TYPE_CHANNEL_LAYOUT();

    @MemberGetter
    public static native int AV_OPT_TYPE_COLOR();

    @MemberGetter
    public static native int AV_OPT_TYPE_DURATION();

    @MemberGetter
    public static native int AV_OPT_TYPE_IMAGE_SIZE();

    @MemberGetter
    public static native int AV_OPT_TYPE_PIXEL_FMT();

    @MemberGetter
    public static native int AV_OPT_TYPE_SAMPLE_FMT();

    @MemberGetter
    public static native int AV_OPT_TYPE_VIDEO_RATE();

    @MemberGetter
    public static native int AV_PIX_FMT_0BGR32();

    @MemberGetter
    public static native int AV_PIX_FMT_0RGB32();

    @MemberGetter
    public static native int AV_PIX_FMT_BAYER_BGGR16();

    @MemberGetter
    public static native int AV_PIX_FMT_BAYER_GBRG16();

    @MemberGetter
    public static native int AV_PIX_FMT_BAYER_GRBG16();

    @MemberGetter
    public static native int AV_PIX_FMT_BAYER_RGGB16();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR32();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR32_1();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR444();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR48();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR555();

    @MemberGetter
    public static native int AV_PIX_FMT_BGR565();

    @MemberGetter
    public static native int AV_PIX_FMT_BGRA64();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRAP16();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP10();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP12();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP14();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP16();

    @MemberGetter
    public static native int AV_PIX_FMT_GBRP9();

    @MemberGetter
    public static native int AV_PIX_FMT_GRAY16();

    @MemberGetter
    public static native int AV_PIX_FMT_NV20();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB32();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB32_1();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB444();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB48();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB555();

    @MemberGetter
    public static native int AV_PIX_FMT_RGB565();

    @MemberGetter
    public static native int AV_PIX_FMT_RGBA64();

    @MemberGetter
    public static native int AV_PIX_FMT_XYZ12();

    @MemberGetter
    public static native int AV_PIX_FMT_YA16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P14();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV420P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P14();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV422P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV440P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV440P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P12();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P14();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUV444P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA420P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA420P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA420P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA422P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA422P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA422P9();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA444P10();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA444P16();

    @MemberGetter
    public static native int AV_PIX_FMT_YUVA444P9();

    @MemberGetter
    public static native int INFINITY();

    @MemberGetter
    public static native int NAN();

    @ByVal
    public static native AVRational av_add_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

    public static native long av_add_stable(@ByVal AVRational aVRational, long j, @ByVal AVRational aVRational2, long j2);

    public static native void av_bprint_channel_layout(AVBPrint aVBPrint, int i, @Cast({"uint64_t"}) long j);

    public static native AVBufferRef av_buffer_alloc(int i);

    public static native AVBufferRef av_buffer_allocz(int i);

    public static native AVBufferRef av_buffer_create(@Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, Free_Pointer_ByteBuffer free_Pointer_ByteBuffer, Pointer pointer, int i2);

    public static native AVBufferRef av_buffer_create(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, Free_Pointer_BytePointer free_Pointer_BytePointer, Pointer pointer, int i2);

    public static native AVBufferRef av_buffer_create(@Cast({"uint8_t*"}) byte[] bArr, int i, Free_Pointer_byte__ free_Pointer_byte__, Pointer pointer, int i2);

    public static native void av_buffer_default_free(Pointer pointer, @Cast({"uint8_t*"}) ByteBuffer byteBuffer);

    public static native void av_buffer_default_free(Pointer pointer, @Cast({"uint8_t*"}) BytePointer bytePointer);

    public static native void av_buffer_default_free(Pointer pointer, @Cast({"uint8_t*"}) byte[] bArr);

    public static native Pointer av_buffer_get_opaque(@Const AVBufferRef aVBufferRef);

    public static native int av_buffer_get_ref_count(@Const AVBufferRef aVBufferRef);

    public static native int av_buffer_is_writable(@Const AVBufferRef aVBufferRef);

    public static native int av_buffer_make_writable(@Cast({"AVBufferRef**"}) PointerPointer pointerPointer);

    public static native int av_buffer_make_writable(@ByPtrPtr AVBufferRef aVBufferRef);

    public static native AVBufferRef av_buffer_pool_get(AVBufferPool aVBufferPool);

    public static native AVBufferPool av_buffer_pool_init(int i, Alloc_int alloc_int);

    public static native void av_buffer_pool_uninit(@Cast({"AVBufferPool**"}) PointerPointer pointerPointer);

    public static native void av_buffer_pool_uninit(@ByPtrPtr AVBufferPool aVBufferPool);

    public static native int av_buffer_realloc(@Cast({"AVBufferRef**"}) PointerPointer pointerPointer, int i);

    public static native int av_buffer_realloc(@ByPtrPtr AVBufferRef aVBufferRef, int i);

    public static native AVBufferRef av_buffer_ref(AVBufferRef aVBufferRef);

    public static native void av_buffer_unref(@Cast({"AVBufferRef**"}) PointerPointer pointerPointer);

    public static native void av_buffer_unref(@ByPtrPtr AVBufferRef aVBufferRef);

    public static native Pointer av_calloc(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

    @Cast({"uint64_t"})
    public static native long av_channel_layout_extract_channel(@Cast({"uint64_t"}) long j, int i);

    @Cast({"const char*"})
    public static native BytePointer av_chroma_location_name(@Cast({"AVChromaLocation"}) int i);

    public static native int av_cmp_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

    @Cast({"const char*"})
    public static native BytePointer av_color_primaries_name(@Cast({"AVColorPrimaries"}) int i);

    @Cast({"const char*"})
    public static native BytePointer av_color_range_name(@Cast({"AVColorRange"}) int i);

    @Cast({"const char*"})
    public static native BytePointer av_color_space_name(@Cast({"AVColorSpace"}) int i);

    @Cast({"const char*"})
    public static native BytePointer av_color_transfer_name(@Cast({"AVColorTransferCharacteristic"}) int i);

    public static native long av_compare_mod(@Cast({"uint64_t"}) long j, @Cast({"uint64_t"}) long j2, @Cast({"uint64_t"}) long j3);

    public static native int av_compare_ts(long j, @ByVal AVRational aVRational, long j2, @ByVal AVRational aVRational2);

    public static native int av_cpu_count();

    @ByVal
    public static native AVRational av_d2q(double d, int i);

    @Cast({"AVClassCategory"})
    public static native int av_default_get_category(Pointer pointer);

    @Cast({"const char*"})
    public static native BytePointer av_default_item_name(Pointer pointer);

    public static native void av_dict_copy(@Cast({"AVDictionary**"}) PointerPointer pointerPointer, @Const AVDictionary aVDictionary, int i);

    public static native void av_dict_copy(@ByPtrPtr AVDictionary aVDictionary, @Const AVDictionary aVDictionary2, int i);

    public static native int av_dict_count(@Const AVDictionary aVDictionary);

    public static native void av_dict_free(@Cast({"AVDictionary**"}) PointerPointer pointerPointer);

    public static native void av_dict_free(@ByPtrPtr AVDictionary aVDictionary);

    public static native AVDictionaryEntry av_dict_get(@Const AVDictionary aVDictionary, String str, @Const AVDictionaryEntry aVDictionaryEntry, int i);

    public static native AVDictionaryEntry av_dict_get(@Const AVDictionary aVDictionary, @Cast({"const char*"}) BytePointer bytePointer, @Const AVDictionaryEntry aVDictionaryEntry, int i);

    public static native int av_dict_get_string(@Const AVDictionary aVDictionary, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer, byte b, byte b2);

    public static native int av_dict_get_string(@Const AVDictionary aVDictionary, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer, byte b, byte b2);

    public static native int av_dict_get_string(@Const AVDictionary aVDictionary, @Cast({"char**"}) PointerPointer pointerPointer, byte b, byte b2);

    public static native int av_dict_get_string(@Const AVDictionary aVDictionary, @ByPtrPtr @Cast({"char**"}) byte[] bArr, byte b, byte b2);

    public static native int av_dict_parse_string(@Cast({"AVDictionary**"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, int i);

    public static native int av_dict_parse_string(@ByPtrPtr AVDictionary aVDictionary, String str, String str2, String str3, int i);

    public static native int av_dict_parse_string(@ByPtrPtr AVDictionary aVDictionary, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, int i);

    public static native int av_dict_set(@Cast({"AVDictionary**"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i);

    public static native int av_dict_set(@ByPtrPtr AVDictionary aVDictionary, String str, String str2, int i);

    public static native int av_dict_set(@ByPtrPtr AVDictionary aVDictionary, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i);

    public static native int av_dict_set_int(@Cast({"AVDictionary**"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer, long j, int i);

    public static native int av_dict_set_int(@ByPtrPtr AVDictionary aVDictionary, String str, long j, int i);

    public static native int av_dict_set_int(@ByPtrPtr AVDictionary aVDictionary, @Cast({"const char*"}) BytePointer bytePointer, long j, int i);

    @ByVal
    public static native AVRational av_div_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

    public static native AVDownmixInfo av_downmix_info_update_side_data(AVFrame aVFrame);

    public static native Pointer av_dynarray2_add(@ByPtrPtr @Cast({"void**"}) Pointer pointer, IntBuffer intBuffer, @Cast({"size_t"}) long j, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer);

    public static native Pointer av_dynarray2_add(@ByPtrPtr @Cast({"void**"}) Pointer pointer, IntPointer intPointer, @Cast({"size_t"}) long j, @Cast({"const uint8_t*"}) BytePointer bytePointer);

    public static native Pointer av_dynarray2_add(@ByPtrPtr @Cast({"void**"}) Pointer pointer, int[] iArr, @Cast({"size_t"}) long j, @Cast({"const uint8_t*"}) byte[] bArr);

    public static native Pointer av_dynarray2_add(@Cast({"void**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"size_t"}) long j, @Cast({"const uint8_t*"}) BytePointer bytePointer);

    public static native void av_dynarray_add(Pointer pointer, IntBuffer intBuffer, Pointer pointer2);

    public static native void av_dynarray_add(Pointer pointer, IntPointer intPointer, Pointer pointer2);

    public static native void av_dynarray_add(Pointer pointer, int[] iArr, Pointer pointer2);

    public static native int av_dynarray_add_nofree(Pointer pointer, IntBuffer intBuffer, Pointer pointer2);

    public static native int av_dynarray_add_nofree(Pointer pointer, IntPointer intPointer, Pointer pointer2);

    public static native int av_dynarray_add_nofree(Pointer pointer, int[] iArr, Pointer pointer2);

    public static native void av_fast_malloc(Pointer pointer, @Cast({"unsigned int*"}) IntBuffer intBuffer, @Cast({"size_t"}) long j);

    public static native void av_fast_malloc(Pointer pointer, @Cast({"unsigned int*"}) IntPointer intPointer, @Cast({"size_t"}) long j);

    public static native void av_fast_malloc(Pointer pointer, @Cast({"unsigned int*"}) int[] iArr, @Cast({"size_t"}) long j);

    public static native Pointer av_fast_realloc(Pointer pointer, @Cast({"unsigned int*"}) IntBuffer intBuffer, @Cast({"size_t"}) long j);

    public static native Pointer av_fast_realloc(Pointer pointer, @Cast({"unsigned int*"}) IntPointer intPointer, @Cast({"size_t"}) long j);

    public static native Pointer av_fast_realloc(Pointer pointer, @Cast({"unsigned int*"}) int[] iArr, @Cast({"size_t"}) long j);

    @Cast({"AVPixelFormat"})
    public static native int av_find_best_pix_fmt_of_2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, IntBuffer intBuffer);

    @Cast({"AVPixelFormat"})
    public static native int av_find_best_pix_fmt_of_2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, IntPointer intPointer);

    @Cast({"AVPixelFormat"})
    public static native int av_find_best_pix_fmt_of_2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int[] iArr);

    public static native int av_find_nearest_q_idx(@ByVal AVRational aVRational, @Const AVRational aVRational2);

    @Cast({"FILE*"})
    public static native Pointer av_fopen_utf8(String str, String str2);

    @Cast({"FILE*"})
    public static native Pointer av_fopen_utf8(@Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2);

    public static native void av_force_cpu_flags(int i);

    public static native AVFrame av_frame_alloc();

    public static native AVFrame av_frame_clone(@Const AVFrame aVFrame);

    public static native int av_frame_copy(AVFrame aVFrame, @Const AVFrame aVFrame2);

    public static native int av_frame_copy_props(AVFrame aVFrame, @Const AVFrame aVFrame2);

    public static native void av_frame_free(@Cast({"AVFrame**"}) PointerPointer pointerPointer);

    public static native void av_frame_free(@ByPtrPtr AVFrame aVFrame);

    public static native long av_frame_get_best_effort_timestamp(@Const AVFrame aVFrame);

    public static native int av_frame_get_buffer(AVFrame aVFrame, int i);

    public static native long av_frame_get_channel_layout(@Const AVFrame aVFrame);

    public static native int av_frame_get_channels(@Const AVFrame aVFrame);

    @Cast({"AVColorRange"})
    public static native int av_frame_get_color_range(@Const AVFrame aVFrame);

    @Cast({"AVColorSpace"})
    public static native int av_frame_get_colorspace(@Const AVFrame aVFrame);

    public static native int av_frame_get_decode_error_flags(@Const AVFrame aVFrame);

    public static native AVDictionary av_frame_get_metadata(@Const AVFrame aVFrame);

    public static native long av_frame_get_pkt_duration(@Const AVFrame aVFrame);

    public static native long av_frame_get_pkt_pos(@Const AVFrame aVFrame);

    public static native int av_frame_get_pkt_size(@Const AVFrame aVFrame);

    public static native AVBufferRef av_frame_get_plane_buffer(AVFrame aVFrame, int i);

    public static native ByteBuffer av_frame_get_qp_table(AVFrame aVFrame, IntBuffer intBuffer, IntBuffer intBuffer2);

    public static native BytePointer av_frame_get_qp_table(AVFrame aVFrame, IntPointer intPointer, IntPointer intPointer2);

    public static native byte[] av_frame_get_qp_table(AVFrame aVFrame, int[] iArr, int[] iArr2);

    public static native int av_frame_get_sample_rate(@Const AVFrame aVFrame);

    public static native AVFrameSideData av_frame_get_side_data(@Const AVFrame aVFrame, @Cast({"AVFrameSideDataType"}) int i);

    public static native int av_frame_is_writable(AVFrame aVFrame);

    public static native int av_frame_make_writable(AVFrame aVFrame);

    public static native void av_frame_move_ref(AVFrame aVFrame, AVFrame aVFrame2);

    public static native AVFrameSideData av_frame_new_side_data(AVFrame aVFrame, @Cast({"AVFrameSideDataType"}) int i, int i2);

    public static native int av_frame_ref(AVFrame aVFrame, @Const AVFrame aVFrame2);

    public static native void av_frame_remove_side_data(AVFrame aVFrame, @Cast({"AVFrameSideDataType"}) int i);

    public static native void av_frame_set_best_effort_timestamp(AVFrame aVFrame, long j);

    public static native void av_frame_set_channel_layout(AVFrame aVFrame, long j);

    public static native void av_frame_set_channels(AVFrame aVFrame, int i);

    public static native void av_frame_set_color_range(AVFrame aVFrame, @Cast({"AVColorRange"}) int i);

    public static native void av_frame_set_colorspace(AVFrame aVFrame, @Cast({"AVColorSpace"}) int i);

    public static native void av_frame_set_decode_error_flags(AVFrame aVFrame, int i);

    public static native void av_frame_set_metadata(AVFrame aVFrame, AVDictionary aVDictionary);

    public static native void av_frame_set_pkt_duration(AVFrame aVFrame, long j);

    public static native void av_frame_set_pkt_pos(AVFrame aVFrame, long j);

    public static native void av_frame_set_pkt_size(AVFrame aVFrame, int i);

    public static native int av_frame_set_qp_table(AVFrame aVFrame, AVBufferRef aVBufferRef, int i, int i2);

    public static native void av_frame_set_sample_rate(AVFrame aVFrame, int i);

    @Cast({"const char*"})
    public static native BytePointer av_frame_side_data_name(@Cast({"AVFrameSideDataType"}) int i);

    public static native void av_frame_unref(AVFrame aVFrame);

    public static native void av_free(Pointer pointer);

    public static native void av_freep(Pointer pointer);

    @Const
    public static native long av_gcd(long j, long j2);

    @Cast({"AVSampleFormat"})
    public static native int av_get_alt_sample_fmt(@Cast({"AVSampleFormat"}) int i, int i2);

    public static native int av_get_bits_per_pixel(@Const AVPixFmtDescriptor aVPixFmtDescriptor);

    public static native int av_get_bytes_per_sample(@Cast({"AVSampleFormat"}) int i);

    @Cast({"const char*"})
    public static native BytePointer av_get_channel_description(@Cast({"uint64_t"}) long j);

    @Cast({"uint64_t"})
    public static native long av_get_channel_layout(String str);

    @Cast({"uint64_t"})
    public static native long av_get_channel_layout(@Cast({"const char*"}) BytePointer bytePointer);

    public static native int av_get_channel_layout_channel_index(@Cast({"uint64_t"}) long j, @Cast({"uint64_t"}) long j2);

    public static native int av_get_channel_layout_nb_channels(@Cast({"uint64_t"}) long j);

    public static native void av_get_channel_layout_string(@Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2, @Cast({"uint64_t"}) long j);

    public static native void av_get_channel_layout_string(@Cast({"char*"}) BytePointer bytePointer, int i, int i2, @Cast({"uint64_t"}) long j);

    public static native void av_get_channel_layout_string(@Cast({"char*"}) byte[] bArr, int i, int i2, @Cast({"uint64_t"}) long j);

    @Cast({"const char*"})
    public static native BytePointer av_get_channel_name(@Cast({"uint64_t"}) long j);

    @Cast({"const char*"})
    public static native BytePointer av_get_colorspace_name(@Cast({"AVColorSpace"}) int i);

    public static native int av_get_cpu_flags();

    public static native long av_get_default_channel_layout(int i);

    public static native double av_get_double(Pointer pointer, String str, @ByPtrPtr @Const AVOption aVOption);

    public static native double av_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const AVOption**"}) PointerPointer pointerPointer);

    public static native double av_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Const AVOption aVOption);

    public static native long av_get_int(Pointer pointer, String str, @ByPtrPtr @Const AVOption aVOption);

    public static native long av_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const AVOption**"}) PointerPointer pointerPointer);

    public static native long av_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Const AVOption aVOption);

    @Cast({"const char*"})
    public static native BytePointer av_get_media_type_string(@Cast({"AVMediaType"}) int i);

    @Cast({"AVSampleFormat"})
    public static native int av_get_packed_sample_fmt(@Cast({"AVSampleFormat"}) int i);

    public static native int av_get_padded_bits_per_pixel(@Const AVPixFmtDescriptor aVPixFmtDescriptor);

    @Cast({"char"})
    public static native byte av_get_picture_type_char(@Cast({"AVPictureType"}) int i);

    @Cast({"AVPixelFormat"})
    public static native int av_get_pix_fmt(String str);

    @Cast({"AVPixelFormat"})
    public static native int av_get_pix_fmt(@Cast({"const char*"}) BytePointer bytePointer);

    public static native int av_get_pix_fmt_loss(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, int i3);

    @Cast({"const char*"})
    public static native BytePointer av_get_pix_fmt_name(@Cast({"AVPixelFormat"}) int i);

    @Cast({"char*"})
    public static native ByteBuffer av_get_pix_fmt_string(@Cast({"char*"}) ByteBuffer byteBuffer, int i, @Cast({"AVPixelFormat"}) int i2);

    @Cast({"char*"})
    public static native BytePointer av_get_pix_fmt_string(@Cast({"char*"}) BytePointer bytePointer, int i, @Cast({"AVPixelFormat"}) int i2);

    @Cast({"char*"})
    public static native byte[] av_get_pix_fmt_string(@Cast({"char*"}) byte[] bArr, int i, @Cast({"AVPixelFormat"}) int i2);

    @Cast({"AVSampleFormat"})
    public static native int av_get_planar_sample_fmt(@Cast({"AVSampleFormat"}) int i);

    @ByVal
    public static native AVRational av_get_q(Pointer pointer, String str, @ByPtrPtr @Const AVOption aVOption);

    @ByVal
    public static native AVRational av_get_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const AVOption**"}) PointerPointer pointerPointer);

    @ByVal
    public static native AVRational av_get_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Const AVOption aVOption);

    @Cast({"AVSampleFormat"})
    public static native int av_get_sample_fmt(String str);

    @Cast({"AVSampleFormat"})
    public static native int av_get_sample_fmt(@Cast({"const char*"}) BytePointer bytePointer);

    @Cast({"const char*"})
    public static native BytePointer av_get_sample_fmt_name(@Cast({"AVSampleFormat"}) int i);

    @Cast({"char*"})
    public static native ByteBuffer av_get_sample_fmt_string(@Cast({"char*"}) ByteBuffer byteBuffer, int i, @Cast({"AVSampleFormat"}) int i2);

    @Cast({"char*"})
    public static native BytePointer av_get_sample_fmt_string(@Cast({"char*"}) BytePointer bytePointer, int i, @Cast({"AVSampleFormat"}) int i2);

    @Cast({"char*"})
    public static native byte[] av_get_sample_fmt_string(@Cast({"char*"}) byte[] bArr, int i, @Cast({"AVSampleFormat"}) int i2);

    public static native int av_get_standard_channel_layout(@Cast({"unsigned"}) int i, @Cast({"uint64_t*"}) LongBuffer longBuffer, @ByPtrPtr @Cast({"const char**"}) ByteBuffer byteBuffer);

    public static native int av_get_standard_channel_layout(@Cast({"unsigned"}) int i, @Cast({"uint64_t*"}) LongPointer longPointer, @ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer);

    public static native int av_get_standard_channel_layout(@Cast({"unsigned"}) int i, @Cast({"uint64_t*"}) LongPointer longPointer, @Cast({"const char**"}) PointerPointer pointerPointer);

    public static native int av_get_standard_channel_layout(@Cast({"unsigned"}) int i, @Cast({"uint64_t*"}) long[] jArr, @ByPtrPtr @Cast({"const char**"}) byte[] bArr);

    @Deprecated
    public static native String av_get_string(Pointer pointer, String str, @ByPtrPtr @Const AVOption aVOption, @Cast({"char*"}) ByteBuffer byteBuffer, int i);

    @Deprecated
    public static native String av_get_string(Pointer pointer, String str, @ByPtrPtr @Const AVOption aVOption, @Cast({"char*"}) BytePointer bytePointer, int i);

    @Deprecated
    public static native String av_get_string(Pointer pointer, String str, @ByPtrPtr @Const AVOption aVOption, @Cast({"char*"}) byte[] bArr, int i);

    @Deprecated
    @Cast({"const char*"})
    public static native BytePointer av_get_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const AVOption**"}) PointerPointer pointerPointer, @Cast({"char*"}) BytePointer bytePointer2, int i);

    @Deprecated
    @Cast({"const char*"})
    public static native BytePointer av_get_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Const AVOption aVOption, @Cast({"char*"}) ByteBuffer byteBuffer, int i);

    @Deprecated
    @Cast({"const char*"})
    public static native BytePointer av_get_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Const AVOption aVOption, @Cast({"char*"}) BytePointer bytePointer2, int i);

    @Deprecated
    @Cast({"const char*"})
    public static native BytePointer av_get_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Const AVOption aVOption, @Cast({"char*"}) byte[] bArr, int i);

    @ByVal
    public static native AVRational av_get_time_base_q();

    public static native int av_image_alloc(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4);

    public static native int av_image_alloc(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4);

    public static native int av_image_alloc(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4);

    public static native int av_image_alloc(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4);

    public static native int av_image_check_sar(@Cast({"unsigned int"}) int i, @Cast({"unsigned int"}) int i2, @ByVal AVRational aVRational);

    public static native int av_image_check_size(@Cast({"unsigned int"}) int i, @Cast({"unsigned int"}) int i2, int i3, Pointer pointer);

    public static native void av_image_copy(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @ByPtrPtr @Cast({"const uint8_t**"}) ByteBuffer byteBuffer2, @Const IntBuffer intBuffer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native void av_image_copy(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @ByPtrPtr @Cast({"const uint8_t**"}) BytePointer bytePointer2, @Const IntPointer intPointer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native void av_image_copy(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t**"}) PointerPointer pointerPointer2, @Const IntPointer intPointer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native void av_image_copy(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @ByPtrPtr @Cast({"const uint8_t**"}) byte[] bArr2, @Const int[] iArr2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native void av_image_copy_plane(@Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, int i2, int i3, int i4);

    public static native void av_image_copy_plane(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i2, int i3, int i4);

    public static native void av_image_copy_plane(@Cast({"uint8_t*"}) byte[] bArr, int i, @Cast({"const uint8_t*"}) byte[] bArr2, int i2, int i3, int i4);

    public static native int av_image_copy_to_buffer(@Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, @ByPtrPtr @Cast({"const uint8_t*const*"}) ByteBuffer byteBuffer2, @Const IntBuffer intBuffer, @Cast({"AVPixelFormat"}) int i2, int i3, int i4, int i5);

    public static native int av_image_copy_to_buffer(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"const uint8_t*const*"}) BytePointer bytePointer2, @Const IntPointer intPointer, @Cast({"AVPixelFormat"}) int i2, int i3, int i4, int i5);

    public static native int av_image_copy_to_buffer(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, @Cast({"const uint8_t*const*"}) PointerPointer pointerPointer, @Const IntPointer intPointer, @Cast({"AVPixelFormat"}) int i2, int i3, int i4, int i5);

    public static native int av_image_copy_to_buffer(@Cast({"uint8_t*"}) byte[] bArr, int i, @ByPtrPtr @Cast({"const uint8_t*const*"}) byte[] bArr2, @Const int[] iArr, @Cast({"AVPixelFormat"}) int i2, int i3, int i4, int i5);

    public static native int av_image_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

    public static native int av_image_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

    public static native int av_image_fill_arrays(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer, @Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

    public static native int av_image_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @Cast({"const uint8_t*"}) byte[] bArr2, @Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

    public static native int av_image_fill_linesizes(IntBuffer intBuffer, @Cast({"AVPixelFormat"}) int i, int i2);

    public static native int av_image_fill_linesizes(IntPointer intPointer, @Cast({"AVPixelFormat"}) int i, int i2);

    public static native int av_image_fill_linesizes(int[] iArr, @Cast({"AVPixelFormat"}) int i, int i2);

    public static native void av_image_fill_max_pixsteps(IntBuffer intBuffer, IntBuffer intBuffer2, @Const AVPixFmtDescriptor aVPixFmtDescriptor);

    public static native void av_image_fill_max_pixsteps(IntPointer intPointer, IntPointer intPointer2, @Const AVPixFmtDescriptor aVPixFmtDescriptor);

    public static native void av_image_fill_max_pixsteps(int[] iArr, int[] iArr2, @Const AVPixFmtDescriptor aVPixFmtDescriptor);

    public static native int av_image_fill_pointers(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, @Cast({"AVPixelFormat"}) int i, int i2, @Cast({"uint8_t*"}) ByteBuffer byteBuffer2, @Const IntBuffer intBuffer);

    public static native int av_image_fill_pointers(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, @Cast({"AVPixelFormat"}) int i, int i2, @Cast({"uint8_t*"}) BytePointer bytePointer2, @Const IntPointer intPointer);

    public static native int av_image_fill_pointers(@Cast({"uint8_t**"}) PointerPointer pointerPointer, @Cast({"AVPixelFormat"}) int i, int i2, @Cast({"uint8_t*"}) BytePointer bytePointer, @Const IntPointer intPointer);

    public static native int av_image_fill_pointers(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, @Cast({"AVPixelFormat"}) int i, int i2, @Cast({"uint8_t*"}) byte[] bArr2, @Const int[] iArr);

    public static native int av_image_get_buffer_size(@Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

    public static native int av_image_get_linesize(@Cast({"AVPixelFormat"}) int i, int i2, int i3);

    @Cast({"unsigned"})
    public static native int av_int_list_length_for_size(@Cast({"unsigned"}) int i, @Const Pointer pointer, @Cast({"uint64_t"}) long j);

    @ByVal
    public static native AVRational av_inv_q(@ByVal AVRational aVRational);

    public static native void av_log(Pointer pointer, int i, String str);

    public static native void av_log(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer);

    public static native void av_log_default_callback(Pointer pointer, int i, String str, @Cast({"va_list*"}) @ByVal Pointer pointer2);

    public static native void av_log_default_callback(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"va_list*"}) @ByVal Pointer pointer2);

    public static native void av_log_format_line(Pointer pointer, int i, String str, @Cast({"va_list*"}) @ByVal Pointer pointer2, @Cast({"char*"}) ByteBuffer byteBuffer, int i2, IntBuffer intBuffer);

    public static native void av_log_format_line(Pointer pointer, int i, String str, @Cast({"va_list*"}) @ByVal Pointer pointer2, @Cast({"char*"}) BytePointer bytePointer, int i2, IntPointer intPointer);

    public static native void av_log_format_line(Pointer pointer, int i, String str, @Cast({"va_list*"}) @ByVal Pointer pointer2, @Cast({"char*"}) byte[] bArr, int i2, int[] iArr);

    public static native void av_log_format_line(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"va_list*"}) @ByVal Pointer pointer2, @Cast({"char*"}) ByteBuffer byteBuffer, int i2, IntBuffer intBuffer);

    public static native void av_log_format_line(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"va_list*"}) @ByVal Pointer pointer2, @Cast({"char*"}) BytePointer bytePointer2, int i2, IntPointer intPointer);

    public static native void av_log_format_line(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"va_list*"}) @ByVal Pointer pointer2, @Cast({"char*"}) byte[] bArr, int i2, int[] iArr);

    public static native int av_log_get_flags();

    public static native int av_log_get_level();

    public static native void av_log_set_callback(Callback_Pointer_int_BytePointer_Pointer callback_Pointer_int_BytePointer_Pointer);

    public static native void av_log_set_callback(Callback_Pointer_int_String_Pointer callback_Pointer_int_String_Pointer);

    public static native void av_log_set_flags(int i);

    public static native void av_log_set_level(int i);

    @Cast({"char*"})
    public static native ByteBuffer av_make_error_string(@Cast({"char*"}) ByteBuffer byteBuffer, @Cast({"size_t"}) long j, int i);

    @Cast({"char*"})
    public static native BytePointer av_make_error_string(@Cast({"char*"}) BytePointer bytePointer, @Cast({"size_t"}) long j, int i);

    @Cast({"char*"})
    public static native byte[] av_make_error_string(@Cast({"char*"}) byte[] bArr, @Cast({"size_t"}) long j, int i);

    @ByVal
    public static native AVRational av_make_q(int i, int i2);

    public static native Pointer av_malloc(@Cast({"size_t"}) long j);

    public static native Pointer av_malloc_array(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

    public static native Pointer av_mallocz(@Cast({"size_t"}) long j);

    public static native Pointer av_mallocz_array(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

    public static native void av_max_alloc(@Cast({"size_t"}) long j);

    public static native void av_memcpy_backptr(@Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, int i2);

    public static native void av_memcpy_backptr(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, int i2);

    public static native void av_memcpy_backptr(@Cast({"uint8_t*"}) byte[] bArr, int i, int i2);

    public static native Pointer av_memdup(@Const Pointer pointer, @Cast({"size_t"}) long j);

    @ByVal
    public static native AVRational av_mul_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

    public static native int av_nearer_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2, @ByVal AVRational aVRational3);

    @Deprecated
    @Const
    public static native AVOption av_next_option(Pointer pointer, @Const AVOption aVOption);

    @Const
    public static native AVClass av_opt_child_class_next(@Const AVClass aVClass, @Const AVClass aVClass2);

    public static native Pointer av_opt_child_next(Pointer pointer, Pointer pointer2);

    public static native int av_opt_copy(Pointer pointer, Pointer pointer2);

    public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, String str, DoubleBuffer doubleBuffer);

    public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, String str, DoublePointer doublePointer);

    public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, String str, double[] dArr);

    public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, DoubleBuffer doubleBuffer);

    public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, DoublePointer doublePointer);

    public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, double[] dArr);

    public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, String str, IntBuffer intBuffer);

    public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, String str, IntPointer intPointer);

    public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, String str, int[] iArr);

    public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, IntBuffer intBuffer);

    public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, IntPointer intPointer);

    public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, int[] iArr);

    public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, String str, FloatBuffer floatBuffer);

    public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, String str, FloatPointer floatPointer);

    public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, String str, float[] fArr);

    public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, FloatBuffer floatBuffer);

    public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, FloatPointer floatPointer);

    public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, float[] fArr);

    public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, String str, IntBuffer intBuffer);

    public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, String str, IntPointer intPointer);

    public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, String str, int[] iArr);

    public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, IntBuffer intBuffer);

    public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, IntPointer intPointer);

    public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, int[] iArr);

    public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, String str, LongBuffer longBuffer);

    public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, String str, LongPointer longPointer);

    public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, String str, long[] jArr);

    public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, LongBuffer longBuffer);

    public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, LongPointer longPointer);

    public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, long[] jArr);

    public static native int av_opt_eval_q(Pointer pointer, @Const AVOption aVOption, String str, AVRational aVRational);

    public static native int av_opt_eval_q(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, AVRational aVRational);

    @Const
    public static native AVOption av_opt_find(Pointer pointer, String str, String str2, int i, int i2);

    @Const
    public static native AVOption av_opt_find(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, int i2);

    @Const
    public static native AVOption av_opt_find2(Pointer pointer, String str, String str2, int i, int i2, @ByPtrPtr @Cast({"void**"}) Pointer pointer2);

    @Const
    public static native AVOption av_opt_find2(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, int i2, @ByPtrPtr @Cast({"void**"}) Pointer pointer2);

    @Const
    public static native AVOption av_opt_find2(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, int i2, @Cast({"void**"}) PointerPointer pointerPointer);

    public static native int av_opt_flag_is_set(Pointer pointer, String str, String str2);

    public static native int av_opt_flag_is_set(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2);

    public static native void av_opt_free(Pointer pointer);

    public static native void av_opt_freep_ranges(@Cast({"AVOptionRanges**"}) PointerPointer pointerPointer);

    public static native void av_opt_freep_ranges(@ByPtrPtr AVOptionRanges aVOptionRanges);

    public static native int av_opt_get(Pointer pointer, String str, int i, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer);

    public static native int av_opt_get(Pointer pointer, String str, int i, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer);

    public static native int av_opt_get(Pointer pointer, String str, int i, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr);

    public static native int av_opt_get(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer);

    public static native int av_opt_get(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer2);

    public static native int av_opt_get(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"uint8_t**"}) PointerPointer pointerPointer);

    public static native int av_opt_get(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr);

    public static native int av_opt_get_channel_layout(Pointer pointer, String str, int i, LongBuffer longBuffer);

    public static native int av_opt_get_channel_layout(Pointer pointer, String str, int i, LongPointer longPointer);

    public static native int av_opt_get_channel_layout(Pointer pointer, String str, int i, long[] jArr);

    public static native int av_opt_get_channel_layout(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, LongBuffer longBuffer);

    public static native int av_opt_get_channel_layout(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, LongPointer longPointer);

    public static native int av_opt_get_channel_layout(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, long[] jArr);

    public static native int av_opt_get_dict_val(Pointer pointer, String str, int i, @ByPtrPtr AVDictionary aVDictionary);

    public static native int av_opt_get_dict_val(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVDictionary**"}) PointerPointer pointerPointer);

    public static native int av_opt_get_dict_val(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @ByPtrPtr AVDictionary aVDictionary);

    public static native int av_opt_get_double(Pointer pointer, String str, int i, DoubleBuffer doubleBuffer);

    public static native int av_opt_get_double(Pointer pointer, String str, int i, DoublePointer doublePointer);

    public static native int av_opt_get_double(Pointer pointer, String str, int i, double[] dArr);

    public static native int av_opt_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, DoubleBuffer doubleBuffer);

    public static native int av_opt_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, DoublePointer doublePointer);

    public static native int av_opt_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, double[] dArr);

    public static native int av_opt_get_image_size(Pointer pointer, String str, int i, IntBuffer intBuffer, IntBuffer intBuffer2);

    public static native int av_opt_get_image_size(Pointer pointer, String str, int i, IntPointer intPointer, IntPointer intPointer2);

    public static native int av_opt_get_image_size(Pointer pointer, String str, int i, int[] iArr, int[] iArr2);

    public static native int av_opt_get_image_size(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, IntBuffer intBuffer, IntBuffer intBuffer2);

    public static native int av_opt_get_image_size(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, IntPointer intPointer, IntPointer intPointer2);

    public static native int av_opt_get_image_size(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, int[] iArr, int[] iArr2);

    public static native int av_opt_get_int(Pointer pointer, String str, int i, LongBuffer longBuffer);

    public static native int av_opt_get_int(Pointer pointer, String str, int i, LongPointer longPointer);

    public static native int av_opt_get_int(Pointer pointer, String str, int i, long[] jArr);

    public static native int av_opt_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, LongBuffer longBuffer);

    public static native int av_opt_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, LongPointer longPointer);

    public static native int av_opt_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, long[] jArr);

    public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) ByteBuffer byteBuffer, String str, String str2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer2, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer3);

    public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) ByteBuffer byteBuffer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer2, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer3);

    public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer, String str, String str2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer2, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer3);

    public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer4, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer5);

    public static native int av_opt_get_key_value(@Cast({"const char**"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"unsigned"}) int i, @Cast({"char**"}) PointerPointer pointerPointer2, @Cast({"char**"}) PointerPointer pointerPointer3);

    public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) byte[] bArr, String str, String str2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) byte[] bArr2, @ByPtrPtr @Cast({"char**"}) byte[] bArr3);

    public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) byte[] bArr, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) byte[] bArr2, @ByPtrPtr @Cast({"char**"}) byte[] bArr3);

    public static native int av_opt_get_pixel_fmt(Pointer pointer, String str, int i, @Cast({"AVPixelFormat*"}) IntBuffer intBuffer);

    public static native int av_opt_get_pixel_fmt(Pointer pointer, String str, int i, @Cast({"AVPixelFormat*"}) IntPointer intPointer);

    public static native int av_opt_get_pixel_fmt(Pointer pointer, String str, int i, @Cast({"AVPixelFormat*"}) int[] iArr);

    public static native int av_opt_get_pixel_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVPixelFormat*"}) IntBuffer intBuffer);

    public static native int av_opt_get_pixel_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVPixelFormat*"}) IntPointer intPointer);

    public static native int av_opt_get_pixel_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVPixelFormat*"}) int[] iArr);

    public static native int av_opt_get_q(Pointer pointer, String str, int i, AVRational aVRational);

    public static native int av_opt_get_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, AVRational aVRational);

    public static native int av_opt_get_sample_fmt(Pointer pointer, String str, int i, @Cast({"AVSampleFormat*"}) IntBuffer intBuffer);

    public static native int av_opt_get_sample_fmt(Pointer pointer, String str, int i, @Cast({"AVSampleFormat*"}) IntPointer intPointer);

    public static native int av_opt_get_sample_fmt(Pointer pointer, String str, int i, @Cast({"AVSampleFormat*"}) int[] iArr);

    public static native int av_opt_get_sample_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVSampleFormat*"}) IntBuffer intBuffer);

    public static native int av_opt_get_sample_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVSampleFormat*"}) IntPointer intPointer);

    public static native int av_opt_get_sample_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVSampleFormat*"}) int[] iArr);

    public static native int av_opt_get_video_rate(Pointer pointer, String str, int i, AVRational aVRational);

    public static native int av_opt_get_video_rate(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, AVRational aVRational);

    public static native int av_opt_is_set_to_default(Pointer pointer, @Const AVOption aVOption);

    public static native int av_opt_is_set_to_default_by_name(Pointer pointer, String str, int i);

    public static native int av_opt_is_set_to_default_by_name(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

    @Const
    public static native AVOption av_opt_next(Pointer pointer, @Const AVOption aVOption);

    public static native Pointer av_opt_ptr(@Const AVClass aVClass, Pointer pointer, String str);

    public static native Pointer av_opt_ptr(@Const AVClass aVClass, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer);

    public static native int av_opt_query_ranges(@Cast({"AVOptionRanges**"}) PointerPointer pointerPointer, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

    public static native int av_opt_query_ranges(@ByPtrPtr AVOptionRanges aVOptionRanges, Pointer pointer, String str, int i);

    public static native int av_opt_query_ranges(@ByPtrPtr AVOptionRanges aVOptionRanges, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

    public static native int av_opt_query_ranges_default(@Cast({"AVOptionRanges**"}) PointerPointer pointerPointer, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

    public static native int av_opt_query_ranges_default(@ByPtrPtr AVOptionRanges aVOptionRanges, Pointer pointer, String str, int i);

    public static native int av_opt_query_ranges_default(@ByPtrPtr AVOptionRanges aVOptionRanges, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

    public static native int av_opt_serialize(Pointer pointer, int i, int i2, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer, byte b, byte b2);

    public static native int av_opt_serialize(Pointer pointer, int i, int i2, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer, byte b, byte b2);

    public static native int av_opt_serialize(Pointer pointer, int i, int i2, @Cast({"char**"}) PointerPointer pointerPointer, byte b, byte b2);

    public static native int av_opt_serialize(Pointer pointer, int i, int i2, @ByPtrPtr @Cast({"char**"}) byte[] bArr, byte b, byte b2);

    public static native int av_opt_set(Pointer pointer, String str, String str2, int i);

    public static native int av_opt_set(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i);

    public static native int av_opt_set_bin(Pointer pointer, String str, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer, int i, int i2);

    public static native int av_opt_set_bin(Pointer pointer, String str, @Cast({"const uint8_t*"}) BytePointer bytePointer, int i, int i2);

    public static native int av_opt_set_bin(Pointer pointer, String str, @Cast({"const uint8_t*"}) byte[] bArr, int i, int i2);

    public static native int av_opt_set_bin(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer, int i, int i2);

    public static native int av_opt_set_bin(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i, int i2);

    public static native int av_opt_set_bin(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const uint8_t*"}) byte[] bArr, int i, int i2);

    public static native int av_opt_set_channel_layout(Pointer pointer, String str, long j, int i);

    public static native int av_opt_set_channel_layout(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, long j, int i);

    public static native void av_opt_set_defaults(Pointer pointer);

    @Deprecated
    public static native void av_opt_set_defaults2(Pointer pointer, int i, int i2);

    public static native int av_opt_set_dict(Pointer pointer, @Cast({"AVDictionary**"}) PointerPointer pointerPointer);

    public static native int av_opt_set_dict(Pointer pointer, @ByPtrPtr AVDictionary aVDictionary);

    public static native int av_opt_set_dict2(Pointer pointer, @Cast({"AVDictionary**"}) PointerPointer pointerPointer, int i);

    public static native int av_opt_set_dict2(Pointer pointer, @ByPtrPtr AVDictionary aVDictionary, int i);

    public static native int av_opt_set_dict_val(Pointer pointer, String str, @Const AVDictionary aVDictionary, int i);

    public static native int av_opt_set_dict_val(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Const AVDictionary aVDictionary, int i);

    public static native int av_opt_set_double(Pointer pointer, String str, double d, int i);

    public static native int av_opt_set_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, double d, int i);

    public static native int av_opt_set_from_string(Pointer pointer, String str, @ByPtrPtr @Cast({"const char*const*"}) ByteBuffer byteBuffer, String str2, String str3);

    public static native int av_opt_set_from_string(Pointer pointer, String str, @ByPtrPtr @Cast({"const char*const*"}) BytePointer bytePointer, String str2, String str3);

    public static native int av_opt_set_from_string(Pointer pointer, String str, @ByPtrPtr @Cast({"const char*const*"}) byte[] bArr, String str2, String str3);

    public static native int av_opt_set_from_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"const char*const*"}) ByteBuffer byteBuffer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3);

    public static native int av_opt_set_from_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"const char*const*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"const char*"}) BytePointer bytePointer4);

    public static native int av_opt_set_from_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*const*"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3);

    public static native int av_opt_set_from_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"const char*const*"}) byte[] bArr, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3);

    public static native int av_opt_set_image_size(Pointer pointer, String str, int i, int i2, int i3);

    public static native int av_opt_set_image_size(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, int i2, int i3);

    public static native int av_opt_set_int(Pointer pointer, String str, long j, int i);

    public static native int av_opt_set_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, long j, int i);

    public static native int av_opt_set_pixel_fmt(Pointer pointer, String str, @Cast({"AVPixelFormat"}) int i, int i2);

    public static native int av_opt_set_pixel_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVPixelFormat"}) int i, int i2);

    public static native int av_opt_set_q(Pointer pointer, String str, @ByVal AVRational aVRational, int i);

    public static native int av_opt_set_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByVal AVRational aVRational, int i);

    public static native int av_opt_set_sample_fmt(Pointer pointer, String str, @Cast({"AVSampleFormat"}) int i, int i2);

    public static native int av_opt_set_sample_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVSampleFormat"}) int i, int i2);

    public static native int av_opt_set_video_rate(Pointer pointer, String str, @ByVal AVRational aVRational, int i);

    public static native int av_opt_set_video_rate(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByVal AVRational aVRational, int i);

    public static native int av_opt_show2(Pointer pointer, Pointer pointer2, int i, int i2);

    public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) IntBuffer intBuffer, String str);

    public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) IntBuffer intBuffer, @Cast({"const char*"}) BytePointer bytePointer);

    public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) IntPointer intPointer, String str);

    public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) IntPointer intPointer, @Cast({"const char*"}) BytePointer bytePointer);

    public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) int[] iArr, String str);

    public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) int[] iArr, @Cast({"const char*"}) BytePointer bytePointer);

    @Deprecated
    public static native int av_parse_cpu_flags(String str);

    @Deprecated
    public static native int av_parse_cpu_flags(@Cast({"const char*"}) BytePointer bytePointer);

    public static native int av_pix_fmt_count_planes(@Cast({"AVPixelFormat"}) int i);

    @Const
    public static native AVPixFmtDescriptor av_pix_fmt_desc_get(@Cast({"AVPixelFormat"}) int i);

    @Cast({"AVPixelFormat"})
    public static native int av_pix_fmt_desc_get_id(@Const AVPixFmtDescriptor aVPixFmtDescriptor);

    @Const
    public static native AVPixFmtDescriptor av_pix_fmt_desc_next(@Const AVPixFmtDescriptor aVPixFmtDescriptor);

    @MemberGetter
    @Deprecated
    @Const
    public static native AVPixFmtDescriptor av_pix_fmt_descriptors();

    @ByRef
    @Const
    @MemberGetter
    @Deprecated
    public static native AVPixFmtDescriptor av_pix_fmt_descriptors(int i);

    public static native int av_pix_fmt_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, IntBuffer intBuffer, IntBuffer intBuffer2);

    public static native int av_pix_fmt_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, IntPointer intPointer, IntPointer intPointer2);

    public static native int av_pix_fmt_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, int[] iArr, int[] iArr2);

    @Cast({"AVPixelFormat"})
    public static native int av_pix_fmt_swap_endianness(@Cast({"AVPixelFormat"}) int i);

    public static native double av_q2d(@ByVal AVRational aVRational);

    @Cast({"uint32_t"})
    public static native int av_q2intfloat(@ByVal AVRational aVRational);

    public static native void av_read_image_line(@Cast({"uint16_t*"}) ShortBuffer shortBuffer, @ByPtrPtr @Cast({"const uint8_t**"}) ByteBuffer byteBuffer, @Const IntBuffer intBuffer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4, int i5);

    public static native void av_read_image_line(@Cast({"uint16_t*"}) ShortPointer shortPointer, @ByPtrPtr @Cast({"const uint8_t**"}) BytePointer bytePointer, @Const IntPointer intPointer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4, int i5);

    public static native void av_read_image_line(@Cast({"uint16_t*"}) ShortPointer shortPointer, @Cast({"const uint8_t**"}) PointerPointer pointerPointer, @Const IntPointer intPointer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4, int i5);

    public static native void av_read_image_line(@Cast({"uint16_t*"}) short[] sArr, @ByPtrPtr @Cast({"const uint8_t**"}) byte[] bArr, @Const int[] iArr, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4, int i5);

    public static native Pointer av_realloc(Pointer pointer, @Cast({"size_t"}) long j);

    public static native Pointer av_realloc_array(Pointer pointer, @Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

    public static native Pointer av_realloc_f(Pointer pointer, @Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

    public static native int av_reallocp(Pointer pointer, @Cast({"size_t"}) long j);

    public static native int av_reallocp_array(Pointer pointer, @Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

    public static native int av_reduce(IntBuffer intBuffer, IntBuffer intBuffer2, long j, long j2, long j3);

    public static native int av_reduce(IntPointer intPointer, IntPointer intPointer2, long j, long j2, long j3);

    public static native int av_reduce(int[] iArr, int[] iArr2, long j, long j2, long j3);

    public static native long av_rescale(long j, long j2, long j3);

    public static native long av_rescale_delta(@ByVal AVRational aVRational, long j, @ByVal AVRational aVRational2, int i, LongBuffer longBuffer, @ByVal AVRational aVRational3);

    public static native long av_rescale_delta(@ByVal AVRational aVRational, long j, @ByVal AVRational aVRational2, int i, LongPointer longPointer, @ByVal AVRational aVRational3);

    public static native long av_rescale_delta(@ByVal AVRational aVRational, long j, @ByVal AVRational aVRational2, int i, long[] jArr, @ByVal AVRational aVRational3);

    public static native long av_rescale_q(long j, @ByVal AVRational aVRational, @ByVal AVRational aVRational2);

    public static native long av_rescale_q_rnd(long j, @ByVal AVRational aVRational, @ByVal AVRational aVRational2, @Cast({"AVRounding"}) int i);

    public static native long av_rescale_rnd(long j, long j2, long j3, @Cast({"AVRounding"}) int i);

    public static native int av_sample_fmt_is_planar(@Cast({"AVSampleFormat"}) int i);

    public static native int av_samples_alloc(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_alloc(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_alloc(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_alloc(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_alloc_array_and_samples(@Cast({"uint8_t***"}) PointerPointer pointerPointer, IntBuffer intBuffer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_alloc_array_and_samples(@Cast({"uint8_t***"}) PointerPointer pointerPointer, IntPointer intPointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_alloc_array_and_samples(@Cast({"uint8_t***"}) PointerPointer pointerPointer, int[] iArr, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_copy(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, @ByPtrPtr @Cast({"uint8_t*const*"}) ByteBuffer byteBuffer2, int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5);

    public static native int av_samples_copy(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, @ByPtrPtr @Cast({"uint8_t*const*"}) BytePointer bytePointer2, int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5);

    public static native int av_samples_copy(@Cast({"uint8_t**"}) PointerPointer pointerPointer, @Cast({"uint8_t*const*"}) PointerPointer pointerPointer2, int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5);

    public static native int av_samples_copy(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, @ByPtrPtr @Cast({"uint8_t*const*"}) byte[] bArr2, int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5);

    public static native int av_samples_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_fill_arrays(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @Cast({"const uint8_t*"}) byte[] bArr2, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_get_buffer_size(IntBuffer intBuffer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_get_buffer_size(IntPointer intPointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_get_buffer_size(int[] iArr, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

    public static native int av_samples_set_silence(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4);

    public static native int av_samples_set_silence(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4);

    public static native int av_samples_set_silence(@Cast({"uint8_t**"}) PointerPointer pointerPointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4);

    public static native int av_samples_set_silence(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4);

    @Deprecated
    public static native void av_set_cpu_flags_mask(int i);

    @Deprecated
    @Const
    public static native AVOption av_set_double(Pointer pointer, String str, double d);

    @Deprecated
    @Const
    public static native AVOption av_set_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, double d);

    @Deprecated
    @Const
    public static native AVOption av_set_int(Pointer pointer, String str, long j);

    @Deprecated
    @Const
    public static native AVOption av_set_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, long j);

    public static native int av_set_options_string(Pointer pointer, String str, String str2, String str3);

    public static native int av_set_options_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3);

    @Deprecated
    @Const
    public static native AVOption av_set_q(Pointer pointer, String str, @ByVal AVRational aVRational);

    @Deprecated
    @Const
    public static native AVOption av_set_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByVal AVRational aVRational);

    @Deprecated
    public static native int av_set_string3(Pointer pointer, String str, String str2, int i, @ByPtrPtr @Const AVOption aVOption);

    @Deprecated
    public static native int av_set_string3(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, @Cast({"const AVOption**"}) PointerPointer pointerPointer);

    @Deprecated
    public static native int av_set_string3(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, @ByPtrPtr @Const AVOption aVOption);

    public static native int av_size_mult(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2, @Cast({"size_t*"}) SizeTPointer sizeTPointer);

    public static native AVStereo3D av_stereo3d_alloc();

    public static native AVStereo3D av_stereo3d_create_side_data(AVFrame aVFrame);

    @Cast({"char*"})
    public static native ByteBuffer av_strdup(String str);

    @Cast({"char*"})
    public static native BytePointer av_strdup(@Cast({"const char*"}) BytePointer bytePointer);

    public static native int av_strerror(int i, @Cast({"char*"}) ByteBuffer byteBuffer, @Cast({"size_t"}) long j);

    public static native int av_strerror(int i, @Cast({"char*"}) BytePointer bytePointer, @Cast({"size_t"}) long j);

    public static native int av_strerror(int i, @Cast({"char*"}) byte[] bArr, @Cast({"size_t"}) long j);

    @Cast({"char*"})
    public static native ByteBuffer av_strndup(String str, @Cast({"size_t"}) long j);

    @Cast({"char*"})
    public static native BytePointer av_strndup(@Cast({"const char*"}) BytePointer bytePointer, @Cast({"size_t"}) long j);

    @ByVal
    public static native AVRational av_sub_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

    public static native void av_vlog(Pointer pointer, int i, String str, @Cast({"va_list*"}) @ByVal Pointer pointer2);

    public static native void av_vlog(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"va_list*"}) @ByVal Pointer pointer2);

    public static native void av_write_image_line(@Cast({"const uint16_t*"}) ShortBuffer shortBuffer, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, @Const IntBuffer intBuffer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4);

    public static native void av_write_image_line(@Cast({"const uint16_t*"}) ShortPointer shortPointer, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, @Const IntPointer intPointer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4);

    public static native void av_write_image_line(@Cast({"const uint16_t*"}) ShortPointer shortPointer, @Cast({"uint8_t**"}) PointerPointer pointerPointer, @Const IntPointer intPointer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4);

    public static native void av_write_image_line(@Cast({"const uint16_t*"}) short[] sArr, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, @Const int[] iArr, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4);

    public static native Pointer av_x_if_null(@Const Pointer pointer, @Const Pointer pointer2);

    @Cast({"AVDictionary**"})
    public static native PointerPointer avpriv_frame_get_metadatap(AVFrame aVFrame);

    @Cast({"const char*"})
    public static native BytePointer avutil_configuration();

    @Cast({"const char*"})
    public static native BytePointer avutil_license();

    @Cast({"unsigned"})
    public static native int avutil_version();

    static {
        Loader.load();
    }
}
