package model;

import java.util.ArrayList;
import java.util.HashSet;

public class Euclidean implements DistanceMetric {
    @Override
    public double calculate(Point start, Point end) {
        double diffXSquared = Math.pow(start.getX() - end.getX(), 2);
        double diffYSquared = Math.pow(start.getY() - end.getY(), 2);

        return Math.sqrt(diffXSquared + diffYSquared);
    }

    @Override
    public double[] calculate(Point start, ArrayList<Point> rest, HashSet<Integer> ignore) {
        double[] distances = new double[rest.size()];

        for(int i = 0; i < rest.size(); i++){
            if(ignore.contains(i))
                distances[i] = Double.MAX_VALUE;
            else
                distances[i] = calculate(start, rest.get(i));
        }

        return distances;
    }
}
