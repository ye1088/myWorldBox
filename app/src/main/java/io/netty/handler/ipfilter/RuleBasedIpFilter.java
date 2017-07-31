package io.netty.handler.ipfilter;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import java.net.InetSocketAddress;

@Sharable
public class RuleBasedIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final IpFilterRule[] rules;

    public RuleBasedIpFilter(IpFilterRule... rules) {
        if (rules == null) {
            throw new NullPointerException("rules");
        }
        this.rules = rules;
    }

    protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
        IpFilterRule[] arr$ = this.rules;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            IpFilterRule rule = arr$[i$];
            if (rule == null) {
                return true;
            }
            if (rule.matches(remoteAddress)) {
                return rule.ruleType() == IpFilterRuleType.ACCEPT;
            } else {
                i$++;
            }
        }
        return true;
    }
}
