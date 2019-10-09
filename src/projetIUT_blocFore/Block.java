package projetIUT_blocFore;

import java.util.Arrays;
import java.util.Vector;

public class Block {
	private float[] dimensions = new float[3];
	private Face[] faces = new Face[6]; //[front, right, back, left, top, bottom]
	private IntersectionFace[] intersectionFaces = new IntersectionFace[12];
	private boolean[][] isIntersectionCreated = new boolean[6][6];
	
	public Block(float x, float y, float z) {
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
		faces[0] = new Face(dimensions[0], dimensions[1], 0, 0, 0);
			
		// Right face
		faces[1] = new Face(dimensions[2], dimensions[1], 0, -90, 0);
		
		// Back face
		faces[2] = new Face(dimensions[0], dimensions[1], 0, 180, 0);
		
		// Left face
		faces[3] = new Face(dimensions[2], dimensions[1], 0, 90, 0);
		
		// Top face
		faces[4] = new Face(dimensions[0], dimensions[1], 90, 0, 0);
		
		// Bottom face
		faces[5] = new Face(dimensions[0], dimensions[2], -90, 0, 0);
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
		for (int i = 0; i < 3; i++) {
			int diffRotBtwFaces = faces[idFace1].getRotation()[i] - faces[idFace2].getRotation()[i];
			if (Math.abs(diffRotBtwFaces) == 180) {
				return false;
			}
		}
		
		return true;
	}
	
	public float[] getDimensions() {
		return dimensions;
	}
}
