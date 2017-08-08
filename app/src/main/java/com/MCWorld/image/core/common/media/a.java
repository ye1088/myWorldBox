package com.MCWorld.image.core.common.media;

import android.webkit.MimeTypeMap;
import com.MCWorld.framework.base.utils.ImmutableMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: MediaUtils */
public class a {
    public static final Map<String, String> yd = ImmutableMap.of("mkv", "video/x-matroska");

    public static boolean bl(@Nullable String mimeType) {
        return mimeType != null && mimeType.startsWith("image/");
    }

    public static boolean isVideo(@Nullable String mimeType) {
        return mimeType != null && mimeType.startsWith("video/");
    }

    @Nullable
    public static String bm(String path) {
        String extension = bn(path);
        if (extension == null) {
            return null;
        }
        extension = extension.toLowerCase(Locale.US);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (mimeType == null) {
            return (String) yd.get(extension);
        }
        return mimeType;
    }

    @Nullable
    private static String bn(String path) {
        int pos = path.lastIndexOf(46);
        if (pos < 0 || pos == path.length() - 1) {
            return null;
        }
        return path.substring(pos + 1);
    }

    public static boolean bo(String mimeType) {
        return yd.containsValue(mimeType);
    }
}
