// dev : Bastien RAOUL

package projetIUT_blocFore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;


public class Main {
	
	private static String LOGS_FILE_NAME = "logs.txt";
	
	public static void main(String[] args) throws IOException {
		JFileChooser dialogue = new JFileChooser(); // Standard dialog for selecting a file from the file system
		File f = new File(new File(".").getCanonicalPath()); // Current Directory
		
		dialogue.setDialogTitle("Select a file");		
		dialogue.setCurrentDirectory(f);
		dialogue.showOpenDialog(null);

		try {
			String fileChosen = dialogue.getSelectedFile().toString();
			ScannerFile scannerFile = new ScannerFile(fileChosen);
			if (scannerFile.deserializeData()) {
				Block block = scannerFile.getBlock();
				String[] messages = block.inspectDrillings();
				
				if (messages[0].equals("")) { // No errors
					System.out.println("File analyzed !");
					System.out.println(messages[1]);
				} else {
					System.out.println(fileChosen + " contains invalid data");
					System.out.println("Consult logs in " + f + "\\" + LOGS_FILE_NAME);
					PrintWriter writer = new PrintWriter(f + "\\" + LOGS_FILE_NAME);
					writer.println(messages[0]);
					writer.close();
				}
			} else {
				System.out.println("Your file does not match \" ([1-9][0-9]*\\\\s*;\\\\s*){2}[1-9][0-9]*(\\\\r*FACE\\\\s*[1-6]\\\\r*(([-0-9]+\\\\s*,\\\\s*){4}[1-9][0-9]*\\\\r*)+){1,6} \"");
			}
		} catch (Exception e) {
			if(e.getMessage() == null) { System.out.println("Aucun fichier de sélectionné"); }
		}	
	}
}
