package vn.edu.ptit.planta.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class SqlDateTypeAdapter extends TypeAdapter<Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(dateFormat.format(value));
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in != null) {
            try {
                String str = in.nextString();
                return Date.valueOf(str);
            } catch (IllegalArgumentException e) {
                throw new IOException(e);
            }
        }
        return null;
    }
}
