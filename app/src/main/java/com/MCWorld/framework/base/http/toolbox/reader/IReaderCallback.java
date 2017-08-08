package com.MCWorld.framework.base.http.toolbox.reader;

public interface IReaderCallback<T extends Throwable, E extends Throwable> {
    void end() throws Throwable;

    void readLoop(int i) throws Throwable;
}
