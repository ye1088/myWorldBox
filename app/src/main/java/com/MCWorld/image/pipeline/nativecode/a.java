package com.MCWorld.image.pipeline.nativecode;

import com.MCWorld.framework.base.utils.soloader.SoLoaderShim;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: ImagePipelineNativeLoader */
public class a {
    public static final String IJ = "imagepipeline";
    public static final List<String> IK = Collections.unmodifiableList(new ArrayList());

    public static void load() {
        SoLoaderShim.loadLibrary(IJ);
    }
}
