import javax.swing.*;    
import java.awt.event.*;    
public class view implements ActionListener{    
JFrame f;    
JMenuBar mb;    
JMenu File,Project,About;    
JMenuItem Open,Save,Run,Stop, New;    
JTextArea ta;    
//have used a text area... might be different for our logic...lemme know what you think! 

view(){    
	f=new JFrame();    
	Open = new JMenuItem("Open");    
	Save = new JMenuItem("Save");    
	New = new JMenuItem("New");    
	Run = new JMenuItem("Run");   
	Stop = new JMenuItem("Stop");  
	
	Open.addActionListener(this);    
	Save.addActionListener(this);    
	New.addActionListener(this);    
	Run.addActionListener(this);    
	Stop.addActionListener(this);  
	
	mb=new JMenuBar();    
	File = new JMenu("File");    
	Project = new JMenu("Project");    
	About = new JMenu("About");     
	
	File.add(Open);
	File.add(Save);
	
	Project.add(Run);
	Project.add(New);
	Project.add(Stop);    
	
	mb.add(File);
	mb.add(Project);
	mb.add(About);    
	
	ta=new JTextArea();    
	ta.setBounds(5,5,360,320);    
	f.add(mb);f.add(ta);    
	f.setJMenuBar(mb);  
	f.setLayout(null);    
	f.setSize(400,400);    
	f.setVisible(true);    
}     
public void actionPerformed(ActionEvent e) {    
		if(e.getSource()==Open){
			//write logic here 
		}
			   
		
			//write logic here 
		
			
}     
public static void main(String[] args) {    
    new view();    
}    
}    