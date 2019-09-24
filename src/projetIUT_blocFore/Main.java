package projetIUT_blocFore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

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
            input = new Scanner("C:\\Users\\basti\\OneDrive\\Documents\\LP MiAR\\projet_bloc_fore\\src\\projetIUT_blocFore\\TextSample.txt");
            file = new File(input.nextLine());    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public static void verifStructureFile() throws FileNotFoundException {
		input = new Scanner(file);
		ArrayList<String> lignes = new ArrayList<String>();
		ArrayList<String> resultat = new ArrayList<String>();
        Boolean face1 = false, face2 = false, face3 = false, face4 = false;
		
		while (input.hasNextLine()) { lignes.add(input.nextLine()); }

        if(!Pattern.compile("([0-9]+\\s*[;]\\s*){2}[0-9]+$").matcher(lignes.get(0)).find()) {
        	resultat.add("Erreur de syntaxe -> " + lignes.get(0));
        }
        
		for (String ligne : lignes) {
			if(!Pattern.compile("[A-Z]+[0-9]$").matcher(ligne).find()) {
				if(!Pattern.compile("([0-9]+[,]\\s){4}[0-9]+$").matcher(ligne).find()) {
					if(!Pattern.compile("([0-9]+\\s*[;]\\s*){2}[0-9]+$").matcher(ligne).find()) {
						resultat.add("Erreur de syntaxe -> " + ligne);
					}
				}
			}else if(Pattern.compile("FACE1").matcher(ligne).find()) {
				face1 = true;
			} else if(Pattern.compile("FACE2").matcher(ligne).find()) {
				face2 = true;
			} else if(Pattern.compile("FACE3").matcher(ligne).find()) {
				face3 = true;
			} else if(Pattern.compile("FACE4").matcher(ligne).find()) {
				face4 = true;
			}	
			
		}
		if(!face4) {
			resultat.add(0, "Face 4 non déclaré");
		}
		if(!face3) {
			resultat.add(0, "Face 3 non déclaré");
		} 
		if(!face2) {
			resultat.add(0, "Face 2 non déclaré");
		}
		if(!face1) {
			resultat.add(0, "Face 1 non déclaré");
		}
		
		System.out.println(resultat);
		input.close();
	}

}
