package GUI;
//importing packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import comp.*;

//class extending JDialog
public class GUIPawnPromotion extends JDialog{

    //creating instance of ActionListener
    ActionListener handler;

    //constructor that takes Action Listener
    public GUIPawnPromotion(ActionListener p){

        handler = p;

        //creating a grid with one row and four columns
        GridLayout layout = new GridLayout(1,4);

        //setting the layout in JDialog
        setLayout(layout);

        //Creating four buttons
        JButton queen = new JButton("Queen");
        JButton rook = new JButton("Rook");
        JButton bishop = new JButton("Bishop");
        JButton knight = new JButton("Knight");

        //adding Action listener to the buttons
        queen.addActionListener(handler);
        rook.addActionListener(handler);
        knight.addActionListener(handler);
        bishop.addActionListener(handler);
        
        //adding the buttons to the JDialog
        add(queen);
        add(rook);
        add(bishop);
        add(knight);
    }


}