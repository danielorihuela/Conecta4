package com.example.dnaiel.conecta4ad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class FragmentoDialogoPvP extends DialogFragment {
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final MainActivityPvP main = (MainActivityPvP) getActivity();
		this.setCancelable(false);

		AlertDialog.Builder fragmento_dialogoBuilder = new AlertDialog.Builder(getActivity());
		fragmento_dialogoBuilder.setTitle(R.string.titulo_dialogo);
		fragmento_dialogoBuilder.setMessage(R.string.mensaje_dialogo);
		fragmento_dialogoBuilder.setPositiveButton("Si",new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog2,int which){
				main.view.setFooBar(200);
				main.shouldAnimate = true;
				main.restartGame();
				main.setContentView(main.view);
				dialog2.dismiss();
			}
		});
		fragmento_dialogoBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog2,int which){
				main.finish();
				dialog2.dismiss();
			}
		});
		
		return fragmento_dialogoBuilder.create();
		
   }
}
