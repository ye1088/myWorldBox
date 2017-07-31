package com.huawei.android.pushselfshow.richpush.favorites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.utils.a.c;
import com.huawei.android.pushselfshow.utils.d;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class a extends BaseAdapter {
    private Context a;
    private boolean b = true;
    private boolean c = false;
    private List d = new ArrayList();

    private static class a {
        ImageView a;
        TextView b;
        TextView c;
        CheckBox d;

        private a() {
        }
    }

    public a(Context context) {
        this.a = context;
    }

    public f a(int i) {
        return (f) this.d.get(i);
    }

    public List a() {
        return this.d;
    }

    public void a(int i, f fVar) {
        try {
            if (this.d.size() >= i) {
                this.d.set(i, fVar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(boolean z) {
        this.b = z;
        notifyDataSetChanged();
    }

    public void a(boolean z, Set set) {
        this.c = z;
        int i = 0;
        for (f fVar : this.d) {
            if (set == null || !set.contains(Integer.valueOf(i))) {
                fVar.a(z);
            } else {
                fVar.a(!z);
            }
            int i2 = i + 1;
            a(i, fVar);
            i = i2;
        }
        notifyDataSetChanged();
    }

    public void b() {
        this.d = c.a(this.a, null);
    }

    public int getCount() {
        return this.d.size();
    }

    public /* synthetic */ Object getItem(int i) {
        return a(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        View inflate;
        if (view == null) {
            try {
                aVar = new a();
                inflate = ((LayoutInflater) this.a.getSystemService("layout_inflater")).inflate(d.c(this.a, "hwpush_collection_item"), null);
            } catch (Exception e) {
                Exception exception = e;
                View view2 = view;
                e.b("PushSelfShowLog", exception.toString());
                return view2;
            }
            try {
                aVar.a = (ImageView) inflate.findViewById(d.d(this.a, "hwpush_favicon"));
                aVar.b = (TextView) inflate.findViewById(d.d(this.a, "hwpush_selfshowmsg_title"));
                aVar.c = (TextView) inflate.findViewById(d.d(this.a, "hwpush_selfshowmsg_content"));
                aVar.d = (CheckBox) inflate.findViewById(d.d(this.a, "hwpush_delCheck"));
                inflate.setTag(aVar);
            } catch (Exception e2) {
                Exception exception2 = e2;
                view2 = inflate;
                exception = exception2;
                e.b("PushSelfShowLog", exception.toString());
                return view2;
            }
        }
        aVar = (a) view.getTag();
        inflate = view;
        Bitmap d = ((f) this.d.get(i)).d();
        if (d == null) {
            d = BitmapFactory.decodeResource(this.a.getResources(), d.f(this.a, "hwpush_list_icon"));
        }
        aVar.a.setBackgroundDrawable(new BitmapDrawable(this.a.getResources(), d));
        CharSequence charSequence = ((f) this.d.get(i)).b().r;
        if (charSequence != null && charSequence.length() > 0) {
            aVar.b.setText(charSequence);
        }
        charSequence = ((f) this.d.get(i)).b().p;
        if (charSequence != null && charSequence.length() > 0) {
            aVar.c.setText(charSequence);
        }
        if (this.b) {
            aVar.d.setVisibility(4);
        } else {
            aVar.d.setVisibility(0);
            if (this.c || ((f) this.d.get(i)).a()) {
                aVar.d.setChecked(true);
            } else {
                aVar.d.setChecked(false);
            }
        }
        return inflate;
    }
}
