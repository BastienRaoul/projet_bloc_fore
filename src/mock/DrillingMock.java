package mock;

import projetIUT_blocFore.Block;
import projetIUT_blocFore.Face;

public class DrillingMock implements DrillingInterface {

	private int[] faceCoords;
	private int depth;
	private int diameter;
	
	public DrillingMock(int x, int y, int z, int depth, int diameter) {
		faceCoords = new int[3];
		faceCoords[0] = x;
		faceCoords[1] = y;
		faceCoords[2] = z;
		this.depth = depth;
		this.diameter = diameter;
	}

	public int[] getFaceCoords() {
		return faceCoords;
	}

	public int getDepth() {
		return depth;
	}

	public int getDiameter() {
		return diameter;
	}

	@Override
	public int[] getAbsCoords() {
		return null;
	}
	
	@Override
	public String toString() {
		return ("(" + faceCoords[0] + ", " + faceCoords[1] + ", " + faceCoords[2] + ", " + depth + ", " + diameter + ")");
	}
	
}
