package com.MCWorld.framework.base.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.image.base.imagepipeline.common.d;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.core.datasource.c;
import com.MCWorld.image.core.datasource.g;
import com.MCWorld.image.drawee.controller.AbstractDraweeControllerBuilder$CacheLevel;
import com.MCWorld.image.drawee.drawable.o;
import com.MCWorld.image.drawee.generic.e;
import com.MCWorld.image.pipeline.core.h;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.MCWorld.image.pipeline.request.ImageRequestBuilder;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.List;

public class PipelineView extends ImageView {
    private static final String TAG = "PipelineView";
    private Callback mCallback;
    private Config mConfig;
    private c<a<b>> mDataSource;
    private a<b> mImage;
    private Uri mLowResolutionUri;
    private d mOptions;
    private d mThumbOptions;
    private Uri mUri;

    public interface Callback {
        void onFailed();

        void onProgressUpdate(float f);

        void onSucc(Bitmap bitmap);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        Config config;
        String lowResUri;
        String uri;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.uri = in.readString();
            this.lowResUri = in.readString();
            this.config = (Config) in.readParcelable(Config.class.getClassLoader());
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.uri);
            dest.writeString(this.lowResUri);
            dest.writeParcelable(this.config, 0);
        }
    }

    public PipelineView(Context context) {
        super(context);
    }

    public PipelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PipelineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public PipelineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttach();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDetach();
    }

    public void setUri(Uri uri, Config config, Callback callabck) {
        setUri(uri, null, null, null, config, callabck);
    }

    public void setUri(Uri uri, Uri lowResUri, d options, d thumbOptions, Config config, Callback callabck) {
        this.mUri = uri;
        this.mLowResolutionUri = lowResUri;
        this.mConfig = config;
        this.mCallback = callabck;
        this.mOptions = options;
        this.mThumbOptions = thumbOptions;
        onAttach();
    }

    private boolean hasLowResolutionReq() {
        return this.mLowResolutionUri != null && UtilsFunction.size(this.mLowResolutionUri.toString()) > 0;
    }

    private void onAttach() {
        if (this.mUri != null && this.mConfig != null) {
            ImageRequestBuilder builder = ImageRequestBuilder.w(this.mUri).a(this.mOptions);
            builder.b(com.MCWorld.image.base.imagepipeline.common.a.hs().O(this.mConfig.highQualityAnimated).hz());
            closeDataSource();
            Supplier<c<a<b>>> supplier = getDataSourceSupplierForRequest(builder.pM());
            if (supplier != null && hasLowResolutionReq()) {
                builder = ImageRequestBuilder.w(this.mLowResolutionUri).a(this.mThumbOptions);
                List<Supplier<c<a<b>>>> suppliers = new ArrayList(2);
                suppliers.add(supplier);
                suppliers.add(getDataSourceSupplierForRequest(builder.pM()));
                supplier = g.l(suppliers);
            }
            this.mDataSource = (c) supplier.get();
            this.mDataSource.a(new com.MCWorld.image.core.datasource.b<a<b>>() {
                protected void onNewResultImpl(c<a<b>> dataSource) {
                    PipelineView.this.closeImage();
                    PipelineView.this.mImage = (a) dataSource.getResult();
                    if (PipelineView.this.mImage == null || PipelineView.this.mImage.get() == null) {
                        if (dataSource.isFinished() && PipelineView.this.mCallback != null) {
                            PipelineView.this.mCallback.onFailed();
                        }
                    } else if (PipelineView.this.mImage.get() instanceof com.MCWorld.image.base.imagepipeline.image.a) {
                        PipelineView.this.setImageBitmap(((com.MCWorld.image.base.imagepipeline.image.a) PipelineView.this.mImage.get()).hM());
                        if (dataSource.isFinished() && PipelineView.this.mCallback != null) {
                            PipelineView.this.mCallback.onSucc(((com.MCWorld.image.base.imagepipeline.image.a) PipelineView.this.mImage.get()).hM());
                        }
                    } else if (h.mz().mA() != null) {
                        Drawable drawable = h.mz().mA().aE(AppConfig.getInstance().getAppContext()).a((b) PipelineView.this.mImage.get());
                        PipelineView.this.setImageDrawable(e.j(drawable, o.a(PipelineView.this.getScaleType())));
                        PipelineView.this.setScaleType(ScaleType.FIT_CENTER);
                        if ((drawable instanceof Animatable) && PipelineView.this.mConfig.autoAnimated) {
                            ((Animatable) drawable).start();
                        }
                        if (dataSource.isFinished() && PipelineView.this.mCallback != null) {
                            PipelineView.this.mCallback.onSucc(null);
                        }
                    }
                }

                protected void onFailureImpl(c<a<b>> dataSource) {
                    if (dataSource.iO()) {
                        PipelineView.this.setImageResource(PipelineView.this.mConfig.errorHolder);
                        if (PipelineView.this.mCallback != null) {
                            PipelineView.this.mCallback.onFailed();
                        }
                    }
                }

                public void onProgressUpdate(c<a<b>> dataSource) {
                    if (PipelineView.this.mCallback != null) {
                        PipelineView.this.mCallback.onProgressUpdate(dataSource.getProgress());
                    }
                }
            }, com.MCWorld.image.core.common.executors.g.ir());
        }
    }

    private Supplier<c<a<b>>> getDataSourceSupplierForRequest(ImageRequest imageRequest) {
        return getDataSourceSupplierForRequest(imageRequest, AbstractDraweeControllerBuilder$CacheLevel.FULL_FETCH);
    }

    private Supplier<c<a<b>>> getDataSourceSupplierForRequest(final ImageRequest imageRequest, final AbstractDraweeControllerBuilder$CacheLevel cacheLevel) {
        return new Supplier<c<a<b>>>() {
            public c<a<b>> get() {
                return h.mz().lj().b(imageRequest, null, com.MCWorld.image.fresco.e.a(cacheLevel));
            }

            public String toString() {
                return Objects.toStringHelper(this).add(SocialConstants.TYPE_REQUEST, imageRequest.toString()).toString();
            }
        };
    }

    private void onDetach() {
        closeDataSource();
        closeImage();
    }

    private void closeDataSource() {
        if (this.mDataSource != null) {
            this.mDataSource.close();
            this.mDataSource = null;
        }
    }

    private void closeImage() {
        if (this.mImage != null) {
            this.mImage.close();
            this.mImage = null;
        }
    }

    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null || !(getDrawable() instanceof BitmapDrawable)) {
            super.onDraw(canvas);
            return;
        }
        Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        if (bitmap == null || !bitmap.isRecycled()) {
            super.onDraw(canvas);
        } else {
            HLog.warn(TAG, "not draw when bitmap is recycled, image " + this.mImage, new Object[0]);
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        state.config = this.mConfig;
        state.uri = this.mUri != null ? this.mUri.toString() : "";
        state.lowResUri = this.mLowResolutionUri != null ? this.mLowResolutionUri.toString() : "";
        return state;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mConfig = savedState.config;
        this.mUri = UtilUri.parseUriOrNull(savedState.uri);
        this.mLowResolutionUri = UtilUri.parseUriOrNull(savedState.lowResUri);
    }
}
