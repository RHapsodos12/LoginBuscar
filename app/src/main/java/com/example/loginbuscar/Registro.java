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

public class Registro extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    public static String nombres= "names", username="user", password="pwd";
    String usuario, nombreusuario, contra;
    RequestQueue rq;
    JsonRequest jrq;

    Button mBtnRegistrar;
    EditText mTxtUser, mTxtPwd, mTxtNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mBtnRegistrar =  findViewById(R.id.btnRegistrarReg);
        mTxtUser = findViewById(R.id.txtUserReg);
        mTxtPwd = findViewById(R.id.txtPwdReg);
        mTxtNames = findViewById(R.id.txtNamesReg);

        rq = Volley.newRequestQueue(getApplicationContext());

        usuario = getIntent().getStringExtra("names");
        nombreusuario = getIntent().getStringExtra("user");
        contra = getIntent().getStringExtra("pwd");

        mBtnRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Intent instancia = new Intent(getApplicationContext(), user_registrado.class);
                //startActivity(instancia);
                nuevo_registro();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent instancia = new Intent(getApplicationContext(), Main2Activity.class);
        instancia.putExtra(Main2Activity.username, nombreusuario);
        instancia.putExtra(Main2Activity.password, contra);
        instancia.putExtra(Main2Activity.nombres, usuario);
        startActivity(instancia);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "Error al registrar"+error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getApplicationContext(), "Registrado Correctamente", Toast.LENGTH_SHORT).show();
        limpiar_cajas();

        Intent instancia = new Intent(getApplicationContext(), Main2Activity.class);
        instancia.putExtra(Main2Activity.username, nombreusuario);
        instancia.putExtra(Main2Activity.password, contra);
        instancia.putExtra(Main2Activity.nombres, usuario);
        startActivity(instancia);
    }

    void nuevo_registro () {

        String names = mTxtNames.getText().toString();
        names.replaceAll(" ", "-");

        String url="http://santacruza.proyectosutd.com/login/registrar.php?names="+names+"&user="+mTxtUser.getText().toString()+"&pwd="+mTxtPwd.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    void limpiar_cajas() {

        mTxtNames.setText("");
        mTxtPwd.setText("");
        mTxtUser.setText("");
    }
}
