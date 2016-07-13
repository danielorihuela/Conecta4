package com.example.dnaiel.conecta4ad;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarseActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);
    }

    public void registrarse(View v) {
        EditText usuario = (EditText) findViewById(R.id.editTextUsuario);
        String usuarioIngresado = usuario.getText().toString();
        EditText contraseña = (EditText) findViewById(R.id.editTextContraseña);
        String contraseñaIngresada = contraseña.getText().toString();

        GestorUsuarios gestor = new GestorUsuarios(PreferenceManager.getDefaultSharedPreferences(this));
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setContraseña(contraseñaIngresada);
        nuevoUsuario.setUsername(usuarioIngresado);
        nuevoUsuario.setPartidasGanadas(0);
        nuevoUsuario.setPartidasPerdidas(0);
        gestor.guardar(nuevoUsuario);
        Toast.makeText(this, R.string.registrado,
                Toast.LENGTH_SHORT).show();

        finish();
    }
}
