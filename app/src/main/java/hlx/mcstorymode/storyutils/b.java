package hlx.mcstorymode.storyutils;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mcinterface.h;
import com.MCWorld.utils.j;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;

/* compiled from: StoryUtils */
public class b {
    public static long ad(String strTimeSecs, int defaultTimeSecs) {
        try {
            return (long) (((float) 1000) * Float.parseFloat(new DecimalFormat(".00").format(Float.valueOf(Float.parseFloat(strTimeSecs)))));
        } catch (Exception e) {
            return (long) (defaultTimeSecs * 1000);
        }
    }

    public static long a(String strTimeSecs, int defaultTimeSecs, float ratios) {
        return (long) (((float) ad(strTimeSecs, defaultTimeSecs)) / ratios);
    }

    public static String w(String objectStr, String escapeSequenceStart, String escapeSequenceEnd) {
        int _start = objectStr.indexOf(escapeSequenceStart) + escapeSequenceStart.length();
        return objectStr.substring(_start, objectStr.indexOf(escapeSequenceEnd, _start));
    }

    public static InputStream aG(String strStory001FilePath, String fileName) {
        if (!j.isExist(strStory001FilePath)) {
            return null;
        }
        try {
            File fHandle = new File(strStory001FilePath);
            InputStream in = new FileInputStream(fHandle);
            int nLen = (int) fHandle.length();
            byte[] b = new byte[((int) fHandle.length())];
            in.read(b);
            in.close();
            h.m(b, nLen);
            return new ByteArrayInputStream(b);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
            return null;
        }
    }

    public static InputStream d(InputStream inputStream, String fileName) {
        if (inputStream == null) {
            return null;
        }
        try {
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            inputStream.close();
            for (int i = 0; i < b.length; i++) {
                b[i] = (byte) (b[i] ^ (i + 22585));
            }
            return new ByteArrayInputStream(b);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
            return null;
        }
    }
}
