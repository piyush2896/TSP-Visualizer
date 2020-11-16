/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author khushboogupta
 */
public class NNeigh {
    
    
     //calculates the nearest neighbor edge from the present node.
    public static int shortest_edge_element(double[][] matrix, int u, int[] visited){
        double mini=Double.MAX_VALUE;
        int n = matrix.length;
        int v=0;
        for(int i=0;i<n;i++){
            if((i!=u) && (visited[i]==0) && (mini>matrix[u][i])){
                mini=matrix[u][i];
                v=i;
            }
        }
        return v;
    }
    
    //goes through all the nodes and calculates the path.
    public static int[] tsp_nn(double[][] matrix){
        int n = matrix.length;
        int path[]=new int[n];
        int visited[]=new int[n];
        for(int i=0;i<n;i++){
            visited[i]=0;
        }
        
        int u=0,flag=0;
        int p=0;
        int v;
        double path_sum=0;
        while(flag==0){
            
            path[p]=u;
            p++;
            visited[u]=1;
            v=shortest_edge_element(matrix,u,visited);
            if (visited[v]==0){
                path_sum+=matrix[u][v];
                u=v;
            }
            else{
                break;
            }
            
        }
        System.out.println("Path Sum is : " + path_sum+"  ");
        System.out.println("Path is : ");
        for(int i=0;i<path.length;i++){
            System.out.print(path[i]+1+"  ");
        }  
        
        return path;
    }
    
}
