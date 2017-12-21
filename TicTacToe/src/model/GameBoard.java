package model;

public class GameBoard {
    public static final int SIZE = 3;
    public static final int TURNS = SIZE * SIZE;

    private Stone grid[][] = new Stone[SIZE][SIZE];

    //Fill the new GameBoard with NONE(" ") Stones
    public GameBoard(){
        for(int r = 0; r < SIZE; r++){
            for(int c = 0; c < SIZE; c++){
                grid[r][c] = Stone.NONE;
            }
        }
    }

    public Stone getStone(int row, int col) {
        return grid[row][col];
    }

    public void setStone(int row, int col, Stone stone) {
            grid[row][col] = stone;
    }
}
