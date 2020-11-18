package view;

import model.TSPData;
import view.plotlab.LinePlot;
import view.plotlab.ScatterPlot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private BufferedImage bi = new BufferedImage(2000, 1000, BufferedImage.TYPE_INT_RGB);

    public DrawPanel(){
        setLayout(new GridLayout(1, 1));
    }

    public void initPlot(Dimension bounds){

        System.out.println(bounds.width + " " + bounds.height);
        ScatterPlot plot = new ScatterPlot(
                TSPData.getInstance().getScaledPoints(0, 0, bounds.width, bounds.height));

        if(getComponentCount() != 0){
            removeAll();
            add(plot);
            revalidate();
            repaint();
        }else{
            add(plot);
        }
    }

    public void updateView(int[] order, Dimension bounds){
        removeAll();
        LinePlot plot = new LinePlot(
                TSPData.getInstance().getScaledPoints(0, 0, bounds.width, bounds.height), order);
        add(plot);
        revalidate();
        repaint();
    }

}
