package com.lotharie.tetra.pieces;

import android.graphics.Color;

public class Tetramino {

    public Block[] shape;
    public int     type;

    public Tetramino(int type){

        BlockPosition[] tetramino;

        switch (type){
            case 1 : { // I
                tetramino = new BlockPosition[]{
                        new BlockPosition(5, 0),
                        new BlockPosition(4, 0),
                        new BlockPosition(6, 0),
                        new BlockPosition(7, 0),
                };

                blockGen(tetramino, Color.CYAN);
                break;
            }
            case 2 : { // O
                tetramino = new BlockPosition[]{
                        new BlockPosition(4, 0),
                        new BlockPosition(5, 0),
                        new BlockPosition(4, 1),
                        new BlockPosition(5, 1),
                };

                blockGen(tetramino, Color.YELLOW);
                break;
            }
            case 3 : { // T
                tetramino = new BlockPosition[]{
                        new BlockPosition(5, 0),
                        new BlockPosition(4, 0),
                        new BlockPosition(6, 0),
                        new BlockPosition(5, 1),
                };

                blockGen(tetramino, Color.MAGENTA);
                break;
            }
            case 4 : { // L
                tetramino = new BlockPosition[]{
                        new BlockPosition(4, 0),
                        new BlockPosition(4, 1),
                        new BlockPosition(5, 0),
                        new BlockPosition(6, 0),
                };

                blockGen(tetramino, Color.DKGRAY);
                break;
            }
            case 5 : { // J
                tetramino = new BlockPosition[]{
                        new BlockPosition(4, 0),
                        new BlockPosition(5, 0),
                        new BlockPosition(6, 0),
                        new BlockPosition(6, 1),
                };

                blockGen(tetramino, Color.BLUE);
                break;
            }
            case 6 : { // Z
                tetramino = new BlockPosition[]{
                        new BlockPosition(4, 0),
                        new BlockPosition(5, 0),
                        new BlockPosition(5, 1),
                        new BlockPosition(6, 1),
                };

                blockGen(tetramino, Color.RED);
                break;
            }
            case 7 : { // S
                tetramino = new BlockPosition[]{
                        new BlockPosition(4, 0),
                        new BlockPosition(5, 0),
                        new BlockPosition(4, 1),
                        new BlockPosition(3, 1),
                };

                blockGen(tetramino, Color.GREEN);
                break;
            }

        }

    }

    private void blockGen(BlockPosition[] tabPos, int couleur) {
        shape = new Block[tabPos.length];
        for( int i =0; i < tabPos.length; i++){
            shape[i] = new Block(tabPos[i].getX(), tabPos[i].getY(), couleur,
                    this, false);
        }
    }


    public void fall() {
        for (Block b : shape) {
            b.blockPosition.y++;
        }
    }

    public void rotate() {
        Block blockRefenrentiel = shape[0];

        for( Block b : getTetramino() ){
            BlockPosition referentielBlockPosition = BlockPosition.substract( b.blockPosition, blockRefenrentiel.blockPosition);
            b.setBlockPosition( BlockPosition.add( blockRefenrentiel.blockPosition, BlockPosition.rotate(referentielBlockPosition)) );
        }
    }

    public void left() {
        for (Block b : shape) {
            b.blockPosition.x--;
        }
    }

    public void right() {
        for (Block b : shape) {
            b.blockPosition.x++;
        }
    }

    public Block[] getTetramino() {
        return shape;
    }
}
