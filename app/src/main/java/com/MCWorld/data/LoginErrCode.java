package com.MCWorld.data;

public enum LoginErrCode {
    ERR_DEFINE(104),
    ERR_OPENID(109),
    ERR_QQ(110),
    ERR_QQ_BIND(111),
    ERR_QQ_NULL(112),
    ERR_113(113),
    ERR_114(114),
    ERR_115(115),
    ERR_116(116),
    ERR_117(117),
    ERR_121(121),
    OK(200),
    OK_201(201);
    
    private String m_msg;
    private int m_val;

    private LoginErrCode(int val) {
        this.m_val = val;
        switch (val) {
            case 104:
                this.m_msg = "";
                return;
            case 109:
                this.m_msg = "QQ验证失败。\n请用注册时关联的QQ进行验证。";
                return;
            case 110:
                this.m_msg = "该QQ已关联了其他3楼账号，请在右上角切换成其他QQ号。";
                return;
            case 111:
                this.m_msg = "该用户已在其他设备上成功验证";
                return;
            case 112:
                this.m_msg = "鉴权信息不存在。\n点击确定跳转到QQ界面登录QQ进行验证。";
                return;
            case 113:
                this.m_msg = "QQ验证失败。\nQQ号和注册时使用的QQ号不同。\n稍后请到个人空间重新验证QQ。";
                return;
            case 114:
                this.m_msg = "鉴权信息更新失败。\n稍后请到个人空间重新验证QQ。";
                return;
            case 115:
                this.m_msg = "QQ验证失败。\nQQ号和注册时使用的QQ号不同。\n请重新验证。";
                return;
            case 116:
                this.m_msg = "鉴权信息更新失败。\n请重新验证。";
                return;
            case 117:
                this.m_msg = "为了您的账号安全。请到安全中心验证QQ号。";
                return;
            case 121:
                this.m_msg = "QQ验证成功";
                return;
            case 200:
                this.m_msg = "登录成功";
                return;
            case 201:
                this.m_msg = "验证成功。你的账号已被保护。";
                return;
            default:
                return;
        }
    }

    public int Value() {
        return this.m_val;
    }

    public String Msg() {
        return this.m_msg;
    }
}
