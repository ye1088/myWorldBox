package com.MCWorld.image.core.datasource;

import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Supplier;
import java.util.List;
import javax.annotation.concurrent.ThreadSafe;
import org.apache.tools.ant.taskdefs.optional.j2ee.HotDeploymentTool;

@ThreadSafe
/* compiled from: IncreasingQualityDataSourceSupplier */
public class g<T> implements Supplier<c<T>> {
    private final List<Supplier<c<T>>> zx;

    private g(List<Supplier<c<T>>> dataSourceSuppliers) {
        Preconditions.checkArgument(!dataSourceSuppliers.isEmpty(), "List of suppliers is empty!");
        this.zx = dataSourceSuppliers;
    }

    public static <T> g<T> l(List<Supplier<c<T>>> dataSourceSuppliers) {
        return new g(dataSourceSuppliers);
    }

    public c<T> get() {
        return new a(this);
    }

    public int hashCode() {
        return this.zx.hashCode();
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof g)) {
            return false;
        }
        return Objects.equal(this.zx, ((g) other).zx);
    }

    public String toString() {
        return Objects.toStringHelper(this).add(HotDeploymentTool.ACTION_LIST, this.zx).toString();
    }
}
