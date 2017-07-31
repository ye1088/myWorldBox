package com.huluxia.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.f;
import com.huluxia.data.UserBaseInfo;
import com.simple.colorful.d;
import java.util.List;

/* compiled from: UtilsRole */
public class as {
    private static Drawable[] bmQ = new Drawable[]{null, null};

    public static boolean a(long userId, List<UserBaseInfo> users) {
        for (UserBaseInfo u : users) {
            if (userId == u.getUserID()) {
                return true;
            }
        }
        return false;
    }

    public static int a(Context context, UserBaseInfo user) {
        return g(context, user.getRole(), user.getGender());
    }

    public static int g(Context context, int role, int gender) {
        int nickColor = d.p(context, 16842806);
        switch (role) {
            case 0:
                nickColor = d.p(context, 16842806);
                break;
            case 1:
                nickColor = b.d.red_text_color;
                break;
            case 2:
                if (gender != 1) {
                    nickColor = b.d.blue_text_color;
                    break;
                }
                nickColor = b.d.pink_text_color;
                break;
        }
        return context.getResources().getColor(nickColor);
    }

    public static void b(ImageView v, int role) {
        switch (role) {
            case 0:
                v.setVisibility(8);
                return;
            case 1:
                v.setImageResource(f.ic_role_admin);
                v.setVisibility(0);
                return;
            case 2:
                v.setVisibility(8);
                return;
            default:
                return;
        }
    }

    public static void a(Context context, ImageView v, UserBaseInfo user) {
        a(context, v, user.getRole());
    }

    public static void a(Context context, ImageView v, int role) {
        switch (role) {
            case 0:
                v.setVisibility(8);
                return;
            case 1:
                v.setImageDrawable(d.o(context, c.drawableRoleVip));
                v.setVisibility(0);
                return;
            case 2:
                v.setVisibility(8);
                return;
            default:
                return;
        }
    }

    public static Drawable k(Context context, int sexid) {
        if (bmQ[0] == null) {
            bmQ[0] = context.getResources().getDrawable(f.g_icon_girl);
            bmQ[0].setBounds(0, 0, bmQ[0].getMinimumWidth(), bmQ[0].getMinimumHeight());
            bmQ[1] = context.getResources().getDrawable(f.g_icon_boy);
            bmQ[1].setBounds(0, 0, bmQ[1].getMinimumWidth(), bmQ[1].getMinimumHeight());
        }
        if (sexid == 1) {
            return bmQ[0];
        }
        return bmQ[1];
    }

    public static int lv(int level) {
        int resid = f.hulu01;
        switch (level) {
            case 1:
                return f.hulu01;
            case 2:
                return f.hulu02;
            case 3:
                return f.hulu03;
            case 4:
                return f.hulu04;
            case 5:
                return f.hulu05;
            case 6:
                return f.hulu06;
            case 7:
                return f.hulu07;
            case 8:
                return f.hulu08;
            case 9:
                return f.hulu09;
            case 10:
                return f.hulu10;
            case 11:
                return f.hulu11;
            case 12:
                return f.hulu12;
            case 13:
                return f.hulu13;
            case 14:
                return f.hulu14;
            case 15:
                return f.hulu15;
            default:
                return f.hulu15;
        }
    }

    public static Drawable l(Context context, int genderId) {
        return genderId == 1 ? d.o(context, c.drawableProfileGenderFemale) : d.o(context, c.drawableProfileGenderMale);
    }

    public static int m(Context context, int genderId) {
        return genderId == 1 ? d.getColor(context, c.textColorProfileFemale) : d.getColor(context, c.textColorProfileMale);
    }
}
