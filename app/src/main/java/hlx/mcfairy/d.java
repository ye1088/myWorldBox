package hlx.mcfairy;

import java.util.LinkedList;
import java.util.List;

/* compiled from: FairyRepository */
public class d {
    private static d bTD;
    private List<e> bTE;
    private List<e> bTF;
    private List<e> bTG;

    public static synchronized d SY() {
        d dVar;
        synchronized (d.class) {
            if (bTD == null) {
                bTD = new d();
            }
            dVar = bTD;
        }
        return dVar;
    }

    public void SZ() {
        Ta();
        Tb();
        Tc();
    }

    private void Ta() {
        this.bTE = new LinkedList();
        g.P(this.bTE);
    }

    private void Tb() {
        this.bTF = new LinkedList();
        g.Q(this.bTF);
    }

    private void Tc() {
        this.bTG = new LinkedList();
        g.R(this.bTG);
    }

    public String b(i mess) {
        String tmpStr = "";
        String Tf = mess.Tf();
        Object obj = -1;
        switch (Tf.hashCode()) {
            case -1965057066:
                if (Tf.equals("eatFood")) {
                    obj = 3;
                    break;
                }
                break;
            case -1148899500:
                if (Tf.equals("addItem")) {
                    obj = 1;
                    break;
                }
                break;
            case -467897640:
                if (Tf.equals("enterLiveMap")) {
                    obj = 5;
                    break;
                }
                break;
            case -372756731:
                if (Tf.equals("loadNetMap")) {
                    obj = 4;
                    break;
                }
                break;
            case -126052719:
                if (Tf.equals("becomeNight")) {
                    obj = 7;
                    break;
                }
                break;
            case -23096732:
                if (Tf.equals("attackAnimal")) {
                    obj = 2;
                    break;
                }
                break;
            case 208615278:
                if (Tf.equals("lostFirstBlood")) {
                    obj = 6;
                    break;
                }
                break;
            case 1549628862:
                if (Tf.equals("delItem")) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                return bl(mess.Tg(), mess.Th());
            case 1:
                return bm(mess.Tg(), mess.Th());
            case 2:
                return mT(mess.Tg());
            case 3:
                return "熔炉烤熟了，味道会更好哟！";
            case 4:
                return "若是进入服务器联机，需要在聊天框注册哟！输入内容后，需回车确认呢！";
            case 5:
                return "欢迎进入MC我的世界。想致富，先撸树！对树长按哟！";
            case 6:
                return "掉血咯！";
            case 7:
                return "黑夜来临！找张床睡下去，一个人的黑夜就会很快过去哟！";
            default:
                return tmpStr;
        }
    }

    private String bl(int para1, int para2) {
        if (this.bTG == null) {
            Tc();
        }
        for (int i = 0; i < this.bTG.size(); i++) {
            e tmpTips = (e) this.bTG.get(i);
            if (tmpTips.bTH == para1) {
                String tmpStr = tmpTips.bTJ;
                this.bTG.remove(tmpTips);
                return tmpStr;
            }
        }
        return null;
    }

    private String bm(int para1, int para2) {
        if (this.bTF == null) {
            Tb();
        }
        for (int i = 0; i < this.bTF.size(); i++) {
            e tmpTips = (e) this.bTF.get(i);
            if (tmpTips.bTH == para1) {
                String tmpStr = tmpTips.bTJ;
                this.bTF.remove(tmpTips);
                return tmpStr;
            }
        }
        return null;
    }

    private String mT(int para1) {
        if (this.bTE == null) {
            Ta();
        }
        for (int i = 0; i < this.bTE.size(); i++) {
            e tmpTips = (e) this.bTE.get(i);
            if (tmpTips.bTH == para1) {
                String tmpStr = tmpTips.bTJ;
                this.bTE.remove(tmpTips);
                return tmpStr;
            }
        }
        return null;
    }
}
