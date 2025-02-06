import javax.swing.*;
import java.awt.*;

// Should be used to draw background images (animation seems to be rather difficult)
public class GamePanel extends JPanel {
    private Image backgroundImage;

    public GamePanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    public void setBackgroundImage(String imagePath) {
        this.backgroundImage = new ImageIcon(imagePath).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,getWidth(),getHeight(), this);
    }

}
