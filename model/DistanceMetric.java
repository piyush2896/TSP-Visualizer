package model;

import java.util.ArrayList;
import java.util.HashSet;

public interface DistanceMetric {
    double calculate(Point start, Point end);
    double[] calculate(Point start, ArrayList<Point> rest, HashSet<Integer> ignore);
}
