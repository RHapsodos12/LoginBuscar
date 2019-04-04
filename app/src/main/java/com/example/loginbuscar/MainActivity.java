package com.example.loginbuscar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonRequest jrq;

    EditText mTxtNames;
    AppCompatButton mBtnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtNames = findViewById(R.id.txtNames1);
        mBtnBuscar = findViewById(R.id.btnBuscar);

        rq = Volley.newRequestQueue(getApplicationContext());

        mBtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buscar();
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        User usuario = new User();

        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setUser(jsonObject.optString("user"));
            usuario.setPwd(jsonObject.optString("pwd"));
            usuario.setNames(jsonObject.optString("names"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Se encontró el usuario", Toast.LENGTH_SHORT).show();

        Intent instancia = new Intent(MainActivity.this, user_encontrado.class);
        instancia.putExtra(user_encontrado.nombres, usuario.getNames());
        instancia.putExtra(user_encontrado.username, usuario.getUser());
        instancia.putExtra(user_encontrado.password, usuario.getPwd());
        startActivity(instancia);
    }

    void buscar() {

        String url="http://santacruza.proyectosutd.com/login/login/buscar.php?user="+mTxtNames.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
}
