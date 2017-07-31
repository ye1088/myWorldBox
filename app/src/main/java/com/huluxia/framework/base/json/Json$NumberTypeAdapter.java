package com.huluxia.framework.base.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

class Json$NumberTypeAdapter implements JsonSerializer<Number> {
    private Json$NumberTypeAdapter() {
    }

    public JsonElement serialize(Number src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
