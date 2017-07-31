package com.google.gson;

import java.lang.reflect.Type;

class Gson$2 implements JsonSerializationContext {
    final /* synthetic */ Gson this$0;

    Gson$2(Gson gson) {
        this.this$0 = gson;
    }

    public JsonElement serialize(Object src) {
        return this.this$0.toJsonTree(src);
    }

    public JsonElement serialize(Object src, Type typeOfSrc) {
        return this.this$0.toJsonTree(src, typeOfSrc);
    }
}
