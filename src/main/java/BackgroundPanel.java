import javax.swing.*;
import java.awt.*;
import java.net.URL;

// Should be used to draw background images (animation seems to be rather difficult)
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            System.out.println("Loaded background: " + imageUrl);
            this.backgroundImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Background image not found: " + imagePath);
        }
    }

    public void setBackgroundImage(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            System.out.println("Loaded background: " + imageUrl);
            this.backgroundImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("Background image not found: " + imagePath);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,getWidth(),getHeight(), this);
    }

}
