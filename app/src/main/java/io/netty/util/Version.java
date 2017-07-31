package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

public final class Version {
    private static final String PROP_BUILD_DATE = ".buildDate";
    private static final String PROP_COMMIT_DATE = ".commitDate";
    private static final String PROP_LONG_COMMIT_HASH = ".longCommitHash";
    private static final String PROP_REPO_STATUS = ".repoStatus";
    private static final String PROP_SHORT_COMMIT_HASH = ".shortCommitHash";
    private static final String PROP_VERSION = ".version";
    private final String artifactId;
    private final String artifactVersion;
    private final long buildTimeMillis;
    private final long commitTimeMillis;
    private final String longCommitHash;
    private final String repositoryStatus;
    private final String shortCommitHash;

    public static Map<String, Version> identify() {
        return identify(null);
    }

    public static Map<String, Version> identify(ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = PlatformDependent.getContextClassLoader();
        }
        Properties props = new Properties();
        InputStream in;
        try {
            Enumeration<URL> resources = classLoader.getResources("META-INF/io.netty.versions.properties");
            while (resources.hasMoreElements()) {
                in = ((URL) resources.nextElement()).openStream();
                props.load(in);
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
        } catch (Throwable th) {
            try {
                in.close();
            } catch (Exception e3) {
            }
        }
        Set<String> artifactIds = new HashSet();
        for (String k : props.keySet()) {
            String artifactId;
            int dotIndex = k.indexOf(46);
            if (dotIndex > 0) {
                artifactId = k.substring(0, dotIndex);
                if (props.containsKey(artifactId + PROP_VERSION)) {
                    if (props.containsKey(artifactId + PROP_BUILD_DATE)) {
                        if (props.containsKey(artifactId + PROP_COMMIT_DATE)) {
                            if (props.containsKey(artifactId + PROP_SHORT_COMMIT_HASH)) {
                                if (props.containsKey(artifactId + PROP_LONG_COMMIT_HASH)) {
                                    if (props.containsKey(artifactId + PROP_REPO_STATUS)) {
                                        artifactIds.add(artifactId);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Map<String, Version> versions = new TreeMap();
        for (String artifactId2 : artifactIds) {
            Map<String, Version> map = versions;
            map.put(artifactId2, new Version(artifactId2, props.getProperty(artifactId2 + PROP_VERSION), parseIso8601(props.getProperty(artifactId2 + PROP_BUILD_DATE)), parseIso8601(props.getProperty(artifactId2 + PROP_COMMIT_DATE)), props.getProperty(artifactId2 + PROP_SHORT_COMMIT_HASH), props.getProperty(artifactId2 + PROP_LONG_COMMIT_HASH), props.getProperty(artifactId2 + PROP_REPO_STATUS)));
        }
        return versions;
    }

    private static long parseIso8601(String value) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(value).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        for (Version v : identify().values()) {
            System.err.println(v);
        }
    }

    private Version(String artifactId, String artifactVersion, long buildTimeMillis, long commitTimeMillis, String shortCommitHash, String longCommitHash, String repositoryStatus) {
        this.artifactId = artifactId;
        this.artifactVersion = artifactVersion;
        this.buildTimeMillis = buildTimeMillis;
        this.commitTimeMillis = commitTimeMillis;
        this.shortCommitHash = shortCommitHash;
        this.longCommitHash = longCommitHash;
        this.repositoryStatus = repositoryStatus;
    }

    public String artifactId() {
        return this.artifactId;
    }

    public String artifactVersion() {
        return this.artifactVersion;
    }

    public long buildTimeMillis() {
        return this.buildTimeMillis;
    }

    public long commitTimeMillis() {
        return this.commitTimeMillis;
    }

    public String shortCommitHash() {
        return this.shortCommitHash;
    }

    public String longCommitHash() {
        return this.longCommitHash;
    }

    public String repositoryStatus() {
        return this.repositoryStatus;
    }

    public String toString() {
        return this.artifactId + '-' + this.artifactVersion + '.' + this.shortCommitHash + ("clean".equals(this.repositoryStatus) ? "" : " (repository: " + this.repositoryStatus + ')');
    }
}
