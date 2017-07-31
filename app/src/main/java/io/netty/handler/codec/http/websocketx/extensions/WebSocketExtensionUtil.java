package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebSocketExtensionUtil {
    private static final String EXTENSION_SEPARATOR = ",";
    private static final Pattern PARAMETER = Pattern.compile("^([^=]+)(=[\\\"]?([^\\\"]+)[\\\"]?)?$");
    private static final char PARAMETER_EQUAL = '=';
    private static final String PARAMETER_SEPARATOR = ";";

    static boolean isWebsocketUpgrade(HttpHeaders headers) {
        return headers.containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, true) && headers.contains(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET, true);
    }

    public static List<WebSocketExtensionData> extractExtensions(String extensionHeader) {
        String[] rawExtensions = extensionHeader.split(",");
        if (rawExtensions.length <= 0) {
            return Collections.emptyList();
        }
        List<WebSocketExtensionData> arrayList = new ArrayList(rawExtensions.length);
        for (String rawExtension : rawExtensions) {
            Map<String, String> parameters;
            String[] extensionParameters = rawExtension.split(PARAMETER_SEPARATOR);
            String name = extensionParameters[0].trim();
            if (extensionParameters.length > 1) {
                parameters = new HashMap(extensionParameters.length - 1);
                for (int i = 1; i < extensionParameters.length; i++) {
                    Matcher parameterMatcher = PARAMETER.matcher(extensionParameters[i].trim());
                    if (parameterMatcher.matches() && parameterMatcher.group(1) != null) {
                        parameters.put(parameterMatcher.group(1), parameterMatcher.group(3));
                    }
                }
            } else {
                parameters = Collections.emptyMap();
            }
            arrayList.add(new WebSocketExtensionData(name, parameters));
        }
        return arrayList;
    }

    static String appendExtension(String currentHeaderValue, String extensionName, Map<String, String> extensionParameters) {
        int length;
        if (currentHeaderValue != null) {
            length = currentHeaderValue.length();
        } else {
            length = extensionName.length() + 1;
        }
        StringBuilder newHeaderValue = new StringBuilder(length);
        if (!(currentHeaderValue == null || currentHeaderValue.trim().isEmpty())) {
            newHeaderValue.append(currentHeaderValue);
            newHeaderValue.append(",");
        }
        newHeaderValue.append(extensionName);
        for (Entry<String, String> extensionParameter : extensionParameters.entrySet()) {
            newHeaderValue.append(PARAMETER_SEPARATOR);
            newHeaderValue.append((String) extensionParameter.getKey());
            if (extensionParameter.getValue() != null) {
                newHeaderValue.append(PARAMETER_EQUAL);
                newHeaderValue.append((String) extensionParameter.getValue());
            }
        }
        return newHeaderValue.toString();
    }

    private WebSocketExtensionUtil() {
    }
}
