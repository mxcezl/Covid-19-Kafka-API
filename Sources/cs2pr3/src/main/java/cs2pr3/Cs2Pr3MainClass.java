package cs2pr3;

import java.util.logging.Logger;

import cs2pr3.consumer.ConsumerUtils;

public class Cs2Pr3MainClass {	

	private static final Logger LOGGER = Logger.getLogger(Cs2Pr3MainClass.class.getName());
	
	public static void main(String[] args) {
		LOGGER.info("Démarrage du Consumer n°2 & Producer n°3\n");
		ConsumerUtils.runConsumer();
	}
}
