package org.bytedeco.javacpp.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit = {avutil.class}, target = "org.bytedeco.javacpp.swresample", value = {@Platform(cinclude = {"<libswresample/swresample.h>"}, link = {"swresample@.1"}), @Platform(preload = {"swresample-1"}, value = {"windows"})})
public class swresample implements InfoMapper {
    public void map(InfoMap infoMap) {
    }
}
