package com.baidu.frontia;

import com.baidu.frontia.base.impl.FrontiaQueryImpl;
import org.json.JSONObject;

public class FrontiaQuery {
    private FrontiaQueryImpl a = new FrontiaQueryImpl();

    public enum SortOrder {
        ASC,
        DESC
    }

    void a(FrontiaQueryImpl frontiaQueryImpl) {
        this.a = frontiaQueryImpl;
    }

    public FrontiaQuery addSort(String str, SortOrder sortOrder) {
        if (sortOrder == SortOrder.ASC) {
            this.a.addSort(str, com.baidu.frontia.base.impl.FrontiaQueryImpl.SortOrder.ASC);
        } else {
            this.a.addSort(str, com.baidu.frontia.base.impl.FrontiaQueryImpl.SortOrder.DESC);
        }
        return this;
    }

    public FrontiaQuery all(String str, Object[] objArr) {
        this.a = this.a.all(str, objArr);
        return this;
    }

    public FrontiaQuery and(FrontiaQuery frontiaQuery) {
        this.a = this.a.and(frontiaQuery.a);
        return this;
    }

    public FrontiaQuery endsWith(String str, String str2) {
        this.a.endsWith(str, str2);
        return this;
    }

    public FrontiaQuery equals(String str, Object obj) {
        this.a = this.a.equals(str, obj);
        return this;
    }

    public int getLimit() {
        return this.a.getLimit();
    }

    public int getSkip() {
        return this.a.getSkip();
    }

    public JSONObject getSort() {
        return this.a.getSort();
    }

    public FrontiaQuery greaterThan(String str, Object obj) {
        this.a = this.a.greaterThan(str, obj);
        return this;
    }

    public FrontiaQuery greaterThanEqualTo(String str, Object obj) {
        this.a = this.a.greaterThanEqualTo(str, obj);
        return this;
    }

    public FrontiaQuery in(String str, Object[] objArr) {
        this.a = this.a.in(str, objArr);
        return this;
    }

    public FrontiaQuery lessThan(String str, Object obj) {
        this.a = this.a.lessThan(str, obj);
        return this;
    }

    public FrontiaQuery lessThanEqualTo(String str, Object obj) {
        this.a = this.a.lessThanEqualTo(str, obj);
        return this;
    }

    public FrontiaQuery not() {
        this.a.not();
        return this;
    }

    public FrontiaQuery notEqual(String str, Object obj) {
        this.a.notEqual(str, obj);
        return this;
    }

    public FrontiaQuery notIn(String str, Object[] objArr) {
        this.a = this.a.notIn(str, objArr);
        return this;
    }

    public FrontiaQuery or(FrontiaQuery frontiaQuery) {
        this.a = this.a.or(frontiaQuery.a);
        return this;
    }

    public FrontiaQuery regEx(String str, String str2) {
        this.a = this.a.regEx(str, str2);
        return this;
    }

    public FrontiaQuery setLimit(int i) {
        this.a.setLimit(i);
        return this;
    }

    public FrontiaQuery setSkip(int i) {
        this.a.setSkip(i);
        return this;
    }

    public FrontiaQuery size(String str, int i) {
        this.a.size(str, i);
        return this;
    }

    public FrontiaQuery startsWith(String str, String str2) {
        this.a = this.a.startsWith(str, str2);
        return this;
    }

    public JSONObject toJSONObject() {
        return this.a.toJSONObject();
    }
}
