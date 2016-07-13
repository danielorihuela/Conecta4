package com.example.dnaiel.conecta4ad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PantallaPrincipal extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantalla_principal);
	}
	
	public void segundaPantalla (View v) {
		
		if(v.getId() == (R.id.button1)){
			Intent i = new Intent("com.example.dnaiel.conecta4ad.MAINACTIVITY");
			startActivity(i);
		}
		if(v.getId() == (R.id.button2)) {
			Intent i = new Intent("com.example.dnaiel.conecta4ad.MAINACTIVITYPVP");
			startActivity(i);
		}
        if(v.getId() == R.id.button3){
            Intent i = new Intent("com.example.dnaiel.conecta4ad.ESTADISTICAS");
            startActivity(i);
        }
	}
}
