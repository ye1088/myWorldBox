package com.MCWorld.image.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.image.drawee.generic.a;
import com.MCWorld.image.drawee.interfaces.d;
import com.MCWorld.q.j;
import javax.annotation.Nullable;

public class SimpleDraweeView extends GenericDraweeView {
    private static Supplier<? extends d> sDraweeControllerBuilderSupplier;
    private d mSimpleDraweeControllerBuilder;

    public static void initialize(Supplier<? extends d> draweeControllerBuilderSupplier) {
        sDraweeControllerBuilderSupplier = draweeControllerBuilderSupplier;
    }

    public static void shutDown() {
        sDraweeControllerBuilderSupplier = null;
    }

    public SimpleDraweeView(Context context, a hierarchy) {
        super(context, hierarchy);
        init(context, null);
    }

    public SimpleDraweeView(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @TargetApi(21)
    public SimpleDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (!isInEditMode()) {
            Preconditions.checkNotNull(sDraweeControllerBuilderSupplier, "SimpleDraweeView was not initialized!");
            this.mSimpleDraweeControllerBuilder = (d) sDraweeControllerBuilderSupplier.get();
            if (attrs != null) {
                TypedArray gdhAttrs = context.obtainStyledAttributes(attrs, j.SimpleDraweeView);
                try {
                    if (gdhAttrs.hasValue(j.SimpleDraweeView_actualImageUri)) {
                        setImageURI(Uri.parse(gdhAttrs.getString(j.SimpleDraweeView_actualImageUri)), null);
                    }
                    gdhAttrs.recycle();
                } catch (Throwable th) {
                    gdhAttrs.recycle();
                }
            }
        }
    }

    protected d getControllerBuilder() {
        return this.mSimpleDraweeControllerBuilder;
    }

    public void setImageURI(Uri uri) {
        setImageURI(uri, null);
    }

    public void setImageURI(@Nullable String uriString) {
        setImageURI(uriString, null);
    }

    public void setImageURI(Uri uri, @Nullable Object callerContext) {
        setController(this.mSimpleDraweeControllerBuilder.G(callerContext).i(uri).b(getController()).jH());
    }

    public void setImageURI(@Nullable String uriString, @Nullable Object callerContext) {
        setImageURI(uriString != null ? Uri.parse(uriString) : null, callerContext);
    }
}
