package com.huluxia.data;

/* compiled from: HTMsgRemind */
public class e {
    private boolean pe = true;
    private boolean pf = true;
    private boolean pg = false;
    private boolean ph = true;
    private boolean pi = true;
    private boolean pj = false;

    public boolean ef() {
        return this.pe;
    }

    public void w(boolean msg_sound) {
        this.pe = msg_sound;
    }

    public boolean isVibration() {
        return this.pf;
    }

    public void x(boolean vibration) {
        this.pf = vibration;
    }

    public void y(boolean chatmsgVibreate) {
        this.ph = chatmsgVibreate;
    }

    public boolean eg() {
        return this.ph;
    }

    public boolean eh() {
        return this.pg;
    }

    public void z(boolean browser) {
        this.pg = browser;
    }

    public boolean ei() {
        return this.pi;
    }

    public void A(boolean msg_notification) {
        this.pi = msg_notification;
    }

    public boolean ej() {
        return this.pj;
    }

    public void B(boolean msg_antinory) {
        this.pj = msg_antinory;
    }

    public boolean isSound() {
        return this.pe;
    }

    public void C(boolean msg_sound) {
        this.pe = msg_sound;
    }
}
