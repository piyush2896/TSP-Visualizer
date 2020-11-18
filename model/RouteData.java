package model;

import java.util.Observable;

public class RouteData extends Observable {
    public static RouteData ROUTE_DATA_INSTANCE = null;

    private int[][] top3Orders;
    private double[] distances;

    private RouteData(){
        top3Orders = new int[3][TSPData.getInstance().getPoints().size()];
        distances = new double[]{Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE};
    }

    public static RouteData getInstance(){
        if(RouteData.ROUTE_DATA_INSTANCE == null){
            RouteData.ROUTE_DATA_INSTANCE = new RouteData();
        }

        return RouteData.ROUTE_DATA_INSTANCE;
    }

    public void checkIfInTop3(int[] order, double distance) {
        boolean isChanged = false;

        if(distance < distances[0]) {
            top3Orders[2] = top3Orders[1];
            top3Orders[1] = top3Orders[0];
            top3Orders[0] = order;

            distances[2] = distances[1];
            distances[1] = distances[0];
            distances[0] = distance;

            isChanged = true;
        }else if(distance < distances[1]) {
            top3Orders[2] = top3Orders[1];
            top3Orders[1] = order;

            distances[2] = distances[1];
            distances[1] = distance;

            isChanged = true;
        }else if(distance < distances[2]) {
            top3Orders[2] = order;
            distances[2] = distance;

            isChanged = true;
        }

        if(isChanged) {
            setChanged();
            notifyObservers();
        }
    }
}
