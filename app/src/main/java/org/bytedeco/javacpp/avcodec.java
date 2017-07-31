package org.bytedeco.javacpp;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.bytedeco.javacpp.annotation.ByPtrPtr;
import org.bytedeco.javacpp.annotation.ByRef;
import org.bytedeco.javacpp.annotation.ByVal;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Opaque;
import org.bytedeco.javacpp.avutil.AVBufferRef;
import org.bytedeco.javacpp.avutil.AVClass;
import org.bytedeco.javacpp.avutil.AVDictionary;
import org.bytedeco.javacpp.avutil.AVFrame;
import org.bytedeco.javacpp.avutil.AVRational;

public class avcodec extends org.bytedeco.javacpp.presets.avcodec {
    public static final int AVDISCARD_ALL = 48;
    public static final int AVDISCARD_BIDIR = 16;
    public static final int AVDISCARD_DEFAULT = 0;
    public static final int AVDISCARD_NONE = -16;
    public static final int AVDISCARD_NONINTRA = 24;
    public static final int AVDISCARD_NONKEY = 32;
    public static final int AVDISCARD_NONREF = 8;
    public static final int AV_AUDIO_SERVICE_TYPE_COMMENTARY = 5;
    public static final int AV_AUDIO_SERVICE_TYPE_DIALOGUE = 4;
    public static final int AV_AUDIO_SERVICE_TYPE_EFFECTS = 1;
    public static final int AV_AUDIO_SERVICE_TYPE_EMERGENCY = 6;
    public static final int AV_AUDIO_SERVICE_TYPE_HEARING_IMPAIRED = 3;
    public static final int AV_AUDIO_SERVICE_TYPE_KARAOKE = 8;
    public static final int AV_AUDIO_SERVICE_TYPE_MAIN = 0;
    public static final int AV_AUDIO_SERVICE_TYPE_NB = 9;
    public static final int AV_AUDIO_SERVICE_TYPE_VISUALLY_IMPAIRED = 2;
    public static final int AV_AUDIO_SERVICE_TYPE_VOICE_OVER = 7;
    public static final int AV_CODEC_ID_012V = AV_CODEC_ID_012V();
    public static final int AV_CODEC_ID_4XM = 35;
    public static final int AV_CODEC_ID_8BPS = 49;
    public static final int AV_CODEC_ID_8SVX_EXP = 86071;
    public static final int AV_CODEC_ID_8SVX_FIB = 86072;
    public static final int AV_CODEC_ID_A64_MULTI = 144;
    public static final int AV_CODEC_ID_A64_MULTI5 = 145;
    public static final int AV_CODEC_ID_AAC = 86018;
    public static final int AV_CODEC_ID_AAC_LATM = 86066;
    public static final int AV_CODEC_ID_AASC = 75;
    public static final int AV_CODEC_ID_AC3 = 86019;
    public static final int AV_CODEC_ID_ADPCM_4XM = 69639;
    public static final int AV_CODEC_ID_ADPCM_ADX = 69641;
    public static final int AV_CODEC_ID_ADPCM_AFC = AV_CODEC_ID_ADPCM_AFC();
    public static final int AV_CODEC_ID_ADPCM_CT = 69644;
    public static final int AV_CODEC_ID_ADPCM_DTK = AV_CODEC_ID_ADPCM_DTK();
    public static final int AV_CODEC_ID_ADPCM_EA = 69642;
    public static final int AV_CODEC_ID_ADPCM_EA_MAXIS_XA = 69658;
    public static final int AV_CODEC_ID_ADPCM_EA_R1 = 69652;
    public static final int AV_CODEC_ID_ADPCM_EA_R2 = 69654;
    public static final int AV_CODEC_ID_ADPCM_EA_R3 = 69653;
    public static final int AV_CODEC_ID_ADPCM_EA_XAS = 69657;
    public static final int AV_CODEC_ID_ADPCM_G722 = 69660;
    public static final int AV_CODEC_ID_ADPCM_G726 = 69643;
    public static final int AV_CODEC_ID_ADPCM_G726LE = AV_CODEC_ID_ADPCM_G726LE();
    public static final int AV_CODEC_ID_ADPCM_IMA_AMV = 69651;
    public static final int AV_CODEC_ID_ADPCM_IMA_APC = 69661;
    public static final int AV_CODEC_ID_ADPCM_IMA_DK3 = 69634;
    public static final int AV_CODEC_ID_ADPCM_IMA_DK4 = 69635;
    public static final int AV_CODEC_ID_ADPCM_IMA_EA_EACS = 69656;
    public static final int AV_CODEC_ID_ADPCM_IMA_EA_SEAD = 69655;
    public static final int AV_CODEC_ID_ADPCM_IMA_ISS = 69659;
    public static final int AV_CODEC_ID_ADPCM_IMA_OKI = AV_CODEC_ID_ADPCM_IMA_OKI();
    public static final int AV_CODEC_ID_ADPCM_IMA_QT = 69632;
    public static final int AV_CODEC_ID_ADPCM_IMA_RAD = AV_CODEC_ID_ADPCM_IMA_RAD();
    public static final int AV_CODEC_ID_ADPCM_IMA_SMJPEG = 69637;
    public static final int AV_CODEC_ID_ADPCM_IMA_WAV = 69633;
    public static final int AV_CODEC_ID_ADPCM_IMA_WS = 69636;
    public static final int AV_CODEC_ID_ADPCM_MS = 69638;
    public static final int AV_CODEC_ID_ADPCM_SBPRO_2 = 69649;
    public static final int AV_CODEC_ID_ADPCM_SBPRO_3 = 69648;
    public static final int AV_CODEC_ID_ADPCM_SBPRO_4 = 69647;
    public static final int AV_CODEC_ID_ADPCM_SWF = 69645;
    public static final int AV_CODEC_ID_ADPCM_THP = 69650;
    public static final int AV_CODEC_ID_ADPCM_VIMA = AV_CODEC_ID_ADPCM_VIMA();
    public static final int AV_CODEC_ID_ADPCM_VIMA_DEPRECATED = 69662;
    public static final int AV_CODEC_ID_ADPCM_XA = 69640;
    public static final int AV_CODEC_ID_ADPCM_YAMAHA = 69646;
    public static final int AV_CODEC_ID_AIC = 170;
    public static final int AV_CODEC_ID_ALAC = 86032;
    public static final int AV_CODEC_ID_ALIAS_PIX = 177;
    public static final int AV_CODEC_ID_AMR_NB = 73728;
    public static final int AV_CODEC_ID_AMR_WB = 73729;
    public static final int AV_CODEC_ID_AMV = 108;
    public static final int AV_CODEC_ID_ANM = 135;
    public static final int AV_CODEC_ID_ANSI = 143;
    public static final int AV_CODEC_ID_APE = 86049;
    public static final int AV_CODEC_ID_APNG = AV_CODEC_ID_APNG();
    public static final int AV_CODEC_ID_ASS = AV_CODEC_ID_ASS();
    public static final int AV_CODEC_ID_ASV1 = 32;
    public static final int AV_CODEC_ID_ASV2 = 33;
    public static final int AV_CODEC_ID_ATRAC1 = 86063;
    public static final int AV_CODEC_ID_ATRAC3 = 86047;
    public static final int AV_CODEC_ID_ATRAC3P = 86056;
    public static final int AV_CODEC_ID_AURA = 124;
    public static final int AV_CODEC_ID_AURA2 = 125;
    public static final int AV_CODEC_ID_AVRN = AV_CODEC_ID_AVRN();
    public static final int AV_CODEC_ID_AVRP = AV_CODEC_ID_AVRP();
    public static final int AV_CODEC_ID_AVS = 83;
    public static final int AV_CODEC_ID_AVUI = AV_CODEC_ID_AVUI();
    public static final int AV_CODEC_ID_AYUV = AV_CODEC_ID_AYUV();
    public static final int AV_CODEC_ID_BETHSOFTVID = 104;
    public static final int AV_CODEC_ID_BFI = 118;
    public static final int AV_CODEC_ID_BINKAUDIO_DCT = 86065;
    public static final int AV_CODEC_ID_BINKAUDIO_RDFT = 86064;
    public static final int AV_CODEC_ID_BINKVIDEO = 136;
    public static final int AV_CODEC_ID_BINTEXT = AV_CODEC_ID_BINTEXT();
    public static final int AV_CODEC_ID_BIN_DATA = AV_CODEC_ID_BIN_DATA();
    public static final int AV_CODEC_ID_BMP = 79;
    public static final int AV_CODEC_ID_BMV_AUDIO = 86073;
    public static final int AV_CODEC_ID_BMV_VIDEO = 155;
    public static final int AV_CODEC_ID_BRENDER_PIX = AV_CODEC_ID_BRENDER_PIX();
    public static final int AV_CODEC_ID_BRENDER_PIX_DEPRECATED = 178;
    public static final int AV_CODEC_ID_C93 = 103;
    public static final int AV_CODEC_ID_CAVS = 88;
    public static final int AV_CODEC_ID_CDGRAPHICS = 133;
    public static final int AV_CODEC_ID_CDXL = 160;
    public static final int AV_CODEC_ID_CELT = 86068;
    public static final int AV_CODEC_ID_CINEPAK = 44;
    public static final int AV_CODEC_ID_CLJR = 37;
    public static final int AV_CODEC_ID_CLLC = 167;
    public static final int AV_CODEC_ID_CMV = 119;
    public static final int AV_CODEC_ID_COMFORT_NOISE = 86078;
    public static final int AV_CODEC_ID_COOK = 86036;
    public static final int AV_CODEC_ID_CPIA = AV_CODEC_ID_CPIA();
    public static final int AV_CODEC_ID_CSCD = 80;
    public static final int AV_CODEC_ID_CYUV = 27;
    public static final int AV_CODEC_ID_DFA = 151;
    public static final int AV_CODEC_ID_DIRAC = 117;
    public static final int AV_CODEC_ID_DNXHD = 100;
    public static final int AV_CODEC_ID_DPX = 129;
    public static final int AV_CODEC_ID_DSD_LSBF = AV_CODEC_ID_DSD_LSBF();
    public static final int AV_CODEC_ID_DSD_LSBF_PLANAR = AV_CODEC_ID_DSD_LSBF_PLANAR();
    public static final int AV_CODEC_ID_DSD_MSBF = AV_CODEC_ID_DSD_MSBF();
    public static final int AV_CODEC_ID_DSD_MSBF_PLANAR = AV_CODEC_ID_DSD_MSBF_PLANAR();
    public static final int AV_CODEC_ID_DSICINAUDIO = 86042;
    public static final int AV_CODEC_ID_DSICINVIDEO = 95;
    public static final int AV_CODEC_ID_DSS_SP = 86083;
    public static final int AV_CODEC_ID_DTS = 86020;
    public static final int AV_CODEC_ID_DVAUDIO = 86022;
    public static final int AV_CODEC_ID_DVB_SUBTITLE = 94209;
    public static final int AV_CODEC_ID_DVB_TELETEXT = 94215;
    public static final int AV_CODEC_ID_DVD_NAV = AV_CODEC_ID_DVD_NAV();
    public static final int AV_CODEC_ID_DVD_SUBTITLE = 94208;
    public static final int AV_CODEC_ID_DVVIDEO = 25;
    public static final int AV_CODEC_ID_DXA = 99;
    public static final int AV_CODEC_ID_DXTORY = 157;
    public static final int AV_CODEC_ID_EAC3 = 86057;
    public static final int AV_CODEC_ID_EIA_608 = AV_CODEC_ID_EIA_608();
    public static final int AV_CODEC_ID_ESCAPE124 = 116;
    public static final int AV_CODEC_ID_ESCAPE130 = AV_CODEC_ID_ESCAPE130();
    public static final int AV_CODEC_ID_ESCAPE130_DEPRECATED = 171;
    public static final int AV_CODEC_ID_EVRC = AV_CODEC_ID_EVRC();
    public static final int AV_CODEC_ID_EXR = AV_CODEC_ID_EXR();
    public static final int AV_CODEC_ID_EXR_DEPRECATED = 180;
    public static final int AV_CODEC_ID_FFMETADATA = 135168;
    public static final int AV_CODEC_ID_FFV1 = 34;
    public static final int AV_CODEC_ID_FFVHUFF = 68;
    public static final int AV_CODEC_ID_FFWAVESYNTH = AV_CODEC_ID_FFWAVESYNTH();
    public static final int AV_CODEC_ID_FIC = 176;
    public static final int AV_CODEC_ID_FIRST_AUDIO = 65536;
    public static final int AV_CODEC_ID_FIRST_SUBTITLE = 94208;
    public static final int AV_CODEC_ID_FIRST_UNKNOWN = 98304;
    public static final int AV_CODEC_ID_FLAC = 86028;
    public static final int AV_CODEC_ID_FLASHSV = 87;
    public static final int AV_CODEC_ID_FLASHSV2 = 132;
    public static final int AV_CODEC_ID_FLIC = 51;
    public static final int AV_CODEC_ID_FLV1 = 22;
    public static final int AV_CODEC_ID_FRAPS = 77;
    public static final int AV_CODEC_ID_FRWU = 131;
    public static final int AV_CODEC_ID_G2M = AV_CODEC_ID_G2M();
    public static final int AV_CODEC_ID_G2M_DEPRECATED = 172;
    public static final int AV_CODEC_ID_G723_1 = 86069;
    public static final int AV_CODEC_ID_G729 = 86070;
    public static final int AV_CODEC_ID_GIF = 98;
    public static final int AV_CODEC_ID_GSM = 86034;
    public static final int AV_CODEC_ID_GSM_MS = 86046;
    public static final int AV_CODEC_ID_H261 = 4;
    public static final int AV_CODEC_ID_H263 = 5;
    public static final int AV_CODEC_ID_H263I = 21;
    public static final int AV_CODEC_ID_H263P = 20;
    public static final int AV_CODEC_ID_H264 = 28;
    public static final int AV_CODEC_ID_HDMV_PGS_SUBTITLE = 94214;
    public static final int AV_CODEC_ID_HEVC = AV_CODEC_ID_HEVC();
    public static final int AV_CODEC_ID_HEVC_DEPRECATED = 175;
    public static final int AV_CODEC_ID_HNM4_VIDEO = 174;
    public static final int AV_CODEC_ID_HQX = 186;
    public static final int AV_CODEC_ID_HQ_HQA = 188;
    public static final int AV_CODEC_ID_HUFFYUV = 26;
    public static final int AV_CODEC_ID_IAC = 86075;
    public static final int AV_CODEC_ID_IDCIN = 48;
    public static final int AV_CODEC_ID_IDF = AV_CODEC_ID_IDF();
    public static final int AV_CODEC_ID_IFF_BYTERUN1 = 138;
    public static final int AV_CODEC_ID_IFF_ILBM = 137;
    public static final int AV_CODEC_ID_ILBC = 86076;
    public static final int AV_CODEC_ID_IMC = 86043;
    public static final int AV_CODEC_ID_INDEO2 = 76;
    public static final int AV_CODEC_ID_INDEO3 = 29;
    public static final int AV_CODEC_ID_INDEO4 = 112;
    public static final int AV_CODEC_ID_INDEO5 = 113;
    public static final int AV_CODEC_ID_INTERPLAY_DPCM = 81921;
    public static final int AV_CODEC_ID_INTERPLAY_VIDEO = 40;
    public static final int AV_CODEC_ID_JACOSUB = AV_CODEC_ID_JACOSUB();
    public static final int AV_CODEC_ID_JPEG2000 = 89;
    public static final int AV_CODEC_ID_JPEGLS = 12;
    public static final int AV_CODEC_ID_JV = 150;
    public static final int AV_CODEC_ID_KGV1 = 139;
    public static final int AV_CODEC_ID_KMVC = 86;
    public static final int AV_CODEC_ID_LAGARITH = 148;
    public static final int AV_CODEC_ID_LJPEG = 10;
    public static final int AV_CODEC_ID_LOCO = 73;
    public static final int AV_CODEC_ID_MACE3 = 86025;
    public static final int AV_CODEC_ID_MACE6 = 86026;
    public static final int AV_CODEC_ID_MAD = 130;
    public static final int AV_CODEC_ID_MDEC = 38;
    public static final int AV_CODEC_ID_METASOUND = 86080;
    public static final int AV_CODEC_ID_MICRODVD = AV_CODEC_ID_MICRODVD();
    public static final int AV_CODEC_ID_MIMIC = 114;
    public static final int AV_CODEC_ID_MJPEG = 8;
    public static final int AV_CODEC_ID_MJPEGB = 9;
    public static final int AV_CODEC_ID_MLP = 86045;
    public static final int AV_CODEC_ID_MMVIDEO = 81;
    public static final int AV_CODEC_ID_MOTIONPIXELS = 120;
    public static final int AV_CODEC_ID_MOV_TEXT = 94213;
    public static final int AV_CODEC_ID_MP1 = 86059;
    public static final int AV_CODEC_ID_MP2 = 86016;
    public static final int AV_CODEC_ID_MP3 = 86017;
    public static final int AV_CODEC_ID_MP3ADU = 86029;
    public static final int AV_CODEC_ID_MP3ON4 = 86030;
    public static final int AV_CODEC_ID_MP4ALS = 86062;
    public static final int AV_CODEC_ID_MPEG1VIDEO = 1;
    public static final int AV_CODEC_ID_MPEG2TS = 131072;
    public static final int AV_CODEC_ID_MPEG2VIDEO = 2;
    public static final int AV_CODEC_ID_MPEG2VIDEO_XVMC = 3;
    public static final int AV_CODEC_ID_MPEG4 = 13;
    public static final int AV_CODEC_ID_MPEG4SYSTEMS = 131073;
    public static final int AV_CODEC_ID_MPL2 = AV_CODEC_ID_MPL2();
    public static final int AV_CODEC_ID_MSA1 = 164;
    public static final int AV_CODEC_ID_MSMPEG4V1 = 15;
    public static final int AV_CODEC_ID_MSMPEG4V2 = 16;
    public static final int AV_CODEC_ID_MSMPEG4V3 = 17;
    public static final int AV_CODEC_ID_MSRLE = 46;
    public static final int AV_CODEC_ID_MSS1 = 163;
    public static final int AV_CODEC_ID_MSS2 = 168;
    public static final int AV_CODEC_ID_MSVIDEO1 = 47;
    public static final int AV_CODEC_ID_MSZH = 54;
    public static final int AV_CODEC_ID_MTS2 = 166;
    public static final int AV_CODEC_ID_MUSEPACK7 = 86044;
    public static final int AV_CODEC_ID_MUSEPACK8 = 86051;
    public static final int AV_CODEC_ID_MVC1 = AV_CODEC_ID_MVC1();
    public static final int AV_CODEC_ID_MVC1_DEPRECATED = 184;
    public static final int AV_CODEC_ID_MVC2 = AV_CODEC_ID_MVC2();
    public static final int AV_CODEC_ID_MVC2_DEPRECATED = 185;
    public static final int AV_CODEC_ID_MXPEG = 147;
    public static final int AV_CODEC_ID_NELLYMOSER = 86050;
    public static final int AV_CODEC_ID_NONE = 0;
    public static final int AV_CODEC_ID_NUV = 85;
    public static final int AV_CODEC_ID_ON2AVC = 86082;
    public static final int AV_CODEC_ID_OPUS = AV_CODEC_ID_OPUS();
    public static final int AV_CODEC_ID_OPUS_DEPRECATED = 86077;
    public static final int AV_CODEC_ID_OTF = AV_CODEC_ID_OTF();
    public static final int AV_CODEC_ID_PAF_AUDIO = AV_CODEC_ID_PAF_AUDIO();
    public static final int AV_CODEC_ID_PAF_AUDIO_DEPRECATED = 86081;
    public static final int AV_CODEC_ID_PAF_VIDEO = AV_CODEC_ID_PAF_VIDEO();
    public static final int AV_CODEC_ID_PAF_VIDEO_DEPRECATED = 179;
    public static final int AV_CODEC_ID_PAM = 67;
    public static final int AV_CODEC_ID_PBM = 64;
    public static final int AV_CODEC_ID_PCM_ALAW = 65543;
    public static final int AV_CODEC_ID_PCM_BLURAY = 65560;
    public static final int AV_CODEC_ID_PCM_DVD = 65555;
    public static final int AV_CODEC_ID_PCM_F32BE = 65556;
    public static final int AV_CODEC_ID_PCM_F32LE = 65557;
    public static final int AV_CODEC_ID_PCM_F64BE = 65558;
    public static final int AV_CODEC_ID_PCM_F64LE = 65559;
    public static final int AV_CODEC_ID_PCM_LXF = 65561;
    public static final int AV_CODEC_ID_PCM_MULAW = 65542;
    public static final int AV_CODEC_ID_PCM_S16BE = 65537;
    public static final int AV_CODEC_ID_PCM_S16BE_PLANAR = AV_CODEC_ID_PCM_S16BE_PLANAR();
    public static final int AV_CODEC_ID_PCM_S16LE = 65536;
    public static final int AV_CODEC_ID_PCM_S16LE_PLANAR = 65554;
    public static final int AV_CODEC_ID_PCM_S24BE = 65549;
    public static final int AV_CODEC_ID_PCM_S24DAUD = 65552;
    public static final int AV_CODEC_ID_PCM_S24LE = 65548;
    public static final int AV_CODEC_ID_PCM_S24LE_PLANAR = AV_CODEC_ID_PCM_S24LE_PLANAR();
    public static final int AV_CODEC_ID_PCM_S24LE_PLANAR_DEPRECATED = 65564;
    public static final int AV_CODEC_ID_PCM_S32BE = 65545;
    public static final int AV_CODEC_ID_PCM_S32LE = 65544;
    public static final int AV_CODEC_ID_PCM_S32LE_PLANAR = AV_CODEC_ID_PCM_S32LE_PLANAR();
    public static final int AV_CODEC_ID_PCM_S32LE_PLANAR_DEPRECATED = 65565;
    public static final int AV_CODEC_ID_PCM_S8 = 65540;
    public static final int AV_CODEC_ID_PCM_S8_PLANAR = 65563;
    public static final int AV_CODEC_ID_PCM_U16BE = 65539;
    public static final int AV_CODEC_ID_PCM_U16LE = 65538;
    public static final int AV_CODEC_ID_PCM_U24BE = 65551;
    public static final int AV_CODEC_ID_PCM_U24LE = 65550;
    public static final int AV_CODEC_ID_PCM_U32BE = 65547;
    public static final int AV_CODEC_ID_PCM_U32LE = 65546;
    public static final int AV_CODEC_ID_PCM_U8 = 65541;
    public static final int AV_CODEC_ID_PCM_ZORK = 65553;
    public static final int AV_CODEC_ID_PCX = 110;
    public static final int AV_CODEC_ID_PGM = 65;
    public static final int AV_CODEC_ID_PGMYUV = 66;
    public static final int AV_CODEC_ID_PICTOR = 142;
    public static final int AV_CODEC_ID_PJS = AV_CODEC_ID_PJS();
    public static final int AV_CODEC_ID_PNG = 62;
    public static final int AV_CODEC_ID_PPM = 63;
    public static final int AV_CODEC_ID_PROBE = 102400;
    public static final int AV_CODEC_ID_PRORES = 149;
    public static final int AV_CODEC_ID_PTX = 105;
    public static final int AV_CODEC_ID_QCELP = 86040;
    public static final int AV_CODEC_ID_QDM2 = 86035;
    public static final int AV_CODEC_ID_QDMC = 86067;
    public static final int AV_CODEC_ID_QDRAW = 59;
    public static final int AV_CODEC_ID_QPEG = 61;
    public static final int AV_CODEC_ID_QTRLE = 56;
    public static final int AV_CODEC_ID_R10K = 146;
    public static final int AV_CODEC_ID_R210 = 134;
    public static final int AV_CODEC_ID_RALF = 86074;
    public static final int AV_CODEC_ID_RAWVIDEO = 14;
    public static final int AV_CODEC_ID_RA_144 = 77824;
    public static final int AV_CODEC_ID_RA_288 = 77825;
    public static final int AV_CODEC_ID_REALTEXT = AV_CODEC_ID_REALTEXT();
    public static final int AV_CODEC_ID_RL2 = 115;
    public static final int AV_CODEC_ID_ROQ = 39;
    public static final int AV_CODEC_ID_ROQ_DPCM = 81920;
    public static final int AV_CODEC_ID_RPZA = 43;
    public static final int AV_CODEC_ID_RV10 = 6;
    public static final int AV_CODEC_ID_RV20 = 7;
    public static final int AV_CODEC_ID_RV30 = 69;
    public static final int AV_CODEC_ID_RV40 = 70;
    public static final int AV_CODEC_ID_S302M = 65562;
    public static final int AV_CODEC_ID_SAMI = AV_CODEC_ID_SAMI();
    public static final int AV_CODEC_ID_SANM = AV_CODEC_ID_SANM();
    public static final int AV_CODEC_ID_SANM_DEPRECATED = 182;
    public static final int AV_CODEC_ID_SGI = 102;
    public static final int AV_CODEC_ID_SGIRLE = AV_CODEC_ID_SGIRLE();
    public static final int AV_CODEC_ID_SGIRLE_DEPRECATED = 183;
    public static final int AV_CODEC_ID_SHORTEN = 86031;
    public static final int AV_CODEC_ID_SIPR = 86058;
    public static final int AV_CODEC_ID_SMACKAUDIO = 86039;
    public static final int AV_CODEC_ID_SMACKVIDEO = 84;
    public static final int AV_CODEC_ID_SMC = 50;
    public static final int AV_CODEC_ID_SMPTE_KLV = AV_CODEC_ID_SMPTE_KLV();
    public static final int AV_CODEC_ID_SMV = AV_CODEC_ID_SMV();
    public static final int AV_CODEC_ID_SMVJPEG = AV_CODEC_ID_SMVJPEG();
    public static final int AV_CODEC_ID_SNOW = AV_CODEC_ID_SNOW();
    public static final int AV_CODEC_ID_SOL_DPCM = 81923;
    public static final int AV_CODEC_ID_SONIC = AV_CODEC_ID_SONIC();
    public static final int AV_CODEC_ID_SONIC_LS = AV_CODEC_ID_SONIC_LS();
    public static final int AV_CODEC_ID_SP5X = 11;
    public static final int AV_CODEC_ID_SPEEX = 86052;
    public static final int AV_CODEC_ID_SRT = 94216;
    public static final int AV_CODEC_ID_SSA = 94212;
    public static final int AV_CODEC_ID_STL = AV_CODEC_ID_STL();
    public static final int AV_CODEC_ID_SUBRIP = AV_CODEC_ID_SUBRIP();
    public static final int AV_CODEC_ID_SUBVIEWER = AV_CODEC_ID_SUBVIEWER();
    public static final int AV_CODEC_ID_SUBVIEWER1 = AV_CODEC_ID_SUBVIEWER1();
    public static final int AV_CODEC_ID_SUNRAST = 111;
    public static final int AV_CODEC_ID_SVQ1 = 23;
    public static final int AV_CODEC_ID_SVQ3 = 24;
    public static final int AV_CODEC_ID_TAK = AV_CODEC_ID_TAK();
    public static final int AV_CODEC_ID_TAK_DEPRECATED = 86079;
    public static final int AV_CODEC_ID_TARGA = 94;
    public static final int AV_CODEC_ID_TARGA_Y216 = AV_CODEC_ID_TARGA_Y216();
    public static final int AV_CODEC_ID_TDSC = 187;
    public static final int AV_CODEC_ID_TEXT = 94210;
    public static final int AV_CODEC_ID_TGQ = 122;
    public static final int AV_CODEC_ID_TGV = 121;
    public static final int AV_CODEC_ID_THEORA = 31;
    public static final int AV_CODEC_ID_THP = 101;
    public static final int AV_CODEC_ID_TIERTEXSEQVIDEO = 96;
    public static final int AV_CODEC_ID_TIFF = 97;
    public static final int AV_CODEC_ID_TIMED_ID3 = AV_CODEC_ID_TIMED_ID3();
    public static final int AV_CODEC_ID_TMV = 127;
    public static final int AV_CODEC_ID_TQI = 123;
    public static final int AV_CODEC_ID_TRUEHD = 86061;
    public static final int AV_CODEC_ID_TRUEMOTION1 = 52;
    public static final int AV_CODEC_ID_TRUEMOTION2 = 78;
    public static final int AV_CODEC_ID_TRUESPEECH = 86037;
    public static final int AV_CODEC_ID_TSCC = 57;
    public static final int AV_CODEC_ID_TSCC2 = 165;
    public static final int AV_CODEC_ID_TTA = 86038;
    public static final int AV_CODEC_ID_TTF = 98304;
    public static final int AV_CODEC_ID_TWINVQ = 86060;
    public static final int AV_CODEC_ID_TXD = 106;
    public static final int AV_CODEC_ID_ULTI = 58;
    public static final int AV_CODEC_ID_UTVIDEO = 154;
    public static final int AV_CODEC_ID_V210 = 128;
    public static final int AV_CODEC_ID_V210X = 126;
    public static final int AV_CODEC_ID_V308 = AV_CODEC_ID_V308();
    public static final int AV_CODEC_ID_V408 = AV_CODEC_ID_V408();
    public static final int AV_CODEC_ID_V410 = 158;
    public static final int AV_CODEC_ID_VB = 109;
    public static final int AV_CODEC_ID_VBLE = 156;
    public static final int AV_CODEC_ID_VC1 = 71;
    public static final int AV_CODEC_ID_VC1IMAGE = 153;
    public static final int AV_CODEC_ID_VCR1 = 36;
    public static final int AV_CODEC_ID_VIMA = AV_CODEC_ID_VIMA();
    public static final int AV_CODEC_ID_VIXL = 60;
    public static final int AV_CODEC_ID_VMDAUDIO = 86027;
    public static final int AV_CODEC_ID_VMDVIDEO = 53;
    public static final int AV_CODEC_ID_VMNC = 90;
    public static final int AV_CODEC_ID_VORBIS = 86021;
    public static final int AV_CODEC_ID_VOXWARE = 86048;
    public static final int AV_CODEC_ID_VP3 = 30;
    public static final int AV_CODEC_ID_VP5 = 91;
    public static final int AV_CODEC_ID_VP6 = 92;
    public static final int AV_CODEC_ID_VP6A = 107;
    public static final int AV_CODEC_ID_VP6F = 93;
    public static final int AV_CODEC_ID_VP7 = AV_CODEC_ID_VP7();
    public static final int AV_CODEC_ID_VP7_DEPRECATED = 181;
    public static final int AV_CODEC_ID_VP8 = 141;
    public static final int AV_CODEC_ID_VP9 = 169;
    public static final int AV_CODEC_ID_VPLAYER = AV_CODEC_ID_VPLAYER();
    public static final int AV_CODEC_ID_WAVPACK = 86041;
    public static final int AV_CODEC_ID_WEBP = AV_CODEC_ID_WEBP();
    public static final int AV_CODEC_ID_WEBP_DEPRECATED = 173;
    public static final int AV_CODEC_ID_WEBVTT = AV_CODEC_ID_WEBVTT();
    public static final int AV_CODEC_ID_WESTWOOD_SND1 = 86033;
    public static final int AV_CODEC_ID_WMALOSSLESS = 86055;
    public static final int AV_CODEC_ID_WMAPRO = 86054;
    public static final int AV_CODEC_ID_WMAV1 = 86023;
    public static final int AV_CODEC_ID_WMAV2 = 86024;
    public static final int AV_CODEC_ID_WMAVOICE = 86053;
    public static final int AV_CODEC_ID_WMV1 = 18;
    public static final int AV_CODEC_ID_WMV2 = 19;
    public static final int AV_CODEC_ID_WMV3 = 72;
    public static final int AV_CODEC_ID_WMV3IMAGE = 152;
    public static final int AV_CODEC_ID_WNV1 = 74;
    public static final int AV_CODEC_ID_WS_VQA = 45;
    public static final int AV_CODEC_ID_XAN_DPCM = 81922;
    public static final int AV_CODEC_ID_XAN_WC3 = 41;
    public static final int AV_CODEC_ID_XAN_WC4 = 42;
    public static final int AV_CODEC_ID_XBIN = AV_CODEC_ID_XBIN();
    public static final int AV_CODEC_ID_XBM = 161;
    public static final int AV_CODEC_ID_XFACE = AV_CODEC_ID_XFACE();
    public static final int AV_CODEC_ID_XSUB = 94211;
    public static final int AV_CODEC_ID_XWD = 159;
    public static final int AV_CODEC_ID_Y41P = AV_CODEC_ID_Y41P();
    public static final int AV_CODEC_ID_YOP = 140;
    public static final int AV_CODEC_ID_YUV4 = AV_CODEC_ID_YUV4();
    public static final int AV_CODEC_ID_ZEROCODEC = 162;
    public static final int AV_CODEC_ID_ZLIB = 55;
    public static final int AV_CODEC_ID_ZMBV = 82;
    public static final int AV_CODEC_PROP_BITMAP_SUB = 65536;
    public static final int AV_CODEC_PROP_INTRA_ONLY = 1;
    public static final int AV_CODEC_PROP_LOSSLESS = 4;
    public static final int AV_CODEC_PROP_LOSSY = 2;
    public static final int AV_CODEC_PROP_REORDER = 8;
    public static final int AV_CODEC_PROP_TEXT_SUB = 131072;
    public static final int AV_FIELD_BB = 3;
    public static final int AV_FIELD_BT = 5;
    public static final int AV_FIELD_PROGRESSIVE = 1;
    public static final int AV_FIELD_TB = 4;
    public static final int AV_FIELD_TT = 2;
    public static final int AV_FIELD_UNKNOWN = 0;
    public static final int AV_GET_BUFFER_FLAG_REF = 1;
    public static final int AV_HWACCEL_FLAG_ALLOW_HIGH_DEPTH = 2;
    public static final int AV_HWACCEL_FLAG_IGNORE_LEVEL = 1;
    public static final int AV_LOCK_CREATE = 0;
    public static final int AV_LOCK_DESTROY = 3;
    public static final int AV_LOCK_OBTAIN = 1;
    public static final int AV_LOCK_RELEASE = 2;
    public static final int AV_PICTURE_STRUCTURE_BOTTOM_FIELD = 2;
    public static final int AV_PICTURE_STRUCTURE_FRAME = 3;
    public static final int AV_PICTURE_STRUCTURE_TOP_FIELD = 1;
    public static final int AV_PICTURE_STRUCTURE_UNKNOWN = 0;
    public static final int AV_PKT_DATA_AUDIO_SERVICE_TYPE = 7;
    public static final int AV_PKT_DATA_DISPLAYMATRIX = 5;
    public static final int AV_PKT_DATA_H263_MB_INFO = 3;
    public static final int AV_PKT_DATA_JP_DUALMONO = 71;
    public static final int AV_PKT_DATA_MATROSKA_BLOCKADDITIONAL = 74;
    public static final int AV_PKT_DATA_METADATA_UPDATE = 77;
    public static final int AV_PKT_DATA_NEW_EXTRADATA = 1;
    public static final int AV_PKT_DATA_PALETTE = 0;
    public static final int AV_PKT_DATA_PARAM_CHANGE = 2;
    public static final int AV_PKT_DATA_REPLAYGAIN = 4;
    public static final int AV_PKT_DATA_SKIP_SAMPLES = 70;
    public static final int AV_PKT_DATA_STEREO3D = 6;
    public static final int AV_PKT_DATA_STRINGS_METADATA = 72;
    public static final int AV_PKT_DATA_SUBTITLE_POSITION = 73;
    public static final int AV_PKT_DATA_WEBVTT_IDENTIFIER = 75;
    public static final int AV_PKT_DATA_WEBVTT_SETTINGS = 76;
    public static final int AV_PKT_FLAG_CORRUPT = 2;
    public static final int AV_PKT_FLAG_KEY = 1;
    public static final int AV_SIDE_DATA_PARAM_CHANGE_CHANNEL_COUNT = 1;
    public static final int AV_SIDE_DATA_PARAM_CHANGE_CHANNEL_LAYOUT = 2;
    public static final int AV_SIDE_DATA_PARAM_CHANGE_DIMENSIONS = 8;
    public static final int AV_SIDE_DATA_PARAM_CHANGE_SAMPLE_RATE = 4;
    public static final int AV_SUBTITLE_FLAG_FORCED = 1;
    public static final int CODEC_CAP_AUTO_THREADS = 32768;
    public static final int CODEC_CAP_CHANNEL_CONF = 1024;
    public static final int CODEC_CAP_DELAY = 32;
    public static final int CODEC_CAP_DR1 = 2;
    public static final int CODEC_CAP_DRAW_HORIZ_BAND = 1;
    public static final int CODEC_CAP_EXPERIMENTAL = 512;
    public static final int CODEC_CAP_FRAME_THREADS = 4096;
    public static final int CODEC_CAP_HWACCEL = 16;
    public static final int CODEC_CAP_HWACCEL_VDPAU = 128;
    public static final int CODEC_CAP_INTRA_ONLY = 1073741824;
    public static final int CODEC_CAP_LOSSLESS = Integer.MIN_VALUE;
    public static final int CODEC_CAP_NEG_LINESIZES = 2048;
    public static final int CODEC_CAP_PARAM_CHANGE = 16384;
    public static final int CODEC_CAP_SLICE_THREADS = 8192;
    public static final int CODEC_CAP_SMALL_LAST_FRAME = 64;
    public static final int CODEC_CAP_SUBFRAMES = 256;
    public static final int CODEC_CAP_TRUNCATED = 8;
    public static final int CODEC_CAP_VARIABLE_FRAME_SIZE = 65536;
    public static final int CODEC_FLAG2_CHUNKS = 32768;
    public static final int CODEC_FLAG2_DROP_FRAME_TIMECODE = 8192;
    public static final int CODEC_FLAG2_EXPORT_MVS = 268435456;
    public static final int CODEC_FLAG2_FAST = 1;
    public static final int CODEC_FLAG2_IGNORE_CROP = 65536;
    public static final int CODEC_FLAG2_LOCAL_HEADER = 8;
    public static final int CODEC_FLAG2_NO_OUTPUT = 4;
    public static final int CODEC_FLAG2_SHOW_ALL = 4194304;
    public static final int CODEC_FLAG2_SKIP_MANUAL = 536870912;
    public static final int CODEC_FLAG_4MV = 4;
    public static final int CODEC_FLAG_AC_PRED = 16777216;
    public static final int CODEC_FLAG_BITEXACT = 8388608;
    public static final int CODEC_FLAG_CLOSED_GOP = Integer.MIN_VALUE;
    public static final int CODEC_FLAG_EMU_EDGE = 16384;
    public static final int CODEC_FLAG_GLOBAL_HEADER = 4194304;
    public static final int CODEC_FLAG_GMC = 32;
    public static final int CODEC_FLAG_GRAY = 8192;
    public static final int CODEC_FLAG_INPUT_PRESERVED = 256;
    public static final int CODEC_FLAG_INTERLACED_DCT = 262144;
    public static final int CODEC_FLAG_INTERLACED_ME = 536870912;
    public static final int CODEC_FLAG_LOOP_FILTER = 2048;
    public static final int CODEC_FLAG_LOW_DELAY = 524288;
    public static final int CODEC_FLAG_MV0 = 64;
    public static final int CODEC_FLAG_NORMALIZE_AQP = 131072;
    public static final int CODEC_FLAG_OUTPUT_CORRUPT = 8;
    public static final int CODEC_FLAG_PASS1 = 512;
    public static final int CODEC_FLAG_PASS2 = 1024;
    public static final int CODEC_FLAG_PSNR = 32768;
    public static final int CODEC_FLAG_QPEL = 16;
    public static final int CODEC_FLAG_QSCALE = 2;
    public static final int CODEC_FLAG_TRUNCATED = 65536;
    public static final int CODEC_FLAG_UNALIGNED = 1;
    public static final int DCT_I = 2;
    public static final int DCT_II = 0;
    public static final int DCT_III = 1;
    public static final int DFT_C2R = 3;
    public static final int DFT_R2C = 0;
    public static final int DST_I = 3;
    public static final int FF_BUFFER_HINTS_PRESERVE = 4;
    public static final int FF_BUFFER_HINTS_READABLE = 2;
    public static final int FF_BUFFER_HINTS_REUSABLE = 8;
    public static final int FF_BUFFER_HINTS_VALID = 1;
    public static final int FF_BUFFER_TYPE_COPY = 8;
    public static final int FF_BUFFER_TYPE_INTERNAL = 1;
    public static final int FF_BUFFER_TYPE_SHARED = 4;
    public static final int FF_BUFFER_TYPE_USER = 2;
    public static final int FF_INPUT_BUFFER_PADDING_SIZE = 32;
    public static final int FF_MAX_B_FRAMES = 16;
    public static final int FF_MIN_BUFFER_SIZE = 16384;
    public static final int FF_QSCALE_TYPE_H264 = 2;
    public static final int FF_QSCALE_TYPE_MPEG1 = 0;
    public static final int FF_QSCALE_TYPE_MPEG2 = 1;
    public static final int FF_QSCALE_TYPE_VP56 = 3;
    public static final int IDFT_C2R = 1;
    public static final int IDFT_R2C = 2;
    public static final int MB_TYPE_16x16 = 8;
    public static final int MB_TYPE_16x8 = 16;
    public static final int MB_TYPE_8x16 = 32;
    public static final int MB_TYPE_8x8 = 64;
    public static final int MB_TYPE_ACPRED = 512;
    public static final int MB_TYPE_CBP = 131072;
    public static final int MB_TYPE_DIRECT2 = 256;
    public static final int MB_TYPE_GMC = 1024;
    public static final int MB_TYPE_INTERLACED = 128;
    public static final int MB_TYPE_INTRA16x16 = 2;
    public static final int MB_TYPE_INTRA4x4 = 1;
    public static final int MB_TYPE_INTRA_PCM = 4;
    public static final int MB_TYPE_L0 = 12288;
    public static final int MB_TYPE_L0L1 = 61440;
    public static final int MB_TYPE_L1 = 49152;
    public static final int MB_TYPE_P0L0 = 4096;
    public static final int MB_TYPE_P0L1 = 16384;
    public static final int MB_TYPE_P1L0 = 8192;
    public static final int MB_TYPE_P1L1 = 32768;
    public static final int MB_TYPE_QUANT = 65536;
    public static final int MB_TYPE_SKIP = 2048;
    public static final int ME_EPZS = 5;
    public static final int ME_FULL = 2;
    public static final int ME_HEX = 7;
    public static final int ME_ITER = 50;
    public static final int ME_LOG = 3;
    public static final int ME_PHODS = 4;
    public static final int ME_TESA = 9;
    public static final int ME_UMH = 8;
    public static final int ME_X1 = 6;
    public static final int ME_ZERO = 1;
    public static final int SUBTITLE_ASS = 3;
    public static final int SUBTITLE_BITMAP = 1;
    public static final int SUBTITLE_NONE = 0;
    public static final int SUBTITLE_TEXT = 2;

    public static class AVBitStreamFilter extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native Close_AVBitStreamFilterContext close();

        public native AVBitStreamFilter close(Close_AVBitStreamFilterContext close_AVBitStreamFilterContext);

        public native Filter_AVBitStreamFilterContext_AVCodecContext_BytePointer_PointerPointer_IntPointer_BytePointer_int_int filter();

        public native AVBitStreamFilter filter(Filter_AVBitStreamFilterContext_AVCodecContext_BytePointer_PointerPointer_IntPointer_BytePointer_int_int filter_AVBitStreamFilterContext_AVCodecContext_BytePointer_PointerPointer_IntPointer_BytePointer_int_int);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        public native AVBitStreamFilter next();

        public native AVBitStreamFilter next(AVBitStreamFilter aVBitStreamFilter);

        public native int priv_data_size();

        public native AVBitStreamFilter priv_data_size(int i);

        static {
            Loader.load();
        }

        public AVBitStreamFilter() {
            allocate();
        }

        public AVBitStreamFilter(int size) {
            allocateArray(size);
        }

        public AVBitStreamFilter(Pointer p) {
            super(p);
        }

        public AVBitStreamFilter position(int position) {
            return (AVBitStreamFilter) super.position(position);
        }
    }

    public static class AVCodec extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native int capabilities();

        public native AVCodec capabilities(int i);

        public native int caps_internal();

        public native AVCodec caps_internal(int i);

        @MemberGetter
        @Cast({"const uint64_t*"})
        public native LongPointer channel_layouts();

        public native Close_AVCodecContext close();

        public native AVCodec close(Close_AVCodecContext close_AVCodecContext);

        public native Decode_AVCodecContext_Pointer_IntPointer_AVPacket decode();

        public native AVCodec decode(Decode_AVCodecContext_Pointer_IntPointer_AVPacket decode_AVCodecContext_Pointer_IntPointer_AVPacket);

        @MemberGetter
        @Const
        public native AVCodecDefault defaults();

        public native Encode2_AVCodecContext_AVPacket_AVFrame_IntPointer encode2();

        public native AVCodec encode2(Encode2_AVCodecContext_AVPacket_AVFrame_IntPointer encode2_AVCodecContext_AVPacket_AVFrame_IntPointer);

        public native Encode_sub_AVCodecContext_BytePointer_int_AVSubtitle encode_sub();

        public native AVCodec encode_sub(Encode_sub_AVCodecContext_BytePointer_int_AVSubtitle encode_sub_AVCodecContext_BytePointer_int_AVSubtitle);

        public native Flush_AVCodecContext flush();

        public native AVCodec flush(Flush_AVCodecContext flush_AVCodecContext);

        @Cast({"AVCodecID"})
        public native int id();

        public native AVCodec id(int i);

        public native Init_AVCodecContext init();

        public native AVCodec init(Init_AVCodecContext init_AVCodecContext);

        public native Init_static_data_AVCodec init_static_data();

        public native AVCodec init_static_data(Init_static_data_AVCodec init_static_data_AVCodec);

        public native Init_thread_copy_AVCodecContext init_thread_copy();

        public native AVCodec init_thread_copy(Init_thread_copy_AVCodecContext init_thread_copy_AVCodecContext);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer long_name();

        @Cast({"uint8_t"})
        public native byte max_lowres();

        public native AVCodec max_lowres(byte b);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        public native AVCodec next();

        public native AVCodec next(AVCodec aVCodec);

        @MemberGetter
        @Cast({"const AVPixelFormat*"})
        public native IntPointer pix_fmts();

        @MemberGetter
        @Const
        public native AVClass priv_class();

        public native int priv_data_size();

        public native AVCodec priv_data_size(int i);

        @MemberGetter
        @Const
        public native AVProfile profiles();

        @MemberGetter
        @Cast({"const AVSampleFormat*"})
        public native IntPointer sample_fmts();

        @MemberGetter
        @Const
        public native AVRational supported_framerates();

        @MemberGetter
        @Const
        public native IntPointer supported_samplerates();

        @Cast({"AVMediaType"})
        public native int type();

        public native AVCodec type(int i);

        public native Update_thread_context_AVCodecContext_AVCodecContext update_thread_context();

        public native AVCodec update_thread_context(Update_thread_context_AVCodecContext_AVCodecContext update_thread_context_AVCodecContext_AVCodecContext);

        static {
            Loader.load();
        }

        public AVCodec() {
            allocate();
        }

        public AVCodec(int size) {
            allocateArray(size);
        }

        public AVCodec(Pointer p) {
            super(p);
        }

        public AVCodec position(int position) {
            return (AVCodec) super.position(position);
        }
    }

    public static class AVCodecContext extends Pointer {
        public static final int AV_EF_AGGRESSIVE = 262144;
        public static final int AV_EF_BITSTREAM = 2;
        public static final int AV_EF_BUFFER = 4;
        public static final int AV_EF_CAREFUL = 65536;
        public static final int AV_EF_COMPLIANT = 131072;
        public static final int AV_EF_CRCCHECK = 1;
        public static final int AV_EF_EXPLODE = 8;
        public static final int AV_EF_IGNORE_ERR = 32768;
        public static final int FF_ASPECT_EXTENDED = 15;
        public static final int FF_BUG_AC_VLC = 0;
        public static final int FF_BUG_AMV = 32;
        public static final int FF_BUG_AUTODETECT = 1;
        public static final int FF_BUG_DC_CLIP = 4096;
        public static final int FF_BUG_DIRECT_BLOCKSIZE = 512;
        public static final int FF_BUG_EDGE = 1024;
        public static final int FF_BUG_HPEL_CHROMA = 2048;
        public static final int FF_BUG_MS = 8192;
        public static final int FF_BUG_NO_PADDING = 16;
        public static final int FF_BUG_OLD_MSMPEG4 = 2;
        public static final int FF_BUG_QPEL_CHROMA = 64;
        public static final int FF_BUG_QPEL_CHROMA2 = 256;
        public static final int FF_BUG_STD_QPEL = 128;
        public static final int FF_BUG_TRUNCATED = 16384;
        public static final int FF_BUG_UMP4 = 8;
        public static final int FF_BUG_XVID_ILACE = 4;
        public static final int FF_CMP_BIT = 5;
        public static final int FF_CMP_CHROMA = 256;
        public static final int FF_CMP_DCT = 3;
        public static final int FF_CMP_DCT264 = 14;
        public static final int FF_CMP_DCTMAX = 13;
        public static final int FF_CMP_NSSE = 10;
        public static final int FF_CMP_PSNR = 4;
        public static final int FF_CMP_RD = 6;
        public static final int FF_CMP_SAD = 0;
        public static final int FF_CMP_SATD = 2;
        public static final int FF_CMP_SSE = 1;
        public static final int FF_CMP_VSAD = 8;
        public static final int FF_CMP_VSSE = 9;
        public static final int FF_CMP_W53 = 11;
        public static final int FF_CMP_W97 = 12;
        public static final int FF_CMP_ZERO = 7;
        public static final int FF_CODER_TYPE_AC = 1;
        public static final int FF_CODER_TYPE_DEFLATE = 4;
        public static final int FF_CODER_TYPE_RAW = 2;
        public static final int FF_CODER_TYPE_RLE = 3;
        public static final int FF_CODER_TYPE_VLC = 0;
        public static final int FF_COMPLIANCE_EXPERIMENTAL = -2;
        public static final int FF_COMPLIANCE_NORMAL = 0;
        public static final int FF_COMPLIANCE_STRICT = 1;
        public static final int FF_COMPLIANCE_UNOFFICIAL = -1;
        public static final int FF_COMPLIANCE_VERY_STRICT = 2;
        public static final int FF_COMPRESSION_DEFAULT = -1;
        public static final int FF_DCT_ALTIVEC = 5;
        public static final int FF_DCT_AUTO = 0;
        public static final int FF_DCT_FAAN = 6;
        public static final int FF_DCT_FASTINT = 1;
        public static final int FF_DCT_INT = 2;
        public static final int FF_DCT_MMX = 3;
        public static final int FF_DEBUG_BITSTREAM = 4;
        public static final int FF_DEBUG_BUFFERS = 32768;
        public static final int FF_DEBUG_BUGS = 4096;
        public static final int FF_DEBUG_DCT_COEFF = 64;
        public static final int FF_DEBUG_ER = 1024;
        public static final int FF_DEBUG_MB_TYPE = 8;
        public static final int FF_DEBUG_MMCO = 2048;
        public static final int FF_DEBUG_MV = 32;
        public static final int FF_DEBUG_NOMC = 16777216;
        public static final int FF_DEBUG_PICT_INFO = 1;
        public static final int FF_DEBUG_PTS = 512;
        public static final int FF_DEBUG_QP = 16;
        public static final int FF_DEBUG_RC = 2;
        public static final int FF_DEBUG_SKIP = 128;
        public static final int FF_DEBUG_STARTCODE = 256;
        public static final int FF_DEBUG_THREADS = 65536;
        public static final int FF_DEBUG_VIS_MB_TYPE = 16384;
        public static final int FF_DEBUG_VIS_MV_B_BACK = 4;
        public static final int FF_DEBUG_VIS_MV_B_FOR = 2;
        public static final int FF_DEBUG_VIS_MV_P_FOR = 1;
        public static final int FF_DEBUG_VIS_QP = 8192;
        public static final int FF_DEFAULT_QUANT_BIAS = 999999;
        public static final int FF_DTG_AFD_14_9 = 11;
        public static final int FF_DTG_AFD_16_9 = 10;
        public static final int FF_DTG_AFD_16_9_SP_14_9 = 14;
        public static final int FF_DTG_AFD_4_3 = 9;
        public static final int FF_DTG_AFD_4_3_SP_14_9 = 13;
        public static final int FF_DTG_AFD_SAME = 8;
        public static final int FF_DTG_AFD_SP_4_3 = 15;
        public static final int FF_EC_DEBLOCK = 2;
        public static final int FF_EC_FAVOR_INTER = 256;
        public static final int FF_EC_GUESS_MVS = 1;
        public static final int FF_IDCT_ALTIVEC = 8;
        public static final int FF_IDCT_ARM = 7;
        public static final int FF_IDCT_AUTO = 0;
        public static final int FF_IDCT_FAAN = 20;
        public static final int FF_IDCT_INT = 1;
        public static final int FF_IDCT_IPP = 13;
        public static final int FF_IDCT_SH4 = 9;
        public static final int FF_IDCT_SIMPLE = 2;
        public static final int FF_IDCT_SIMPLEALPHA = 23;
        public static final int FF_IDCT_SIMPLEARM = 10;
        public static final int FF_IDCT_SIMPLEARMV5TE = 16;
        public static final int FF_IDCT_SIMPLEARMV6 = 17;
        public static final int FF_IDCT_SIMPLEAUTO = 128;
        public static final int FF_IDCT_SIMPLEMMX = 3;
        public static final int FF_IDCT_SIMPLENEON = 22;
        public static final int FF_IDCT_SIMPLEVIS = 18;
        public static final int FF_IDCT_XVID = 14;
        public static final int FF_IDCT_XVIDMMX = 14;
        public static final int FF_LEVEL_UNKNOWN = -99;
        public static final int FF_MB_DECISION_BITS = 1;
        public static final int FF_MB_DECISION_RD = 2;
        public static final int FF_MB_DECISION_SIMPLE = 0;
        public static final int FF_PRED_LEFT = 0;
        public static final int FF_PRED_MEDIAN = 2;
        public static final int FF_PRED_PLANE = 1;
        public static final int FF_PROFILE_AAC_ELD = 38;
        public static final int FF_PROFILE_AAC_HE = 4;
        public static final int FF_PROFILE_AAC_HE_V2 = 28;
        public static final int FF_PROFILE_AAC_LD = 22;
        public static final int FF_PROFILE_AAC_LOW = 1;
        public static final int FF_PROFILE_AAC_LTP = 3;
        public static final int FF_PROFILE_AAC_MAIN = 0;
        public static final int FF_PROFILE_AAC_SSR = 2;
        public static final int FF_PROFILE_DTS = 20;
        public static final int FF_PROFILE_DTS_96_24 = 40;
        public static final int FF_PROFILE_DTS_ES = 30;
        public static final int FF_PROFILE_DTS_EXPRESS = 70;
        public static final int FF_PROFILE_DTS_HD_HRA = 50;
        public static final int FF_PROFILE_DTS_HD_MA = 60;
        public static final int FF_PROFILE_H264_BASELINE = 66;
        public static final int FF_PROFILE_H264_CAVLC_444 = 44;
        public static final int FF_PROFILE_H264_CONSTRAINED = 512;
        public static final int FF_PROFILE_H264_CONSTRAINED_BASELINE = 578;
        public static final int FF_PROFILE_H264_EXTENDED = 88;
        public static final int FF_PROFILE_H264_HIGH = 100;
        public static final int FF_PROFILE_H264_HIGH_10 = 110;
        public static final int FF_PROFILE_H264_HIGH_10_INTRA = 2158;
        public static final int FF_PROFILE_H264_HIGH_422 = 122;
        public static final int FF_PROFILE_H264_HIGH_422_INTRA = 2170;
        public static final int FF_PROFILE_H264_HIGH_444 = 144;
        public static final int FF_PROFILE_H264_HIGH_444_INTRA = 2292;
        public static final int FF_PROFILE_H264_HIGH_444_PREDICTIVE = 244;
        public static final int FF_PROFILE_H264_INTRA = 2048;
        public static final int FF_PROFILE_H264_MAIN = 77;
        public static final int FF_PROFILE_HEVC_MAIN = 1;
        public static final int FF_PROFILE_HEVC_MAIN_10 = 2;
        public static final int FF_PROFILE_HEVC_MAIN_STILL_PICTURE = 3;
        public static final int FF_PROFILE_HEVC_REXT = 4;
        public static final int FF_PROFILE_JPEG2000_CSTREAM_NO_RESTRICTION = 2;
        public static final int FF_PROFILE_JPEG2000_CSTREAM_RESTRICTION_0 = 0;
        public static final int FF_PROFILE_JPEG2000_CSTREAM_RESTRICTION_1 = 1;
        public static final int FF_PROFILE_JPEG2000_DCINEMA_2K = 3;
        public static final int FF_PROFILE_JPEG2000_DCINEMA_4K = 4;
        public static final int FF_PROFILE_MPEG2_422 = 0;
        public static final int FF_PROFILE_MPEG2_AAC_HE = 131;
        public static final int FF_PROFILE_MPEG2_AAC_LOW = 128;
        public static final int FF_PROFILE_MPEG2_HIGH = 1;
        public static final int FF_PROFILE_MPEG2_MAIN = 4;
        public static final int FF_PROFILE_MPEG2_SIMPLE = 5;
        public static final int FF_PROFILE_MPEG2_SNR_SCALABLE = 3;
        public static final int FF_PROFILE_MPEG2_SS = 2;
        public static final int FF_PROFILE_MPEG4_ADVANCED_CODING = 11;
        public static final int FF_PROFILE_MPEG4_ADVANCED_CORE = 12;
        public static final int FF_PROFILE_MPEG4_ADVANCED_REAL_TIME = 9;
        public static final int FF_PROFILE_MPEG4_ADVANCED_SCALABLE_TEXTURE = 13;
        public static final int FF_PROFILE_MPEG4_ADVANCED_SIMPLE = 15;
        public static final int FF_PROFILE_MPEG4_BASIC_ANIMATED_TEXTURE = 7;
        public static final int FF_PROFILE_MPEG4_CORE = 2;
        public static final int FF_PROFILE_MPEG4_CORE_SCALABLE = 10;
        public static final int FF_PROFILE_MPEG4_HYBRID = 8;
        public static final int FF_PROFILE_MPEG4_MAIN = 3;
        public static final int FF_PROFILE_MPEG4_N_BIT = 4;
        public static final int FF_PROFILE_MPEG4_SCALABLE_TEXTURE = 5;
        public static final int FF_PROFILE_MPEG4_SIMPLE = 0;
        public static final int FF_PROFILE_MPEG4_SIMPLE_FACE_ANIMATION = 6;
        public static final int FF_PROFILE_MPEG4_SIMPLE_SCALABLE = 1;
        public static final int FF_PROFILE_MPEG4_SIMPLE_STUDIO = 14;
        public static final int FF_PROFILE_RESERVED = -100;
        public static final int FF_PROFILE_UNKNOWN = -99;
        public static final int FF_PROFILE_VC1_ADVANCED = 3;
        public static final int FF_PROFILE_VC1_COMPLEX = 2;
        public static final int FF_PROFILE_VC1_MAIN = 1;
        public static final int FF_PROFILE_VC1_SIMPLE = 0;
        public static final int FF_PROFILE_VP9_0 = 0;
        public static final int FF_PROFILE_VP9_1 = 1;
        public static final int FF_PROFILE_VP9_2 = 2;
        public static final int FF_PROFILE_VP9_3 = 3;
        public static final int FF_RC_STRATEGY_XVID = 1;
        public static final int FF_SUB_CHARENC_MODE_AUTOMATIC = 0;
        public static final int FF_SUB_CHARENC_MODE_DO_NOTHING = -1;
        public static final int FF_SUB_CHARENC_MODE_PRE_DECODER = 1;
        public static final int FF_THREAD_FRAME = 1;
        public static final int FF_THREAD_SLICE = 2;
        public static final int SLICE_FLAG_ALLOW_FIELD = 2;
        public static final int SLICE_FLAG_ALLOW_PLANE = 4;
        public static final int SLICE_FLAG_CODED_ORDER = 1;

        public static class Get_buffer_AVCodecContext_AVFrame extends FunctionPointer {
            private native void allocate();

            @Deprecated
            public native int call(AVCodecContext aVCodecContext, AVFrame aVFrame);

            static {
                Loader.load();
            }

            public Get_buffer_AVCodecContext_AVFrame(Pointer p) {
                super(p);
            }

            protected Get_buffer_AVCodecContext_AVFrame() {
                allocate();
            }
        }

        public static class Get_format_AVCodecContext_IntPointer extends FunctionPointer {
            private native void allocate();

            @Cast({"AVPixelFormat"})
            public native int call(AVCodecContext aVCodecContext, @Cast({"const AVPixelFormat*"}) IntPointer intPointer);

            static {
                Loader.load();
            }

            public Get_format_AVCodecContext_IntPointer(Pointer p) {
                super(p);
            }

            protected Get_format_AVCodecContext_IntPointer() {
                allocate();
            }
        }

        public static class Reget_buffer_AVCodecContext_AVFrame extends FunctionPointer {
            private native void allocate();

            @Deprecated
            public native int call(AVCodecContext aVCodecContext, AVFrame aVFrame);

            static {
                Loader.load();
            }

            public Reget_buffer_AVCodecContext_AVFrame(Pointer p) {
                super(p);
            }

            protected Reget_buffer_AVCodecContext_AVFrame() {
                allocate();
            }
        }

        public static class Release_buffer_AVCodecContext_AVFrame extends FunctionPointer {
            private native void allocate();

            @Deprecated
            public native void call(AVCodecContext aVCodecContext, AVFrame aVFrame);

            static {
                Loader.load();
            }

            public Release_buffer_AVCodecContext_AVFrame(Pointer p) {
                super(p);
            }

            protected Release_buffer_AVCodecContext_AVFrame() {
                allocate();
            }
        }

        private native void allocate();

        private native void allocateArray(int i);

        public native int active_thread_type();

        public native AVCodecContext active_thread_type(int i);

        @Cast({"AVAudioServiceType"})
        public native int audio_service_type();

        public native AVCodecContext audio_service_type(int i);

        @MemberGetter
        @Const
        public native AVClass av_class();

        public native int b_frame_strategy();

        public native AVCodecContext b_frame_strategy(int i);

        public native float b_quant_factor();

        public native AVCodecContext b_quant_factor(float f);

        public native float b_quant_offset();

        public native AVCodecContext b_quant_offset(float f);

        public native int b_sensitivity();

        public native AVCodecContext b_sensitivity(int i);

        public native int bidir_refine();

        public native AVCodecContext bidir_refine(int i);

        public native int bit_rate();

        public native AVCodecContext bit_rate(int i);

        public native int bit_rate_tolerance();

        public native AVCodecContext bit_rate_tolerance(int i);

        public native int bits_per_coded_sample();

        public native AVCodecContext bits_per_coded_sample(int i);

        public native int bits_per_raw_sample();

        public native AVCodecContext bits_per_raw_sample(int i);

        public native int block_align();

        public native AVCodecContext block_align(int i);

        @Deprecated
        public native float border_masking();

        public native AVCodecContext border_masking(float f);

        public native int brd_scale();

        public native AVCodecContext brd_scale(int i);

        @Cast({"uint64_t"})
        public native long channel_layout();

        public native AVCodecContext channel_layout(long j);

        public native int channels();

        public native AVCodecContext channels(int i);

        @Cast({"uint16_t*"})
        public native ShortPointer chroma_intra_matrix();

        public native AVCodecContext chroma_intra_matrix(ShortPointer shortPointer);

        @Cast({"AVChromaLocation"})
        public native int chroma_sample_location();

        public native AVCodecContext chroma_sample_location(int i);

        public native int chromaoffset();

        public native AVCodecContext chromaoffset(int i);

        @MemberGetter
        @Const
        public native AVCodec codec();

        @MemberGetter
        @Const
        public native AVCodecDescriptor codec_descriptor();

        @Cast({"AVCodecID"})
        public native int codec_id();

        public native AVCodecContext codec_id(int i);

        @Deprecated
        @Cast({"char"})
        public native byte codec_name(int i);

        @MemberGetter
        @Deprecated
        @Cast({"char*"})
        public native BytePointer codec_name();

        public native AVCodecContext codec_name(int i, byte b);

        @Cast({"unsigned int"})
        public native int codec_tag();

        public native AVCodecContext codec_tag(int i);

        @Cast({"AVMediaType"})
        public native int codec_type();

        public native AVCodecContext codec_type(int i);

        @Cast({"char*"})
        public native BytePointer codec_whitelist();

        public native AVCodecContext codec_whitelist(BytePointer bytePointer);

        public native AVCodecContext coded_frame(AVFrame aVFrame);

        public native AVFrame coded_frame();

        public native int coded_height();

        public native AVCodecContext coded_height(int i);

        public native int coded_width();

        public native AVCodecContext coded_width(int i);

        public native int coder_type();

        public native AVCodecContext coder_type(int i);

        @Cast({"AVColorPrimaries"})
        public native int color_primaries();

        public native AVCodecContext color_primaries(int i);

        @Cast({"AVColorRange"})
        public native int color_range();

        public native AVCodecContext color_range(int i);

        @Cast({"AVColorTransferCharacteristic"})
        public native int color_trc();

        public native AVCodecContext color_trc(int i);

        @Cast({"AVColorSpace"})
        public native int colorspace();

        public native AVCodecContext colorspace(int i);

        public native int compression_level();

        public native AVCodecContext compression_level(int i);

        public native int context_model();

        public native AVCodecContext context_model(int i);

        public native int cutoff();

        public native AVCodecContext cutoff(int i);

        public native float dark_masking();

        public native AVCodecContext dark_masking(float f);

        public native int dct_algo();

        public native AVCodecContext dct_algo(int i);

        public native int debug();

        public native AVCodecContext debug(int i);

        public native int debug_mv();

        public native AVCodecContext debug_mv(int i);

        public native int delay();

        public native AVCodecContext delay(int i);

        public native int dia_size();

        public native AVCodecContext dia_size(int i);

        public native Draw_horiz_band_AVCodecContext_AVFrame_IntPointer_int_int_int draw_horiz_band();

        public native AVCodecContext draw_horiz_band(Draw_horiz_band_AVCodecContext_AVFrame_IntPointer_int_int_int draw_horiz_band_AVCodecContext_AVFrame_IntPointer_int_int_int);

        @Deprecated
        public native int dtg_active_format();

        public native AVCodecContext dtg_active_format(int i);

        @Cast({"uint8_t*"})
        public native BytePointer dump_separator();

        public native AVCodecContext dump_separator(BytePointer bytePointer);

        public native int err_recognition();

        public native AVCodecContext err_recognition(int i);

        @Cast({"uint64_t"})
        public native long error(int i);

        @MemberGetter
        @Cast({"uint64_t*"})
        public native LongPointer error();

        public native AVCodecContext error(int i, long j);

        public native int error_concealment();

        public native AVCodecContext error_concealment(int i);

        @Deprecated
        public native int error_rate();

        public native AVCodecContext error_rate(int i);

        public native Execute_AVCodecContext_Func_AVCodecContext_Pointer_Pointer_IntPointer_int_int execute();

        public native AVCodecContext execute(Execute_AVCodecContext_Func_AVCodecContext_Pointer_Pointer_IntPointer_int_int execute_AVCodecContext_Func_AVCodecContext_Pointer_Pointer_IntPointer_int_int);

        public native Execute2_AVCodecContext_Func_AVCodecContext_Pointer_int_int_Pointer_IntPointer_int execute2();

        public native AVCodecContext execute2(Execute2_AVCodecContext_Func_AVCodecContext_Pointer_int_int_Pointer_IntPointer_int execute2_AVCodecContext_Func_AVCodecContext_Pointer_int_int_Pointer_IntPointer_int);

        @Cast({"uint8_t*"})
        public native BytePointer extradata();

        public native AVCodecContext extradata(BytePointer bytePointer);

        public native int extradata_size();

        public native AVCodecContext extradata_size(int i);

        @Cast({"AVFieldOrder"})
        public native int field_order();

        public native AVCodecContext field_order(int i);

        public native int flags();

        public native AVCodecContext flags(int i);

        public native int flags2();

        public native AVCodecContext flags2(int i);

        public native int frame_bits();

        public native AVCodecContext frame_bits(int i);

        public native int frame_number();

        public native AVCodecContext frame_number(int i);

        public native int frame_size();

        public native AVCodecContext frame_size(int i);

        public native int frame_skip_cmp();

        public native AVCodecContext frame_skip_cmp(int i);

        public native int frame_skip_exp();

        public native AVCodecContext frame_skip_exp(int i);

        public native int frame_skip_factor();

        public native AVCodecContext frame_skip_factor(int i);

        public native int frame_skip_threshold();

        public native AVCodecContext frame_skip_threshold(int i);

        public native AVCodecContext framerate(AVRational aVRational);

        @ByRef
        public native AVRational framerate();

        public native Get_buffer_AVCodecContext_AVFrame get_buffer();

        public native AVCodecContext get_buffer(Get_buffer_AVCodecContext_AVFrame get_buffer_AVCodecContext_AVFrame);

        public native Get_buffer2_AVCodecContext_AVFrame_int get_buffer2();

        public native AVCodecContext get_buffer2(Get_buffer2_AVCodecContext_AVFrame_int get_buffer2_AVCodecContext_AVFrame_int);

        public native Get_format_AVCodecContext_IntPointer get_format();

        public native AVCodecContext get_format(Get_format_AVCodecContext_IntPointer get_format_AVCodecContext_IntPointer);

        public native int global_quality();

        public native AVCodecContext global_quality(int i);

        public native int gop_size();

        public native AVCodecContext gop_size(int i);

        public native int has_b_frames();

        public native AVCodecContext has_b_frames(int i);

        public native int header_bits();

        public native AVCodecContext header_bits(int i);

        public native int height();

        public native AVCodecContext height(int i);

        public native AVCodecContext hwaccel(AVHWAccel aVHWAccel);

        public native AVHWAccel hwaccel();

        public native Pointer hwaccel_context();

        public native AVCodecContext hwaccel_context(Pointer pointer);

        public native int i_count();

        public native AVCodecContext i_count(int i);

        public native float i_quant_factor();

        public native AVCodecContext i_quant_factor(float f);

        public native float i_quant_offset();

        public native AVCodecContext i_quant_offset(float f);

        public native int i_tex_bits();

        public native AVCodecContext i_tex_bits(int i);

        public native int idct_algo();

        public native AVCodecContext idct_algo(int i);

        public native int ildct_cmp();

        public native AVCodecContext ildct_cmp(int i);

        public native int initial_padding();

        public native AVCodecContext initial_padding(int i);

        @Cast({"uint16_t*"})
        public native ShortPointer inter_matrix();

        public native AVCodecContext inter_matrix(ShortPointer shortPointer);

        public native int inter_quant_bias();

        public native AVCodecContext inter_quant_bias(int i);

        public native AVCodecContext internal(AVCodecInternal aVCodecInternal);

        public native AVCodecInternal internal();

        public native int intra_dc_precision();

        public native AVCodecContext intra_dc_precision(int i);

        @Cast({"uint16_t*"})
        public native ShortPointer intra_matrix();

        public native AVCodecContext intra_matrix(ShortPointer shortPointer);

        public native int intra_quant_bias();

        public native AVCodecContext intra_quant_bias(int i);

        public native int keyint_min();

        public native AVCodecContext keyint_min(int i);

        public native int last_predictor_count();

        public native AVCodecContext last_predictor_count(int i);

        public native int level();

        public native AVCodecContext level(int i);

        @Deprecated
        public native int lmax();

        public native AVCodecContext lmax(int i);

        @Deprecated
        public native int lmin();

        public native AVCodecContext lmin(int i);

        public native int log_level_offset();

        public native AVCodecContext log_level_offset(int i);

        public native int lowres();

        public native AVCodecContext lowres(int i);

        public native float lumi_masking();

        public native AVCodecContext lumi_masking(float f);

        public native int max_b_frames();

        public native AVCodecContext max_b_frames(int i);

        public native int max_prediction_order();

        public native AVCodecContext max_prediction_order(int i);

        public native int max_qdiff();

        public native AVCodecContext max_qdiff(int i);

        public native int mb_cmp();

        public native AVCodecContext mb_cmp(int i);

        public native int mb_decision();

        public native AVCodecContext mb_decision(int i);

        public native int mb_lmax();

        public native AVCodecContext mb_lmax(int i);

        public native int mb_lmin();

        public native AVCodecContext mb_lmin(int i);

        @Deprecated
        public native int mb_threshold();

        public native AVCodecContext mb_threshold(int i);

        public native int me_cmp();

        public native AVCodecContext me_cmp(int i);

        public native int me_method();

        public native AVCodecContext me_method(int i);

        public native int me_penalty_compensation();

        public native AVCodecContext me_penalty_compensation(int i);

        public native int me_pre_cmp();

        public native AVCodecContext me_pre_cmp(int i);

        public native int me_range();

        public native AVCodecContext me_range(int i);

        public native int me_sub_cmp();

        public native AVCodecContext me_sub_cmp(int i);

        public native int me_subpel_quality();

        public native AVCodecContext me_subpel_quality(int i);

        @Deprecated
        public native int me_threshold();

        public native AVCodecContext me_threshold(int i);

        public native int min_prediction_order();

        public native AVCodecContext min_prediction_order(int i);

        public native int misc_bits();

        public native AVCodecContext misc_bits(int i);

        public native int mpeg_quant();

        public native AVCodecContext mpeg_quant(int i);

        public native int mv0_threshold();

        public native AVCodecContext mv0_threshold(int i);

        public native int mv_bits();

        public native AVCodecContext mv_bits(int i);

        public native int noise_reduction();

        public native AVCodecContext noise_reduction(int i);

        public native int nsse_weight();

        public native AVCodecContext nsse_weight(int i);

        public native Pointer opaque();

        public native AVCodecContext opaque(Pointer pointer);

        public native int p_count();

        public native AVCodecContext p_count(int i);

        public native float p_masking();

        public native AVCodecContext p_masking(float f);

        public native int p_tex_bits();

        public native AVCodecContext p_tex_bits(int i);

        @Cast({"AVPixelFormat"})
        public native int pix_fmt();

        public native AVCodecContext pix_fmt(int i);

        public native AVCodecContext pkt(AVPacket aVPacket);

        @Deprecated
        public native AVPacket pkt();

        public native AVCodecContext pkt_timebase(AVRational aVRational);

        @ByRef
        public native AVRational pkt_timebase();

        public native int pre_dia_size();

        public native AVCodecContext pre_dia_size(int i);

        public native int pre_me();

        public native AVCodecContext pre_me(int i);

        public native int prediction_method();

        public native AVCodecContext prediction_method(int i);

        public native Pointer priv_data();

        public native AVCodecContext priv_data(Pointer pointer);

        public native int profile();

        public native AVCodecContext profile(int i);

        public native long pts_correction_last_dts();

        public native AVCodecContext pts_correction_last_dts(long j);

        public native long pts_correction_last_pts();

        public native AVCodecContext pts_correction_last_pts(long j);

        public native long pts_correction_num_faulty_dts();

        public native AVCodecContext pts_correction_num_faulty_dts(long j);

        public native long pts_correction_num_faulty_pts();

        public native AVCodecContext pts_correction_num_faulty_pts(long j);

        public native float qblur();

        public native AVCodecContext qblur(float f);

        public native float qcompress();

        public native AVCodecContext qcompress(float f);

        public native int qmax();

        public native AVCodecContext qmax(int i);

        public native int qmin();

        public native AVCodecContext qmin(int i);

        @Deprecated
        public native float rc_buffer_aggressivity();

        public native AVCodecContext rc_buffer_aggressivity(float f);

        public native int rc_buffer_size();

        public native AVCodecContext rc_buffer_size(int i);

        @MemberGetter
        @Deprecated
        @Cast({"const char*"})
        public native BytePointer rc_eq();

        public native int rc_initial_buffer_occupancy();

        public native AVCodecContext rc_initial_buffer_occupancy(int i);

        @Deprecated
        public native float rc_initial_cplx();

        public native AVCodecContext rc_initial_cplx(float f);

        public native float rc_max_available_vbv_use();

        public native AVCodecContext rc_max_available_vbv_use(float f);

        public native int rc_max_rate();

        public native AVCodecContext rc_max_rate(int i);

        public native int rc_min_rate();

        public native AVCodecContext rc_min_rate(int i);

        public native float rc_min_vbv_overflow_use();

        public native AVCodecContext rc_min_vbv_overflow_use(float f);

        public native AVCodecContext rc_override(RcOverride rcOverride);

        public native RcOverride rc_override();

        public native int rc_override_count();

        public native AVCodecContext rc_override_count(int i);

        @Deprecated
        public native float rc_qmod_amp();

        public native AVCodecContext rc_qmod_amp(float f);

        @Deprecated
        public native int rc_qmod_freq();

        public native AVCodecContext rc_qmod_freq(int i);

        @Deprecated
        public native float rc_qsquish();

        public native AVCodecContext rc_qsquish(float f);

        public native int rc_strategy();

        public native AVCodecContext rc_strategy(int i);

        public native int refcounted_frames();

        public native AVCodecContext refcounted_frames(int i);

        public native int refs();

        public native AVCodecContext refs(int i);

        public native Reget_buffer_AVCodecContext_AVFrame reget_buffer();

        public native AVCodecContext reget_buffer(Reget_buffer_AVCodecContext_AVFrame reget_buffer_AVCodecContext_AVFrame);

        public native Release_buffer_AVCodecContext_AVFrame release_buffer();

        public native AVCodecContext release_buffer(Release_buffer_AVCodecContext_AVFrame release_buffer_AVCodecContext_AVFrame);

        public native long reordered_opaque();

        public native AVCodecContext reordered_opaque(long j);

        @Cast({"uint64_t"})
        public native long request_channel_layout();

        public native AVCodecContext request_channel_layout(long j);

        @Deprecated
        public native int request_channels();

        public native AVCodecContext request_channels(int i);

        @Cast({"AVSampleFormat"})
        public native int request_sample_fmt();

        public native AVCodecContext request_sample_fmt(int i);

        public native Rtp_callback_AVCodecContext_Pointer_int_int rtp_callback();

        public native AVCodecContext rtp_callback(Rtp_callback_AVCodecContext_Pointer_int_int rtp_callback_AVCodecContext_Pointer_int_int);

        public native int rtp_payload_size();

        public native AVCodecContext rtp_payload_size(int i);

        public native AVCodecContext sample_aspect_ratio(AVRational aVRational);

        @ByRef
        public native AVRational sample_aspect_ratio();

        @Cast({"AVSampleFormat"})
        public native int sample_fmt();

        public native AVCodecContext sample_fmt(int i);

        public native int sample_rate();

        public native AVCodecContext sample_rate(int i);

        @Deprecated
        public native int scenechange_factor();

        public native AVCodecContext scenechange_factor(int i);

        public native int scenechange_threshold();

        public native AVCodecContext scenechange_threshold(int i);

        public native int seek_preroll();

        public native AVCodecContext seek_preroll(int i);

        public native int side_data_only_packets();

        public native AVCodecContext side_data_only_packets(int i);

        public native int skip_alpha();

        public native AVCodecContext skip_alpha(int i);

        public native int skip_bottom();

        public native AVCodecContext skip_bottom(int i);

        public native int skip_count();

        public native AVCodecContext skip_count(int i);

        @Cast({"AVDiscard"})
        public native int skip_frame();

        public native AVCodecContext skip_frame(int i);

        @Cast({"AVDiscard"})
        public native int skip_idct();

        public native AVCodecContext skip_idct(int i);

        @Cast({"AVDiscard"})
        public native int skip_loop_filter();

        public native AVCodecContext skip_loop_filter(int i);

        public native int skip_top();

        public native AVCodecContext skip_top(int i);

        public native int slice_count();

        public native AVCodecContext slice_count(int i);

        public native int slice_flags();

        public native AVCodecContext slice_flags(int i);

        public native IntPointer slice_offset();

        public native AVCodecContext slice_offset(IntPointer intPointer);

        public native int slices();

        public native AVCodecContext slices(int i);

        public native float spatial_cplx_masking();

        public native AVCodecContext spatial_cplx_masking(float f);

        @Cast({"char*"})
        public native BytePointer stats_in();

        public native AVCodecContext stats_in(BytePointer bytePointer);

        @Cast({"char*"})
        public native BytePointer stats_out();

        public native AVCodecContext stats_out(BytePointer bytePointer);

        @Deprecated
        @Cast({"unsigned int"})
        public native int stream_codec_tag();

        public native AVCodecContext stream_codec_tag(int i);

        public native int strict_std_compliance();

        public native AVCodecContext strict_std_compliance(int i);

        @Cast({"char*"})
        public native BytePointer sub_charenc();

        public native AVCodecContext sub_charenc(BytePointer bytePointer);

        public native int sub_charenc_mode();

        public native AVCodecContext sub_charenc_mode(int i);

        @Cast({"uint8_t*"})
        public native BytePointer subtitle_header();

        public native AVCodecContext subtitle_header(BytePointer bytePointer);

        public native int subtitle_header_size();

        public native AVCodecContext subtitle_header_size(int i);

        @Cast({"AVPixelFormat"})
        public native int sw_pix_fmt();

        public native AVCodecContext sw_pix_fmt(int i);

        public native float temporal_cplx_masking();

        public native AVCodecContext temporal_cplx_masking(float f);

        public native int thread_count();

        public native AVCodecContext thread_count(int i);

        @Deprecated
        public native Pointer thread_opaque();

        public native AVCodecContext thread_opaque(Pointer pointer);

        public native int thread_safe_callbacks();

        public native AVCodecContext thread_safe_callbacks(int i);

        public native int thread_type();

        public native AVCodecContext thread_type(int i);

        public native int ticks_per_frame();

        public native AVCodecContext ticks_per_frame(int i);

        public native AVCodecContext time_base(AVRational aVRational);

        @ByRef
        public native AVRational time_base();

        public native long timecode_frame_start();

        public native AVCodecContext timecode_frame_start(long j);

        public native int trellis();

        public native AVCodecContext trellis(int i);

        @Cast({"uint64_t"})
        public native long vbv_delay();

        public native AVCodecContext vbv_delay(long j);

        public native int width();

        public native AVCodecContext width(int i);

        public native int workaround_bugs();

        public native AVCodecContext workaround_bugs(int i);

        @Deprecated
        public native int xvmc_acceleration();

        public native AVCodecContext xvmc_acceleration(int i);

        static {
            Loader.load();
        }

        public AVCodecContext() {
            allocate();
        }

        public AVCodecContext(int size) {
            allocateArray(size);
        }

        public AVCodecContext(Pointer p) {
            super(p);
        }

        public AVCodecContext position(int position) {
            return (AVCodecContext) super.position(position);
        }
    }

    @Opaque
    public static class AVCodecDefault extends Pointer {
        public AVCodecDefault(Pointer p) {
            super(p);
        }
    }

    public static class AVCodecDescriptor extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"AVCodecID"})
        public native int id();

        public native AVCodecDescriptor id(int i);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer long_name();

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer mime_types(int i);

        @MemberGetter
        @Cast({"const char*const*"})
        public native PointerPointer mime_types();

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        public native int props();

        public native AVCodecDescriptor props(int i);

        @Cast({"AVMediaType"})
        public native int type();

        public native AVCodecDescriptor type(int i);

        static {
            Loader.load();
        }

        public AVCodecDescriptor() {
            allocate();
        }

        public AVCodecDescriptor(int size) {
            allocateArray(size);
        }

        public AVCodecDescriptor(Pointer p) {
            super(p);
        }

        public AVCodecDescriptor position(int position) {
            return (AVCodecDescriptor) super.position(position);
        }
    }

    @Opaque
    public static class AVCodecInternal extends Pointer {
        public AVCodecInternal(Pointer p) {
            super(p);
        }
    }

    public static class AVCodecParser extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native int codec_ids(int i);

        @MemberGetter
        public native IntPointer codec_ids();

        public native AVCodecParser codec_ids(int i, int i2);

        public native AVCodecParser next();

        public native AVCodecParser next(AVCodecParser aVCodecParser);

        public native Parser_close_AVCodecParserContext parser_close();

        public native AVCodecParser parser_close(Parser_close_AVCodecParserContext parser_close_AVCodecParserContext);

        public native Parser_init_AVCodecParserContext parser_init();

        public native AVCodecParser parser_init(Parser_init_AVCodecParserContext parser_init_AVCodecParserContext);

        public native Parser_parse_AVCodecParserContext_AVCodecContext_PointerPointer_IntPointer_BytePointer_int parser_parse();

        public native AVCodecParser parser_parse(Parser_parse_AVCodecParserContext_AVCodecContext_PointerPointer_IntPointer_BytePointer_int parser_parse_AVCodecParserContext_AVCodecContext_PointerPointer_IntPointer_BytePointer_int);

        public native int priv_data_size();

        public native AVCodecParser priv_data_size(int i);

        public native Split_AVCodecContext_BytePointer_int split();

        public native AVCodecParser split(Split_AVCodecContext_BytePointer_int split_AVCodecContext_BytePointer_int);

        static {
            Loader.load();
        }

        public AVCodecParser() {
            allocate();
        }

        public AVCodecParser(int size) {
            allocateArray(size);
        }

        public AVCodecParser(Pointer p) {
            super(p);
        }

        public AVCodecParser position(int position) {
            return (AVCodecParser) super.position(position);
        }
    }

    public static class AVCodecParserContext extends Pointer {
        public static final int AV_PARSER_PTS_NB = 4;
        public static final int PARSER_FLAG_COMPLETE_FRAMES = 1;
        public static final int PARSER_FLAG_FETCHED_OFFSET = 4;
        public static final int PARSER_FLAG_ONCE = 2;
        public static final int PARSER_FLAG_USE_CODEC_TS = 4096;

        private native void allocate();

        private native void allocateArray(int i);

        public native int coded_height();

        public native AVCodecParserContext coded_height(int i);

        public native int coded_width();

        public native AVCodecParserContext coded_width(int i);

        public native long convergence_duration();

        public native AVCodecParserContext convergence_duration(long j);

        public native long cur_frame_dts(int i);

        @MemberGetter
        public native LongPointer cur_frame_dts();

        public native AVCodecParserContext cur_frame_dts(int i, long j);

        public native long cur_frame_end(int i);

        @MemberGetter
        public native LongPointer cur_frame_end();

        public native AVCodecParserContext cur_frame_end(int i, long j);

        public native long cur_frame_offset(int i);

        @MemberGetter
        public native LongPointer cur_frame_offset();

        public native AVCodecParserContext cur_frame_offset(int i, long j);

        public native long cur_frame_pos(int i);

        @MemberGetter
        public native LongPointer cur_frame_pos();

        public native AVCodecParserContext cur_frame_pos(int i, long j);

        public native long cur_frame_pts(int i);

        @MemberGetter
        public native LongPointer cur_frame_pts();

        public native AVCodecParserContext cur_frame_pts(int i, long j);

        public native int cur_frame_start_index();

        public native AVCodecParserContext cur_frame_start_index(int i);

        public native long cur_offset();

        public native AVCodecParserContext cur_offset(long j);

        public native long dts();

        public native AVCodecParserContext dts(long j);

        public native int dts_ref_dts_delta();

        public native AVCodecParserContext dts_ref_dts_delta(int i);

        public native int dts_sync_point();

        public native AVCodecParserContext dts_sync_point(int i);

        public native int duration();

        public native AVCodecParserContext duration(int i);

        public native int fetch_timestamp();

        public native AVCodecParserContext fetch_timestamp(int i);

        @Cast({"AVFieldOrder"})
        public native int field_order();

        public native AVCodecParserContext field_order(int i);

        public native int flags();

        public native AVCodecParserContext flags(int i);

        public native int format();

        public native AVCodecParserContext format(int i);

        public native long frame_offset();

        public native AVCodecParserContext frame_offset(long j);

        public native int height();

        public native AVCodecParserContext height(int i);

        public native int key_frame();

        public native AVCodecParserContext key_frame(int i);

        public native long last_dts();

        public native AVCodecParserContext last_dts(long j);

        public native long last_pos();

        public native AVCodecParserContext last_pos(long j);

        public native long last_pts();

        public native AVCodecParserContext last_pts(long j);

        public native long next_frame_offset();

        public native AVCodecParserContext next_frame_offset(long j);

        public native long offset();

        public native AVCodecParserContext offset(long j);

        public native int output_picture_number();

        public native AVCodecParserContext output_picture_number(int i);

        public native AVCodecParser parser();

        public native AVCodecParserContext parser(AVCodecParser aVCodecParser);

        public native int pict_type();

        public native AVCodecParserContext pict_type(int i);

        @Cast({"AVPictureStructure"})
        public native int picture_structure();

        public native AVCodecParserContext picture_structure(int i);

        public native long pos();

        public native AVCodecParserContext pos(long j);

        public native Pointer priv_data();

        public native AVCodecParserContext priv_data(Pointer pointer);

        public native long pts();

        public native AVCodecParserContext pts(long j);

        public native int pts_dts_delta();

        public native AVCodecParserContext pts_dts_delta(int i);

        public native int repeat_pict();

        public native AVCodecParserContext repeat_pict(int i);

        public native int width();

        public native AVCodecParserContext width(int i);

        static {
            Loader.load();
        }

        public AVCodecParserContext() {
            allocate();
        }

        public AVCodecParserContext(int size) {
            allocateArray(size);
        }

        public AVCodecParserContext(Pointer p) {
            super(p);
        }

        public AVCodecParserContext position(int position) {
            return (AVCodecParserContext) super.position(position);
        }
    }

    public static class AVHWAccel extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        public native Alloc_frame_AVCodecContext_AVFrame alloc_frame();

        public native AVHWAccel alloc_frame(Alloc_frame_AVCodecContext_AVFrame alloc_frame_AVCodecContext_AVFrame);

        public native int capabilities();

        public native AVHWAccel capabilities(int i);

        public native Decode_mb_MpegEncContext decode_mb();

        public native AVHWAccel decode_mb(Decode_mb_MpegEncContext decode_mb_MpegEncContext);

        public native Decode_slice_AVCodecContext_BytePointer_int decode_slice();

        public native AVHWAccel decode_slice(Decode_slice_AVCodecContext_BytePointer_int decode_slice_AVCodecContext_BytePointer_int);

        public native End_frame_AVCodecContext end_frame();

        public native AVHWAccel end_frame(End_frame_AVCodecContext end_frame_AVCodecContext);

        public native int frame_priv_data_size();

        public native AVHWAccel frame_priv_data_size(int i);

        @Cast({"AVCodecID"})
        public native int id();

        public native AVHWAccel id(int i);

        public native Init_AVCodecContext init();

        public native AVHWAccel init(Init_AVCodecContext init_AVCodecContext);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        public native AVHWAccel next();

        public native AVHWAccel next(AVHWAccel aVHWAccel);

        @Cast({"AVPixelFormat"})
        public native int pix_fmt();

        public native AVHWAccel pix_fmt(int i);

        public native int priv_data_size();

        public native AVHWAccel priv_data_size(int i);

        public native Start_frame_AVCodecContext_BytePointer_int start_frame();

        public native AVHWAccel start_frame(Start_frame_AVCodecContext_BytePointer_int start_frame_AVCodecContext_BytePointer_int);

        @Cast({"AVMediaType"})
        public native int type();

        public native AVHWAccel type(int i);

        public native Uninit_AVCodecContext uninit();

        public native AVHWAccel uninit(Uninit_AVCodecContext uninit_AVCodecContext);

        static {
            Loader.load();
        }

        public AVHWAccel() {
            allocate();
        }

        public AVHWAccel(int size) {
            allocateArray(size);
        }

        public AVHWAccel(Pointer p) {
            super(p);
        }

        public AVHWAccel position(int position) {
            return (AVHWAccel) super.position(position);
        }
    }

    public static class AVPacket extends Pointer {

        public static class Destruct_AVPacket extends FunctionPointer {
            private native void allocate();

            @Deprecated
            public native void call(AVPacket aVPacket);

            static {
                Loader.load();
            }

            public Destruct_AVPacket(Pointer p) {
                super(p);
            }

            protected Destruct_AVPacket() {
                allocate();
            }
        }

        private native void allocate();

        private native void allocateArray(int i);

        public native AVPacket buf(AVBufferRef aVBufferRef);

        public native AVBufferRef buf();

        public native long convergence_duration();

        public native AVPacket convergence_duration(long j);

        @Cast({"uint8_t*"})
        public native BytePointer data();

        public native AVPacket data(BytePointer bytePointer);

        public native Destruct_AVPacket destruct();

        public native AVPacket destruct(Destruct_AVPacket destruct_AVPacket);

        public native long dts();

        public native AVPacket dts(long j);

        public native int duration();

        public native AVPacket duration(int i);

        public native int flags();

        public native AVPacket flags(int i);

        public native long pos();

        public native AVPacket pos(long j);

        @Deprecated
        public native Pointer priv();

        public native AVPacket priv(Pointer pointer);

        public native long pts();

        public native AVPacket pts(long j);

        public native AVPacket side_data(AVPacketSideData aVPacketSideData);

        public native AVPacketSideData side_data();

        public native int side_data_elems();

        public native AVPacket side_data_elems(int i);

        public native int size();

        public native AVPacket size(int i);

        public native int stream_index();

        public native AVPacket stream_index(int i);

        static {
            Loader.load();
        }

        public AVPacket() {
            allocate();
        }

        public AVPacket(int size) {
            allocateArray(size);
        }

        public AVPacket(Pointer p) {
            super(p);
        }

        public AVPacket position(int position) {
            return (AVPacket) super.position(position);
        }
    }

    public static class AVPacketSideData extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"uint8_t*"})
        public native BytePointer data();

        public native AVPacketSideData data(BytePointer bytePointer);

        public native int size();

        public native AVPacketSideData size(int i);

        @Cast({"AVPacketSideDataType"})
        public native int type();

        public native AVPacketSideData type(int i);

        static {
            Loader.load();
        }

        public AVPacketSideData() {
            allocate();
        }

        public AVPacketSideData(int size) {
            allocateArray(size);
        }

        public AVPacketSideData(Pointer p) {
            super(p);
        }

        public AVPacketSideData position(int position) {
            return (AVPacketSideData) super.position(position);
        }
    }

    public static class AVPanScan extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @MemberGetter
        @Cast({"int16_t(*)[2]"})
        @Name({"position"})
        public native ShortPointer _position();

        public native AVPanScan _position(int i, int i2, short s);

        @Name({"position"})
        public native short _position(int i, int i2);

        public native int height();

        public native AVPanScan height(int i);

        public native int id();

        public native AVPanScan id(int i);

        public native int width();

        public native AVPanScan width(int i);

        static {
            Loader.load();
        }

        public AVPanScan() {
            allocate();
        }

        public AVPanScan(int size) {
            allocateArray(size);
        }

        public AVPanScan(Pointer p) {
            super(p);
        }

        public AVPanScan position(int position) {
            return (AVPanScan) super.position(position);
        }
    }

    public static class AVPicture extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"uint8_t*"})
        public native BytePointer data(int i);

        @MemberGetter
        @Cast({"uint8_t**"})
        public native PointerPointer data();

        public native AVPicture data(int i, BytePointer bytePointer);

        public native int linesize(int i);

        @MemberGetter
        public native IntPointer linesize();

        public native AVPicture linesize(int i, int i2);

        static {
            Loader.load();
        }

        public AVPicture() {
            allocate();
        }

        public AVPicture(int size) {
            allocateArray(size);
        }

        public AVPicture(Pointer p) {
            super(p);
        }

        public AVPicture position(int position) {
            return (AVPicture) super.position(position);
        }
    }

    public static class AVProfile extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @MemberGetter
        @Cast({"const char*"})
        public native BytePointer name();

        public native int profile();

        public native AVProfile profile(int i);

        static {
            Loader.load();
        }

        public AVProfile() {
            allocate();
        }

        public AVProfile(int size) {
            allocateArray(size);
        }

        public AVProfile(Pointer p) {
            super(p);
        }

        public AVProfile position(int position) {
            return (AVProfile) super.position(position);
        }
    }

    @Opaque
    public static class AVResampleContext extends Pointer {
        public AVResampleContext(Pointer p) {
            super(p);
        }
    }

    public static class AVSubtitle extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"uint32_t"})
        public native int end_display_time();

        public native AVSubtitle end_display_time(int i);

        public native AVSubtitle format(short s);

        @Cast({"uint16_t"})
        public native short format();

        @Cast({"unsigned"})
        public native int num_rects();

        public native AVSubtitle num_rects(int i);

        public native long pts();

        public native AVSubtitle pts(long j);

        @MemberGetter
        @Cast({"AVSubtitleRect**"})
        public native PointerPointer rects();

        public native AVSubtitle rects(int i, AVSubtitleRect aVSubtitleRect);

        public native AVSubtitleRect rects(int i);

        @Cast({"uint32_t"})
        public native int start_display_time();

        public native AVSubtitle start_display_time(int i);

        static {
            Loader.load();
        }

        public AVSubtitle() {
            allocate();
        }

        public AVSubtitle(int size) {
            allocateArray(size);
        }

        public AVSubtitle(Pointer p) {
            super(p);
        }

        public AVSubtitle position(int position) {
            return (AVSubtitle) super.position(position);
        }
    }

    public static class AVSubtitleRect extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"char*"})
        public native BytePointer ass();

        public native AVSubtitleRect ass(BytePointer bytePointer);

        public native int flags();

        public native AVSubtitleRect flags(int i);

        public native int h();

        public native AVSubtitleRect h(int i);

        public native int nb_colors();

        public native AVSubtitleRect nb_colors(int i);

        @ByRef
        public native AVPicture pict();

        public native AVSubtitleRect pict(AVPicture aVPicture);

        @Cast({"char*"})
        public native BytePointer text();

        public native AVSubtitleRect text(BytePointer bytePointer);

        @Cast({"AVSubtitleType"})
        public native int type();

        public native AVSubtitleRect type(int i);

        public native int w();

        public native AVSubtitleRect w(int i);

        public native int x();

        public native AVSubtitleRect x(int i);

        public native int y();

        public native AVSubtitleRect y(int i);

        static {
            Loader.load();
        }

        public AVSubtitleRect() {
            allocate();
        }

        public AVSubtitleRect(int size) {
            allocateArray(size);
        }

        public AVSubtitleRect(Pointer p) {
            super(p);
        }

        public AVSubtitleRect position(int position) {
            return (AVSubtitleRect) super.position(position);
        }
    }

    @Opaque
    public static class DCTContext extends Pointer {
        public DCTContext(Pointer p) {
            super(p);
        }
    }

    public static class FFTComplex extends Pointer {
        private native void allocate();

        private native void allocateArray(int i);

        @Cast({"FFTSample"})
        public native float im();

        public native FFTComplex im(float f);

        @Cast({"FFTSample"})
        public native float re();

        public native FFTComplex re(float f);

        static {
            Loader.load();
        }

        public FFTComplex() {
            allocate();
        }

        public FFTComplex(int size) {
            allocateArray(size);
        }

        public FFTComplex(Pointer p) {
            super(p);
        }

        public FFTComplex position(int position) {
            return (FFTComplex) super.position(position);
        }
    }

    @Opaque
    public static class FFTContext extends Pointer {
        public FFTContext(Pointer p) {
            super(p);
        }
    }

    @Opaque
    public static class MpegEncContext extends Pointer {
        public MpegEncContext(Pointer p) {
            super(p);
        }
    }

    @Opaque
    public static class RDFTContext extends Pointer {
        public RDFTContext(Pointer p) {
            super(p);
        }
    }

    @Opaque
    public static class ReSampleContext extends Pointer {
        public ReSampleContext(Pointer p) {
            super(p);
        }
    }

    @MemberGetter
    public static native int AV_CODEC_ID_012V();

    @MemberGetter
    public static native int AV_CODEC_ID_ADPCM_AFC();

    @MemberGetter
    public static native int AV_CODEC_ID_ADPCM_DTK();

    @MemberGetter
    public static native int AV_CODEC_ID_ADPCM_G726LE();

    @MemberGetter
    public static native int AV_CODEC_ID_ADPCM_IMA_OKI();

    @MemberGetter
    public static native int AV_CODEC_ID_ADPCM_IMA_RAD();

    @MemberGetter
    public static native int AV_CODEC_ID_ADPCM_VIMA();

    @MemberGetter
    public static native int AV_CODEC_ID_APNG();

    @MemberGetter
    public static native int AV_CODEC_ID_ASS();

    @MemberGetter
    public static native int AV_CODEC_ID_AVRN();

    @MemberGetter
    public static native int AV_CODEC_ID_AVRP();

    @MemberGetter
    public static native int AV_CODEC_ID_AVUI();

    @MemberGetter
    public static native int AV_CODEC_ID_AYUV();

    @MemberGetter
    public static native int AV_CODEC_ID_BINTEXT();

    @MemberGetter
    public static native int AV_CODEC_ID_BIN_DATA();

    @MemberGetter
    public static native int AV_CODEC_ID_BRENDER_PIX();

    @MemberGetter
    public static native int AV_CODEC_ID_CPIA();

    @MemberGetter
    public static native int AV_CODEC_ID_DSD_LSBF();

    @MemberGetter
    public static native int AV_CODEC_ID_DSD_LSBF_PLANAR();

    @MemberGetter
    public static native int AV_CODEC_ID_DSD_MSBF();

    @MemberGetter
    public static native int AV_CODEC_ID_DSD_MSBF_PLANAR();

    @MemberGetter
    public static native int AV_CODEC_ID_DVD_NAV();

    @MemberGetter
    public static native int AV_CODEC_ID_EIA_608();

    @MemberGetter
    public static native int AV_CODEC_ID_ESCAPE130();

    @MemberGetter
    public static native int AV_CODEC_ID_EVRC();

    @MemberGetter
    public static native int AV_CODEC_ID_EXR();

    @MemberGetter
    public static native int AV_CODEC_ID_FFWAVESYNTH();

    @MemberGetter
    public static native int AV_CODEC_ID_G2M();

    @MemberGetter
    public static native int AV_CODEC_ID_HEVC();

    @MemberGetter
    public static native int AV_CODEC_ID_IDF();

    @MemberGetter
    public static native int AV_CODEC_ID_JACOSUB();

    @MemberGetter
    public static native int AV_CODEC_ID_MICRODVD();

    @MemberGetter
    public static native int AV_CODEC_ID_MPL2();

    @MemberGetter
    public static native int AV_CODEC_ID_MVC1();

    @MemberGetter
    public static native int AV_CODEC_ID_MVC2();

    @MemberGetter
    public static native int AV_CODEC_ID_OPUS();

    @MemberGetter
    public static native int AV_CODEC_ID_OTF();

    @MemberGetter
    public static native int AV_CODEC_ID_PAF_AUDIO();

    @MemberGetter
    public static native int AV_CODEC_ID_PAF_VIDEO();

    @MemberGetter
    public static native int AV_CODEC_ID_PCM_S16BE_PLANAR();

    @MemberGetter
    public static native int AV_CODEC_ID_PCM_S24LE_PLANAR();

    @MemberGetter
    public static native int AV_CODEC_ID_PCM_S32LE_PLANAR();

    @MemberGetter
    public static native int AV_CODEC_ID_PJS();

    @MemberGetter
    public static native int AV_CODEC_ID_REALTEXT();

    @MemberGetter
    public static native int AV_CODEC_ID_SAMI();

    @MemberGetter
    public static native int AV_CODEC_ID_SANM();

    @MemberGetter
    public static native int AV_CODEC_ID_SGIRLE();

    @MemberGetter
    public static native int AV_CODEC_ID_SMPTE_KLV();

    @MemberGetter
    public static native int AV_CODEC_ID_SMV();

    @MemberGetter
    public static native int AV_CODEC_ID_SMVJPEG();

    @MemberGetter
    public static native int AV_CODEC_ID_SNOW();

    @MemberGetter
    public static native int AV_CODEC_ID_SONIC();

    @MemberGetter
    public static native int AV_CODEC_ID_SONIC_LS();

    @MemberGetter
    public static native int AV_CODEC_ID_STL();

    @MemberGetter
    public static native int AV_CODEC_ID_SUBRIP();

    @MemberGetter
    public static native int AV_CODEC_ID_SUBVIEWER();

    @MemberGetter
    public static native int AV_CODEC_ID_SUBVIEWER1();

    @MemberGetter
    public static native int AV_CODEC_ID_TAK();

    @MemberGetter
    public static native int AV_CODEC_ID_TARGA_Y216();

    @MemberGetter
    public static native int AV_CODEC_ID_TIMED_ID3();

    @MemberGetter
    public static native int AV_CODEC_ID_V308();

    @MemberGetter
    public static native int AV_CODEC_ID_V408();

    @MemberGetter
    public static native int AV_CODEC_ID_VIMA();

    @MemberGetter
    public static native int AV_CODEC_ID_VP7();

    @MemberGetter
    public static native int AV_CODEC_ID_VPLAYER();

    @MemberGetter
    public static native int AV_CODEC_ID_WEBP();

    @MemberGetter
    public static native int AV_CODEC_ID_WEBVTT();

    @MemberGetter
    public static native int AV_CODEC_ID_XBIN();

    @MemberGetter
    public static native int AV_CODEC_ID_XFACE();

    @MemberGetter
    public static native int AV_CODEC_ID_Y41P();

    @MemberGetter
    public static native int AV_CODEC_ID_YUV4();

    @Deprecated
    public static native int audio_resample(ReSampleContext reSampleContext, ShortBuffer shortBuffer, ShortBuffer shortBuffer2, int i);

    @Deprecated
    public static native int audio_resample(ReSampleContext reSampleContext, ShortPointer shortPointer, ShortPointer shortPointer2, int i);

    @Deprecated
    public static native int audio_resample(ReSampleContext reSampleContext, short[] sArr, short[] sArr2, int i);

    @Deprecated
    public static native void audio_resample_close(ReSampleContext reSampleContext);

    @Deprecated
    public static native ReSampleContext av_audio_resample_init(int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5, @Cast({"AVSampleFormat"}) int i6, int i7, int i8, int i9, double d);

    public static native void av_bitstream_filter_close(AVBitStreamFilterContext aVBitStreamFilterContext);

    public static native int av_bitstream_filter_filter(AVBitStreamFilterContext aVBitStreamFilterContext, AVCodecContext aVCodecContext, String str, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, int i, int i2);

    public static native int av_bitstream_filter_filter(AVBitStreamFilterContext aVBitStreamFilterContext, AVCodecContext aVCodecContext, String str, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i, int i2);

    public static native int av_bitstream_filter_filter(AVBitStreamFilterContext aVBitStreamFilterContext, AVCodecContext aVCodecContext, String str, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @Cast({"const uint8_t*"}) byte[] bArr2, int i, int i2);

    public static native int av_bitstream_filter_filter(AVBitStreamFilterContext aVBitStreamFilterContext, AVCodecContext aVCodecContext, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, int i, int i2);

    public static native int av_bitstream_filter_filter(AVBitStreamFilterContext aVBitStreamFilterContext, AVCodecContext aVCodecContext, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer2, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer3, int i, int i2);

    public static native int av_bitstream_filter_filter(AVBitStreamFilterContext aVBitStreamFilterContext, AVCodecContext aVCodecContext, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i, int i2);

    public static native int av_bitstream_filter_filter(AVBitStreamFilterContext aVBitStreamFilterContext, AVCodecContext aVCodecContext, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @Cast({"const uint8_t*"}) byte[] bArr2, int i, int i2);

    public static native AVBitStreamFilterContext av_bitstream_filter_init(String str);

    public static native AVBitStreamFilterContext av_bitstream_filter_init(@Cast({"const char*"}) BytePointer bytePointer);

    public static native AVBitStreamFilter av_bitstream_filter_next(@Const AVBitStreamFilter aVBitStreamFilter);

    @Cast({"uint16_t*"})
    public static native ShortPointer av_codec_get_chroma_intra_matrix(@Const AVCodecContext aVCodecContext);

    @Const
    public static native AVCodecDescriptor av_codec_get_codec_descriptor(@Const AVCodecContext aVCodecContext);

    public static native int av_codec_get_lowres(@Const AVCodecContext aVCodecContext);

    public static native int av_codec_get_max_lowres(@Const AVCodec aVCodec);

    @ByVal
    public static native AVRational av_codec_get_pkt_timebase(@Const AVCodecContext aVCodecContext);

    public static native int av_codec_get_seek_preroll(@Const AVCodecContext aVCodecContext);

    public static native int av_codec_is_decoder(@Const AVCodec aVCodec);

    public static native int av_codec_is_encoder(@Const AVCodec aVCodec);

    public static native AVCodec av_codec_next(@Const AVCodec aVCodec);

    public static native void av_codec_set_chroma_intra_matrix(AVCodecContext aVCodecContext, @Cast({"uint16_t*"}) ShortBuffer shortBuffer);

    public static native void av_codec_set_chroma_intra_matrix(AVCodecContext aVCodecContext, @Cast({"uint16_t*"}) ShortPointer shortPointer);

    public static native void av_codec_set_chroma_intra_matrix(AVCodecContext aVCodecContext, @Cast({"uint16_t*"}) short[] sArr);

    public static native void av_codec_set_codec_descriptor(AVCodecContext aVCodecContext, @Const AVCodecDescriptor aVCodecDescriptor);

    public static native void av_codec_set_lowres(AVCodecContext aVCodecContext, int i);

    public static native void av_codec_set_pkt_timebase(AVCodecContext aVCodecContext, @ByVal AVRational aVRational);

    public static native void av_codec_set_seek_preroll(AVCodecContext aVCodecContext, int i);

    public static native int av_copy_packet(AVPacket aVPacket, @Const AVPacket aVPacket2);

    public static native int av_copy_packet_side_data(AVPacket aVPacket, @Const AVPacket aVPacket2);

    public static native void av_dct_calc(DCTContext dCTContext, @Cast({"FFTSample*"}) FloatBuffer floatBuffer);

    public static native void av_dct_calc(DCTContext dCTContext, @Cast({"FFTSample*"}) FloatPointer floatPointer);

    public static native void av_dct_calc(DCTContext dCTContext, @Cast({"FFTSample*"}) float[] fArr);

    public static native void av_dct_end(DCTContext dCTContext);

    public static native DCTContext av_dct_init(int i, @Cast({"DCTTransformType"}) int i2);

    @Deprecated
    public static native void av_destruct_packet(AVPacket aVPacket);

    public static native int av_dup_packet(AVPacket aVPacket);

    public static native void av_fast_padded_malloc(Pointer pointer, @Cast({"unsigned int*"}) IntBuffer intBuffer, @Cast({"size_t"}) long j);

    public static native void av_fast_padded_malloc(Pointer pointer, @Cast({"unsigned int*"}) IntPointer intPointer, @Cast({"size_t"}) long j);

    public static native void av_fast_padded_malloc(Pointer pointer, @Cast({"unsigned int*"}) int[] iArr, @Cast({"size_t"}) long j);

    public static native void av_fast_padded_mallocz(Pointer pointer, @Cast({"unsigned int*"}) IntBuffer intBuffer, @Cast({"size_t"}) long j);

    public static native void av_fast_padded_mallocz(Pointer pointer, @Cast({"unsigned int*"}) IntPointer intPointer, @Cast({"size_t"}) long j);

    public static native void av_fast_padded_mallocz(Pointer pointer, @Cast({"unsigned int*"}) int[] iArr, @Cast({"size_t"}) long j);

    public static native void av_fft_calc(FFTContext fFTContext, FFTComplex fFTComplex);

    public static native void av_fft_end(FFTContext fFTContext);

    public static native FFTContext av_fft_init(int i, int i2);

    public static native void av_fft_permute(FFTContext fFTContext, FFTComplex fFTComplex);

    public static native void av_free_packet(AVPacket aVPacket);

    public static native int av_get_audio_frame_duration(AVCodecContext aVCodecContext, int i);

    public static native int av_get_bits_per_sample(@Cast({"AVCodecID"}) int i);

    @Cast({"size_t"})
    public static native long av_get_codec_tag_string(@Cast({"char*"}) ByteBuffer byteBuffer, @Cast({"size_t"}) long j, @Cast({"unsigned int"}) int i);

    @Cast({"size_t"})
    public static native long av_get_codec_tag_string(@Cast({"char*"}) BytePointer bytePointer, @Cast({"size_t"}) long j, @Cast({"unsigned int"}) int i);

    @Cast({"size_t"})
    public static native long av_get_codec_tag_string(@Cast({"char*"}) byte[] bArr, @Cast({"size_t"}) long j, @Cast({"unsigned int"}) int i);

    public static native int av_get_exact_bits_per_sample(@Cast({"AVCodecID"}) int i);

    @Cast({"AVCodecID"})
    public static native int av_get_pcm_codec(@Cast({"AVSampleFormat"}) int i, int i2);

    @Cast({"const char*"})
    public static native BytePointer av_get_profile_name(@Const AVCodec aVCodec, int i);

    public static native int av_grow_packet(AVPacket aVPacket, int i);

    public static native AVHWAccel av_hwaccel_next(@Const AVHWAccel aVHWAccel);

    public static native void av_imdct_calc(FFTContext fFTContext, @Cast({"FFTSample*"}) FloatBuffer floatBuffer, @Cast({"const FFTSample*"}) FloatBuffer floatBuffer2);

    public static native void av_imdct_calc(FFTContext fFTContext, @Cast({"FFTSample*"}) FloatPointer floatPointer, @Cast({"const FFTSample*"}) FloatPointer floatPointer2);

    public static native void av_imdct_calc(FFTContext fFTContext, @Cast({"FFTSample*"}) float[] fArr, @Cast({"const FFTSample*"}) float[] fArr2);

    public static native void av_imdct_half(FFTContext fFTContext, @Cast({"FFTSample*"}) FloatBuffer floatBuffer, @Cast({"const FFTSample*"}) FloatBuffer floatBuffer2);

    public static native void av_imdct_half(FFTContext fFTContext, @Cast({"FFTSample*"}) FloatPointer floatPointer, @Cast({"const FFTSample*"}) FloatPointer floatPointer2);

    public static native void av_imdct_half(FFTContext fFTContext, @Cast({"FFTSample*"}) float[] fArr, @Cast({"const FFTSample*"}) float[] fArr2);

    public static native void av_init_packet(AVPacket aVPacket);

    public static native int av_lockmgr_register(Cb_PointerPointer_int cb_PointerPointer_int);

    public static native int av_lockmgr_register(Cb_Pointer_int cb_Pointer_int);

    @Deprecated
    public static native void av_log_ask_for_sample(Pointer pointer, String str);

    @Deprecated
    public static native void av_log_ask_for_sample(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer);

    @Deprecated
    public static native void av_log_missing_feature(Pointer pointer, String str, int i);

    @Deprecated
    public static native void av_log_missing_feature(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

    public static native void av_mdct_calc(FFTContext fFTContext, @Cast({"FFTSample*"}) FloatBuffer floatBuffer, @Cast({"const FFTSample*"}) FloatBuffer floatBuffer2);

    public static native void av_mdct_calc(FFTContext fFTContext, @Cast({"FFTSample*"}) FloatPointer floatPointer, @Cast({"const FFTSample*"}) FloatPointer floatPointer2);

    public static native void av_mdct_calc(FFTContext fFTContext, @Cast({"FFTSample*"}) float[] fArr, @Cast({"const FFTSample*"}) float[] fArr2);

    public static native void av_mdct_end(FFTContext fFTContext);

    public static native FFTContext av_mdct_init(int i, int i2, double d);

    public static native int av_new_packet(AVPacket aVPacket, int i);

    public static native int av_packet_copy_props(AVPacket aVPacket, @Const AVPacket aVPacket2);

    public static native void av_packet_free_side_data(AVPacket aVPacket);

    public static native int av_packet_from_data(AVPacket aVPacket, @Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i);

    public static native int av_packet_from_data(AVPacket aVPacket, @Cast({"uint8_t*"}) BytePointer bytePointer, int i);

    public static native int av_packet_from_data(AVPacket aVPacket, @Cast({"uint8_t*"}) byte[] bArr, int i);

    @Cast({"uint8_t*"})
    public static native ByteBuffer av_packet_get_side_data(AVPacket aVPacket, @Cast({"AVPacketSideDataType"}) int i, IntBuffer intBuffer);

    @Cast({"uint8_t*"})
    public static native BytePointer av_packet_get_side_data(AVPacket aVPacket, @Cast({"AVPacketSideDataType"}) int i, IntPointer intPointer);

    @Cast({"uint8_t*"})
    public static native byte[] av_packet_get_side_data(AVPacket aVPacket, @Cast({"AVPacketSideDataType"}) int i, int[] iArr);

    public static native int av_packet_merge_side_data(AVPacket aVPacket);

    public static native void av_packet_move_ref(AVPacket aVPacket, AVPacket aVPacket2);

    @Cast({"uint8_t*"})
    public static native BytePointer av_packet_new_side_data(AVPacket aVPacket, @Cast({"AVPacketSideDataType"}) int i, int i2);

    @Cast({"uint8_t*"})
    public static native ByteBuffer av_packet_pack_dictionary(AVDictionary aVDictionary, IntBuffer intBuffer);

    @Cast({"uint8_t*"})
    public static native BytePointer av_packet_pack_dictionary(AVDictionary aVDictionary, IntPointer intPointer);

    @Cast({"uint8_t*"})
    public static native byte[] av_packet_pack_dictionary(AVDictionary aVDictionary, int[] iArr);

    public static native int av_packet_ref(AVPacket aVPacket, @Const AVPacket aVPacket2);

    public static native void av_packet_rescale_ts(AVPacket aVPacket, @ByVal AVRational aVRational, @ByVal AVRational aVRational2);

    public static native int av_packet_shrink_side_data(AVPacket aVPacket, @Cast({"AVPacketSideDataType"}) int i, int i2);

    @Cast({"const char*"})
    public static native BytePointer av_packet_side_data_name(@Cast({"AVPacketSideDataType"}) int i);

    public static native int av_packet_split_side_data(AVPacket aVPacket);

    public static native int av_packet_unpack_dictionary(@Cast({"const uint8_t*"}) ByteBuffer byteBuffer, int i, @ByPtrPtr AVDictionary aVDictionary);

    public static native int av_packet_unpack_dictionary(@Cast({"const uint8_t*"}) BytePointer bytePointer, int i, @Cast({"AVDictionary**"}) PointerPointer pointerPointer);

    public static native int av_packet_unpack_dictionary(@Cast({"const uint8_t*"}) BytePointer bytePointer, int i, @ByPtrPtr AVDictionary aVDictionary);

    public static native int av_packet_unpack_dictionary(@Cast({"const uint8_t*"}) byte[] bArr, int i, @ByPtrPtr AVDictionary aVDictionary);

    public static native void av_packet_unref(AVPacket aVPacket);

    public static native int av_parser_change(AVCodecParserContext aVCodecParserContext, AVCodecContext aVCodecContext, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, int i, int i2);

    public static native int av_parser_change(AVCodecParserContext aVCodecParserContext, AVCodecContext aVCodecContext, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i, int i2);

    public static native int av_parser_change(AVCodecParserContext aVCodecParserContext, AVCodecContext aVCodecContext, @Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer, int i, int i2);

    public static native int av_parser_change(AVCodecParserContext aVCodecParserContext, AVCodecContext aVCodecContext, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @Cast({"const uint8_t*"}) byte[] bArr2, int i, int i2);

    public static native void av_parser_close(AVCodecParserContext aVCodecParserContext);

    public static native AVCodecParserContext av_parser_init(int i);

    public static native AVCodecParser av_parser_next(@Const AVCodecParser aVCodecParser);

    public static native int av_parser_parse2(AVCodecParserContext aVCodecParserContext, AVCodecContext aVCodecContext, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, int i, long j, long j2, long j3);

    public static native int av_parser_parse2(AVCodecParserContext aVCodecParserContext, AVCodecContext aVCodecContext, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i, long j, long j2, long j3);

    public static native int av_parser_parse2(AVCodecParserContext aVCodecParserContext, AVCodecContext aVCodecContext, @Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer, int i, long j, long j2, long j3);

    public static native int av_parser_parse2(AVCodecParserContext aVCodecParserContext, AVCodecContext aVCodecContext, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @Cast({"const uint8_t*"}) byte[] bArr2, int i, long j, long j2, long j3);

    public static native void av_picture_copy(AVPicture aVPicture, @Const AVPicture aVPicture2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native int av_picture_crop(AVPicture aVPicture, @Const AVPicture aVPicture2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native int av_picture_pad(AVPicture aVPicture, @Const AVPicture aVPicture2, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, int i6, int i7, IntBuffer intBuffer);

    public static native int av_picture_pad(AVPicture aVPicture, @Const AVPicture aVPicture2, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, int i6, int i7, IntPointer intPointer);

    public static native int av_picture_pad(AVPicture aVPicture, @Const AVPicture aVPicture2, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int i5, int i6, int i7, int[] iArr);

    public static native void av_rdft_calc(RDFTContext rDFTContext, @Cast({"FFTSample*"}) FloatBuffer floatBuffer);

    public static native void av_rdft_calc(RDFTContext rDFTContext, @Cast({"FFTSample*"}) FloatPointer floatPointer);

    public static native void av_rdft_calc(RDFTContext rDFTContext, @Cast({"FFTSample*"}) float[] fArr);

    public static native void av_rdft_end(RDFTContext rDFTContext);

    public static native RDFTContext av_rdft_init(int i, @Cast({"RDFTransformType"}) int i2);

    public static native void av_register_bitstream_filter(AVBitStreamFilter aVBitStreamFilter);

    public static native void av_register_codec_parser(AVCodecParser aVCodecParser);

    public static native void av_register_hwaccel(AVHWAccel aVHWAccel);

    @Deprecated
    public static native int av_resample(AVResampleContext aVResampleContext, ShortBuffer shortBuffer, ShortBuffer shortBuffer2, IntBuffer intBuffer, int i, int i2, int i3);

    @Deprecated
    public static native int av_resample(AVResampleContext aVResampleContext, ShortPointer shortPointer, ShortPointer shortPointer2, IntPointer intPointer, int i, int i2, int i3);

    @Deprecated
    public static native int av_resample(AVResampleContext aVResampleContext, short[] sArr, short[] sArr2, int[] iArr, int i, int i2, int i3);

    @Deprecated
    public static native void av_resample_close(AVResampleContext aVResampleContext);

    @Deprecated
    public static native void av_resample_compensate(AVResampleContext aVResampleContext, int i, int i2);

    @Deprecated
    public static native AVResampleContext av_resample_init(int i, int i2, int i3, int i4, int i5, double d);

    public static native void av_shrink_packet(AVPacket aVPacket, int i);

    @Cast({"unsigned int"})
    public static native int av_xiphlacing(@Cast({"unsigned char*"}) ByteBuffer byteBuffer, @Cast({"unsigned int"}) int i);

    @Cast({"unsigned int"})
    public static native int av_xiphlacing(@Cast({"unsigned char*"}) BytePointer bytePointer, @Cast({"unsigned int"}) int i);

    @Cast({"unsigned int"})
    public static native int av_xiphlacing(@Cast({"unsigned char*"}) byte[] bArr, @Cast({"unsigned int"}) int i);

    public static native void avcodec_align_dimensions(AVCodecContext aVCodecContext, IntBuffer intBuffer, IntBuffer intBuffer2);

    public static native void avcodec_align_dimensions(AVCodecContext aVCodecContext, IntPointer intPointer, IntPointer intPointer2);

    public static native void avcodec_align_dimensions(AVCodecContext aVCodecContext, int[] iArr, int[] iArr2);

    public static native void avcodec_align_dimensions2(AVCodecContext aVCodecContext, IntBuffer intBuffer, IntBuffer intBuffer2, IntBuffer intBuffer3);

    public static native void avcodec_align_dimensions2(AVCodecContext aVCodecContext, IntPointer intPointer, IntPointer intPointer2, IntPointer intPointer3);

    public static native void avcodec_align_dimensions2(AVCodecContext aVCodecContext, int[] iArr, int[] iArr2, int[] iArr3);

    public static native AVCodecContext avcodec_alloc_context3(@Const AVCodec aVCodec);

    @Deprecated
    public static native AVFrame avcodec_alloc_frame();

    @Cast({"AVChromaLocation"})
    public static native int avcodec_chroma_pos_to_enum(int i, int i2);

    public static native int avcodec_close(AVCodecContext aVCodecContext);

    @Cast({"const char*"})
    public static native BytePointer avcodec_configuration();

    public static native int avcodec_copy_context(AVCodecContext aVCodecContext, @Const AVCodecContext aVCodecContext2);

    @Deprecated
    public static native int avcodec_decode_audio3(AVCodecContext aVCodecContext, ShortBuffer shortBuffer, IntBuffer intBuffer, AVPacket aVPacket);

    @Deprecated
    public static native int avcodec_decode_audio3(AVCodecContext aVCodecContext, ShortPointer shortPointer, IntPointer intPointer, AVPacket aVPacket);

    @Deprecated
    public static native int avcodec_decode_audio3(AVCodecContext aVCodecContext, short[] sArr, int[] iArr, AVPacket aVPacket);

    public static native int avcodec_decode_audio4(AVCodecContext aVCodecContext, AVFrame aVFrame, IntBuffer intBuffer, @Const AVPacket aVPacket);

    public static native int avcodec_decode_audio4(AVCodecContext aVCodecContext, AVFrame aVFrame, IntPointer intPointer, @Const AVPacket aVPacket);

    public static native int avcodec_decode_audio4(AVCodecContext aVCodecContext, AVFrame aVFrame, int[] iArr, @Const AVPacket aVPacket);

    public static native int avcodec_decode_subtitle2(AVCodecContext aVCodecContext, AVSubtitle aVSubtitle, IntBuffer intBuffer, AVPacket aVPacket);

    public static native int avcodec_decode_subtitle2(AVCodecContext aVCodecContext, AVSubtitle aVSubtitle, IntPointer intPointer, AVPacket aVPacket);

    public static native int avcodec_decode_subtitle2(AVCodecContext aVCodecContext, AVSubtitle aVSubtitle, int[] iArr, AVPacket aVPacket);

    public static native int avcodec_decode_video2(AVCodecContext aVCodecContext, AVFrame aVFrame, IntBuffer intBuffer, @Const AVPacket aVPacket);

    public static native int avcodec_decode_video2(AVCodecContext aVCodecContext, AVFrame aVFrame, IntPointer intPointer, @Const AVPacket aVPacket);

    public static native int avcodec_decode_video2(AVCodecContext aVCodecContext, AVFrame aVFrame, int[] iArr, @Const AVPacket aVPacket);

    public static native int avcodec_default_execute(AVCodecContext aVCodecContext, Func_AVCodecContext_Pointer func_AVCodecContext_Pointer, Pointer pointer, IntBuffer intBuffer, int i, int i2);

    public static native int avcodec_default_execute(AVCodecContext aVCodecContext, Func_AVCodecContext_Pointer func_AVCodecContext_Pointer, Pointer pointer, IntPointer intPointer, int i, int i2);

    public static native int avcodec_default_execute(AVCodecContext aVCodecContext, Func_AVCodecContext_Pointer func_AVCodecContext_Pointer, Pointer pointer, int[] iArr, int i, int i2);

    public static native int avcodec_default_execute2(AVCodecContext aVCodecContext, Func_AVCodecContext_Pointer_int_int func_AVCodecContext_Pointer_int_int, Pointer pointer, IntBuffer intBuffer, int i);

    public static native int avcodec_default_execute2(AVCodecContext aVCodecContext, Func_AVCodecContext_Pointer_int_int func_AVCodecContext_Pointer_int_int, Pointer pointer, IntPointer intPointer, int i);

    public static native int avcodec_default_execute2(AVCodecContext aVCodecContext, Func_AVCodecContext_Pointer_int_int func_AVCodecContext_Pointer_int_int, Pointer pointer, int[] iArr, int i);

    @Deprecated
    public static native int avcodec_default_get_buffer(AVCodecContext aVCodecContext, AVFrame aVFrame);

    public static native int avcodec_default_get_buffer2(AVCodecContext aVCodecContext, AVFrame aVFrame, int i);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_default_get_format(AVCodecContext aVCodecContext, @Cast({"const AVPixelFormat*"}) IntBuffer intBuffer);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_default_get_format(AVCodecContext aVCodecContext, @Cast({"const AVPixelFormat*"}) IntPointer intPointer);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_default_get_format(AVCodecContext aVCodecContext, @Cast({"const AVPixelFormat*"}) int[] iArr);

    @Deprecated
    public static native int avcodec_default_reget_buffer(AVCodecContext aVCodecContext, AVFrame aVFrame);

    @Deprecated
    public static native void avcodec_default_release_buffer(AVCodecContext aVCodecContext, AVFrame aVFrame);

    @Const
    public static native AVCodecDescriptor avcodec_descriptor_get(@Cast({"AVCodecID"}) int i);

    @Const
    public static native AVCodecDescriptor avcodec_descriptor_get_by_name(String str);

    @Const
    public static native AVCodecDescriptor avcodec_descriptor_get_by_name(@Cast({"const char*"}) BytePointer bytePointer);

    @Const
    public static native AVCodecDescriptor avcodec_descriptor_next(@Const AVCodecDescriptor aVCodecDescriptor);

    @Deprecated
    public static native int avcodec_encode_audio(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, @Const ShortBuffer shortBuffer);

    @Deprecated
    public static native int avcodec_encode_audio(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) BytePointer bytePointer, int i, @Const ShortPointer shortPointer);

    @Deprecated
    public static native int avcodec_encode_audio(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) byte[] bArr, int i, @Const short[] sArr);

    public static native int avcodec_encode_audio2(AVCodecContext aVCodecContext, AVPacket aVPacket, @Const AVFrame aVFrame, IntBuffer intBuffer);

    public static native int avcodec_encode_audio2(AVCodecContext aVCodecContext, AVPacket aVPacket, @Const AVFrame aVFrame, IntPointer intPointer);

    public static native int avcodec_encode_audio2(AVCodecContext aVCodecContext, AVPacket aVPacket, @Const AVFrame aVFrame, int[] iArr);

    public static native int avcodec_encode_subtitle(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, @Const AVSubtitle aVSubtitle);

    public static native int avcodec_encode_subtitle(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) BytePointer bytePointer, int i, @Const AVSubtitle aVSubtitle);

    public static native int avcodec_encode_subtitle(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) byte[] bArr, int i, @Const AVSubtitle aVSubtitle);

    @Deprecated
    public static native int avcodec_encode_video(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, @Const AVFrame aVFrame);

    @Deprecated
    public static native int avcodec_encode_video(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) BytePointer bytePointer, int i, @Const AVFrame aVFrame);

    @Deprecated
    public static native int avcodec_encode_video(AVCodecContext aVCodecContext, @Cast({"uint8_t*"}) byte[] bArr, int i, @Const AVFrame aVFrame);

    public static native int avcodec_encode_video2(AVCodecContext aVCodecContext, AVPacket aVPacket, @Const AVFrame aVFrame, IntBuffer intBuffer);

    public static native int avcodec_encode_video2(AVCodecContext aVCodecContext, AVPacket aVPacket, @Const AVFrame aVFrame, IntPointer intPointer);

    public static native int avcodec_encode_video2(AVCodecContext aVCodecContext, AVPacket aVPacket, @Const AVFrame aVFrame, int[] iArr);

    public static native int avcodec_enum_to_chroma_pos(IntBuffer intBuffer, IntBuffer intBuffer2, @Cast({"AVChromaLocation"}) int i);

    public static native int avcodec_enum_to_chroma_pos(IntPointer intPointer, IntPointer intPointer2, @Cast({"AVChromaLocation"}) int i);

    public static native int avcodec_enum_to_chroma_pos(int[] iArr, int[] iArr2, @Cast({"AVChromaLocation"}) int i);

    public static native int avcodec_fill_audio_frame(AVFrame aVFrame, int i, @Cast({"AVSampleFormat"}) int i2, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer, int i3, int i4);

    public static native int avcodec_fill_audio_frame(AVFrame aVFrame, int i, @Cast({"AVSampleFormat"}) int i2, @Cast({"const uint8_t*"}) BytePointer bytePointer, int i3, int i4);

    public static native int avcodec_fill_audio_frame(AVFrame aVFrame, int i, @Cast({"AVSampleFormat"}) int i2, @Cast({"const uint8_t*"}) byte[] bArr, int i3, int i4);

    @Deprecated
    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, IntBuffer intBuffer);

    @Deprecated
    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, IntPointer intPointer);

    @Deprecated
    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int[] iArr);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, IntBuffer intBuffer);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, IntPointer intPointer);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_2(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, @Cast({"AVPixelFormat"}) int i3, int i4, int[] iArr);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_list(@Cast({"const AVPixelFormat*"}) IntBuffer intBuffer, @Cast({"AVPixelFormat"}) int i, int i2, IntBuffer intBuffer2);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_list(@Cast({"const AVPixelFormat*"}) IntPointer intPointer, @Cast({"AVPixelFormat"}) int i, int i2, IntPointer intPointer2);

    @Cast({"AVPixelFormat"})
    public static native int avcodec_find_best_pix_fmt_of_list(@Cast({"const AVPixelFormat*"}) int[] iArr, @Cast({"AVPixelFormat"}) int i, int i2, int[] iArr2);

    public static native AVCodec avcodec_find_decoder(@Cast({"AVCodecID"}) int i);

    public static native AVCodec avcodec_find_decoder_by_name(String str);

    public static native AVCodec avcodec_find_decoder_by_name(@Cast({"const char*"}) BytePointer bytePointer);

    public static native AVCodec avcodec_find_encoder(@Cast({"AVCodecID"}) int i);

    public static native AVCodec avcodec_find_encoder_by_name(String str);

    public static native AVCodec avcodec_find_encoder_by_name(@Cast({"const char*"}) BytePointer bytePointer);

    public static native void avcodec_flush_buffers(AVCodecContext aVCodecContext);

    public static native void avcodec_free_context(@Cast({"AVCodecContext**"}) PointerPointer pointerPointer);

    public static native void avcodec_free_context(@ByPtrPtr AVCodecContext aVCodecContext);

    @Deprecated
    public static native void avcodec_free_frame(@Cast({"AVFrame**"}) PointerPointer pointerPointer);

    @Deprecated
    public static native void avcodec_free_frame(@ByPtrPtr AVFrame aVFrame);

    public static native void avcodec_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, IntBuffer intBuffer, IntBuffer intBuffer2);

    public static native void avcodec_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, IntPointer intPointer, IntPointer intPointer2);

    public static native void avcodec_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, int[] iArr, int[] iArr2);

    @Const
    public static native AVClass avcodec_get_class();

    public static native int avcodec_get_context_defaults3(AVCodecContext aVCodecContext, @Const AVCodec aVCodec);

    @Deprecated
    @Cast({"unsigned"})
    public static native int avcodec_get_edge_width();

    @Const
    public static native AVClass avcodec_get_frame_class();

    @Deprecated
    public static native void avcodec_get_frame_defaults(AVFrame aVFrame);

    @Cast({"const char*"})
    public static native BytePointer avcodec_get_name(@Cast({"AVCodecID"}) int i);

    public static native int avcodec_get_pix_fmt_loss(@Cast({"AVPixelFormat"}) int i, @Cast({"AVPixelFormat"}) int i2, int i3);

    @Const
    public static native AVClass avcodec_get_subtitle_rect_class();

    @Cast({"AVMediaType"})
    public static native int avcodec_get_type(@Cast({"AVCodecID"}) int i);

    public static native int avcodec_is_open(AVCodecContext aVCodecContext);

    @Cast({"const char*"})
    public static native BytePointer avcodec_license();

    public static native int avcodec_open2(AVCodecContext aVCodecContext, @Const AVCodec aVCodec, @Cast({"AVDictionary**"}) PointerPointer pointerPointer);

    public static native int avcodec_open2(AVCodecContext aVCodecContext, @Const AVCodec aVCodec, @ByPtrPtr AVDictionary aVDictionary);

    @Cast({"unsigned int"})
    public static native int avcodec_pix_fmt_to_codec_tag(@Cast({"AVPixelFormat"}) int i);

    public static native void avcodec_register(AVCodec aVCodec);

    public static native void avcodec_register_all();

    @Deprecated
    public static native void avcodec_set_dimensions(AVCodecContext aVCodecContext, int i, int i2);

    public static native void avcodec_string(@Cast({"char*"}) ByteBuffer byteBuffer, int i, AVCodecContext aVCodecContext, int i2);

    public static native void avcodec_string(@Cast({"char*"}) BytePointer bytePointer, int i, AVCodecContext aVCodecContext, int i2);

    public static native void avcodec_string(@Cast({"char*"}) byte[] bArr, int i, AVCodecContext aVCodecContext, int i2);

    @Cast({"unsigned"})
    public static native int avcodec_version();

    public static native int avpicture_alloc(AVPicture aVPicture, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    @Deprecated
    public static native int avpicture_deinterlace(AVPicture aVPicture, @Const AVPicture aVPicture2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native int avpicture_fill(AVPicture aVPicture, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native int avpicture_fill(AVPicture aVPicture, @Cast({"const uint8_t*"}) BytePointer bytePointer, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native int avpicture_fill(AVPicture aVPicture, @Cast({"const uint8_t*"}) byte[] bArr, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native void avpicture_free(AVPicture aVPicture);

    public static native int avpicture_get_size(@Cast({"AVPixelFormat"}) int i, int i2, int i3);

    public static native int avpicture_layout(@Const AVPicture aVPicture, @Cast({"AVPixelFormat"}) int i, int i2, int i3, @Cast({"unsigned char*"}) ByteBuffer byteBuffer, int i4);

    public static native int avpicture_layout(@Const AVPicture aVPicture, @Cast({"AVPixelFormat"}) int i, int i2, int i3, @Cast({"unsigned char*"}) BytePointer bytePointer, int i4);

    public static native int avpicture_layout(@Const AVPicture aVPicture, @Cast({"AVPixelFormat"}) int i, int i2, int i3, @Cast({"unsigned char*"}) byte[] bArr, int i4);

    public static native void avsubtitle_free(AVSubtitle aVSubtitle);

    static {
        Loader.load();
    }
}
