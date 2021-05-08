package Grupo45.Projeto;


import java.util.ArrayList;
import java.io.File;

/**
 * Date May 07-2021
	 *This class is responsible for searching files.
	 * 
	 * 
	 * @author G45
	 * @author Andre Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
 * @version 1.0
 *
 */
public class Search {
	private ArrayList<File> ficheiros = new ArrayList<>();

	/**
	* This method searches for .java files in the given directory
	* @param main  is the file where the method will search for java files
	* @return  returns an array of all java files
	*/
	public ArrayList<File> search(File main) {
		File[] files = main.listFiles();
		for (File file : files) {
			files_loader(file);
		}
		return ficheiros;
	}

	/**
	 * This method add .java files to an array.
	 * @param file is the file to be checked and added to the array.
	 */
	public void files_loader(File file) {
		if (file.isDirectory()) {
			this.search(file);
		}
		if (file.isFile() && file.getAbsolutePath().endsWith(".java")) {
			ficheiros.add(file);
		}
	}
}