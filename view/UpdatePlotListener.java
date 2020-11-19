package view;

import model.Point;

import java.util.ArrayList;

public interface UpdatePlotListener {

    void newScatterPlot(ArrayList<Point> points);
    void newOrderFound(ArrayList<Point> points, int[] order);
}
