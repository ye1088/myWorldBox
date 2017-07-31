package com.huluxia.framework.base.http.deliver;

import android.os.Handler;
import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.io.Response;
import com.huluxia.framework.base.http.toolbox.error.CancelError;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import java.util.concurrent.Executor;

public class ExecutorDelivery implements ResponseDelivery {
    private final Executor mResponsePoster;

    private class ResponseDeliveryRunnable implements Runnable {
        private final Request mRequest;
        private final Response mResponse;
        private final Runnable mRunnable;

        public ResponseDeliveryRunnable(Request request, Response response, Runnable runnable) {
            this.mRequest = request;
            this.mResponse = response;
            this.mRunnable = runnable;
        }

        public void run() {
            if (this.mRequest.isCanceled()) {
                this.mRequest.deliverCancel(this.mResponse.error);
                this.mRequest.finish("canceled-at-delivery");
                return;
            }
            if (this.mResponse.isSuccess()) {
                this.mRequest.deliverResponse(this.mResponse.result);
            } else {
                this.mRequest.deliverError(this.mResponse.error);
            }
            if (this.mResponse.intermediate) {
                this.mRequest.addMarker("intermediate-response");
            } else {
                this.mRequest.finish(this.mResponse.isSuccess() + "-done");
            }
            if (this.mRunnable != null) {
                this.mRunnable.run();
            }
        }
    }

    public ExecutorDelivery(final Handler handler) {
        this.mResponsePoster = new Executor() {
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    public ExecutorDelivery(Executor executor) {
        this.mResponsePoster = executor;
    }

    public void postResponse(Request<?> request, Response<?> response) {
        postResponse(request, response, null);
    }

    public void postResponse(Request<?> request, Response<?> response, Runnable runnable) {
        request.markDelivered();
        request.addMarker("post-response");
        this.mResponsePoster.execute(new ResponseDeliveryRunnable(request, response, runnable));
    }

    public void postError(Request<?> request, VolleyError error) {
        request.addMarker("post-error");
        this.mResponsePoster.execute(new ResponseDeliveryRunnable(request, Response.error(error), null));
    }

    public void postCancel(Request<?> request, CancelError error) {
        request.addMarker("post-cancel");
        this.mResponsePoster.execute(new ResponseDeliveryRunnable(request, Response.error(error), null));
    }
}
