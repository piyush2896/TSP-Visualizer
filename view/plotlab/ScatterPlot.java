package view.plotlab;

import model.Point;
import model.TSPData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ScatterPlot extends JPanel{

    private static final long serialVersionUID = 1L;
    private ArrayList<Point> coords;
    
    public ScatterPlot(ArrayList<Point> coords){
        this.coords = coords;
    }

    private void fillCoords(Graphics2D g2d){
//        Dimension bounds = getParent().getSize();
//        ArrayList<Point> coords = tspData.getScaledPoints(0, 0, bounds.width, bounds.height);
        for (int i = 0; i < coords.size(); i++) {
            Point2D.Double pt = new Point2D.Double(coords.get(i).getX(), coords.get(i).getY());
            Ellipse2D dot = new Ellipse2D.Double(pt.x - 1, pt.y - 1, 5, 5);
            g2d.fill(dot);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g.setColor(Color.BLACK);
        fillCoords(g2d);
        g2d.dispose();
    }
}