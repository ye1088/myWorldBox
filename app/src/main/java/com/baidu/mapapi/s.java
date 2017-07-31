package com.baidu.mapapi;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import io.netty.handler.codec.http.HttpHeaders.Values;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.apache.tools.ant.util.FileUtils;

public class s {
    static s b;
    static boolean c = false;
    ArrayList<File> a = new ArrayList();
    private boolean d = false;
    private String e;
    private Handler f = new t(this);

    class a extends Thread {
        File a;
        final /* synthetic */ s b;

        public a(s sVar, File file) {
            this.b = sVar;
            this.a = file;
            Log.d("kal", "upload file:" + this.a.getAbsolutePath());
        }

        public void a() {
            if (!this.a.exists()) {
                this.b.a.remove(this.a);
            }
            s.a().c();
        }

        public void run() {
            try {
                if (s.c) {
                    Log.d("kal", "UploadWorker:begin uploading.");
                }
                Map hashMap = new HashMap();
                hashMap.put("ver", "2");
                hashMap.put("pd", "mapsdk");
                hashMap.put("im", Mj.k);
                hashMap.put("os", "android");
                String uuid = UUID.randomUUID().toString();
                String str = "--";
                String str2 = "\r\n";
                String str3 = Values.MULTIPART_FORM_DATA;
                String str4 = "UTF-8";
                HttpURLConnection a = d.a("http://ulog.imap.baidu.com/up");
                if (s.c) {
                    Log.d("kal", "UploadWorker:begin get connection ok.");
                }
                a.setReadTimeout(5000);
                a.setDoInput(true);
                a.setDoOutput(true);
                a.setUseCaches(false);
                a.setRequestMethod("POST");
                a.setRequestProperty("connection", "keep-alive");
                a.setRequestProperty("Charsert", "UTF-8");
                a.setRequestProperty("Content-Type", str3 + ";boundary=" + uuid);
                StringBuilder stringBuilder = new StringBuilder();
                for (Entry entry : hashMap.entrySet()) {
                    stringBuilder.append(str);
                    stringBuilder.append(uuid);
                    stringBuilder.append(str2);
                    stringBuilder.append("Content-Disposition: form-data; name=\"" + ((String) entry.getKey()) + "\"" + str2);
                    stringBuilder.append("Content-Type: text/plain; charset=" + str4 + str2);
                    stringBuilder.append("Content-Transfer-Encoding: 8bit" + str2);
                    stringBuilder.append(str2);
                    stringBuilder.append((String) entry.getValue());
                    stringBuilder.append(str2);
                }
                DataOutputStream dataOutputStream = new DataOutputStream(a.getOutputStream());
                dataOutputStream.write(stringBuilder.toString().getBytes());
                if (s.c) {
                    Log.d("kal", "UploadWorker:write header is ok.");
                }
                if (this.a != null) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str);
                    stringBuilder2.append(uuid);
                    stringBuilder2.append(str2);
                    stringBuilder2.append("Content-Disposition: form-data; name=\"datafile\"; filename=\"" + this.a.getName() + "\"" + str2);
                    stringBuilder2.append("Content-Type: application/octet-stream; charset=" + str4 + str2);
                    stringBuilder2.append(str2);
                    dataOutputStream.write(stringBuilder2.toString().getBytes());
                    InputStream fileInputStream = new FileInputStream(this.a);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        dataOutputStream.write(bArr, 0, read);
                    }
                    fileInputStream.close();
                    dataOutputStream.write(str2.getBytes());
                    if (s.c) {
                        Log.d("kal", "UploadWorker: write data is ok.");
                    }
                }
                dataOutputStream.write((str + uuid + str + str2).getBytes());
                dataOutputStream.flush();
                int responseCode = a.getResponseCode();
                dataOutputStream.close();
                a.disconnect();
                if (responseCode == 200) {
                    synchronized (this.b.a) {
                        this.b.d = false;
                        if (s.c) {
                            Log.d("kal", "remove file:" + this.a.getAbsolutePath());
                        }
                        this.b.a.remove(this.a);
                        if (s.c) {
                            Log.d("kal", "updateList size: " + this.b.a.size());
                        }
                        this.a.delete();
                        if (this.b.a.size() > 0) {
                            this.b.f.sendMessageDelayed(this.b.f.obtainMessage(1), FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                        } else {
                            this.b.f.sendEmptyMessage(2);
                        }
                    }
                    return;
                }
                synchronized (this.b.a) {
                    this.b.d = false;
                    if (this.b.a.size() > 0) {
                        this.b.f.sendMessageDelayed(this.b.f.obtainMessage(1), FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY);
                    } else {
                        this.b.f.sendEmptyMessage(2);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                a();
            } catch (IOException e2) {
                e2.printStackTrace();
                a();
            }
        }
    }

    public static s a() {
        if (b == null) {
            b = new s();
        }
        return b;
    }

    private void b() {
        if (this.a.size() > 0) {
            if (c) {
                Log.d("kal", "started.");
            }
            this.d = true;
            new a(this, (File) this.a.get(0)).start();
        }
    }

    private void b(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles.length > 0) {
                for (File file2 : listFiles) {
                    if (!(file2.getName().contains("udclog") || this.a.contains(file2))) {
                        this.a.add(file2);
                    }
                }
                b();
            }
        }
    }

    private void c() {
        if (c) {
            Log.d("kal", "stoped.");
        }
        this.d = false;
        if (this.e != null) {
            b(new File(this.e));
        }
    }

    public String a(String str, String str2) {
        String AppendRecord = Mj.AppendRecord(str, str2);
        if (!(AppendRecord == null || "".equals(AppendRecord))) {
            a(new File(AppendRecord));
        }
        return AppendRecord;
    }

    void a(Context context) {
        b(new File(context.getFilesDir(), "udc/"));
    }

    public void a(File file) {
        synchronized (this.a) {
            if (!this.a.contains(file)) {
                this.a.add(file);
                this.e = file.getParent();
                if (!this.d) {
                    b();
                }
            }
        }
    }
}
