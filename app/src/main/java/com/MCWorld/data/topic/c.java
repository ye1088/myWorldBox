package com.MCWorld.data.topic;

import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.module.picture.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TopicDraft */
public class c {
    private String contact;
    private long pW;
    private List<b> photos = new ArrayList();
    private long postId;
    private String ra;
    private long rd;
    private List<UserBaseInfo> remindUsers = new ArrayList();
    private String title;

    public List<b> getPhotos() {
        return this.photos;
    }

    public void f(List<b> photos) {
        this.photos = photos;
    }

    public List<UserBaseInfo> getRemindUsers() {
        return this.remindUsers;
    }

    public void setRemindUsers(List<UserBaseInfo> remindUsers) {
        this.remindUsers = remindUsers;
    }

    public c(String title, String details, String contact) {
        this.title = title;
        this.ra = details;
        this.contact = contact;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String eE() {
        return this.ra;
    }

    public void aC(String details) {
        this.ra = details;
    }

    public long eF() {
        return this.postId;
    }

    public void m(long id) {
        this.postId = id;
    }

    public long eG() {
        return this.rd;
    }

    public void n(long tagId) {
        this.rd = tagId;
    }

    public long eH() {
        return this.pW;
    }

    public void o(long userId) {
        this.pW = userId;
    }
}
