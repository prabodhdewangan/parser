package com.ef;

import java.io.File;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ef.util.HibernateUtil;

public class Parser {
	public static void main(String[] args) {
		if (args.length > 0) {
			runApp(args);
		} else {
			printUsage();
		}
	}

	public static HashMap<String, String> decodeArgs(String[] array) {
		return (HashMap<String, String>) Stream.of(array).map(elem -> elem.split("\\="))
				.filter(elem -> elem.length == 2).collect(Collectors.toMap(e -> e[0], e -> e[1])).entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().split("\\-\\-")[1], e -> e.getValue()));
	}

	public static void runApp(String[] array) {
		try {
			String accesslog = decodeArgs(array).get("accesslog");
			if (accesslog == null){
				System.err.println("accesslog file path is not provided");
				printUsage();
			}
			File file = new File(accesslog);
			if (!file.exists()){
				System.err.println("accesslog file doesn't exists :"+ accesslog);
				printUsage();
			}
			String username = decodeArgs(array).get("mysqlusername");
			if (username == null){
				System.err.println("mysql username is not provided.");
				printUsage();
			}
			String password = decodeArgs(array).get("mysqlpassword");
			if (password == null )
			{
				System.err.println("mysql password is not provided.");
				printUsage();
			}
			String dbUrl = decodeArgs(array).get("mysqlconnectionurl");
			if (dbUrl == null )
			{
				System.err.println("mysql connection URL is not provided.");
				printUsage();
			}
			System.setProperty("mysqlusername", username);
			System.setProperty("mysqlpassword", password);
			System.setProperty("mysqlconnectionurl", dbUrl);
			String startDate = decodeArgs(array).get("startDate");
			String duration = decodeArgs(array).get("duration");
			if (duration == null && startDate == null) {
				System.out.println("loading access log file to DB.....");
				DBLoader analiser = new DBLoader(accesslog);
				analiser.loadToDB();
				System.out.println("finished loading access log file to DB.....");
			} else {
				String thresholdStr = decodeArgs(array).get("threshold");
				if (thresholdStr == null )
				{
					System.err.println("threshold is not provided.");
					printUsage();
				}
				int threshold = Integer.parseInt(thresholdStr);
				System.out.println(startDate + " : " + duration + " : " + threshold);
				Analyser analiser = new Analyser(accesslog);
				analiser.findBlockedIpAddress(startDate, duration, threshold);
			}
		} finally {
			HibernateUtil.shutdown();
		}
		
	}
	
	private static void printUsage() {
		System.out.println("App usages :");
		System.out.println(
				"java -cp \"parser.jar\" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 "
				+ "--accesslog=/Users/pdewanga/sourcecode/java_mysql/src/main/resources/access.log "
				+ "--mysqlusername=root --mysqlpassword=admin --mysqlconnectionurl=jdbc:mysql://localhost:3306/JAVA_MYSQL");
		System.out.println(" or ");
		System.out.println(
				"java -cp \"parser.jar\" com.ef.Parser --accesslog=/Users/pdewanga/sourcecode/java_mysql/src/main/resources/access.log "
				+ "--mysqlusername=root --mysqlpassword=admin --mysqlconnectionurl=jdbc:mysql://localhost:3306/JAVA_MYSQL");
		System.out.println("Please ensure that your MySQL server is running");
		System.out.println("Please ensure that your MySQL server has JAVA_MYSQL schema");
		System.exit(0);
	}
}
