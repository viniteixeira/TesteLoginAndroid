package support.api.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public abstract class SupportJson {

    public static JSONObject getInstanceObject(String json) throws Exception {
        return (JSONObject) new JSONTokener(json).nextValue();
    }

    public static JSONArray getInstanceArray(String json) throws Exception {
        return (JSONArray) new JSONTokener(json).nextValue();
    }

    /**
     * Valida retorno do json para nao ocorrer erros (String)
     */
    public static String getString(JSONObject jsonObject, String tag, String valueDefault) throws Exception{
        if (jsonObject.isNull(tag)) {
            SupportJson.showProblema(tag, jsonObject);
            return valueDefault;
        }
        else
            return jsonObject.getString(tag);
    }

    /**
     * Valida retorno do json para nao ocorrer erros (Integer)
     */
    public static int getInt(JSONObject jsonObject, String tag, int valueDefault) throws Exception{
        if (jsonObject.isNull(tag)) {
            SupportJson.showProblema(tag, jsonObject);
            return valueDefault;
        }
        else
            return jsonObject.getInt(tag);
    }

    /**
     * Valida retorno do json para nao ocorrer erros (Boolean)
     */
    public static Boolean getBoolean(JSONObject jsonObject, String tag, Boolean valueDefault) throws Exception{
        if (jsonObject.isNull(tag)) {
            SupportJson.showProblema(tag, jsonObject);
            return valueDefault;
        }
        else
            return jsonObject.getBoolean(tag);
    }

    /**
     * Valida retorno do json para nao ocorrer erros (Double)
     */
    public static double getDouble(JSONObject jsonObject, String tag, double valueDefault) throws Exception{
        if (jsonObject.isNull(tag)) {
            SupportJson.showProblema(tag, jsonObject);
            return valueDefault;
        }
        else
            return jsonObject.getDouble(tag);
    }

    /**
     * Valida retorno do json para nao ocorrer erros (JSONArray)
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String tag) {
        JSONArray jsonArray;
        JSONObject jsonObject1;
        try {
            jsonArray = (JSONArray) new JSONTokener(jsonObject.getString(tag)).nextValue();
        }
        catch (Exception err) {
            try {
                jsonObject1 = jsonObject.getJSONObject(tag);
                jsonArray = new JSONArray();
                if (jsonObject1.length() > 0)
                    jsonArray.put(jsonObject1);
            }
            catch (Exception err2) {
                SupportJson.showProblema(tag, jsonObject);
                return new JSONArray();
            }
        }
        return jsonArray;
    }

    /**
     * Valida retorno do json para nao ocorrer erros (JSONObject)
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String tag) {
        JSONObject jsonObject1;

        try {
            jsonObject1 = (JSONObject) new JSONTokener(jsonObject.getString(tag)).nextValue();
        }
        catch (Exception err) {
            SupportJson.showProblema(tag, jsonObject);
            return new JSONObject();
        }
        return jsonObject1;
    }

    /**
     * Exibe log erro para mostrar que foi encontrado um problema
     * @param tag
     */
    private static void showProblema(String tag, JSONObject jsonObject) {
//        Log.e(SupportJson.class.getSimpleName(),
//            String.format(
//                "Não foi encontrado nenhum objeto para a tag %1$s no json %2$s",
//                tag, jsonObject.toString()
//            )
//        );
    }
}
