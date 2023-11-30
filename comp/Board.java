package comp;
import java.util.*;

public class Board{
    public int x = 0;
    
    //Creating an ArrayList which stores ArrayLists which further store Blocks
    List<List<Block>> board = new ArrayList<List<Block>>(8);

    public Board(){

        //for loop for storing eight blocks in inner ArrayList
        for(int i = 0; i<8;i++){
            ArrayList<Block> al = new ArrayList<Block>(8);
            al.add(new Block());
            al.add(new Block());
            al.add(new Block());
            al.add(new Block());
            al.add(new Block());
            al.add(new Block());
            al.add(new Block());
            al.add(new Block());
            //Storing inner ArrayList in outer ArrayList
            board.add(al);
        }
        
    }

    
    //Method for getting a piece by providing x and y coordinates
    public Piece getPiece(int x, int y){
        return this.getBlock(x,y).getPiece();
    }

    //Method for getting a piece by providing a point
    public Piece getPiece(Point p){
        return this.getPiece(p.getX(),p.getY());
    }

    //Method for setting piece on the board by providing x,y coordinates and that piece
    public void setPiece(int x, int y, Piece p){
        this.getBlock(x,y).setPiece(p);
    }

    //Method for setting Piece on the Board by providing that piece and a point
    public void setPiece(Point pos, Piece p){
        this.setPiece(pos.getX(),pos.getY(), p);
    }

    //Method for getting a Block from the Board by providing x,y coordinates
    public Block getBlock(int x, int y){
        return board.get(x).get(y);
    }

    //Method for getting a block from the Board by providing a point
    public Block getBlock(Point p){
        return this.getBlock(p.getX(),p.getY());
    }
    
}