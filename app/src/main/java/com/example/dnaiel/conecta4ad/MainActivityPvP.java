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

public class MainActivityPvP extends Activity {
    GamePvP gamepvp;
    private int coloreado;
    private int coloreado2;

    private int anchoX;
    private int anchoY;
    MyView view;
    private int pixel_x;
    boolean shouldAnimate = true;

    Paint fondo;
    Paint texto;
    Paint separador;
    Paint turnoJugador;
    Paint fichajugador1;
    Paint fichajugador2;
    Paint vacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        gamepvp = new GamePvP();
        view = new MyView(MainActivityPvP.this);
    }

    public class MyView extends View {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "fooBar", 200, 1000);
        int inicio = 200;

        public MyView(Context context) {
            super(context);
            animator.setDuration(800);

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

            turnoJugador = new Paint();
            fichajugador1 = new Paint();
            fichajugador2 = new Paint();

            vacio = new Paint();
            vacio.setColor(Color.WHITE);
        }

        public void onDraw(Canvas canvas) {
            anchoX = getWidth();
            anchoY = getHeight();
            fondo.setShader(new LinearGradient(0, 0, 0, getHeight(), Color.parseColor("#06DCFB"), Color.parseColor("#03EBA6"), Shader.TileMode.MIRROR));
            canvas.drawPaint(fondo);
            /*Texto*/
            canvas.drawText("CONECTA 4", 70, 130, texto);
            /*Separador*/
            canvas.drawRect(0, 200, anchoX, 210, separador);
            /*Tablero*/
            float radius = getResources().getDimension(R.dimen.radio);
            for (int i = 0; i < GamePvP.NFILAS; i++)
                for (int j = 0; j < GamePvP.NCOLUMNAS; j++) {
                    if(gamepvp.quienJuega() == GamePvP.JUGADOR){
                        turnoJugador.setColor(coloreado);
                    } else {
                        turnoJugador.setColor(coloreado2);
                    }
                    canvas.drawCircle(getPixelFromColumna(getColumna(pixel_x)), inicio, radius, turnoJugador);

                    if (gamepvp.estaVacio(i, j)) {
                        canvas.drawCircle(getPixelFromColumna(j), getPixelFromFila(i), radius, vacio);
                    } else if (gamepvp.estaJugador(i, j)) {
                        fichajugador1.setColor(coloreado);
                        canvas.drawCircle(getPixelFromColumna(j), getPixelFromFila(i), radius, fichajugador1);
                    } else if (gamepvp.estaJugador2(i,j)){
                        fichajugador2.setColor(coloreado2);
                        canvas.drawCircle(getPixelFromColumna(j), getPixelFromFila(i), radius, fichajugador2);
                    }
                }
        }

        void setFooBar(int inicio) {
            this.inicio = inicio;
            invalidate();
        }

        public boolean onTouchEvent(MotionEvent event) {
            final int columna;

            if(mismoColor()){
                Toast.makeText(getApplicationContext(), R.string.cambiaColor, Toast.LENGTH_SHORT).show();
            }

            if ((event.getAction() == MotionEvent.ACTION_DOWN) && shouldAnimate) {
                shouldAnimate = false;
                pixel_x = (int) event.getX();
                columna = getColumna(pixel_x);
                if(gamepvp.columnaLlena(columna)){
                    Toast.makeText(getApplicationContext(), R.string.notoques,
                            Toast.LENGTH_LONG).show();
                    return shouldAnimate = true;
                }
                animator.start();
                animator.setIntValues(200, getPixelFromFila(gamepvp.generarFila(columna)));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animator.removeAllListeners();
                        gamepvp.ponerJugador(gamepvp.generarFila(columna),columna);
                        if(gamepvp.comprobarCuatro(gamepvp.quienJuega())){
                            Toast.makeText(getApplicationContext(), "Ha ganado el jugador " + gamepvp.quienJuega(),
                                    Toast.LENGTH_LONG).show();
                            if(gamepvp.fin()){
                                FragmentoDialogoPvP dialogo = new FragmentoDialogoPvP();
                                dialogo.show(getFragmentManager(), "Alert Dialog2");
                            }
                        } else if(!gamepvp.comprobarCuatro(gamepvp.quienJuega()) && gamepvp.tableroLleno()){
                            Toast.makeText(getApplicationContext(), "Habeis empatado",
                                    Toast.LENGTH_LONG).show();
                            FragmentoDialogoPvP dialogo = new FragmentoDialogoPvP();
                            dialogo.show(getFragmentManager(), "Alert Dialog2");
                        }
                        shouldAnimate = true;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            } else {
                return false;
            }
            if(!gamepvp.fin()){
                gamepvp.cambiarTurno();
            }
            return false;
        }
    }


    private int getPixelFromColumna(int columnaDada) {
        int pixel_x = (anchoX / GamePvP.NCOLUMNAS) / 2;
        for (int columna = 0; columna < columnaDada; columna++)
            pixel_x = pixel_x + (anchoX / GamePvP.NCOLUMNAS);

        return pixel_x;
    }

    private int getColumna(int pixel_x) {
        int columna = -1;
        for (int j = 0; j < anchoX; j = j + (anchoX / GamePvP.NCOLUMNAS))
            if (j < pixel_x)
                columna++;

        return columna;
    }

    private int getPixelFromFila(int filaDada) {
        int pixel_y = anchoY - (((anchoY-200) / Game.NFILAS) / 2);
        for (int fila = 0; fila < filaDada; fila++)
            pixel_y = pixel_y - ((anchoY-200) / Game.NFILAS);

        return pixel_y;
    }

        public void onResume() {
            super.onResume();

            if (music())
                Musica.play(this, R.raw.hey);

            if(color().equals("a"))
                coloreado = Color.YELLOW;
            else if(color().equals("b"))
                coloreado = Color.parseColor("#2DC800");
            else if(color().equals("c"))
                coloreado = Color.MAGENTA;
            else if(color().equals("d"))
                coloreado = Color.BLACK;
            else if(color().equals("e"))
                coloreado = Color.GRAY;
            else if(color().equals("f"))
                coloreado = Color.CYAN;
            else if(color().equals("g"))
                coloreado = Color.parseColor("#FF7300");
            else if(color().equals("h"))
                coloreado = Color.RED;

            if(color2().equals("a"))
                coloreado2 = Color.YELLOW;
            else if(color2().equals("b"))
                coloreado2 = Color.parseColor("#2DC800");
            else if(color2().equals("c"))
                coloreado2 = Color.MAGENTA;
            else if(color2().equals("d"))
                coloreado2 = Color.BLACK;
            else if(color2().equals("e"))
                coloreado2 = Color.GRAY;
            else if(color2().equals("f"))
                coloreado2 = Color.CYAN;
            else if(color2().equals("g"))
                coloreado2 = Color.parseColor("#FF7300");
            else if(color2().equals("h"))
                coloreado2 = Color.RED;
        }

        public void onPause() {
            super.onPause();
            Musica.stop(this);
        }

        public void restartGame() {
            gamepvp.restart();
        }

        public boolean mismoColor() {
            if (coloreado == coloreado2) {
                return true;
            }
            return false;
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
                    Intent i = new Intent("com.example.dnaiel.conecta4ad.CONECTA4PREFERENCE2");
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
            editor.putBoolean(Conecta4Preference2.PLAY_MUSIC_KEY, value);
            editor.commit();
        }

        public String color() {
            String color = "a";

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPreferences.contains(Conecta4Preference2.COLOR_FICHAS_KEY))
                color = sharedPreferences.getString(Conecta4Preference2.COLOR_FICHAS_KEY, Conecta4Preference2.COLOR_DEFAULT);

            return color;
        }

        public void setColor(Boolean value) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Conecta4Preference2.COLOR_FICHAS_KEY, value);
            editor.commit();
        }

        public String color2() {
            String color = "b";

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPreferences.contains(Conecta4Preference2.COLOR_FICHAS_KEY2))
                color = sharedPreferences.getString(Conecta4Preference2.COLOR_FICHAS_KEY2, Conecta4Preference2.COLOR_DEFAULT2);

            return color;
        }

        public void setColor2(Boolean value) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Conecta4Preference2.COLOR_FICHAS_KEY2, value);
            editor.commit();
        }
    }