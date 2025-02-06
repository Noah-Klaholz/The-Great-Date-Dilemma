import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{
    String backgroundImage_path = "Media/img.png";
    private final BackgroundPanel backgroundPanel;
    private final DrawingPanel drawingPanel;
    private final JLayeredPane layeredPane;

    public GUI(Dimension d) {

        setTitle("The Great Date Dilemma");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(d);
        //setLayout(null); // POSSIBLE MISTAKE !!

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,getWidth(),getHeight());
        setContentPane(layeredPane);

        backgroundPanel = new BackgroundPanel(backgroundImage_path);
        backgroundPanel.setBounds(0,0,getWidth(), getHeight());
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        drawingPanel = new DrawingPanel();
        drawingPanel.setBounds(0,0,getWidth(), getHeight());
        layeredPane.add(drawingPanel, Integer.valueOf(1));

        repaint();
        setVisible(true);
    }

    public void setBackgroundImage(String backgroundImage_path) {
        backgroundPanel.setBackgroundImage(backgroundImage_path);
    }

    public void text(String fulltext) {
        drawingPanel.text(fulltext);
    }
}
