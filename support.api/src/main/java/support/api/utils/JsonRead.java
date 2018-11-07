package support.api.utils;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonRead {
    private JsonReader mJsonReader;

    public JsonRead(File file) throws Exception{
        mJsonReader = new JsonReader(new InputStreamReader(new FileInputStream(file)));
    }

    public JsonRead(String content) throws Exception{
        mJsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes())));
    }

    public HashMap read() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        JsonToken token;
        String name;
        Object value = null;

        //Obtem token
        token = mJsonReader.peek();

        //Verifica se é array
        if (token == JsonToken.BEGIN_ARRAY) {
            value = this.readArray();
        }
        //Verifica se é object
        else if (token == JsonToken.BEGIN_OBJECT) {
            value = this.readObject();
        }

        //Guarda valor
        hashMap.put("JSON", value);

        return hashMap;
    }

    /**
     * Le json object
     * @return
     * @throws Exception
     */
    private HashMap readObject() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        JsonToken token;
        String name;
        Object value;

        mJsonReader.beginObject();

        while (mJsonReader.hasNext()) {
            name = mJsonReader.nextName();

            token = mJsonReader.peek();
            if (token == JsonToken.BEGIN_OBJECT) {
                value = this.readObject();
            }
            else if (token == JsonToken.BEGIN_ARRAY) {
                value = this.readArray();
            }
            else {
                value =  mJsonReader.nextString();
            }

            hashMap.put(name, value);
        }

        mJsonReader.endObject();

        return hashMap;
    }

    /**
     * Le json array
     * @return
     * @throws Exception
     */
    private ArrayList<Object> readArray() throws Exception {
        ArrayList<Object> arrHashMap = new ArrayList<>();
        Object value;

        mJsonReader.beginArray();

        while (mJsonReader.peek() == JsonToken.BEGIN_OBJECT) {
            value = this.readObject();
            arrHashMap.add(value);
        }

        mJsonReader.endArray();

        return arrHashMap;
    }

    public void close() throws Exception {
        mJsonReader.close();
    }
}
