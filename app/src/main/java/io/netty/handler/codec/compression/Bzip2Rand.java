package io.netty.handler.codec.compression;

import com.huluxia.framework.base.image.Config;
import com.huluxia.image.pipeline.memory.b;
import com.huluxia.module.h;
import com.huluxia.module.h$a;
import com.huluxia.module.n;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import org.apache.http.HttpStatus;
import org.apache.tools.zip.UnixStat;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avcodec.AVCodecContext;
import org.bytedeco.javacpp.avformat.AVStream;
import org.bytedeco.javacpp.avutil;
import org.mozilla.classfile.ByteCode;

final class Bzip2Rand {
    private static final int[] RNUMS = new int[]{619, 720, 127, 481, 931, h.asx, 813, 233, 566, 247, 985, 724, 205, 454, 863, 491, 741, 242, 949, ErrorCode.COPY_TMPDIR_ERROR, 733, 859, avutil.AV_PIX_FMT_YUV440P12LE, 708, 621, 574, 73, 654, 730, 472, HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE, 436, 278, 496, 867, ErrorCode.ROM_NOT_ENOUGH, 399, 680, 480, 51, 878, 465, 811, 169, 869, 675, h.arj, 697, 867, 561, 862, 687, HttpStatus.SC_INSUFFICIENT_STORAGE, 283, 482, 129, h.asu, 591, 733, 623, 150, 238, 59, 379, 684, 877, h.art, 169, 643, 105, 170, 607, 520, 932, 727, 476, 693, 425, 174, h.arG, 73, 122, avutil.AV_PIX_FMT_YUV440P12LE, 530, 442, 853, 695, 249, 445, 515, 909, h.aqW, 703, 919, 874, 474, 882, 500, 594, h.ark, h.arB, h.aso, ErrorCode.COPY_INSTALL_SUCCESS, 162, 819, 984, 589, 513, 495, 799, 161, 604, 958, 533, ErrorCode.INCRUPDATE_INSTALL_SUCCESS, 400, 386, 867, h.arp, h$a.asX, 382, 596, HttpStatus.SC_REQUEST_URI_TOO_LONG, 171, 516, 375, 682, 485, 911, 276, 98, h.ard, 163, 354, 666, 933, HttpStatus.SC_FAILED_DEPENDENCY, 341, 533, 870, 227, 730, 475, avcodec.AV_CODEC_ID_HQX, 263, h.arG, h.aqO, 686, h.arp, 224, 469, 68, 770, 919, ByteCode.ARRAYLENGTH, AVStream.MAX_STD_TIMEBASES, 294, 822, h.asv, 206, 184, 943, h$a.atk, b.HD, 383, 461, 404, 758, 839, 887, 715, 67, 618, 276, 204, 918, 873, 777, 604, 560, 951, 160, AVCodecContext.FF_PROFILE_H264_CONSTRAINED_BASELINE, 722, 79, h.asr, 96, HttpStatus.SC_CONFLICT, 713, 940, 652, 934, 970, 447, 318, 353, 859, 672, 112, 785, h.arE, 863, h.asq, 350, 139, 93, 354, 99, 820, 908, h.arh, 772, 154, d.xI, 580, 184, 79, h.aru, h.ary, 742, 653, 282, 762, 623, 680, 81, 927, h.aru, 789, 125, HttpStatus.SC_LENGTH_REQUIRED, 521, 938, 300, 821, 78, 343, 175, 128, Config.DEFAULT_FADE_DURATION, 170, 774, 972, 275, 999, 639, 495, 78, 352, 126, 857, 956, 358, 619, 580, 124, 737, 594, 701, h.ark, 669, 112, 134, 694, 363, 992, h.asw, 743, 168, 974, 944, 375, 748, 52, h.arp, 747, h.arC, 182, 862, 81, 344, h.ass, 988, 739, UnixStat.DEFAULT_LINK_PERM, 655, 814, avutil.AV_PIX_FMT_YUV440P10BE, 249, 515, 897, 955, h.arR, 981, h.arI, 113, 974, 459, 893, 228, 433, 837, h.ard, 268, 926, com.huluxia.video.recorder.b.bpd, 102, 654, 459, 51, 686, 754, h.ast, 760, UnixStat.DEFAULT_DIR_PERM, 403, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, 394, 687, 700, 946, 670, h.arJ, h.ari, 738, 392, 760, 799, 887, 653, 978, 321, 576, h.arm, h.aru, HttpStatus.SC_BAD_GATEWAY, 894, 679, 243, 440, 680, 879, ByteCode.MONITORENTER, 572, h.arA, 724, 926, 56, 204, 700, 707, 151, 457, 449, h$a.atm, ByteCode.MONITOREXIT, 791, 558, 945, 679, 297, 59, 87, 824, 713, h.arQ, HttpStatus.SC_PRECONDITION_FAILED, 693, 342, 606, 134, 108, 571, 364, h.arz, ErrorCode.COPY_FAIL, 174, 643, 304, 329, 343, 97, 430, 751, 497, 314, 983, 374, 822, 928, 140, 206, 73, 263, 980, 736, 876, 478, 430, 305, 170, 514, 364, 692, 829, 82, 855, 953, 676, 246, 369, 970, 294, 750, h.asu, 827, 150, 790, n.avY, 923, h.asr, 378, 215, 828, 592, 281, 565, 555, 710, 82, 896, 831, h.aqX, 261, h.aqC, 462, 293, 465, HttpStatus.SC_BAD_GATEWAY, 56, h.arO, 821, 976, 991, h.arL, 869, 905, 758, 745, ByteCode.INSTANCEOF, 768, h.ara, h.arg, 933, 378, 286, 215, 979, 792, 961, 61, 688, 793, h.arD, 986, 403, 106, 366, 905, h.arD, 372, 567, 466, 434, h.arE, ErrorCode.ROM_NOT_ENOUGH, 389, h.ara, 919, 135, h$a.asV, 773, 635, 389, 707, 100, h.aru, 958, 165, 504, 920, 176, ByteCode.INSTANCEOF, 713, 857, 265, 203, 50, 668, 108, h.arE, 990, h.aru, ByteCode.MULTIANEWARRAY, 510, 357, 358, 850, 858, 364, 936, 638};

    static int rNums(int i) {
        return RNUMS[i];
    }

    private Bzip2Rand() {
    }
}
