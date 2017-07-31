package com.huluxia.data;

import com.huluxia.data.topic.a;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PageList extends ArrayList<Object> {
    private static final long serialVersionUID = 2;
    private int currPageNo;
    private int isTmp;
    private int pageSize;
    private List<UserBaseInfo> remindUsers;
    private a studioInfo;
    private int totalPage;

    public PageList() {
        this.remindUsers = new ArrayList();
        this.totalPage = 1;
        this.currPageNo = 1;
        this.pageSize = 1;
    }

    public PageList(int cur, int total, int size) {
        this.remindUsers = new ArrayList();
        this.totalPage = total;
        this.currPageNo = cur;
        this.pageSize = size;
    }

    public PageList(JSONObject json) throws JSONException {
        this.remindUsers = new ArrayList();
        this.totalPage = json.optInt("totalPage");
        this.currPageNo = json.optInt("currPageNo");
        this.pageSize = json.optInt("pageSize");
        JSONArray userArray = json.optJSONArray("remindUsers");
        if (userArray != null) {
            for (int i = 0; i < userArray.length(); i++) {
                this.remindUsers.add(new UserBaseInfo(userArray.optJSONObject(i)));
            }
        }
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPageNo() {
        return this.currPageNo;
    }

    public void setCurrPageNo(int currPageNo) {
        this.currPageNo = currPageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<UserBaseInfo> getRemindUsers() {
        return this.remindUsers;
    }

    public void setRemindUsers(List<UserBaseInfo> remindUsers) {
        this.remindUsers = remindUsers;
    }

    public a getStudioInfo() {
        return this.studioInfo;
    }

    public void setStudioInfo(a studioInfo) {
        this.studioInfo = studioInfo;
    }

    public int getIsTmp() {
        return this.isTmp;
    }

    public void setIsTmp(int isTmp) {
        this.isTmp = isTmp;
    }
}
