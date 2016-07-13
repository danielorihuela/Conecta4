package com.example.dnaiel.conecta4ad;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    Game game;
    private int coloreado;
    Usuario usuarioActual;
    GestorUsuarios gestor;

    private int anchoX;
    private int anchoY;
    MyView view;
    int pixel_x = 0;
    int columnaMaquina;
    int filaMaquina;
    boolean shouldAnimate = true;

    Paint fondo;
    Paint texto;
    Paint separador;
    Paint fichaJugador;
    Paint fichaMaquina;
    Paint vacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        game = new Game();
        usuarioActual = Singleton.getInstance().getUsuarioIngresado();
        gestor = new GestorUsuarios(PreferenceManager.getDefaultSharedPreferences(this));
        view = new MyView(MainActivity.this);

    }

    public class MyView extends View {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "fooBar", 200, 200);
        int inicio = 200;
        ObjectAnimator animator2 = ObjectAnimator.ofInt(this, "fooBar2", 200, 200);
        int inicio2 = 200;

        public MyView(Context context) {
            super(context);
            animator.setDuration(800);
            animator2.setDuration(800);

            init();
        }

        private void init() {
            fondo = new Paint();
            fondo.setFlags(Paint.ANTI_ALIAS_FLAG);
            fondo.setStyle(Paint.Style.FILL);

            texto = new Paint();
            texto.setTextSize(getResources().getDimension(R.dimen.grande));
            texto.setColor(Color.BLACK);

            separador = new Paint();
            separador.setTextSize(getResources().getDimension(R.dimen.grande));
            separador.setColor(Color.BLACK);

            fichaJugador = new Paint();

            fichaMaquina = new Paint();
            fichaMaquina.setColor(Color.RED);

            vacio = new Paint();
            vacio.setColor(Color.WHITE);
        }

        public void onDraw(Canvas canvas) {
            anchoX = getWidth();
            anchoY = getHeight();
            fondo.setShader(new LinearGradient(0, 0, 0, anchoY, Color.parseColor("#06DCFB"), Color.parseColor("#03EBA6"), Shader.TileMode.MIRROR));
            canvas.drawPaint(fondo);
            /*Texto*/
            canvas.drawText("CONECTA 4", 70, 130, texto);
            /*Separador*/
            canvas.drawRect(0, 200, anchoX, 210, separador);
            /*Tablero*/
            float radius = getResources().getDimension(R.dimen.radio);
            for (int i = 0; i < Game.NFILAS; i++)
                for (int j = 0; j < Game.NCOLUMNAS; j++) {
                    fichaJugador.setColor(coloreado);
                    canvas.drawCircle(getPixelFromColumna(getColumna(pixel_x)), inicio, radius, fichaJugador);
                    canvas.drawCircle(getPixelFromColumna(columnaMaquina), inicio2, radius, fichaMaquina);

                    if (game.estaVacio(i, j)) {
                        canvas.drawCircle(getPixelFromColumna(j), getPixelFromFila(i), radius, vacio);
                    } else if (game.estaJugador(i, j)) {
                        canvas.drawCircle(getPixelFromColumna(j), getPixelFromFila(i), radius, fichaJugador);
                    } else {
                        canvas.drawCircle(getPixelFromColumna(j), getPixelFromFila(i), radius, fichaMaquina);
                    }
                }
        }

        void setFooBar(int inicio) {
            this.inicio = inicio;
            invalidate();
        }

        void setFooBar2(int inicio2) {
            this.inicio2 = inicio2;
            invalidate();
        }

        public boolean onTouchEvent(MotionEvent event) {
            final int columna;
            final int fila;

            if ((event.getAction() == MotionEvent.ACTION_DOWN) && shouldAnimate) {
                shouldAnimate = false;
                pixel_x = (int) event.getX();
                columna = getColumna(pixel_x);
                fila = game.generarFila(columna);
                if(game.columnaLlena(columna)){
                    Toast.makeText(getApplicationContext(), R.string.notoques,
                            Toast.LENGTH_LONG).show();
                    return shouldAnimate = true;
                }
                animator.start();
                animator.setIntValues(200, getPixelFromFila(game.generarFila(columna)));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animator.removeAllListeners();
                        game.ponerJugador(fila, columna);
                        if (game.comprobarCuatro(Game.JUGADOR)) {
                            if (Singleton.getInstance().getUsuarioIngresado() != null) {
                                usuarioActual.setPartidasGanadas(usuarioActual.getPartidasGanadas() + 1);
                                gestor.guardar(usuarioActual);
                            }
                            Toast.makeText(getApplicationContext(), "Has ganado " + Singleton.getInstance().getJugadorIngresado(),
                                    Toast.LENGTH_LONG).show();
                            if (game.fin()) {
                                FragmentoDialogo dialogo = new FragmentoDialogo();
                                dialogo.show(getFragmentManager(), "Alert Dialog2");
                            }
                        } else if(!game.comprobarCuatro(Game.JUGADOR) && game.tableroLleno()){
                            Toast.makeText(getApplicationContext(), "Has empatado",
                                    Toast.LENGTH_LONG).show();
                            FragmentoDialogo dialogo = new FragmentoDialogo();
                            dialogo.show(getFragmentManager(), "Alert Dialog2");
                        } else {
                            columnaMaquina = game.posicion();
                            while (game.columnaLlena(columnaMaquina)) {
                                columnaMaquina = game.generarColumna();
                            }
                            filaMaquina = game.generarFila(columnaMaquina);
                            animator2.setIntValues(200, getPixelFromFila(filaMaquina));
                            animator2.start();
                            animator2.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    animator2.removeAllListeners();
                                    game.ponerMaquina(filaMaquina, columnaMaquina);
                                    shouldAnimate = true;
                                    if (game.comprobarCuatro(Game.MAQUINA)) {
                                        if (Singleton.getInstance().getUsuarioIngresado() != null) {
                                            usuarioActual.setPartidasPerdidas(usuarioActual.getPartidasPerdidas() + 1);
                                            gestor.guardar(usuarioActual);
                                        }
                                        Toast.makeText(getApplicationContext(),
                                                "Has perdido " + Singleton.getInstance().getJugadorIngresado(),
                                                Toast.LENGTH_LONG).show();
                                        if (game.fin()) {
                                            FragmentoDialogo dialogo = new FragmentoDialogo();
                                            dialogo.show(getFragmentManager(), "Alert Dialog2");
                                        }
                                    } else if(!game.comprobarCuatro(Game.MAQUINA) && game.tableroLleno()){
                                        Toast.makeText(getApplicationContext(), "Has empatado",
                                                Toast.LENGTH_LONG).show();
                                        FragmentoDialogo dialogo = new FragmentoDialogo();
                                        dialogo.show(getFragmentManager(), "Alert Dialog2");
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                });
            }
            return super.onTouchEvent(event);
        }

    }

    private int getPixelFromColumna(int columnaDada) {
        int pixel_x = (anchoX / Game.NCOLUMNAS) / 2;
        for (int columna = 0; columna < columnaDada; columna++)
            pixel_x = pixel_x + (anchoX / Game.NCOLUMNAS);

        return pixel_x;
    }

    private int getColumna(int pixel_x) {
        int columna = -1;
        for (int j = 0; j < anchoX; j = j + (anchoX / Game.NCOLUMNAS))
            if (j < pixel_x)
                columna++;
        return columna;
    }

    private int getPixelFromFila(int filaDada) {
        int pixel_y = anchoY - (((anchoY - 200) / Game.NFILAS) / 2);
        for (int fila = 0; fila < filaDada; fila++)
            pixel_y = pixel_y - ((anchoY - 200) / Game.NFILAS);

        return pixel_y;
    }

    public void onResume() {
        super.onResume();

        if (music())
            Musica.play(this, R.raw.hey);

        if (color().equals("a"))
            coloreado = Color.YELLOW;
        else if (color().equals("b"))
            coloreado = Color.parseColor("#2DC800");
        else if (color().equals("c"))
            coloreado = Color.MAGENTA;
        else if (color().equals("d"))
            coloreado = Color.BLACK;
        else if (color().equals("e"))
            coloreado = Color.GRAY;
        else if (color().equals("f"))
            coloreado = Color.CYAN;
        else if (color().equals("g"))
            coloreado = Color.parseColor("#FF7300");
    }

    public void onPause() {
        super.onPause();
        Musica.stop(this);
    }

    public void restartGame() {
        game.restart();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuAbout:
                Intent x = new Intent("com.example.dnaiel.conecta4ad.ABOUT");
                startActivity(x);
                return true;

            case R.id.menuPreferences:
                Intent i = new Intent("com.example.dnaiel.conecta4ad.CONECTA4PREFERENCE");
                startActivity(i);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public Boolean music() {
        Boolean play = false;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(Conecta4Preference.PLAY_MUSIC_KEY))
            play = sharedPreferences.getBoolean(Conecta4Preference.PLAY_MUSIC_KEY, Conecta4Preference.PLAY_MUSIC_DEFAULT);

        return play;
    }

    public void setMusic(Boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Conecta4Preference.PLAY_MUSIC_KEY, value);
        editor.commit();
    }

    public String color() {
        String color = "a";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.contains(Conecta4Preference.COLOR_FICHAS_KEY))
            color = sharedPreferences.getString(Conecta4Preference.COLOR_FICHAS_KEY, Conecta4Preference.COLOR_DEFAULT);

        return color;
    }

    public void setColor(Boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Conecta4Preference.COLOR_FICHAS_KEY, value);
        editor.commit();
    }
}