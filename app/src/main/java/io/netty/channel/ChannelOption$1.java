package io.netty.channel;

import io.netty.util.ConstantPool;

class ChannelOption$1 extends ConstantPool<ChannelOption<Object>> {
    ChannelOption$1() {
    }

    protected ChannelOption<Object> newConstant(int id, String name) {
        return new ChannelOption(id, name, null);
    }
}
