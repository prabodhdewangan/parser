package com.ef.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ef.model.LogEntry;

public class Utils {

	public static final SimpleDateFormat sdf = new SimpleDateFormat();
	
	public static Date parse(String dateString, String pattern){
		sdf.applyPattern(pattern);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("failed to parse date :" + dateString + ", with given pattern "+ pattern);
		}
	}
	
	private static Date addHour(Date date, int noOfHour){
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.add(Calendar.HOUR, noOfHour);
		return instance.getTime();
	}

	private static Date addDay(Date date, int noOfDay) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.add(Calendar.DATE, noOfDay);
		return instance.getTime();
	}
	
	public static Date computeEndDate(String startDate, String duration) {
		if ("hourly".equalsIgnoreCase(duration))
			return addHour(Utils.parse(startDate, "yyyy-MM-dd.HH:mm:ss"), 1);
		else if ("daily".equalsIgnoreCase(duration))
			return addDay(Utils.parse(startDate, "yyyy-MM-dd.HH:mm:ss"), 1);
		throw new RuntimeException("Unrecognized duration passed");
	}
	

	public static LogEntry parseEntry(String line) {
		String[] splits = line.split("\\|");
		return new LogEntry(new Timestamp(parse(splits[0], "yyyy-MM-dd HH:mm:ss.SSS").getTime()) , splits[1],  
				splits[2],  Integer.parseInt(splits[3]),  splits[4]);
	}
	
	/**
	 * error codes are referred from https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml
	 * @param serverBlockCode
	 * @return
	 */
	public static String getBlockReason(int serverBlockCode) {
		String blockReason = "";
		
		switch (serverBlockCode) {
		case 400:
			blockReason = "400 Bad Request";
			break;
		case 401:
			blockReason = "401 Unauthorized";
			break;
		case 402:
			blockReason = "402 Payment Required";
			break;
		case 403:
			blockReason = "403 Forbidden";
			break;
		case 404:
			blockReason = "404 Not Found";
			break;
		case 405:
			blockReason = "405 Method Not Allowed";
			break;
		case 406:
			blockReason = "406 Not Acceptable";
			break;
		case 407:
			blockReason = "407 Proxy Authentication Required";
		case 408:
			blockReason = "408 Request Timeout";
			break;
		case 409:
			blockReason = "409 Conflict";
			break;
		case 410:
			blockReason = "410 Gone";
			break;
		case 411:
			blockReason = "411 Length Required";
			break;
		case 412:
			blockReason = "412 Precondition Failed";
			break;
		case 413:
			blockReason = "413 Entity Too Large";
			break;
		case 414:
			blockReason = "414 URI Too Long";
			break;
		case 415:
			blockReason = "415 Unsupported Media Type";
			break;
		case 416:
			blockReason = "416 Range Not Satisfiable";
			break;
		case 417:
			blockReason = "417 Expectation Failed";
			break;
		case 418:
			blockReason = "418 Unassigned";
			break;
		case 419:
			blockReason = "419 Unassigned";
			break;
		case 420:
			blockReason = "420 Unassigned";
			break;
		case 421:
			blockReason = "421 Misdirected Request";
			break;
		case 422:
			blockReason = "422 Unprocessable Entity";
			break;
		case 423:
			blockReason = "423 Locked";
			break;
		case 424:
			blockReason = "424 Failed Dependency";
			break;
		case 425:
			blockReason = "425 Unassigned";
			break;
		case 426:
			blockReason = "426 Upgrade Required";
			break;
		case 428:
			blockReason = "428 Precondition Required";
			break;
		case 429:
			blockReason = "429 Too Many Requests";
			break;
		case 430:
			blockReason = "430 Unassigned";
			break;
		case 431:
			blockReason = "431 Request Header Fields Too Large";
			break;
		case 450:
			blockReason = "450 Blocked by Windows Parental Controls (Microsoft)";
			break;
		case 451:
			blockReason = "451 Unavailable For Legal Reasons";
			break;
		default:
			blockReason = "Not Blocked";
		}

		return blockReason;
	}
}
