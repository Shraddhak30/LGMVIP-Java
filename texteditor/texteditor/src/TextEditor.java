import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class TextEditor extends JFrame implements ActionListener, WindowListener{
JTextArea jta=new JTextArea();
	File fnameContainer;
	
	public TextEditor(){
            

		Font fnt=new Font("Arial",Font.PLAIN,15);
		Container con=getContentPane();
		
		JMenuBar jmb=new JMenuBar();
		JMenu jfile = new JMenu("FILE");
		JMenu jedit = new JMenu("EDIT");
		JMenu jhelp = new JMenu("HELP");
		
		con.setLayout(new BorderLayout());
		//trying to add scrollbar
		JScrollPane sbrText = new JScrollPane(jta);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sbrText.setVisible(true);
		
		
		jta.setFont(fnt);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		
		con.add(sbrText);

		createMenuItem(jfile,"NEW");
		createMenuItem(jfile,"OPEN");
		createMenuItem(jfile,"SAVE");
		jfile.addSeparator();
		createMenuItem(jfile,"EXIT");
		
		createMenuItem(jedit,"CUT");
		createMenuItem(jedit,"COPY");
		createMenuItem(jedit,"PASTE");
        createMenuItem(jedit,"SAVE AND SUBMIT");
        
		
		createMenuItem(jhelp,"ABOUT TEXT-EDITOR");
		
		jmb.add(jfile);
		jmb.add(jedit);
		jmb.add(jhelp);
		
		setJMenuBar(jmb);
		setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
		addWindowListener(this);
		setSize(500,500);
		setTitle("Untitled.txt - TEXT-EDITOR");
				
		setVisible(true);
	
	}

	public void createMenuItem(JMenu jm,String txt){
		JMenuItem jmi=new JMenuItem(txt);
		jmi.addActionListener(this);
		jm.add(jmi);
	}
	
	public void actionPerformed(ActionEvent e){	
		JFileChooser jfc=new JFileChooser();
		
		if(e.getActionCommand().equals("NEW")){ 
			//new
			this.setTitle("Untitled.txt - TEXT-EDITOR");
			jta.setText("");
			fnameContainer=null;
		}else if(e.getActionCommand().equals("OPEN")){
			//open
			int ret=jfc.showDialog(null,"OPEN");
			
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				try{
					File fl=jfc.getSelectedFile();
					OpenFile(fl.getAbsolutePath());
					this.setTitle(fl.getName()+ " - TextEditor");
					fnameContainer=fl;
				}catch(IOException ers){}
			}
			
		}else if(e.getActionCommand().equals("SAVE")){
			
			if(fnameContainer != null){
				jfc.setCurrentDirectory(fnameContainer);		
				jfc.setSelectedFile(fnameContainer);
			}
			else {
				
				jfc.setSelectedFile(new File("Untitled.txt"));
			}
			
			int ret=jfc.showSaveDialog(null);
				
			if(ret == JFileChooser.APPROVE_OPTION){
				try{
					
					File fyl=jfc.getSelectedFile();
					SaveFile(fyl.getAbsolutePath());
					this.setTitle(fyl.getName()+ " - TextEditor");
					fnameContainer=fyl;
					
				}catch(Exception ers2){}
			}
			
		}else if(e.getActionCommand().equals("EXIT")){
			
			Exiting();

		}else if(e.getActionCommand().equals("COPY")){
			
			jta.copy();

		}else if(e.getActionCommand().equals("PASTE")){
			
			jta.paste();

        }else if(e.getActionCommand().equals("SAVE AND SUBMIT")){
			
			if(fnameContainer != null){
				jfc.setCurrentDirectory(fnameContainer);		
				jfc.setSelectedFile(fnameContainer);
			}
			else {
				
				jfc.setSelectedFile(new File("Untitled.txt"));
			}
			
			int ret=jfc.showSaveDialog(null);
				
			if(ret == JFileChooser.APPROVE_OPTION){
				try{
					
					File fl=jfc.getSelectedFile();
					SaveFile(fl.getAbsolutePath());
					this.setTitle(fl.getName()+ " - TextEditor");
					fnameContainer=fl;
					
				}catch(Exception ers2){}
			}

            Exiting();
        
		}else if(e.getActionCommand().equals("ABOUT TEXT-EDITOR")){ 
			
			JOptionPane.showMessageDialog(this,"Created by: SHRADDHA KADAM","TEXT-EDITOR",JOptionPane.INFORMATION_MESSAGE);

		}else if(e.getActionCommand().equals("Cut")){

			jta.cut();

		}
	}
	
	public void OpenFile(String fname) throws IOException {	
		
		BufferedReader d=new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
		String l;
		
		jta.setText("");
	
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
			
		while((l=d.readLine())!= null) {
			jta.setText(jta.getText()+l+"\r\n");
		}
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		d.close();
	}
	
	public void SaveFile(String fname) throws IOException {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		DataOutputStream o=new DataOutputStream(new FileOutputStream(fname));
		o.writeBytes(jta.getText());
		o.close();		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void windowDeactivated(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	
	public void windowClosing(WindowEvent e) {
		Exiting();
	}
	
	public void windowOpened(WindowEvent e){}
	
	public void Exiting() {
		System.exit(0);
	}

}