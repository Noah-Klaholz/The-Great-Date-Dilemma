import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        GUI gui = new GUI(size); // Uses the Dimension size to adjust to screen size


        DialogueSystem dialogueSystem = new DialogueSystem(gui); // Uses the GUI to add buttons, labels etc. - everything that's necessary for the System to work

        GameLogic gameLogic = new GameLogic(dialogueSystem); // Uses Dialogue System to print out the Story / Text


        // Test Background image change

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gui.setBackgroundImage("Media/img_1.png");
        gui.text("Hallo ich bin ein text.");


    }
}