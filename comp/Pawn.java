package comp;
import java.util.*;

public class Pawn extends Piece{

    private boolean first_move = true;


    //constructor initializing undefined point
    public Pawn(){
        pos = new Point();
    }


    //constructor initializing Pawn at a Specific Point
    public Pawn(Point p){
        pos = new Point(p.getX(),p.getY());
    }


    //constructor initializing Pawn at a Specific Point, also init Board and giving color
    public Pawn(int x, int y, Board b, String c){
        pos = new Point(x,y);
        current_board = b;
        color = c;
    }


    //constructor initializing Pawn at a Specific Point, assigning Board and also Color
    public Pawn(Point p, Board b, String c){
        pos = new Point(p.getX(),p.getY());
        current_board =b;
        color = c;
    }


    
    @Override
    //method to get All Possible Moves of Pawn at current Position
    public ArrayList<Point> getPossibleMoves(){

        //initializing Arraylist to store all Possible move Points
        ArrayList<Point> possible = new ArrayList<Point>();
        int sign;

        //assigning diff sign to the two colors to contol direction for each
        //positive sign for black, negative for white
        if(color == "WHITE"){
            sign = -1;
        }
        else{
            sign = 1;
        }

        //to get one point forward
        Point pos1 = new Point(pos.getX()+(1*sign),pos.getY());


        //checking wheter point lies within Board limits
        if(params_check(pos1)){
            //getting Piece on that Point
            Piece p1 = current_board.getPiece(pos1);

            //if no piece is present, add Point to Possible Moves array 
            if(p1 == null){
                possible.add(pos1);
            }
        }


        //if the first move is pawn, getting two points forward
        if(first_move){

            //getting point two points forward 
            Point pos2 = new Point(pos.getX()+(2*sign),pos.getY());

            //checking wheter point lies within Board Limits
            if(params_check(pos2)){
                //getting both points infront of pawn
                Piece p2 = current_board.getPiece(pos2);
                Piece p1 = current_board.getPiece(pos1);
            
                //if both are empty, add them to Possible moves array
                if((p1 == null) && (p2 == null)){
                    possible.add(pos2);
                }
            }
        }


        //getting neigboring diagonal point forward
        Point pos3 = new Point(pos.getX()+(1*sign),pos.getY()-1);

        //checking if point lies within Board limits
        if(params_check(pos3)){
            //getting Piece on that point
            Piece p3 = current_board.getPiece(pos3);

            //if Point is empty, or has enemy Piece, add Point to array
            if(p3!= null && p3.color != this.color){
                possible.add(pos3);
            }
        }


        //getting neighboring diagonal point forward
        Point pos4 = new Point(pos.getX()+(1*sign),pos.getY()+1);


        //checking if point lies within Board limits
        if(params_check(pos4)){
            //getting Piece on that point
            Piece p4 = current_board.getPiece(pos4);

             //if Point is empty, or has enemy Piece, add Point to array
            if(p4!= null && p4.color != this.color){
                possible.add(pos4);
            }
        }


        //returning array with All Possible Moves of Pawn at current Position
        return possible;
    }
        


    //function to check if a point exists in Pawn's Possible moves
    //if yes, moves Pawn to that point
    public boolean move(Point p){

        //initializing array of all Possible moves
        ArrayList<Point> moves = getPossibleMoves();

        //iterating through array to see if that Point exists in Possible Moves
        for(Point point : moves){

            //if exists, then move to that Point is Possible, and move is made
            if(point.equals(p)){
                pos = p;
                first_move = false;
                return true;
            }
        }

        //if doesnt exist, then move is not possible, and method returns false
        return false;
    }

    @Override
    public String toString(){
        return String.format("Pawn");

    }
    
}