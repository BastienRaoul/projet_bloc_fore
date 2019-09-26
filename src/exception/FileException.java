package exception;

public class FileException extends Exception {
	public FileException() {
		System.out.println("Impossible d'accéder au fichier");
	}
}
