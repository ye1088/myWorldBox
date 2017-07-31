package com.integralblue.httpresponsecache.compat.java.net;

import java.util.regex.Pattern;

public class InetAddress {
    private static final Pattern IPV4_PATTERN = Pattern.compile("\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z");
    private static final Pattern IPV6_6HEX4DEC_PATTERN = Pattern.compile("\\A((?:[0-9A-Fa-f]{1,4}:){6,6})(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z");
    private static final Pattern IPV6_HEX4DECCOMPRESSED_PATTERN = Pattern.compile("\\A((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?) ::((?:[0-9A-Fa-f]{1,4}:)*)(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z");
    private static final Pattern IPV6_HEXCOMPRESSED_PATTERN = Pattern.compile("\\A((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)\\z");
    private static final Pattern IPV6_PATTERN = Pattern.compile("\\A(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}\\z");

    public static boolean isNumeric(String address) {
        return IPV4_PATTERN.matcher(address).matches() || IPV6_HEX4DECCOMPRESSED_PATTERN.matcher(address).matches() || IPV6_6HEX4DEC_PATTERN.matcher(address).matches() || IPV6_HEXCOMPRESSED_PATTERN.matcher(address).matches() || IPV6_PATTERN.matcher(address).matches();
    }
}
