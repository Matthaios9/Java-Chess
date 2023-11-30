import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.*;
import java.util.List;
import java.io.*;
import GUI.GUICenter;

public class App {

    // creating a text area
    private JPanel text_area = new JPanel();

    // creating a JFrame
    private JFrame frame = new JFrame("Resort Management System");

    // creating a JPanel
    private JPanel button_panel = new JPanel();
    private Timer timer;

    private boolean move_selected = false;
    private int move_ind = 0;

    // creating a refrence of JButton
    private JButton selected_button = null;

    private ArrayList<String> games;
    // creating a jbutton
    private JButton playGame;
    // creating a scroll pane
    private JScrollPane scrollPane;

    // constructor to setup the Board
    public App() {

        text_area.setLayout(new BoxLayout(text_area, BoxLayout.Y_AXIS)); 
        // Created a rigid area to properly adjust width
        text_area.add(Box.createRigidArea(new Dimension(120,1)));
        //Creating JPanel which stores buttons
        button_panel = new JPanel();
        //creating box layout 
        BoxLayout box_layout = new BoxLayout(button_panel, BoxLayout.Y_AXIS);
        //setting layout of button Panel as box layout
        button_panel.setLayout(box_layout);
        //creating an inner JPanel
        JPanel innerPanel1 = new JPanel();
        //creating a first JLabel
        JLabel label = new JLabel("Resort Management System");
        //setting its font to Bold
        label.setFont(new Font("Arial", Font.BOLD, 40));
        //adding label to centre of first inner JPanel
        innerPanel1.add(label,BorderLayout.CENTER);

        //creating second inner JPanel
        JPanel innerPanel2 = new JPanel();
        //setting its layout as Box Layout along X axis
        innerPanel2.setLayout(new BoxLayout(innerPanel2,BoxLayout.X_AXIS));
        //creating JButtons
        JButton local = new JButton("Book");
        JButton remote = new JButton("Login");
        JButton previous = new JButton("Services");
        //creating a ButtonHandler
        ButtonHandler buttonHandler = new ButtonHandler();
        //adding action listener containing button handler to Buttons
        local.addActionListener(buttonHandler);
        remote.addActionListener(buttonHandler);
        previous.addActionListener(buttonHandler);

        //setting buttons font
        Font button_font = new Font("Times New Roman",Font.BOLD,25);
        local.setFont(button_font);
        remote.setFont(button_font);
        previous.setFont(button_font);
        //adding buttons to second inner panel
        innerPanel2.add(local);
        innerPanel2.add(Box.createHorizontalStrut(30));
        innerPanel2.add(remote);
        innerPanel2.add(Box.createHorizontalStrut(30));
        innerPanel2.add(previous);

        //adding the inner panels to outer JPanel
        button_panel.add(new JPanel());
        button_panel.add(innerPanel1);
        button_panel.add(new JPanel());
        button_panel.add(innerPanel2);
        button_panel.add(new JPanel());
        button_panel.add(new JPanel());
        button_panel.add(new JPanel());

        //adding the outer JPanel to JFrame 
        frame.add(button_panel);
        frame.setSize(950, 675);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //calling GUICentre to set the JFrame in centre of the Screen
        GUICenter.center(frame);
        frame.setVisible(true);
        frame.setResizable(false);
    }


    

    public static void main(String[] args) {
        new App();
    }

}
