package com.example.loginbuscar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    public static String nombres= "names", username="user", password="pwd";
    TextView tvBienvenido, tvUser, tvPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvBienvenido = findViewById(R.id.tvbienvenido);
        tvUser = findViewById(R.id.tvuser);
        tvPass = findViewById(R.id.tvpass);

        //Asignar a un objeto String el valor enviado por el intent con el método putExtra
        String usuario = getIntent().getStringExtra("names");
        String nombreusuario = getIntent().getStringExtra("user");
        String contra = getIntent().getStringExtra("pwd");

        tvBienvenido.setText("¡Bienvenido "+usuario+"!");
        tvUser.setText("Usuario: "+ nombreusuario);
        tvPass.setText("Contraseña: "+ contra);
    }
}
