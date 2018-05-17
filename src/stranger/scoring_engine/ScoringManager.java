package stranger.scoring_engine;

import java.io.*;
import java.util.ArrayList;

public class ScoringManager {
    ArrayList<Integer> scores= new ArrayList<>(10);

    public static   void saveScores(ArrayList scores){
        System.out.println(scores);
        try {
            OutputStream file = new FileOutputStream("E:/scores.ser");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(scores);
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  ArrayList<Integer> loadScores(){
        ArrayList<Integer> temp = new ArrayList<>();
        System.out.println(temp);
        try {
            InputStream file = new FileInputStream("E:/scores.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream (buffer);
            temp=(ArrayList<Integer>)input.readObject();
            System.out.println(temp);
        }
        catch (FileNotFoundException fnfe){
            System.out.println("file not found, created new object");
            return temp;
        }
        catch (EOFException e){
            return temp;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
