package view;

import model.TSPData;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainContainer extends JFrame {
    private DrawPanel plotPanel;
    private MenuBar menuBar;

    private UpdatePlotListener updatePlotListener;

    private void initUpdatePlotListener(){
        updatePlotListener = new UpdatePlotListener() {
            @Override
            public void newFileRead() {
                plotPanel.initPlot(getSize());
                revalidate();
                repaint();
            }

            @Override
            public void newOrderFound(int[] order) {
                plotPanel.updateView(order, getSize());
                revalidate();
                repaint();
            }
        };
    }

    public MainContainer(){
        menuBar = new MenuBar();
        menuBar.getOpenMenuItem().setEnabled(true);
        menuBar.getSaveMenuItem().setEnabled(false);
        menuBar.getRunMenuItem().setEnabled(false);
        menuBar.getNewMenuItem().setEnabled(true);
        menuBar.getStopMenuItem().setEnabled(false);

        plotPanel = new DrawPanel();

        initUpdatePlotListener();
        setJMenuBar(menuBar);
        add(plotPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setVisible(true);
    }

    public UpdatePlotListener getUpdatePlotListener() {
        return updatePlotListener;
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

    public static void main(String[] args) {
        MainContainer container = new MainContainer();
        TSPData tspData = TSPData.getInstance();
        tspData.init("C:\\Piyush\\Fall2020\\CSE564\\Assignment05\\CSE564-Assign05\\Data\\wi29.tsp");

        container.getUpdatePlotListener().newFileRead();
    }
}
