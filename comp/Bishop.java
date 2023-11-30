package comp;
import java.util.*;
public class Bishop extends Piece{

    String NAME = "Bishop";


    public Bishop(){
        pos = new Point();
    }

    //constructor initializing Bishop's position
    public Bishop(Point p){
        pos = new Point(p.getX(),p.getY());
    }


    public Bishop(int x, int y, Board b, String c){
        pos = new Point(x,y);
        current_board = b;
        color = c;
    }

    //constructor initializing Bishop with a point, along with a Board, and assigning color
    public Bishop(Point p, Board b, String c){
        pos = new Point(p.getX(),p.getY());
        current_board = b;
        color = c;
    }



    @Override
     //method to get all possible moves for Bishop at current position
    public ArrayList<Point> getPossibleMoves(){

        //initializing array to hold all possibe moves
        ArrayList<Point> possible = new ArrayList<Point>();

        //initializing boolean move_added true
        boolean move_added = true;
        int ind = 1;

        //continue the loop while move added is true
        while(move_added){
            move_added = false;

            //initializing points diagonally downwards(left) to check if Bishop can move down diag-leftwards
            Point pos1 = new Point(pos.getX()+ind,pos.getY()+ind);

             // Checking if point is within bounds of board
            if(params_check(pos1)){
                //getting piece at required point
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
            //icreasing index by 1 each time to check ALL possible points in that direction
            ind++;
        }

        //making move_added true again to check points in another direction
        move_added = true;
        ind = 1;

         //continue the loop while move added is true
        while(move_added){
            move_added = false;

            //initializing points diagonally down (right) to check if Bishop can move down-diag-right
            Point pos2 = new Point(pos.getX()+ind,pos.getY()-ind);

            //checking if point is within bounds of board
            if(params_check(pos2)){
                //getting Piece at required Point
                Piece p = current_board.getPiece(pos2);
                //checking if the piece is null and adding that point to possible moves
                if(p == null ){
                    possible.add(pos2);
                    move_added = true;
                }
                //checking if it is an enemy piece and adding that point to possible moves
                else if(p.color!=this.color){
                    possible.add(pos2);
                }
            }
             //icreasing index by 1 each time to check ALL possible points in that direction
            ind++;
        }

         //making move_added true again to check points in another direction
        move_added = true;
        ind = 1;


         //continue the loop while move added is true
        while(move_added){
            move_added = false;

             //initializing points diagonally up (right) to check if Bishop can move up-diag-right
            Point pos3 = new Point(pos.getX()-ind,pos.getY()-ind);
            //checking if point is within bounds of board
            if(params_check(pos3)){
                //getting Piece at required Point
                Piece p = current_board.getPiece(pos3);
                //checking if the piece is null and adding that point to possible moves
                if(p == null ){
                    possible.add(pos3);
                    move_added = true;
                }
                 //checking if it is an enemy piece and adding that point to possible moves
                else if(p.color!=this.color){
                    possible.add(pos3);
                }
            }
             //icreasing index by 1 each time to check ALL possible points in that direction
            ind++;
        }

        // same thing to check if B can move diag-up-left, if yes, add to array
        move_added = true;
        ind = 1;
        while(move_added){
            move_added = false;
            //initializing Points and checking to c if B can move diag-up-left
            Point pos4 = new Point(pos.getX()-ind,pos.getY()+ind);
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

        return possible;
    }
    

    //method for checking if a particular point exists in Bishops's possible moves
    //if yes, move is made
    public boolean move(Point p){
        ArrayList<Point> moves = getPossibleMoves();

        for(Point point : moves){
            if(point.equals(p)){
                pos = p;
                return true;
            }
        }
            return false;
    }



    //method to check what Points lie in the EMPTY path between Bishop, and another given Point passed as Parameter
    //usually, this point will be enemy King's point, as method is for Checkmate logic 
    public ArrayList<Point> getPath(Point p) {

        //Arraylist storing the Bishop's Path
        ArrayList<Point> path = new ArrayList<Point>();

        int x = p.getX();
        int y = p.getY();
        int current_x = pos.getX();
        int current_y = pos.getY();
        //absolute diff B/w rows (and =columns) of Bish and Point to get req no. of iterations
        int ind = Math.abs(current_x - x);


        //if Bishop is upwards-right of Point, store all diagonal points b/w them
        if(x>current_x && y > current_y){
            for(int i = 1; i<ind;i++){
                path.add(new Point(current_x+i,current_y+i));
            }
        }

        //if Bishop is downwards-right of Point, store all diagonal points b/w them
        else if (x < current_x && y > current_y) {
            for (int i = 1; i < ind ; i++) {
                path.add(new Point(current_x - i, current_y + i));
            }
        }

        //if Bishop is downwards-left of Point, store all diagonal points b/w them
        else if(x < current_x && y < current_y){
            for(int i = 1; i< ind;i++){
                path.add(new Point(current_x-i,current_y-i));
            }
        }

        //if Bishop is upwards-left of Point, store all diagonal points b/w them
        else if (x > current_x && y < current_y) {
            for (int i = 1; i < ind; i++) {
                path.add(new Point(current_x + i, current_y - i));
            }
        }

        return path;
    }
    

    @Override
    //method returning name of Piece
    public String toString(){
        return String.format("Bishop");

    }
}