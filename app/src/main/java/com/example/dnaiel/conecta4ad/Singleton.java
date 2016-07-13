package com.example.dnaiel.conecta4ad;

public class Singleton {
    private static Singleton mInstance = null;

    private String jugadorIngresado;
    private Usuario usuarioIngresado;

    public static Singleton getInstance(){
        if(mInstance == null) {
            mInstance = new Singleton();
        }
        return mInstance;
    }
    public String getJugadorIngresado(){
        return this.jugadorIngresado;
    }
    public void setJugadorIngresado(String jugador){
        jugadorIngresado = jugador;
    }
    public Usuario getUsuarioIngresado(){
        return this.usuarioIngresado;
    }
    public void setUsuarioIngresado(Usuario usuario){
        usuarioIngresado = usuario;
    }
}
