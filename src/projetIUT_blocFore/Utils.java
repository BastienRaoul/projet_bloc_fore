package projetIUT_blocFore;

import mock.DrillingInterface;

public class Utils {

	public static float getDistance(float x1, float y1, float x2, float y2) {
		//System.out.println("sqrt( (" + x1 + "-" + x2 + ")^2 + (" + );
		return (float) Math.sqrt( Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) );
	}
	
	public static boolean areEncompassed(DrillingInterface drilling, DrillingInterface otherDrill, float espacement) {
	// True if a radius is greater than the sum of the other radius and the espacement
		float r1 = drilling.getDiameter()/2;
		float r2 = otherDrill.getDiameter()/2;
		if (r1 >= r2 + espacement || r2 >= r1 + espacement) {
			return true;
		}
		return false;
	}
	
}
