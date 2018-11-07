package login.visaogrupo.login.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import login.visaogrupo.login.HTTPCallback;
import login.visaogrupo.login.R;
import login.visaogrupo.login.task.RequestTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, HTTPCallback {

    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControls();
    }

    private void setControls() {
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == btnLogin) {
            request();
//            Log.i("Log", "Button Pressed");
//            Toast.makeText(this, "button pressed", Toast.LENGTH_SHORT).show();
        }
    }

    private void request() {

        try {
            JSONObject body = new JSONObject();
            body.put("Email", edtLogin.getText().toString());
            body.put("Senha", edtSenha.getText().toString());

            new RequestTask(this,"http://wwwi.visaogrupo.com.br/ws/loginteste", body.toString(), this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSucess(String response) {
        Log.i("onSucess","AEHOO");
    }
}
