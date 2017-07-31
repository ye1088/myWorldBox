package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

class Gson$5 extends TypeAdapter<Number> {
    final /* synthetic */ Gson this$0;

    Gson$5(Gson gson) {
        this.this$0 = gson;
    }

    public Number read(JsonReader in) throws IOException {
        if (in.peek() != JsonToken.NULL) {
            return Long.valueOf(in.nextLong());
        }
        in.nextNull();
        return null;
    }

    public void write(JsonWriter out, Number value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());
        }
    }
}
