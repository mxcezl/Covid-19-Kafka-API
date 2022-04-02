package pr2cs3;

import java.io.IOException;
import java.util.Scanner;

import org.xml.sax.SAXException;

import pr2cs3.commandes.CommandesUtils;

public class Pr2Cs3MainClass {
	
	private static final Scanner SCANNER = new Scanner(System.in); // Pour la lecture des user inputs
	private static final CommandesUtils CMD_UTILS = new CommandesUtils(); // Affichage des commandes
	
	public static void main(String[] args) {
		
		System.out.println("[INFO] Démarrage du Producer n°2 & Consumer n°3");
		System.out.println("[INFO] Veuillez patienter, le CLI démarre ...");
		
		//FIXME Quoi faire s'il y a une desynchrinisation du topic?
		// Eg : si le Pr2Cs3 est lancé alors qu'il ya deja des messages dans le topic
		
		while (true) {			
			// Lecture de la commande user
			String commande = readUserInput();
			
			// Execution de la commande (send to topic : producer)
			try {
				CMD_UTILS.execute(commande);
			} catch (SAXException | IOException e) {
				System.out.println("[ERROR] Erreur lors de l'execution de la commande.");
				e.printStackTrace();
			}
		}
	}

	private static String readUserInput() {
		String uInput = "";
		do {
			System.out.print("\nEntrez votre commande (ex: help) : ");
			uInput = SCANNER.nextLine().toLowerCase();
			
			if (!CMD_UTILS.isValid(uInput)) {
				System.out.println("\n/!\\ Commande non reconnue, veuillez taper \"help\" pour plus d'informations.");
			}
			
		} while (!CMD_UTILS.isValid(uInput));
		
		return uInput;
	}
}