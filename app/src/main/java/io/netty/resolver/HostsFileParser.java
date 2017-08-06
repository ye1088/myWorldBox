package io.netty.resolver;

import io.netty.util.NetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public final class HostsFileParser {
    private static final Pattern WHITESPACES = Pattern.compile("[ \t]+");
    private static final String WINDOWS_DEFAULT_SYSTEM_ROOT = "C:\\Windows";
    private static final String WINDOWS_HOSTS_FILE_RELATIVE_PATH = "\\system32\\drivers\\etc\\hosts";
    private static final String X_PLATFORMS_HOSTS_FILE_PATH = "/etc/hosts";
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(HostsFileParser.class);

    private static File locateHostsFile() {
        if (!PlatformDependent.isWindows()) {
            return new File(X_PLATFORMS_HOSTS_FILE_PATH);
        }
        File hostsFile = new File(System.getenv("SystemRoot") + WINDOWS_HOSTS_FILE_RELATIVE_PATH);
        if (hostsFile.exists()) {
            return hostsFile;
        }
        return new File("C:\\Windows\\system32\\drivers\\etc\\hosts");
    }

    public static Map<String, InetAddress> parseSilently() {
        File hostsFile = locateHostsFile();
        try {
            return parse(hostsFile);
        } catch (Throwable e) {
            logger.warn("Failed to load and parse hosts file at " + hostsFile.getPath(), e);
            return Collections.emptyMap();
        }
    }

    public static Map<String, InetAddress> parse() throws IOException {
        return parse(locateHostsFile());
    }

    public static Map<String, InetAddress> parse(File file) throws IOException {
        ObjectUtil.checkNotNull(file, "file");
        if (file.exists() && file.isFile()) {
            return parse(new BufferedReader(new FileReader(file)));
        }
        return Collections.emptyMap();
    }

    public static Map<String, InetAddress> parse(Reader reader) throws IOException {
        ObjectUtil.checkNotNull(reader, "reader");
        BufferedReader buff = new BufferedReader(reader);
        try {
            Map<String, InetAddress> entries = new HashMap();
            while (true) {
                String line = buff.readLine();
                if (line == null) {
                    break;
                }
                int commentPosition = line.indexOf(35);
                if (commentPosition != -1) {
                    line = line.substring(0, commentPosition);
                }
                line = line.trim();
                if (!line.isEmpty()) {
                    List<String> lineParts = new ArrayList();
                    for (String s : WHITESPACES.split(line)) {
                        if (!s.isEmpty()) {
                            lineParts.add(s);
                        }
                    }
                    if (lineParts.size() >= 2) {
                        byte[] ipBytes = NetUtil.createByteArrayFromIpAddressString((String) lineParts.get(0));
                        if (ipBytes != null) {
                            for (int i = 1; i < lineParts.size(); i++) {
                                String hostname = (String) lineParts.get(i);
                                String hostnameLower = hostname.toLowerCase(Locale.ENGLISH);
                                if (!entries.containsKey(hostnameLower)) {
                                    entries.put(hostnameLower, InetAddress.getByAddress(hostname, ipBytes));
                                }
                            }
                        }
                    }
                }
            }
            return entries;
        } finally {
            try {
                buff.close();
            } catch (Throwable e) {
                logger.warn("Failed to close a_isRightVersion reader", e);
            }
        }
    }

    private HostsFileParser() {
    }
}
