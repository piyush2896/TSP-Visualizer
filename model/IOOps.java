package model;

import java.io.*;
import java.util.ArrayList;

public class IOOps {
    public static ArrayList<Point> file2points(String filename){
        BufferedReader reader;

        ArrayList<Point> points = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while(!(line.charAt(0) == '1')){
                line = reader.readLine();
            }

            while(line != null){
                try{
                    String[] splits = line.split(" ");
                    points.add(new Point(Double.parseDouble(splits[1]), Double.parseDouble(splits[2])));
                }catch(Exception ex){
                    break;
                }

                line = reader.readLine();
            }

            reader.close();
            return points;

        }catch(IOException ex){
            System.out.print(ex);
        }
        return null;
    }

    public static void points2file(ArrayList<Point> points, String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            PrintWriter writer = new PrintWriter(fileWriter);
            for(int i = 0; i < points.size(); i++){
                Point pt = points.get(i);
                writer.printf("%d %f %f%n", i+1, pt.getX(), pt.getY());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
