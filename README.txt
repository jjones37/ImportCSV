ImportCSV Coding Challenge


1. We need a Java application that will consume a CSV file, parse the data and insert to a SQLite In-Memory database.  
	a. Table X has 10 columns A, B, C, D, E, F, G, H, I, J which correspond with the CSV file column header names.
	b. Include all DDL in submitted repository
	c. Create your own SQLite DB
2. The data sets can be extremely large so be sure the processing is optimized with efficiency in mind.  

3. Each record needs to be verified to contain the right number of data elements to match the columns.  
	a. Records that do not match the column count must be written to the bad-data-<timestamp>.csv file
	b. Elements with commas will be double quoted

4. At the end of the process write statistics to a log file
	a. # of records received
	b. # of records successful
	c. # of records failed


To Run the Import CSV

	1.Run importCSV.jar
	2.Pick .csv file when prompted
	3.Import process will begin
	4.Once finished a propmt will pop up giving completion message
	5.A log file for bad records will be stored in /logs/ImportErrors/bad-data-<timestamp of run>.csv
	6.A log file for stats will be stored in /logs/ImportStats/import-stats<timestamp of run>.txt

Thought Process

The approach I took to solve this challenge was to read in each record and then go through each column to look for any blanks using some sort of csv reader.
The next piece was to setup a database to append records to. My inital process was using a persistent database to check if good records were actully being appended.
I later switched it to In-Memory to go with the challenge. The database I used for testing is still located in the git repository and I left the code for it commented in the java files.
Once switching to In-Memory I started creating the table ever time and changed around how I did my connections to the database.
After setting up the database and my logic around catching bad records I noticed that some records had more columns than what was supposed to be appended to the good records.
That then required an additional conditon based around bad records and was added later on. Once all of the sql errors were resolved writing to the stats to the text file was next.
After completing all of those requirements I added a file selector and propmt window on completion for convenience.


