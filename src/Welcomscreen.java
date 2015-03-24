import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
											//Importing all the necessary classes and functions
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Panel;

import javax.swing.JEditorPane;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;


@SuppressWarnings("serial")
public class Welcomscreen extends JFrame {
												// Initialising and declaring the global Variables to be used within the frame.
	private JPanel contentPane;
	private JTextField txtfeildFilepath;
	private JFileChooser fileSelector;
	private String SelectorTitle;
	private static String path;
	private JButton btnopen;
	private JButton btnproceed;
	private JButton btnExtract;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcomscreen frame = new Welcomscreen();
					frame.setVisible(true); 					// setting the frame visible
					frame.setLocationRelativeTo(null);			 // Setting the screen to be placed at the centre of the screen
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Welcomscreen() { // This is where the buttons and other GUI features and their actions created, the global components have been previously declared at the start of file.
		setTitle("MobView - Digital Forensic Toolkit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 439);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("File");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		Image icon2 = new ImageIcon(this.getClass().getResource("/manual.png")).getImage(); // adds an image icon to the "Instructions" menu tab item
		mntmInstructions.setIcon(new ImageIcon(icon2));
		mntmInstructions.addActionListener(new ActionListener() { 		// when the Menu tab option instructions has been clicked, it open the instructions text file in notepad.
			public void actionPerformed(ActionEvent arg0) {
				ProcessBuilder Process = new ProcessBuilder("Notepad.exe", "instructions.txt");
				try {
					Process.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		mnNewMenu_1.add(mntmInstructions);
		
		JMenu mnNewMenu = new JMenu("System");
		menuBar.add(mnNewMenu);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		Image icon = new ImageIcon(this.getClass().getResource("/logout.png")).getImage(); // adds an image icon to the "logout" menu tab item
		mntmLogout.setIcon(new ImageIcon(icon));
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { // when the logout menu tab option is clicked, it dispose the current frame and opens the login screen/window 
				dispose();
				Login tmp = new Login();
				tmp.frmLogin.setVisible(true);
				tmp.frmLogin.setLocationRelativeTo(null);
				
			}
		});
		mnNewMenu.add(mntmLogout);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		Image icon5 = new ImageIcon(this.getClass().getResource("/exit.png")).getImage(); // adds an image icon to the "Exit" menu tab item
		mntmNewMenuItem.setIcon(new ImageIcon(icon5));
		mntmNewMenuItem.addActionListener(new ActionListener() {    // when the exit menu tab option is clicked, it exits the application
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you Sure you want to exit the Application");
			    if (response == JOptionPane.YES_OPTION) {
			    	System.exit(JFrame.EXIT_ON_CLOSE);					// dialog box that pops up confirming the exit option that initiated 
			    } else if (response == JOptionPane.NO_OPTION) {
	
			    }
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel Welcometxtpanel = new Panel();
		Welcometxtpanel.setBounds(10, 10, 588, 196);
		contentPane.add(Welcometxtpanel);
		Welcometxtpanel.setLayout(null);
																			// This sets an Welcome message to the user, with a short message regarding the toolkit and its functionality,
		JEditorPane dtrpnWelcomeToPrototype = new JEditorPane();         
		dtrpnWelcomeToPrototype.setEditable(false);
		dtrpnWelcomeToPrototype.setFont(new Font("Times New Roman", Font.BOLD, 14));
		dtrpnWelcomeToPrototype.setBackground(Color.LIGHT_GRAY);					
		dtrpnWelcomeToPrototype.setText("Welcome to the prototype version of the Open Source Mobie Forensic Tool Kit MobView. Which is a Forensic Toolkit made to aid in the analysis and Extraction of Data from an Android Mobile Phone. \r\n\r\nBefore you can you can use toolkit effectivley there has to be some criterias that has to be met to use the System without an defects. So please take this time to read the Instructionsmanual provided under the \"File\" tab on the Menu bar. Failing to follow the instructions   will result in the malfucntion of the tool.\r\n\r\nOnce the criteria's are Met you can Use the tool to aid you with youre investigation.");
		dtrpnWelcomeToPrototype.setBounds(10, 11, 568, 174);
		Welcometxtpanel.add(dtrpnWelcomeToPrototype);
		
		Panel selectfolderPanel = new Panel();
		selectfolderPanel.setBounds(52, 287, 546, 44);
		contentPane.add(selectfolderPanel);
		selectfolderPanel.setLayout(null);
		
		txtfeildFilepath = new JTextField();
		txtfeildFilepath.setBounds(170, 12, 366, 20);
		selectfolderPanel.add(txtfeildFilepath);
		txtfeildFilepath.setColumns(10);
		
		
		btnopen = new JButton("Select File Folder");
		btnopen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {  								// When the Select folder button is clicked, it open up the Filechooser method, which is a function that allows the GUI interface for folder selection
				try {
				fileSelector = new JFileChooser();
				fileSelector.setCurrentDirectory(new File("C:/"));
				fileSelector.setDialogTitle(SelectorTitle);
				fileSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileSelector.setAcceptAllFileFilterUsed(false);
				//
				if (fileSelector.showOpenDialog(btnopen) == JFileChooser.APPROVE_OPTION) { 		//if the method successfully chooses the folder then it updates the textfield
					path = fileSelector.getSelectedFile().getPath();
					path = path.replace("\\", "/");												//this replaces the forward slash with backward slashes, as java uses backward slash to traverse up the tree
					txtfeildFilepath.setText(path); 
	
				} else {
					JOptionPane.showMessageDialog(null, "Selection Cancelled"); 				// if the method is not successful then it shows the selection cancelled message
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnopen.setBounds(10, 11, 136, 23);
		selectfolderPanel.add(btnopen);
		

		btnproceed = new JButton("Proceed");
		btnproceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // When the Proceed button is clicked if the text field is not empty then it opens up the showfiles page and disposes of the current frame
				try {
	
					ShowFiles temp = new ShowFiles();

					
					if (txtfeildFilepath.getText().equals("") ){ // if the textfeild is empty then it prompts the user to specify a filepath
						
						JOptionPane.showMessageDialog(null, "Please Specify a folder");
					}
					else {  // if it is not empty that means a file path has been entered and it then opens up the ShowFiles class
						
				sqlConnection.class.newInstance(); 							// This creates an instance of the sqlConnection class
				sqlConnection.value = (txtfeildFilepath.getText()); 				 //This then updates the variable thats created inside the sqlConnection class
						
						temp.setVisible(true);
						temp.setLocationRelativeTo(null);
						dispose(); // closes the current frame
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				
				
			}
		});
		btnproceed.setBounds(461, 348, 89, 23);
		contentPane.add(btnproceed);
		
		btnExtract = new JButton("Image Files");
		btnExtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { // when the image files option is clicked it runs a .bat file that has been previously created, to extract specific files from an android phone using ADB
				
				try {
					Runtime.getRuntime().exec("C:/Users/anoop/workspace/MainInterface/run.bat"); 
					String Deafaultpath = "C:/ForensicsTest".replace("\\", "/");
					Runtime.getRuntime().exec("C:/Users/anoop/workspace/MainInterface/imagefiles.bat");
					txtfeildFilepath.setText(Deafaultpath);
					JOptionPane.showMessageDialog(null, "Files Extracted");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		btnExtract.setBounds(63, 258, 133, 23);
		contentPane.add(btnExtract);
	}
}
