package projetIUT_blocFore;

import java.io.FileNotFoundException;

import javax.swing.*;

import exception.FileException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		JFileChooser dialogue = new JFileChooser();
		dialogue.showOpenDialog(null);

		ScannerFile scannerFile = new ScannerFile(dialogue.getSelectedFile().toString());
		System.out.println(scannerFile.verifStructureFile());
	}
}
