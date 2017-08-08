package com.MCWorld.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.n;
import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.mctool.e;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.utils.y;
import com.simple.colorful.setter.j;
import java.util.Locale;

public class AchieveActivity extends HTBaseLoadingActivity {
    private ProfileInfo aKG;
    private int[] beH = new int[]{f.login_day, f.game_day, f.instance_day, f.story_day, f.dig_day, f.enchanting_day, f.kill_day, f.death_day, f.map_day, f.js_day, f.skin_day, f.wood_day};
    private int[] beI = new int[]{f.login_night, f.game_night, f.instance_night, f.story_night, f.dig_night, f.enchanting_night, f.kill_night, f.death_night, f.map_night, f.js_night, f.skin_night, f.wood_night};
    private String[] beJ = new String[]{""};
    private String[] beK;
    private int[] beL;
    private boolean beM;
    private Activity mActivity;
    private Resources mResources;

    private class a extends BaseAdapter implements com.simple.colorful.b {
        final /* synthetic */ AchieveActivity beN;

        private a(AchieveActivity achieveActivity) {
            this.beN = achieveActivity;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            b holder;
            int i;
            int i2 = 4;
            if (convertView == null) {
                holder = new b();
                convertView = LayoutInflater.from(this.beN.mActivity).inflate(i.item_achieve, parent, false);
                holder.aRg = (PaintView) convertView.findViewById(g.image);
                holder.beO = (TextView) convertView.findViewById(g.achieve_name);
                holder.beP = (TextView) convertView.findViewById(g.achieve_number);
                holder.beQ = (RelativeLayout) convertView.findViewById(g.dividing_layout);
                holder.beR = convertView.findViewById(g.first_dividing_line);
                holder.beS = convertView.findViewById(g.second_dividing_line);
                holder.beT = convertView.findViewById(g.third_dividing_line);
                convertView.setTag(holder);
            } else {
                holder = (b) convertView.getTag();
            }
            View view = holder.beR;
            if (position % 2 == 0) {
                i = 4;
            } else {
                i = 0;
            }
            view.setVisibility(i);
            View view2 = holder.beT;
            if (position % 2 == 0) {
                i2 = 0;
            }
            view2.setVisibility(i2);
            holder.beQ.setVisibility(position % 2 == 0 ? 0 : 8);
            a(holder, position);
            holder.aRg.setImageResource(this.beN.beM ? this.beN.beH[position] : this.beN.beI[position]);
            holder.beO.setText(this.beN.beK[position]);
            holder.beP.setText(String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(this.beN.beL[position])}));
            return convertView;
        }

        private void a(b holder, int position) {
            Drawable drawable = null;
            Context a;
            int color;
            if (position == 0) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.login_day);
                } else {
                    color = this.beN.mResources.getColor(d.login_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 1) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.game_day);
                } else {
                    color = this.beN.mResources.getColor(d.game_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 2) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.instance_day);
                } else {
                    color = this.beN.mResources.getColor(d.instance_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 3) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.story_day);
                } else {
                    color = this.beN.mResources.getColor(d.story_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 4) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.dig_day);
                } else {
                    color = this.beN.mResources.getColor(d.dig_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 5) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.exchanting_day);
                } else {
                    color = this.beN.mResources.getColor(d.exchanting_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 6) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.kill_day);
                } else {
                    color = this.beN.mResources.getColor(d.kill_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 7) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.death_day);
                } else {
                    color = this.beN.mResources.getColor(d.death_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 8) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.map_day);
                } else {
                    color = this.beN.mResources.getColor(d.map_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 9) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.js_day);
                } else {
                    color = this.beN.mResources.getColor(d.js_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 10) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.skin_day);
                } else {
                    color = this.beN.mResources.getColor(d.skin_night);
                }
                drawable = y.e(a, color, 4);
            } else if (position == 11) {
                a = this.beN.mActivity;
                if (this.beN.beM) {
                    color = this.beN.mResources.getColor(d.wood_day);
                } else {
                    color = this.beN.mResources.getColor(d.wood_night);
                }
                drawable = y.e(a, color, 4);
            }
            holder.aRg.setBackgroundDrawable(drawable);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public Object getItem(int position) {
            return Integer.valueOf(this.beN.beH[position]);
        }

        public int getCount() {
            return this.beN.beH.length;
        }

        public void a(j setter) {
        }
    }

    public static class b {
        PaintView aRg;
        TextView beO;
        TextView beP;
        RelativeLayout beQ;
        View beR;
        View beS;
        View beT;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_achieve);
        this.mActivity = this;
        this.mResources = this.mActivity.getResources();
        this.aKG = (ProfileInfo) getIntent().getExtras().get("profileInfo");
        this.beM = com.simple.colorful.d.isDayMode();
        ej("个人成就");
        GridView gridView = (GridView) findViewById(g.achieve_grid);
        a gridViewAdapter = new a();
        this.beK = this.mActivity.getResources().getStringArray(com.MCWorld.bbs.b.b.achieve);
        this.beL = Jg();
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ AchieveActivity beN;

            {
                this.beN = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                t.a(this.beN.mActivity, position, this.beN.beL[position], this.beN.aKG);
            }
        });
    }

    private int[] Jg() {
        com.MCWorld.mctool.structure.a continuousLogonDay = e.Dk().Dl();
        int days = continuousLogonDay == null ? 0 : continuousLogonDay.apJ;
        int gameTime = e.Dk().wx();
        int insZoneBreakthroughStageClearCount = e.Dk().Dm();
        int storyModeGameTime = e.Dk().Dn();
        int digBlockCount = e.Dk().Dq();
        int enchatCount = e.Dk().Dr();
        int killMonsterCount = e.Dk().Do();
        int deathMonsterCount = e.Dk().Dp();
        int downMapCount = e.Dk().Ds();
        int downJsCount = e.Dk().Dt();
        int downSkinCount = e.Dk().Du();
        int downWoodCount = e.Dk().Dv();
        return new int[]{days * 100, (gameTime * 10) / 3600, insZoneBreakthroughStageClearCount * 10, (storyModeGameTime * 15) / 3600, digBlockCount, enchatCount, killMonsterCount, deathMonsterCount, downMapCount, downJsCount, downSkinCount, downWoodCount};
    }

    protected int AN() {
        return n.HtAppTheme;
    }

    protected int AO() {
        return n.HtAppTheme_Night;
    }
}
