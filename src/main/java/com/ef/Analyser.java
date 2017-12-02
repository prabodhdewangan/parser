package com.ef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ef.model.BlockedAddress;
import com.ef.model.LogEntry;
import com.ef.util.HibernateUtil;
import com.ef.util.Utils;;
/**
 * Analyses log file in memory 
 * @author pdewanga
 */
public class Analyser extends Base {
	
	public Analyser(String logFile) {
		super(logFile);
	}

	public void findBlockedIpAddress(String startDate, String duration, int threshhold) {
		List<BlockedAddress> blockedAddresses = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		HashMap<String, List<LogEntry>> groupRecordsByIpAddress = groupRecordsByIpAddress(startDate, duration, threshhold);
		System.out.println("_______________________________________");
		System.out.println("Filter IP address with access time & http status");
		System.out.println("________________________________________");
		System.out.println("IP address | Access time | HTTP status");
		groupRecordsByIpAddress.entrySet().stream().forEach(e -> {
			e.getValue().stream().forEachOrdered(System.out::println);
		});;
		System.out.println("_______________________________________");
		try {
			groupRecordsByIpAddress.entrySet().stream().forEach(e -> {
					e.getValue().stream().distinct()
					.collect(Collectors.groupingBy(LogEntry::getHttpStatus))
					.entrySet().stream().forEach(leSet->{
						Transaction transaction = session.beginTransaction();
						try {
							BlockedAddress blockedAddress = new BlockedAddress(e.getKey(), leSet.getKey(), Utils.getBlockReason(leSet.getKey()));
							session.save(blockedAddress);
							blockedAddresses.add(blockedAddress);
							transaction.commit();
							session.clear();
						} catch (Exception ex) {
							transaction.rollback();
							ex.printStackTrace();
						} 
					});
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			HibernateUtil.shutdown();
		}
		
		System.out.println("_______________________________________");
		System.out.println("Blocked IP Address vs Blocked Reason logs aggregated");
		System.out.println("________________________________________");
		System.out.println("  IP Address      :      Blocked Reason  ");
		System.out.println("________________________________________");
		blockedAddresses.stream().forEachOrdered(
				c -> System.out.println(c.getAddress() + " : " + c.getComment()));
		System.out.println("________________________________________");

	}
	
	private HashMap<String, List<LogEntry>> groupRecordsByIpAddress(String startDate, String duration, int threshhold) {
		return (HashMap<String, List<LogEntry>>) groupRecordsByIpAddress(startDate, duration).entrySet().stream()
				.filter(a -> a.getValue().size() > threshhold)
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}
	
	private HashMap<String, List<LogEntry>> groupRecordsByIpAddress(String startDate, String duration) {
		return (HashMap<String, List<LogEntry>>) getRecordsBtnDuration(startDate, duration)
				.collect(Collectors.groupingBy(LogEntry::getAddress));
	}
	
	private Stream<LogEntry> getRecordsBtnDuration(String startDate, String duration) {
		return logs.stream()
				.filter(r -> (r.getAccessDateTime().after( Utils.parse(startDate, "yyyy-MM-dd.HH:mm:ss"))
						&& r.getAccessDateTime().before(Utils.computeEndDate(startDate, duration))));
	}

}
