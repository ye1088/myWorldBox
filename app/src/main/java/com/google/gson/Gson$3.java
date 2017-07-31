package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

class Gson$3 extends TypeAdapter<Number> {
    final /* synthetic */ Gson this$0;

    Gson$3(Gson gson) {
        this.this$0 = gson;
    }

    public Double read(JsonReader in) throws IOException {
        if (in.peek() != JsonToken.NULL) {
            return Double.valueOf(in.nextDouble());
        }
        in.nextNull();
        return null;
    }

    public void write(JsonWriter out, Number value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        Gson.access$000(this.this$0, value.doubleValue());
        out.value(value);
    }
}
