package GUI;

//importing required packages 
import comp.*;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.*;
import java.util.List;
import java.io.*;



public class GUIGame {
    //Creating objects of Board, Black and White
    private Board board = new Board();
    private White white = new White(board);
    private Black black = new Black(board);

    //creating a text area
    private JPanel text_area = new JPanel();

    //creating a JFrame
    private JFrame frame = new JFrame("CHESS");

    //creating a JPanel
    private JPanel button_panel = new JPanel(); 
    private Timer timer;

    private boolean move_selected = false;
    private int move_ind = 0;

    //creating a refrence of JButton 
    private JButton selected_button = null;

    private ArrayList<String> games;
    //creating a jbutton
    private JButton playGame;
    //creating a scroll pane
    private JScrollPane scrollPane;

    //constructor to setup the Board
    public GUIGame() {

        //setting white and black pieces on the board
        white.setupBoard();
        black.setupBoard();

        //setting layout of JPanel called Text Area where moves are written
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
        JLabel label = new JLabel("CHESS");
        //setting its font to Bold
        label.setFont(new Font("Arial", Font.BOLD, 70));
        //adding label to centre of first inner JPanel
        innerPanel1.add(label,BorderLayout.CENTER);

        //creating second inner JPanel
        JPanel innerPanel2 = new JPanel();
        //setting its layout as Box Layout along X axis
        innerPanel2.setLayout(new BoxLayout(innerPanel2,BoxLayout.X_AXIS));
        //creating JButtons
        JButton local = new JButton("Play Local");
        JButton remote = new JButton("Play Online");
        JButton previous = new JButton("Replay Previous Games");
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


    //ActionListener to handle events when Button is clicked
    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //getting text from the Button that is clicked
            String s = e.getActionCommand();
            //if Play Local Button is clicked
            if (s == "Play Local") {
                //removing the previous Jpanel from the frame
                frame.remove(button_panel);
                //setting up the local board
                GUILocal b = new GUILocal(board, white, black, text_area);
                //adding that board to JFrame
                frame.add(b);
                //adding text area to JFrame
                frame.add(text_area, BorderLayout.EAST);
                frame.revalidate();

            } 

            //if Play Online Button is clicked
            else if (s == "Play Online") {
                //removing the previous JPanel from the frame
                frame.remove(button_panel);
                button_panel = new JPanel();
                BoxLayout box_layout = new BoxLayout(button_panel, BoxLayout.Y_AXIS);
                //adding box layout to the JPanel
                button_panel.setLayout(box_layout);
                //creating first inner JPanel
                JPanel innerPanel1 = new JPanel();
                //craeting a JLabel
                JLabel label = new JLabel("Select Connection Mode");
                //setting label's font
                label.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 50));
                //adding the JLabel to first inner panel
                innerPanel1.add(label, BorderLayout.CENTER);

                //creating second inner JPanel
                JPanel innerPanel2 = new JPanel();
                //setting its layout as BoxLayout
                innerPanel2.setLayout(new BoxLayout(innerPanel2, BoxLayout.X_AXIS));
                //creating JButtons
                JButton host = new JButton("Host Game");
                JButton client = new JButton("Join Game");
                //creating object of the class
                ButtonHandler buttonHandler = new ButtonHandler();
                //adding action listener to JButtons
                host.addActionListener(buttonHandler);
                client.addActionListener(buttonHandler);
                //setting button's fonts
                Font button_font = new Font("Times New Roman", Font.PLAIN, 35);
                host.setFont(button_font);
                client.setFont(button_font);
                //adding the buttons to second inner panel
                innerPanel2.add(host);
                innerPanel2.add(Box.createHorizontalStrut(30));
                innerPanel2.add(client);

                //adding the inner JPanels to outer JPanel
                button_panel.add(new JPanel());
                button_panel.add(innerPanel1);
                button_panel.add(new JPanel());
                button_panel.add(innerPanel2);
                button_panel.add(new JPanel());
                button_panel.add(new JPanel());
                button_panel.add(new JPanel());

                //adding the Jpanel to JFrame
                frame.add(button_panel);
                frame.revalidate();

            }

            //if Replay previous games button is clicked
            else if(s == "Replay Previous Games"){
                //removing the previous JPanel from the frame
                frame.remove(button_panel);
                //storing all the moves in an ArrayList
                games = ReadMoves.readMoves();
                //creating an ArrayList storing JButtons
                ArrayList<JButton> button_array = new ArrayList<JButton>();
                int ind = 1;
                //for loop that iterates through the ArrayList stroing all the moves
                for(String game : games){
                    //creating a JButton displaying index no. of game
                    JButton temp = new JButton(ind + " . " + game.split(" : ")[0]);
                    //setting size of JButton
                    temp.setSize(50,80);
                    //adding action listener to the button
                    temp.addActionListener(new SelectGame());
                    //adding the button to the ArrayList
                    button_array.add(temp);
                    ind++;
                }

                //Creating a JPanel
                JPanel games_list = new JPanel();
                //creating ScrollPane to scroll down the JFarme
                scrollPane = new JScrollPane(games_list,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                //creating a vertical BoxLayout
                BoxLayout layout = new BoxLayout(games_list, BoxLayout.Y_AXIS);
                //setting it as JPanels layout
                games_list.setLayout(layout);
                //for loop that iterates through ArrayList containing buttons with index number of the game
                for(JButton button : button_array){
                    //adding those buttons to JPanel
                    games_list.add(button);
                    //creating vertical space between buttons
                    games_list.add(Box.createVerticalStrut(10));
                }
                //
                scrollPane.setViewportView(games_list);
                //adding scrollPane to JFrame
                frame.add(scrollPane,BorderLayout.CENTER);

                //creating Button with "replay game" written on it
                playGame = new JButton("Replay Game");
                //setting font of text on the button
                playGame.setFont(new Font("Calibri",Font.BOLD,30));
                //adding action listener to the button
                playGame.addActionListener(new ButtonHandler());
                //adding the button to JFrame on the East
                frame.add(playGame,BorderLayout.EAST);
                frame.revalidate();

            }

            //if Host Game button is clicked
            if (s == "Host Game") {
                //removing the previous JPanel from the frame
                frame.remove(button_panel);
                //creating a JPanel
                JPanel pan = new JPanel();
                //creating a JLabel with "Connecting..." written on it
                JLabel connecting = new JLabel("Connecting...");
                //setting font of text on JLabel
                connecting.setFont(new Font("Calibri",Font.ITALIC,40));
                //adding JLabel to JPanel
                pan.add(connecting);
                //adding panel to JFrame
                frame.add(pan);
                frame.revalidate();

                //setting timer to 1000 mili sec before action is performed
                timer = new Timer(1000, new ActionListener() {
                    //setting action event
                    public void actionPerformed(ActionEvent e) {
                        //setting up GUIHost board
                        GUIHost host = new GUIHost(board, white, black, text_area);
                        //removing previous Panel from the JFrame
                        frame.remove(pan);
                        frame.revalidate();
                        //adding board to JFrame
                        frame.add(host);
                        //adding TextArea to JFrame
                        frame.add(text_area, BorderLayout.EAST);
                        frame.revalidate();
                    }
                });
                //starting timer
                timer.start();
                //setting timer false to stop it after sending its first action event
                timer.setRepeats(false);
                
            //if Join Game button is clicked
            } else if (s == "Join Game") {
                //removing previous JPanel from JFrame
                frame.remove(button_panel);
                //setting up GUIClient board
                GUIClient client = new GUIClient(board, white, black, text_area);
                //adding the board to JFrame
                frame.add(client);
                //adding TextField to JFrame
                frame.add(text_area, BorderLayout.EAST);
                frame.revalidate();

            }

            //if replay game button is clicked
            if(s == "Replay Game"){
                //if boolean move_selected is true
                if(move_selected){
                    try{
                        //storing game moves in a String
                        String moves = games.get(move_ind).split(" : ")[1];
                        //storing those moves in an array and splitting them on ,
                        String[] array_moves = moves.split(" , ");
                        //creating an ArrayList which stores all the moves
                        ArrayList<String> all_moves = new ArrayList<String>();
                        for(String move : array_moves){
                            all_moves.add(move);
                        }
                        //removing the previous JPanel from JFrame
                        frame.remove(button_panel);
                        //removing previous button from JFrame
                        frame.remove(playGame);
                        //removing scrollpane from JFrame
                        frame.remove(scrollPane);
                        //setting up GUIPrevGame board 
                        GUIPrevGame prev = new GUIPrevGame(board, white, black, text_area, all_moves);
                        //adding that board on JFrame
                        frame.add(prev);
                        //adding textarea on JFrame
                        frame.add(text_area, BorderLayout.EAST);

                        frame.revalidate();

                    }
                    //if try dosent work perform this
                    catch(IndexOutOfBoundsException index_error){
                        index_error.printStackTrace();
                    }
                }
            }

        }
    }

    


    //ActionListener to handle events when a game is selected
    private class SelectGame implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            //if move_selected is true
            if(move_selected){
                //setting background of the selected button
                selected_button.setBackground(new JButton().getBackground());
                move_selected = false;
            }

            //getting the source on which action was performed and casting it to JButton
            selected_button = (JButton) e.getSource();
            //getting text from the Button that is clicked
            String s = e.getActionCommand();
    
            if (s.substring(1, 2).equals(" ")) {
                move_ind = Integer.parseInt(s.substring(0,1))-1;
            } else {
                move_ind = Integer.parseInt(s.substring(0,2))-1;
            }
            
            // Setting move_selected state to true
            move_selected = true;
            // Turning current selected button color to red
            selected_button.setBackground(Color.RED);

        }

    }
}
