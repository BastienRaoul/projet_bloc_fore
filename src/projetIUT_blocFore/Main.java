package projetIUT_blocFore;

import java.io.FileNotFoundException;

import javax.swing.*;

public class Main {		
	public static void main(String[] args) {	
        JFileChooser dialogue = new JFileChooser(); 
        dialogue.showOpenDialog(null);       
        
        ScannerFile scannerFile = new ScannerFile(dialogue.getSelectedFile().toString());
		try {
			System.out.println(scannerFile.verifStructureFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}       
	}
}
