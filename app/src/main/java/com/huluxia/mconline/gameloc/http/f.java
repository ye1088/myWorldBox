package com.huluxia.mconline.gameloc.http;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: RoomCreateItem */
public class f implements Parcelable {
    public static final Creator<f> CREATOR = new 1();
    public String admin_avatar;
    public long admin_id;
    public String admin_name;
    public String client_ip;
    public int game_mode;
    public int game_size;
    public int game_type;
    public String game_version;
    public String online_ip;
    public int online_port;
    public int server_curplayer;
    public String server_ip;
    public int server_maxplayer = 5;
    public String server_name;
    public int server_port;
    public long studio_id;
    public String studio_name;
    public boolean tcp_register;

    public void fillAdmin(long admin_id, String admin_name, String admin_avatar) {
        this.admin_id = admin_id;
        this.admin_name = admin_name;
        this.admin_avatar = admin_avatar;
    }

    public void fillGame(String server_name, String game_version, int game_mode, int game_type, int game_size, int server_curplayer, int server_maxplayer) {
        this.server_name = server_name;
        this.game_version = game_version;
        this.game_mode = game_mode;
        this.game_type = game_type;
        this.game_size = game_size;
        this.server_curplayer = server_curplayer;
        this.server_maxplayer = server_maxplayer;
    }

    public void fillStudio(long studio_id, String studio_name) {
        this.studio_id = studio_id;
        this.studio_name = studio_name;
    }

    public void fillIp(String online_ip, int online_port, String client_ip, String server_ip, int server_port) {
        this.online_ip = online_ip;
        this.online_port = online_port;
        this.client_ip = client_ip;
        this.server_ip = server_ip;
        this.server_port = server_port;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.online_ip);
        dest.writeInt(this.online_port);
        dest.writeString(this.client_ip);
        dest.writeString(this.server_ip);
        dest.writeInt(this.server_port);
        dest.writeString(this.server_name);
        dest.writeLong(this.admin_id);
        dest.writeString(this.admin_name);
        dest.writeString(this.admin_avatar);
        dest.writeLong(this.studio_id);
        dest.writeString(this.studio_name);
        dest.writeString(this.game_version);
        dest.writeInt(this.server_curplayer);
        dest.writeInt(this.server_maxplayer);
        dest.writeInt(this.game_mode);
        dest.writeInt(this.game_type);
        dest.writeInt(this.game_size);
        dest.writeByte(this.tcp_register ? (byte) 1 : (byte) 0);
    }

    protected f(Parcel in) {
        this.online_ip = in.readString();
        this.online_port = in.readInt();
        this.client_ip = in.readString();
        this.server_ip = in.readString();
        this.server_port = in.readInt();
        this.server_name = in.readString();
        this.admin_id = in.readLong();
        this.admin_name = in.readString();
        this.admin_avatar = in.readString();
        this.studio_id = in.readLong();
        this.studio_name = in.readString();
        this.game_version = in.readString();
        this.server_curplayer = in.readInt();
        this.server_maxplayer = in.readInt();
        this.game_mode = in.readInt();
        this.game_type = in.readInt();
        this.game_size = in.readInt();
        this.tcp_register = in.readByte() != (byte) 0;
    }
}
