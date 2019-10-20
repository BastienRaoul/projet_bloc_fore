// dev : Rémi THOMAS

package projetIUT_blocFore;

import java.util.ArrayList;
import java.util.List;

import mock.DrillingInterface;

public class Drilling implements DrillingInterface {
	
	private Block block;
	private Face face;
	private int[] faceCoords = new int[3];
	private int[] absCoords = new int[3];
	private int depth;
	private int diameter;
	private List<Drilling> testedDrillingsIntersects = new ArrayList<Drilling>(); // With which drillings this one has been tested
	
	public Drilling(Block block, Face face, int[] faceCoords, int depth, int diameter) {
		this.block = block;
		this.face = face;
		this.faceCoords = faceCoords;
		this.depth = depth;
		this.diameter = diameter;
		absCoords = convertFaceToAbsCoords();
	}
	
	private int[] convertFaceToAbsCoords() {
		int[] coords = faceCoords;
		int[] dimBlock = block.getDimensions();
		
		switch(face.getId()) {
		// face 0 ignored cause it's the same coordinates
		// Right
		case 1: 
			coords[0] = dimBlock[0] - faceCoords[2];
			//coords[1] = faceCoords[1];
			coords[2] = faceCoords[0];
			break;
		// Back
		case 2: 
			coords[0] = dimBlock[0] - faceCoords[0];
			//coords[1] = faceCoords[1];
			coords[2] = dimBlock[2] - faceCoords[2];
			break;
		// Left
		case 3: 
			coords[0] = faceCoords[2];
			//coords[1] = faceCoords[1];
			coords[2] = dimBlock[0] - faceCoords[0];
			break;
		// Top
		case 4: 
			//coords[0] = faceCoords[0];
			coords[1] = dimBlock[1] - faceCoords[2];
			coords[2] = faceCoords[1];
			break;
		// Bottom
		case 5:
			//coords[0] = faceCoords[0];
			coords[1] = faceCoords[2];
			coords[2] = dimBlock[2] - faceCoords[1];
			break;
		}
		
		return coords;
	}
	
	@Override
	public String toString() {
		return ("(" + faceCoords[0] + ", " + faceCoords[1] + ", " + faceCoords[2] + ") on " + face.toString());
	}
	
	public int[] getFaceCoords() {
		return faceCoords;
	}

	public int[] getAbsCoords() {
		return absCoords;
	}

	public int getDepth() {
		return depth;
	}

	public int getDiameter() {
		return diameter;
	}

	public List<Drilling> getTestedDrillingsIntersects() {
		return testedDrillingsIntersects;
	}

	public void setTestedDrillingsIntersects(List<Drilling> testedDrillingsIntersects) {
		this.testedDrillingsIntersects = testedDrillingsIntersects;
	}

	@Override
	public Face getFace() {
		return face;
	}
}
