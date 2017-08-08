package com.MCWorld.controller.resource.handler.segments;

import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import java.util.HashSet;
import java.util.Set;

/* compiled from: SegmentTable */
public class f {
    public boolean on = false;
    public Set<a> oo = new HashSet();
    public String path;
    public long total;

    /* compiled from: SegmentTable */
    public static class a {
        public String id;
        public DownloadRecord mN;
        public b oq;
        public long or;
        public long start;

        public a(b url) {
            this.id = a.generateRecordId(url.url);
            this.oq = url;
        }

        public a(a segment) {
            this.id = segment.id;
            this.oq = segment.oq;
            this.start = segment.start;
            this.or = segment.or;
            this.mN = segment.mN;
        }

        public String toString() {
            return "Segment{id='" + this.id + '\'' + ", url='" + this.oq + '\'' + ", start=" + this.start + ", end=" + this.or + '}';
        }

        public a(b url, long start, long end) {
            this.id = a.generateRecordId(url.url);
            this.oq = url;
            this.start = start;
            this.or = end;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            a segment = (a) o;
            if (this.id != null) {
                return this.id.equals(segment.id);
            }
            if (segment.id != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.id != null ? this.id.hashCode() : 0;
        }
    }

    /* compiled from: SegmentTable */
    public static class b {
        public int rate = Integer.MAX_VALUE;
        public String url;
        public int weight;

        public b(String url, int weight, int rate) {
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

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            b that = (b) o;
            if (this.url != null) {
                return this.url.equals(that.url);
            }
            if (that.url != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.url != null ? this.url.hashCode() : 0;
        }

        public String toString() {
            return "SegmentUrl{url='" + this.url + '\'' + ", weight=" + this.weight + '}';
        }
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return dU();
    }

    public String toString() {
        return "SegmentTable{total=" + this.total + ", path='" + this.path + '\'' + ", err=" + this.on + ", segments=" + this.oo + '}';
    }

    public f dU() {
        f table = new f();
        table.total = this.total;
        table.path = this.path;
        table.on = this.on;
        table.oo = new HashSet(this.oo.size());
        for (a segment : this.oo) {
            table.oo.add(new a(segment));
        }
        return table;
    }
}
