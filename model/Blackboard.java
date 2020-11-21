package model;

import java.util.ArrayList;
import java.util.Observable;

public class Blackboard extends Observable {

    private static Blackboard BLACKBOARD_INSTANCE = null;

    private TSPData tspData;
    private RouteData routeData;

    public Blackboard() {
        this.tspData = TSPData.getInstance();
        this.routeData = RouteData.getInstance();
    }

    public void addPoint(double x, double y, double minX, double minY, double maxX, double maxY){
        tspData.addPoint(x, y, minX, minY, maxX, maxY);
        setChanged();
        notifyObservers(true);
    }

    public void init(String filename) {
        tspData.init(filename);
        routeData.init();
    }

    public ArrayList<Point> getScaledPoints(double minX, double minY, double maxX, double maxY){
        return tspData.getScaledPoints(minX, minY, maxX, maxY);
    }

    public boolean isInitialized() {
        return tspData.isInitialized();
    }

    public static Blackboard getInstance() {
        if(Blackboard.BLACKBOARD_INSTANCE == null) {
        Blackboard.BLACKBOARD_INSTANCE = new Blackboard();
    }

        return Blackboard.BLACKBOARD_INSTANCE;
}

    public ArrayList<Point> getPoints() {
        return tspData.getPoints();
    }

    public String getFilename() {
        return tspData.getFilename();
    }

    public void clean() {
        tspData.clean();
    }

    public int[][] getTop3Orders() {
        return routeData.getTop3Orders();
    }

    public double[] getDistances() {
        return routeData.getDistances();
    }

    public void checkIfInTop3(int[] order, double distance) {
        routeData.checkIfInTop3(order, distance);
        setChanged();
        notifyObservers(false);
    }
}
