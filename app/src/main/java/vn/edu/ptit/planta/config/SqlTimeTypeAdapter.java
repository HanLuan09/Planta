package vn.edu.ptit.planta.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class SqlTimeTypeAdapter extends TypeAdapter<Time> {
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void write(JsonWriter out, Time value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(timeFormat.format(value));
        }
    }

    @Override
    public Time read(JsonReader in) throws IOException {
        if (in != null) {
            try {
                String str = in.nextString();
                return Time.valueOf(str);
            } catch (IllegalArgumentException e) {
                throw new IOException(e);
            }
        }
        return null;
    }
}

