// dev : Rémi THOMAS

package projetIUT_blocFore;

import java.util.ArrayList;

import mock.DrillingInterface;

public class Face {
	
	private int MIN_DRILL_ESPACEMENT = 1; // in millimeters
	
	private static int id = 0;
	private int[] dimensions;
	//private int[] rotation = new int[3]; // Deprecated
	private String normalDir; // Direction of the normal (- or +)
	private int normal; // The normal of the face (0 or 1 or 2 => x, y, z)
	// Attributes stored separately because -0 means nothing
	private ArrayList<DrillingInterface> drillings = new ArrayList<>();
	
	public Face(int dimX, int dimY, String normalDir, int normal/*int rotX, int rotY, int rotZ*/) {
		id++;
		dimensions = new int[] {dimX, dimY};
		//rotation = new int[] {rotX, rotY, rotZ};
		this.normalDir = normalDir;
		this.normal = normal;
	}
	
	public void addDrillings(ArrayList<DrillingInterface> drillings) {
		this.drillings.addAll(drillings);
	}
	
	// {errors, shavings} / inspect means find errors and shavings points
	public String[] inspectOwnDrillings() {
		String[] messages = {"", ""};
		
		String[] inspEsp = inspectEspacement();
		String[] insDepth = inspectDepth();
		
		messages[0] += inspEsp[0];
		messages[1] += inspEsp[1];
		messages[0] += insDepth[0];
		messages[1] += insDepth[1];
		
		return messages;
	}
	
	private String[] inspectEspacement() {
		String[] messages = {"", ""};
		
		for (int i = 0; i < drillings.size(); i++) {
			DrillingInterface drilling = drillings.get(i);
			
			// Verify espacement with edges
			if (drilling.getFaceCoords()[0] - drilling.getDiameter()/2 < MIN_DRILL_ESPACEMENT ||		// (left and bottom sides)
					drilling.getFaceCoords()[1] - drilling.getDiameter()/2 < MIN_DRILL_ESPACEMENT) {
				messages[0] += "\n[Espacement - Edges] " + drilling.toString();
			}
			if (dimensions[0] - (drilling.getFaceCoords()[0] + drilling.getDiameter()/2) < MIN_DRILL_ESPACEMENT ||		// (right and top sides)
					dimensions[1] - (drilling.getFaceCoords()[1] + drilling.getDiameter()/2) < MIN_DRILL_ESPACEMENT) {
				messages[0] += "\n[Espacement - Edges] " + drilling.toString();
			}
			
			// Verify espacement with other drillings
			for (int j = i+1; j < drillings.size(); j++) {
				DrillingInterface otherDrill = drillings.get(j);
				float centersEspacement = Utils.getDistance(
					drilling.getFaceCoords()[0], drilling.getFaceCoords()[1],
					otherDrill.getFaceCoords()[0], otherDrill.getFaceCoords()[1]
				);
				//System.out.println("Dist btwn " + drilling.toString() + "/" + otherDrill.toString() + "\n = " + centersEspacement);
				float drillingsEspacement = centersEspacement - drilling.getDiameter()/2 - otherDrill.getDiameter()/2;
				if (drillingsEspacement < MIN_DRILL_ESPACEMENT) { // If drillings are too close
					if (!Utils.areEncompassed(drilling, otherDrill, centersEspacement)) {
						messages[0] += "\n[Espacement - Same face] " + drilling.toString() + " with " + otherDrill.toString(); // error
					} else {
						messages[1] += "\n" + drilling.toString() + " - " + otherDrill.toString(); // shavings
					}
				}
			}
		}
		
		return messages;  
	}
	
	private String[] inspectDepth() {
		String[] messages = {"", ""};
		
		for (int i = 0; i < drillings.size(); i++) {
			DrillingInterface drilling = drillings.get(i);
			if (drilling.getFaceCoords()[2] > 0) { // If a drilling is inside the block
				/*if (!hasParentDrilling(drilling)) {
					messages[0] += "\n[Depth] " + drilling.toString() + " starts inside the block without \"parent drilling\"";
				}*/
				DrillingInterface parent = drilling, childOfParent;
				do {
					childOfParent = parent;
					parent = parentDrilling(drilling);
					if (parent != null) { 
						messages[1] += "\n" + childOfParent.toString() + " - " + parent.toString(); // shavings point
						if (parent.getFaceCoords()[2] <= 0) break; // We just found the last parent
					} else {
						messages[0] += "\n[Depth] " + drilling.toString() + " starts inside the block without \"parent drilling\""; // error
					}
				} while (parent != null);
			}
		}
		
		return messages;
	}
	
	/*private boolean hasParentDrilling(DrillingInterface drilling) { // This function can't return the shavings points
		for (int i = 0; i < drillings.size(); i++) {
			DrillingInterface parent = drillings.get(i);
			if (parent != drilling) {
				
				// Does the parent encompass the current drilling ?
				int[] dCoords = drilling.getFaceCoords();
				int[] pCoords = parent.getFaceCoords();
				
				// distBtwCenters + child radius < parent radius  --> like 'areEncompassed()' but not reciprocally
				float espacement = Utils.getDistance(dCoords[0], dCoords[1], pCoords[0], pCoords[1]);
				//(can't use 'areEncompassed()' because the child doesn't have to encompass its parent
				if (espacement + (float) drilling.getDiameter()/2 < (float) parent.getDiameter()/2) { 
					// Test if parent's z coord start before child's one and reaches it
					if (pCoords[2] < dCoords[2] && pCoords[2] + parent.getDepth() >= dCoords[2]) {
						if (pCoords[2] <= 0) { // The parent doesn't start inside the block
							return true;
						} else {
							return hasParentDrilling(parent);
						}
					}
				}
			}
		}
		return false;
	}*/
	
	private DrillingInterface parentDrilling(DrillingInterface drilling) {
		for (int i = 0; i < drillings.size(); i++) {
			DrillingInterface parent = drillings.get(i);
			if (parent != drilling) {
				
				// Does the parent encompass the current drilling ?
				int[] dCoords = drilling.getFaceCoords();
				int[] pCoords = parent.getFaceCoords();
				
				// distBtwCenters + child radius < parent radius  --> like 'areEncompassed()' but not reciprocally
				float espacement = Utils.getDistance(dCoords[0], dCoords[1], pCoords[0], pCoords[1]);
				//(can't use 'areEncompassed()' because the child doesn't have to encompass its parent
				if (espacement + (float) drilling.getDiameter()/2 < (float) parent.getDiameter()/2) { 
					// Test if parent's z coord start before child's one and reaches it
					if (pCoords[2] < dCoords[2] && pCoords[2] + parent.getDepth() >= dCoords[2]) {
						return parent;
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "FACE " + ((char) 65 + id);
	}
	
	public ArrayList<DrillingInterface> getDrillings() {
		return drillings;
	}
	
	public void clearDrillings() {
		drillings.clear();
	}
	
	/*public int[] getRotation() {
		return rotation;
	}*/
	
	public int getId() {
		return id;
	}
	
	public String getNormalDir() {
		return normalDir;
	}
	
	public int getNormal() {
		return normal;
	}
	
	/**
	 * @return
	 * Axes that are not the normal
	 */
	public int[] getAxesUsed() {
		int index = 0;
		int axes[] = new int[2];
		for (int i = 0; i < 3; i++) {
			if (i != Math.abs(normal)) {
				axes[index++] = i;
			}
		}
		return axes;
	}
}
