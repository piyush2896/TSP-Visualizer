/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.Scanner;
//import CSEAssignment2.NNeigh;
//import CSE564-Assign05

/**
 *
 * @author khushboogupta
 */
public class TSP {
    
    //builds the cordinates matrix
    public static double[][] coordinate_matrix(String filepath) throws  IOException{
        
        int lineno=0,nol=0, flag_eof = 0,dim=0;
          
        LineNumberReader r = new LineNumberReader(new FileReader(filepath));
        String l;       
        while ((l = r.readLine()) != null) 
        {
            Scanner s = new Scanner(l);       
            while (s.hasNext()) {
                nol++;
                String data = s.nextLine();
                if(data.equalsIgnoreCase("NODE_COORD_SECTION")){  
                    lineno=r.getLineNumber();
                }
                if(data.equalsIgnoreCase("EOF")){  
                    flag_eof=1;
                }                
            }            
        }
        if (flag_eof==1){
            dim=nol-lineno-1;
        }
        else{
            dim=nol-lineno;
        }
        
        
        
        double points[][] = new double[dim][2];
        for(int i=0;i<dim;i++){
            for(int j=0;j<2;j++){
                points[i][j]=0;
            }
        }
        File text = new File(filepath);
        System.out.println("File Executed : " + text.getName());
        int presentline=0;
        Scanner s = new Scanner(text);       
            while (s.hasNext()) {
                String prline = s.nextLine();
                presentline++;
                if(presentline>lineno && presentline<=dim+lineno)
                {
                    
                    String[] sp = (prline.split("\\s"));
                    int ind=Integer.parseInt(sp[0]);
                    for(int i=0;i<sp.length;i++){
                        if(i==1){
                            points[ind-1][0]=Double.parseDouble(sp[i]);
                        }
                        if(i==2){
                            points[ind-1][1]=Double.parseDouble(sp[i]);
                        }
                        
                    }
                }
            }

            return points;
    }
    
    //given two points , it calculates the euclidean distance between them.
    public static double euclidean(double x1,double y1,double x2,double y2){
        double xcord=pow(x1-x2,2);
        double ycord=pow(y1-y2,2);
                            
        double dis=xcord+ycord;
        return sqrt(dis);
    }
    

    //builds the adjacency matrix for symmetric data
    public static int[] adjacencymatrix_tsp(double[][] points) throws FileNotFoundException, IOException 
    {       
        int dim=points.length;
        int path[];
        double distance[][] = new double[dim][dim];
        int node=0;
            for(int i=0;i<dim;i++){
                for(int j=0;j<dim;j++){
                        if(i==j){
                            distance[i][j]=0.0;
                        }
                        else{
                            
                            double dis=euclidean(points[i][0],points[i][1],points[j][0],points[j][1]);
                            
                            distance[i][j]= dis;
                            distance[j][i]=distance[i][j];
                        }                   
                }
            }  
            
        path=NNeigh.tsp_nn(distance);
        return path;
    }
    
}
