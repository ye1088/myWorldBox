package com.huluxia.utils;

import android.util.SparseArray;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import com.tencent.smtt.sdk.TbsReaderView.ReaderCallback;

/* compiled from: UtilsError */
public class ab {
    private static SparseArray<String> blL = new SparseArray();

    static {
        blL.put(100, "服务器异常，请联系客服。");
        blL.put(101, "请求参数不能为空");
        blL.put(102, "参数不合法");
        blL.put(103, "登陆失效，请重新登陆");
        blL.put(106, "验证码错误");
        blL.put(107, "设备更换，重新验证");
        blL.put(108, "请更换使用注册时的QQ号登录");
        blL.put(ErrorCode.ERROR_HOSTAPP_UNAVAILABLE, "该邮箱已被注册");
        blL.put(ErrorCode.ERROR_FORCE_SYSWEBVIEW, "账号或密码错误");
        blL.put(ErrorCode.ERROR_NOMATCH_COREVERSION, "头像不能为空");
        blL.put(1004, "第三方登陆失败，该第三方账号未和应用账号绑定");
        blL.put(1011, "当前用户已绑定其他此类型第三方账号");
        blL.put(1012, "此第三方账号已与其他用户绑定");
        blL.put(1013, "当前用户与该第三方账号已绑定过");
        blL.put(1014, "账号被屏蔽，无法登陆");
        blL.put(1015, "设备被屏蔽，无法登陆");
        blL.put(1016, "设备被屏蔽，无法注册");
        blL.put(2001, "未找到上传的图片");
        blL.put(2002, "上传图片大小超出限制");
        blL.put(2003, "图片类型错误");
        blL.put(2101, "未找到上传的音频");
        blL.put(2102, "上传音频大小超出限制");
        blL.put(2103, "音频类型错误");
        blL.put(3001, "活动不存在或者已被删除");
        blL.put(3011, "活动报名人数已满");
        blL.put(3012, "活动不存在");
        blL.put(3013, "确认者不是活动发起者");
        blL.put(3021, "活动不存在或者已经被删除");
        blL.put(3022, "您并没有参与该活动");
        blL.put(3023, "活动未开始，不能签到");
        blL.put(3024, "活动已过期，不能签到");
        blL.put(3031, "活动不存在或者已被删除");
        blL.put(3032, "不能报名自己发布的活动");
        blL.put(3033, "需要带礼物才能参加，或者指定礼物不存在");
        blL.put(3034, "金币不足，请充值");
        blL.put(3041, "活动不存在或者已被删除");
        blL.put(3042, "签到申请无效");
        blL.put(3043, "不是礼物模式活动，不能签到确认");
        blL.put(3044, "活动未开始，不能签到确认");
        blL.put(3045, "活动已过期，不能签到确认");
        blL.put(3046, "不是送礼物活动发起者，没有权限签到确认");
        blL.put(3047, "不是收礼物活动报名者，没有权限签到确认");
        blL.put(3051, "多人聚会时人数不能少于3");
        blL.put(3052, "多人聚会时人数不能少于3");
        blL.put(3053, "金币不足，请充值");
        blL.put(3054, "发活动数超过限制");
        blL.put(3061, "活动不存在或者已被删除值");
        blL.put(3062, "未报名该活动，不能取消");
        blL.put(3063, "报名已通过，不能取消");
        blL.put(3064, "退还礼物金币失败");
        blL.put(3071, "活动不存在");
        blL.put(4001, "用户不存在或已被封禁");
        blL.put(4002, "用户基本信息不存在");
        blL.put(4003, "旧密码不正确");
        blL.put(4012, "您当前没有活动，不能邀请");
        blL.put(ReaderCallback.HIDDEN_BAR, "不能送礼物给自己");
        blL.put(ReaderCallback.SHOW_BAR, "礼物不存在");
        blL.put(ReaderCallback.COPY_SELECT_TEXT, "金币不足，请充值");
        blL.put(ReaderCallback.SEARCH_SELECT_TEXT, "已经超过赠送上限");
        blL.put(ReaderCallback.READER_TOAST, "绣球不能连续抛给同一人");
        blL.put(6001, "没有上传照片");
        blL.put(6002, "该照片不存在");
        blL.put(6003, "该照片不是本人上传的，不能删除");
        blL.put(6004, "喜欢的照片不存在");
        blL.put(7001, "指定关系的用户不存在");
        blL.put(7002, "关注数达到上限(500)");
        blL.put(9001, "兑换指定商品不存在或者已被删除");
        blL.put(9002, "实物商品未指定分发方式(快递或折现)");
        blL.put(9003, "礼券不足");
    }

    public static String n(int code, String msg) {
        return code == 104 ? msg : (String) blL.get(code);
    }
}
