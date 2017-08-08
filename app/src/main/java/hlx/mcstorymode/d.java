package hlx.mcstorymode;

import com.MCWorld.data.storymode.c;
import hlx.mcstorymode.storyutils.a;
import hlx.mcstorymode.storyutils.b;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/* compiled from: StoryDataInterface */
public class d {
    private static d bWG;
    private a bWH;

    public void mX(int storyChapterIndex) {
        InputStream inputStream;
        this.bWH = new a();
        InputStream inputStream2 = null;
        switch (storyChapterIndex) {
            case 1:
                try {
                    inputStream2 = b.aG(e.ng(storyChapterIndex), e.nc(storyChapterIndex));
                    break;
                } catch (Exception e) {
                    break;
                }
            case 2:
                try {
                    inputStream = new FileInputStream(new File(e.ng(storyChapterIndex)));
                    try {
                        inputStream2 = b.d(inputStream, e.nc(storyChapterIndex));
                        break;
                    } catch (Exception e2) {
                        inputStream2 = inputStream;
                        break;
                    }
                } catch (Exception e3) {
                    break;
                }
            case 3:
                try {
                    inputStream = new FileInputStream(new File(e.ng(storyChapterIndex)));
                    try {
                        inputStream2 = b.d(inputStream, e.nc(storyChapterIndex));
                        break;
                    } catch (Exception e4) {
                        inputStream2 = inputStream;
                        break;
                    }
                } catch (Exception e5) {
                    break;
                }
            case 4:
                try {
                    inputStream = new FileInputStream(new File(e.ng(storyChapterIndex)));
                    try {
                        inputStream2 = b.d(inputStream, e.nc(storyChapterIndex));
                        break;
                    } catch (Exception e6) {
                        inputStream2 = inputStream;
                        break;
                    }
                } catch (Exception e7) {
                    break;
                }
            case 5:
                try {
                    inputStream = new FileInputStream(new File(e.ng(storyChapterIndex)));
                    try {
                        inputStream2 = b.d(inputStream, e.nc(storyChapterIndex));
                        break;
                    } catch (Exception e8) {
                        inputStream2 = inputStream;
                        break;
                    }
                } catch (Exception e9) {
                    break;
                }
            case 6:
                try {
                    inputStream = new FileInputStream(new File(e.ng(storyChapterIndex)));
                    try {
                        inputStream2 = b.d(inputStream, e.nc(storyChapterIndex));
                        break;
                    } catch (Exception e10) {
                        inputStream2 = inputStream;
                        break;
                    }
                } catch (Exception e11) {
                    break;
                }
            case 7:
                try {
                    inputStream = new FileInputStream(new File(e.ng(storyChapterIndex)));
                    try {
                        inputStream2 = b.d(inputStream, e.nc(storyChapterIndex));
                        break;
                    } catch (Exception e12) {
                        inputStream2 = inputStream;
                        break;
                    }
                } catch (Exception e13) {
                    break;
                }
        }
        this.bWH.z(inputStream2);
    }

    public c hl(String storyChapterName) {
        return this.bWH.hq(storyChapterName);
    }

    public com.MCWorld.data.storymode.a hm(String id) {
        this.bWH.hr(id);
        return this.bWH.TQ();
    }

    public void TB() {
        this.bWH = null;
        this.bWH.TR();
        bWG = null;
    }

    public static d TC() {
        if (bWG == null) {
            bWG = new d();
        }
        return bWG;
    }
}
