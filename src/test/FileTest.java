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
	private JFileChooser dialogue;
	private ScannerFile scannerFile;

	@BeforeEach
	void getFile() {
		dialogue = new JFileChooser();
		dialogue.showOpenDialog(null);
		scannerFile = new ScannerFile(dialogue.getSelectedFile().toString());
	}

	@Test
	@Disabled
	void scanFileValid() {
		assertTrue(scannerFile.getFile().canExecute());
	}

	@Test
	@Disabled
	void structureFile() {
		try {
			System.out.println(scannerFile.verifStructureFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	void structureFileValid() {
		try {
			assertTrue(!scannerFile.verifStructureFile().isEmpty());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	void structureFileNotValid() {
		try {
			assertFalse(scannerFile.verifStructureFile().isEmpty());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
