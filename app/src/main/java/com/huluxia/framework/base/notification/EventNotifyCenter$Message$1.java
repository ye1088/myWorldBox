package com.huluxia.framework.base.notification;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.framework.base.notification.EventNotifyCenter.Message;

class EventNotifyCenter$Message$1 implements Creator<Message> {
    EventNotifyCenter$Message$1() {
    }

    public Message createFromParcel(Parcel source) {
        return new Message(source);
    }

    public Message[] newArray(int size) {
        return new Message[size];
    }
}
