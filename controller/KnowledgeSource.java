package controller;

import model.Euclidean;
import model.KNN;
import model.Point;

import java.util.ArrayList;

public class KnowledgeSource extends Thread {
    private int number;
    private ArrayList<Point> points;
    private int startPoint;
    private int k;
    private KNN knn;
    private double distance;

    public KnowledgeSource(ArrayList<Point> points, int number, int k, int startPoint){
        this.points = points;
        this.number = number;
        this.startPoint = startPoint;
        this.k = k;
    }

    public int getNumber() {
        return number;
    }

    public double getDistance() {
        return distance;
    }

    public int[] getOrder(){
        return knn.getBestOrder();
    }

    @Override
    public void run() {
        try {
            knn = new KNN(this.k, new Euclidean());
            distance = knn.run(this.startPoint);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
