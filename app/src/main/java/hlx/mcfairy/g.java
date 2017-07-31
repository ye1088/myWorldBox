package hlx.mcfairy;

import com.huluxia.HTApplication;
import com.huluxia.framework.base.log.HLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/* compiled from: FairyTipsReaderUtils */
public class g {
    public static void P(List<e> list) {
        IOException e;
        Throwable th;
        try {
            BufferedReader reader = null;
            try {
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(HTApplication.getAppContext().getResources().getAssets().open("data/100019")));
                int line = 1;
                while (true) {
                    try {
                        String tempString = reader2.readLine();
                        if (tempString == null) {
                            break;
                        }
                        String[] sourceStrArray = tempString.split("#");
                        if (sourceStrArray[0].equals("at")) {
                            if (sourceStrArray[1].equals("id") && sourceStrArray[3].equals("tips")) {
                                list.add(new e(Integer.parseInt(sourceStrArray[2]), sourceStrArray[4]));
                            }
                            line++;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        reader = reader2;
                    } catch (Throwable th2) {
                        th = th2;
                        reader = reader2;
                    }
                }
                reader2.close();
                if (reader2 != null) {
                    try {
                        reader2.close();
                        reader = reader2;
                        return;
                    } catch (IOException e3) {
                        reader = reader2;
                        return;
                    }
                }
            } catch (IOException e4) {
                e = e4;
                try {
                    e.printStackTrace();
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e5) {
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e6) {
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e7) {
            HLog.verbose("Exception", e7.getMessage(), new Object[0]);
        }
    }

    public static void Q(List<e> list) {
        IOException e;
        Throwable th;
        try {
            BufferedReader reader = null;
            try {
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(HTApplication.getAppContext().getResources().getAssets().open("data/100020")));
                int line = 1;
                while (true) {
                    try {
                        String tempString = reader2.readLine();
                        if (tempString == null) {
                            break;
                        }
                        String[] sourceStrArray = tempString.split("#");
                        if (sourceStrArray[0].equals("putB")) {
                            if (sourceStrArray[1].equals("id") && sourceStrArray[3].equals("tips")) {
                                list.add(new e(Integer.parseInt(sourceStrArray[2]), sourceStrArray[4]));
                            }
                            line++;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        reader = reader2;
                    } catch (Throwable th2) {
                        th = th2;
                        reader = reader2;
                    }
                }
                reader2.close();
                if (reader2 != null) {
                    try {
                        reader2.close();
                        reader = reader2;
                        return;
                    } catch (IOException e3) {
                        reader = reader2;
                        return;
                    }
                }
            } catch (IOException e4) {
                e = e4;
                try {
                    e.printStackTrace();
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e5) {
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e6) {
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e7) {
            HLog.verbose("Exception", e7.getMessage(), new Object[0]);
        }
    }

    public static void R(List<e> list) {
        IOException e;
        Throwable th;
        try {
            BufferedReader reader = null;
            try {
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(HTApplication.getAppContext().getResources().getAssets().open("data/100021")));
                int line = 1;
                while (true) {
                    try {
                        String tempString = reader2.readLine();
                        if (tempString == null) {
                            break;
                        }
                        String[] sourceStrArray = tempString.split("#");
                        if (sourceStrArray[0].equals("destoryB")) {
                            if (sourceStrArray[1].equals("id") && sourceStrArray[3].equals("tips")) {
                                list.add(new e(Integer.parseInt(sourceStrArray[2]), sourceStrArray[4]));
                            }
                            line++;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        reader = reader2;
                    } catch (Throwable th2) {
                        th = th2;
                        reader = reader2;
                    }
                }
                reader2.close();
                if (reader2 != null) {
                    try {
                        reader2.close();
                        reader = reader2;
                        return;
                    } catch (IOException e3) {
                        reader = reader2;
                        return;
                    }
                }
            } catch (IOException e4) {
                e = e4;
                try {
                    e.printStackTrace();
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e5) {
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e6) {
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e7) {
            HLog.verbose("Exception", e7.getMessage(), new Object[0]);
        }
    }
}
