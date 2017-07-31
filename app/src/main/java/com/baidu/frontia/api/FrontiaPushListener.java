package com.baidu.frontia.api;

import com.baidu.frontia.api.FrontiaPushUtil.MessageContent;
import com.baidu.frontia.api.FrontiaPushUtil.Trigger;
import java.util.List;
import org.json.JSONObject;

public interface FrontiaPushListener {

    public interface CommonMessageListener {
        void onFailure(int i, String str);

        void onSuccess();
    }

    public interface DescribeMessageListener {
        void onFailure(int i, String str);

        void onSuccess(DescribeMessageResult describeMessageResult);
    }

    public static class DescribeMessageResult {
        private com.baidu.frontia.module.push.FrontiaPushListenerImpl.DescribeMessageResult a;

        DescribeMessageResult(com.baidu.frontia.module.push.FrontiaPushListenerImpl.DescribeMessageResult describeMessageResult) {
            this.a = describeMessageResult;
        }

        public String getChannelId() {
            return this.a.getChannelId();
        }

        public JSONObject getExtras() {
            return this.a.getExtras();
        }

        public String getId() {
            return this.a.getId();
        }

        public MessageContent getMessage() {
            return new MessageContent(this.a.getMessage());
        }

        public String getTag() {
            return this.a.getTag();
        }

        public Trigger getTrigger() {
            return new Trigger(this.a.getTrigger());
        }

        public String getUserId() {
            return this.a.getUserId();
        }
    }

    public interface ListMessageListener {
        void onFailure(int i, String str);

        void onSuccess(List<DescribeMessageResult> list);
    }

    public interface PushMessageListener {
        void onFailure(int i, String str);

        void onSuccess(String str);
    }
}
