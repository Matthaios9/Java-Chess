package GUI;

// Importing required packages
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class PieceLabel extends JLabel {
    // Declaring coordinates class variables
    public int x_pos;
    public int y_pos;

    // Contructor with image icon and position coordinates
    public PieceLabel(ImageIcon i, int x, int y) {
        super(i);
        x_pos = x;
        y_pos = y;

    }
    
    // Contructor with position coordinates
    public PieceLabel(int x, int y) {
        super();
        x_pos = x;
        y_pos = y;

    }
}