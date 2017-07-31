package io.netty.channel;

import io.netty.util.internal.StringUtil;

public class ReflectiveChannelFactory<T extends Channel> implements ChannelFactory<T> {
    private final Class<? extends T> clazz;

    public ReflectiveChannelFactory(Class<? extends T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        this.clazz = clazz;
    }

    public T newChannel() {
        try {
            return (Channel) this.clazz.newInstance();
        } catch (Throwable t) {
            ChannelException channelException = new ChannelException("Unable to create Channel from class " + this.clazz, t);
        }
    }

    public String toString() {
        return StringUtil.simpleClassName(this.clazz) + ".class";
    }
}
