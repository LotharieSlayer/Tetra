package com.lotharie.tetra;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fullscreen();

        setContentView(R.layout.activity_home);
    }

    public void fullscreen() {
        // Enlever la barre de notifications
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Enlever la barre de titre
        getSupportActionBar().hide();

        // Enlever la barre de navigation
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            // Sinon pour API > ou = à 19.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void settings(View view){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
    }

    public void play(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "N'OUBLIEZ PAS DE SECOUER POUR UN MEILLEUR SCORE !", Toast.LENGTH_LONG);
        toast.show();
        Intent gameIntent = new Intent(this, Game.class);
        startActivity(gameIntent);
    }

    public void rumble(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "Actuellement le Rumble Mode est le mode Normal.", Toast.LENGTH_LONG);
        toast.show();
    }

    public void highscore(View view){
        Intent highscoreIntent = new Intent(this, HighScore.class);
        startActivity(highscoreIntent);
    }
}
