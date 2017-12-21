package view;

import model.GameBoard;

public class GameFieldView {
    public void printGameField(GameBoard board){
        System.out.println("  0|1|2|");
        for(int row = 0; row < GameBoard.SIZE; row++){
            System.out.print(row);
            for(int col = 0; col < GameBoard.SIZE; col++){
                System.out.print("|");
                System.out.print(board.getStone(row, col));
            }
            System.out.println("|");
        }

    }
}
