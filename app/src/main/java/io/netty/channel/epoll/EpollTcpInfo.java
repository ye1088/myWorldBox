package io.netty.channel.epoll;

public final class EpollTcpInfo {
    final int[] info = new int[32];

    public int state() {
        return this.info[0] & 255;
    }

    public int caState() {
        return this.info[1] & 255;
    }

    public int retransmits() {
        return this.info[2] & 255;
    }

    public int probes() {
        return this.info[3] & 255;
    }

    public int backoff() {
        return this.info[4] & 255;
    }

    public int options() {
        return this.info[5] & 255;
    }

    public int sndWscale() {
        return this.info[6] & 255;
    }

    public int rcvWscale() {
        return this.info[7] & 255;
    }

    public long rto() {
        return ((long) this.info[8]) & 4294967295L;
    }

    public long ato() {
        return ((long) this.info[9]) & 4294967295L;
    }

    public long sndMss() {
        return ((long) this.info[10]) & 4294967295L;
    }

    public long rcvMss() {
        return ((long) this.info[11]) & 4294967295L;
    }

    public long unacked() {
        return ((long) this.info[12]) & 4294967295L;
    }

    public long sacked() {
        return ((long) this.info[13]) & 4294967295L;
    }

    public long lost() {
        return ((long) this.info[14]) & 4294967295L;
    }

    public long retrans() {
        return ((long) this.info[15]) & 4294967295L;
    }

    public long fackets() {
        return ((long) this.info[16]) & 4294967295L;
    }

    public long lastDataSent() {
        return ((long) this.info[17]) & 4294967295L;
    }

    public long lastAckSent() {
        return ((long) this.info[18]) & 4294967295L;
    }

    public long lastDataRecv() {
        return ((long) this.info[19]) & 4294967295L;
    }

    public long lastAckRecv() {
        return ((long) this.info[20]) & 4294967295L;
    }

    public long pmtu() {
        return ((long) this.info[21]) & 4294967295L;
    }

    public long rcvSsthresh() {
        return ((long) this.info[22]) & 4294967295L;
    }

    public long rtt() {
        return ((long) this.info[23]) & 4294967295L;
    }

    public long rttvar() {
        return ((long) this.info[24]) & 4294967295L;
    }

    public long sndSsthresh() {
        return ((long) this.info[25]) & 4294967295L;
    }

    public long sndCwnd() {
        return ((long) this.info[26]) & 4294967295L;
    }

    public long advmss() {
        return ((long) this.info[27]) & 4294967295L;
    }

    public long reordering() {
        return ((long) this.info[28]) & 4294967295L;
    }

    public long rcvRtt() {
        return ((long) this.info[29]) & 4294967295L;
    }

    public long rcvSpace() {
        return ((long) this.info[30]) & 4294967295L;
    }

    public long totalRetrans() {
        return ((long) this.info[31]) & 4294967295L;
    }
}
