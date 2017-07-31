package android.os;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class FileUtils {
    private static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("[\\w%+,./=_-]+");
    public static final int S_IRGRP = 32;
    public static final int S_IROTH = 4;
    public static final int S_IRUSR = 256;
    public static final int S_IRWXG = 56;
    public static final int S_IRWXO = 7;
    public static final int S_IRWXU = 448;
    public static final int S_IWGRP = 16;
    public static final int S_IWOTH = 2;
    public static final int S_IWUSR = 128;
    public static final int S_IXGRP = 8;
    public static final int S_IXOTH = 1;
    public static final int S_IXUSR = 64;

    public static final class FileStatus {
        public long atime;
        public int blksize;
        public long blocks;
        public long ctime;
        public int dev;
        public int gid;
        public int ino;
        public int mode;
        public long mtime;
        public int nlink;
        public int rdev;
        public long size;
        public int uid;
    }

    public static native int getFatVolumeId(String str);

    public static native boolean getFileStatus(String str, FileStatus fileStatus);

    public static native int getPermissions(String str, int[] iArr);

    public static native int setPermissions(String str, int i, int i2, int i3);

    public static boolean sync(FileOutputStream stream) {
        if (stream != null) {
            try {
                stream.getFD().sync();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean copyFile(File srcFile, File destFile) {
        InputStream in;
        try {
            in = new FileInputStream(srcFile);
            boolean result = copyToFile(in, destFile);
            in.close();
            return result;
        } catch (IOException e) {
            return false;
        } catch (Throwable th) {
            in.close();
        }
    }

    public static boolean copyToFile(InputStream inputStream, File destFile) {
        FileOutputStream out;
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead < 0) {
                    break;
                }
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            try {
                out.getFD().sync();
            } catch (IOException e) {
            }
            out.close();
            return true;
        } catch (IOException e2) {
            return false;
        } catch (Throwable th) {
            out.flush();
            try {
                out.getFD().sync();
            } catch (IOException e3) {
            }
            out.close();
        }
    }

    public static boolean isFilenameSafe(File file) {
        return SAFE_FILENAME_PATTERN.matcher(file.getPath()).matches();
    }

    public static String readTextFile(File file, int max, String ellipsis) throws IOException {
        InputStream input = new FileInputStream(file);
        try {
            long size = file.length();
            byte[] data;
            String str;
            if (max > 0 || (size > 0 && max == 0)) {
                if (size > 0 && (max == 0 || size < ((long) max))) {
                    max = (int) size;
                }
                data = new byte[(max + 1)];
                int length = input.read(data);
                if (length <= 0) {
                    str = "";
                } else if (length <= max) {
                    str = new String(data, 0, length);
                    input.close();
                } else if (ellipsis == null) {
                    str = new String(data, 0, max);
                    input.close();
                } else {
                    str = new String(data, 0, max) + ellipsis;
                    input.close();
                }
                return str;
            } else if (max < 0) {
                boolean rolled = false;
                byte[] last = null;
                data = null;
                while (true) {
                    if (last != null) {
                        rolled = true;
                    }
                    byte[] tmp = last;
                    last = data;
                    data = tmp;
                    if (data == null) {
                        data = new byte[(-max)];
                    }
                    len = input.read(data);
                    if (len != data.length) {
                        break;
                    }
                }
                if (last == null && len <= 0) {
                    str = "";
                    input.close();
                    return str;
                } else if (last == null) {
                    str = new String(data, 0, len);
                    input.close();
                    return str;
                } else {
                    if (len > 0) {
                        rolled = true;
                        System.arraycopy(last, len, last, 0, last.length - len);
                        System.arraycopy(data, 0, last, last.length - len, len);
                    }
                    if (ellipsis == null || !rolled) {
                        str = new String(last);
                        input.close();
                        return str;
                    }
                    str = ellipsis + new String(last);
                    input.close();
                    return str;
                }
            } else {
                ByteArrayOutputStream contents = new ByteArrayOutputStream();
                data = new byte[1024];
                while (true) {
                    len = input.read(data);
                    if (len > 0) {
                        contents.write(data, 0, len);
                    }
                    if (len != data.length) {
                        break;
                    }
                }
                str = contents.toString();
                input.close();
                return str;
            }
        } finally {
            input.close();
        }
    }
}
