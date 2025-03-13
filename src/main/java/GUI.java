import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.*;

public class GUI extends JFrame {

    // Inits for Frame
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    // Inits for Game Panel
    private final GameLogic gameLogic;
    private BackgroundPanel gameBackgroundPanel;
    public DrawingPanel gameDrawingPanel;

    // Init for achievementLabels
    public ArrayList<JLabel> achievementLabels;

    // Initialise global Variables for Panels
    JPanel menuScreen;
    JPanel gameScreen;
    JPanel endingScreen;
    JPanel achievementScreen;
    private Map<String, JPanel> panels = new HashMap<>();

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
        achievementScreen = createAchievementScreen();

        addPanel(menuScreen, "Menu");
        addPanel(gameScreen, "Game");
        addPanel(endingScreen, "Ending");
        addPanel(achievementScreen, "Achievement");

        cardLayout.show(mainPanel, "Menu");
        mainPanel.revalidate();
        mainPanel.repaint();

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void addPanel(JPanel panel, String name) {
        panels.put(name, panel);
        mainPanel.add(panel, name);
    }

    private void refreshPanel(String panelName) {
        JPanel oldPanel = panels.get(panelName);
        mainPanel.remove(oldPanel);

        switch(panelName) {
            case "Menu":
                addPanel(createMenuScreen(), panelName);
                break;
            case "Game":
                addPanel(createGameScreen(), panelName);
                break;
            case "Ending":
                addPanel(createEndingScreen(), panelName);
                break;
            case "Achievement":
                addPanel(createAchievementScreen(), panelName);
                break;
        }

        cardLayout.show(mainPanel, panelName);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
        return layeredPane;
    }

    public JPanel createGameScreen () {
        JPanel gameScreen = new JPanel(new BorderLayout());

        // Icons for Action Buttons
        ImageIcon OrigExitButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.exit_Button_path)));
        Image scaledIcon = OrigExitButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon exitButtonIcon = new ImageIcon(scaledIcon);

        /*
        ImageIcon OrigSaveButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.save_Button_path)));
        Image scaledIcon2 = OrigSaveButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon saveButtonIcon = new ImageIcon(scaledIcon2);
         */

        // Creation of action Buttons
        /*
        JButton saveButton = new JButton(saveButtonIcon);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.setBounds(60,0,saveButtonIcon.getIconWidth(),saveButtonIcon.getIconHeight());
        saveButton.addActionListener(e -> gameLogic.saveGame(this, true));
         */

        JButton exitButton = new JButton(exitButtonIcon);
        exitButton.setBounds(0,0,exitButtonIcon.getIconWidth(),exitButtonIcon.getIconHeight());
        exitButton.addActionListener(e -> System.exit(0));

        // Create layeredPane
        JLayeredPane gameLayeredPane = createLayeredPane();
        gameLayeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameBackgroundPanel.setBounds(0, 0, gameLayeredPane.getWidth(), gameLayeredPane.getHeight());
                gameDrawingPanel.setBounds(0, 0, gameLayeredPane.getWidth(), gameLayeredPane.getHeight());
            }
        });

        // Create and add a BackgroundPanel
        gameBackgroundPanel = new BackgroundPanel(CONST.backgroundImage_path);

        // Create and add a DrawingPanel
        gameDrawingPanel = new DrawingPanel();

        // Add all components to layeredPane
        gameLayeredPane.add(exitButton, Integer.valueOf(1));
        //gameLayeredPane.add(saveButton, Integer.valueOf(1));
        gameLayeredPane.add(gameBackgroundPanel, Integer.valueOf(0));
        gameLayeredPane.add(gameDrawingPanel, Integer.valueOf(1)); // Puts the Drawing Panel in foreground, use this for any other foreground features!

        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof MouseEvent mouseEvent && mouseEvent.getID() == MouseEvent.MOUSE_CLICKED && !gameLogic.activeChoice && event.getSource() != exitButton ) { // && event.getSource() != saveButton
                if(gameDrawingPanel.text && gameDrawingPanel.fulltext.length()-1 > gameDrawingPanel.textIndex) {
                    gameDrawingPanel.finishText();
                } else gameLogic.next(this);
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        // Add layered Pane to gameScreen Panel
        gameScreen.add(gameLayeredPane);
        return gameScreen;
    }

    public JPanel createEndingScreen() {
        endingScreen = new JPanel(new BorderLayout());

        JLayeredPane endingLayeredPane = createLayeredPane();

        ImageIcon OrigExitButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.exit_Button_path)));
        Image scaledIcon = OrigExitButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon exitButtonIcon = new ImageIcon(scaledIcon);

        ImageIcon OrigMenuButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.menu_Button_path)));
        Image scaledIcon2 = OrigMenuButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon menuButtonIcon = new ImageIcon(scaledIcon2);

        // Create Buttons
        JButton exitButton = new JButton(exitButtonIcon);
        exitButton.addActionListener(e -> System.exit(0));

        JButton menuButton = new JButton(menuButtonIcon);
        menuButton.addActionListener(e -> mainMenu());

        JLabel titleLabel = new JLabel("Thank you for playing!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, CONST.TextSize));
        titleLabel.setForeground(Color.WHITE);

        JLabel earningsLabel = new JLabel("");
        earningsLabel.setFont(new Font("Arial", Font.BOLD, CONST.TextSize));
        earningsLabel.setForeground(Color.WHITE);

        if(!gameLogic.addedAchievements.isEmpty()) {
            earningsLabel.setText("You have earned the following achievements: ");
            achievementLabels = new ArrayList<>();
            Set<Integer> achievements = gameLogic.addedAchievements;
            int x = 100;
            int y = 100;
            for(Integer achievement : achievements) {
                int index = achievement;
                JLabel label = new JLabel(gameLogic.achievementsParser.parseLine(index));
                System.out.println(gameLogic.achievementsParser.parseLine(index));
                label.setFont(new Font("Arial", Font.BOLD, CONST.TextSize));
                label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
                label.setForeground(Color.YELLOW);
                label.setOpaque(false);

                achievementLabels.add(label);
                endingLayeredPane.add(label, Integer.valueOf(1));
                y += getHeight() / 10;
                if(y >= getHeight() - 100) {
                    x = getWidth()-50;
                    y = 100;
                }
            }
        }

        // Create BackgroundPanel
        BackgroundPanel endingBackgroundPanel = new BackgroundPanel(CONST.ending_Background_Path);
        endingBackgroundPanel.setLayout(null);

        endingLayeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                endingBackgroundPanel.setBounds(0,0,getWidth(),getHeight());
                exitButton.setBounds(0,0,exitButtonIcon.getIconWidth(),exitButtonIcon.getIconHeight());
                menuButton.setBounds(60,0,menuButtonIcon.getIconWidth(),menuButtonIcon.getIconHeight());
                titleLabel.setBounds((getWidth()-titleLabel.getPreferredSize().width)/2,50,titleLabel.getPreferredSize().width,titleLabel.getPreferredSize().height);
                earningsLabel.setBounds((getWidth()-earningsLabel.getPreferredSize().width)/2,150,earningsLabel.getPreferredSize().width,earningsLabel.getPreferredSize().height);
            }
        });

        endingLayeredPane.add(exitButton, Integer.valueOf(1));
        endingLayeredPane.add(menuButton, Integer.valueOf(1));
        endingLayeredPane.add(titleLabel, Integer.valueOf(1));
        endingLayeredPane.add(earningsLabel, Integer.valueOf(1));
        endingLayeredPane.add(endingBackgroundPanel, Integer.valueOf(0));

        endingScreen.add(endingLayeredPane);
        return endingScreen;
    }

    public JPanel createMenuScreen() {
        menuScreen = new JPanel(new BorderLayout());

        JLayeredPane menuLayeredPane = createLayeredPane();
        menuLayeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameBackgroundPanel.setBounds(0, 0, menuLayeredPane.getWidth(), menuLayeredPane.getHeight());
                gameDrawingPanel.setBounds(0, 0, menuLayeredPane.getWidth(), menuLayeredPane.getHeight());
            }
        });

        // Create Icons for Buttons and Labels
        ImageIcon OrigExitButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.exit_Button_path)));
        Image scaledIcon = OrigExitButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon exitButtonIcon = new ImageIcon(scaledIcon);

        ImageIcon OrigStartButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.start_Button_path)));
        Image scaledIcon2 = OrigStartButtonIcon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        ImageIcon startButtonIcon = new ImageIcon(scaledIcon2);

        /*
        ImageIcon OrigLoadGameButton = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.load_game_Button_path)));
        Image scaledIcon3 = OrigLoadGameButton.getImage().getScaledInstance(80,80, Image.SCALE_SMOOTH);
        ImageIcon loadGameButtonIcon = new ImageIcon(scaledIcon3);
         */

        ImageIcon OrigAchievementButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.achievement_Button_path)));
        Image scaledIcon4 = OrigAchievementButtonIcon.getImage().getScaledInstance(80,80, Image.SCALE_SMOOTH);
        ImageIcon achievementButtonIcon = new ImageIcon(scaledIcon4);

        ImageIcon OrigTitleLabelIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.title_path)));
        Image scaledIcon5 = OrigTitleLabelIcon.getImage().getScaledInstance(300,300, Image.SCALE_SMOOTH);
        ImageIcon titleLabelIcon = new ImageIcon(scaledIcon5);

        // Create Buttons and labels
        JButton exitButton = new JButton(exitButtonIcon);
        exitButton.setBounds(0,0,exitButtonIcon.getIconWidth(),exitButtonIcon.getIconHeight());
        exitButton.addActionListener(e -> System.exit(0));

        JButton startButton = new JButton(startButtonIcon);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setBounds(getWidth()/2-50,getHeight()/2+100,startButtonIcon.getIconWidth(),startButtonIcon.getIconHeight());
        startButton.addActionListener(e -> gameLogic.startGame(this));

        /*
        JButton loadGameButton = new JButton(loadGameButtonIcon);
        SaveData saveData = SafeFileSystem.loadGame();
        if(!saveData.getPlayerSaved()) {
            loadGameButton.setEnabled(false);
            loadGameButton.setContentAreaFilled(false);
            loadGameButton.setBorderPainted(false);
        }
        loadGameButton.setBounds(getWidth()/2-40,getHeight()/2+200,loadGameButtonIcon.getIconWidth(),loadGameButtonIcon.getIconHeight());
        loadGameButton.addActionListener(e -> gameLogic.loadGame(this));
         */

        JButton achievementButton = new JButton(achievementButtonIcon);
        achievementButton.setContentAreaFilled(false);
        achievementButton.setBorderPainted(false);
        achievementButton.setBounds(getWidth()/2-40,getHeight()/2+200,achievementButtonIcon.getIconWidth(),achievementButtonIcon.getIconHeight());
        achievementButton.addActionListener(e -> showAchievement());

        JLabel titleLabel = new JLabel(titleLabelIcon);
        titleLabel.setBounds(getWidth()/2-150,50,titleLabelIcon.getIconWidth(),titleLabelIcon.getIconHeight());

        // Create BackgroundPanel
        BackgroundPanel menubackgroundPanel = new BackgroundPanel(CONST.menuBackgroundImage_path);
        menubackgroundPanel.setLayout(null);
        menubackgroundPanel.setBounds(0,0,getWidth(),getHeight());

        menuLayeredPane.add(exitButton, Integer.valueOf(1));
        menuLayeredPane.add(startButton, Integer.valueOf(1));
        menuLayeredPane.add(titleLabel, Integer.valueOf(1));
        menuLayeredPane.add(achievementButton, Integer.valueOf(1));
        //menuLayeredPane.add(loadGameButton, Integer.valueOf(1));
        menuLayeredPane.add(menubackgroundPanel, Integer.valueOf(0));

        menuScreen.add(menuLayeredPane);
        return menuScreen;
    }

    public JPanel createAchievementScreen() {
        achievementScreen = new JPanel(new BorderLayout());

        JLayeredPane achievementLayeredPane = createLayeredPane();

        // Create Icons for Buttons
        ImageIcon OrigExitButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.exit_Button_path)));
        Image scaledIcon = OrigExitButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon exitButtonIcon = new ImageIcon(scaledIcon);

        ImageIcon OrigMenuButtonIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(CONST.menu_Button_path)));
        Image scaledIcon2 = OrigMenuButtonIcon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH);
        ImageIcon menuButtonIcon = new ImageIcon(scaledIcon2);

        // Create Buttons
        JButton exitButton = new JButton(exitButtonIcon);
        exitButton.addActionListener(e -> System.exit(0));

        JButton menuButton = new JButton(menuButtonIcon);
        menuButton.addActionListener(e -> mainMenu());

        // Create BackgroundPanel
        BackgroundPanel menubackgroundPanel = new BackgroundPanel(CONST.achievement_Background_Path);
        menubackgroundPanel.setLayout(null);

        achievementLayeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                menubackgroundPanel.setBounds(0,0,getWidth(),getHeight());
                exitButton.setBounds(0,0,exitButtonIcon.getIconWidth(),exitButtonIcon.getIconHeight());
                menuButton.setBounds(60,0,menuButtonIcon.getIconWidth(),menuButtonIcon.getIconHeight());
            }
        });

        // Create Labels for each achievement
        achievementLabels = new ArrayList<>();
        SaveData saveData = SafeFileSystem.loadGame();
        Set<Integer> achievements = saveData.getAchievements();
        int x = 100;
        int y = 100;
        for(Integer achievement : achievements) {
            int index = achievement;
            JLabel label = new JLabel(gameLogic.achievementsParser.parseLine(index));
            System.out.println(gameLogic.achievementsParser.parseLine(index));
            label.setFont(new Font("Arial", Font.BOLD, CONST.TextSize));
            label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
            label.setForeground(Color.YELLOW);
            label.setOpaque(false);

            achievementLabels.add(label);
            achievementLayeredPane.add(label, Integer.valueOf(1));
            y += getHeight() / 10;
            if(y >= getHeight() - 100) {
                x = getWidth()-50;
                y = 100;
            }
        }

        achievementLayeredPane.add(exitButton, Integer.valueOf(1));
        achievementLayeredPane.add(menuButton, Integer.valueOf(1));
        achievementLayeredPane.add(menubackgroundPanel, Integer.valueOf(0));

        achievementScreen.add(achievementLayeredPane);

        return achievementScreen;
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

    public void showAchievement(String achievement) {
        gameDrawingPanel.showAchievement(achievement);
        repaint();
    }

    public void showEndingScreen() {
        refreshPanel("Ending");
    }

    public void mainMenu() {
        refreshPanel("Menu");
    }

    public void showGame() {
        cardLayout.show(mainPanel, "Game");
    }

    public void showAchievement() {
        refreshPanel("Achievement");
    }
}
