

import controller.GameController;
import controller.SimpleAI;
import model.GameBoard;
import view.GameFieldView;

import java.io.IOException;

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

    public static void main(String[] args) throws IOException {
        GameBoard model = new GameBoard();
        GameFieldView view = new GameFieldView();
        SimpleAI sai = new SimpleAI(model);
        GameController controller = new GameController(model, view, sai);

        controller.play();
    }
}
