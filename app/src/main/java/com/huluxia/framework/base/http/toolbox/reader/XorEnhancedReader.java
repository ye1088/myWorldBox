package com.huluxia.framework.base.http.toolbox.reader;

import android.support.v4.media.session.PlaybackStateCompat;
import com.huluxia.framework.base.http.toolbox.error.LocalFileError;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.utils.ByteArrayPool;
import java.io.IOException;
import java.io.InputStream;
import org.bytedeco.javacpp.avcodec;

public class XorEnhancedReader extends DownloadReader {
    private static final int DEFALUT_BUFFER_SIZE = 8192;
    private static int DEFAULT_POOL_SIZE = 8192;
    private int KEY_BYTE_0 = 0;
    private int KEY_BYTE_1 = 0;
    private int KEY_BYTE_2 = avcodec.AV_CODEC_ID_HQX;
    private int KEY_BYTE_3 = 252;
    private byte[] buffer;
    private InputStream mInputStream;
    private IAdapterToStreamAndRaf mOutputStream;
    private long mStartLength;

    public XorEnhancedReader(ByteArrayPool byteArrayPool) {
        super(byteArrayPool);
    }

    public void setStartLength(long length) {
        this.mStartLength = length;
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
            int bufferIndex = 0;
            while (bufferIndex < count) {
                int i;
                int bufferIndex2;
                if (this.mStartLength < PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                    int left = count - bufferIndex;
                    byte[] decoded;
                    if (left < 4) {
                        decoded = new byte[left];
                        i = 0;
                        while (i < decoded.length) {
                            bufferIndex2 = bufferIndex + 1;
                            decoded[i] = (byte) (this.buffer[bufferIndex] ^ getXorValue(i));
                            i++;
                            bufferIndex = bufferIndex2;
                        }
                        this.mStartLength += (long) decoded.length;
                        try {
                            outputStream.write(decoded, 0, decoded.length);
                        } catch (IOException e) {
                            throw new LocalFileError(e);
                        }
                    }
                    int remainder = (int) (this.mStartLength % 4);
                    decoded = new byte[(4 - remainder)];
                    i = remainder;
                    int j = 0;
                    bufferIndex2 = bufferIndex;
                    while (i < 4) {
                        bufferIndex = bufferIndex2 + 1;
                        decoded[j] = (byte) (this.buffer[bufferIndex2] ^ getXorValue(i));
                        i++;
                        j++;
                        bufferIndex2 = bufferIndex;
                    }
                    this.mStartLength += (long) decoded.length;
                    try {
                        outputStream.write(decoded, 0, decoded.length);
                        bufferIndex = bufferIndex2;
                    } catch (IOException e2) {
                        throw new LocalFileError(e2);
                    }
                } else if (bufferIndex != 0) {
                    int length = count - bufferIndex;
                    byte[] data = new byte[length];
                    i = 0;
                    bufferIndex2 = bufferIndex;
                    while (i < length) {
                        bufferIndex = bufferIndex2 + 1;
                        data[i] = this.buffer[bufferIndex2];
                        i++;
                        bufferIndex2 = bufferIndex;
                    }
                    this.mStartLength += (long) count;
                    try {
                        outputStream.write(data, 0, data.length);
                        bufferIndex = bufferIndex2;
                    } catch (IOException e22) {
                        throw new LocalFileError(e22);
                    }
                } else {
                    this.mStartLength += (long) count;
                    bufferIndex += count;
                    try {
                        outputStream.write(this.buffer, 0, count);
                    } catch (IOException e222) {
                        throw new LocalFileError(e222);
                    }
                }
            }
            if (callback != null) {
                callback.readLoop(count);
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

    private byte getXorValue(int index) {
        switch (index) {
            case 0:
                return (byte) this.KEY_BYTE_0;
            case 1:
                return (byte) this.KEY_BYTE_1;
            case 2:
                return (byte) this.KEY_BYTE_2;
            default:
                return (byte) this.KEY_BYTE_3;
        }
    }
}
