package com.ef;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.ef.model.LogEntry;
import com.ef.util.Utils;;

public abstract class Base {
	
	protected ArrayList<LogEntry> logs;

	public Base(String logFile) {
		this.logs = new ArrayList<>();
		readFile(logFile);
	}

	private void readFile(String filename) {
		/**
		 * Create File resource for fileName iterate over its lines; use
		 * LogToLogEntry for each to convert it to LogEntry objects add resulting
		 */
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEachOrdered(line -> {
				logs.add(Utils.parseEntry(line));
			});
		} catch (IOException e) {
			throw new RuntimeException("Failed to read file :"+ e.getMessage());
		}
	}
}
