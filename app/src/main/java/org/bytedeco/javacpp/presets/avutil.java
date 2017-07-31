package org.bytedeco.javacpp.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(target = "org.bytedeco.javacpp.avutil", value = {@Platform(cinclude = {"<libavutil/avutil.h>", "<libavutil/error.h>", "<libavutil/mem.h>", "<libavutil/mathematics.h>", "<libavutil/rational.h>", "<libavutil/log.h>", "<libavutil/buffer.h>", "<libavutil/pixfmt.h>", "<libavutil/frame.h>", "<libavutil/samplefmt.h>", "<libavutil/channel_layout.h>", "<libavutil/cpu.h>", "<libavutil/dict.h>", "<libavutil/opt.h>", "<libavutil/audioconvert.h>", "<libavutil/pixdesc.h>", "<libavutil/imgutils.h>", "<libavutil/downmix_info.h>", "<libavutil/stereo3d.h>", "<libavutil/ffversion.h>"}, compiler = {"default", "nodeprecated"}, define = {"__STDC_CONSTANT_MACROS"}, includepath = {"/usr/local/include/ffmpeg/", "/opt/local/include/ffmpeg/", "/usr/include/ffmpeg/"}, link = {"avutil@.54"}), @Platform(includepath = {"C:/MinGW/local/include/ffmpeg/", "C:/MinGW/include/ffmpeg/"}, preload = {"avutil-54"}, value = {"windows"})})
public class avutil implements InfoMapper {
    public void map(InfoMap infoMap) {
        infoMap.put(new Info(new String[]{"AV_NOPTS_VALUE"}).cppTypes(new String[]{"int64_t"}).translate(false)).put(new Info(new String[]{"AV_TIME_BASE_Q", "PixelFormat", "CodecID"}).cppTypes(new String[0])).put(new Info(new String[]{"av_const"}).annotations(new String[]{"@Const"})).put(new Info(new String[]{"FF_CONST_AVUTIL55"}).annotations(new String[0])).put(new Info(new String[]{"av_malloc_attrib", "av_alloc_size", "av_always_inline"}).cppTypes(new String[0]).annotations(new String[0])).put(new Info(new String[]{"attribute_deprecated"}).annotations(new String[]{"@Deprecated"})).put(new Info(new String[]{"AVPanScan", "AVCodecContext"}).cast().pointerTypes(new String[]{"Pointer"})).put(new Info(new String[]{"AV_PIX_FMT_ABI_GIT_MASTER", "AV_HAVE_INCOMPATIBLE_LIBAV_ABI", "!FF_API_XVMC", "FF_API_GET_BITS_PER_SAMPLE_FMT", "FF_API_FIND_OPT"}).define(false)).put(new Info(new String[]{"AV_PIX_FMT_Y400A", "ff_check_pixfmt_descriptors"}).skip());
    }
}
