package comp;

//implemented by White and Black classes
//define behavior of black and white Player
public interface Player{

    public void setupBoard();

    public String getColor();

    public String move(Point p1, Point p2);

    public String move(int x1, int y1, int x2, int y2);


}