package com.MCWorld.image.drawee.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import com.MCWorld.image.drawee.drawable.o.b;
import com.MCWorld.image.drawee.generic.a;

@TargetApi(19)
/* compiled from: DraweeTransition */
public class c extends Transition {
    private static final String DP = "draweeTransition:bounds";
    private final com.MCWorld.image.drawee.drawable.o.c DQ;
    private final com.MCWorld.image.drawee.drawable.o.c DR;

    public static TransitionSet a(com.MCWorld.image.drawee.drawable.o.c fromScale, com.MCWorld.image.drawee.drawable.o.c toScale) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new c(fromScale, toScale));
        return transitionSet;
    }

    public c(com.MCWorld.image.drawee.drawable.o.c fromScale, com.MCWorld.image.drawee.drawable.o.c toScale) {
        this.DQ = fromScale;
        this.DR = toScale;
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        Rect startBounds = (Rect) startValues.values.get(DP);
        Rect endBounds = (Rect) endValues.values.get(DP);
        if (startBounds == null || endBounds == null || this.DQ == this.DR) {
            return null;
        }
        final GenericDraweeView draweeView = startValues.view;
        final b scaleType = new b(this.DQ, this.DR, startBounds, endBounds);
        ((a) draweeView.getHierarchy()).b(scaleType);
        Animator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ c DU;

            public void onAnimationUpdate(ValueAnimator animation) {
                scaleType.h(((Float) animation.getAnimatedValue()).floatValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter(this) {
            final /* synthetic */ c DU;

            public void onAnimationEnd(Animator animation) {
                ((a) draweeView.getHierarchy()).b(this.DU.DR);
            }
        });
        return animator;
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof GenericDraweeView) {
            transitionValues.values.put(DP, new Rect(0, 0, transitionValues.view.getWidth(), transitionValues.view.getHeight()));
        }
    }
}
