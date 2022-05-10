package org.restapi.crud.customer.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {

	
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/customeradd", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
	
	
	
	public String readCustomers()
	{
			String output = "";
			
			try
			{
					Connection con = connect();
					
					if (con == null)
					{
							return "Error while connecting to the database for reading.";
					}
					
					// Prepare the html table to be displayed
					output = "<table class=\'table table-bordered\' border='1'><tr><th>ID</th>"
							+ "<th>Name</th>"
							+"<th>Email</th>"
							+ "<th>Contact Number</th>"
							+ "<th>Unit</th>"
							+ "<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from customer"; // table name customer
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					// iterate through the rows in the result set
					while (rs.next())
					{
							String docId = rs.getString("docId");
							String docName = rs.getString("docName");
							String docEmail = rs.getString("docEmail");
							String docContact = rs.getString("docContact");
							String docUnit = rs.getString("docUnit");
							
							
							// Add into the html table
							output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate' type='hidden' value='" + docId + "'>" + docId + "</td>";
							output += "<td>" + docName + "</td>";
							output += "<td>" + docEmail + "</td>";
							output += "<td>" + docContact + "</td>";
							output += "<td>" + docUnit + "</td>";
						
							
							// buttons
							output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-success'></td>"
								   + "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-itemid='"+ docId + "'></td></tr>";
					}
					con.close();
					// Complete the html table
					output += "</table>";
			}
			catch (Exception e)
			{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
			}
			return output;
	}
	
	
	 public String insertcustomers(String docId, String docName, String docEmail, String docContact,String docUnit ) {
			
			String output = "";
			
			try {
					Connection con = connect();

					if (con == null) {
						
						return "Error while connecting to the database";
						
					}

				// create a prepared statement
				String query = " insert into customer " + "(`docId`,`docName`,`docEmail`,`docContact`,`docUnit`)"
						+ " values (?,?,?,?,?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, docId);
				preparedStmt.setString(2, docName);
				preparedStmt.setString(3, docEmail);
				preparedStmt.setString(4, docContact);
				preparedStmt.setString(5, docUnit);
				

				// execute the statement
				preparedStmt.execute();
				con.close();

				String newdoc = readCustomers();
				output = "{\"status\":\"success\", \"data\": \"" + newdoc + "\"}";

			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"Error while inserting the Customer Deatils.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		}
	 
	 
	 public String updatecustomers(String docId,String docEmail, String docContact, String docUnit) {
			String output = "";
			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement
				String query = "update customer set docEmail = ?,docContact = ?,docUnit = ? where docId = ?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values

				preparedStmt.setString(1, docEmail);
				preparedStmt.setString(2, docContact);
				preparedStmt.setString(3, docUnit);
				
				preparedStmt.setString(4, docId);
				

				// execute the statement
				preparedStmt.execute();
				con.close();

				String newdoc = readCustomers();
				output = "{\"status\":\"success\", \"data\": \"" + newdoc + "\"}";

			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\":\"Error while updating the Customer Details.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		}
	 
	 
	 public String deleteCustomers(String docId) {
			
			String output = "";

			try {

					Connection con = connect();
		
					if (con == null) {
						return "Error while connecting to the database for deleting.";
					}
		
					// create a prepared statement
					String query = "delete from customer where docId=?";
					
					PreparedStatement preparedStmt = con.prepareStatement(query);
					// binding values
					preparedStmt.setString(1, docId);
		
					// execute the statement
					preparedStmt.execute();
					con.close();
					
					String newdoc = readCustomers();
					output = "{\"status\":\"success\", \"data\": \"" + newdoc + "\"}";
			}		
			catch (Exception e)
			{
					output = "{\"status\":\"error\", \"data\":\"Error while deleting a Customer.\"}";
					System.err.println(e.getMessage());
			}
			
			return output;
		}
}
