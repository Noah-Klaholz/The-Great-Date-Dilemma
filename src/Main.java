import java.awt.*;

public class Main {

    public static void main(String[] args) {

        GameLogic gameLogic = new GameLogic(); // Uses Dialogue System to print out the Story / Text and calls necessary other classes

        GUI gui = new GUI(gameLogic);

        // Test Background image change


/*
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gui.setBackgroundImage("Media/TestBackground2.png");
        gui.text("Hallo ich bin ein text.");


 */






    }
}