// dev : Bastien RAOUL

package test;

import projetIUT_blocFore.ScannerFile;

import org.junit.*;

import javax.swing.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

public class FileTest {
	
	
	// DEPRECATED
	
	
	/*private JFileChooser dialogue;
	private ScannerFile scannerFile;
	
	@BeforeEach
	void getFile() {
		dialogue = new JFileChooser();
		dialogue.showOpenDialog(null);
		scannerFile = new ScannerFile(dialogue.getSelectedFile().toString());
	}

	@Test
	void scanFileValid() {
		assertTrue(scannerFile.getFile().canExecute());
	}

	@Test
	//@Disabled
	void structureFile() {
		try {
			System.out.println(scannerFile.verifStructureFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	//@Disabled
	void structureFileValid() {
		try {
			assertTrue(!scannerFile.verifStructureFile().isEmpty());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	//@Disabled
	void structureFileNotValid() {
		try {
			assertFalse(scannerFile.verifStructureFile().isEmpty());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}*/
}
