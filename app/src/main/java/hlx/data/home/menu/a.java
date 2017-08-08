package hlx.data.home.menu;

import com.MCWorld.framework.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ItemList */
public class a {
    public static a bIT = new a(R.drawable.menu_follow, "我的关注", false);
    public static List<Object> list;

    /* compiled from: ItemList */
    public static class a {
        public int bIU;
        public boolean bIV;
        public int count;
        public String title;

        public a(int titleImgResource, String title, boolean hideGoImg) {
            this.bIU = titleImgResource;
            this.title = title;
            this.bIV = hideGoImg;
            this.count = 0;
        }

        public a(int titleImgResource, String title, boolean hideGoImg, int count) {
            this.bIU = titleImgResource;
            this.title = title;
            this.bIV = hideGoImg;
            this.count = count;
        }
    }

    static {
        list = null;
        list = new ArrayList();
        list.add(bIT);
        list.add(new b(1));
        list.add(new a(R.drawable.menu_gamble, "买定离手", false));
        list.add(new a(R.drawable.menu_exchange, "兑换中心", false));
        list.add(new b(2));
    }
}
