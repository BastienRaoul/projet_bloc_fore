package projetIUT_blocFore;

import java.util.ArrayList;
import java.util.List;

public class Drilling {
	
	private Block block;
	private Face face;
	private float[] faceCoords = new float[3];
	private float[] absCoords = new float[3];
	private float depth;
	private float diameter;
	private List<Drilling> testedDrillingsIntersects = new ArrayList<Drilling>(); // With which drillings this one has been tested
	
	public Drilling(Block block, Face face, float[] faceCoords, float depth, float diameter) {
		this.block = block;
		this.face = face;
		this.faceCoords = faceCoords;
		this.depth = depth;
		this.diameter = diameter;
		absCoords = convertFaceToAbsCoords();
	}
	
	private float[] convertFaceToAbsCoords() {
		float[] coords = faceCoords;
		float[] dimBlock = block.getDimensions();
		
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
	
	public float[] getFaceCoords() {
		return faceCoords;
	}

	public float[] getAbsCoords() {
		return absCoords;
	}

	public float getDepth() {
		return depth;
	}

	public float getDiameter() {
		return diameter;
	}

	public List<Drilling> getTestedDrillingsIntersects() {
		return testedDrillingsIntersects;
	}

	public void setTestedDrillingsIntersects(List<Drilling> testedDrillingsIntersects) {
		this.testedDrillingsIntersects = testedDrillingsIntersects;
	}
}
