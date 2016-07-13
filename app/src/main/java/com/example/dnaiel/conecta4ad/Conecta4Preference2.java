package com.example.dnaiel.conecta4ad;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Conecta4Preference2 extends Activity {
	public final static String PLAY_MUSIC_KEY = "music";
	public final static boolean PLAY_MUSIC_DEFAULT = true;
	public final static String COLOR_FICHAS_KEY = "color_fichas1";
	public final static String COLOR_FICHAS_KEY2 = "color_fichas2";
	public final static String COLOR_DEFAULT = "a";
	public final static String COLOR_DEFAULT2 = "b";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		Conecta4PreferenceFragment2 fragment = new Conecta4PreferenceFragment2();
		fragmentTransaction.replace(android.R.id.content, fragment);
		fragmentTransaction.commit();
		
	}
}
