package view;

import model.Blackboard;
import model.Point;
import view.plotlab.LinePlot;
import view.plotlab.ScatterPlot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DrawPanel extends JPanel implements Observer {

    public DrawPanel(){
        setLayout(new GridLayout(1, 1));
    }

    public void initPlot(ArrayList<Point> points, Dimension bounds){

        ScatterPlot plot = new ScatterPlot(points);

        if(getComponentCount() != 0){
            removeAll();
            add(plot);
            revalidate();
            repaint();
        }else{
            add(plot);
        }
    }

    public void updateView(ArrayList<Point> points, int[][] orders, Dimension bounds){
        removeAll();
        LinePlot plot = new LinePlot(points, orders);
        add(plot);
        revalidate();
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        if((Boolean) arg ){
            Dimension size = getSize();
            ArrayList<Point> scaledPoints = Blackboard.getInstance().getScaledPoints(
                    0, 0, size.width, size.height);
            initPlot(scaledPoints, getSize());
            revalidate();
            repaint();
        }else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Dimension size = getSize();
                    ArrayList<Point> scaledPoints = Blackboard.getInstance().getScaledPoints(
                            0, 0, size.width, size.height);
                    updateView(scaledPoints, Blackboard.getInstance().getTop3Orders(), getSize());
                    revalidate();
                    repaint();
                }
            });
        }
    }
}
