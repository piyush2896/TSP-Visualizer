package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class TSPData implements Observer {

    private static final int START_ROW = 8;
    private static TSPData TSP_DATA_INSTANCE = null;

    private ArrayList<Point> points = new ArrayList<>();
    private boolean isInitialized = false;
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;

    private TSPData(){}

    private void initMaxAndMin(){
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;

        for (Point point : points) {
            if (maxX < point.getX())
                maxX = point.getX();
            if (maxY < point.getY())
                maxY = point.getY();
            if (minX > point.getX())
                minX = point.getX();
            if (minY > point.getY())
                minY = point.getY();
        }
    }

    public void init(String filename){
        isInitialized = true;

        points = IOOps.file2points(filename, TSPData.START_ROW);
        initMaxAndMin();
    }

    private Point toRange(Point point, double[] beforeX, double[] beforeY, double[] newX, double[] newY){
        Point newPoint = point.clone();
        double x =  (point.getX() - beforeX[0]) / (beforeX[1] - beforeX[0]) * (newX[1] - newX[0]);
        double y =  (point.getY() - beforeY[0]) / (beforeY[1] - beforeY[0]) * (newY[1] - newY[0]);

        newPoint.setX(x);
        newPoint.setY(y);
        return newPoint;
    }

    public void addPoint(double x, double y, double minX, double minY, double maxX, double maxY){

        Point point = toRange(
                new Point(x, y), new double[]{minX, maxX}, new double[]{minY, maxY},
                new double[]{this.minX, this.maxX}, new double[]{this.minY, this.maxY});

        points.add(point);
    }

    public ArrayList<Point> getScaledPoints(double minX, double minY, double maxX, double maxY){
        ArrayList<Point> scaledPoints = new ArrayList<>();

        for(Point point : this.points){
            Point newPoint = toRange(
                    point, new double[]{this.minX, this.maxX}, new double[]{this.minY, this.maxY},
                    new double[]{minX, maxX}, new double[]{minY, maxY}
            );
            scaledPoints.add(newPoint);
        }

        return scaledPoints;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public static TSPData getInstance(){
        if(TSP_DATA_INSTANCE == null){
            TSP_DATA_INSTANCE = new TSPData();
        }

        return TSP_DATA_INSTANCE;
    }

    public ArrayList<Point> getPoints() { return points; }

    @Override
    public void update(Observable o, Object coords) {
        // coords - > {Y coord, X coord, minY, minX, maxY, maxX}
        double[] pointDouble = (double[]) coords;
        addPoint(pointDouble[1], pointDouble[0], pointDouble[3], pointDouble[2], pointDouble[5], pointDouble[4]);
    }
}
