package GUI;

import java.io.*;
import java.util.*;

// Class to read moves from database
public class ReadMoves {
    // Static method to access database and read moves
    public static ArrayList<String> readMoves(){
        try{
            // Creating new Scanner object with database as input
            Scanner scn = new Scanner(new File("database.txt"));
            // Initializing list of moves
            ArrayList<String> moves = new ArrayList<String>();
            // Reading all moves in database and add to moves list
            while (scn.hasNextLine()) {
                moves.add(scn.nextLine());
            }

            // Returning moves list
            return moves;
        }
        // File exception handling
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
        return null;
        
    }
}
