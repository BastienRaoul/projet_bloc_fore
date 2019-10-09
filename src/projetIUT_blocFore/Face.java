package projetIUT_blocFore;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineListener;

public class Face {
	
	private int MIN_DRILL_ESPACEMENT = 1; // in millimeters
	
	private static int id = 0;
	private float[] dimensions;
	private int[] rotation = new int[3];
	private List<Drilling> drillings = new ArrayList<Drilling>();
	
	public Face(float dimX, float dimY, int rotX, int rotY, int rotZ) {
		id++;
		dimensions = new float[] {dimX, dimY};
		rotation = new int[] {rotX, rotY, rotZ};
	}
	
	public void addDrillings(ArrayList<Drilling> drillings) {
		drillings.addAll(drillings);
	}
	
	public String verifyCoplanarDrillings() {
		String errors = "";
		
		errors += verifyEspacement();
		errors += verifyDepth();
		
		return errors;
	}
	
	private String verifyEspacement() {
		String errors = "";
		
		for (int i = 0; i < drillings.size(); i++) {
			Drilling drilling = drillings.get(i);
			
			// Verify espacement with edges
			if (drilling.getFaceCoords()[0] - drilling.getDiameter()/2 < MIN_DRILL_ESPACEMENT ||
					drilling.getFaceCoords()[1] - drilling.getDiameter()/2 < MIN_DRILL_ESPACEMENT) {
				errors += "[Espacement] " + drilling.toString();
			}
			
			// Verify espacement with other drillings
			for (int j = 0; j < drillings.size(); j++) {
				if (i != j) {
					Drilling otherDrill = drillings.get(j);
					float centersEspacement = (float) Math.sqrt(
							Math.pow(drilling.getFaceCoords()[0] - otherDrill.getFaceCoords()[0], 2) +
							Math.pow(drilling.getFaceCoords()[1] - otherDrill.getFaceCoords()[1], 2)
					);
					float drillingsEspacement = centersEspacement - drilling.getDiameter()/2 - otherDrill.getDiameter()/2;
					if (drillingsEspacement < MIN_DRILL_ESPACEMENT) {
						errors += "[Espacement] " + drilling.toString() + " with " + otherDrill.toString();
					}
				}
			}
		}
		
		return errors;
	}
	
	private String verifyDepth() {
		String errors = "";
		
		for (int i = 0; i < drillings.size(); i++) {
			Drilling drilling = drillings.get(i);
			if (drilling.getFaceCoords()[2] > 0) { // If a drilling inside the block
				if (!hasParentDrilling(drilling)) {
					errors += drilling.toString() + " starts inside the block without \"parent drilling\"";
				}
			}
		}
		
		return errors;
	}
	
	private boolean hasParentDrilling(Drilling drilling) {
		
		return false;
	}
	
	@Override
	public String toString() {
		return "FACE " + ((char) 65 + id);
	}
	
	public List<Drilling> getDrillings() {
		return drillings;
	}
	
	public int[] getRotation() {
		return rotation;
	}
	
	public int getId() {
		return id;
	}
}
