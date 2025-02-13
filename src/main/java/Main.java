import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        GameLogic gameLogic = new GameLogic(); // Uses Dialogue System to print out the Story / Text and calls necessary other classes

        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI(gameLogic);
        });

    }
}