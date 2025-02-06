import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GUI {
    String backgroundImage_path = "Media/img.png";
    private final JFrame frame;
    private final GamePanel panel;

    public GUI(Dimension d) {

        frame = new JFrame("The Great Date Dilemma");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(d);

        panel = new GamePanel(backgroundImage_path);



        frame.add(panel);

        frame.setVisible(true);
    }

    public void setBackgroundImage(String backgroundImage_path) {
        panel.setBackgroundImage(backgroundImage_path);
    }
}
