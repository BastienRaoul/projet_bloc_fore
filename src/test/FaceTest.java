package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mock.DrillingInterface;
import mock.DrillingMock;
import projetIUT_blocFore.Face;

class FaceTest {

	private static Face face;	
	private static ArrayList<DrillingInterface> drillings = new ArrayList<>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		face = new Face(50, 50, 0, 0, 0);

		/* Tests manquants
			
			// To test depth
		drillings.add(new DrillingMock(20, 20, 10, 50, 1));		// Has no parent
		drillings.add(new DrillingMock(25, 20, 50, 30, 1));		// Another drill (the next one) encompass it but doesn't reach it
		drillings.add(new DrillingMock(25, 20, 0, 30, 2));
		drillings.add(new DrillingMock(30, 20, 30, 30, 1));		// encompassed by an excentred drill
		drillings.add(new DrillingMock(30, 18, 0, 40, 6));
		drillings.add(new DrillingMock(35, 20, 30, 30, 2));		// reached by another drill but smaller
		drillings.add(new DrillingMock(35, 20, 0, 40, 1));
		drillings.add(new DrillingMock(30, 30, 50, 40, 1)); 	// A drilling with 2 parents
		drillings.add(new DrillingMock(30, 30, 30, 20, 2));
		drillings.add(new DrillingMock(30, 30, 0, 30, 3));*/
	}

	/*@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}*/

	@AfterEach
	void tearDown() throws Exception {
		face.clearDrillings();
		drillings.clear();
	}

	@Test
	void addDrillingsTest() {
		
		drillings.add(new DrillingMock(20, 10, 0, 50, 4));			
		drillings.add(new DrillingMock(19, 10, 0, 50, 4));	
		drillings.add(new DrillingMock(20, 9, 0, 50, 4));
		drillings.add(new DrillingMock(20, 13, 0, 50, 1));
		
		assertTrue(face.getDrillings().size() == 0, "drillings list empty");
		
		face.addDrillings(drillings);
		ArrayList<DrillingInterface> faceDrillings = face.getDrillings();
		
		assertTrue(faceDrillings.size() == drillings.size(), "drilling list has the correct size");
		assertTrue(faceDrillings.get(0) == drillings.get(0), "first element of the drilling list is the good one");
		assertTrue(
			faceDrillings.get(faceDrillings.size()-1) == drillings.get(drillings.size()-1), 
			"last element of the drilling list is the good one"
		);
	}
	
	@Test
	void horizontalEspacementTest() {
		
		drillings.add(new DrillingMock(20, 10, 0, 50, 4));		
		drillings.add(new DrillingMock(19, 10, 0, 50, 4));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement] (20, 10, 0, 50, 4) with (19, 10, 0, 50, 4)"), "drillings are not enough horizontally spaced");
	}
	
	@Test
	void verticalEspacementTest() {
		
		drillings.add(new DrillingMock(20, 10, 0, 50, 4));		
		drillings.add(new DrillingMock(20, 9, 0, 50, 4));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement] (20, 10, 0, 50, 4) with (20, 9, 0, 50, 4)"), "drillings are not enough vertically spaced");
	}
	
	@Test
	void goodEspacementTest() {
		
		drillings.add(new DrillingMock(20, 10, 0, 50, 4));		
		drillings.add(new DrillingMock(20, 13, 0, 50, 1));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals(""), "drillings are enough spaced");
	}
	
	@Test
	void wrongLeftTopEspacementTest() {
		
		drillings.add(new DrillingMock(2, 2, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement] (2, 2, 0, 50, 5)"), "Drilling too close from top and left sides");
	}
	
	@Test
	void leftSideCrossedTest() {
		
		drillings.add(new DrillingMock(-2, 30, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement] (-2, 30, 0, 50, 5)"), "Drilling too close from left side");
	}
	
	@Test
	void outOfBlockTest() {
		
		drillings.add(new DrillingMock(-20, 10, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement] (-20, 10, 0, 50, 5)"), "Drilling is outside the block");
	}
	
	@Test
	void topSideCrossedTest() {
		
		drillings.add(new DrillingMock(10, 70, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement] (10, 70, 0, 50, 5)"), "Drilling too close from top side");
	}
	
	@Test
	void rightSideCrossedTest() {
		
		drillings.add(new DrillingMock(70, 10, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement] (70, 10, 0, 50, 5)"), "Drilling too close from right side");
	}
	
	@Test
	void bottomSideCrossedTest() {
		
		drillings.add(new DrillingMock(-20, 10, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement] (-20, 10, 0, 50, 5)"), "Drilling too close from bottom side");
	}
	
	@Test
	void drillingInFaceTest() {
		
		drillings.add(new DrillingMock(5, 5, 0, 50, 2));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyCoplanarDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals(""), "Drilling belongs to the face");
	}

}