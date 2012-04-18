package map;



/**
 * This class will create x and y coordinates for
 * one wall with frame 800 x 600 in future: 
 * - n x m if user choose to resize window. 
 * 
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-16
 */

import java.util.Random; 
public class Block {
	Random number;
	private static int X, Y;
	
	public Block() {
		number = new Random();
		int[] x = new int[20]; 
	}
	
	/**
	 * Random number on x-axis
	 */
	public int getXCoordinate() {
		return X = number.nextInt(800);		
	}
	
	/**
	 * Random nr on y-axis
	 */
	public int getYCoordinate() {
		return Y = number.nextInt(600);		
	}
	
	
}
