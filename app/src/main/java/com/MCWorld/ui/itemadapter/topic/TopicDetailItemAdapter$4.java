package com.MCWorld.ui.itemadapter.topic;

import android.widget.Toast;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.video.views.scalable.b;
import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

class TopicDetailItemAdapter$4 implements Runnable {
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$4(TopicDetailItemAdapter this$0) {
        this.aXA = this$0;
    }

    public void run() {
        Exception e;
        Throwable th;
        HLog.debug("TopicDetailItemAdapter", "begin video bitmap ", new Object[0]);
        FFmpegFrameGrabber grabber = null;
        try {
            FFmpegFrameGrabber grabber2 = new FFmpegFrameGrabber(TopicDetailItemAdapter.j(this.aXA));
            try {
                grabber2.start();
                long mProgressR = grabber2.getLengthInTime();
                TopicDetailItemAdapter.a(this.aXA, new b(grabber2.getImageWidth(), grabber2.getImageHeight()));
                grabber2.setFrameNumber(5);
                Frame frame = grabber2.grabKeyFrame();
                TopicDetailItemAdapter.a(this.aXA, new AndroidFrameConverter().convert(frame));
                TopicDetailItemAdapter.a(this.aXA).runOnUiThread(new Runnable(this) {
                    final /* synthetic */ TopicDetailItemAdapter$4 aXB;

                    {
                        this.aXB = this$1;
                    }

                    public void run() {
                        if (TopicDetailItemAdapter.g(this.aXB.aXA) != null) {
                            TopicDetailItemAdapter.f(this.aXB.aXA).setImageBitmap(TopicDetailItemAdapter.g(this.aXB.aXA));
                        }
                        TopicDetailItemAdapter.k(this.aXB.aXA);
                        TopicDetailItemAdapter.a(this.aXB.aXA, true);
                    }
                });
                if (grabber2 != null) {
                    try {
                        grabber2.stop();
                        grabber2.release();
                    } catch (Exception e2) {
                        HLog.warn("TopicDetailItemAdapter", "edit video stop grab frames", new Object[]{e2});
                        grabber = grabber2;
                        return;
                    }
                }
                grabber = grabber2;
            } catch (Exception e3) {
                e = e3;
                grabber = grabber2;
                try {
                    HLog.error("TopicDetailItemAdapter", "edit video grab frames", e, new Object[0]);
                    TopicDetailItemAdapter.a(this.aXA).runOnUiThread(new Runnable(this) {
                        final /* synthetic */ TopicDetailItemAdapter$4 aXB;

                        {
                            this.aXB = this$1;
                        }

                        public void run() {
                            Toast.makeText(TopicDetailItemAdapter.a(this.aXB.aXA), "剪辑视频出错，可能不支持当前视频的格式剪辑", 0).show();
                        }
                    });
                    if (grabber != null) {
                        try {
                            grabber.stop();
                            grabber.release();
                        } catch (Exception e22) {
                            HLog.warn("TopicDetailItemAdapter", "edit video stop grab frames", new Object[]{e22});
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (grabber != null) {
                        try {
                            grabber.stop();
                            grabber.release();
                        } catch (Exception e222) {
                            HLog.warn("TopicDetailItemAdapter", "edit video stop grab frames", new Object[]{e222});
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                grabber = grabber2;
                if (grabber != null) {
                    grabber.stop();
                    grabber.release();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            HLog.error("TopicDetailItemAdapter", "edit video grab frames", e, new Object[0]);
            TopicDetailItemAdapter.a(this.aXA).runOnUiThread(/* anonymous class already generated */);
            if (grabber != null) {
                grabber.stop();
                grabber.release();
            }
        }
    }
}
