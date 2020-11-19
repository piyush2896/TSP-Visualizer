package view.plotlab;

import model.Point;
import model.TSPData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class LinePlot extends JPanel{

    private static final long serialVersionUID = 1L;

    private ArrayList<Point> coords;
    private int[] order;
    
    public LinePlot(ArrayList<Point> points, int[] order){
        this.coords = points;
        this.order = order;
    }


    private void fillCoords(Graphics2D g2d){

//        Dimension bounds = getParent().getSize();
//        ArrayList<Point> coords = tspData.getScaledPoints(0, 0, bounds.width, bounds.height);

        Point2D.Double pt = new Point2D.Double(coords.get(0).getX(), coords.get(0).getY());
        Ellipse2D dot = new Ellipse2D.Double(pt.x - 1, pt.y - 1, 5, 5);
        g2d.fill(dot);
        for (int i = 0; i < order.length-1; i++) {
            pt = new Point2D.Double(coords.get(i+1).getX(), coords.get(i+1).getY());
            dot = new Ellipse2D.Double(pt.x - 1, pt.y - 1, 5, 5);
            Shape l = new Line2D.Double(
                    coords.get(order[i]).getX(), coords.get(order[i]).getY(),
                    coords.get(order[i+1]).getX(), coords.get(order[i+1]).getY());
            g2d.draw(l);
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