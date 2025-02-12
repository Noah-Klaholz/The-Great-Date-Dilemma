
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Should be used to draw background images (animation seems to be rather difficult)
public class DrawingPanel extends JPanel {

    public boolean text = false;

    String displayedText = ""; // What’s currently shown
    public String fulltext = "";
    int textIndex = 0; // Tracks how much of the text is revealed
    ArrayList<JButton> buttons = new ArrayList<>();

    public DrawingPanel() {
        setOpaque(false);
    }

    public void printSpeechBubble(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(100, getHeight()-(getHeight()/4),getWidth()-200,getHeight()/8,50, 100);
        g2.setColor(Color.PINK);
        g2.setStroke(new BasicStroke(10));
        g2.drawRoundRect(100, getHeight()-(getHeight()/4),getWidth()-200,getHeight()/8,50, 100);
    }

    // Draws text according to Variable displayedText
    public void showText(Graphics g) {
        if(text) {
            Graphics2D g2 = (Graphics2D) g;
            printSpeechBubble(g2);
            g2.setFont(new Font("Arial", Font.BOLD, CONST.TextSize));
            FontMetrics fm = g2.getFontMetrics();
            int textX = (getWidth() - fm.stringWidth(displayedText)) / 2;
            int textY = getHeight()-(getHeight()/6);
            g2.drawString(displayedText, textX, textY);
        }
    }

    // Makes characters show up one at a time using a Timer
    public void text(String fullText) {
        this.fulltext = fullText;
        text = true;
        Timer timer = new Timer(CONST.textDelay, e -> {
            if (textIndex < fullText.length()) {
                displayedText += fullText.charAt(textIndex);
                textIndex++;
                repaint(); // Refresh panel to update text
            } else {
                ((Timer) e.getSource()).stop(); // Stop when full text is displayed
            }
        });
        timer.start();
    }

    public void finishText() {
        displayedText = fulltext;
        textIndex = fulltext.length();
        repaint();
    }

    public void showAchievement(String achievement) {
        JLabel floatingLabel = new JLabel("Achievement gained: " + achievement);
        floatingLabel.setOpaque(true);
        floatingLabel.setBackground(new Color(0, 0, 0, 0));
        floatingLabel.setForeground(Color.WHITE);
        floatingLabel.setBounds(getWidth() - 200, 50, 180, 30); // Adjust position

        setLayout(null); // Required for absolute positioning
        add(floatingLabel);
        revalidate();
        repaint();

        // Make the label disappear after 2 seconds
        Timer timer = new Timer(2000, e -> {
            remove(floatingLabel);
            revalidate();
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }


    public void showChoice(GUI gui, GameLogic gameLogic,Choice choice) {
        if(gameLogic.activeChoice) {
            int x = getWidth()-50;
            int y = getHeight()/2;
            for(int i=0; i<choice.choices.length; i++) {
                int index = i;
                String s = choice.choices[i];
                JButton button = new JButton(s);
                button.addActionListener(e -> {
                    gameLogic.choose(gui, choice, index);
                });
                button.setBounds(x-CONST.ButtonWidth,y-CONST.ButtonHeight,CONST.ButtonWidth,CONST.ButtonHeight);
                buttons.add(button);
                add(button);
                y += getHeight()/10;
            }
            repaint();
        }
    }

    public void removeChoices() {
        for(JButton button : buttons) {
            remove(button);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        showText(g);
    }

}
