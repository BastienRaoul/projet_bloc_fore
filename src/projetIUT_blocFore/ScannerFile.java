// dev : Bastien RAOUL

package projetIUT_blocFore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerFile {
	private File file;
	private Scanner input;
	private ArrayList<String> resultat = new ArrayList<String>();
	private Boolean face1 = false;
	private Boolean face2 = false;
	private Boolean face3 = false;
	private Boolean face4 = false;
	private Boolean face5 = false;
	private Boolean face6 = false;
	private Block block;

	public ScannerFile(String path) {
		try {
			input = new Scanner(path);
			file = new File(input.nextLine());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
 
	/**
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * 
	 * @return a boolean allowing to know if the file is written correctly
	 * @throws FileNotFoundException
	 */
	public Boolean validStructure() throws FileNotFoundException {
		Boolean result = false;
		ArrayList<String> text = verifStructureFile();
		result = text.isEmpty() ? true : false;
		return result;
	}

	/**
	 * 
	 * @return an ArrayList with every line of the file
	 * @throws FileNotFoundException
	 */
	public ArrayList<String> getLignes() throws FileNotFoundException {
		ArrayList<String> lignes = new ArrayList<String>();
		input = new Scanner(file);
		while (input.hasNextLine()) { lignes.add(input.nextLine()); }
		return lignes;
	}

	/**
	 * 
	 * @return an ArrayList with the errors in the file
	 * @throws FileNotFoundException
	 */
	public ArrayList<String> verifStructureFile() throws FileNotFoundException {
		input = new Scanner(file);
		ArrayList<String> lignes = getLignes();

		if (!Pattern.compile("([0-9]+\\s*[;]\\s*){2}[0-9]+$").matcher(lignes.get(0)).find()) {
			resultat.add("Erreur de syntaxe -> " + lignes.get(0));
		} else {
			String ligne = lignes.get(0);			
	        String mots[] = ligne.split(" ");	 
	        ArrayList<String> values = new ArrayList<String>();
	        for (String i : mots) { values.add(i); }	        
//	        bloc = new Bloc(Float.parseFloat(values.get(0)), Float.parseFloat(values.get(2)), Float.parseFloat(values.get(4)));
		}

		lignes.remove(0);

		for (String ligne : lignes) {
			if (!Pattern.compile("[A-Z]+[0-9]$").matcher(ligne).find()) {
				if (!Pattern.compile("([0-9]+[,]\\s){4}[0-9]+$").matcher(ligne).find()) {
					resultat.add("Erreur de syntaxe -> " + ligne);					
				}
			} else if (Pattern.compile("FACE1").matcher(ligne).find()) {
				face1 = true;
			} else if (Pattern.compile("FACE2").matcher(ligne).find()) {
				face2 = true;
			} else if (Pattern.compile("FACE3").matcher(ligne).find()) {
				face3 = true;
			} else if (Pattern.compile("FACE4").matcher(ligne).find()) {
				face4 = true;
			} else if (Pattern.compile("FACE5").matcher(ligne).find()) {
				face5 = true;
			} else if (Pattern.compile("FACE6").matcher(ligne).find()) {
				face6 = true;
			}

		}
		if (!face6) {
			resultat.add(0, "Face 6 non déclarée");
		}
		if (!face5) {
			resultat.add(0, "Face 5 non déclarée");
		}
		if (!face4) {
			resultat.add(0, "Face 4 non déclarée");
		}
		if (!face3) {
			resultat.add(0, "Face 3 non déclarée");
		}
		if (!face2) {
			resultat.add(0, "Face 2 non déclarée");
		}
		if (!face1) {
			resultat.add(0, "Face 1 non déclarée");
		}

		input.close();
		return resultat;
	}
}
