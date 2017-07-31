package org.bytedeco.javacpp.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit = {swresample.class}, target = "org.bytedeco.javacpp.avcodec", value = {@Platform(cinclude = {"<libavcodec/avcodec.h>", "<libavcodec/avfft.h>"}, link = {"avcodec@.56"}), @Platform(preload = {"avcodec-56"}, value = {"windows"})})
public class avcodec implements InfoMapper {
    public void map(InfoMap infoMap) {
        infoMap.put(new Info(new String[]{"!FF_API_LOWRES", "!FF_API_DEBUG_MV"}).define(false)).putFirst(new Info(new String[]{"AVPanScan"}).pointerTypes(new String[]{"AVPanScan"})).putFirst(new Info(new String[]{"AVCodecContext"}).pointerTypes(new String[]{"AVCodecContext"}));
    }
}
