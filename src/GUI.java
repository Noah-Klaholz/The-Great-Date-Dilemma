import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {

    private final GameLogic gameLogic;
    private final BackgroundPanel backgroundPanel;
    public final DrawingPanel drawingPanel;

    public GUI(GameLogic gameLogic) {

        this.gameLogic = gameLogic;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        setTitle("The Great Date Dilemma");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,800,800);

        // Fullscreen mode

        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            System.out.println("Fullscreen not supported");
        }


        ImageIcon OrigExitButtonIcon = new ImageIcon(CONST.exit_Button_path);
        Image scaledIcon = OrigExitButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon exitButtonIcon = new ImageIcon(scaledIcon);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,getWidth(),getHeight());
        setContentPane(layeredPane);

        JButton exitButton = new JButton(exitButtonIcon);
        exitButton.setBounds(0,0,exitButtonIcon.getIconWidth(),exitButtonIcon.getIconHeight());
        exitButton.addActionListener(e -> System.exit(0));

        backgroundPanel = new BackgroundPanel(CONST.backgroundImage_path);
        backgroundPanel.setBounds(0,0,getWidth(), getHeight());

        drawingPanel = new DrawingPanel();
        drawingPanel.setBounds(0,0,getWidth(), getHeight());

        layeredPane.add(exitButton, Integer.valueOf(1));
        layeredPane.add(backgroundPanel, Integer.valueOf(0));
        layeredPane.add(drawingPanel, Integer.valueOf(1)); // Puts the Drawing Panel in foreground, use this for any other foreground features!

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof MouseEvent mouseEvent && mouseEvent.getID() == MouseEvent.MOUSE_CLICKED) {
                if(drawingPanel.text && drawingPanel.fulltext.length()-1 > drawingPanel.textIndex) {
                    drawingPanel.finishText();
                } else gameLogic.next(this);
                System.out.println("Mouse is clicked");
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        //Test Test
        repaint();
        setVisible(true);
    }

    public void setBackgroundImage(String backgroundImage_path) {
        backgroundPanel.setBackgroundImage(backgroundImage_path);
        repaint();
    }

    public void text(String fulltext) {
        drawingPanel.text(fulltext);
        repaint();
    }

}
