// dev : Rémi THOMAS

package projetIUT_blocFore;

import java.util.Arrays;
import java.util.List;

import mock.DrillingInterface;

public class IntersectionFace {
	private Face faceX;
	private List<DrillingInterface> drillingsX;
	private Face faceY;
	private List<DrillingInterface> drillingsY;
	private int[][] intersections; // Values : 0 = no intersection ; 1 = intersection ; -1 = not defined

	public IntersectionFace(Face faceX, Face faceY) {
		this.faceX = faceX;
		drillingsX = faceX.getDrillings();
		this.faceY = faceY;
		drillingsY = faceY.getDrillings();
		intersections = new int[drillingsY.size()][drillingsX.size()];
		Arrays.fill(intersections, -1);
	}

	public String getIntersections() {

		String intersectionsToString = "";

		for (int j = 0; j < faceY.getDrillings().size(); j++) {
			for (int i = 0; i < faceX.getDrillings().size(); i++) {
				if (intersections[j][i] == 1) {
					intersectionsToString += drillingsY.get(j).toString() + " " + faceY.toString() + faceX.toString()
							+ " " + drillingsX.get(i) + "\n";
				}
			}
		}
		return intersectionsToString;
	}
}
