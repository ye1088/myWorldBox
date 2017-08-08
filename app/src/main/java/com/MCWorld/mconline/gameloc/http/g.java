package com.MCWorld.mconline.gameloc.http;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: RoomInfo */
public class g implements Parcelable {
    public static final Creator<g> CREATOR = new 1();
    public String admin_avatar;
    public long admin_id;
    public String admin_name;
    public int game_mode;
    public String game_mode_name;
    public int game_size;
    public int game_type;
    public String game_type_name;
    public String game_version;
    public String online_ip;
    public int online_port;
    public long room_align;
    public int room_no;
    public int server_host;
    public String server_ip;
    public int server_maxplayer;
    public String server_name;
    public int server_player;
    public int server_port;
    public long studio_id;
    public String studio_name;

    public static void setRoomInfo(g out, g in) {
        out.online_ip = in.online_ip;
        out.online_port = in.online_port;
        out.room_no = in.room_no;
        out.server_ip = in.server_ip;
        out.server_port = in.server_port;
        out.server_name = in.server_name;
        out.server_host = in.server_host;
        out.server_player = in.server_player;
        out.server_maxplayer = in.server_maxplayer;
        out.admin_id = in.admin_id;
        out.admin_name = in.admin_name;
        out.admin_avatar = in.admin_avatar;
        out.studio_id = in.studio_id;
        out.studio_name = in.studio_name;
        out.game_version = in.game_version;
        out.game_mode = in.game_mode;
        out.game_type = in.game_type;
        out.game_size = in.game_size;
        out.room_align = in.room_align;
        out.game_mode_name = in.game_mode_name;
        out.game_type_name = in.game_type_name;
    }

    public static void setRoomInfo(f out, g in) {
        out.online_ip = in.online_ip;
        out.online_port = in.online_port;
        out.server_ip = in.server_ip;
        out.server_port = in.server_port;
        out.server_name = in.server_name;
        out.server_curplayer = 0;
        out.server_maxplayer = in.server_maxplayer;
        out.admin_id = in.admin_id;
        out.admin_name = in.admin_name;
        out.admin_avatar = in.admin_avatar;
        out.studio_id = in.studio_id;
        out.studio_name = in.studio_name;
        out.game_version = in.game_version;
        out.game_mode = in.game_mode;
        out.game_type = in.game_type;
        out.game_size = in.game_size;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.online_ip);
        dest.writeInt(this.online_port);
        dest.writeInt(this.room_no);
        dest.writeString(this.server_ip);
        dest.writeInt(this.server_port);
        dest.writeString(this.server_name);
        dest.writeInt(this.server_host);
        dest.writeInt(this.server_player);
        dest.writeInt(this.server_maxplayer);
        dest.writeLong(this.admin_id);
        dest.writeString(this.admin_name);
        dest.writeString(this.admin_avatar);
        dest.writeLong(this.studio_id);
        dest.writeString(this.studio_name);
        dest.writeString(this.game_version);
        dest.writeInt(this.game_mode);
        dest.writeInt(this.game_type);
        dest.writeInt(this.game_size);
        dest.writeLong(this.room_align);
        dest.writeString(this.game_mode_name);
        dest.writeString(this.game_type_name);
    }

    protected g(Parcel in) {
        this.online_ip = in.readString();
        this.online_port = in.readInt();
        this.room_no = in.readInt();
        this.server_ip = in.readString();
        this.server_port = in.readInt();
        this.server_name = in.readString();
        this.server_host = in.readInt();
        this.server_player = in.readInt();
        this.server_maxplayer = in.readInt();
        this.admin_id = in.readLong();
        this.admin_name = in.readString();
        this.admin_avatar = in.readString();
        this.studio_id = in.readLong();
        this.studio_name = in.readString();
        this.game_version = in.readString();
        this.game_mode = in.readInt();
        this.game_type = in.readInt();
        this.game_size = in.readInt();
        this.room_align = in.readLong();
        this.game_mode_name = in.readString();
        this.game_type_name = in.readString();
    }
}
