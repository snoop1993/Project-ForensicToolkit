import java.awt.EventQueue;
import java.awt.Image;
												
import javax.swing.ImageIcon;
//Importing all the necessary classes and functions
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.sql.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;



public class Login {

	                                        // Initialising and declaring the global Variables to be used within the application.
											//JFrame is made public as it is accessed in other forms where as the rest of the components is made private
	JFrame frmLogin;
	private JTextField textFieldusername;
	private JPasswordField passwordField;
	private JLabel imagelabel1;
	private JButton registerbtn;
	private JLabel lblClickHereTo;
	private JMenuItem mntmNewMenuItem;
	private JButton btnLogin;
	private JMenuBar menuBar;
	private JMenu mnSystem;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmLogin.setVisible(true);				// setting the window visible
					window.frmLogin.setLocationRelativeTo(null);   // Setting the Screen to be placed at the centre of the screen
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	Connection connection = null;   //clearing any existing connection made to the SQL database by setting its value null.



	public Login() {
		initialize();	
		connection =sqlConnection.loginconnector(); // This establishes a connection with the chosen database(declared in another class called 'Loginconnector')
		
	}
	

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
																	// This is where the buttons and other GUI features and their actions created, the global components have been previously declared at the start of file.
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 587, 343);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frmLogin.getContentPane().setLayout(null);
		
		
		
		JLabel lblNewLabel = new JLabel("UserName:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));  
		lblNewLabel.setBounds(186, 69, 105, 14);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(186, 115, 83, 14);
		frmLogin.getContentPane().add(lblNewLabel_1);

		textFieldusername = new JTextField();
		textFieldusername.setBounds(295, 68, 147, 23);
		frmLogin.getContentPane().add(textFieldusername);
		textFieldusername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(295, 113, 147, 23);
		frmLogin.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		btnLogin = new JButton("  Login");
		Image icon = new ImageIcon(this.getClass().getResource("/tick.png")).getImage(); // adds an image icon to the login button
		btnLogin.setIcon(new ImageIcon(icon));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {      // When The Login button is Clicked it carries out the declared function in this section
				try
				
				{
					String Query = "Select * From LoginData Where username=? and password=? ";
					PreparedStatement pst = connection.prepareStatement(Query);				 //	Making use of the PreparedStatement within the JDBC libraries to query with the connected SQL Database		
					pst.setString(1,textFieldusername.getText());							// This query gets the values for Username and password from the database	
					pst.setString(2,passwordField.getText() );
					ResultSet rs = pst.executeQuery();								// this sets the Varribles 'rs' resultset to equals the value from the result of the query executed 
					
					int count = 0;
					
					while(rs.next()){   // this loops through all the data in the database until it reaches the end then the while statement is satisfied
						count++;   // increments the variable count by 1	
					} 
					
					if(count == 1)  // the count value equals 1 then the it means the connection is successful, as the user name and the pass word is inside the database
					{
						JOptionPane.showMessageDialog(null, "Login Successfull");
						frmLogin.dispose();
						Welcomscreen temp = new Welcomscreen();
						temp.setVisible(true);
						temp.setLocationRelativeTo(null);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Login Details Incorrect"); // if the the count value equals any other value then the it means there are not match within the database, there for showing a dialogbox saying login details incorrect
					}
					rs.close();  // this closes the resultset connection
					pst.close(); // this closes the prepared statement connection
					
				    }
			catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);

				}
			}
		});
		btnLogin.setBounds(257, 168, 135, 33);
		frmLogin.getContentPane().add(btnLogin);
		
		imagelabel1 = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/Loginimg.png")).getImage(); // This is what sets the at the image at the login page, by inserting an image into the JLabel
		imagelabel1.setIcon(new ImageIcon(img));
		imagelabel1.setBounds(21, 28, 128, 161);
		frmLogin.getContentPane().add(imagelabel1);
		
		registerbtn = new JButton("Register");
		registerbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { // when the Register button is clicked it open up the Register page(dialogbox)
				
				RegisterForm temp = new RegisterForm();   
				temp.setLocationRelativeTo(null);
				temp.setVisible(true);


			}
		});
		registerbtn.setBounds(394, 256, 113, 23);
		frmLogin.getContentPane().add(registerbtn);
		
		lblClickHereTo = new JLabel("Not a member? Click Here to Register");
		lblClickHereTo.setBounds(348, 238, 227, 23);
		frmLogin.getContentPane().add(lblClickHereTo);
		
		menuBar = new JMenuBar();
		frmLogin.setJMenuBar(menuBar);
		
		mnSystem = new JMenu("System");
		menuBar.add(mnSystem);
		
		mntmNewMenuItem = new JMenuItem("Exit");
		Image icon1 = new ImageIcon(this.getClass().getResource("/exit.png")).getImage(); // adds an image icon to the "Exit" menu tab item
		mntmNewMenuItem.setIcon(new ImageIcon(icon1));
		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			// when the users clicks on the exit option within the menu tab, promotes a check to confirm this action.
				
				int response = JOptionPane.showConfirmDialog(null, "Are you Sure you want to exit the Application");
			    if (response == JOptionPane.YES_OPTION) {
			    	System.exit(JFrame.EXIT_ON_CLOSE);
			    } else if (response == JOptionPane.NO_OPTION) {
	
			    }
			}
		});
		mnSystem.add(mntmNewMenuItem);
		


		

	}
}
