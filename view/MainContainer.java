package view;

import model.Point;
import model.TSPData;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MainContainer extends JFrame {
    private DrawPanel plotPanel;
    private MenuBar menuBar;

    private UpdatePlotListener updatePlotListener;

    private void initUpdatePlotListener(){
        updatePlotListener = new UpdatePlotListener() {
            @Override
            public void newScatterPlot(ArrayList<Point> points) {
                plotPanel.initPlot(points, getSize());
                revalidate();
                repaint();
            }

            @Override
            public void newOrderFound(ArrayList<Point> points, int[] order) {
                plotPanel.updateView(points, order, getSize());
                revalidate();
                repaint();
            }
        };
    }

    public MainContainer(){
        menuBar = new MenuBar();
        updateMenuItemEnabled(true, false, false, true, false);

        plotPanel = new DrawPanel();

        initUpdatePlotListener();
        setJMenuBar(menuBar);
        add(plotPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public DrawPanel getPlotPanel() {
        return plotPanel;
    }

    public void updateMenuItemEnabled(boolean openMenu, boolean saveMenu, boolean runMenu, boolean newMenu, boolean stopMenu) {
        menuBar.getOpenMenuItem().setEnabled(openMenu);
        menuBar.getSaveMenuItem().setEnabled(saveMenu);
        menuBar.getRunMenuItem().setEnabled(runMenu);
        menuBar.getNewMenuItem().setEnabled(newMenu);
        menuBar.getStopMenuItem().setEnabled(stopMenu);
    }

    public UpdatePlotListener getUpdatePlotListener() {
        return updatePlotListener;
    }

    public void addMouseClickListener(MouseListener mouseListener){
        plotPanel.addMouseListener(mouseListener);
    }

    public void addNewMenuActionListener(ActionListener newMenuActionListener){
        menuBar.getNewMenuItem().addActionListener(newMenuActionListener);
    }

    public void addOpenMenuActionListener(ActionListener openMenuActionListener){
        menuBar.getOpenMenuItem().addActionListener(openMenuActionListener);
    }

    public void addStopMenuActionListener(ActionListener stopMenuActionListener){
        menuBar.getStopMenuItem().addActionListener(stopMenuActionListener);
    }

    public void addRunMenuActionListener(ActionListener runMenuActionListener){
        menuBar.getRunMenuItem().addActionListener(runMenuActionListener);
    }

    public void addSaveMenuActionListener(ActionListener saveMenuActionListener){
        menuBar.getSaveMenuItem().addActionListener(saveMenuActionListener);
    }

//    public static void main(String[] args) {
//        MainContainer container = new MainContainer();
//        container.setSize(1000, 1000);
//        container.setVisible(true);
//        TSPData tspData = TSPData.getInstance();
//        tspData.init("C:\\Piyush\\Fall2020\\CSE564\\Assignment05\\CSE564-Assign05\\Data\\wi29.tsp");
//
//        container.getUpdatePlotListener().newScatterPlot();
//    }
}
