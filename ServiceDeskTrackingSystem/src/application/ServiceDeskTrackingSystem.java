package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ServiceDeskTrackingSystem {
	//databace connection
	private static Connection con;
	//boolean to verify that there is a table in the data base
	private static boolean hasData = false;
	

	// Method to display the database services
	public ResultSet displayServices() throws SQLException, ClassNotFoundException {

		//checking if there is a service to display
		if(con== null) {
			getConnection();
		}
		Statement state = con.createStatement();
		//selection the information to display
		ResultSet res = state.executeQuery("SELECT ID, dateOfRequest, requestDescription, serviceCompleted, dateOfCompletion, technicianName, notes FROM services");
		return res;
		
	}
	public ResultSet displayServicesByUserTechnicianName(String technician) throws SQLException, ClassNotFoundException {
		//checking if there is a service to display
		if(con== null) {
			getConnection();
		}
		Statement state = con.createStatement();
		//selection the information to display
		ResultSet res = state.executeQuery("SELECT ID, dateOfRequest, requestDescription, serviceCompleted, dateOfCompletion, technicianName, notes FROM services WHERE technicianName = '"+ technician+"'");
		return res;
		
	}
	public ResultSet getRecordID() throws SQLException, ClassNotFoundException{
		if(con== null) {
			getConnection();
		}
		Statement state = con.createStatement();
		//selection the information to display
		
		ResultSet res = state.executeQuery("SELECT COUNT(*) FROM services");
		
		return res;
		
	}
	public ResultSet displayServicesByRecordDate(String RecordDate) throws SQLException, ClassNotFoundException {
		//checking if there is a service to display
		if(con== null) {
			getConnection();
		}
		Statement state = con.createStatement();
		//selection the information to display
		ResultSet res = state.executeQuery("SELECT dateOfRequest, requestDescription, serviceCompleted, dateOfCompletion, technicianName, notes FROM services WHERE dateOfRequest = '"+ RecordDate+"'");
		return res;
		
	}
	//method to get the connection
	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:ServiceDeskAppDB.db");
		initialise();
	}
	
	//initializing database
	private void initialise() throws SQLException {
		if(!hasData) {
			hasData = true;
			//checking if there is table in the database
			Statement createDataBase = con.createStatement();
			ResultSet res = createDataBase.executeQuery("SELECT name FROM sqlite_master WHERE type= 'table' AND name ='services'");
			//if there is not a table in the database it will create one
			if( !res.next()) {
				
				Statement createTable = con.createStatement();
				
				
				createTable.execute("CREATE TABLE services(ID INTEGER PRIMARY KEY,"+"dateOfRequest varchar(10),"+"requestDescription varchar(128),"
				+ "serviceCompleted varchar(3),"+"dateOfCompletion varchar(10),"+"technicianName varchar(50),"+"notes varchar(120));");
				System.out.println("Building the user Database");

				
			}
		}
		
	}
	

	
	//method to add new services
	public void addService( Integer id, String dateOfRequest, String requestDescription, String serviceCompleted, String dateOfCompletion, String technicianName ,String notes) throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		
		PreparedStatement insert = con.prepareStatement("INSERT INTO services values(?,?,?,?,?,?,?);");

		insert.setInt(1, (id));
		insert.setString(2, dateOfRequest);
		insert.setString(2, dateOfRequest);
		insert.setString(3, requestDescription);
		insert.setString(4, serviceCompleted);
		insert.setString(5, dateOfCompletion);
		insert.setString(6, technicianName);
		insert.setString(7, notes);
		insert.execute();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
