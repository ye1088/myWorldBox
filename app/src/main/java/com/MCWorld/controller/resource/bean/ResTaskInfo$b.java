package com.MCWorld.controller.resource.bean;

import com.MCWorld.controller.resource.handler.segments.f.b;

public class ResTaskInfo$b {
    public int rate = Integer.MAX_VALUE;
    public String url;
    public int weight = 1;

    public ResTaskInfo$b(String url, int weight, int rate) {
        this.url = url;
        if (weight <= 0) {
            weight = 1;
        }
        this.weight = weight;
        if (rate <= 0) {
            rate = Integer.MAX_VALUE;
        }
        this.rate = rate;
    }

    public ResTaskInfo$b(b url) {
        this.url = url.url;
        this.weight = url.weight;
        this.rate = url.rate;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResTaskInfo$b that = (ResTaskInfo$b) o;
        if (this.url != null) {
            return this.url.equals(that.url);
        }
        if (that.url != null) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "SegmentUrl{url='" + this.url + '\'' + ", weight=" + this.weight + ", rate=" + this.rate + '}';
    }

    public int hashCode() {
        return this.url != null ? this.url.hashCode() : 0;
    }
}
