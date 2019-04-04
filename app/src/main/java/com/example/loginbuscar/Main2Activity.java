package com.example.loginbuscar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    public static String nombres= "names", username="user", password="pwd";
    String usuario, nombreusuario, contra;
    TextView tvBienvenido, tvUser, tvPass;
    int i = 0;
    FrameLayout buscar, registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvBienvenido = findViewById(R.id.tvbienvenido);
        tvUser = findViewById(R.id.tvuser);
        tvPass = findViewById(R.id.tvpass);

        final User user = new User();

        //Asignar a un objeto String el valor enviado por el intent con el método putExtra
            usuario = getIntent().getStringExtra("names");
            nombreusuario = getIntent().getStringExtra("user");
            contra = getIntent().getStringExtra("pwd");

        tvBienvenido.setText("¡Bienvenido "+usuario+"!");
        tvUser.setText("Usuario: "+ nombreusuario);
        tvPass.setText("Contraseña: "+ contra);

        registrar = findViewById(R.id.registrarUser);
        buscar = findViewById(R.id.BuscarUser);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent instancia = new Intent(getApplicationContext(), MainActivity.class);
                    instancia.putExtra(Registro.username, nombreusuario);
                    instancia.putExtra(Registro.password, contra);
                    instancia.putExtra(Registro.nombres, usuario);
                    startActivity(instancia);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent instancia = new Intent(getApplicationContext(), Registro.class);
                    instancia.putExtra(Registro.username, nombreusuario);
                    instancia.putExtra(Registro.password, contra);
                    instancia.putExtra(Registro.nombres, usuario);
                    startActivity(instancia);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        try {
            confirmDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("¿Está seguro que desea salir?")
                .setPositiveButton("Sí",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(getApplicationContext(),"Gucci",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
