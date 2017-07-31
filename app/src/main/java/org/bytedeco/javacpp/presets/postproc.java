package org.bytedeco.javacpp.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(inherit = {avutil.class}, target = "org.bytedeco.javacpp.postproc", value = {@Platform(cinclude = {"<libpostproc/postprocess.h>"}, link = {"postproc@.53"}), @Platform(preload = {"postproc-53"}, value = {"windows"})})
public class postproc implements InfoMapper {
    public void map(InfoMap infoMap) {
        infoMap.put(new Info(new String[]{"QP_STORE_T"}).cppTypes(new String[0]).valueTypes(new String[]{"byte"}).pointerTypes(new String[]{"BytePointer"})).put(new Info(new String[]{"LIBPOSTPROC_VERSION_INT < (52<<16)"}).define(false));
    }
}
