/*
 * Assignment 4 priority queue 
 * Written By Aron Lawrence
 * Crowd organizer App
 * Main program, reads events file
 * 
 */

package edu.wmich.cs3310.A4;

import java.io.*;
import java.util.Scanner;

public class CrowdOrganizerApp {

	//************Main driver of the program********************8
	public static void main(String[] args) throws IOException {

		String logFile = "Log.txt";
		String startFile = "Lineat6Am.csv";

		BufferedWriter log = new BufferedWriter(new FileWriter(logFile));
		log.write(">>> program starting \n");

		File events = new File("Events.txt");
		Scanner s = new Scanner(events);

		CustomerPrQ prQ = new CustomerPrQ(log);

		readFile(startFile, s, prQ);
		s.close();

		log.write(">>> program terminating");

		log.close();
	}

	// **************reads all events and calls proper methods********
	private static void readFile(String startFile, Scanner s, CustomerPrQ prQ)
			throws IOException {
		String[] data;
		while (s.hasNextLine()) {
			String inLine = s.nextLine();

			if (!inLine.startsWith("//")) {
				data = inLine.split(",");
				if (data[0].toLowerCase().startsWith("o")) {

					prQ.arrangeCustomerq(startFile);
				} else if (data[0].toLowerCase().startsWith("s")) {
					prQ.serveACustomer();
				} else if (data[0].toLowerCase().startsWith("n")) {
					prQ.addCustomerToQ(data);
				} else if (data[0].toLowerCase().startsWith("c")) {
					prQ.serveRemainingCustomers();

				}

			}
		}
	}

}
