package com.example.loginbuscar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
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

    public static String nombres= "names", username="user", password="pwd";

    RequestQueue rq;
    JsonRequest jrq;

    String usuario, nombreusuario, contra;

    EditText mTxtNames;
    AppCompatButton mBtnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Toolbar barra= findViewById(R.id.barrita);
            setSupportActionBar(barra);
            barra.setTitle("");
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            barra.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        mTxtNames = findViewById(R.id.txtNames1);
        mBtnBuscar = findViewById(R.id.btnBuscar);

        usuario = getIntent().getStringExtra("names");
        nombreusuario = getIntent().getStringExtra("user");
        contra = getIntent().getStringExtra("pwd");

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

        User user = new User();

        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            user.setUser(jsonObject.optString("user"));
            user.setPwd(jsonObject.optString("pwd"));
            user.setNames(jsonObject.optString("names"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Se encontró el usuario", Toast.LENGTH_SHORT).show();

        Intent instancia = new Intent(MainActivity.this, user_encontrado.class);
        instancia.putExtra(user_encontrado.nombres, user.getNames());
        instancia.putExtra(user_encontrado.username, user.getUser());
        instancia.putExtra(user_encontrado.password, user.getPwd());

        instancia.putExtra(user_encontrado.nombres0, usuario);
        instancia.putExtra(user_encontrado.username0, nombreusuario);
        instancia.putExtra(user_encontrado.password0, contra);
        startActivity(instancia);
    }

   @Override
    public void onBackPressed() {
        Intent instancia = new Intent(MainActivity.this, Main2Activity.class);
        instancia.putExtra(Main2Activity.nombres, usuario);
        instancia.putExtra(Main2Activity.username, nombreusuario);
        instancia.putExtra(Main2Activity.password, contra);
        startActivity(instancia);
    }

    void buscar() {

        String url="http://santacruza.proyectosutd.com/login/buscar.php?user="+mTxtNames.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

}
