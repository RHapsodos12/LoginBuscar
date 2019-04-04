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

import org.json.JSONObject;

public class Modificar extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonRequest jrq;

    public static String nombres= "names", username="user", password="pwd";
    String usuario, nombreusuario, contra;

    EditText User, Name, Pwd;
    Button mBtnCancelar, mBtnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        rq = Volley.newRequestQueue(getApplicationContext());

        User = findViewById(R.id.etUser);
        Name = findViewById(R.id.etName);
        Pwd = findViewById(R.id.etPwd);
        mBtnCancelar = findViewById(R.id.btnCancel);
        mBtnModificar = findViewById(R.id.btnMod);

        usuario = getIntent().getStringExtra("names");
        nombreusuario = getIntent().getStringExtra("user");
        contra = getIntent().getStringExtra("pwd");

        //Toast.makeText(getApplicationContext(), "Nombre: "+usuario+" User: "+nombreusuario+" Contra:" + contra, Toast.LENGTH_LONG).show();

        User.setText(nombreusuario);
        Name.setText(usuario);
        Pwd.setText(contra);

        mBtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instancia = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(instancia);
            }
        });

        mBtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar();
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "Hubo un error al modificar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getApplicationContext(), "Usuario Modificado Correctamente", Toast.LENGTH_SHORT).show();
        Intent instancia = new Intent(Modificar.this, MainActivity.class);
        startActivity(instancia);
    }

    void modificar(){

        String url="http://santacruza.proyectosutd.com/login/login/buscar.php?accion=m&user="+nombreusuario+"&names="+Name.getText().toString()+"&pwd="+Pwd.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
}
