package com.MCWorld.framework.base.widget.status;

class StatusBasePage$PendingArguement<T extends Statement> {
    public static final int TYPE_LOADING = 0;
    public static final int TYPE_NETWORK_ERR = 3;
    public static final int TYPE_NODATA = 2;
    public static final int TYPE_RELOAD = 1;
    public T statement;
    public int type;

    private StatusBasePage$PendingArguement() {
    }
}
