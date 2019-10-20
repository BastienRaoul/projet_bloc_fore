package projetIUT_blocFore;

import java.util.Arrays;

import mock.DrillingInterface;

public class Block {
	
	private int MIN_DRILL_ESPACEMENT = 1;
	private int[] dimensions = new int[3];
	private Face[] faces = new Face[6]; //[front, right, back, left, top, bottom]
	private IntersectionFace[] intersectionFaces = new IntersectionFace[12];
	private boolean[][] isIntersectionCreated = new boolean[6][6];
	
	public Block(int x, int y, int z) {
		dimensions[0] = x;
		dimensions[1] = y;
		dimensions[2] = z;
		createFaces();
		//createIntersectionFaces(); // To execute after the creation of the drillings 
		Arrays.fill(isIntersectionCreated, false); // A matrix to not create back intersections (eg: face A with B then face B with A)
	}

	private void createFaces() {
		// TODO Auto-generated method stub
		
		// Front face		
		faces[0] = new Face(dimensions[0], dimensions[1], "+", 2/*0, 0, 0*/);
			
		// Right face
		faces[1] = new Face(dimensions[2], dimensions[1], "-", 0/*0, -90, 0*/);
		
		// Back face
		faces[2] = new Face(dimensions[0], dimensions[1], "-", 2/*0, 180, 0*/);
		
		// Left face
		faces[3] = new Face(dimensions[2], dimensions[1], "+", 0/*0, 90, 0*/);
		
		// Top face
		faces[4] = new Face(dimensions[0], dimensions[1], "-", 1/*90, 0, 0*/);
		
		// Bottom face
		faces[5] = new Face(dimensions[0], dimensions[2], "+", 1/*-90, 0, 0*/);
	}

	public void createIntersectionFaces() {
		// TODO Auto-generated method stub
		// We go through all faces 
		int idInter = 0;
		for (int j = 0; j < faces.length; j++) {
			for (int i = 0; i < faces.length; i++) {
				if (areFacesPerpendicular(i, j) && !isIntersectionCreated[i][j]) {
					intersectionFaces[idInter] = new IntersectionFace(faces[i], faces[j]);
					idInter++;
					isIntersectionCreated[i][j] = true;
				}
			}
		}
		if (idInter != 12) {
			System.out.println("Some intersections between faces ar not found.");
		}
	}
	
	private boolean areFacesPerpendicular(int idFace1, int idFace2) {
		if (idFace1 == idFace2)
			return false;
		
		// If there is at least one difference of rotation between faces
		// that equals 180 or -180, the faces aren't perpendicular.
		// It works because only one axe is used for each face.
		/*for (int i = 0; i < 3; i++) {
			int diffRotBtwFaces = faces[idFace1].getRotation()[i] - faces[idFace2].getRotation()[i];
			if (Math.abs(diffRotBtwFaces) == 180) {
				return false;
			}
		}
		return true;*/
		
		// If the faces have a different absolute normal they are perpendicular
		return faces[idFace1].getNormal() != faces[idFace2].getNormal();
	}
	
	public String verifyDrillings() {
		String errors = "";
		
		for (Face face : faces) {
			errors += face.verifyOwnDrillings();
		}
		
		
		for (Face face : faces) {
			for (Face otherFace : faces) {
				// Verify intersections between opposite faces
				if (otherFace != face && !areFacesPerpendicular(face.getId(), otherFace.getId())) {
					errors += verifyFaceToFaceIntersections(face, otherFace);
				}
				// Verify intersections between perpendicular faces
				else if (areFacesPerpendicular(face.getId(), otherFace.getId())) {
					errors += verifySidesIntersections(face, otherFace);
				}
			}
		}
		
		return errors;
	}
	
	private String verifyFaceToFaceIntersections(Face f1, Face f2) {
		String errors = "";
		
		for (DrillingInterface drilling : f1.getDrillings()) {
			for (DrillingInterface otherDrill : f2.getDrillings()) {
				int dCoords[] = drilling.getAbsCoords();
				int oCoords[] = otherDrill.getAbsCoords();
				int axes[] = f1.getAxesUsed();
				
				// If espacement is wrong and if the drillings meet each other
				float espacement = Utils.getDistance(dCoords[axes[0]], dCoords[axes[1]], oCoords[axes[0]], oCoords[axes[1]]);
				float drillingsEspacement = espacement - drilling.getDiameter()/2 - otherDrill.getDiameter()/2;
				int depthAxis = f1.getNormal();
				
				int near = dimensions[depthAxis] - drilling.getDepth() - otherDrill.getDepth(); // Thickness of the block - depth of the drillings
				near += (f1.getNormalDir().equals("-") ? dCoords[depthAxis] - dimensions[depthAxis] : -dCoords[depthAxis]); // Minus z pos of the drilling
				near += (f2.getNormalDir().equals("-") ? oCoords[depthAxis] - dimensions[depthAxis] : -oCoords[depthAxis]); // Minus z pos of the other drilling
				
				if (drillingsEspacement < MIN_DRILL_ESPACEMENT && near <= 0) {  
					// If one doesn't encompass the other so that an error
					if (!Utils.areEncompassed(drilling, otherDrill, espacement)) {
						errors += "\n[Espacement - Opposite faces] " + drilling.toString() + " with " + otherDrill.toString();
					}
				}
			}
		}
		
		return errors;
	}
	
	private String verifySidesIntersections(Face f1, Face f2) {
		String errors = "";
		
		for (DrillingInterface drilling : f1.getDrillings()) {
			for (DrillingInterface otherDrill : f2.getDrillings()) {
				// drilling is side viewed and has a rectangle shape
				// otherDrill has a round shape
				
				if (areTooClose(drilling, otherDrill)) { // In a 2D view (side of the face of otherDrill)
					if (areCrossing(drilling, otherDrill)) { // otherDrill reaches drilling
						int axisForEspacement = 3 - drilling.getFace().getNormal() - otherDrill.getFace().getNormal(); // the axis that is neither the normal of drilling nor otherDrill
						int espacement = Math.abs(drilling.getAbsCoords()[axisForEspacement] - otherDrill.getAbsCoords()[axisForEspacement]);
						if (!Utils.areEncompassed(drilling, otherDrill, espacement)) {
							errors += "\n[Espacement - Perpendicular faces]" + drilling.toString() + " with " + otherDrill.toString();
						}
					}
				}
			}
		}
		
		return errors;
	}
	
	private boolean areTooClose(DrillingInterface drilling, DrillingInterface otherDrill) {
		
		// Projection (returns x & y of the face but with absolute axes)
		int[] axes = otherDrill.getFace().getAxesUsed();
		
		// Coordinates of the center of the other drilling (round shape)
		int[] oCoords = {otherDrill.getAbsCoords()[axes[0]], otherDrill.getAbsCoords()[axes[1]]};
		
		// drilling (rectangle shape)  direction is horizontal (0) or vertical (1) ? 
		int drillDir = (drilling.getFace().getNormal() == axes[0] ? 0 : 1);
		// If we want the other axis just do (1 - drillDir)
		// case1 drillDir = 1 -> notDrillDir = 1 - 1 = 0
		// case2 drillDir = 0 -> notDrillDir = 1 - 0 = 1
		
		
		// Is the otherDrill in the depth of drilling
		boolean isInDrillDepth;
		int z1 = drilling.getAbsCoords()[drilling.getFace().getNormal()];
		int[] dCoords = {drilling.getAbsCoords()[axes[0]], drilling.getAbsCoords()[axes[1]]};
		int depth1 = drilling.getDepth();
		
		if (drilling.getFace().getNormalDir().equals("+")) {
			isInDrillDepth = z1 < oCoords[drillDir] && oCoords[drillDir] < z1 + depth1;
		} else { // normalDir = "-"
			isInDrillDepth = z1 - depth1 < oCoords[drillDir] && oCoords[drillDir] < z1;
		}
		
		// The otherDrill (round) is enough spaced to the sides of the drilling (rect)
		if (isInDrillDepth) {
			if (Math.abs(dCoords[1-drillDir] - oCoords[1-drillDir]) - drilling.getDiameter()/2 - otherDrill.getDiameter()/2 < MIN_DRILL_ESPACEMENT) {
				return true;
			}
		}

		return false;
	}
	
	// first approach to know if perpendicular drillings were too close
	/*private boolean isCenterInsideRect(DrillingInterface otherDrill, DrillingInterface drilling) {
		
		// Projection
		int[] axes = otherDrill.getFace().getAxesUsed();
		
		// x & y are the coordinates that we'll know if they're in the rectangle
		int x = otherDrill.getAbsCoords()[axes[0]];
		int y = otherDrill.getAbsCoords()[axes[1]];
		
		
		// Those are sides of the rectangle
		int x1, x2, y1, y2;	
		if (drilling.getFace().getNormal() == axes[0]) { // The drilling is horizontal
			
			x1 = drilling.getFaceCoords()[2];
			x2 = x1 + drilling.getDepth();
			y1 = drilling.getAbsCoords()[axes[1]] - drilling.getDiameter()/2;
			y2 = y1 + drilling.getDiameter();
			
		} else { // The drilling is vertical
			x1 = drilling.getAbsCoords()[axes[0]] - drilling.getDiameter()/2;
			x2 = x1 + drilling.getDiameter();
			y1 = drilling.getFaceCoords()[2];
			y2 = y1 + drilling.getDepth();	
		}
		
		if ((y > y1 && y < y2) && (x > x1 && x < x2)) return true;
		else return false;
	}*/

	// return true if the otherDrill reaches drilling
	private boolean areCrossing(DrillingInterface drilling, DrillingInterface otherDrill) {
		
		int depthAxis = otherDrill.getFace().getNormal();
		
		// Variables with 1 are related to drilling and variables with 2 are related to otherDrill
		
		int z2 = otherDrill.getAbsCoords()[depthAxis]; // Absolute z coordinate of otherDrill
		int depth2 = otherDrill.getDepth();
		
		int center1 = drilling.getAbsCoords()[depthAxis]; // x or y of the center of drilling depending on depth axis
		int r1 = drilling.getDiameter()/2; // Radius of drilling
		
		if (otherDrill.getFace().getNormalDir().equals("+"))
			return z2 + depth2 > center1 - r1;
		else
			return z2 - depth2 < center1 + r1;	
		
	}
	
	public int[] getDimensions() {
		return dimensions;
	}
}
