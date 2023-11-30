package GUI;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;


// Class to write moves to database
public class WriteToFile {
    // Static method to access database and write moves
    public static int writeMoves(ArrayList<String> moves){
        // Checking if moves list is empty then terminating
        if(moves.size()==0){
            return -1;
        }
        // Setting date and time formatter
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        // Getting current tiem and date
        LocalDateTime now = LocalDateTime.now();
        try {
            // Opening database file in write mode
            FileWriter file = new FileWriter("database.txt", true);
            // Formatting current time and date and adding to string all_moves
            String all_moves = dtf.format(now)+" : ";
            // Appending all moves in list moves to all_moves
            for (String s : moves) {
                all_moves += s + " , ";
            }
            // Writing moves to database
            file.write(all_moves.substring(0,all_moves.length()-3) + "\n");
            // Closing file
            file.close();
        } 
        // Catching file exceptions
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}
