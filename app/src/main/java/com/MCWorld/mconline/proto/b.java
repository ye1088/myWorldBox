package com.MCWorld.mconline.proto;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mconline.gameloc.http.f;
import com.MCWorld.mconline.proto.DTControlProto.DTControlInfo;
import com.MCWorld.mconline.proto.DTControlProto.ServerInfo;
import com.MCWorld.mconline.proto.DTControlProto.ServerInfo.a;
import io.netty.channel.Channel;

/* compiled from: MCTCPSession */
public class b {
    private static final Boolean amG = Boolean.valueOf(true);
    private static b amH;

    public static synchronized b BU() {
        b bVar;
        synchronized (b.class) {
            if (amH == null) {
                amH = new b();
            }
            bVar = amH;
        }
        return bVar;
    }

    public void b(f item) {
        a(item.server_ip, item.server_port, item.server_name, item.admin_name, item.admin_id, item.admin_avatar, item.game_version, item.server_curplayer, item.server_maxplayer, item.game_mode, item.game_type, item.game_size);
    }

    public void a(int inputRoomId, String inputServerIP, int inputServerPort) {
        try {
            a _tmpServerInfo = ServerInfo.newBuilder();
            _tmpServerInfo.dl(inputServerIP);
            _tmpServerInfo.aa((long) inputServerPort);
            _tmpServerInfo.ab((long) inputRoomId);
            _tmpServerInfo.dm("TEST");
            _tmpServerInfo.dn("TEST");
            _tmpServerInfo.do("TEST");
            _tmpServerInfo.dp("TEST");
            _tmpServerInfo.hW(999);
            _tmpServerInfo.hX(999);
            _tmpServerInfo.hY(999);
            _tmpServerInfo.hZ(999);
            _tmpServerInfo.ia(999);
            DTControlInfo.a _tmpDTControlInfo = DTControlInfo.newBuilder();
            _tmpDTControlInfo.hS(a.amC);
            _tmpDTControlInfo.dk("requestUpdateCreatRoom");
            _tmpDTControlInfo.a(_tmpServerInfo);
            _tmpDTControlInfo.Bs();
            com.MCWorld.mconline.gameloc.tcp.creator.a.AT().ao(_tmpDTControlInfo);
            if (amG.booleanValue()) {
                HLog.verbose("TAG", "DTPrint 客户端请求刷新创建的游戏房间;", new Object[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(String server_ip, int server_port, String server_name, String admin_name, long admin_id, String admin_avatar, String game_version, int server_curplayer, int server_maxplayer, int game_mode, int game_type, int game_size) {
        try {
            a _tmpServerInfo = ServerInfo.newBuilder();
            _tmpServerInfo.dl(server_ip);
            _tmpServerInfo.aa((long) server_port);
            _tmpServerInfo.dm(server_name);
            _tmpServerInfo.dn(admin_name);
            _tmpServerInfo.ab(admin_id);
            _tmpServerInfo.do(admin_avatar);
            _tmpServerInfo.dp(game_version);
            _tmpServerInfo.hW(server_curplayer);
            _tmpServerInfo.hX(server_maxplayer);
            _tmpServerInfo.hY(game_mode);
            _tmpServerInfo.hZ(game_type);
            _tmpServerInfo.ia(game_size);
            DTControlInfo.a _tmpDTControlInfo = DTControlInfo.newBuilder();
            _tmpDTControlInfo.hS(1281);
            _tmpDTControlInfo.dk("requestCreatRoom");
            _tmpDTControlInfo.a(_tmpServerInfo);
            _tmpDTControlInfo.Bs();
            com.MCWorld.mconline.gameloc.tcp.creator.a.AT().ao(_tmpDTControlInfo);
            if (amG.booleanValue()) {
                HLog.verbose("TAG", "DTPrint 客户端请求创建游戏房间;", new Object[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean a(Channel inputCtx, int inputRoomId, String inputRemoveIP, int inputRemovePort, String inputServerIP, int inputServerPort) {
        if (inputCtx == null || inputRemoveIP == null || inputRemovePort == 0) {
            return false;
        }
        try {
            inputCtx.writeAndFlush(new c(a.amA, inputRoomId, inputRemoveIP, inputRemovePort, inputServerIP, inputServerPort).c(0, null));
            System.out.println("DTPrint 请求发送成功 响应从游戏服务器移除玩家");
            return true;
        } catch (Exception e) {
            System.out.println("DTPrint 请求发送失败 " + e);
            return false;
        }
    }

    public boolean a(Channel inputCtx, int inputRoomId, String inputServerIP, int inputServerPort) {
        if (inputCtx == null || inputServerIP == null || inputServerPort == 0) {
            return false;
        }
        try {
            inputCtx.writeAndFlush(new c(a.amE, inputRoomId, inputServerIP, inputServerPort, inputServerIP, inputServerPort).c(0, null));
            System.out.println("DTPrint 请求发送成功 响应心跳包");
            return true;
        } catch (Exception e) {
            System.out.println("DTPrint 请求发送失败 " + e);
            return false;
        }
    }
}
