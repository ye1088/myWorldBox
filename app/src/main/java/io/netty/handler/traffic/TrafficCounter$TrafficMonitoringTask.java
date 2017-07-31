package io.netty.handler.traffic;

import java.util.concurrent.TimeUnit;

final class TrafficCounter$TrafficMonitoringTask implements Runnable {
    final /* synthetic */ TrafficCounter this$0;

    private TrafficCounter$TrafficMonitoringTask(TrafficCounter trafficCounter) {
        this.this$0 = trafficCounter;
    }

    public void run() {
        if (this.this$0.monitorActive) {
            this.this$0.resetAccounting(TrafficCounter.milliSecondFromNano());
            if (this.this$0.trafficShapingHandler != null) {
                this.this$0.trafficShapingHandler.doAccounting(this.this$0);
            }
            this.this$0.scheduledFuture = this.this$0.executor.schedule(this, this.this$0.checkInterval.get(), TimeUnit.MILLISECONDS);
        }
    }
}
