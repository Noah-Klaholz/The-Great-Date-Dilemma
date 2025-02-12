import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {

    // Inits for Frame
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    // Inits for Game Panel
    private final GameLogic gameLogic;
    private BackgroundPanel gameBackgroundPanel;
    public DrawingPanel gameDrawingPanel;

    // Initialise global Variables for Panels
    JPanel menuScreen;
    JPanel gameScreen;
    JPanel endingScreen;

    public GUI(GameLogic gameLogic) {

        this.gameLogic = gameLogic;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        setTitle("The Great Date Dilemma");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fullscreen mode
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            System.out.println("Fullscreen not supported");
            setBounds(0,0,800,800);
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(0,0,getWidth(), getHeight());

        //Create different screens
        menuScreen = createMenuScreen();
        gameScreen = createGameScreen();
        endingScreen = createEndingScreen();

        mainPanel.add(menuScreen, "Menu");
        mainPanel.add(gameScreen, "Game");
        mainPanel.add(endingScreen, "Ending");

        cardLayout.show(mainPanel, "Game");
        mainPanel.revalidate();
        mainPanel.repaint();

        setContentPane(mainPanel);
        setVisible(true);
    }

    public JPanel createGameScreen () {
        JPanel gameScreen = new JPanel(new BorderLayout());

        // Icons for Action Buttons
        ImageIcon OrigExitButtonIcon = new ImageIcon(CONST.exit_Button_path);
        Image scaledIcon = OrigExitButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon exitButtonIcon = new ImageIcon(scaledIcon);

        ImageIcon OrigSaveButtonIcon = new ImageIcon(CONST.save_Button_path);
        Image scaledIcon2 = OrigSaveButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon saveButtonIcon = new ImageIcon(scaledIcon2);

        // Settings for action Buttons
        JButton exitButton = new JButton(exitButtonIcon);
        exitButton.setBounds(60,0,exitButtonIcon.getIconWidth(),exitButtonIcon.getIconHeight());
        exitButton.addActionListener(e -> System.exit(0));

        JButton saveButton = new JButton(saveButtonIcon);
        saveButton.setBounds(0,0,saveButtonIcon.getIconWidth(),saveButtonIcon.getIconHeight());
        saveButton.addActionListener(e -> gameLogic.saveGame(this));

        // Create layeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(getWidth(), getHeight()));

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameBackgroundPanel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
                gameDrawingPanel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            }
        });

        // Create and add a BackgroundPanel
        gameBackgroundPanel = new BackgroundPanel(CONST.backgroundImage_path);

        // Create and add a DrawingPanel
        gameDrawingPanel = new DrawingPanel();

        // Add all components to layeredPane
        layeredPane.add(exitButton, Integer.valueOf(1));
        layeredPane.add(saveButton, Integer.valueOf(1));
        layeredPane.add(gameBackgroundPanel, Integer.valueOf(0));
        layeredPane.add(gameDrawingPanel, Integer.valueOf(1)); // Puts the Drawing Panel in foreground, use this for any other foreground features!

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof MouseEvent mouseEvent && mouseEvent.getID() == MouseEvent.MOUSE_CLICKED && !gameLogic.activeChoice && event.getSource() != exitButton && event.getSource() != saveButton) {
                if(gameDrawingPanel.text && gameDrawingPanel.fulltext.length()-1 > gameDrawingPanel.textIndex) {
                    gameDrawingPanel.finishText();
                } else gameLogic.next(this);
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        // Add layered Pane to gameScreen Panel
        gameScreen.add(layeredPane);
        return gameScreen;
    }

    public JPanel createEndingScreen() {
        endingScreen = new JPanel();
        // TO-DO
        return endingScreen;
    }

    public JPanel createMenuScreen() {
        menuScreen = new JPanel();
        // TO-DO
        return menuScreen;
    }

    public void setBackgroundImage(String backgroundImage_path) {
        gameBackgroundPanel.setBackgroundImage(backgroundImage_path);
        repaint();
    }

    public void text(String fulltext) {
        gameDrawingPanel.text(fulltext);
        repaint();
    }

    // Makes given choices visible on the Drawing Panel (# of Buttons with possible choices on them)
    // Add ActionListeners to Buttons and Upon actionEvent -> call
    public void showChoice(Choice choice) {
        gameLogic.activeChoice = true;
        gameDrawingPanel.showChoice(this, gameLogic, choice);
    }

    public void removeChoice() {
        gameDrawingPanel.removeChoices();
    }

    public void mainMenu() {
        cardLayout.show(mainPanel, "Menu");
    }

    public void showAchievement(String achievement) {
        gameDrawingPanel.showAchievement(achievement);
        repaint();
    }

    public void showEndingScreen() {
        cardLayout.show(mainPanel, "Ending");
    }

    public void showEnding(String ending_path) {
        // TO-DO
    }
}
