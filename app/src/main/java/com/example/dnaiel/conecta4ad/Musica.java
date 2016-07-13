package com.example.dnaiel.conecta4ad;

import android.content.Context;
import android.media.MediaPlayer;

public class Musica {
	private static MediaPlayer player;

	public static void play(Context hey,int mp3) {
		player = MediaPlayer.create(hey, mp3);
		player.setLooping(true);
		player.start();
	}
	
	public static void stop(Context hey) {
		if(player != null) {
			player.stop();
			player.release();
			player=null;
		}
	}
}
