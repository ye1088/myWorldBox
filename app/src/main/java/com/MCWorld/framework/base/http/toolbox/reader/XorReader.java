package com.MCWorld.framework.base.http.toolbox.reader;

import com.MCWorld.framework.base.http.toolbox.error.LocalFileError;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.utils.ByteArrayPool;
import java.io.IOException;
import java.io.InputStream;

public class XorReader extends DownloadReader {
    private static final int DEFALUT_BUFFER_SIZE = 8192;
    private static int DEFAULT_POOL_SIZE = 8192;
    private byte[] buffer;
    private byte key = (byte) 104;
    private InputStream mInputStream;
    private IAdapterToStreamAndRaf mOutputStream;

    public XorReader(ByteArrayPool byteArrayPool) {
        super(byteArrayPool);
    }

    public <E extends Throwable, T extends Throwable> void copy(InputStream inputStream, IAdapterToStreamAndRaf outputStream, IReaderCallback<E, T> callback) throws IOException, Throwable, Throwable, VolleyError {
        this.buffer = this.mByteArrayPool.getBuf(8192);
        this.mInputStream = inputStream;
        this.mOutputStream = outputStream;
        while (true) {
            int count = inputStream.read(this.buffer);
            if (count == -1) {
                break;
            }
            byte[] encodeByte = new byte[8192];
            for (int j = 0; j < count; j++) {
                encodeByte[j] = (byte) (this.key ^ this.buffer[j]);
            }
            try {
                outputStream.write(encodeByte, 0, count);
                if (callback != null) {
                    callback.readLoop(count);
                }
            } catch (IOException e) {
                throw new LocalFileError(e);
            }
        }
        if (callback != null) {
            callback.end();
        }
    }

    public void close() throws IOException {
        this.mOutputStream.flush();
        this.mOutputStream.close();
        this.mOutputStream = null;
        this.mByteArrayPool.returnBuf(this.buffer);
        this.mInputStream.close();
        this.mInputStream = null;
    }
}
