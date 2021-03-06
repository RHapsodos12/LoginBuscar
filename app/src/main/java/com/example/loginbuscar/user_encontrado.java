package com.example.loginbuscar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class user_encontrado extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonRequest jrq;

    public static String nombres= "names", username="user", password="pwd";
    public static String nombres0= "names0", username0="user0", password0="pwd0";
    TextView txtNombre, txtUser, txtPwd;

    Button  mBtnModificar, mBtnEliminar;

    String usuario, nombreusuario, contra;
    String usuario0, nombreusuario0, contra0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_encontrado);

        try {
            Toolbar barra= findViewById(R.id.barrita);
            setSupportActionBar(barra);
            barra.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        rq = Volley.newRequestQueue(getApplicationContext());

        txtNombre = findViewById(R.id.txtName);
        txtUser = findViewById(R.id.txtUser);
        txtPwd = findViewById(R.id.txtPwd);
        mBtnModificar = findViewById(R.id.btnModificar);
        mBtnEliminar = findViewById(R.id.btnEliminar);

        usuario = getIntent().getStringExtra("names");
        nombreusuario = getIntent().getStringExtra("user");
        contra = getIntent().getStringExtra("pwd");

        usuario0 = getIntent().getStringExtra("names0");
        nombreusuario0 = getIntent().getStringExtra("user0");
        contra0 = getIntent().getStringExtra("pwd0");


        txtNombre.setText(" "+usuario);
        txtUser.setText(" "+ nombreusuario);
        txtPwd.setText(" "+ contra);

        mBtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent instancia = new Intent(user_encontrado.this, Modificar.class);
                instancia.putExtra(Modificar.nombres, usuario);
                instancia.putExtra(Modificar.username, nombreusuario);
                instancia.putExtra(Modificar.password, contra);

                instancia.putExtra(Modificar.nombres0, usuario0);
                instancia.putExtra(Modificar.username0, nombreusuario0);
                instancia.putExtra(Modificar.password0, contra0);
                startActivity(instancia);
            }
        });

        mBtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
    }


    @Override
    public void onBackPressed() {

        usuario = getIntent().getStringExtra("names0");
        nombreusuario = getIntent().getStringExtra("user0");
        contra = getIntent().getStringExtra("pwd0");

        Intent instancia = new Intent(getApplicationContext(), MainActivity.class);
        instancia.putExtra(MainActivity.nombres, usuario);
        instancia.putExtra(MainActivity.username, nombreusuario);
        instancia.putExtra(MainActivity.password, contra);
        startActivity(instancia);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Usuario no Eliminado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Usuario Eliminado", Toast.LENGTH_SHORT).show();
        usuario = getIntent().getStringExtra("names0");
        nombreusuario = getIntent().getStringExtra("user0");
        contra = getIntent().getStringExtra("pwd0");
        Intent instancia = new Intent(user_encontrado.this, MainActivity.class);
        instancia.putExtra(MainActivity.nombres, usuario);
        instancia.putExtra(MainActivity.username, nombreusuario);
        instancia.putExtra(MainActivity.password, contra);
        startActivity(instancia);
    }

    void eliminar() {

        String url="http://santacruza.proyectosutd.com/login/buscar.php?accion=e&user="+nombreusuario;

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

}
