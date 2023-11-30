package comp;
import java.util.ArrayList;

//Abstract class 
public abstract class Piece{
    public String color;
    public String NAME;
    public Board current_board = new Board();
    public abstract boolean move(Point p);

    //abstract ArrayList which stores all moves of a particular Piece at a Point
    public abstract ArrayList<Point> getPossibleMoves();

    //Position of Piece currently
    public Point pos = new Point();


    //method to get Position of a particular Piece
    public Point getPosition(){
        return pos;
    }


    //method to set colour of a particular piece
    public void setColor(String color){
        this.color = color;
    }


    //method to check if x,y coordinates exist within the board
    protected boolean params_check(Point p){
        int x = p.getX();
        int y = p.getY();
        if(x>=0 && x<8 && y>=0 && y<8){
            return true;
        }
        return false;
    }


    //method to get Path of a particular piece till a specific point
    public ArrayList<Point> getPath(Point p){
        return null;
    }


    public String getName(){
        return NAME;
    }


    public int getX(){
        return pos.getX();
    }


    public int getY(){
        return pos.getY();
    }


    //method to get colour of particular piece
    public String getColor(){
        return color;
    }
}