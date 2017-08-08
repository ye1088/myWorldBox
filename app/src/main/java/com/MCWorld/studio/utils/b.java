package com.MCWorld.studio.utils;

import com.MCWorld.framework.base.utils.UtilsTime;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PastRankTimeData */
public class b {
    public List<a> aGU = new ArrayList();
    private int aGV = 8;
    private int startYear = 2016;

    public b() {
        Fg();
    }

    private void Fg() {
        long time = System.currentTimeMillis();
        int currentYear = UtilsTime.getYear(time);
        int currentMonth = UtilsTime.getMonth(time) - 1;
        if (currentYear - this.startYear > 0) {
            while (currentMonth > 0) {
                this.aGU.add(new a(currentYear, currentMonth));
                currentMonth--;
            }
            for (currentYear--; currentYear - this.startYear >= 0; currentYear--) {
                int i;
                if (currentYear - this.startYear == 0) {
                    for (i = 12; i >= this.aGV; i--) {
                        this.aGU.add(new a(currentYear, i));
                    }
                } else {
                    for (i = 12; i >= 1; i--) {
                        this.aGU.add(new a(currentYear, i));
                    }
                }
            }
            return;
        }
        while (currentMonth - this.aGV >= 0) {
            this.aGU.add(new a(currentYear, currentMonth));
            currentMonth--;
        }
    }
}
