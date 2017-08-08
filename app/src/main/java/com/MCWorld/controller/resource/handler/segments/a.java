package com.MCWorld.controller.resource.handler.segments;

import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.z;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.DownloadMemCache;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord$State;
import com.MCWorld.framework.base.http.toolbox.download.DownloadReporter;
import com.MCWorld.framework.base.http.toolbox.error.ErrorCode;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsMD5;
import java.io.File;

/* compiled from: Helper */
public class a {
    private static final String TAG = "Helper";

    public static String dP() {
        File metaDataDir = new File(Environment.getExternalStorageDirectory(), AppConfig.getInstance().getRootPath() + File.separator + "download-metadata");
        if (!metaDataDir.exists()) {
            metaDataDir.mkdirs();
        }
        return metaDataDir.getAbsolutePath();
    }

    public static String am(String tableId) {
        return new File(dP(), tableId).getAbsolutePath();
    }

    public static String generateRecordId(String url) {
        return UtilsMD5.getMD5String(url + String.valueOf(SystemClock.elapsedRealtime()));
    }

    @z
    public static f an(String tableId) {
        return s(tableId, 0);
    }

    @z
    public static f s(String tableId, int mode) {
        f table = i.dW().u(am(tableId), mode);
        if (table == null) {
            return null;
        }
        if (table.total == 0 || UtilsFunction.empty(table.oo)) {
            return null;
        }
        return table;
    }

    @z
    public static DownloadRecord getRecord(String tableId) {
        return t(tableId, 0);
    }

    @z
    public static DownloadRecord t(String tableId, int mode) {
        f table = i.dW().u(am(tableId), mode);
        if (table == null) {
            return null;
        }
        if (table.total == 0 || UtilsFunction.empty(table.oo)) {
            return null;
        }
        long progress = 0;
        boolean pause = false;
        boolean error = false;
        int notDownloadCount = 0;
        for (com.MCWorld.controller.resource.handler.segments.f.a segment : table.oo) {
            DownloadRecord record = DownloadMemCache.getInstance().getRecord(segment.id);
            if (record == null || ErrorCode.isRestart(record.error)) {
                notDownloadCount++;
            } else {
                error = error || ErrorCode.isSegmentErr(record.error);
                segment.mN = record;
                progress += record.progress;
                pause = pause || (record.pause && record.progress < segment.or - segment.start);
            }
        }
        if (notDownloadCount == UtilsFunction.size(table.oo)) {
            HLog.info(TAG, "no segment download record", new Object[0]);
            return null;
        }
        long j;
        File file = new File(table.path);
        record = new DownloadRecord();
        record.url = tableId;
        record.total = table.total;
        record.dir = file.getParent();
        record.name = file.getName();
        if (progress > table.total) {
            j = table.total;
        } else {
            j = progress;
        }
        record.progress = j;
        record.pause = pause;
        record.error = table.on ? 4096 : -1;
        if (progress == 0) {
            record.state = DownloadRecord$State.INIT.state;
            return record;
        } else if (progress >= table.total) {
            record.state = DownloadRecord$State.COMPLETION.state;
            return record;
        } else {
            record.state = DownloadRecord$State.DOWNLOADING.state;
            return record;
        }
    }

    public static void delete(String tableId) {
        DownloadReporter reporter = new DownloadReporter();
        f downloadedTable = an(tableId);
        if (!(downloadedTable == null || UtilsFunction.empty(downloadedTable.oo))) {
            for (com.MCWorld.controller.resource.handler.segments.f.a segment : downloadedTable.oo) {
                reporter.deleteRecord(segment.id);
            }
        }
        i.dW().remove(am(tableId));
    }
}
