package hlx.ui.online;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.t;

class CreateRoomAdapter$a implements OnClickListener {
    private String ccq;
    final /* synthetic */ CreateRoomAdapter ccr;

    public CreateRoomAdapter$a(CreateRoomAdapter createRoomAdapter, String item) {
        this.ccr = createRoomAdapter;
        this.ccq = item;
    }

    public void onClick(View v) {
        t.o(CreateRoomAdapter.a(this.ccr), "啊哈哈哈");
        String str = this.ccq;
        Object obj = -1;
        switch (str.hashCode()) {
            case 25381656:
                if (str.equals(CreateRoomAdapter.cck)) {
                    obj = 1;
                    break;
                }
                break;
            case 687346878:
                if (str.equals(CreateRoomAdapter.ccl)) {
                    obj = 2;
                    break;
                }
                break;
            case 746199266:
                if (str.equals(CreateRoomAdapter.cco)) {
                    obj = 5;
                    break;
                }
                break;
            case 786815051:
                if (str.equals(CreateRoomAdapter.ccm)) {
                    obj = 3;
                    break;
                }
                break;
            case 865573413:
                if (str.equals(CreateRoomAdapter.ccn)) {
                    obj = 4;
                    break;
                }
                break;
            case 1123531214:
                if (str.equals(CreateRoomAdapter.ccj)) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
        }
    }
}
