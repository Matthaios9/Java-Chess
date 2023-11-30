package GUI;

// importing required packages
import comp.*;
import comp.Point;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Class to replay games from database
public class GUIPrevGame extends JPanel {

    // Declaring class instance variables
    private Board piece_board;
    private JPanel board;
    private PiecePanel[][] blocks;
    private PiecePanel currently_selected;
    private PiecePanel previously_selected;
    private White whitePlayer;
    private Black blackPlayer;
    private Player currentTurn;
    private JPanel text_area;
    private JTextArea area;
    private JButton next_move;
    private GUIPawnPromotion g;
    private Point previous_check;
    private int current_move = 0;
    private ArrayList<String> all_previous_moves;
    // Declaring and Intializing class variables
    private boolean promotion = false;
    private Color check_red = new Color(181, 14, 14);
    private ArrayList<String> stored_moves = new ArrayList<String>();
    private ArrayList<PiecePanel> highlighted = new ArrayList<PiecePanel>();

    public GUIPrevGame(Board b, White w, Black bl, JPanel txt, ArrayList<String> prev) {
        // Intialzing class variables
        all_previous_moves = prev;
        piece_board = b;
        whitePlayer = w;
        blackPlayer = bl;
        currentTurn = whitePlayer;
        text_area = txt;

        // Creating two different fonts
        Font f1 = new Font("Bold", Font.BOLD, 24);
        Font f2 = new Font("Plain", Font.PLAIN, 16);

        // Creating and intializing states of text area
        area = new JTextArea();
        area.setSize(15, 10);
        area.setEditable(false);
        area.setFont(f2);
        // Creating and setting font of new label
        JLabel lab = new JLabel("MOVE TRACKER         ");
        lab.setFont(f1);

        // Creating and setting font of button
        next_move = new JButton("Next Move");
        next_move.setFont(f1);
        // Adding handler to button
        next_move.addActionListener(new MoveLoader());

        // Creating a boxlayout for text_area panel
        BoxLayout lay = new BoxLayout(text_area,BoxLayout.Y_AXIS);
        text_area.setLayout(lay);
        // Creating a scrollpane for move tracker
        JScrollPane pane = new JScrollPane(area, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adding move tracker text area to scroll pane
        pane.setViewportView(area);

        // Adding label, scrollpane and button to panel text_area
        text_area.add(lab);
        text_area.add(next_move);
        text_area.add(pane);
        
        // Creating 8x8 grid layout
        GridLayout layout = new GridLayout(8, 8);
        layout.setHgap(4);
        layout.setVgap(4);

        // Creating JPanel with the grid layout
        board = new JPanel(layout);
        // Creating 2D array to hold 8x8 PiecePanels
        blocks = new PiecePanel[8][8];
        boolean reverse_color = true;

        // Iterating through loops and setting colors of the respective blocks of board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // Getting index on board
                int ind = i * 8 + j;
                // Creating PiecePanel with 1x1 grid layout and poisiton (i,j)
                PiecePanel temp = new PiecePanel(new GridLayout(1, 1), i, j);
                // Checking if on alternate rows
                if (reverse_color) {
                    // Checking if ind is even
                    if (ind % 2 == 0) {
                        // Setting background color to white and storing it as original color of current
                        // PiecePanel
                        temp.setBackground(Color.WHITE);
                        temp.original_color = Color.WHITE;
                    }
                    // Else if ind is odd
                    else {
                        // Setting background color to dark gray and storing it as original color of
                        // current PiecePanel
                        temp.setBackground(Color.DARK_GRAY);
                        temp.original_color = Color.DARK_GRAY;
                    }
                } else {
                    // Checking if ind is even
                    if (ind % 2 == 0) {
                        // Setting background color to dark gray and storing it as original color of
                        // current PiecePanel
                        temp.setBackground(Color.DARK_GRAY);
                        temp.original_color = Color.DARK_GRAY;
                    }
                    // Else if ind is odd
                    else {
                        // Setting background color to black and storing it as original color of current
                        // PiecePanel
                        temp.setBackground(Color.WHITE);
                        temp.original_color = Color.WHITE;
                    }
                }
                // Changing state of reverse_color at end of row
                if (ind % 8 == 7) {
                    reverse_color = !reverse_color;
                }

                // adding current PiecePanel to board panel and blocks array
                board.add(temp);
                blocks[i][j] = temp;
            }
        }
        // Adding board array to class instance
        add(board);
        // Calling method to setup board
        setBoard();

    }

    // Method to set the board initially
    private void setBoard() {

        // Iterating through piece board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JPanel temp = blocks[i][j];
                // Getting piece at current position
                Piece tempPiece = piece_board.getPiece(i, j);
                // Checking if piece is not empty
                if (tempPiece != null) {
                    // Getting address of image using piece name and color
                    String image = String.format("img/%s%s.png", tempPiece, tempPiece.color.substring(0, 1));
                    // Creating PieceLabel with image icon and current position
                    PieceLabel label = new PieceLabel(new ImageIcon(image), i, j);
                    // Centering the image in PieceLabel
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    // Adding the label to the panel
                    temp.add(label);
                }
                // Else if piece does not exist
                else {
                    // Creating an empty label with current position
                    PieceLabel label = new PieceLabel(i, j);
                    // Centering the label
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    // Adding the label to the panel
                    temp.add(label);
                }
            }
        }
    }

    // Method to refresh the board after every turn
    private void refreshBoard() {
        // Iterating through the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // Getting ther panel and piece at the current position
                PiecePanel temp = blocks[i][j];
                Piece tempPiece = piece_board.getPiece(i, j);
                // Checking if piece is not null
                if (tempPiece != null) {
                    // Getting address of image using piece name and color
                    String image = String.format("img/%s%s.png", tempPiece, tempPiece.color.substring(0, 1));
                    // Getting and casting the PieceLabel from the PiecePanel
                    PieceLabel label = (PieceLabel) temp.getComponent(0);
                    // Setting the new image on the label
                    label.setIcon(new ImageIcon(image));
                }
                // Else if piece is empty
                else {
                    // Getting and casting the PieceLabel from the PiecePanel
                    PieceLabel label = (PieceLabel) temp.getComponent(0);
                    // Setting the image on the label to none
                    label.setIcon(null);
                }
            }
        }
    }

    // Method to change the state of turn after every move
    private void changeTurn() {
        if (currentTurn == whitePlayer) {
            currentTurn = blackPlayer;
        } else {
            currentTurn = whitePlayer;
        }
    }

    // Method to check if a king falls and end the game
    private void check_for_win() {
        // Getting the white king from its stored position
        Piece whiteKing = piece_board.getPiece(whitePlayer.king.getPosition());
        // Checking if any other piece exists at its position
        if (!(whiteKing instanceof King)) {
            // Showing message dialog of black winning
            JOptionPane.showMessageDialog(null, "Black wins");
            // Writing the moves to database
            WriteToFile.writeMoves(stored_moves);
            // Exiting application
            System.exit(0);
        }

        // Getting the black king from its stored position
        Piece blackKing = piece_board.getPiece(blackPlayer.king.getPosition());
        // Checking if any other piece exists at its position
        if (!(blackKing instanceof King)) {
            // Showing message dialog of white winning
            JOptionPane.showMessageDialog(null, "White wins");
            // Writing the moves to database
            WriteToFile.writeMoves(stored_moves);
            // Exiting application
            System.exit(0);
        }

    }

    // Method to check if a king is threatened by another piece
    private void check_for_check() {
        // Getting the stored king instances of players
        King wKing = whitePlayer.king;
        King bKing = blackPlayer.king;

        // Getting the kings position
        Point w_pos = wKing.getPosition();
        Point b_pos = bKing.getPosition();

        // Getting the kings blocks
        Block w_block = piece_board.getBlock(w_pos);
        Block b_block = piece_board.getBlock(b_pos);

        // Getting the kings panels
        PiecePanel white_panel = blocks[wKing.getX()][wKing.getY()];
        PiecePanel black_panel = blocks[bKing.getX()][bKing.getY()];

        // Setting the color of previous check back to original
        if (previous_check != null) {
            PiecePanel previous_panel = blocks[previous_check.getX()][previous_check.getY()];
            previous_panel.setBackground(previous_panel.original_color);
            previous_panel = null;
        }

        // Checking if white king is threatened by black
        if (w_block.blackThreatened) {
            // Setting the color of king panel to red and storing its position in previous
            // check
            white_panel.setBackground(check_red);
            previous_check = w_pos;
        }

        // Checking if black king is threatened by white
        if (b_block.whiteThreatened) {
            // Setting the color of king panel to red and storing its position in previous
            // check
            black_panel.setBackground(check_red);
            previous_check = b_pos;
        }

    }

    // Method to check if king is in checkmate
    private void check_for_checkmate() {
        // Getting the stored king instances of players
        King wKing = (King) whitePlayer.king;
        King bKing = (King) blackPlayer.king;

        // Getting the kings position
        Point w_pos = wKing.getPosition();
        Point b_pos = bKing.getPosition();

        // Getting the kings blocks
        Block w_block = piece_board.getBlock(w_pos);
        Block b_block = piece_board.getBlock(b_pos);

        // Getting all the pieces on the board
        ArrayList<Piece> white_pieces = whitePlayer.getCurrentPieces();
        ArrayList<Piece> black_pieces = blackPlayer.getCurrentPieces();

        // Checking if white king is threatened by black and it has no possible moves
        if (w_block.blackThreatened && wKing.getPossibleMoves().size() == 0) {
            // Setting saveable state initially to false
            boolean saveable = false;

            // Declaring the attacker on king to null
            Piece agressor = null;
            // Iterating though all the black pieces
            for (Piece p : black_pieces) {
                // Iterating though all possible moves of every piece to check if they contain
                // king position
                for (Point pos : p.getPossibleMoves()) {
                    // If current piece is attacking king
                    if (pos.equals(w_pos)) {
                        // Setting current piece as agressor
                        agressor = p;
                    }
                }
            }

            // Checking if agressor is a bishop,knight or queen
            if (agressor instanceof Queen || agressor instanceof Bishop || agressor instanceof Rook) {
                // Gettin path of agressor to king
                ArrayList<Point> path = agressor.getPath(w_pos);
                // Checking if any piece can intercept agressor
                for (Point p1 : whitePlayer.getAllPossibleMoves()) {
                    for (Point p2 : path) {
                        // if interceptable setting savable state to true
                        if (p2.equals(p1)) {
                            saveable = true;
                        }
                    }
                }
            }

            // Checking if any white piece can take down the black agressor
            for (Piece p : white_pieces) {
                for (Point pos : p.getPossibleMoves()) {
                    // If attacker in possible moves of any white piece
                    if (pos.equals(agressor.getPosition())) {
                        // Setting saveable state to true
                        saveable = true;
                    }
                }
            }

            // Checking if saveable state is false
            if (!saveable) {
                // Showing message of black winning by checkmate
                JOptionPane.showMessageDialog(null, "Black wins by checkmate");
                // Storing the moves of game in database
                WriteToFile.writeMoves(stored_moves);
                // Exiting the game
                System.exit(0);
            }
        }

        // Checking if black king is threatened by black and it has no possible moves
        else if (b_block.whiteThreatened && bKing.getPossibleMoves().size() == 0) {
            // Setting saveable state initially to false
            boolean saveable = false;

            // Declaring the attacker on king to null
            Piece agressor = null;
            // Iterating though all the black
            for (Piece p : white_pieces) {
                // Iterating though all possible moves of every piece to check if they contain
                // king position
                for (Point pos : p.getPossibleMoves()) {
                    // If current piece is attacking king
                    if (pos.equals(b_pos)) {
                        // Setting current piece as agressor
                        agressor = p;
                    }
                }
            }

            // Checking if agressor is a bishop,knight or queen
            if (agressor instanceof Queen || agressor instanceof Bishop || agressor instanceof Rook) {
                // Gettin path of agressor to king
                ArrayList<Point> path = agressor.getPath(bKing.getPosition());
                // Checking if any piece can intercept agressor
                for (Point p1 : blackPlayer.getAllPossibleMoves()) {
                    for (Point p2 : path) {
                        // if interceptable setting savable state to true
                        if (p2.equals(p1)) {
                            saveable = true;
                        }
                    }
                }
            }

            // Checking if any black piece can take down the white agressor
            for (Piece p : black_pieces) {
                for (Point pos : p.getPossibleMoves()) {
                    // If attacker in possible moves of any black piece
                    if (pos.equals(agressor.getPosition())) {
                        // Setting saveable state to true
                        saveable = true;
                    }
                }
            }

            // Checking if saveable state is false
            if (!saveable) {
                // Showing message of white winning by checkmate
                JOptionPane.showMessageDialog(null, "White wins by checkmate");
                // Storing the moves of game in database
                WriteToFile.writeMoves(stored_moves);
                // Exiting the game
                System.exit(0);
            }
        }
    }


    // EventHandler for next move button
    private class MoveLoader implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                // Get current move from list of moves
                String move = all_previous_moves.get(current_move);
                // Parse the move string for starting and ending coordinates
                int x = Integer.parseInt(move.substring(0, 1));
                int y = Integer.parseInt(move.substring(2, 3));
                int x_2 = Integer.parseInt(move.substring(4, 5));
                int y_2 = Integer.parseInt(move.substring(6, 7));
                // Move the piece using from starting to ending point
                String s = currentTurn.move(x, y, x_2, y_2);

                // Checking for pawn promotion
                Piece temp_piece = piece_board.getPiece(x_2, y_2);
                if (temp_piece instanceof Pawn && (temp_piece.getX() == 7 || temp_piece.getX() == 0)) {
                    promotion = true;
                }
                else{
                    promotion = false;
                }
                // If pawn is to be promoted
                if(promotion){
                    // Parsing the move string to get the piece to promote to
                    String piece_to_promote = move.substring(8, 9);
                    // Getting current player color
                    String color = currentTurn.getColor();

                    // Promoting pawn as per stored move
                    if (piece_to_promote.equals("Q")) {
                        Queen q = new Queen(x_2, y_2, piece_board, color);
                        piece_board.setPiece(x_2, y_2, q);
                    } else if (piece_to_promote.equals("R")) {
                        Rook r = new Rook(x_2, y_2, piece_board, color);
                        piece_board.setPiece(x_2, y_2, r);
                    } else if (piece_to_promote.equals("B")) {
                        Bishop b = new Bishop(x_2, y_2, piece_board, color);
                        piece_board.setPiece(x_2, y_2, b);
                    } else if (piece_to_promote.equals("K")) {
                        Knight k = new Knight(x_2, y_2, piece_board, color);
                        piece_board.setPiece(x_2, y_2, k);
                    }
                }
                // Adding move to move tracker
                area.append(s);
            }
            // Exception Handling
            catch(Exception error){
                error.printStackTrace();
                JOptionPane.showMessageDialog(null, "Game END");
                System.exit(0);       
            }
            
            // Changing turn, refreshing board and checking for check or win
            changeTurn();
            refreshBoard();
            check_for_win();
            check_for_check();
            check_for_checkmate();

            // Incrememting move index
            current_move+=1;
        }
    }

}
