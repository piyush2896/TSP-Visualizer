

/**
 *
 * @author khushboogupta
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class DrawPanel extends JPanel implements ActionListener, Runnable {

    public static DrawPanel dp = new DrawPanel();
    public static JFileChooser openFileChooser;
    public double[][] points;
    public int[][] scaledpoints;
    public int path[];
    static JFrame f = new JFrame("frame");
    public double path_sum = 0;
    public static JMenu File= new JMenu("File");
    public static JMenu Project= new JMenu("Project");
    public static JMenu About= new JMenu("About");

    public static JMenuItem Save= new JMenuItem("Save");
    public static JMenuItem Run= new JMenuItem("Run");
    public static JMenuItem Stop = new JMenuItem("Stop");
    public static JMenuItem New= new JMenuItem("New");
    public static JMenuItem Open= new JMenuItem("Open");
    public static JMenuBar mb=new JMenuBar();
    public static JLabel l = new JLabel();
    private BufferedImage bi = new BufferedImage(2000, 1000, BufferedImage.TYPE_INT_RGB);
    Thread t;

    public DrawPanel() {
        setBackground(Color.WHITE);
        openFileChooser = new JFileChooser();
        t = new Thread(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bi, 0, 100, Color.yellow, this);
    }

    public void runOpenBtn() {
        Open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("open btn clicked");
                int returnValue = openFileChooser.showOpenDialog(DrawPanel.this);
                int dim = 0;
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    Save.setEnabled(true);
                    File sf = openFileChooser.getSelectedFile();
                    try {
                        points = TSP.coordinate_matrix(sf.toString());
                        for(int i=0;i<points.length;i++){

                            System.out.println(points[i][0]+"  "+points[i][1]);
                        }
                        drawoval(points);

                    } catch (Exception e2) {
                    }

                }
            }

            public void drawoval(double[][] points) {
                scaledpoints = new int[points.length][2];
                Graphics g = bi.getGraphics();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.red);
                double max_x, min_x, max_y, min_y;
                int x, y;

                for (int i = 0; i < points.length; i++) {
                    max_x = calmax(points, 0);
                    min_x = calmin(points, 0);
                    max_y = calmax(points, 1);
                    min_y = calmin(points, 1);

                    x = scale(points[i][0], max_x, min_x);
                    y = scale(points[i][1], max_y, min_y);

                    scaledpoints[i][0] = x;
                    scaledpoints[i][1] = y;
                    System.out.println(points[i][0] + "  " + points[i][0]+ "  "+ scaledpoints[i][0] + "  " + scaledpoints[i][1]);
                    g2d.fillOval(x, y, 4, 4);
                    dp.repaint();

                }
                Run.setEnabled(true);
                g.dispose();
                g2d.dispose();
            }

            //scales all the points to fit them in the frame
            private int scale(double pt, double max, double min) {
                pt = 700 * (pt - min) / (max - min);
                return (int) pt;
            }

            //calculates maximum of all the x cordinates
            private double calmax(double[][] points, int col) {
                double max = Double.MIN_VALUE;
                for (int i = 0; i < points.length; i++) {
                    if (points[i][col] > max) {
                        max = points[i][col];
                    }
                }
                return max;
            }

            //calculates minimum of all the y cordinates
            private double calmin(double[][] points, int col) {
                double min = Double.MAX_VALUE;
                for (int i = 0; i < points.length; i++) {
                    if (points[i][col] < min) {
                        min = points[i][col];
                    }
                }
                return min;
            }
        });
    }

    public void runSaveBtn(){
        Run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void runNewBtn(){
        New.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bi = new BufferedImage(2000, 1000, BufferedImage.TYPE_INT_RGB);
                dp.repaint();
                for(int i=0;i<points.length;i++){
                    for(int j=0;j<2;j++){
                        points[i][j]=0;
                        scaledpoints[i][j]=0;
                    }
                }
                Open.setEnabled(true);
                Save.setEnabled(false);
                Run.setEnabled(false);
                New.setEnabled(true);
                Stop.setEnabled(false);


            }
        });
    }

        private void runRunBtn() {
        Run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Run.getText().equalsIgnoreCase("Run")) {
                    if (t.isAlive()) {
                        t.resume();
                        Run.setEnabled(false);
                        Stop.setEnabled(true);

                    }
                    else {
                        t.start();
                        Run.setEnabled(false);
                        Stop.setEnabled(true);
                    }
                }
            }

        });
    }

    public void runStopBtn(){
        Stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.suspend();
                Run.setEnabled(true);
                Stop.setEnabled(false);

            }
        });
    }

    public void runAboutBtn(){
        About.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                System.out.println("menuSelected");
                JFrame f= new JFrame("About");
                JLabel label = new JLabel("my text");
                label.setFont(new java.awt.Font("Arial", Font.ITALIC, 16));
                label.setOpaque(true);
                label.setBackground(Color.BLACK);
                label.setForeground(Color.WHITE);

                f.getContentPane().add( label );
                f.setSize(400,400);
                f.setLayout(null);
                f.setVisible(true);

            }

            @Override
            public void menuDeselected(MenuEvent e) {
                //System.out.println("menuDeselected");

            }

            @Override
            public void menuCanceled(MenuEvent e) {
                //System.out.println("menuCanceled");

            }
        });
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();

                Open.setEnabled(true);
                Save.setEnabled(false);
                Run.setEnabled(false);
                New.setEnabled(true);
                Stop.setEnabled(false);

                dp.runOpenBtn();
                dp.runSaveBtn();
                dp.runNewBtn();
                dp.runRunBtn();
                dp.runStopBtn();
                dp.runAboutBtn();

                File.add(Open);
                File.add(Save);

                Project.add(New);
                Project.add(Run);
                Project.add(Stop);

                mb.add(File);
                mb.add(Project);
                mb.add(About);

                dp.add(mb);

                frame.add(dp);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 1000);
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




    @Override
    public void run() {
        int it = 0;
        Graphics g = bi.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.yellow);

        try {
            path = TSP.adjacencymatrix_tsp(points);
            for (int i = 0; i < path.length; i++) {
                if (i == path.length - 1) {
                    g2d.drawLine(scaledpoints[path[i]][0], scaledpoints[path[i]][1], scaledpoints[path[0]][0], scaledpoints[path[0]][1]);
                    dp.repaint();
                    Stop.setEnabled(false);
                    t.sleep(1000);

                } else {
                    g2d.drawLine(scaledpoints[path[i]][0], scaledpoints[path[i]][1], scaledpoints[path[i + 1]][0], scaledpoints[path[i + 1]][1]);
                    dp.repaint();
                    t.sleep(1000);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
