import java.sql.*;
							// Importing all the necessary classes and functions
import javax.swing.*;

public class sqlConnection {

	 Connection connection = null; 	//clearing any existing connection made to the SQL database by setting its value equal null.
	 static String value = ""; 		// creating an empty string variable called value, this is updated within another frame
	

	public static Connection loginconnector()
	{
		  							// This class connects to the SQL Database File Logindata.sqlite and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			
			Connection connection = DriverManager.getConnection("jdbc:sqlite:C://Users/anoop/workspace/MainInterface/LoginData.sqlite");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static Connection Contactsdb()
	{							// This class connects to the SQL Database File contacts2.db and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+value+"/contacts2.db");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static Connection logsdb()
	{									// This class connects to the SQL Database File logs.db and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+value+"/logs.db");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static Connection Messagesdb()
	{									// This class connects to the SQL Database File mmssms.db and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+value+"/mmssms.db");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static Connection Emaildb()
	{									// This class connects to the SQL Database File EmailProvider.db and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+value+"/EmailProvider.db");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static Connection Gmaildb()
	{									// This class connects to the SQL Database File gmail.db and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+value+"/gmail.db");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static Connection Calenderdb()
	{						// This class connects to the SQL Database File calendar.db and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+value+"/calendar.db");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static Connection Memodb()
	{								// This class connects to the SQL Database File memo.db and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+value+"/memo.db");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static Connection Downloadsdb()
	{								// This class connects to the SQL Database File downloads.db and returns this connection when the class is called.
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+value+"/downloads.db");
			return connection;
		}
	catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
}
