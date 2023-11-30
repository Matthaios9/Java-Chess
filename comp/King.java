package comp;
import java.util.*;
public class King extends Piece{

    String NAME = "King";
    public boolean first_move= true;
   

    public King(){
        pos = new Point();
    }

    //constructor initializing King's position
    public King(Point p){
        pos = new Point(p.getX(),p.getY());
    }


    //constructor initializing a Point, Board and Color
    public King(int x, int y, Board b, String c){
        pos = new Point(x,y);
        current_board = b;
        color = c;
    }


    public King(Point p, Board b, String c){
        pos = new Point(p.getX(),p.getY());
        current_board = b;
        color = c;
    }


    @Override
    //method to get all possible moves for King at current position
    public ArrayList<Point> getPossibleMoves(){

        //initializing array to hold all possibe moves
        ArrayList<Point> possible = new ArrayList<Point>();
  
         //checking if the king can move forward one row
        Point pos1 = new Point(pos.getX()+1,pos.getY());

        //checking if point is withing bound of board
        if(params_check(pos1)){
            // Getting piece at the required point
            Piece p = current_board.getPiece(pos1);
            // Checking if piece is enemy or the square is empty
            if(p == null || p.color!=this.color){
                // Adding the point to possible moves
                possible.add(pos1);
                
            }
        }

        //checking if King can move forward one row and one column
        Point pos2 = new Point(pos.getX()+1,pos.getY()+1);
        if(params_check(pos2)){
            Piece p = current_board.getPiece(pos2);
        
            if(p == null || p.color!=this.color){
                possible.add(pos2);
                
            }
        }
        
        //checking if king can move forward one row and backward one column
        Point pos3 = new Point(pos.getX()+1,pos.getY()-1);
        if(params_check(pos3)){
            Piece p = current_board.getPiece(pos3);
            if(p== null || p.color != this.color){
                possible.add(pos3);
            }
        }

        //checking if king can move backward one column 
        Point pos4 = new Point(pos.getX(),pos.getY()-1);
        if(params_check(pos4)){
            Piece p = current_board.getPiece(pos4);
            if(p== null || p.color != this.color){
                possible.add(pos4);
                
            }
        }

        //checking if king can move forward one column 
        Point pos5 = new Point(pos.getX(),pos.getY()+1);
        if(params_check(pos5)){
            Piece p = current_board.getPiece(pos5);
            if(p== null || p.color != this.color){
                possible.add(pos5);
                
            }
        }

        //checking if king can move backward one row and one column 
        Point pos6 = new Point(pos.getX()-1,pos.getY()-1);
        if(params_check(pos6)){
            Piece p = current_board.getPiece(pos6);
            if(p== null || p.color != this.color){
                possible.add(pos6);
               
            }
        }

        //checking if king can move backward one row
        Point pos7 = new Point(pos.getX()-1,pos.getY());
        if(params_check(pos7)){
            Piece p = current_board.getPiece(pos7);
            if(p== null || p.color != this.color){
                possible.add(pos7);
 
            }
        }

        //checking if king can move backward one row and forward one column
        Point pos8 = new Point(pos.getX()-1,pos.getY()+1);
        if(params_check(pos8)){
            Piece p = current_board.getPiece(pos8);
            if(p== null || p.color != this.color){
                possible.add(pos8);
            }
        }
        
        //checking if white king's first move is true and it can move two blocks towards rook
        if(pos.getX()==7 && pos.getY()==4 && first_move) {
            Point pos9=new Point(7,6);
            Point pos10= new Point(7,2);
            Piece p1=current_board.getPiece(pos9);
            Piece p2=current_board.getPiece(7,5);
            Piece p3=current_board.getPiece(pos10);
            Piece p4=current_board.getPiece(7,3);
            Piece p5=current_board.getPiece(7,1);
            //checking if two blocks right to king are empty
            if(p1==null && p2==null) {
                //adding point to possible moves
                possible.add(pos9);
            }
            //checking if two blocks left to the king are empty
            if(p3==null && p4==null && p5==null) {
                //adding point to possible moves
                possible.add(pos10);
            }

        }

        //checking if black king's first move is true and it can move two blocks toward rook
        if(pos.getX()==0 && pos.getY()==4 && first_move) {
            Point pos10=new Point(0,6);
            Point pos11=new Point(0,2);
            Piece p1=current_board.getPiece(pos10);
            Piece p2=current_board.getPiece(pos11);
            Piece p3=current_board.getPiece(0,1);
            Piece p4=current_board.getPiece(0,3);
            Piece p5=current_board.getPiece(0,5);
            //checking if two blocks right to king are empty
            if(p5==null && p1==null) {
                //adding point to possible moves
                possible.add(pos10);
            }
            //checking if two blocks left to the king are empty
            if(p4==null && p2==null && p3==null) {
                //adding point to possible moves
                possible.add(pos11);
            }

        }

        ArrayList<Point> final_possible = new ArrayList<Point>();
        for(Point p : possible){
            Block b = current_board.getBlock(p);
            if(color == "WHITE"){
                if(!b.blackThreatened){
                    final_possible.add(p);
                }
            }
            else if(color == "BLACK"){
                if(!b.whiteThreatened){
                    final_possible.add(p);
                }
            }
        }
           return final_possible;
    }
    
    
    //method for checking if a particular point exists in King's possible moves
    //if yes, moving King to that Point
    public boolean move(Point p){
        ArrayList<Point> moves = getPossibleMoves();
        first_move = false;

        for(Point point : moves){
            if(point.equals(p)){
                pos = p;
                return true;
            }
        }
            return false;
    }

    @Override
    public String toString(){
        return String.format("King");

    }
}