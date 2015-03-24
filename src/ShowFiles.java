import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;				//Importing all the necessary classes and functions
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import javax.swing.ScrollPaneConstants;



public class ShowFiles extends JFrame {
												// Initialising and declaring the global Variables to be used within the frame.
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField searchtextField;
	private JComboBox<String> comboBoxselect;
	private JTextField Titlelable;
	private JTextArea keyAreaFeild ;
	private JTextField textFielddate;
	JFileChooser chooser = new JFileChooser();
	private JMenuBar menuBar;
	private JButton btnContactload;			
	private JButton btncallsload;
	private JButton btnEmail;
	private JButton btnGmails;
	private JButton btnMessages;
	private JButton btnCalender;
	private JButton btnMemo;
	private JButton btnDownloads;
	private JMenu mnNewMenu_1;
	private JMenuItem mntmExportFile;
	private JMenu mnNewMenu;
	private JMenuItem mntmBack;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JTextField textFieldtime;
	private JMenuItem mntmInstructions;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowFiles frame = new ShowFiles();
					frame.setVisible(true);					// setting the frame visible
					frame.setLocationRelativeTo(null);		// Setting the screen to be placed at the centre of the screen

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;	//clearing any existing connection made to the SQL database by setting its value equal null.

	public void clock()   // This contains a simple method thats been implemented to show the live date and time on the screen
	{
		Thread Tclock = new Thread()
		{
			public void run()
			{
				try {
					while(true){ // while loop runs an infinite loop, thats how the clock value is updated as the loop is never stopped until the application is closed
					Calendar cal = new GregorianCalendar();
					int day = cal.get(Calendar.DAY_OF_MONTH);
					int month = cal.get(Calendar.MONTH);
					int year = cal.get(Calendar.YEAR);
					int second = cal.get(Calendar.SECOND);
					int minute = cal.get(Calendar.MINUTE);
					int hour = cal.get(Calendar.HOUR_OF_DAY);
					
					textFielddate.setText("Current Date: "+day+"/"+month+"/"+year); // Setting the value to the textfeild that shows the current date
					textFieldtime.setText("Time: "+hour+":"+minute+":"+second); // setting the value to the textfeild that shows live current time
					
					sleep(1000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
			

		};
		
		Tclock.start();	
	}
	

	@SuppressWarnings("serial")
	public ShowFiles() {
		setTitle("Database File Explorer");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 475);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(167, 62, 567, 344);
		getContentPane().add(scrollPane);
											// This shows the key within the textarea
		keyAreaFeild = new JTextArea();
		keyAreaFeild.setEditable(false);
		keyAreaFeild.setToolTipText("What the Type column Represent\r\n in calls and messages tables");
		keyAreaFeild.setBackground(Color.LIGHT_GRAY);
		keyAreaFeild.setForeground(Color.BLACK);
		keyAreaFeild.setFont(new Font("Monospaced", Font.BOLD, 11));
		keyAreaFeild.setText("\tKey\r\nType 1 = Received\r\nType 2 = Dialed/Sent\r\nType 3 = Missed/Draft\r\nType 5 = Automated");
		keyAreaFeild.setBounds(10, 11, 151, 84);
		getContentPane().add(keyAreaFeild);
		
		table = new JTable() {
		    public boolean isCellEditable(int row, int column) {		// this makes it so the user can select the data within the table but unable to edit it
		       return false;
		    }
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);		
		table.setEnabled(true);
		scrollPane.setViewportView(table);					// creating the table that shows the data from the files
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);

		Titlelable = new JTextField();
		Titlelable.setEditable(false);
		Titlelable.setBounds(167, 41, 86, 20);
		getContentPane().add(Titlelable);
		Titlelable.setColumns(10);

		searchtextField = new JTextField();
		searchtextField.setToolTipText("Enter your search query here");
		searchtextField.setBounds(516, 28, 119, 23);
		getContentPane().add(searchtextField);
		searchtextField.setColumns(10);
		
		comboBoxselect = new JComboBox<String>();
		comboBoxselect.setToolTipText("Search Table Colomun selector");
		comboBoxselect.setBounds(326, 31, 170, 20);
		getContentPane().add(comboBoxselect);
		

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {		// the following section contains the search function
			public void actionPerformed(ActionEvent arg0) {
				connection = null;
				String searchvalue = Titlelable.getText();
				
		switch (searchvalue){	// creating a switch statement to use for the different search tables
				
				case "Calls":				// this case is executed when the user searches while viewing the calls table
				connection = sqlConnection.logsdb();
					try
					{
					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
					String selection=(String)comboBoxselect.getSelectedItem();
					String query = ("SELECT name as Name, type as Type, duration as Duration, normalized_number as Number,"
							+ " strftime('%d/%m/%Y Time: %H:%M:%S', date/1000, 'unixepoch') as Call_Date "
							+ "FROM logs "
							+ "WHERE logs.logtype = '100' and "+selection+" like ?");
					
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1,"%"+ searchtextField.getText() + "%");
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					rs.close();
					pst.close();
					break;
					}	
					catch (Exception ex){
					ex.printStackTrace();
					break;
					}
					
				case "Contacts":   // this case is executed when the user searches while viewing the contacts table
					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
					connection = sqlConnection.Contactsdb();
					try
					{
						
						String selection=(String)comboBoxselect.getSelectedItem();
						String Query = ("SELECT display_name as Contact_Name, times_contacted as Times_Contacted,strftime('%d/%m/%Y Time: %H:%M:%S', last_time_contacted/1000, 'unixepoch') AS Date_LastContacted, phone_lookup.normalized_number as Phone_Number "
								+ "FROM raw_contacts, phone_lookup "
								+ "WHERE raw_contacts._id = phone_lookup.raw_contact_id and "+selection+" like ?");
						PreparedStatement pst = connection.prepareStatement(Query);
						pst.setString(1,"%"+ searchtextField.getText() + "%");
						ResultSet rs = pst.executeQuery();

						table.setModel(DbUtils.resultSetToTableModel(rs));
						
						rs.close();
						pst.close();
						break;
					}catch (Exception ex){
					ex.printStackTrace();
					break;
										}
				case "Messages":	// this case is executed when the user searches while viewing the Messages table
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
					connection = sqlConnection.Messagesdb();
					try
					{
						String selection=(String)comboBoxselect.getSelectedItem();
						String query = ("SELECT address as Address, body as Message, strftime('%d/%m/%Y Time: %H:%M:%S', date/1000, 'unixepoch') as Message_Date, type as Type "
								+ "FROM sms "
								+ "WHERE "+selection+" like ?");
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1,"%"+ searchtextField.getText() + "%");
						
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						table.getColumnModel().getColumn(0).setPreferredWidth(145);
						table.getColumnModel().getColumn(1).setPreferredWidth(200);
						table.getColumnModel().getColumn(2).setPreferredWidth(150);
						table.getColumnModel().getColumn(3).setPreferredWidth(54);
						
						rs.close();
						pst.close();
						break;
				}catch (Exception ex){
					ex.printStackTrace();
						break;	
				}
				
				case "Gmails":	// this case is executed when the user searches while viewing the Gmail table
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					connection = sqlConnection.Gmaildb();
					try
					{
						String selection=(String)comboBoxselect.getSelectedItem();
						String query = ("select fromAddress as Sender,toAddresses as Receiver, strftime('%d/%m/%Y Time: %H:%M:%S', dateSentMs/1000, 'unixepoch') AS Datesent,strftime('%H:%M:%S %d/%m/%Y', dateReceivedMs/1000, 'unixepoch') AS Datereceived, subject as Subject From messages WHERE "+selection+" like ?");
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1,"%"+ searchtextField.getText() + "%");
						
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						table.getColumnModel().getColumn(0).setPreferredWidth(100);
						table.getColumnModel().getColumn(1).setPreferredWidth(100);
						table.getColumnModel().getColumn(2).setPreferredWidth(110);
						table.getColumnModel().getColumn(3).setPreferredWidth(110);
						table.getColumnModel().getColumn(4).setPreferredWidth(129);
						
						rs.close();
						pst.close();
						break;
				}catch (Exception ex){
					ex.printStackTrace();
						break;
				}
			
			case "Memo/Notes": 	// this case is executed when the user searches while viewing the Memo/Notes table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
				connection = sqlConnection.Memodb();
				try
				{
					String selection=(String)comboBoxselect.getSelectedItem();
					String query = ("select title as Title, strippedContent as Content, strftime('%d/%m/%Y Time: %H:%M:%S', createdAt/1000, 'unixepoch') AS Datecreated,strftime('%d/%m/%Y Time: %H:%M:%S', lastModifiedAt/1000, 'unixepoch') AS Last_ModifiedDate from memo WHERE "+selection+" like ?");
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1,"%"+ searchtextField.getText() + "%");
					
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					rs.close();
					pst.close();
					break;
			}catch (Exception ex){
				ex.printStackTrace();
					break;
			}
				
		    case "Calendar":		// this case is executed when the user searches while viewing the Calendar table
		    	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
				connection = sqlConnection.Calenderdb();
				try
				{
					String selection=(String)comboBoxselect.getSelectedItem();
					String query = ("select title as Title, organizer as Organizer from Events WHERE "+selection+" like ?");
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1,"%"+ searchtextField.getText() + "%");
					
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					rs.close();
					pst.close();
					break;
			}catch (Exception ex){
				ex.printStackTrace();
					break;
			}
			case "Downloads":		// this case is executed when the user searches while viewing the Downloads table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
				connection = sqlConnection.Downloadsdb();
				try
				{
					String selection=(String)comboBoxselect.getSelectedItem();
					String query = ("select uri as Source,title as File_Name, strftime('%d/%m/%Y Time: %H:%M:%S', lastmod/1000, 'unixepoch') AS Downloaded_Date from downloads WHERE "+selection+" like ?");
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1,"%"+ searchtextField.getText() + "%");
			
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					rs.close();
					pst.close();
					break;
			}catch (Exception ex){
				ex.printStackTrace();
					break;
			}
			
			case "Email":  // this case is executed when the user searches while viewing the Email table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
				connection = sqlConnection.Emaildb();
				try
				{
					String selection=(String)comboBoxselect.getSelectedItem();
					String query = ("Select subject as Subject, strftime('%d/%m/%Y Time: %H:%M:%S', timeStamp/1000, 'unixepoch') as Sent_date, fromList as Sender, toList as Receiver, snippet as Message_content From Message WHERE "+selection+" like ?");
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1,"%"+ searchtextField.getText() + "%");
			
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table.getColumnModel().getColumn(0).setPreferredWidth(100);
					table.getColumnModel().getColumn(1).setPreferredWidth(100);
					table.getColumnModel().getColumn(2).setPreferredWidth(110);
					table.getColumnModel().getColumn(3).setPreferredWidth(110);
					table.getColumnModel().getColumn(4).setPreferredWidth(129);
					
					rs.close();
					pst.close();
					break;
			}catch (Exception ex){
				ex.printStackTrace();
					break;
			}
				}

				
		}});
		btnSearch.setBounds(645, 28, 89, 23);
		getContentPane().add(btnSearch);
		
		
		
		btnContactload = new JButton("Contacts");
		btnContactload.setToolTipText("Click to load contacts");
		btnContactload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			// the following code executes when the user clicks the contacts button, it extracts the information from contacts .db file and shows it in the table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				Titlelable.setText( "Contacts" );
				comboBoxselect.removeAllItems();
				comboBoxselect.addItem("Contact_Name");
				comboBoxselect.addItem("Times_Contacted");
				comboBoxselect.addItem("Date_LastContacted");
				comboBoxselect.addItem("Phone_Number");
				
				connection = sqlConnection.Contactsdb();
				try {
					String query = ("SELECT display_name as Contact_Name, times_contacted as Times_Contacted,strftime('%d/%m/%Y Time: %H:%M:%S', last_time_contacted/1000, 'unixepoch') AS Date_LastContacted, phone_lookup.normalized_number as Phone_Number "
							+ "FROM raw_contacts, phone_lookup "
							+ "WHERE raw_contacts._id = phone_lookup.raw_contact_id ");	// selecting specific information to be extracted from the .db file
					
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));

					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
			}
		});
		btnContactload.setBounds(39, 106, 100, 23);
		getContentPane().add(btnContactload);
		

		
		
		btncallsload = new JButton("Calls");
		btncallsload.setToolTipText("Click to load Calls");
		btncallsload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	// the following code executes when the user clicks the calls button, it extracts the information from calls .db file and shows it in the table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
				comboBoxselect.removeAllItems();
				comboBoxselect.addItem("Name");
				comboBoxselect.addItem("Type");
				comboBoxselect.addItem("Duration");
				comboBoxselect.addItem("Number");
				comboBoxselect.addItem("Call_Date");
				
				Titlelable.setText( "Calls" );
				connection = sqlConnection.logsdb();
				try {
					String query = ("SELECT name as Name, type as Type, duration as Duration,"
							+ " normalized_number as Number, strftime('%d/%m/%Y Time: %H:%M:%S', date/1000, 'unixepoch') as Call_Date "
							+ "FROM logs "				// selecting specific information to be extracted from the .db file
							+ "WHERE logtype = '100' ");
				
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
		});
		btncallsload.setBounds(39, 138, 100, 23);
		getContentPane().add(btncallsload);
		
		
		btnEmail = new JButton("Email"); // the following code executes when the user clicks the Email button, it extracts the information from email .db file and shows it in the table
		btnEmail.setToolTipText("Click to load Emails");
		btnEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
				
				comboBoxselect.removeAllItems();
				comboBoxselect.addItem("Subject");
				comboBoxselect.addItem("Sent_date");
				comboBoxselect.addItem("Sender");
				comboBoxselect.addItem("Receiver");
				comboBoxselect.addItem("Message_content");
				
				Titlelable.setText( "Email" );
				connection = sqlConnection.Emaildb();
				try {					// selecting specific information to be extracted from the .db file
					String query = ("Select subject as Subject, strftime('%d/%m/%Y Time: %H:%M:%S', timeStamp/1000, 'unixepoch') as Sent_date, fromList as Sender, toList as Receiver, snippet as Message_content From Message");
					
					
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table.getColumnModel().getColumn(0).setPreferredWidth(100);
					table.getColumnModel().getColumn(1).setPreferredWidth(100);
					table.getColumnModel().getColumn(2).setPreferredWidth(110);
					table.getColumnModel().getColumn(3).setPreferredWidth(110);
					table.getColumnModel().getColumn(4).setPreferredWidth(129);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
			
		});
		btnEmail.setBounds(39, 204, 100, 23);
		getContentPane().add(btnEmail);
		
		btnGmails = new JButton("Gmails"); // the following code executes when the user clicks the Gmail button, it extracts the information from Gmail .db file and shows it in the table
		btnGmails.setToolTipText("Click to load Gmails");
		btnGmails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
				comboBoxselect.removeAllItems();
				comboBoxselect.addItem("Sender");
				comboBoxselect.addItem("Receiver");
				comboBoxselect.addItem("Datesent");
				comboBoxselect.addItem("Datereceived");
				comboBoxselect.addItem("Subject");
				
				Titlelable.setText( "Gmails" );
				connection = sqlConnection.Gmaildb();
				try {					// selecting specific information to be extracted from the .db file
					String query = ("select fromAddress as Sender,toAddresses as Receiver, strftime('%d/%m/%Y Time: %H:%M:%S', dateSentMs/1000, 'unixepoch') AS Datesent,strftime('%H:%M:%S %d/%m/%Y', dateReceivedMs/1000, 'unixepoch') AS Datereceived, subject as Subject from messages");
					
					
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table.getColumnModel().getColumn(0).setPreferredWidth(100);
					table.getColumnModel().getColumn(1).setPreferredWidth(100);
					table.getColumnModel().getColumn(2).setPreferredWidth(110);
					table.getColumnModel().getColumn(3).setPreferredWidth(110);
					table.getColumnModel().getColumn(4).setPreferredWidth(129);
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGmails.setBounds(39, 234, 100, 23);
		getContentPane().add(btnGmails);
		
		
		btnMessages = new JButton("Messages");
		btnMessages.setToolTipText("Click to load Messages");
		btnMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// the following code executes when the user clicks the Messages button, it extracts the information from Messages .db file and shows it in the table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
				comboBoxselect.removeAllItems();
				comboBoxselect.addItem("Address");
				comboBoxselect.addItem("Message");
				comboBoxselect.addItem("Message_Date");
				comboBoxselect.addItem("Type");
				
				Titlelable.setText( "Messages" );
				connection = sqlConnection.Messagesdb();
				try {
					String query = ("SELECT address as Address, body as Message,"
							+ " strftime('%d/%m/%Y Time: %H:%M:%S', date/1000, 'unixepoch') as Message_Date, type as Type "
							+ "FROM sms ");		// selecting specific information to be extracted from the .db file
					
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table.getColumnModel().getColumn(0).setPreferredWidth(145);
					table.getColumnModel().getColumn(1).setPreferredWidth(200);
					table.getColumnModel().getColumn(2).setPreferredWidth(150);
					table.getColumnModel().getColumn(3).setPreferredWidth(54);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		btnMessages.setBounds(39, 170, 100, 23);
		getContentPane().add(btnMessages);
		
		
		btnCalender = new JButton("Calendar");
		btnCalender.setToolTipText("Click to load Calendar Events");
		btnCalender.addActionListener(new ActionListener() {	// the following code executes when the user clicks the Calendar button, it extracts the information from calendar .db file and shows it in the table
			public void actionPerformed(ActionEvent e) {
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
				comboBoxselect.removeAllItems();
				comboBoxselect.addItem("Title");
				comboBoxselect.addItem("Organizer");

				
				Titlelable.setText( "Calendar" );
				connection = sqlConnection.Calenderdb();
				try {
					String query = ("select title as Title, organizer as Organizer from Events");
		
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnCalender.setBounds(39, 264, 100, 23);
		getContentPane().add(btnCalender);
		
		btnMemo = new JButton("Memo/Notes");
		btnMemo.setToolTipText("Click to load Memo/Notes");
		btnMemo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	// the following code executes when the user clicks the Memo/notes button, it extracts the information from Memo/notes .db file and shows it in the table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
				comboBoxselect.removeAllItems();
				comboBoxselect.addItem("Title");
				comboBoxselect.addItem("Content");
				comboBoxselect.addItem("Datecreated");
				comboBoxselect.addItem("Last_ModifiedDate");
				
				Titlelable.setText( "Memo/Notes" );
				connection = sqlConnection.Memodb();
				try {						// selecting specific information to be extracted from the .db file
					String query =("select title as Title, strippedContent as Content, strftime('%d/%m/%Y Time: %H:%M:%S', createdAt/1000, 'unixepoch') AS Datecreated,strftime('%d/%m/%Y Time: %H:%M:%S', lastModifiedAt/1000, 'unixepoch') AS Last_ModifiedDate from memo");
		
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnMemo.setBounds(39, 298, 100, 23);
		getContentPane().add(btnMemo);
		
		btnDownloads = new JButton("Downloads");
		btnDownloads.setToolTipText("Click to load Downloadsfiles");
		btnDownloads.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// the following code executes when the user clicks the downloads button, it extracts the information from downloads .db file and shows it in the table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	
				comboBoxselect.removeAllItems();
				comboBoxselect.addItem("Source");
				comboBoxselect.addItem("File_Name");
				comboBoxselect.addItem("Downloaded_Date");
				
				Titlelable.setText( "Downloads" );
				connection = sqlConnection.Downloadsdb();
				try {							// selecting specific information to be extracted from the .db file
					String query = ("select uri as Source,title as File_Name, strftime('%d/%m/%Y Time: %H:%M:%S', lastmod/1000, 'unixepoch') AS Downloaded_Date from downloads");
		
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDownloads.setBounds(39, 330, 100, 23);
		getContentPane().add(btnDownloads);
		
		textFielddate = new JTextField();
		textFielddate.setBackground(Color.LIGHT_GRAY);
		textFielddate.setFont(new Font("Tahoma", Font.BOLD, 11));
		textFielddate.setText("Date");
		textFielddate.setEditable(false);
		textFielddate.setBounds(245, -2, 143, 22);
		getContentPane().add(textFielddate);
		textFielddate.setColumns(10);
		
		textFieldtime = new JTextField();
		textFieldtime.setBackground(Color.LIGHT_GRAY);
		textFieldtime.setFont(new Font("Tahoma", Font.BOLD, 11));
		textFieldtime.setText("Time");
		textFieldtime.setEditable(false);
		textFieldtime.setBounds(387, -2, 111, 22);
		getContentPane().add(textFieldtime);
		textFieldtime.setColumns(10);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu_1 = new JMenu("File");
		menuBar.add(mnNewMenu_1);
		
		mntmExportFile = new JMenuItem("Save File");
		Image icon = new ImageIcon(this.getClass().getResource("/save.png")).getImage(); // adds an image icon to the "Export file" menu tab item
		mntmExportFile.setIcon(new ImageIcon(icon));
		mntmExportFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			// the following code is executed when the user clicks save, it writes the information within the table into .xls file

				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new File("C:/"));
			    int select = chooser.showSaveDialog(null);
			    if (select == JFileChooser.APPROVE_OPTION) 
			    {
					try {
						
						String file = (".xls"); 
						TableModel model = table.getModel();	
						FileWriter out = new FileWriter(chooser.getSelectedFile()+file);
						
						for(int i=0; i < model.getColumnCount(); i++) {  					 // goes through each coloumn and gets the data 
		                out.write(model.getColumnName(i) + "\t");
						}
						out.write("\n");														// this writes the data to the file

						for(int i=0; i< model.getRowCount(); i++) {
					for(int j=0; j < model.getColumnCount(); j++) {									// goes through each row and gets the data 
						out.write(model.getValueAt(i,j)+"\t");
						}
						out.write("\n");														// this writes the data to the file
					   }
						out.close();								
					
					JOptionPane.showMessageDialog(null, "File Saved");
					
						} catch (IOException ex) {
							System.out.println(ex);
						}	
			    	}
			    else {																			// if the chooser API is cancelled then a dialog message is shown saying save cancelled
			    	JOptionPane.showMessageDialog(null, "Save Cancelled");
			    	}
				}

				});
		mnNewMenu_1.add(mntmExportFile);
		
		mntmInstructions = new JMenuItem("Instructions");
		Image icon2 = new ImageIcon(this.getClass().getResource("/manual.png")).getImage(); // adds an image icon to the "Instructions" menu tab item
		mntmInstructions.setIcon(new ImageIcon(icon2));
		mntmInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcessBuilder Process = new ProcessBuilder("Notepad.exe", "instructions.txt");
				try {
					Process.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu_1.add(mntmInstructions);
		
		mnNewMenu = new JMenu("System");
		menuBar.add(mnNewMenu);
		
		mntmBack = new JMenuItem("Back");
		Image icon3 = new ImageIcon(this.getClass().getResource("/back.png")).getImage(); // adds an image icon to the "Back" menu tab item
		mntmBack.setIcon(new ImageIcon(icon3));
		mntmBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();					// closes the current window and opens the welcome screen
				Welcomscreen temp = new Welcomscreen();
				temp.setVisible(true);						
				temp.setLocationRelativeTo(null);
			}
		});
		mnNewMenu.add(mntmBack);
		
		mntmNewMenuItem = new JMenuItem("Logout");
		Image icon4 = new ImageIcon(this.getClass().getResource("/logout.png")).getImage(); // adds an image icon to the "logout" menu tab item
		mntmNewMenuItem.setIcon(new ImageIcon(icon4));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();				// closes the current window and opens the login screen
				Login tmp = new Login();
				tmp.frmLogin.setVisible(true);
				tmp.frmLogin.setLocationRelativeTo(null);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("Exit");
		Image icon5 = new ImageIcon(this.getClass().getResource("/exit.png")).getImage(); // adds an image icon to the "Exit" menu tab item
		mntmNewMenuItem_1.setIcon(new ImageIcon(icon5));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you Sure you want to exit the Application");
			    if (response == JOptionPane.YES_OPTION) {
			    	System.exit(JFrame.EXIT_ON_CLOSE);
			    } else if (response == JOptionPane.NO_OPTION) {
	
			    }
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		
		clock();

	}
}
