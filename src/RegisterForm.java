
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;										// Importing all the necessary classes and functions
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class RegisterForm extends JDialog {
													//declaring the global Variables to be used within JDialog.
	private final JPanel contentPanel = new JPanel();
	private JTextField nameTxtfield;
	private JTextField surnametxtField;
	private JTextField agetxtField;
	private JTextField UNtxtField;
	private JPasswordField passField;
	private JPasswordField conpassField;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel validcheck;
	private JLabel UNcheck;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegisterForm dialog = new RegisterForm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(null);  // Setting the Screen to be placed at the centre of the screen
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	Connection connection = null; //clearing any existing connection made to the SQL database by setting its value null.
	private JLabel lblregister;

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("deprecation")
	public RegisterForm() {
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setTitle("Sign Up");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(100, 100, 513, 394);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		{
			JLabel label = new JLabel("Name:");
			label.setBounds(85, 63, 114, 28);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("Surname");
			label.setBounds(85, 108, 114, 25);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("Username:");
			label.setBounds(85, 192, 114, 25);
			contentPanel.add(label);
		}
		{
			nameTxtfield = new JTextField();
			nameTxtfield.setColumns(10);
			nameTxtfield.setBounds(199, 65, 200, 25);
			contentPanel.add(nameTxtfield);
		}
		{
			JLabel label = new JLabel("Password:");
			label.setBounds(85, 238, 114, 25);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("Confirm Password:");
			label.setBounds(85, 277, 114, 24);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("Age");
			label.setBounds(85, 150, 114, 25);
			contentPanel.add(label);
		}
		{
			surnametxtField = new JTextField();
			surnametxtField.setColumns(10);
			surnametxtField.setBounds(199, 108, 200, 25);
			contentPanel.add(surnametxtField);
		}
		{
			agetxtField = new JTextField();
			agetxtField.setColumns(10);
			agetxtField.setBounds(199, 150, 200, 25);
			contentPanel.add(agetxtField);
		}
		{
			UNtxtField = new JTextField();
			UNtxtField.setColumns(10);
			UNtxtField.setBounds(199, 192, 200, 25);
			contentPanel.add(UNtxtField);
		}
		{
			passField = new JPasswordField();
			passField.setBounds(199, 236, 200, 28);
			contentPanel.add(passField);
		}
		{
			conpassField = new JPasswordField();
			conpassField.setBounds(199, 275, 200, 28);
			contentPanel.add(conpassField);
		}
		
		{
			validcheck = new JLabel("Password doesnt Match, Check and re-enter data");
			validcheck.setForeground(Color.RED);
			validcheck.setBounds(170, 304, 303, 28);
			contentPanel.add(validcheck);

			validcheck.hide();
		}
		
		
			{
				lblregister = new JLabel("Complete the following Sections to contine");
				Image img = new ImageIcon(this.getClass().getResource("/register.png")).getImage(); // This is what sets the at the image at the login page, by inserting an image into the JLabel
				lblregister.setIcon(new ImageIcon(img));
				lblregister.setBackground(Color.WHITE);
				lblregister.setForeground(SystemColor.textHighlight);
				lblregister.setFont(new Font("Tahoma", Font.BOLD, 12));
				lblregister.setBounds(57, 11, 371, 51);
				contentPanel.add(lblregister);
			}
			
			UNcheck = new JLabel("Sorry, That username is taken");
			UNcheck.setForeground(Color.RED);
			UNcheck.hide();
			UNcheck.setBounds(262, 209, 185, 28);
			contentPanel.add(UNcheck);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Register");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) { // when the register button is clicked it executes the following fuctions
						
						
						
				try{
					connection =sqlConnection.loginconnector();
					{
						String Query = "Select * From LoginData Where username=?"; // creating the sql query thats going to be used in the prepared statement 
						PreparedStatement pst = connection.prepareStatement(Query);				 		
						pst.setString(1,UNtxtField.getText());							// This query gets the values for Username 	
						ResultSet rs = pst.executeQuery();								// this sets the Varribles 'rs' resultset to equals the value from the result of the query executed 
						
						int count = 0;
						
						while(rs.next()){   // this loops through all the data in the database until it reaches the end then the while statement is satisfied
							count++;   // increments the variable count by 1
							
						} 
						
						if(count == 1) 	{			// if the count value is 1 this means, the username value already exists in the database, show it shows an error messagelabel on the screen.
							UNcheck.show();
							validcheck.hide();

						}
						else {						// otherwise it means the username does not exists in the databse therefore, the user is able to use that username and it runs the rest of the code
							UNcheck.hide();
							if(passField.getText().equals(conpassField.getText())) // it check if the both the passwords match within the fields given
						{
							
							
							try
							
							{
								String Query2 = "Insert into LoginData (Name,Surname,Age,username,password) values (?,?,?,?,?)"; // "values (????)" means the value is going be entered at a later stage
								PreparedStatement pst2 = connection.prepareStatement(Query2); // created a variable called pst and made it equals the Pre-defined statement (prepared Statement)
								pst2.setString(1,nameTxtfield.getText());  
								pst2.setString(2,surnametxtField.getText() );     // sets the values within the query
								pst2.setString(3,agetxtField.getText() );
								pst2.setString(4,UNtxtField.getText() );
								pst2.setString(5,passField.getText());
	
								pst2.execute(); // this executes the prepared statement
								pst2.close();	// this closes the connection
								
								dispose(); // this closes the dialogbox
								JOptionPane.showMessageDialog(null, "Registered"); // shows a dialog box indicating the successful registration
								

							}
						    catch(Exception e1)   // any errors while executing the function with pop uo a dialog box showing the error.
							{
								JOptionPane.showMessageDialog(null, e1);

							}
							validcheck.hide();
							} 
							else{
							validcheck.show(); // if statement not satisfied then it means the passwords doesn't match and there for shows the error message within the dialog box
							}
						}
				}
					}catch(Exception e2)
				
				{
					
					JOptionPane.showMessageDialog(null, e2);
				}							
		
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose(); // closes the dialog box when the cancel button is clicked
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				
				buttonPane.add(cancelButton);
			}
		}
	}
}
