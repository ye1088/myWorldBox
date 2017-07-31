package org.bytedeco.javacpp.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit = {avutil.class}, target = "org.bytedeco.javacpp.swscale", value = {@Platform(cinclude = {"<libswscale/swscale.h>"}, link = {"swscale@.3"}), @Platform(preload = {"swscale-3"}, value = {"windows"})})
public class swscale implements InfoMapper {
    public void map(InfoMap infoMap) {
    }
}
