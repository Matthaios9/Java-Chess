package comp;

import java.util.*;


public class White implements Player{

    public Board current_board;
    public King king;

    public String color = "WHITE";

    //providing constructor a Board
    public White(Board b){
        current_board = b;
    }

    //method for setting up the Board with white Pieces at initial pos
    public void setupBoard(){
        //initializing pieces and setting them on the board at specific positions
        king = new King(7,4,current_board,color);
        current_board.setPiece(7,0,new Rook(7,0,current_board,color));
        current_board.setPiece(7,1,new Knight(7,1,current_board,color));
        current_board.setPiece(7,2,new Bishop(7,2,current_board,color));
        current_board.setPiece(7,3,new Queen(7,3,current_board,color));
        current_board.setPiece(7,4,king);
        current_board.setPiece(7,5,new Bishop(7,5,current_board,color));
        current_board.setPiece(7,6,new Knight(7,6,current_board,color));
        current_board.setPiece(7,7,new Rook(7,7,current_board,color));
        for(int i = 0; i<8; i++){
            current_board.setPiece(6,i,new Pawn(6,i,current_board,color));
        }
    }

    //method for moving the pieces on the Board by providing initial and end points
    public String move(Point p1, Point p2){

        //getting piece on the initial point
        Piece piece_1 = current_board.getPiece(p1);
        //checking if the end point provided is in a Piece's possible moves
        boolean b = piece_1.move(p2);
     
        //checking if initial point provided is a king
        if(p1.equals(new Point(7,4))) {
            Piece piece_p1=current_board.getPiece(p1);

            //checking if end point provided is two blocks right to the King
            if(p2.equals(new Point(7,6))) {
                Point p3=new Point(7,5);
                Point p4=new Point(7,7);
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
            else if(p2.equals(new Point(7,2))) {
                Piece piece_p2=current_board.getPiece(p2);
                Point p4=new Point(7,0);
                Point p3=new Point(7,3);
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
            return String.format("White\n"+piece_1+ ": "+p1.toNotation()+" to "+p2.toNotation()+"\n");
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


     //Method returning an ArrayList storing all possible moves of all current White pieces
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


    //Method for checking if any Black enemy piece is threatened by White
    public void updateThreatened(){

        //creating an ArrayList having all possible moves of White pieces
        ArrayList<Point> threatenedPoints = getAllPossibleMoves();

        //double for loop to go through all the Blocks on the Board
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                //getting a Block from the Board
                Block b = current_board.getBlock(i,j);
                boolean found = false;


                //iterating though ArrayList having all possible moves of White pieces
                for(Point p : threatenedPoints){

                    //if coordinates of possible move of any White piece equals to coordinates of the block
                    if(p.getX()==i&&p.getY()==j){
                            b.whiteThreatened = true;
                            found = true;
                        }
                }
                //if founcd is false then Black Block is not threatened by a White piece
                if(!found){
                    b.whiteThreatened= false;
                }
            }
        }
    }

     //method returning an ArrayList containing all current White pieces on the board
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