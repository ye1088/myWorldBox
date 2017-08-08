package com.MCWorld.image.pipeline.core;

import bolts.g;
import com.MCWorld.image.core.datasource.h;

/* compiled from: ImagePipeline */
class e$4 implements g<Boolean, Void> {
    final /* synthetic */ e Fq;
    final /* synthetic */ h Fr;

    e$4(e this$0, h hVar) {
        this.Fq = this$0;
        this.Fr = hVar;
    }

    public /* synthetic */ Object a(bolts.h hVar) throws Exception {
        return b(hVar);
    }

    public Void b(bolts.h<Boolean> task) throws Exception {
        h hVar = this.Fr;
        boolean z = (task.isCancelled() || task.ba() || !((Boolean) task.getResult()).booleanValue()) ? false : true;
        hVar.u(Boolean.valueOf(z));
        return null;
    }
}
