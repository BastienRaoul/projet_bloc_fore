package projetIUT_blocFore;

public class Utils {

	public static float getDistance(float x1, float y1, float x2, float y2) {
		//System.out.println("sqrt( (" + x1 + "-" + x2 + ")^2 + (" + );
		return (float) Math.sqrt( Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) );
	}
	
}
