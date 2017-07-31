package com.huluxia.widget.wheel.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.util.LinkedList;
import java.util.List;

/* compiled from: AbstractWheelAdapter */
public abstract class a implements f {
    private List<DataSetObserver> bGx;

    public View a(View convertView, ViewGroup parent) {
        return null;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        if (this.bGx == null) {
            this.bGx = new LinkedList();
        }
        this.bGx.add(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (this.bGx != null) {
            this.bGx.remove(observer);
        }
    }

    protected void Qt() {
        if (this.bGx != null) {
            for (DataSetObserver observer : this.bGx) {
                observer.onChanged();
            }
        }
    }

    protected void Qu() {
        if (this.bGx != null) {
            for (DataSetObserver observer : this.bGx) {
                observer.onInvalidated();
            }
        }
    }
}
