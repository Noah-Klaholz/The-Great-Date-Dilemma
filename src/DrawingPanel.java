
import javax.swing.*;
import java.awt.*;

// Should be used to draw background images (animation seems to be rather difficult)
public class DrawingPanel extends JPanel {

    boolean text = false;

    String displayedText = ""; // What’s currently shown
    int textIndex = 0; // Tracks how much of the text is revealed

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

    public void showText(Graphics g) {
        if(text) {
            Graphics2D g2 = (Graphics2D) g;
            printSpeechBubble(g2);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fm = g2.getFontMetrics();
            int textX = (getWidth() - fm.stringWidth(displayedText)) / 2;
            int textY = getHeight()-(getHeight()/6+50); // Slightly above the bottom
            g2.drawString(displayedText, textX, textY);
        }
    }

    public void text(String fullText) {
        text = true;
        Timer timer = new Timer(50, e -> {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        showText(g);
    }

}
