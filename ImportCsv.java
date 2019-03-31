import java.sql.*;
import com.opencsv.*;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileSystemView;
import java.util.*;
import java.io.*;
import java.text.*;

public class ImportCsv {
	
	private static String databasePath = "jdbc:sqlite:mem:RecordTracker.db";
	static Connection conn;

	public static void main(String[] args) throws Exception {
		conn = DriverManager.getConnection(databasePath);
		createTable();
        readCsv();
        conn.close();
	}
	
	public static void readCsv() {
		
		String[] line;
		int countBlanks;
		String currentDir = System.getProperty("user.dir");
		long countBadRecords = 0;
		long countGoodRecords = 0;

		try {
			
			//Select import file
			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
			int returnFile = fileChooser.showOpenDialog(null);
			FileReader importFile = new FileReader(fileChooser.getSelectedFile());
			
			//Setup reader and list for bad records
			CSVReader csvReader = new CSVReader(importFile);
			List<String[]> badRecords = new ArrayList<String[]>();
			
			while ((line = csvReader.readNext()) != null) {
				
				countBlanks = 0;

				
				for (String column : line){
					
					//Add bad records to list if one of the columns is empty then break or record is over limit then break
					if(column.isEmpty() || line.length > 10) {
						badRecords.add(line);
						countBadRecords++;
						countBlanks++;
						break;
					}
					
				}
				
				//Add good records if no blanks were found
				if(countBlanks == 0) {
					
					addGoodRecord(line, currentDir);
					countGoodRecords++;
				}
				
			}
			
			//Add bad records to csv and close reader
			addBadRecord(badRecords, currentDir);
			printStats(currentDir,countGoodRecords,countBadRecords-1);
			csvReader.close();
			
			//Completion message
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Import Complete");
			//System.exit(0);
			
			}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void addBadRecord(List<String[]> line, String currentDir) {
		try {
			
			//Setup log for bad records
			Date currentDate = new Date();
			long time = currentDate.getTime();
			Timestamp currentTime = new Timestamp(time);
			String BadRecordPath = currentDir + "\\Logs\\ImportErrors\\bad-data-" + new SimpleDateFormat("yyyyMMddHHmmss").format(currentTime) + ".csv";
			File badFile = new File(BadRecordPath);
			
			//Setup Writer
			CSVWriter badCSVWriter = new CSVWriter(new FileWriter(badFile));
			
			//Write and close
			badCSVWriter.writeAll(line);
			
			badCSVWriter.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addGoodRecord(String[] record, String currentDir) {
		try {
			
			String sql = "";
			//String databasePath = "jdbc:sqlite:" + currentDir + "/db/RecordTracker.db";
			//Connection conn = DriverManager.getConnection(databasePath);
			
			//Insert good records into good record table
			sql = "INSERT INTO good_table (fname, lname, email, gender, pngFile, cardIss, Amount, bool1, bool2, city) VALUES (" 
			+ "'" + record[0].replace("'", "''") + "' , "
			+ "'" + record[1].replace("'", "''") + "' , "
			+ "'" + record[2].replace("'", "''") + "' , "
			+ "'" + record[3].replace("'", "''") + "' , "
			+ "'" + record[4].replace("'", "''") + "' , "
			+ "'" + record[5].replace("'", "''") + "' , "
			+ "'" + record[6].replace("'", "''") + "' , "
			+ "'" + record[7].replace("'", "''") + "' , "
			+ "'" + record[8].replace("'", "''") + "' , "
			+ "'" + record[9].replace("'", "''") + "');";
			
			//Execute and close
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			//conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printStats(String currentDir, long countGoodRecords, long countBadRecords) {
		try {
			//Setup date/time
			Date currentDate = new Date();
			long time = currentDate.getTime();
			Timestamp currentTime = new Timestamp(time);
			
			//Setup file
			String statsPath = currentDir + "\\logs\\ImportStats\\import-stats"+ new SimpleDateFormat("yyyyMMddHHmmss").format(currentTime) + ".txt";
			File statsFile = new File(statsPath);
			FileWriter writeStats = new FileWriter(statsFile);
			
			//Write to file
			writeStats.write("Total Records: " + (countGoodRecords+countBadRecords) + " \n");
			writeStats.write("Records Appended: " + countGoodRecords + " \n");
			writeStats.write("Records Not Appended: " + countBadRecords + " \n");
			
			//Close writer
			writeStats.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createTable() {
		
		
		try {
			
			String sql = "";
			String currentDir = System.getProperty("user.dir");
			//String databasePath = "jdbc:sqlite:" + currentDir + "/db/RecordTracker.db";
			//Connection conn = DriverManager.getConnection(databasePath);
			
			
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
