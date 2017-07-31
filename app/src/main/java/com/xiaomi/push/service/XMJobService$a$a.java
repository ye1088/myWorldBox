package com.xiaomi.push.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.timers.a;

class XMJobService$a$a extends Handler {
    JobService a;

    XMJobService$a$a(JobService jobService) {
        super(jobService.getMainLooper());
        this.a = jobService;
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                JobParameters jobParameters = (JobParameters) message.obj;
                b.a("Job finished " + jobParameters.getJobId());
                this.a.jobFinished(jobParameters, false);
                if (jobParameters.getJobId() == 1) {
                    a.a(false);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
