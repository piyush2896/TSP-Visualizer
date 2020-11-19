package view;

import model.Point;
import model.TSPData;
import view.plotlab.LinePlot;
import view.plotlab.ScatterPlot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private BufferedImage bi = new BufferedImage(2000, 1000, BufferedImage.TYPE_INT_RGB);

    public DrawPanel(){
        setLayout(new GridLayout(1, 1));
    }

    public void initPlot(ArrayList<Point> points, Dimension bounds){

        System.out.println(bounds.width + " " + bounds.height);
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

    public void updateView(ArrayList<Point> points, int[] order, Dimension bounds){
        removeAll();
        LinePlot plot = new LinePlot(points, order);
        add(plot);
        revalidate();
        repaint();
    }

}
