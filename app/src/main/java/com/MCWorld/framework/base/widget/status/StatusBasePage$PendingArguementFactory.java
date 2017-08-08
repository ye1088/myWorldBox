package com.MCWorld.framework.base.widget.status;

import com.MCWorld.framework.base.widget.status.state.LoadingStatement;
import com.MCWorld.framework.base.widget.status.state.NetworkErrorStatement;
import com.MCWorld.framework.base.widget.status.state.NoDataStatement;
import com.MCWorld.framework.base.widget.status.state.ReloadStatement;

class StatusBasePage$PendingArguementFactory {
    private StatusBasePage$PendingArguementFactory() {
    }

    public static <T extends Statement> StatusBasePage$PendingArguement<T> newArguement(T statement) {
        StatusBasePage$PendingArguement<T> arguement = new StatusBasePage$PendingArguement();
        arguement.type = getType(statement);
        arguement.statement = statement;
        return arguement;
    }

    public static <T extends Statement> int getType(T statement) {
        if (statement instanceof LoadingStatement) {
            return 0;
        }
        if (statement instanceof NetworkErrorStatement) {
            return 3;
        }
        if (statement instanceof NoDataStatement) {
            return 2;
        }
        if (statement instanceof ReloadStatement) {
            return 1;
        }
        return 0;
    }
}
