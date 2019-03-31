import java.sql.*;

public class CreateTable {

	public static void main(String[] args) {
		
		createTable();
	}
	
	public static void createTable() {
		
		
		try {
			
			String sql = "";
			String currentDir = System.getProperty("user.dir");
			String databasePath = "jdbc:sqlite:" + currentDir + "/db/RecordTracker.db";
			Connection conn = DriverManager.getConnection(databasePath);
			
			
			sql = "CREATE TABLE IF NOT EXISTS good_table ( "
					+ "fname TEXT, "
					+ "lname TEXT, "
					+ "email TEXT, "
					+ "gender TEXT, "
					+ "pngFile TEXT, "
					+ "cardIss TEXT, "
					+ "amount TEXT, "
					+ "bool1 TEXT, "
					+ "bool2 TEXT, "
					+ "city TEXT); ";
					
			
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
