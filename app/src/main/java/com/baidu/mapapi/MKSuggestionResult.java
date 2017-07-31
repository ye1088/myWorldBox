package com.baidu.mapapi;

import java.util.ArrayList;

public class MKSuggestionResult {
    private int a = 0;
    private ArrayList<MKSuggestionInfo> b;

    void a(ArrayList<MKSuggestionInfo> arrayList) {
        this.b = arrayList;
    }

    public ArrayList<MKSuggestionInfo> getAllSuggestions() {
        return this.b;
    }

    public MKSuggestionInfo getSuggestion(int i) {
        return (this.b == null || this.a <= i) ? null : (MKSuggestionInfo) this.b.get(i);
    }

    public int getSuggestionNum() {
        if (this.b != null) {
            this.a = this.b.size();
        } else {
            this.a = 0;
        }
        return this.a;
    }
}
