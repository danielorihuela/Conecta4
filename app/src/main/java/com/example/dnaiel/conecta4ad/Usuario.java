package com.example.dnaiel.conecta4ad;

public class Usuario {

    String username;
    String contraseña;
    int partidasGanadas;
    int partidasPerdidas;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public int getPartidasGanadas() {
        return partidasGanadas;
    }
    public void setPartidasGanadas(int partidasGanadas){
        this.partidasGanadas = partidasGanadas;
    }
    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }
    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }
}
