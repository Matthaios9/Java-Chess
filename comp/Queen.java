package comp;
import java.util.*;
public class Queen extends Piece{

    String NAME = "Queen";
    
    
    public Queen(){
        pos = new Point();
    }


    //Constructor initializing Queen's position
    public Queen(Point p){
        pos = new Point(p.getX(),p.getY());
    }


    //constructor initializing a Point, Board and Color
    public Queen(int x, int y, Board b, String c){
        pos = new Point(x,y);
        current_board = b;
        color = c;
    }


    public Queen(Point p, Board b, String c){
        pos = new Point(p.getX(),p.getY());
        current_board = b;
        color = c;
    }


    @Override
    //method to get all possible moves for King at current position
    public ArrayList<Point> getPossibleMoves(){

        //initializing array to hold all possibe moves
        ArrayList<Point> possible = new ArrayList<Point>();
        boolean move_added = true;
        //initializing index
        int ind = 1;
        //continue the loop while move added is true
        while(move_added){
            //setting move_added false
            move_added = false;
            //checking if Queen can move forawrd the rows 
            Point pos1 = new Point(pos.getX()+ind,pos.getY());
            //checking if point is withing bound of board
            if(params_check(pos1)){
                //getting piece at that point
                Piece p = current_board.getPiece(pos1);
                //checking if piece is null and adding that point to possible moves
                if(p == null ){
                    possible.add(pos1);
                    //setting move_added true
                    move_added = true;
                }
                //checking if enemy piece is at that point and adding that point to possible moves
                else if(p.color!=this.color){
                    possible.add(pos1);
                }
            }
            ind++;
        }

        move_added = true;
        ind = 1;
        //continue the loop while move added is true
        while(move_added){
            move_added = false;
            //checking if Queen can move backward the rows 
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
            //checking if Queen can move forward the columns 
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
            //checking if Queen can move backward the columns 
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

        move_added = true;
        ind = 1;
        //continue the loop while move added is true
        while(move_added){
            move_added = false;
            //checking if queen can move forward diagonally to the right
            Point pos5 = new Point(pos.getX()+ind,pos.getY()+ind);
            if(params_check(pos5)){
                Piece p = current_board.getPiece(pos5);
                if(p == null ){
                    possible.add(pos5);
                    move_added = true;
                }
                else if(p.color!=this.color){
                    possible.add(pos5);
                }
            }
            ind++;
        }

        move_added = true;
        ind = 1;
        //continue the loop while move added is true
        while(move_added){
            move_added = false;
            //checking if Queen can move forward diagonally to the left
            Point pos6 = new Point(pos.getX()+ind,pos.getY()-ind);
            if(params_check(pos6)){
                Piece p = current_board.getPiece(pos6);
                if(p == null ){
                    possible.add(pos6);
                    move_added = true;
                }
                else if(p.color!=this.color){
                    possible.add(pos6);
                }
            }
            ind++;
        }

        move_added = true;
        ind = 1;
        //continue the loop while move added is true
        while(move_added){
            move_added = false;
            //checking if Queen can move backward diagonally to the left
            Point pos7 = new Point(pos.getX()-ind,pos.getY()-ind);
            if(params_check(pos7)){
                Piece p = current_board.getPiece(pos7);
                if(p == null ){
                    possible.add(pos7);
                    move_added = true;
                }
                else if(p.color!=this.color){
                    possible.add(pos7);
                }
            }
            ind++;
        }

        move_added = true;
        ind = 1;
        //continue the loop while move added is true
        while(move_added){
            move_added = false;
            //checking if Queen can move backward diagonally to the right
            Point pos8 = new Point(pos.getX()-ind,pos.getY()+ind);
            if(params_check(pos8)){
                Piece p = current_board.getPiece(pos8);
                if(p == null ){
                    possible.add(pos8);
                    move_added = true;
                }
                else if(p.color!=this.color){
                    possible.add(pos8);
                }
            }
            ind++;
        }


        return possible;
    }
    

    //method for checking if a particular point exists in Queen's possible moves and moving Queen to that Point
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


    //Method returning ArrayList having Path of Queen till a specific Point
    public ArrayList<Point> getPath(Point p) {
        //initializing an arraylist storing Queens path 
        ArrayList<Point> path = new ArrayList<Point>();
        int x = p.getX();
        int y = p.getY();
        int current_x = pos.getX();
        int current_y = pos.getY();

        //checking if Queen is present diagonally from the Point provided
        if(Math.abs(current_x-x)==Math.abs(current_y-y)){
            int ind = Math.abs(current_x - x);

            //checking if Queen is to the left and upward from given Point
            if (x > current_x && y > current_y) {
                //adding Queen's path till given Point
                for (int i = 1; i < ind; i++) {
                    path.add(new Point(current_x + i, current_y + i));
                }
            } 

            //checking if queen is to the left and downward from given point
            else if (x < current_x && y > current_y) {
                for (int i = 1; i < ind; i++) {
                    path.add(new Point(current_x - i, current_y + i));
                }
            }
            
            //checking if queen is to right and downward from given point
            else if (x < current_x && y < current_y) {
                for (int i = 1; i < ind; i++) {
                    path.add(new Point(current_x - i, current_y - i));
                }
            } 
            
            //checking if queen is to the right and downward from given point
            else if (x > current_x && y < current_y) {
                for (int i = 1; i < ind; i++) {
                    path.add(new Point(current_x + i, current_y - i));
                }
            }
        }
        
        //checking if queen is present in the same row or column as the given Point
        else if(current_x == x || current_y == y){
            //checking if queen is to the right of given Point
            if (current_y > y) {
                for (int i = current_y - 1; i > y; i--) {
                    path.add(new Point(x, i));
                }
            } 

            //checking if Queen is to the left of the given Point
            else if(current_y < y){
                for (int i = current_y + 1; i < y; i++) {
                    path.add(new Point(x, i));
                }
            }
             
            //checking if Queen is below the given Point
            else if (current_x > x) {
                for (int i = current_x - 1; i > x; i--) {
                    path.add(new Point(i, y));
                }
            } 

            //checking if Queen is above the given Point
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
        return String.format("Queen");

    }
}