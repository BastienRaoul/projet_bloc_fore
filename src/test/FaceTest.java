package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import mock.DrillingInterface;
import mock.DrillingMock;
import projetIUT_blocFore.Face;

class FaceTest {

	private static Face face;	
	private static ArrayList<DrillingInterface> drillings = new ArrayList<>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		face = new Face(50, 50, "+", 0/*0, 0, 0*/);
	}

	/*@AfterAll
	static void tearDownAfterClass() throws Exception {
	}*/

	@BeforeEach
	void setUp() throws Exception {
		assertTrue(face.getDrillings().size() == 0, "face drilling cleared");
		assertTrue(drillings.size() == 0, "drillings cleared");
	}

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
		
		String errMsg = face.verifyOwnDrillings();
		System.out.println("errMsg 1 : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement - Same face] (20, 10, 0, 50, 4) with (19, 10, 0, 50, 4)"), "drillings are not enough horizontally spaced");
	}
	
	@Test
	void verticalEspacementTest() {
		
		drillings.add(new DrillingMock(20, 10, 0, 50, 4));		
		drillings.add(new DrillingMock(20, 9, 0, 50, 4));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		System.out.println("errMsg 2 : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement - Same face] (20, 10, 0, 50, 4) with (20, 9, 0, 50, 4)"), "drillings are not enough vertically spaced");
	}
	
	@Test
	void goodEspacementTest() {
		
		drillings.add(new DrillingMock(20, 10, 0, 50, 4));		
		drillings.add(new DrillingMock(20, 13, 0, 50, 1));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals(""), "drillings are enough spaced");
	}
	
	@Test
	void wrongLeftTopEspacementTest() {
		
		drillings.add(new DrillingMock(2, 2, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement - Same face] (2, 2, 0, 50, 5)"), "Drilling too close from top and left sides");
	}
	
	@Test
	void leftSideCrossedTest() {
		
		drillings.add(new DrillingMock(-2, 30, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement - Same face] (-2, 30, 0, 50, 5)"), "Drilling too close from left side");
	}
	
	@Test
	void outOfBlockTest() {
		
		drillings.add(new DrillingMock(-20, 10, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement - Same face] (-20, 10, 0, 50, 5)"), "Drilling is outside the block");
	}
	
	@Test
	void topSideCrossedTest() {
		
		drillings.add(new DrillingMock(10, 70, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement - Same face] (10, 70, 0, 50, 5)"), "Drilling too close from top side");
	}
	
	@Test
	void rightSideCrossedTest() {
		
		drillings.add(new DrillingMock(70, 10, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement - Same face] (70, 10, 0, 50, 5)"), "Drilling too close from right side");
	}
	
	@Test
	void bottomSideCrossedTest() {
		
		drillings.add(new DrillingMock(-20, 10, 0, 50, 5));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Espacement - Same face] (-20, 10, 0, 50, 5)"), "Drilling too close from bottom side");
	}
	
	@Test
	void drillingInFaceTest() {
		
		drillings.add(new DrillingMock(5, 5, 0, 50, 2));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals(""), "The drilling belongs to the face");
	}

	@Test
	void noParentTest() {
		
		drillings.add(new DrillingMock(20, 20, 10, 50, 1));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Depth] (20, 20, 10, 50, 1) starts inside the block without \"parent drilling\""), "The drilling starts inside the block without parent");
	}
	
	//xavier.aime@univ-nantes.fr
	
	@Test
	void encompassButNotReachTest() {
		
		drillings.add(new DrillingMock(25, 20, 50, 30, 1));		
		drillings.add(new DrillingMock(25, 20, 0, 30, 2));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Depth] (25, 20, 50, 30, 1) starts inside the block without \"parent drilling\""), "Another drilling encompass the first one but doesn't reach it");
	}
	
	@Test
	void excentredEncompassTest() {
		
		drillings.add(new DrillingMock(30, 20, 30, 30, 1));
		drillings.add(new DrillingMock(30, 18, 0, 40, 6));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals(""), "Encompassed by an excentred drill");
	}
	
	@Test
	void reachedBySmallerDrillTest() {
		
		drillings.add(new DrillingMock(35, 20, 30, 30, 2));
		drillings.add(new DrillingMock(35, 20, 0, 40, 1));
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals("\n[Depth] (35, 20, 30, 30, 2) starts inside the block without \"parent drilling\""), "An inside drilling is reached by a smaller one");
	}
	
	@Test
	void multiParentedTest() {
		
		drillings.add(new DrillingMock(30, 30, 0, 10, 6));
		drillings.add(new DrillingMock(30, 30, 10, 5, 5));
		drillings.add(new DrillingMock(30, 30, 15, 15, 4));
		drillings.add(new DrillingMock(30, 30, 30, 8, 3));
		drillings.add(new DrillingMock(30, 30, 38, 2, 2));
		drillings.add(new DrillingMock(30, 30, 40, 5, 1)); 
		
		face.addDrillings(drillings);
		
		String errMsg = face.verifyOwnDrillings();
		//System.out.println("errMsg : " + errMsg);
		assertTrue(errMsg.equals(""), "The drilling is parented by many others");
	}
}
