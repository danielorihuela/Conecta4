package com.example.dnaiel.conecta4ad;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class Conecta4PreferenceFragment extends PreferenceFragment {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);
	}
}
