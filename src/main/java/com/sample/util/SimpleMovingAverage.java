package com.sample.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class implements simple moving average algorithm. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

public class SimpleMovingAverage {

	// this queue used to store list of averages
	private static final Queue<Double> resultSet = new LinkedList<>();

	private static double sum; 

	/**
	 * This method is used to add new data in the
	 * list and update the sum to get the new mean value
	 *  
	 * @param num
	 * @param mavgPoints
	 */
	public static void addData(Double num, int mavgPoints) { 
		sum += num; 
		resultSet.add(num); 

		// Updating size so that length of data set should be equal to period  
		if (resultSet.size() > mavgPoints) { 
			sum -= resultSet.remove(); 
		} 
	} 

	/**
	 * Calculate mean value
	 * @param mavgPoints
	 * @return double
	 */
	public static double getMean(int mavgPoints) { 
		return sum / mavgPoints; 
	} 

	/**
	 * Get mean value for each new item of input list
	 * @param input
	 * @param mavgPoints
	 * @return
	 */
	public static List<Double> compute(List<Double> input, int mavgPoints) {
		List<Double> result = new ArrayList<>();
		input.stream().forEach(i -> {
			addData(i, mavgPoints);
			result.add(getMean(mavgPoints));
		});
		return result;
	}
}
