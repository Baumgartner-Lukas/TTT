package controller;

import model.GameBoard;
import model.Stone;

import static java.util.concurrent.ThreadLocalRandom.current;

public class SimpleAI {
    int counter = 1;

    public SimpleAI() {
    }

    /**
     * public method to use in the GameController class
     * @param model model of the current game board
     */
    protected void updateGameBoard(GameBoard model) {
        alwaysDraw(model);
        counter++;
    }

    /**
     * Adds stones randomly on the field. Easiest difficulty.
     * @param model model of the current game board
     */
    private void addRandomStone(GameBoard model) {
        int row = getRandomNumber();
        int col = getRandomNumber();
        while (model.getStone(row, col) != Stone.NONE) {
            row = getRandomNumber();
            col = getRandomNumber();
        }
        model.setStone(row, col, Stone.O);
    }

    /**
     * Adds stones in a way to the board, that should always lead to a draw
     * @param model model of the current game board
     */
    private void alwaysDraw(GameBoard model) {
        //if there is no stone set in the middle, set a stone in the middle
        if (counter == 1) {
            if (model.getStone(1, 1) == (Stone.NONE) && counter == 1) {
                model.setStone(1, 1, Stone.O);
            } else {
                //if there is a stone in the middle, set the stone in one of the edges
                model.setStone(getRandomEvenNumber(), getRandomEvenNumber(), Stone.O);
            }
        } else {
            if (!checkDiagonal(model)) {
                if (!checkRows(model)) {
                    if (!checkCols(model)) {
                        if (!checkCorners(model)) {
                            checkStraights(model);
                        }
                    }
                }
            }
        }
    }

    /**
     * checks if there is a free space on any of the middle lanes(0:1, 2:1, 1:0, 1:2)
     * @param model current model of the game board
     */
    private void checkStraights(GameBoard model) {
        int r = getRandomNumber();
        int c = getRandomNumber();
        if(model.getStone(r, c) == Stone.NONE && (r + c > 0 && r + c < 4)) {
            model.setStone(r, c, Stone.O);
        }else{
            checkStraights(model);
        }

    }

    /**
     * checks if any of the corners of the game board is free to set a stone
     * @param model current model of the game board
     * @return  true if there was a free corner and a friendly stone was set
     *          false if no corner was empty
     */
    private boolean checkCorners(GameBoard model) {
        int cornerCount = 0;
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 2; c++) {
                if (model.getStone(r * 2, c * 2) == Stone.X) {
                    cornerCount++;
                    if (cornerCount < 2 && model.getStone(r * 2, c * 2) == Stone.NONE) {
                        model.setStone(r * 2, c * 2, Stone.O);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are two enemy stones already in a diagonal position. If so, make the according counter move
     * If there is no enemy stone in the middle, skip that check.
     * @param model model of the current game board
     * @return  false if there is no enemy stone in the middle.
     *          true if there are two enemy stones in a diagonal pos and a counter move was made.
     */
    private boolean checkDiagonal(GameBoard model) {
        if (model.getStone(1, 1) != Stone.X) return false;
        if (model.getStone(1, 1) == Stone.X &&
                model.getStone(0, 0) == Stone.X &&
                model.getStone(2, 2) != Stone.O) {
            model.setStone(2, 2, Stone.O);
            return true;
        } else if (model.getStone(1, 1) == Stone.X &&
                model.getStone(0, 2) == Stone.X &&
                model.getStone(2, 0) != Stone.O) {
            model.setStone(2, 0, Stone.O);
            return true;
        } else if (model.getStone(1, 1) == Stone.X &&
                model.getStone(2, 0) == Stone.X &&
                model.getStone(0, 2) != Stone.O) {
            model.setStone(0, 2, Stone.O);
            return true;
        } else if (model.getStone(1, 1) == Stone.X &&
                model.getStone(2, 2) == Stone.X &&
                model.getStone(0, 0) != Stone.O) {
            model.setStone(0, 0, Stone.O);
            return true;
        }
        return false;
    }

    /**
     * Checks all rows if two enemy stones are in the same row
     * @param model model of the current game board
     * @return  false if there are no two enemy stones in the same row.
     *          true if there are two enemy stones in the same row and a counter move was made
     */
    private boolean checkRows(GameBoard model) {
        for (int r = 0; r < 3; r++) {
            int stoneCount = 0;
            for (int c = 0; c < 3; c++) {
                if (model.getStone(r, c) == Stone.X) {
                    stoneCount++;
                }else if(model.getStone(r,c) == Stone.O){
                    stoneCount--;
                }
            }
            if (stoneCount == 2) {
                counterMoveRow(model, r);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks columns if for enemy stones
     * @param model model of the current game board
     * @return  false if threre are no two enemy stones in the same column
     *          true if there are two enemy stones in the same column and a counter move was made
     */
    private boolean checkCols(GameBoard model) {
        for (int c = 0; c < 3; c++) {
            int stoneCount = 0;
            for (int r = 0; r < 3; r++) {
                if (model.getStone(r, c) == Stone.X) {
                    stoneCount++;
                }else if(model.getStone(r,c) == Stone.O) stoneCount--;
            }
            if (stoneCount == 2) {
                counterMoveCol(model, c);
                return true;
            }
        }
        return false;
    }

    /**
     * Sets a friendly stone in the appropriate position
     * @param model model of the current game board
     * @param c column in which the two enemy stones were found
     */
    private void counterMoveCol(GameBoard model, int c) {
        for (int r = 0; r < 3; r++) {
            if (model.getStone(r, c) == Stone.NONE) model.setStone(r, c, Stone.O);
        }
    }

    /**
     * Sets a friendly stone in the appropriate position
     * @param model model of the current game board
     * @param r row in which the two enemy stones were found
     */
    private void counterMoveRow(GameBoard model, int r) {
        for (int c = 0; c < 3; c++) {
            if (model.getStone(r, c) == Stone.NONE) model.setStone(r, c, Stone.O);
        }
    }

    /**
     * generates a random integer number with a range between 0 and 2
     * @return random int between 0 and 2
     */
    private int getRandomNumber() {
        return current().nextInt(0, 3);
    }

    /**
     * generates an even random number (0 or 2)
     * used to setting a stone in one of the corners
     * @return random even int (0 or 2)
     */
    private int getRandomEvenNumber() {
        return current().nextInt(0, 2) * 2;
    }

}
