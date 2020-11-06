package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	
	static final String URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
	static final String USERNAME = "mayero";
	static final String PASSWD = "mayero";
	private static Connection conn;

	public static Connection getInstance() throws BDDException {
		if(conn == null){
			initConnection();
		} else {
			try {
				if (!conn.isValid(60)) {
					initConnection();
				}
			} catch (SQLException e) {
				System.out.println("Connection failed!");
				e.printStackTrace();
			}
		}

		if(conn == null){
			throw new BDDException("Couldn't establish connection");
		}
		return conn;
	}
	
	private static void initConnection() {
		
		try {
    		
    		// Chargement du driver Oracle
    		System.out.print("Loading Oracle driver... "); 
    		
    		//original line for calling the driver, but it was not recognized by eclipse
    		//DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    		
    		//new line that eclipse recognizes
    		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    		
    		System.out.println("loaded");
    		
    		// Etablissement de la connection
    		System.out.print("Connecting to the database... ");
    		conn = DriverManager.getConnection(URL, USERNAME, PASSWD);
    		System.out.println("connected");
    		
    	} catch (SQLException e) {
    		System.err.println("failed");
    		e.printStackTrace(System.err);
    	}
		
	}
	
	
	public static void closeConnexion() {
		
		try {
			
			System.out.print("Closing connection...");
			conn.close();
			System.out.print("Connexion closed successfuly");
			
		} catch (SQLException e) {
			
			System.out.print("failed");
			e.printStackTrace();
		
		}
	}
	
	
	
}
