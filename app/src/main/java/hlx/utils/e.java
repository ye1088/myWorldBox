package hlx.utils;

import android.view.View;
import android.view.View.OnClickListener;
import java.util.Calendar;

/* compiled from: NoDoubleClickListener */
public abstract class e implements OnClickListener {
    public static final int cgN = 1000;
    private long cgO = 0;

    public abstract void c(View view);

    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - this.cgO > 1000) {
            this.cgO = currentTime;
            c(v);
        }
    }
}
