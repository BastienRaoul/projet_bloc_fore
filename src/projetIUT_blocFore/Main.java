package projetIUT_blocFore;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	private static Scanner input;
	private static File file;
		
	public static void main(String[] args) throws FileNotFoundException {	
		readFile();
		verifStructureFile();
	}
	
	public static void readFile() throws FileNotFoundException {
		try {
//          System.out.print("Enter the file name with extension : ");            
            input = new Scanner("/home/e174687c/Réseau/Perso/Documents/LP MiAR/projet_bloc_fore/src/projetIUT_blocFore/TextSample.txt");
            file = new File(input.nextLine());    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public static void verifStructureFile() throws FileNotFoundException {
		input = new Scanner(file);
		ArrayList<String> lignes = new ArrayList<String>();
		ArrayList<String> resultat = new ArrayList<String>();
		
		while (input.hasNextLine()) { lignes.add(input.nextLine()); }
		
        if(!Pattern.compile("([0-9]+\\s*[;]\\s*){2}[0-9]+").matcher(lignes.get(0)).find()) {
        	resultat.add("Erreur de syntaxe -> " + lignes.get(0));
        }
        
        Boolean face = false;
		for (String ligne : lignes) {
			if(face) {
				if(Pattern.compile("[A-Z]+[0-9]").matcher(ligne).find()) {
					if (!Pattern.compile("([0-9]+\\s*[,]\\s*){4}[0-9]+").matcher(ligne).find()){
						resultat.add("Erreur de syntaxe -> " + ligne);
					}
				} else if (!Pattern.compile("([0-9]+\\s*[,]\\s*){4}[0-9]+").matcher(ligne).find()){
					resultat.add("Erreur de syntaxe -> " + ligne);
				}
			} else {
				if(Pattern.compile("[A-Z]+[0-9]").matcher(ligne).find()) {
					face = true;
				} else {
					resultat.add("Erreur de syntaxe -> " + ligne);
				}
			}
		}
		
		System.out.println(resultat);
		input.close();
	}

}
