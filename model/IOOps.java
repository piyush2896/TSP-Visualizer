package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IOOps {
    public static ArrayList<Point> file2points(String filename, int startRow){
        BufferedReader reader;

        ArrayList<Point> points = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(filename));
            int lineNum = 1;
            String line = reader.readLine();

            while(!line.contains("DIMENSION")){
                line = reader.readLine();
                lineNum += 1;
            }

            while(line != null){
                if(lineNum == startRow){
                    String[] splits = line.split(" ");
                    try{
                        points.add(new Point(Double.parseDouble(splits[1]), Double.parseDouble(splits[2])));
                    }catch(Exception ex){
                        break;
                    }
                }else{
                    lineNum += 1;
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
}
