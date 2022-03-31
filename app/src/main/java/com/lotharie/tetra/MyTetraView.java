package com.lotharie.tetra;


import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.View;

import com.lotharie.tetra.pieces.Block;
import com.lotharie.tetra.pieces.BlockPosition;
import com.lotharie.tetra.pieces.Tetramino;

public class MyTetraView extends View implements View.OnClickListener, SensorListener{
    Paint paint = new Paint();

    // SET SIZE
    int lignes = 18;
    int colonnes = 10;

    // SHAKER
    private static final int SHAKE_THRESHOLD = 15000;
    float last_x;
    float last_y;
    float last_z;
    long lastUpdate;

    // MESURES
    int largX;
    int hautY;
    float largCase;
    float hautCase;

    // GRILLE
    Block[][] grid;

    Tetramino piece;
    Runnable refresh;
    Handler handler;

    int score = 0;
    boolean isFinished = false;

    Context c;
    Home home;


    public MyTetraView(Context context, Game home) {
        super(context);
        c = context;
        home = home;
        setFocusable(true);
        setOnClickListener(this);

        //SHAKE
        SensorManager sensorMgr = (SensorManager) c.getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);

        this.grid = new Block[colonnes][lignes];

        for(int i = 0; i < colonnes; i++){
            for(int j =0; j < lignes; j++){
                this.grid[i][j] = new Block(i, j);
            }
        }

        randomPiece();

        // Handler principal (Thread)
        handler = new Handler(Looper.getMainLooper());
        refresh = new Runnable() {
            public void run() {
                if(!isFinished){

                    if(!fallTetramino(piece)){
                        addPiece(piece);

                        addPoints();

                        randomPiece();
                    }
                    invalidate();
                    handler.postDelayed(this, 500);
                }
            }};
        refresh.run();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        largX = getWidth();
        hautY = getHeight();

        largCase = largX*0.90f/colonnes;
        hautCase = hautY*0.7f/lignes;

        largCase = largX*0.90f/colonnes;
        hautCase = hautY*0.7f/lignes;

        gridGen(canvas);
        gridDisplay(canvas);
        scoreDisplay(canvas);
        tetraminoDisplay(canvas);

    }


    /**
     * Génération de la grille
     * @param canvas
     */
    private void gridGen(Canvas canvas) {
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(5f);

        canvas.drawLine(largX * 0.05f, hautY * 0.15f, largX * 0.05f, hautY * 0.85f, paint);
        canvas.drawLine(largX * 0.05f, hautY * 0.85f, largX * 0.95f, hautY * 0.85f, paint);
        canvas.drawLine(largX * 0.95f, hautY * 0.15f, largX * 0.95f, hautY * 0.85f, paint);
        canvas.drawLine(largX * 0.95f, hautY * 0.15f, largX * 0.05f, hautY * 0.15f, paint);

        paint.setStrokeWidth(1f);

        for (float i = largX * 0.05f + largCase; i < largX * 0.95f; i += largCase)
            canvas.drawLine(i, hautY * 0.15f, i, hautY * 0.85f, paint);

        for (float i = hautY * 0.15f + hautCase; i < hautY * 0.85f; i += hautCase)
            canvas.drawLine(largX * 0.05f, i, largX * 0.95f, i, paint);
    }

    /**
     * Ajouter le score
     */
    private void addPoints() {

        boolean fullLine;
        for(int ligne = lignes-1; ligne >= 0; ligne--){
            fullLine = true;
            for(int colonne = colonnes-1; colonne >=0; colonne--){
                if( this.grid[colonne][ligne].emptyCell){
                    fullLine = false;
                    break;
                }
            }

            if (fullLine){
                score += 100;

                Vibrator vibrator = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(300);

            }
        }
    }

    private void addPiece(Tetramino piece) {
        for(Block b : piece.getTetramino() ){
            getBlock(b.blockPosition).setBlock(b);
        }
    }

    private void randomPiece() {
        int typeAlea = (int)(Math.random()*7)+1;

        piece = new Tetramino( typeAlea );

        for(Block b: piece.getTetramino() ){
            if ( !getBlock(b.blockPosition).emptyCell)
                isFinished = true;
        }
    }

    public boolean fallTetramino(Tetramino t){
        if( canMove(t, new BlockPosition(0,1)) ){
            t.fall();
            return true;
        }

        return false;
    }

    public boolean moveRight(){
        if( canMove(piece, new BlockPosition(1,0)) ){
            piece.right();
            return true;
        }

        return false;
    }
    public boolean moveLeft(){
        if( canMove(piece, new BlockPosition(-1,0)) ){
            piece.left();
            return true;
        }

        return false;
    }

    boolean rotatePiece() {
        if (piece.type == 2) {
            return true;
        } else {
            for (Block b : piece.shape) {
                if (b.emptyCell)
                    continue;

                Block blockRef = piece.shape[0];
                BlockPosition coorRef = BlockPosition.substract( b.blockPosition, blockRef.blockPosition);
                if (isBlocked(BlockPosition.add( blockRef.blockPosition, BlockPosition.rotate(coorRef))) ) {
                    return false;
                }
            }
            piece.rotate();
            return true;
        }
    }

    /**
     * Savoir si la pièce est capable de bouger
     * @param t
     * @param p
     * @return
     */
    public boolean canMove(Tetramino t, BlockPosition p){
        for (Block b : t.getTetramino()) {
            BlockPosition futurPos = BlockPosition.add(b.blockPosition, p);
            if (isBlocked(futurPos)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Réinitialise la grille, est appelée sur le shaker
     */
    public void rumble(){
        for (int i = 0; i < colonnes; i++) {
            for (int j = 0; j < lignes; j++) {
                this.grid[i][j] = new Block(i,j);
            }
        }
    }

    /**
     * Savoir si la pièce peut bouger
     * @param futurePosition
     * @return
     */
    private boolean isBlocked(BlockPosition futurePosition) {
        if (futurePosition.y < 0 || futurePosition.y >= lignes || futurePosition.x < 0 || futurePosition.x >= colonnes)
            return true;

        return !this.getBlock(futurePosition).emptyCell;
    }

    /**
     * Affichage des tétraminos sur la grille stockée
     * @param canvas
     */
    private void gridDisplay(Canvas canvas) {
        for(int cptLig = 0; cptLig < lignes; cptLig++){
            for(int colonnes = 0; colonnes < this.colonnes; colonnes++){
                blockDisplay(canvas, grid[colonnes][cptLig]);
            }
        }
    }

    /**
     * Affiche le score ou le game over
     * @param canvas
     */
    private void scoreDisplay(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(hautY*0.02f);
        if(isFinished){
            canvas.drawText("Game Over, touchez pour continuer.", largX*0.05f, hautY*0.10f, paint);
        } else {
            canvas.drawText("Score : " + score, largX*0.05f, hautY*0.10f, paint);
        }
    }

    private void tetraminoDisplay(Canvas c){
        for( Block b : piece.getTetramino() ){
            blockDisplay(c, b);
        }
    }

    /**
     * Affichage d'un bloc
     * @param canvas
     * @param b
     */
    private void blockDisplay(Canvas canvas, Block b){
        float x = largX*0.05f+b.blockPosition.getX()* largCase;
        float y = hautY*0.15f+b.blockPosition.getY()* hautCase;

        paint.setStrokeWidth(1f);
        paint.setColor( b.color);
        canvas.drawRect( x+1, y+1, x+ largCase -1, y+ hautCase -1, paint);
    }


    public Block getBlock(BlockPosition p ){
        return grid[p.x][p.y];
    }


    /**
     * Une fois que c'est fini on bloque l'interface afin de laisser le joueur cliquer pour aller rejoindre l"activité Highscore
     * @param view
     */
    public void onClick(View view) {
        if(isFinished){
            Intent highscoreIntent = new Intent(c, HighScore.class);
            c.startActivity(highscoreIntent);
        }
    }


    /**
     * Listener du shaker
     * @param sensor
     * @param values
     */
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {

            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

            float x = values[SensorManager.DATA_X];
            float y = values[SensorManager.DATA_Y];
            float z = values[SensorManager.DATA_Z];

            float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                System.out.println("Shake avec : " + speed + " de vitesse.");
                rumble();
            }
            last_x = x;
            last_y = y;
            last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(int i, int i1) {

    }

}
