package com.MCWorld.image.pipeline.memory;

import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.base.imagepipeline.memory.a;
import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.base.imagepipeline.memory.g;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
/* compiled from: PoolFactory */
public class s {
    private g ED;
    private d El;
    private d Eo;
    private j Er;
    private final r Ik;
    private l Il;
    private v Im;
    private a In;

    public s(r config) {
        this.Ik = (r) Preconditions.checkNotNull(config);
    }

    public d op() {
        if (this.El == null) {
            this.El = new d(this.Ik.mc(), this.Ik.og(), this.Ik.oh());
        }
        return this.El;
    }

    public j oq() {
        if (this.Er == null) {
            this.Er = new j(this.Ik.mc(), this.Ik.ok());
        }
        return this.Er;
    }

    public int os() {
        return this.Ik.ok().Iu;
    }

    public l ot() {
        if (this.Il == null) {
            this.Il = new l(this.Ik.mc(), this.Ik.oi(), this.Ik.oj());
        }
        return this.Il;
    }

    public d ou() {
        if (this.Eo == null) {
            this.Eo = new n(ot(), ov());
        }
        return this.Eo;
    }

    public g ov() {
        if (this.ED == null) {
            this.ED = new g(ox());
        }
        return this.ED;
    }

    public v ow() {
        if (this.Im == null) {
            this.Im = new v(this.Ik.mc(), this.Ik.ok());
        }
        return this.Im;
    }

    public a ox() {
        if (this.In == null) {
            this.In = new k(this.Ik.mc(), this.Ik.ol(), this.Ik.om());
        }
        return this.In;
    }
}
