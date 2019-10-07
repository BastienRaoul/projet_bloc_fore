package projetIUT_blocFore;

import java.io.File;
import java.io.IOException;

import javax.swing.*;


public class Main {
	public static void main(String[] args) throws IOException {
		JFileChooser dialogue = new JFileChooser(); // Standard dialog for selecting a file from the file system
		File f = new File(new File(".").getCanonicalPath()); // Current Directory
		
		dialogue.setDialogTitle("Select a file");		
		dialogue.setCurrentDirectory(f);
		dialogue.showOpenDialog(null);

		try {
			ScannerFile scannerFile = new ScannerFile(dialogue.getSelectedFile().toString());
			System.out.println(scannerFile.verifStructureFile());
		} catch (Exception e) {
			if(e.getMessage() == null) { System.out.println("Aucun fichier de sélectionné"); }
		}		
	}
}
