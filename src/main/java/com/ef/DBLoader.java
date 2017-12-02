package com.ef;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ef.model.LogEntry;
import com.ef.util.HibernateUtil;

;
/**
 * Loads log file in to DB 
 * @author pdewanga
 */
public class DBLoader extends Base {
	
	public DBLoader(String logFile) {
		super(logFile);
	}

	public void loadToDB(){
		long currentTimeMillis = System.currentTimeMillis();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session openSession = sessionFactory.openSession();
		Transaction transaction = null;
		int count = 0;
		try {
			transaction = openSession.beginTransaction();
			for (LogEntry logEntry : logs){
				count++;
				logEntry.setId(null); // let DB generate sequence
				openSession.save(logEntry);
				if (count % 500 == 0){ //batch every 500 record and restart transaction
					transaction.commit();
					transaction = openSession.beginTransaction();
					openSession.clear();
					System.out.println("# of record loaded "+ count) ;
				}
				
			}
		} finally {
			transaction.commit();
			openSession.close();
			System.out.println("Total # record loaded "+ count) ;
			HibernateUtil.shutdown();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("DB load finished in "+ (endTime - currentTimeMillis)/1000 + " seconds ") ;
	}

}
