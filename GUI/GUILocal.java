package GUI;

// importing required packages
import comp.*;
import comp.Point;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// class to create GUI for local chess
public class GUILocal extends JPanel{

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
    private GUIPawnPromotion g;
    private Point previous_check;
    // Declaring and Intializing class variables
    private ArrayList<String> stored_moves = new ArrayList<String>();
    private ArrayList<PiecePanel> highlighted = new ArrayList<PiecePanel>();;
    private final Color check_red = new Color(181,14,14);

    

    // Constructor to intialize starting state of game
    public GUILocal(Board b, White w, Black bl, JPanel txt){
        // Intialzing class variables
        piece_board = b;
        whitePlayer = w;
        blackPlayer = bl;
        currentTurn = whitePlayer;
        text_area = txt;

        // Creating two different fonts
        Font f1 = new Font("Bold",Font.BOLD,24);
        Font f2 = new Font("Plain",Font.PLAIN,16);
        // Creating and intializing stars of text area
        area = new JTextArea();
        area.setSize(100,100);
        area.setEditable(false);
        area.setFont(f2);
        // Creating a scrollpane for move tracker
        JScrollPane pane = new JScrollPane(area, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adding move tracker text area to scroll pane
        pane.setViewportView(area);
        // Creating and setting font of new label
        JLabel lab = new JLabel("MOVE TRACKER     ");
        lab.setFont(f1);

        // Adding label and text area to panel text_area
        text_area.add(lab);
        text_area.add(pane);

        // Creating 8x8 grid layout
        GridLayout layout = new GridLayout(8,8);
        layout.setHgap(4);
        layout.setVgap(4);

        // Creating JPanel with the grid layout
        board = new JPanel(layout);
        // Creating 2D array to hold 8x8 PiecePanels
        blocks = new PiecePanel[8][8];
        boolean reverse_color = true;

        // Iterating through loops and setting colors of the respective blocks of board
        for(int i =0; i<8; i++){
            for(int j = 0; j<8; j++){
                // Getting index on board
                int ind = i*8+j;
                // Creating PiecePanel with 1x1 grid layout and poisiton (i,j)
                PiecePanel temp = new PiecePanel(new GridLayout(1,1),i,j);
                // Checking if on alternate rows
                if(reverse_color){
                    //Checking if ind is even
                    if(ind%2==0){
                        // Setting background color to white and storing it as original color of current PiecePanel
                        temp.setBackground(Color.WHITE);
                        temp.original_color = Color.WHITE;
                    }
                    // Else if ind is odd
                    else{
                        // Setting background color to dark gray and storing it as original color of current PiecePanel
                        temp.setBackground(Color.DARK_GRAY);
                        temp.original_color = Color.DARK_GRAY;
                    }
                }
                else{
                    // Checking if ind is even
                    if(ind%2==0){
                        // Setting background color to dark gray and storing it as original color of current PiecePanel
                        temp.setBackground(Color.DARK_GRAY);
                        temp.original_color = Color.DARK_GRAY;
                    }
                    // Else if ind is odd
                    else{
                        // Setting background color to black and storing it as original color of current PiecePanel
                        temp.setBackground(Color.WHITE);
                        temp.original_color = Color.WHITE;
                    }
                }
                // Changing state of reverse_color at end of row
                if(ind%8==7){
                    reverse_color=!reverse_color;
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
    private void setBoard(){
        // Action handler for the pieces
        MoveHandler moveHandler = new MoveHandler();

        // Iterating through piece board
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                JPanel temp = blocks[i][j];
                // Getting piece at current position
                Piece tempPiece = piece_board.getPiece(i,j);
                // Checking if piece is not empty
                if(tempPiece!=null){
                    // Getting address of image using piece name and color
                    String image = String.format("img/%s%s.png", tempPiece, tempPiece.color.substring(0,1));
                    // Creating PieceLabel with image icon and current position
                    PieceLabel label = new PieceLabel( new ImageIcon(image),i,j);
                    // Centering the image in PieceLabel
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    // Adding the handler to the label
                    label.addMouseListener(moveHandler);
                    // Adding the label to the panel
                    temp.add(label);
                }
                // Else if piece does not exist
                else{
                    // Creating an empty label with current position
                    PieceLabel label = new PieceLabel(i,j);
                    // Centering the label
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    // Adding handle to the label
                    label.addMouseListener(moveHandler);
                    // Adding the label to the panel
                    temp.add(label);
                }
            }
        }
    }


    // Method to refresh the board after every turn
    private void refreshBoard(){
        // Iterating through the board
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                // Getting ther panel and piece at the current position
                PiecePanel temp = blocks[i][j];
                Piece tempPiece = piece_board.getPiece(i,j);
                // Checking if piece is not null
                if(tempPiece!=null){
                    // Getting address of image using piece name and color
                    String image = String.format("img/%s%s.png", tempPiece, tempPiece.color.substring(0,1));
                    // Getting and casting the PieceLabel from the PiecePanel
                    PieceLabel label =(PieceLabel) temp.getComponent(0);
                    // Setting the new image on the label
                    label.setIcon(new ImageIcon(image));
                }
                // Else if piece is empty
                else{
                    // Getting and casting the PieceLabel from the PiecePanel
                    PieceLabel label = (PieceLabel) temp.getComponent(0);
                    // Setting the image on the label to none
                    label.setIcon(null);
                }
            }
        }
    }



    // Method to change the state of turn after every move
    private void changeTurn(){
        if(currentTurn==whitePlayer){
            currentTurn = blackPlayer;
        }
        else{
            currentTurn = whitePlayer;
        }      
    }

    // Event Handler to handle mouse clicks on board and move pieces
    private class MoveHandler extends MouseAdapter{
        @Override
        // Method for when mouse is pressed on a label
        public void mousePressed(MouseEvent event){
            // Declaring and initializing the state of the current move to false
            boolean moved = false;
            // Going through the highlighted array and setting the color of each PiecePanel to original color
            for(PiecePanel pan : highlighted){
                Color c = pan.original_color;
                pan.setBackground(c);
            }
            // Getting the source of the event and its position
            PieceLabel source = (PieceLabel) event.getSource();
            int x = source.x_pos;
            int y = source.y_pos;
            
            // Storing the piece previously selected
            previously_selected = currently_selected;
            // Getting the panel from board using the position of event occuring label
            currently_selected = blocks[x][y];

            // Checking if highlighted list is not empty
            if(highlighted.size()>0){
                // Iterating through all panels in highlighted
                for( PiecePanel p : highlighted){
                    // Checking if event occuring panel exists in highlighted
                    if( p == currently_selected){
                        // Moving the piece from the position of the previously selected panel to the currently selected one
                        String s  = currentTurn.move(previously_selected.x_pos,previously_selected.y_pos, x,y);

                        // Updating the board blocks threatened state after move
                        whitePlayer.updateThreatened();
                        blackPlayer.updateThreatened();

                        // Checking to see if a pawn reached end
                        Piece temp_piece = piece_board.getPiece(x,y);
                        if((temp_piece instanceof Pawn) && (temp_piece.getX() == 7 || temp_piece.getX()==0)){
                                // Creating and showing pawn promotion dialog
                                g = new GUIPawnPromotion(new PieceSelector(temp_piece));
                                g.setSize(450,100);
                                GUICenter.center(g);
                                g.setVisible(true);

                        }
                        else{
                            // Storing the move in the stored moves list
                            stored_moves.add(String.format("%d %d %d %d", previously_selected.x_pos,
                                    previously_selected.y_pos, x, y));
                        }
                        // Adding the move to move tracker text area
                        area.append(s);

                        // Setting moved state to true
                        moved = true;

                        // Changing turn
                        changeTurn();
                        // Refreshing board and checking for game end
                        refreshBoard();
                        check_for_win();
                        check_for_checkmate();
                        check_for_check();
                    }
                }
            }

            // Clearing highlighted list
            highlighted.clear();

            // Checking if current label selected is not empty and is of the same color as current turn
            if(piece_board.getPiece(x,y)!=null && piece_board.getPiece(x,y).color==currentTurn.getColor()){
                // Getting the current lpiece
                Piece piece = piece_board.getPiece(x,y);
                // Checking if piece not moved
                if(!moved){
                    // Getting all possible moves of selected piece
                    ArrayList<Point> moves = piece.getPossibleMoves();
                    // Iterating through all the points of possible moves
                    for(Point p: moves){
                        // Getting piece and panel and block at current point in loop
                        PiecePanel temp_panel =blocks[p.getX()][p.getY()];
                        Piece temp_piece = piece_board.getPiece(p.getX(),p.getY());
                        Block b = piece_board.getBlock(p);
                        // Checking if current piece in loop is null
                        if(temp_piece==null){
                            // Setting the color of selected panel to cyan
                            temp_panel.setBackground(Color.CYAN);
                        }
                        // Else if current piece is not null
                        else{
                            // Setting the color of selected panel to red as it contain an enemy piece
                            temp_panel.setBackground(Color.RED);
                        }

                        // Adding the panel to highlighted
                        highlighted.add(temp_panel);

                    }
                }
            }  

            // Refreshing the board and checking for check
            refreshBoard(); 
            check_for_check();
            
        }
    }


    // Method to check if a king falls and end the game
    private void check_for_win(){
        // Getting the white king from its stored position
        Piece whiteKing = piece_board.getPiece(whitePlayer.king.getPosition());
        // Checking if any other piece exists at its position
        if(!(whiteKing instanceof King)){
            // Showing message dialog of black winning
            JOptionPane.showMessageDialog(null,"Black wins");
            // Writing the moves to database
            WriteToFile.writeMoves(stored_moves);
            // Exiting application
            System.exit(0);
        }

        // Getting the black king from its stored position
        Piece blackKing = piece_board.getPiece(blackPlayer.king.getPosition());
        // Checking if any other piece exists at its position
        if(!(blackKing instanceof King)){
            // Showing message dialog of white winning
            JOptionPane.showMessageDialog(null,"White wins");
            // Writing the moves to database
            WriteToFile.writeMoves(stored_moves);
            // Exiting application
            System.exit(0);
        }
        
    }


    // Method to check if a king is threatened by another piece
    private void check_for_check(){
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
        if(previous_check!=null){
            PiecePanel previous_panel = blocks[previous_check.getX()][previous_check.getY()];
            previous_panel.setBackground(previous_panel.original_color);
            previous_panel = null;
        }

        // Checking if white king is threatened by black
        if(w_block.blackThreatened){
            // Setting the color of king panel to red and storing its position in previous check
            white_panel.setBackground(check_red);
            previous_check = w_pos;
        }

        // Checking if black king is threatened by white
        if(b_block.whiteThreatened){
            // Setting the color of king panel to red and storing its position in previous check
            black_panel.setBackground(check_red);
            previous_check = b_pos;
        }

    }



    // Method to check if king is in checkmate
    private void check_for_checkmate(){
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
        if(w_block.blackThreatened && wKing.getPossibleMoves().size()==0){
            // Setting saveable state initially to false
            boolean saveable = false;

            // Declaring the attacker on king to null
            Piece agressor = null;
            // Iterating though all the black pieces
            for(Piece p : black_pieces){
                // Iterating though all possible moves of every piece to check if they contain king position
                for(Point pos : p.getPossibleMoves()){
                    // If current piece is attacking king
                    if(pos.equals(w_pos)){
                        // Setting current piece as agressor
                        agressor = p;
                    }
                }
            }

            // Checking if agressor is a bishop,knight or queen
            if( agressor instanceof Queen || agressor instanceof Bishop || agressor instanceof Rook){
                // Gettin path of agressor to king
                ArrayList<Point> path = agressor.getPath(w_pos);
                // Checking if any piece can intercept agressor
                for(Point p1 : whitePlayer.getAllPossibleMoves()){
                    for(Point p2 : path){
                        // if interceptable setting savable state to true
                        if(p2.equals(p1)){
                            saveable = true;
                        }
                    }
                }
            }

            // Checking if any white piece can take down the black agressor
            for(Piece p : white_pieces){
                for(Point pos : p.getPossibleMoves()){
                    // If attacker in possible moves of any white piece
                    if(pos.equals(agressor.getPosition())){
                        // Setting saveable state to true
                        saveable = true;
                    }
                }
            }

            // Checking if saveable state is false
            if(!saveable){
                // Showing message of black winning by checkmate
                JOptionPane.showMessageDialog(null,"Black wins by checkmate");
                // Storing the moves of game in database
                WriteToFile.writeMoves(stored_moves);
                // Exiting the game
                System.exit(0);
            }
        }

        // Checking if black king is threatened by black and it has no possible moves
        else if(b_block.whiteThreatened && bKing.getPossibleMoves().size()==0){
            // Setting saveable state initially to false
            boolean saveable = false;
            
            // Declaring the attacker on king to null
            Piece agressor = null;
            // Iterating though all the black
            for(Piece p : white_pieces){
                // Iterating though all possible moves of every piece to check if they contain king position
                for(Point pos : p.getPossibleMoves()){
                    // If current piece is attacking king
                    if(pos.equals(b_pos)){
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
            for(Piece p : black_pieces){
                for(Point pos : p.getPossibleMoves()){
                    // If attacker in possible moves of any black piece
                    if(pos.equals(agressor.getPosition())){
                        // Setting saveable state to true
                        saveable = true;
                    }
                }
            }

            // Checking if saveable state is false
            if(!saveable){
                // Showing message of white winning by checkmate
                JOptionPane.showMessageDialog(null,"White wins by checkmate");
                // Storing the moves of game in database
                WriteToFile.writeMoves(stored_moves);
                // Exiting the game
                System.exit(0);
            }
        }
    }



    // Handler for pawn promotion
    private class PieceSelector implements ActionListener{
        // Declaring class variables
        private Piece piece;
        private int x,y;
        private String color;

        // Constructor to set piece and get its position and color
        public PieceSelector(Piece temp_piece){
            piece = temp_piece;
            Point p = temp_piece.getPosition();
            x = p.getX();
            y = p.getY();
            color = temp_piece.color;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            // Getting the source text
            String s = e.getActionCommand();
            // Checking if Queen button pressed
            if(s=="Queen"){
                // Creating and setting a new Queen on board
                Queen q = new Queen(x,y,piece_board,color);
                piece_board.setPiece(x,y,q);
            }
            // Checking if Rook button pressed
            else if(s=="Rook"){
                // Creating and setting a new Rook on board
                Rook r = new Rook(x,y,piece_board,color);
                piece_board.setPiece(x,y,r);
            }
            // Checking if Bishop button pressed
            else if(s=="Bishop"){
                // Creating and setting a new Bishop on board
                Bishop b = new Bishop(x,y,piece_board,color);
                piece_board.setPiece(x,y,b);
            }
            // Checking if Knight button pressed
            else if(s=="Knight"){
                // Creating and setting a new Knight on board
                Knight k = new Knight(x,y,piece_board,color);
                piece_board.setPiece(x,y,k);
            }

            Piece temp = piece_board.getPiece(x, y);
            // Storing the moves in stored moves list
            stored_moves.add(
                    String.format("%d %d %d %d %s", previously_selected.x_pos, previously_selected.y_pos, x, y, temp));
            // Disposing the JDialog
            g.dispose();
            // Refreshing board
            refreshBoard();
            
        }

    }

}
