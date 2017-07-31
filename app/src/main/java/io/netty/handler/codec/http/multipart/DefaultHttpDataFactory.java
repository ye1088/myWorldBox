package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DefaultHttpDataFactory implements HttpDataFactory {
    public static final long MAXSIZE = -1;
    public static final long MINSIZE = 16384;
    private Charset charset;
    private final boolean checkSize;
    private long maxSize;
    private long minSize;
    private final Map<HttpRequest, List<HttpData>> requestFileDeleteMap;
    private final boolean useDisk;

    public DefaultHttpDataFactory() {
        this.maxSize = -1;
        this.charset = HttpConstants.DEFAULT_CHARSET;
        this.requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
        this.useDisk = false;
        this.checkSize = true;
        this.minSize = MINSIZE;
    }

    public DefaultHttpDataFactory(Charset charset) {
        this();
        this.charset = charset;
    }

    public DefaultHttpDataFactory(boolean useDisk) {
        this.maxSize = -1;
        this.charset = HttpConstants.DEFAULT_CHARSET;
        this.requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
        this.useDisk = useDisk;
        this.checkSize = false;
    }

    public DefaultHttpDataFactory(boolean useDisk, Charset charset) {
        this(useDisk);
        this.charset = charset;
    }

    public DefaultHttpDataFactory(long minSize) {
        this.maxSize = -1;
        this.charset = HttpConstants.DEFAULT_CHARSET;
        this.requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
        this.useDisk = false;
        this.checkSize = true;
        this.minSize = minSize;
    }

    public DefaultHttpDataFactory(long minSize, Charset charset) {
        this(minSize);
        this.charset = charset;
    }

    public void setMaxLimit(long maxSize) {
        this.maxSize = maxSize;
    }

    private List<HttpData> getList(HttpRequest request) {
        List<HttpData> list = (List) this.requestFileDeleteMap.get(request);
        if (list != null) {
            return list;
        }
        list = new ArrayList();
        this.requestFileDeleteMap.put(request, list);
        return list;
    }

    public Attribute createAttribute(HttpRequest request, String name) {
        Attribute attribute;
        if (this.useDisk) {
            attribute = new DiskAttribute(name, this.charset);
            attribute.setMaxSize(this.maxSize);
            getList(request).add(attribute);
            return attribute;
        } else if (this.checkSize) {
            attribute = new MixedAttribute(name, this.minSize, this.charset);
            attribute.setMaxSize(this.maxSize);
            getList(request).add(attribute);
            return attribute;
        } else {
            attribute = new MemoryAttribute(name);
            attribute.setMaxSize(this.maxSize);
            return attribute;
        }
    }

    public Attribute createAttribute(HttpRequest request, String name, long definedSize) {
        Attribute attribute;
        if (this.useDisk) {
            attribute = new DiskAttribute(name, definedSize, this.charset);
            attribute.setMaxSize(this.maxSize);
            getList(request).add(attribute);
            return attribute;
        } else if (this.checkSize) {
            attribute = new MixedAttribute(name, definedSize, this.minSize, this.charset);
            attribute.setMaxSize(this.maxSize);
            getList(request).add(attribute);
            return attribute;
        } else {
            attribute = new MemoryAttribute(name, definedSize);
            attribute.setMaxSize(this.maxSize);
            return attribute;
        }
    }

    private static void checkHttpDataSize(HttpData data) {
        try {
            data.checkSize(data.length());
        } catch (IOException e) {
            throw new IllegalArgumentException("Attribute bigger than maxSize allowed");
        }
    }

    public Attribute createAttribute(HttpRequest request, String name, String value) {
        Attribute attribute;
        if (this.useDisk) {
            try {
                attribute = new DiskAttribute(name, value, this.charset);
                attribute.setMaxSize(this.maxSize);
            } catch (IOException e) {
                attribute = new MixedAttribute(name, value, this.minSize, this.charset);
                attribute.setMaxSize(this.maxSize);
            }
            checkHttpDataSize(attribute);
            getList(request).add(attribute);
            return attribute;
        } else if (this.checkSize) {
            attribute = new MixedAttribute(name, value, this.minSize, this.charset);
            attribute.setMaxSize(this.maxSize);
            checkHttpDataSize(attribute);
            getList(request).add(attribute);
            return attribute;
        } else {
            try {
                attribute = new MemoryAttribute(name, value, this.charset);
                attribute.setMaxSize(this.maxSize);
                checkHttpDataSize(attribute);
                return attribute;
            } catch (IOException e2) {
                throw new IllegalArgumentException(e2);
            }
        }
    }

    public FileUpload createFileUpload(HttpRequest request, String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
        FileUpload fileUpload;
        if (this.useDisk) {
            fileUpload = new DiskFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
            fileUpload.setMaxSize(this.maxSize);
            checkHttpDataSize(fileUpload);
            getList(request).add(fileUpload);
            return fileUpload;
        } else if (this.checkSize) {
            fileUpload = new MixedFileUpload(name, filename, contentType, contentTransferEncoding, charset, size, this.minSize);
            fileUpload.setMaxSize(this.maxSize);
            checkHttpDataSize(fileUpload);
            getList(request).add(fileUpload);
            return fileUpload;
        } else {
            fileUpload = new MemoryFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
            fileUpload.setMaxSize(this.maxSize);
            checkHttpDataSize(fileUpload);
            return fileUpload;
        }
    }

    public void removeHttpDataFromClean(HttpRequest request, InterfaceHttpData data) {
        if (data instanceof HttpData) {
            getList(request).remove(data);
        }
    }

    public void cleanRequestHttpData(HttpRequest request) {
        List<HttpData> fileToDelete = (List) this.requestFileDeleteMap.remove(request);
        if (fileToDelete != null) {
            for (HttpData data : fileToDelete) {
                data.delete();
            }
            fileToDelete.clear();
        }
    }

    public void cleanAllHttpData() {
        Iterator<Entry<HttpRequest, List<HttpData>>> i = this.requestFileDeleteMap.entrySet().iterator();
        while (i.hasNext()) {
            Entry<HttpRequest, List<HttpData>> e = (Entry) i.next();
            i.remove();
            List<HttpData> fileToDelete = (List) e.getValue();
            if (fileToDelete != null) {
                for (HttpData data : fileToDelete) {
                    data.delete();
                }
                fileToDelete.clear();
            }
        }
    }

    public void cleanRequestHttpDatas(HttpRequest request) {
        cleanRequestHttpData(request);
    }

    public void cleanAllHttpDatas() {
        cleanAllHttpData();
    }
}
