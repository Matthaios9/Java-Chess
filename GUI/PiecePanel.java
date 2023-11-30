package GUI;

// Importing the required packages
import javax.swing.JPanel;
import java.awt.*;


public class PiecePanel extends JPanel {
    // Declaring class variables
    public int x_pos;
    public int y_pos;
    public Color original_color;

    // Constructor with position coordinates
    public PiecePanel(int x, int y) {
        super();
        x_pos = x;
        y_pos = y;
    }
    // Constructor with gridlayout and position coordinates
    public PiecePanel(GridLayout g, int x, int y) {
        super(g);
        x_pos = x;
        y_pos = y;
    }
}