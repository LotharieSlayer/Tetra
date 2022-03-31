package com.lotharie.tetra.pieces;

public class BlockPosition {
    public int y;
    public int x;

    /**
     *
     * @param x Colonne
     * @param y Ligne
     */
    public BlockPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static BlockPosition rotate(BlockPosition p){
        return  new BlockPosition( -p.y, p.x);
    }
    public static BlockPosition add(BlockPosition p1, BlockPosition p2){
        return new BlockPosition( p1.x + p2.x, p1.y + p2.y);
    }
    public static BlockPosition substract(BlockPosition p1, BlockPosition p2){
        return new BlockPosition( p1.x - p2.x, p1.y - p2.y);
    }

    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
}
