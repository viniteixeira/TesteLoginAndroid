package login.visaogrupo.login.network;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Requestor {

    private Requestor(String url, Context context, final JSONObject body) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("onResponse:",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                byte[] data = "09:57:45|/loginteste|061118,1|3.1.0[14]".getBytes();
                String header = Base64.encodeToString(data, Base64.DEFAULT);
                params.put("USUARIO", header);
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return body.toString().getBytes("UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };

        queue.add(stringRequest);
    }

}
