package controller;

import model.GameBoard;
import model.Stone;
import view.GameFieldView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameController {
    private BufferedReader reader;
    private GameFieldView view;
    private GameBoard model;
    private SimpleAI simpleAI;
    private boolean SimpleAIisActive = false;


    public GameController(GameBoard model, GameFieldView view, SimpleAI sai) {
        this.model = model;
        this.view = view;
        this.simpleAI = sai;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private int counter = 0; //counter to determine which players turn it is and when the max. turns are reached

    public void play() throws IOException {
        int[] input = new int[2];
        System.out.println("Single- or Multiplayer (S | M): ");
        String opt = reader.readLine();
        if (opt.trim().toLowerCase().equals("s")) {
            SimpleAIisActive = true;
        }
        while (counter < GameBoard.TURNS) {
            //print Field
            view.printGameField(model);
            if (SimpleAIisActive && counter % 2 == 1) {
                simpleAI.updateGameBoard(model);
                if (hasWon()) {
                    System.out.printf("%n AI has won! You noob %n");
                    view.printGameField(model);
                    return;
                }
                System.out.printf("%n AI-Turn %n%n");
                view.printGameField(model);
                counter++;
            }
            //prompt players for their turn
            try {
                input = prompt();
                while (input[0] < 0 || input[0] > 2 || input[1] < 0 || input[1] > 2) {
                    System.err.printf("Row and Col must be between 0 - 2. %n");
                    input = prompt();
                }
                while (!isValidMove(input)) {
                    System.err.printf("This field is already taken! %n");
                    input = prompt();
                }
            } catch (IOException ioe) {
                System.err.println("Error reading input");
                ioe.printStackTrace();
            }
            placeStone(input);
            if (hasWon()) {
                view.printGameField(model);
                System.out.printf("%nPlayer %d has won! GG EZ %n", counter % 2 + 1);
                return;
            }
            counter++;
        }
        view.printGameField(model);
        System.out.println("Game finished with a draw!");
    }

    /**
     * For readability
     *
     * @return returns True if one of those conditions is true
     */
    private boolean hasWon() {
        return checkStraight() || checkDiagonal();
    }

    /**
     * Checks if there are 3 same Stones in a diagonal line
     *
     * @return returns true if 3 Stones are found. False if not.
     */
    private boolean checkDiagonal() {
        //middle Stone
        Stone s = model.getStone(1, 1);
        return s != Stone.NONE && (
                s == model.getStone(0, 0) && s == model.getStone(2, 2) ||
                        s == model.getStone(0, 2) && s == model.getStone(2, 0));
    }

    /**
     * Checks if there are 3 same Stones in a straight line
     *
     * @return returns true if 3 Stones are found. False if not.
     */
    protected boolean checkStraight() {
        int i = 0;
        while (i < 3) {
            Stone sCol = model.getStone(0, i);
            Stone sRow = model.getStone(i, 0);
            if (sCol == model.getStone(1, i) && sCol == model.getStone(2, i) && sCol != Stone.NONE) return true;
            if (sRow == model.getStone(i, 1) && sRow == model.getStone(i, 2) && sRow != Stone.NONE) return true;
            i++;
        }
        return false;
    }

    /**
     * Checks if the user puts a Stone on a valid (empty) position on the board
     *
     * @param input row and col of field where to set the stone
     * @return returns true if the input field is empty
     */
    protected boolean isValidMove(int[] input) {
        int row = input[0];
        int col = input[1];
        return (model.getStone(row, col) == Stone.NONE);
    }

    protected void placeStone(int[] input) {
        int row = input[0];
        int col = input[1];

        if (counter % 2 == 0) {
            model.setStone(row, col, Stone.X);
        } else {
            model.setStone(row, col, Stone.O);
        }
    }

    /**
     * Prompts the player for the position where to set the stone
     *
     * @return returns the inputarray [0] = row, [1] = col
     * @throws IOException Throws an Exception if the inputvalues are out of bound of the gamefield
     */
    private int[] prompt() throws IOException {
        int player;
        int[] input = new int[2];
        player = counter % 2 + 1;
        System.out.println("==========");
        System.out.printf("It is player %d's turn! %n", player);
        System.out.println("Give Row: ");
        input[0] = Integer.parseInt(reader.readLine());
        System.out.println("Give Col: ");
        input[1] = Integer.parseInt(reader.readLine());

        return input;
    }

    protected int getCounter(){
        return counter;
    }
}
