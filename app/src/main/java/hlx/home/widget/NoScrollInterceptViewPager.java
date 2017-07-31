package hlx.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.huluxia.widget.InterceptViewPager;

public class NoScrollInterceptViewPager extends InterceptViewPager {
    public NoScrollInterceptViewPager(Context context) {
        super(context);
    }

    public NoScrollInterceptViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
