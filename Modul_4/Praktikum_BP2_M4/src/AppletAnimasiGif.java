import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

/**
 * 
 * @author Muhammad Rizal Nurfirdaus
 */
public class AppletAnimasiGif extends Applet {
    private Image gifImage;

    @Override
    public void init() {
        // Load .gif file from the applet code base (directory where the .class file is located)
        gifImage = getImage(getCodeBase(), "animasi.gif");
    }

    @Override
    public void paint(Graphics g) {
        // Gambar animasi di tengah applet
        int x = (getWidth() - gifImage.getWidth(this)) / 2;
        int y = (getHeight() - gifImage.getHeight(this)) / 2;
        g.drawImage(gifImage, x, y, this);
    }
}
