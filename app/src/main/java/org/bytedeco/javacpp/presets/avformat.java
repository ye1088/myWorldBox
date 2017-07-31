package org.bytedeco.javacpp.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit = {avcodec.class}, target = "org.bytedeco.javacpp.avformat", value = {@Platform(cinclude = {"<libavformat/avio.h>", "<libavformat/avformat.h>"}, link = {"avformat@.56"}), @Platform(preload = {"avformat-56"}, value = {"windows"})})
public class avformat implements InfoMapper {
    public void map(InfoMap infoMap) {
        infoMap.put(new Info(new String[]{"URLContext"}).cast().pointerTypes(new String[]{"Pointer"})).put(new Info(new String[]{"AVPROBE_SCORE_RETRY", "AVPROBE_SCORE_STREAM_RETRY"}).translate(false)).put(new Info(new String[]{"LIBAVFORMAT_VERSION_MAJOR <= 54", "FF_API_ALLOC_OUTPUT_CONTEXT", "FF_API_FORMAT_PARAMETERS", "FF_API_READ_PACKET", "FF_API_CLOSE_INPUT_FILE", "FF_API_NEW_STREAM", "FF_API_SET_PTS_INFO"}).define(false));
    }
}
