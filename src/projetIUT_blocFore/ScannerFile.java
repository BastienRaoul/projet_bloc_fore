// dev : Remi THOMAS

package projetIUT_blocFore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import mock.DrillingInterface;

public class ScannerFile {
	
	private File file;
	private String data = "";
	
	private Block block;
	private HashMap<Integer, ArrayList<DrillingInterface>> drillings = new HashMap<>();

	public ScannerFile(String path) {		
		
		try {
			file = new File(path);
			
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			String line;
			
			while ((line = br.readLine()) != null) {
				data += line;
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return a boolean which indicates if the file is well structured
	 */
	public boolean deserializeData() {

		if (!Pattern.compile("([1-9][0-9]*\\s*;\\s*){2}[1-9][0-9]*(\\r*FACE\\s*[1-6]\\r*(([-0-9]+\\s*,\\s*){4}[1-9][0-9]*\\r*)+){1,6}").matcher(data).find()) return false;
		// I didn't find how to say where it didn't match
		
		System.out.println("File OK");
		
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			String line;
			
			int idCurrentFace = 0;
			while ((line = br.readLine()) != null) {
				
				// Create the block
				if (Pattern.compile("(\\s*[1-9][0-9]*\\s*;\\s*){2}[1-9][0-9]*").matcher(line).find()) {
					createBlock(line);
					
				// Set the current face to add the following drilling to it
				} else if (Pattern.compile("\\s*FACE\\s*[1-6]\\s*").matcher(line).find()) {
					int length = line.length();
					idCurrentFace = Integer.parseInt(line.replaceAll("\\s", "").substring(length-1, length));
					System.out.println("Entering face " + idCurrentFace + " data");
				
				// Create a drilling for the concerned face
				} else if (Pattern.compile("\\s*([-0-9]+\\s*,\\s*){4}[1-9][0-9]*\\s*").matcher(line).find()) {
					createDrillingForFace(line, idCurrentFace);
				}
			}
			
			// Add all drillings the their corresponding face
			for (int i = 0; i < drillings.size(); i++) {
				block.getFace(i).addDrillings(drillings.get(i));
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private void createBlock(String data) {
		
		System.out.println("Creating block with : " + data);
		String[] dimStr = data.replaceAll("\\s", "").split(";");
		int[] dim = new int[dimStr.length];
		
		for (int i = 0; i < dimStr.length; i++) {
			dim[i] = Integer.parseInt(dimStr[i]);
		}
		
		block = new Block(dim[0], dim[1], dim[2]);
		
		System.out.println("Block created");
	}
	
	private void createDrillingForFace(String data, int idFace) {
		
		System.out.println("Creating drilling for face " + idFace + " with : " + data);
		String[] paramStr = data.replaceAll("\\s", "").split(",");
		int[] param = new int[paramStr.length];
		
		for (int i = 0; i < paramStr.length; i++) {
			param[i] = Integer.parseInt(paramStr[i]);
		}
		
		int[] coords = {param[0], param[1], param[2]}; // x, y, z of the drilling / the rest is depth and diameter
		DrillingInterface drilling = new Drilling(block, block.getFace(idFace-1), coords, param[3], param[4]);
		
		ArrayList<DrillingInterface> newDrillingList = drillings.get(idFace);
		newDrillingList.add(drilling);
		drillings.put(idFace, newDrillingList);
	}
	
	public Block getBlock() {
		return block;
	}
}
