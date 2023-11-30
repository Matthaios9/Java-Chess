package comp;
import java.util.*;
public class Rook extends Piece{

    public String NAME = "Rook";
    public boolean first_move=true;
  
    public Rook(){
        pos = new Point();
    }

    //constructor initializing Rook's position
    public Rook(Point p){
        pos = new Point(p.getX(),p.getY());
    }


    public Rook(int x, int y, Board b, String c){
        pos = new Point(x,y);
        current_board = b;
        color = c;
    }

    //constructor initializing a Point, Board and Color
    public Rook(Point p, Board b, String c){
        pos = new Point(p.getX(),p.getY());
        current_board = b;
        color = c;
    }


    @Override
    //method to get all possible moves for Rook at current position
    public ArrayList<Point> getPossibleMoves(){

        //initializing array to hold all possibe moves
        ArrayList<Point> possible = new ArrayList<Point>();

        //initializing boolean move_added true
        boolean move_added = true;
        int ind = 1;

        //continue the loop while move added is true
        while(move_added){
            move_added = false;
            //checking if Rook can move forward the rows
            Point pos1 = new Point(pos.getX()+ind,pos.getY());
            
            // Checking if point is within bound of board
            if(params_check(pos1)){
                //getting piece at the required point
                Piece p = current_board.getPiece(pos1);
                //checking if the piece is null and adding that point to possible moves
                if(p == null ){
                    possible.add(pos1);
                    move_added = true;
                }
                //checking if it is an enemy piece and adding that point to possible moves
                else if(p.color!=this.color){
                    possible.add(pos1);
                }
                
            }
            ind++;
        }

        //continue the loop while move added is true
        move_added = true;
        ind = 1;
        while(move_added){
            move_added = false;
            //checking if Rook can move backward the rows
            Point pos2 = new Point(pos.getX()-ind,pos.getY());
            if(params_check(pos2)){
                Piece p = current_board.getPiece(pos2);
                if(p == null ){
                    possible.add(pos2);
                    move_added = true;
                }
                else if(p.color!=this.color){
                    possible.add(pos2);
                }
           
            }
            ind++;
        }

        move_added = true;
        ind = 1;
        //continue the loop while move added is true
        while(move_added){
            move_added = false;
            //checking if Rook can move forward the Columns
            Point pos3 = new Point(pos.getX(),pos.getY()+ind);
            if(params_check(pos3)){
                Piece p = current_board.getPiece(pos3);
                if(p == null ){
                    possible.add(pos3);
                    move_added = true;
                }
                else if(p.color!=this.color){
                    possible.add(pos3);
                }
            }
            ind++;
        }

        move_added = true;
        ind = 1;
        //continue the loop while move added is true
        while(move_added){
            move_added = false;
            //checking if rook can move backward the columns
            Point pos4 = new Point(pos.getX(),pos.getY()-ind);
            if(params_check(pos4)){
                Piece p = current_board.getPiece(pos4);
                if(p == null ){
                    possible.add(pos4);
                    move_added = true;
                }
                else if(p.color!=this.color){
                    possible.add(pos4);
                }
            }
            ind++;
        }

        //checking if first move is true 
        if(pos.getX()==7 && pos.getY()==7 &&first_move) {
            //checking if white rook can move to the immediate left of white king
            Point pos5=new Point(7,5);
            Piece p1=current_board.getPiece(pos5);
            Piece p2=current_board.getPiece(7,6);
            if(p1==null && p2==null) {
                //adding point to possible moves
                possible.add(pos5);
            }
           
        }

        //checking if first move is true 
        if(pos.getX()==7 && pos.getY()==0 && first_move) {
            //checking if white rook can move to the immediate right of white king
            Point pos6=new Point(7,3);
            Piece p1=current_board.getPiece(pos6);
            Piece p2=current_board.getPiece(7,1);
            Piece p3=current_board.getPiece(7,2);
            if(p1==null && p2==null && p3==null) {
                //adding point to possible moves
                possible.add(pos6);
            }
        }

        //checking if first move is true 
        if(pos.getX()==0 && pos.getY()==7 && first_move) {
            //checking if white rook can move to the immediate right of white king
            Point pos7=new Point(0,5);
            Piece p1=current_board.getPiece(pos7);
            Piece p2=current_board.getPiece(0,6);
            if(p1==null && p2==null) {
                possible.add(pos7);
            }
        }

        //checking if first move is true 
        if(pos.getX()==0 && pos.getY()==0 && first_move) {
            //checking if white rook can move to the immediate left of white king
            Point pos8=new Point(0,3);
            Piece p1=current_board.getPiece(pos8);
            Piece p2=current_board.getPiece(0,1);
            Piece p3=current_board.getPiece(0,2);
            if(p1==null && p2==null && p3==null) {
                //adding point to possible moves
                possible.add(pos8);
            }


        }
    
        return possible;
    }
    

    //method for checking if a particular point exists in Rook's possible moves
    //if yes, moves rook to that Point
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
    //method to check what Points lie in the EMPTY path between Rook, and another given Point passed as Parameter
    //usually, this point will be enemy King's point, as method is used for Checkmate logic 
    public ArrayList<Point> getPath(Point p){

        //initializing an arraylist storing Rooks path
        ArrayList<Point> path = new ArrayList<Point>();
        
        int x = p.getX();
        int y = p.getY();
        int current_x = pos.getX();
        int current_y = pos.getY();

        //checking if rook is present in the same row as the given Point
        if(x==current_x){
            //checking if rook is to the right of given Point
            if(current_y>y){
                for(int i = current_y-1; i>y; i--){
                    path.add(new Point(x,i));
                }
            }

            //checking if queen is to the left of given Point
            else{
                for (int i = current_y + 1; i < y; i++) {
                    path.add(new Point(x, i));
                }
            }
        }

        //checking if rook is present in the same column as the given Point
        else if (y == current_y) {
            //checking if rook is below the given Point
            if (current_x > x) {
                for (int i = current_x - 1; i > x; i--) {
                    path.add(new Point(i, y));
                }
            }
            //checking if rook is below the given Point
            else {
                for (int i = current_x + 1; i < x; i++) {
                    path.add(new Point(i, y));
                }
            }
        }
        return path;
    }

    @Override
    public String toString(){
        return String.format("Rook");

    }
}