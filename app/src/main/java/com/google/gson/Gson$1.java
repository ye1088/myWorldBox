package com.google.gson;

import java.lang.reflect.Type;

class Gson$1 implements JsonDeserializationContext {
    final /* synthetic */ Gson this$0;

    Gson$1(Gson gson) {
        this.this$0 = gson;
    }

    public <T> T deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        return this.this$0.fromJson(json, typeOfT);
    }
}
