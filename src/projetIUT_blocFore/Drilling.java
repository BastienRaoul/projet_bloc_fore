package projetIUT_blocFore;

import java.util.ArrayList;
import java.util.List;

public class Drilling {
	
	private Face face;
	private float[] faceCoords = new float[3];
	private float[] absCoords = new float[3];
	private float depth;
	private float diameter;
	private List<Drilling> testedDrillings = new ArrayList<Drilling>();
	
	public Drilling(Face face, float[] faceCoords, float depth, float diameter) {
		this.face = face;
		this.faceCoords = faceCoords;
		this.depth = depth;
		this.diameter = diameter;
		absCoords = convertFaceToAbsCoords();
	}
	
	private float[] convertFaceToAbsCoords() {
		float[] coords = faceCoords;
		
		return coords;
	}
}
