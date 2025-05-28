import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

/**
 *
 * @author bayui
 */
public class AppletMouse extends Applet implements MouseListener, MouseMotionListener {
    int width, height;
    Vector<Point> listOfPositions;

    public void init() {
        width = getSize().width;
        height = getSize().height;
        setBackground(Color.white);
        listOfPositions = new Vector<Point>();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    
    public void mouseMoved(MouseEvent e) {
        if (listOfPositions.size() >= 50) {
            // delete the first element
            listOfPositions.removeElementAt(0);
        }
        // add the new position to the list
        listOfPositions.addElement(new Point(e.getX(), e.getY()));
        repaint();
        e.consume();
    }
    
    public void mouseDragged(MouseEvent e) {}

    public void paint(Graphics g) {
        g.setColor(Color.black);  // Changed to black for better visibility
        for (int j = 1; j < listOfPositions.size(); ++j) {
            Point A = listOfPositions.elementAt(j-1);
            Point B = listOfPositions.elementAt(j);
            g.drawLine(A.x, A.y, B.x, B.y);
        }
    }
}