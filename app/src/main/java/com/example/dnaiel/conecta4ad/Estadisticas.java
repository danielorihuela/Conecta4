package com.example.dnaiel.conecta4ad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Estadisticas extends Activity {
    TextView jugadorIngresado;
    TextView partidasJugadas;
    TextView partidasGanadas;
    TextView partidasPerdidas;
    TextView porcentajeVictorias;
    Usuario usuarioActual = Singleton.getInstance().getUsuarioIngresado();
    int totalPartidas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas_jugador);

        jugadorIngresado = (TextView) findViewById(R.id.tvJugadorIngresado);
        partidasJugadas = (TextView) findViewById(R.id.tvPartidasJugadas);
        partidasGanadas = (TextView) findViewById(R.id.tvPartidasGanadas);
        partidasPerdidas = (TextView) findViewById(R.id.tvPartidasPerdidas);
        porcentajeVictorias = (TextView) findViewById(R.id.tvPorcentajeVictorias);
        totalPartidas = usuarioActual.getPartidasGanadas() + usuarioActual.getPartidasPerdidas();

        jugadorIngresado.setText(Singleton.getInstance().getJugadorIngresado());
        partidasJugadas.setText(Integer.toString(totalPartidas));
        partidasGanadas.setText(Integer.toString(usuarioActual.getPartidasGanadas()));
        partidasPerdidas.setText(Integer.toString(usuarioActual.getPartidasPerdidas()));

        if(totalPartidas == 0){
            porcentajeVictorias.setText("0");
        } else {
            porcentajeVictorias.setText(Integer.toString((usuarioActual.getPartidasGanadas() * 100) / totalPartidas) + "%");
        }
    }
}
