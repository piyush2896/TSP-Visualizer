import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class view extends JPanel implements ActionListener, Runnable{

	public static view dp = new view();
	public static JMenuBar mb=new JMenuBar();
	public static JMenu File= new JMenu("File");
	public static JMenu Project= new JMenu("Project");
	public static JMenu About= new JMenu("About");
	public static JMenuItem Open= new JMenuItem("Open");
	public static JMenuItem Save= new JMenuItem("Save");
	public static JMenuItem Run= new JMenuItem("Run");
	public static JMenuItem Stop = new JMenuItem("Stop");
	public static JMenuItem New= new JMenuItem("New");;
	public static JFileChooser openFileChooser;
	public int[][] scaledpoints;
	String FilePath="";
	Thread t;
	public double[][] points;

	private BufferedImage bi = new BufferedImage(2000, 1000, BufferedImage.TYPE_INT_RGB);

	public view() {
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

				JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int r = j.showOpenDialog(view.this);
				if (r == JFileChooser.APPROVE_OPTION) {
					FilePath = j.getSelectedFile().getAbsolutePath();
					System.out.println("path" + j.getSelectedFile().getAbsolutePath());

					try {
						points = TSP.coordinate_matrix(FilePath);
						System.out.println("length " + points.length);
						for (int i = 0; i < points.length; i++) {
							System.out.println(points[i][0] + " " + points[i][1]);
						}
						drawoval(points);

					} catch (IOException ioException) {
						ioException.printStackTrace();
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

					g2d.fillOval(x, y, 4, 4);
					dp.repaint();
				}
				g.dispose();
				g2d.dispose();
			}


			//scales all the points to fit them in the frame
			private int scale(double pt, double max, double min) {

				pt = 1000 * (pt - min) / (max - min);
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


public static void main(String[] args)  {

	EventQueue.invokeLater(new Runnable() {
		@Override
		public void run() {
			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout());
			dp.runOpenBtn();
			File.add(Open);
			File.add(Save);

			Project.add(Run);
			Project.add(New);
			Project.add(Stop);

			mb.add(File);
			mb.add(Project);
			mb.add(About);

			dp.add(mb);
			frame.add(dp,BorderLayout.CENTER);
			frame.add(mb);
			frame.setJMenuBar(mb);
			frame.setLayout(null);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1000, 1000);
			frame.setVisible(true);
		}
	});

}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void run() {

	}
}