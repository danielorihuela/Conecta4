package com.example.dnaiel.conecta4ad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class Ingresar extends Activity {

    EditText usuarioEscrito;
    EditText contraseñaEscrita;

    CallbackManager callback;
    LoginButton fblogin;
    GestorUsuarios gestor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.ingresar);

        usuarioEscrito = (EditText) findViewById(R.id.etUsuario);
        contraseñaEscrita = (EditText) findViewById(R.id.etContraseña);
        gestor = new GestorUsuarios(PreferenceManager.getDefaultSharedPreferences(this));

        fblogin = (LoginButton) findViewById(R.id.fbloginBT);
        fblogin.setReadPermissions(Arrays.asList("public_profile, email"));
        callback = CallbackManager.Factory.create();
        fblogin.registerCallback(callback, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String id = loginResult.getAccessToken().getUserId();
                if(gestor.mirarSiExiste(id)) {
                    Singleton.getInstance().setUsuarioIngresado(gestor.obtenerPorUsername(id));
                    Singleton.getInstance().setJugadorIngresado("Facebook " + id);
                } else {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setUsername(id);
                    nuevoUsuario.setContraseña(id);
                    nuevoUsuario.setPartidasGanadas(0);
                    nuevoUsuario.setPartidasPerdidas(0);
                    gestor.guardar(nuevoUsuario);
                }
                Intent ma = new Intent("com.example.dnaiel.conecta4ad.PANTALLA");
                startActivity(ma);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callback.onActivityResult(requestCode, resultCode, data);
    }

    public void entrar(View v) {
        if (v.getId() == (R.id.buttonEntrar)) {
            GestorUsuarios gestor = new GestorUsuarios(PreferenceManager.getDefaultSharedPreferences(this));
            if(gestor.comprobarLogin(usuarioEscrito.getText().toString(),contraseñaEscrita.getText().toString())){
                Singleton.getInstance().setJugadorIngresado(usuarioEscrito.getText().toString());
                Singleton.getInstance().setUsuarioIngresado(gestor.obtenerPorUsername(usuarioEscrito.getText().toString()));
                Intent i = new Intent("com.example.dnaiel.conecta4ad.PANTALLA");
                startActivity(i);
            } else {
                Toast.makeText(this, R.string.malusuario,
                        Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == (R.id.buttonRegistrarse)) {
            Intent i = new Intent("com.example.dnaiel.conecta4ad.REGISTRARSE");
            startActivityForResult(i, 0);
        }
    }
}