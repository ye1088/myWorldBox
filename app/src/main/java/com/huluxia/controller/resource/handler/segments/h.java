package com.huluxia.controller.resource.handler.segments;

import android.support.annotation.z;
import com.huluxia.controller.resource.handler.segments.f.a;
import com.huluxia.controller.resource.handler.segments.f.b;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: SegmentsMetadata */
public class h {
    private static final String TAG = "SegmentsMetadata";
    private static final String oy = "metadata";

    public static boolean ao(String dir) {
        File segmentFile = new File(dir);
        if (segmentFile.exists()) {
            return UtilsFile.deleteFile(segmentFile);
        }
        return true;
    }

    @z
    public static f ap(String dir) {
        FileNotFoundException e;
        IOException e2;
        Throwable th;
        File segmentFile = new File(dir, oy);
        if (!segmentFile.exists()) {
            return null;
        }
        f fVar = null;
        BufferedReader bufferedReader = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(segmentFile));
            try {
                long total = Long.parseLong(br.readLine());
                if (null == null) {
                    fVar = new f();
                }
                fVar.total = total;
                fVar.path = br.readLine();
                fVar.on = Boolean.parseBoolean(br.readLine());
                while (true) {
                    try {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        String[] blocks = line.split("\\|");
                        if (blocks != null && blocks.length == 6) {
                            a segment = new a();
                            segment.id = blocks[0];
                            String url = blocks[1];
                            try {
                                segment.oq = new b(url, Integer.parseInt(blocks[2]), Integer.parseInt(blocks[3]));
                                segment.start = Long.parseLong(blocks[4]);
                                segment.or = Long.parseLong(blocks[5]);
                                fVar.oo.add(segment);
                            } catch (NumberFormatException e3) {
                                HLog.error(TAG, "format start or end error", e3, new Object[0]);
                            }
                        }
                    } catch (FileNotFoundException e4) {
                        e = e4;
                        bufferedReader = br;
                    } catch (IOException e5) {
                        e2 = e5;
                        bufferedReader = br;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader = br;
                    }
                }
                if (br != null) {
                    try {
                        br.close();
                        bufferedReader = br;
                    } catch (IOException e22) {
                        HLog.error(TAG, "read segments close io ex", e22, new Object[0]);
                        bufferedReader = br;
                    }
                }
                HLog.debug(TAG, "read table " + fVar, new Object[0]);
                return fVar;
            } catch (NumberFormatException e32) {
                HLog.error(TAG, "format total error", e32, new Object[0]);
                if (br == null) {
                    return null;
                }
                try {
                    br.close();
                    return null;
                } catch (IOException e222) {
                    HLog.error(TAG, "read segments close io ex", e222, new Object[0]);
                    return null;
                }
            }
        } catch (FileNotFoundException e6) {
            e = e6;
            try {
                HLog.error(TAG, "read segments record failed because of file not exists", e, new Object[0]);
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e2222) {
                        HLog.error(TAG, "read segments close io ex", e2222, new Object[0]);
                    }
                }
                HLog.debug(TAG, "read table " + fVar, new Object[0]);
                return fVar;
            } catch (Throwable th3) {
                th = th3;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e22222) {
                        HLog.error(TAG, "read segments close io ex", e22222, new Object[0]);
                    }
                }
                throw th;
            }
        } catch (IOException e7) {
            e22222 = e7;
            HLog.error(TAG, "read segments record failed because of io ex", e22222, new Object[0]);
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e222222) {
                    HLog.error(TAG, "read segments close io ex", e222222, new Object[0]);
                }
            }
            HLog.debug(TAG, "read table " + fVar, new Object[0]);
            return fVar;
        }
    }

    public static boolean a(String dir, f table) {
        IOException e;
        Throwable th;
        if (table == null) {
            HLog.warn(TAG, "write segments table empty " + table, new Object[0]);
            return false;
        }
        HLog.info(TAG, "write table " + table + " to " + dir, new Object[0]);
        File file = new File(dir);
        if (!file.exists()) {
            boolean succ = file.mkdirs();
            if (!succ) {
                succ = file.mkdirs();
            }
            if (!succ) {
                HLog.error(TAG, "make meta file failed, " + dir, new Object[0]);
                return false;
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(new File(dir, oy), false));
            try {
                bufferedWriter2.write(String.valueOf(table.total));
                bufferedWriter2.newLine();
                bufferedWriter2.write(table.path);
                bufferedWriter2.newLine();
                bufferedWriter2.write(String.valueOf(table.on));
                bufferedWriter2.newLine();
                if (!UtilsFunction.empty(table.oo)) {
                    List<a> segmentList = new ArrayList(table.oo);
                    Collections.sort(segmentList, new Comparator<a>() {
                        public /* synthetic */ int compare(Object obj, Object obj2) {
                            return a((a) obj, (a) obj2);
                        }

                        public int a(a lhs, a rhs) {
                            long result = lhs.start - rhs.start;
                            if (result == 0) {
                                result = lhs.or - rhs.or;
                            }
                            return (int) result;
                        }
                    });
                    long length = 0;
                    for (a s : segmentList) {
                        length += s.or - s.start;
                        bufferedWriter2.write(String.format("%s|%s|%d|%d|%d|%d", new Object[]{s.id, s.oq.url, Integer.valueOf(s.oq.weight), Integer.valueOf(s.oq.rate), Long.valueOf(s.start), Long.valueOf(s.or)}));
                        bufferedWriter2.newLine();
                    }
                    if (bufferedWriter2 == null) {
                        return true;
                    }
                    try {
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                        return true;
                    } catch (IOException e2) {
                        HLog.error(TAG, "write table close ex " + e2, new Object[0]);
                        return true;
                    }
                } else if (bufferedWriter2 == null) {
                    return true;
                } else {
                    try {
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                        return true;
                    } catch (IOException e22) {
                        HLog.error(TAG, "write table close ex " + e22, new Object[0]);
                        return true;
                    }
                }
            } catch (IOException e3) {
                e22 = e3;
                bufferedWriter = bufferedWriter2;
                try {
                    HLog.error(TAG, "write segments record failed because of io ex", e22, new Object[0]);
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e222) {
                            HLog.error(TAG, "write table close ex " + e222, new Object[0]);
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e2222) {
                            HLog.error(TAG, "write table close ex " + e2222, new Object[0]);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter = bufferedWriter2;
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
                throw th;
            }
        } catch (IOException e4) {
            e2222 = e4;
            HLog.error(TAG, "write segments record failed because of io ex", e2222, new Object[0]);
            if (bufferedWriter != null) {
                bufferedWriter.flush();
                bufferedWriter.close();
            }
            return false;
        }
    }
}
