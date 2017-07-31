package com.huluxia.controller.resource.bean;

/* compiled from: TaskInfo */
public abstract class b {
    public String url;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        b taskInfo = (b) o;
        if (this.url != null) {
            if (this.url.equals(taskInfo.url)) {
                return true;
            }
        } else if (taskInfo.url == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.url != null ? this.url.hashCode() : 0;
    }
}
