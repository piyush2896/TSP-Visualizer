package model;

import model.Euclidean;
import model.KNN;
import model.Point;

import java.util.ArrayList;

public class KnowledgeSource extends Thread {
    private int number;
    private ArrayList<Point> points;
    private int startPoint;
    private int endPoint;
    private int k;
    private KNN bestKNN;
    private double distance;

    public KnowledgeSource(ArrayList<Point> points, int number, int k, int startPoint, int endPoint){
        this.points = points;
        this.number = number;
        this.startPoint = startPoint;
        this.k = k;
        this.endPoint = endPoint;
    }

    public int getNumber() {
        return number;
    }

    public double getDistance() {
        return distance;
    }

    public int[] getOrder(){
        return bestKNN.getBestOrder();
    }

    @Override
    public void run() {
        try {
            double minDistance = Double.MAX_VALUE;
            KNN knn = new KNN(this.k, new Euclidean());
            for(int i = startPoint; i <= endPoint; i++){
                distance = knn.run(i);
                if(distance < minDistance){
                    minDistance = distance;
                    bestKNN = knn;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
