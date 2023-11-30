package comp;
import java.util.*;
public class Block{

    private Piece current_piece;
    private Point position;
    public boolean blackThreatened=false;
    public boolean whiteThreatened=false;

    //empty constructor
    public Block(){
    }

    //constructor initializing Block by placing Piece on a Point 
    public Block(Piece p, Point pos){
        current_piece = p;
        position = pos; 
    }


    //method to get Point of Block/Positiob
    public Point getPosition(){
        return position;
    }

    //method to get Piece placed on Block
    public Piece getPiece(){
        return current_piece;
    }

    //method to set Point of Block//initialize Position
    public void setPosition(Point p){
        position = p;
    }

    //method to set Piece on Block//initialize current_Piece
    public void setPiece(Piece p){
        current_piece = p;
    }
}