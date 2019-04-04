package com.example.loginbuscar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Login extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    //ENLAZAR LOS COMPONENTES XML CON LOS OBJETOS EN JAVA
    EditText txtUser, txtPwd;
    Button btnRegistrar;

    RequestQueue rq;
    JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = (EditText) findViewById(R.id.txtuserLogin);
        txtPwd = (EditText) findViewById(R.id.txtpwdLogin);
        btnRegistrar = (Button) findViewById(R.id.btnregistrar);

        //Realiza una petición de JSON básica
        rq = Volley.newRequestQueue(getApplicationContext());

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inicio_sesion();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(getApplicationContext(), "Inicia Sesión"+txtUser.getText().toString()+txtPwd.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "Usuario y/o Contraseña incorrectos, verifique"+txtUser.getText().toString()+txtPwd.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        User usuario = new User();

        //Utilizar los objetos y arreglos de tipo JSON para traer los valores de web services

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

        Toast.makeText(getApplicationContext(), "Acceso Correcto a: "+txtUser.getText().toString(), Toast.LENGTH_SHORT).show();

        Intent instancia = new Intent(Login.this, Main2Activity.class);
        instancia.putExtra(Main2Activity.nombres, usuario.getNames());
        instancia.putExtra(Main2Activity.username, usuario.getUser());
        instancia.putExtra(Main2Activity.password, usuario.getPwd());
        startActivity(instancia);
    }

    void inicio_sesion() {
        //URL donde se encuentra el archivo web service envio de parametros con el metodo GET
        //String url="http://172.20.98.193/htdocs/login/web_service.php?user="+txtUser.getText().toString()+"&pwd="+txtPwd.getText().toString();

        String url="http://santacruza.proyectosutd.com/login/web_service.php?user="+txtUser.getText().toString()+"&pwd="+txtPwd.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
}
