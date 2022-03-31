package com.lotharie.tetra;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity implements View.OnClickListener{

    int score;

    FrameLayout menu;
    MyTetraView jeu;
    RelativeLayout bouton;

    Button left;
    Button right;
    Button rotate;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fullscreen();

        // Layout pas beau à refaire avec le préconçu activity_game.xml
        menu = new FrameLayout(this);

        jeu = new MyTetraView(this, this);
        bouton = new RelativeLayout(this);

        left = new Button(this);
        left.setText("←");
        left.setId(R.id.left);

        right = new Button(this);
        right.setText("→");
        right.setId(R.id.right);

        rotate = new Button(this);
        rotate.setText("Tourner");
        rotate.setId(R.id.rotate);

        bouton.setLayoutParams( new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        bouton.setId(R.id.left);
        bouton.addView(left);
        bouton.addView(right);
        bouton.addView(rotate);

        RelativeLayout.LayoutParams leftButton  = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams rightButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams turnButton  = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        leftButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        leftButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        rightButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        rightButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        turnButton.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        turnButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        left.setLayoutParams(leftButton);
        right.setLayoutParams(rightButton);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        rotate.setLayoutParams(turnButton);
        rotate.setOnClickListener(this);

        // menu.setContentView(R.layout.activity_game);
        menu.addView(jeu);
        menu.addView(bouton);

        setContentView(menu);
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

    /**
     * Futur menu pause
     * @param view
     */
    public void pauseMenu(View view){
        // Close pour le moment
        Intent homeIntent = new Intent(this, Home.class);
        startActivity(homeIntent);
    }


    /**
     * Interaction avec les views
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(view == left){
            jeu.moveLeft();
        }
        if(view == right){
            jeu.moveRight();
        }
        if(view == rotate){
            jeu.rotatePiece();
        }
    }
}

