package com.example.dnaiel.conecta4ad;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class GestorUsuarios {
    Gson gson = new Gson();
    SharedPreferences prefs;

    public GestorUsuarios(SharedPreferences prefs) {
        this.prefs=prefs;
    }

    public boolean mirarSiExiste(String username){
        return prefs.getString(username,null)!= null;
    }

    public void guardar(Usuario usuario){
        String usuarioJSON=gson.toJson(usuario);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(usuario.getUsername(), usuarioJSON);
        editor.commit();
    }

    public boolean comprobarLogin(String username, String contraseña) {
        Usuario usuario = obtenerPorUsername(username);
        if (usuario!=null) {
            return usuario.getContraseña().equals(contraseña);
        } else {
            return false;
        }
    }

    public Usuario obtenerPorUsername(String username) {
        Usuario usuario=null;
        String usuarioJSON=prefs.getString(username,null);
        if (usuarioJSON!=null) {
            usuario=gson.fromJson(usuarioJSON, Usuario.class);
        }
        return usuario;
    }
}
