package comp;
import java.util.*;

public class Point{
    //x and y coordinates of Point
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }


    public Point(){
        x = 0;
        y = 0;
    }


    //method to get x coordinate of point
    public int getX(){
        return x;
    }


    //method to get y coordinate of point
    public int getY(){
        return y;
    }

    
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public String toNotation(){
        //HashMap storing key value pairs
        Map<Integer,String> converter = new HashMap<Integer,String>();
        converter.put(0,"A");
        converter.put(1,"B");
        converter.put(2,"C");
        converter.put(3,"D");
        converter.put(4,"E");
        converter.put(5,"F");
        converter.put(6,"G");
        converter.put(7,"H");
        return String.format("%s%d",converter.get(y),8-x);
    }


    //method checking if two Points have the same coordinates
    public boolean equals(Point p2){
        if(this.getX() == p2.getX() && this.getY() == p2.getY()){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("(%d,%d)",x,y);
    }
}