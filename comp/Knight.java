package comp;
import java.util.*;
public class Knight extends Piece{

    String NAME = "Knight";


    //constructor initializing random point
    public Knight(){
        pos = new Point();
    }

    //constructor giving Knight specific Point
    public Knight(Point p){
        pos = new Point(p.getX(),p.getY());
    }

    //constructor initializing Knight with coordinates(point), and also board and color
    public Knight(int x, int y, Board b, String c){
        pos = new Point(x,y);
        current_board = b;
        color = c;
    }


    public Knight(Point p, Board b, String c){
        pos = new Point(p.getX(),p.getY());
        current_board = b;
        color = c;
    }


    @Override
    //method to get all the Possible moves for Knight at current Position
    public ArrayList<Point> getPossibleMoves(){

        //initializing array to hold all possibe moves
        ArrayList<Point> possible = new ArrayList<Point>();


        //initializing Points to check if they are Possible Knight moves



        //point for down-right L shape (2 down-1 right)
        Point pos1 = new Point(pos.getX()+2,pos.getY()-1);
        
        //checking to see if Point is within bounds of Board
        if(params_check(pos1)){
            //getting piece at that point being checked
            Piece p = current_board.getPiece(pos1);

            //if Point is empty or contains enemy Piece, add to PossibleMoves array
            if(p == null || p.color!=this.color){
                possible.add(pos1);
            }
        }



        //point down-left L shape (2 down-1 left)
        Point pos2 = new Point(pos.getX()+2,pos.getY()+1);

        //checking to see if point is within board bounds
        if(params_check(pos2)){
            //getting Piece at that Point
            Piece p = current_board.getPiece(pos2);
        
            //if Point empty, or if it contains enemy Piece, add to Array
            if(p == null || p.color!=this.color){
                possible.add(pos2);
            }
        }
        


        //point down-right L shape (1 down-2 right)
        Point pos3 = new Point(pos.getX()+1,pos.getY()-2);

        if(params_check(pos3)){
            Piece p = current_board.getPiece(pos3);

            if(p== null || p.color != this.color){
                possible.add(pos3);
            }
        }



        //point down-left L shape (1 down-2 left)
        Point pos4 = new Point(pos.getX()+1,pos.getY()+2);

        if(params_check(pos4)){
            Piece p = current_board.getPiece(pos4);

            if(p== null || p.color != this.color){
                possible.add(pos4);
            }
        }


        //point up-right L shape (1 up-2 right)
        Point pos5 = new Point(pos.getX()-1,pos.getY()-2);

        if(params_check(pos5)){
            Piece p = current_board.getPiece(pos5);

            if(p== null || p.color != this.color){
                possible.add(pos5);
            }
        }


        //point up-left L shape (1 up-2 left)
        Point pos6 = new Point(pos.getX()-1,pos.getY()+2);

        if(params_check(pos6)){
            Piece p = current_board.getPiece(pos6);

            if(p== null || p.color != this.color){
                possible.add(pos6);
            }
        }


        //point up-right L shape (2 up-1 right)
        Point pos7 = new Point(pos.getX()-2,pos.getY()-1);

        if(params_check(pos7)){
            Piece p = current_board.getPiece(pos7);

            if(p== null || p.color != this.color){
                possible.add(pos7);
            }
        }


        //point up-left L shape (2 up-1 left)
        Point pos8 = new Point(pos.getX()-2,pos.getY()+1);

        if(params_check(pos8)){
            Piece p = current_board.getPiece(pos8);

            if(p== null || p.color != this.color){
                possible.add(pos8);
            }
        }


        //returning array full of all Possible moves of Knight
        return possible;
    }
    

    //method for checking if a Point exists in Knight's possible moves
    // if yes, moving Knight to that Point
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


    //method for returning name of Piece, i.e, Knight
    @Override
    public String toString(){
        return String.format("Knight");

    }
}