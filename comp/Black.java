package comp;

import java.util.*;


public class Black implements Player{
    public Board current_board;
    public King king;
    public String color = "BLACK";

    //providing constructor a Board
    public Black(Board b){
        current_board = b;
    }

    //method for setting up the Board with Black pieces at initial pos
    public void setupBoard(){
        king = new King(0,4,current_board,color);
        //initializing pieces and setting them on the board at specific positions
        current_board.setPiece(0,0,new Rook(0,0,current_board,color));
        current_board.setPiece(0,1,new Knight(0,1,current_board,color));
        current_board.setPiece(0,2,new Bishop(0,2,current_board,color));
        current_board.setPiece(0,3,new Queen(0,3,current_board,color));
        current_board.setPiece(0,4,king);
        current_board.setPiece(0,5,new Bishop(0,5,current_board,color));
        current_board.setPiece(0,6,new Knight(0,6,current_board,color));
        current_board.setPiece(0,7,new Rook(0,7,current_board,color));
        for(int i = 0; i<8; i++){
            current_board.setPiece(1,i,new Pawn(1,i,current_board,color));
        }
    }

    //method for moving the pieces on the Board by providing initial and end points
    public String move(Point p1, Point p2){

        //getting piece on the initial point
        Piece piece_1 = current_board.getPiece(p1);
        //checking if the end point provided is in a Piece's possible moves 
        boolean b = piece_1.move(p2);
        
        //checking if initial point provided is a king 
        if(p1.equals(new Point(0,4))) {
            Piece piece_p1=current_board.getPiece(p1);

            //checking if end point provided is two blocks right to the King
            if(p2.equals(new Point(0,6))) {
                Point p3=new Point(0,5);
                Point p4=new Point(0,7);
                Piece piece_rook=current_board.getPiece(p4);
                boolean b1=piece_rook.move(p3);

                //checking if the moves are possible and performing castling 
                if(b && b1) {
                    Piece piece_p3=current_board.getPiece(p3);
                    current_board.setPiece(p1,null);
                    current_board.setPiece(p2,piece_p1);
                    current_board.setPiece(p4,null);
                    current_board.setPiece(p3,piece_rook);
                }

                return String.format("White\n"+piece_p1+ ": "+p1.toNotation()+" to "+p2.toNotation()+"\n"+
                piece_rook+" : "+p4.toNotation()+" to "+p3.toNotation()+"\n");
            }

            //checking if end point provided is two blocks left to the King
            else if(p2.equals(new Point(0,2))) {
                Piece piece_p2=current_board.getPiece(p2);
                Point p4=new Point(0,0);
                Point p3=new Point(0,3);
                Piece piece_rook=current_board.getPiece(p4);
                boolean b1=piece_rook.move(p3);

                //checking if the moves are possible and performing castling 
                if(b && b1) {
                    current_board.setPiece(p1,null);
                    current_board.setPiece(p2,piece_p1);
                    current_board.setPiece(p4,null);
                    current_board.setPiece(p3,piece_rook);
                }

                return String.format("White\n"+piece_p1+ ": "+p1.toNotation()+" to "+p2.toNotation()+"\n"+
                piece_rook+" : "+p4.toNotation()+" to "+p3.toNotation()+"\n");
            }
        }
        
        //if the move is possible then moving piece from initial point to end point
        if(b){
            current_board.setPiece(p2,piece_1);
            current_board.setPiece(p1,null);

            return String.format("Black\n"+piece_1+ ": "+p1.toNotation()+" to "+p2.toNotation()+"\n");
        }

        return null;
    }
        
    
    //moving the Piece on the Board by providing x and y coordinates / function overloading
    public String move(int x1, int y1, int x2, int y2){
        return move(new Point(x1,y1),new Point(x2,y2));
    }

    
    //method for returning color
    public String getColor(){
        return color;
    }


    //Method returning an ArrayList storing all possible moves of all current Black pieces
    public ArrayList<Point> getAllPossibleMoves(){

        ArrayList<Point> allPoints = new ArrayList<Point>();

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                Piece temp_piece = current_board.getPiece(i,j);
                if(temp_piece == null){
                    continue;
                }
                else if(temp_piece.getColor() == color){
                    for(Point p : temp_piece.getPossibleMoves()){
                        allPoints.add(p);
                    }
                }
            }
        }
        return allPoints;
    }
   

    //Method for checking if any White enemy piece is threatened by Black
    public void updateThreatened(){

        //creating an ArrayList having all possible moves of Black pieces
        ArrayList<Point> threatenedPoints = getAllPossibleMoves();

        //double for loop to go through all the Blocks on the Board
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                //getting a Block from the Board
                Block b = current_board.getBlock(i,j);
                boolean found = false;

                
                //iterating though ArrayList having all possible moves of Black pieces
                for(Point p : threatenedPoints){

                    //if coordinates of possible move of any black piece equals to coordinates of the block
                    if(p.getX()==i&&p.getY()==j){
                            b.blackThreatened = true;
                            found = true;
                        }
                    }
                //if founcd is false then block not threatened by a Black piece
                if(!found){
                    b.blackThreatened= false;
                }
            }
        }
    }

   
    //method returning an ArrayList containing all current Black pieces on the board
    public ArrayList<Piece> getCurrentPieces(){
        ArrayList<Piece> all_pieces = new ArrayList<Piece>();
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                Piece temp_piece = current_board.getPiece(i,j);
                if (temp_piece!=null && temp_piece.color==this.color){
                    all_pieces.add(temp_piece);
                }
            }
        }
        return all_pieces;

    }
    


}