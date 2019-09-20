package org.projet_iut.bloc_fore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	private static Scanner input;
	private static File file;
		
	public static void main(String[] args) throws FileNotFoundException {	
		readFile();
		System.out.println(verifStructureFile());
	}
	
	public static void readFile() {
	// /home/e174687c/Réseau/Perso/Documents/LP MiAR/projet_bloc_fore/src/org/projet_iut/bloc_fore/TextSample.txt
		try {
//            System.out.print("Enter the file name with extension : ");
            
            input = new Scanner("/home/e174687c/Réseau/Perso/Documents/LP MiAR/projet_bloc_fore/src/org/projet_iut/bloc_fore/TextSample.txt");

            file = new File(input.nextLine());    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public static Boolean verifStructureFile() throws FileNotFoundException {
		input = new Scanner(file);
		int nbLine = 0;
		Boolean validiteFileBoolean = true;
		String[] tabFile;
		
//		while (input.hasNextLine()) {
//            String line = input.nextLine();
//            System.out.println(line);
//        }
		
		while (input.hasNextLine()) { 
			String line = input.nextLine();
			nbLine++; 
		}
		
		for(int i=0; i < nbLine; i++) {
			if(i == 0) {
				String regExp = "[0-9]+;+[0-9]+;+[0-9]";
//				String line = input.nextLine();
//				validiteFileBoolean = line.matches(regExp);
				
			}
		}
		input.close();
		return validiteFileBoolean;
	}

}
