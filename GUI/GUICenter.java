package GUI;
//importing required packages
import javax.swing.*;
import java.awt.*;

//class to set the game Frame in the centre
public class GUICenter{

    //method taking a Jframe as argument and setting it in centre of the screen
    public static void center(JFrame frame) {

        //getting size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        //getting width of JFrame
        int w = frame.getSize().width;
        //getting height of JFrame
        int h = frame.getSize().height;

        //x,y coordinates to set the Frame in the centre
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
 
        //setting frame centralized on the screen
        frame.setLocation(x, y);
 
    }


    //method taking JDialog as argument and setting it in centre of Screen
    public static void center(JDialog dialog) {

        //getting size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        //getting width of JDialog
        int w = dialog.getSize().width;
        //getting height of JFrame
        int h = dialog.getSize().height;
 
        //x,y coordinates to set the Dialog box in the centre
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        //setting Dialog box on the center of screen
        dialog.setLocation(x, y);
 
    }
}