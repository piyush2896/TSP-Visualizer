package controller;

import model.Point;
import model.RouteData;
import model.TSPData;
import model.TSPSolver;
import view.MainContainer;
import view.UpdatePlotListener;
import view.plotlab.LinePlot;
import view.plotlab.ScatterPlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class TSPController implements Observer {

    private final static int K = 1;

    private MainContainer mainContainer;
    private TSPSolver tspSolver;
    private UpdatePlotListener plotListener;
    private boolean isNew;

    private void addResizeListener() {
        mainContainer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(mainContainer.getPlotPanel().getComponentCount()!=0){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if(mainContainer.getPlotPanel().getComponent(0) instanceof ScatterPlot){
                                Dimension size = mainContainer.getPlotPanel().getSize();
                                ArrayList<Point> scaledPoints = TSPData.getInstance().getScaledPoints(0, 0, size.width, size.height);
                                plotListener.newScatterPlot(scaledPoints);
                            }else if(mainContainer.getPlotPanel().getComponent(0) instanceof LinePlot){
                                Dimension size = mainContainer.getPlotPanel().getSize();
                                ArrayList<Point> scaledPoints = TSPData.getInstance().getScaledPoints(0, 0, size.width, size.height);
                                plotListener.newOrderFound(scaledPoints, RouteData.getInstance().getTop3Orders());
                            }
                        }
                    });
                }
            }
        });
    }

    private void show(){
        mainContainer.setSize(1000, 1000);
        mainContainer.setVisible(true);
    }

    private void addMenuActionListeners(){
        mainContainer.addNewMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tspSolver.kill();
                TSPData.getInstance().clean();
                mainContainer.getPlotPanel().removeAll();
                mainContainer.updateMenuItemEnabled(true, false, false, true, false);
                mainContainer.revalidate();
                mainContainer.repaint();
                isNew = true;
            }
        });

        mainContainer.addRunMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: CHECK FOR TSPData init
                if(isNew) {
                    int nPoints = TSPData.getInstance().getPoints().size();
                    int nWorkers = 20;
                    if (nPoints < 40) {
                        nWorkers = nPoints;
                    }

                    System.out.println(nPoints + " " + nWorkers);

                    tspSolver = new TSPSolver(nWorkers, TSPController.K);
                    tspSolver.run();
                }else {
                    tspSolver.resume();
                }
                mainContainer.updateMenuItemEnabled(true, true, false, true, true);
            }
        });

        mainContainer.addOpenMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                int result = fileChooser.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    String tspFile = fileChooser.getSelectedFile().getAbsolutePath();
                    TSPData.getInstance().clean();
                    TSPData.getInstance().init(tspFile);
                    mainContainer.updateMenuItemEnabled(true, true, true, true, false);
                    Dimension size = mainContainer.getPlotPanel().getSize();
                    ArrayList<Point> scaledPoints = TSPData.getInstance().getScaledPoints(0, 0, size.width, size.height);
                    plotListener.newScatterPlot(scaledPoints);
                    mainContainer.revalidate();
                    mainContainer.repaint();
                }
            }
        });

        mainContainer.addSaveMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Save data
            }
        });

        mainContainer.addStopMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Check if tspSolver isn't null
                tspSolver.pause();
                isNew = false;
                mainContainer.updateMenuItemEnabled(
                        true, true, true, true, false);
            }
        });
    }

    public TSPController() {
        tspSolver = null;
        isNew = true;
        mainContainer = new MainContainer();
        plotListener = mainContainer.getUpdatePlotListener();
        addMenuActionListeners();
        addResizeListener();
        show();
    }

    public static void main(String[] args) {
        TSPController controller = new TSPController();
        RouteData.getInstance().addObserver(controller);
        TSPData.getInstance().addObserver(controller);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof TSPData){
            if(tspSolver != null){
                tspSolver.pause();
            }
            Dimension size = mainContainer.getPlotPanel().getSize();
            ArrayList<Point> scaledPoints = TSPData.getInstance().getScaledPoints(
                    0, 0, size.width, size.height);
            plotListener.newScatterPlot(scaledPoints);
            if(tspSolver != null) {
                tspSolver.resume();
            }
        }else if(o instanceof RouteData){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Dimension size = mainContainer.getPlotPanel().getSize();
                    ArrayList<Point> scaledPoints = TSPData.getInstance().getScaledPoints(
                            0, 0, size.width, size.height);
                    plotListener.newOrderFound(scaledPoints, RouteData.getInstance().getTop3Orders());
                }
            });
        }
    }
}
