package com.lotharie.tetra.pieces;


public class Block {
    public BlockPosition blockPosition;
    public int color;
    public Tetramino tetramino;
    public boolean emptyCell;

    /**
     * Block représente une cellule de la grille
     * @param x
     * @param y
     * @param color
     * @param tetramino
     * @param emptyCell
     */
    public Block(int x, int y, int color, Tetramino tetramino, boolean emptyCell) {
        blockPosition = new BlockPosition(x, y);
        this.color = color;
        this.tetramino = tetramino;
        this.emptyCell = emptyCell;
    }

    /**
     * Block représente une cellule vide de la grille
     * @param x
     * @param y
     */
    public Block(int x, int y){
        blockPosition = new BlockPosition(x, y);
        emptyCell = true;
        tetramino = null;
    }

    public void setBlock(Block b){
        setBlockPosition(b.blockPosition);
        this.color = b.color;
        this.tetramino = b.tetramino;
        this.emptyCell = b.emptyCell;
    }

    public void setBlockPosition(BlockPosition p){
        this.blockPosition = p;
    }

    public BlockPosition getBlockPosition(){
        return blockPosition;
    }
}
