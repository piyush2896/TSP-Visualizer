package controller;

import model.*;
import model.Point;
import view.MainContainer;
import view.UpdatePlotListener;
import view.plotlab.LinePlot;
import view.plotlab.ScatterPlot;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class TSPController implements Observer {

    private final static int K = 1;
    private final static String ABOUT_CONTENT = new StringBuilder()
            .append("<html><center>Team Details...")
            .append("<br>Khushboo Gupta, kgupta51@asu.edu, 1217167315")
            .append("<br>Piyush Malhotra, pmalhot3@asu.edu, 1217439379")
            .append("<br>Swarnalatha Srenigarajan, ssreniga@asu.edu, 1217035534</center></html>")
            .toString();

    private MainContainer mainContainer;
    private TSPSolver tspSolver;
    private UpdatePlotListener plotListener;
    private boolean isNew;

    private void show(){
        mainContainer.setSize(1000, 1000);
        mainContainer.setVisible(true);
    }

    private void addAbout() {
        mainContainer.addAboutMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                JLabel label = new JLabel(TSPController.ABOUT_CONTENT);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                JOptionPane.showMessageDialog(mainContainer,  label);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
    }

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

    private void addMouseClickListener() {
        mainContainer.addMouseClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(mainContainer.getPlotPanel().getComponentCount() == 0 ||
                        mainContainer.getPlotPanel().getComponent(0) instanceof ScatterPlot){

                    double x = e.getX();
                    double y = e.getY();

                    Dimension bounds = mainContainer.getPlotPanel().getSize();
                    TSPData.getInstance().addPoint(x, y, 0, 0, bounds.width, bounds.height);

                    mainContainer.updateMenuItemEnabled(
                            true, true, true, true, false);
                }
            }
        });
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
                if(isNew) {
                    int nPoints = TSPData.getInstance().getPoints().size();
                    int nWorkers = 20;
                    if (nPoints < 40) {
                        nWorkers = nPoints;
                    }

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
                int result = fileChooser.showOpenDialog(mainContainer);
                if(result == JFileChooser.APPROVE_OPTION){
                    String tspFile = fileChooser.getSelectedFile().getAbsolutePath();
                    tspSolver = null;
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
                JFileChooser fileChooser = new JFileChooser(".");
                int result = fileChooser.showSaveDialog(mainContainer);

                if(result == JFileChooser.APPROVE_OPTION) {
                    String saveFile = fileChooser.getSelectedFile().getAbsolutePath();
                    IOOps.points2file(TSPData.getInstance().getPoints(), saveFile);
                }
//                String filename = TSPData.getInstance().getFilename();
//                filename = filename.replace(".tsp", ".txt");
//                IOOps.points2file(TSPData.getInstance().getPoints(), filename);
            }
        });

        mainContainer.addStopMenuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        EventQueue.invokeLater(() ->{
            mainContainer = new MainContainer();
            plotListener = mainContainer.getUpdatePlotListener();
            addMenuActionListeners();
            addResizeListener();
            addMouseClickListener();
            addAbout();
            show();
        });
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
