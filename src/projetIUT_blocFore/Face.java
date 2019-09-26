package projetIUT_blocFore;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineListener;

public class Face {

	private static int id = 0;
	private float[] dimensions;
	private int[] rotation = new int[3];
	private List<Drilling> drillings = new ArrayList<Drilling>();

	public Face(float dimX, float dimY, int rotX, int rotY, int rotZ) {
		id++;
		dimensions = new float[] { dimX, dimY };
		rotation = new int[] { rotX, rotY, rotZ };
	}

	public void addDrilling(Drilling drilling) {
		drillings.add(drilling);
	}

	@Override
	public String toString() {
		return "Face " + id;
	}

	public List<Drilling> getDrillings() {
		return drillings;
	}

	public int[] getRotation() {
		return rotation;
	}
}
