package hlx.mcstorymode.storyutils;

import android.util.Xml;
import com.MCWorld.data.storymode.b;
import com.MCWorld.data.storymode.c;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: ResolveUtils */
public class a {
    private static final boolean DEBUG = false;
    private XmlPullParser bXJ = Xml.newPullParser();
    private File mFile;
    private InputStream mInputStream;
    private String qC;

    public boolean z(InputStream is) {
        try {
            this.bXJ.setInput(is, "UTF-8");
            if (this.bXJ.getEventType() != 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public c hq(String storyChapterName) {
        c storyAttribute = new c();
        int eventType;
        do {
            try {
                eventType = this.bXJ.next();
                if (eventType == 2 && this.bXJ.getName().equals(hlx.mcstorymode.c.bVD)) {
                    String _tmpStoryChapterName = this.bXJ.getAttributeValue("", "name");
                    if (storyChapterName.equals(_tmpStoryChapterName)) {
                        storyAttribute = new c();
                        storyAttribute.qH = _tmpStoryChapterName;
                        storyAttribute.qI = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVF);
                        storyAttribute.qJ = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVG);
                        storyAttribute.qK = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVH);
                        return storyAttribute;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return storyAttribute;
            }
        } while (eventType != 1);
        return storyAttribute;
    }

    public void hr(String nextId) {
        this.qC = nextId;
    }

    public com.MCWorld.data.storymode.a TQ() {
        com.MCWorld.data.storymode.a tmpItem = null;
        try {
            int eventType = this.bXJ.next();
            while (eventType != 1) {
                if (eventType != 2) {
                    if (eventType == 3 && this.bXJ.getName().equals(hlx.mcstorymode.c.bVI) && tmpItem != null) {
                        break;
                    }
                }
                String tmpName = this.bXJ.getName();
                if (tmpName.equals(hlx.mcstorymode.c.bVI)) {
                    if (this.qC != null && this.qC.equals(this.bXJ.getAttributeValue("", "id"))) {
                        tmpItem = new com.MCWorld.data.storymode.a();
                        tmpItem.mId = this.qC;
                        tmpItem.qi = this.bXJ.getAttributeValue("", "type");
                        tmpItem.qk = this.bXJ.getAttributeValue("", "time");
                        tmpItem.ql = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVM);
                        tmpItem.qm = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVN);
                        tmpItem.qn = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVO);
                        tmpItem.qo = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVP);
                        tmpItem.qp = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVQ);
                        tmpItem.qr = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVR);
                        tmpItem.qs = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVS);
                        tmpItem.qt = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVT);
                    } else if (this.qC == null) {
                        tmpItem = new com.MCWorld.data.storymode.a();
                        tmpItem.mId = this.bXJ.getAttributeValue("", "id");
                        tmpItem.qi = this.bXJ.getAttributeValue("", "type");
                        tmpItem.qk = this.bXJ.getAttributeValue("", "time");
                        tmpItem.ql = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVM);
                        tmpItem.qm = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVN);
                        tmpItem.qn = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVO);
                        tmpItem.qo = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVP);
                        tmpItem.qp = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVQ);
                        tmpItem.qr = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVR);
                        tmpItem.qs = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVS);
                        tmpItem.qt = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVT);
                    }
                } else if (tmpName.equals(hlx.mcstorymode.c.bVU) && tmpItem != null) {
                    b tmpSentence = new b();
                    tmpSentence.mName = this.bXJ.getAttributeValue("", "name");
                    if (tmpSentence.mName == null) {
                        tmpSentence.mName = "";
                    }
                    tmpSentence.qF = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVW);
                    tmpSentence.qG = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVX);
                    tmpSentence.mValue = this.bXJ.getAttributeValue("", "value");
                    tmpSentence.qE = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bVZ);
                    tmpSentence.qC = this.bXJ.getAttributeValue("", hlx.mcstorymode.c.bWa);
                    tmpSentence.qD = false;
                    tmpItem.qj.add(tmpSentence);
                }
                try {
                    eventType = this.bXJ.next();
                } catch (Exception e) {
                    e.printStackTrace();
                    tmpItem = null;
                }
            }
            return tmpItem;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public void TR() {
        if (this.mInputStream != null) {
            try {
                this.mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
