package com.huluxia.image.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import com.huluxia.image.drawee.generic.a;
import com.huluxia.image.drawee.generic.b;
import com.huluxia.image.drawee.generic.c;
import javax.annotation.Nullable;

public class GenericDraweeView extends DraweeView<a> {
    public GenericDraweeView(Context context, a hierarchy) {
        super(context);
        setHierarchy(hierarchy);
    }

    public GenericDraweeView(Context context) {
        super(context);
        inflateHierarchy(context, null);
    }

    public GenericDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateHierarchy(context, attrs);
    }

    public GenericDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflateHierarchy(context, attrs);
    }

    @TargetApi(21)
    public GenericDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateHierarchy(context, attrs);
    }

    protected void inflateHierarchy(Context context, @Nullable AttributeSet attrs) {
        b builder = c.b(context, attrs);
        setAspectRatio(builder.kD());
        setHierarchy(builder.kQ());
    }
}
