package org.bytedeco.javacpp.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit = {avformat.class, postproc.class, swresample.class, swscale.class}, target = "org.bytedeco.javacpp.avfilter", value = {@Platform(cinclude = {"<libavfilter/avfilter.h>", "<libavfilter/buffersink.h>", "<libavfilter/buffersrc.h>"}, link = {"avfilter@.5"}), @Platform(preload = {"avfilter-5"}, value = {"windows"})})
public class avfilter implements InfoMapper {
    public void map(InfoMap infoMap) {
        infoMap.put(new Info(new String[]{"AVFilterPool", "AVFilterCommand", "AVFilterChannelLayouts"}).cast().pointerTypes(new String[]{"Pointer"})).put(new Info(new String[]{"AV_HAVE_INCOMPATIBLE_LIBAV_ABI || !FF_API_OLD_GRAPH_PARSE"}).define(true)).put(new Info(new String[]{"!FF_API_FOO_COUNT"}).define(false));
    }
}
