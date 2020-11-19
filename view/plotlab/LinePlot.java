package view.plotlab;

import model.PausableThreadPoolService;
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
    private int[][] orders;
    
    public LinePlot(ArrayList<Point> points, int[][] orders){
        this.coords = points;
        this.orders = orders;
    }

    private void fillPoints(Graphics2D g2d) {

        for(int i = 0; i < coords.size(); i++) {
            Point2D.Double pt = new Point2D.Double(coords.get(i).getX(), coords.get(i).getY());
            Ellipse2D dot = new Ellipse2D.Double(pt.x - 1, pt.y - 1, 5, 5);
            g2d.fill(dot);
        }
    }

    private void fillCoords(Graphics2D g2d, int[] order){

        int i = 0;
        for (; i < order.length-1; i++) {
            Shape l = new Line2D.Double(
                    coords.get(order[i]).getX(), coords.get(order[i]).getY(),
                    coords.get(order[i+1]).getX(), coords.get(order[i+1]).getY());
            g2d.draw(l);
        }
        Shape l = new Line2D.Double(
                coords.get(order[i]).getX(), coords.get(order[i]).getY(),
                coords.get(order[0]).getX(), coords.get(order[0]).getY());
        g2d.draw(l);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Color[] colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN};
        g2d.setStroke(new BasicStroke(4f));
        try{

//            PausableThreadPoolService executor = new PausableThreadPoolService();
            for(int i = 0; i < orders.length; i++){
                g2d.setColor(colors[i]);
                fillCoords(g2d, orders[i]);
//                executor.execute(new DrawLine(g2d, orders[i]));
            }
//            executor.shutdown();
        }catch (Exception e){

        }
        g2d.setColor(Color.BLACK);
        fillPoints(g2d);
        g2d.dispose();
    }

//    class DrawLine extends Thread {
//
//        private Graphics2D g2d;
//        private int[] order;
//
//        public DrawLine(Graphics2D g2d, int[] order) {
//            this.g2d = g2d;
//            this.order = order;
//        }
//
//        public void run() {
//            int i = 0;
//            for (; i < order.length-1; i++) {
//                Shape l = new Line2D.Double(
//                        coords.get(order[i]).getX(), coords.get(order[i]).getY(),
//                        coords.get(order[i+1]).getX(), coords.get(order[i+1]).getY());
//                g2d.draw(l);
////                try {
////                    sleep(100);
////                }catch (Exception e){}
//            }
//            Shape l = new Line2D.Double(
//                    coords.get(order[i]).getX(), coords.get(order[i]).getY(),
//                    coords.get(order[0]).getX(), coords.get(order[0]).getY());
//            g2d.draw(l);
//        }
//    }
}
