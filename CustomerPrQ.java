/*
 * Assignment 4 priority queue 
 * Written By Aron Lawrence
 * CustomerPrQ
 * handles the making, organizing, inserts
 * and deletes of the heap
 * 
 */


package edu.wmich.cs3310.A4;

import java.util.Scanner;
import java.io.*;

public class CustomerPrQ {

	private BufferedWriter logFile;
	private int N;
	private final int maxN = 100;
	private Customer[] minheap;
	private int startPriVal = 100;
	private int priorityValue;

	// ************PUBLIC METHODS*****************************

	// ***************************constructor**********************
	public CustomerPrQ(BufferedWriter log) {
		logFile = log;
		minheap = new Customer[maxN];
		N = 0;
	}

	// ************************fills the Q with the intital line at 6
	// am****************
	public void arrangeCustomerq(String inFile) throws IOException {
		File startFile = new File(inFile);
		Scanner s = new Scanner(startFile);

		try {
			logFile.write("STORE IS OPENING \n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		readFile(s);
		logFile.write(">>> initial heap built containing " + N + " nodes \n");
		s.close();
	}

	// ************************Empty the Q*************************
	public void serveRemainingCustomers() throws IOException {
		logFile.write("STORE IS CLOSING\n");
		logFile.write(">>> heap currently has " + N + " nodes remaining \n");
		while (!heapEmpty()) {
			logFile.write(String.format("SERVING:   %s (%d) \n",
					minheap[0].getName(), minheap[0].getPriority()));
			heapDelete();
		}
		logFile.write(">>> heap is now empty \n");

	}

	// ********************adds Customer to Q*****************
	public void addCustomerToQ(String[] data) throws IOException {
		String[] val = new String[4];
		for (int i = 0; i < 4; i++) {
			val[i] = data[i + 1];
		}
		determinePriorityValue(val);
		logFile.write(String.format("ADDING:    %s (%d) \n", val[0],
				priorityValue));
		heapInsert(val[0], priorityValue);
	}

	// ******************calls delete method**************
	public void serveACustomer() throws IOException {
		logFile.write(String.format("SERVING:   %s (%d) \n",
				minheap[0].getName(), minheap[0].getPriority()));
		heapDelete();

	}

	// ***************PRIVATE METHODS***************************

	// *************creates a node and calls walk up*********
	private void heapInsert(String value, int priVal) {
		Customer newCustomer = new Customer(priVal, value);
		minheap[N] = newCustomer;
		N++;
		walkUp(N - 1);

	}

	// *****deletes a node and changes N. and calls walkdown*****
	private void heapDelete() throws IOException {

		minheap[0] = minheap[N - 1];
		N--;
		walkDown(0);
	}

	// ************checks if heap is empty********************
	private boolean heapEmpty() {
		boolean status = false;
		if (N == 0) {
			status = true;
		}
		return status;
	}

	// *****************find priority Value*************************
	private void determinePriorityValue(String[] vals) throws IOException {
		startPriVal++;
		priorityValue = startPriVal;
		int age = new Integer(vals[3]);
		if (age >= 80) {
			priorityValue -= 30;
		} else if (age >= 65) {
			priorityValue -= 15;
		}

		for (int i = 1; i < 3; i++) {
			switch (vals[i].toLowerCase()) {
			case "employee":
				priorityValue -= 25;
				break;
			case "owner":
				priorityValue -= 80;
				break;
			case "vip":
				priorityValue -= 5;
				break;
			case "supervip":
				priorityValue -= 10;
				break;
			}
		}

	}

	// *********************shift from top down**********
	private void walkDown(int startFrom) {
		int i = startFrom;
		int smallChild = subOfSmCh(i);
		Customer temp = minheap[i];
		while ((2 * i + 1) <= (N - 1)
				&& (temp.getPriority() > minheap[smallChild].getPriority())) {
			minheap[i] = minheap[smallChild];
			i = smallChild;
			smallChild = subOfSmCh(i);
		}
		minheap[i] = temp;
	}

	// *************get smallest child********************
	private int subOfSmCh(int i) {
		int leftChild = (2 * i) + 1;
		int rightChild = (2 * i) + 2;
		if (rightChild > (N - 1)
				|| minheap[leftChild].getPriority() < minheap[rightChild]
						.getPriority()) {
			return leftChild;
		} else {
			return rightChild;
		}
	}

	// **************shuffles heap from bottom up********
	private void walkUp(int i) {
		Customer temp = minheap[i];
		while ((i > 0)
				&& (temp.getPriority() < minheap[(i - 1) / 2].getPriority())) {
			minheap[i] = minheap[(i - 1) / 2];
			i = (i - 1) / 2;
		}
		minheap[i] = temp;
	}

	// ****************reads file********************
	private void readFile(Scanner s) throws IOException {
		String inLine;
		String[] data;
		while (s.hasNextLine()) {
			inLine = s.nextLine();

			if (inLine.startsWith("//")) {

			} else {
				data = inLine.split(",");
				determinePriorityValue(data);
				heapInsert(data[0], priorityValue);
			}
		}
	}
}
