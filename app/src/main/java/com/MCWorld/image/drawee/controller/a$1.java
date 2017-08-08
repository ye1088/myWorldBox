package com.MCWorld.image.drawee.controller;

import com.MCWorld.image.core.datasource.b;
import com.MCWorld.image.core.datasource.c;

/* compiled from: AbstractDraweeController */
class a$1 extends b<T> {
    final /* synthetic */ boolean Ah;
    final /* synthetic */ a Ai;
    final /* synthetic */ String val$id;

    a$1(a this$0, String str, boolean z) {
        this.Ai = this$0;
        this.val$id = str;
        this.Ah = z;
    }

    public void onNewResultImpl(c<T> dataSource) {
        boolean isFinished = dataSource.isFinished();
        float progress = dataSource.getProgress();
        T image = dataSource.getResult();
        if (image != null) {
            a.a(this.Ai, this.val$id, dataSource, image, progress, isFinished, this.Ah);
        } else if (isFinished) {
            a.a(this.Ai, this.val$id, dataSource, new NullPointerException(), true);
        }
    }

    public void onFailureImpl(c<T> dataSource) {
        a.a(this.Ai, this.val$id, dataSource, dataSource.iP(), true);
    }

    public void onProgressUpdate(c<T> dataSource) {
        boolean isFinished = dataSource.isFinished();
        a.a(this.Ai, this.val$id, dataSource, dataSource.getProgress(), isFinished);
    }
}
