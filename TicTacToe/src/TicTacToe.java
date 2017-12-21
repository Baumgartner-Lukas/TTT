

import controller.GameController;
import model.GameBoard;
import view.GameFieldView;

/**
 * TicTacToe - Game
 * Code to practice MVC Pattern
 *
 * MVC-Pattern:
 *
 * Model - Model represents an object carrying data.
 * It can also have logic to update controller if its data changes.
 *
 * View - View represents the visualization of the data that model contains.
 *
 *  Controller - Controller acts on both model and view.
 *  It controls the data flow into model object and updates the view whenever data changes.
 *  It keeps view and model separate.
 *
 */
public class TicTacToe {

    public static void main(String[] args) {
        GameBoard model = new GameBoard();
        GameFieldView view = new GameFieldView();
        GameController controller = new GameController(model, view);

        controller.play();
    }
}
