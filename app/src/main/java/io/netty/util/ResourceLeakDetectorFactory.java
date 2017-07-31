package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class ResourceLeakDetectorFactory {
    private static volatile ResourceLeakDetectorFactory factoryInstance = new DefaultResourceLeakDetectorFactory();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetectorFactory.class);

    private static final class DefaultResourceLeakDetectorFactory extends ResourceLeakDetectorFactory {
        private final Constructor<?> customClassConstructor;

        DefaultResourceLeakDetectorFactory() {
            String customLeakDetector;
            try {
                customLeakDetector = (String) AccessController.doPrivileged(new PrivilegedAction<String>() {
                    public String run() {
                        return SystemPropertyUtil.get("io.netty.customResourceLeakDetector");
                    }
                });
            } catch (Throwable cause) {
                ResourceLeakDetectorFactory.logger.error("Could not access System property: io.netty.customResourceLeakDetector", cause);
                customLeakDetector = null;
            }
            this.customClassConstructor = customLeakDetector == null ? null : customClassConstructor(customLeakDetector);
        }

        private static Constructor<?> customClassConstructor(String customLeakDetector) {
            try {
                Class<?> detectorClass = Class.forName(customLeakDetector, true, PlatformDependent.getSystemClassLoader());
                if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
                    return detectorClass.getConstructor(new Class[]{Class.class, Integer.TYPE, Long.TYPE});
                }
                ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", (Object) customLeakDetector);
                return null;
            } catch (Throwable t) {
                ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, t);
            }
        }

        public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource, int samplingInterval, long maxActive) {
            if (this.customClassConstructor != null) {
                try {
                    ResourceLeakDetector<T> leakDetector = (ResourceLeakDetector) this.customClassConstructor.newInstance(new Object[]{resource, Integer.valueOf(samplingInterval), Long.valueOf(maxActive)});
                    ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", this.customClassConstructor.getDeclaringClass().getName());
                    return leakDetector;
                } catch (Throwable t) {
                    ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.customClassConstructor.getDeclaringClass().getName(), resource, t);
                }
            }
            ResourceLeakDetector<T> resourceLeakDetector = new ResourceLeakDetector(resource, samplingInterval, maxActive);
            ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", (Object) resourceLeakDetector);
            return resourceLeakDetector;
        }
    }

    public abstract <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> cls, int i, long j);

    public static ResourceLeakDetectorFactory instance() {
        return factoryInstance;
    }

    public static void setResourceLeakDetectorFactory(ResourceLeakDetectorFactory factory) {
        factoryInstance = (ResourceLeakDetectorFactory) ObjectUtil.checkNotNull(factory, "factory");
    }

    public final <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> resource) {
        return newResourceLeakDetector(resource, 128, Long.MAX_VALUE);
    }
}
