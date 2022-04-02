package pr2cs3.commandes;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pr2cs3.consumer.ConsumerCreator;
import pr2cs3.producer.ProducerCreator;
import pr2cs3.producer.ProducerUtils;

public class CommandesUtils {
	
	private Map<String, String> commandesDef;
	private Producer<String, String> producer;
	private Consumer<String, String> consumer;
	private static final Logger LOGGER = Logger.getLogger(CommandesUtils.class.getName());
	
	public CommandesUtils() {
		this.setProducer(ProducerCreator.createProducer());
		this.setConsumer(ConsumerCreator.createConsumer());
		
		this.getCommandesDef().put(Commandes.HELP.getValue(), DescriptionCommandes.HELP.getValue());
		this.getCommandesDef().put(Commandes.GET_GLOBAL_VALUES.getValue(), DescriptionCommandes.GET_GLOBAL_VALUES.getValue());
		this.getCommandesDef().put(Commandes.GET_COUNTRY_VALUES.getValue() + " V_PAYS", DescriptionCommandes.GET_COUNTRY_VALUES.getValue());
		this.getCommandesDef().put(Commandes.GET_CONFIRMED_AVG.getValue(), DescriptionCommandes.GET_CONFIRMED_AVG.getValue());
		this.getCommandesDef().put(Commandes.GET_DEATHS_AVG.getValue(), DescriptionCommandes.GET_DEATHS_AVG.getValue());
		this.getCommandesDef().put(Commandes.GET_COUNTRIES_DEATHS_PERCENT.getValue(), DescriptionCommandes.GET_COUNTRIES_DEATHS_PERCENT.getValue());
		this.getCommandesDef().put(Commandes.EXPORT.getValue(), DescriptionCommandes.EXPORT.getValue());
		this.getCommandesDef().put(Commandes.EXIT.getValue(), DescriptionCommandes.EXIT.getValue());
	}
	
	public void afficherCommandes() {
		System.out.println("\nCommandes disponibles :");
		this.getCommandesDef().forEach((commande, description) -> {
			System.out.println("  [*] " + commande + " : " + description);
		});
	}

	/**
	 * Cette fonction permet d'executer les differentes commandes disponibles
	 * @param commande
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public void execute(String commande) throws SAXException, IOException {
		if (Commandes.HELP.getValue().equals(commande)) {
			this.afficherCommandes();
			
		} else if (Commandes.EXIT.getValue().equals(commande)) {
			
			// Fermeture des flux
			this.getConsumer().close();
			this.getProducer().close();
			
			System.out.println("\n---------------------------\nMerci de votre utilisation.\nA bientot !\n---------------------------");
			System.exit(0);

		} else {
			LOGGER.info("Envoi de la commande dans le topic 2...");
			
			try {
				// Envoi de la commande
				ProducerUtils.sendToTopic(commande);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Reception de la réponse
			try {
				this.readResponse(Commandes.EXPORT.getValue().equals(commande));
			} catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
				LOGGER.severe("Erreur lors de la lecture de la reponse");
				e.printStackTrace();
			}
		}
	}

	private void readResponse(boolean export) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		
		boolean messageLu = false;
		
		while (!messageLu) {
			@SuppressWarnings("deprecation") // consumer.poll(long) est déprécié
			final ConsumerRecords<String, String> consumerRecords = this.getConsumer().poll(1000);
			
			for (ConsumerRecord<String, String> record : consumerRecords) {
				
				System.out.println("\nRéponse reçue : ");
				if (export) {
					System.out.println(record.value().replaceAll("--><", "-->\n<"));
				} else {
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					JsonParser jp = new JsonParser();
					JsonElement je = jp.parse(record.value());
					String prettyJsonString = gson.toJson(je);
					
					System.out.println(prettyJsonString);
				}
				
				messageLu = true;
			}
			consumer.commitAsync();
		}
	}
	
	/**
	 * Retourne vrai si la commande est valide sytaxiquement et fonctionnellement dans le programme
	 * Si la commande n'est pas valide, un message informatif est affiché à l'utilisateur
	 * @param cmd
	 * @return
	 */
	public boolean isValid(String cmd) {
		
		// Si la commande recue n'est pas nulle et qu'elle existe
		if (null != cmd && !cmd.isEmpty() && this.isExist(cmd)) {
			
			// Verification du format de la commande s'il y a un parametre a renseigner (V_PAYS par exemple)
			if (Commandes.GET_COUNTRY_VALUES.getValue().equals(cmd.split(" ")[0]) && cmd.split(" ").length != 2) {
				System.out.println("Parametre V_PAYS invalide pour la commande.\nExemple : get_country_values FR");
				return false;
			
			} else if (Commandes.GET_COUNTRY_VALUES.getValue().equals(cmd.split(" ")[0]) && cmd.split(" ").length == 2) {
				return true;
			}
			
			// Si c'est une autre commande que GET_COUNTRY_VALUES, alors il n'y a pas de parametre a recuperer
			// Donc si on a plus de choses qu'une commande, c'est que la requête est invalide
			if (cmd.split(" ").length != 1) {
				return false;
			}
			
			// Si tout les controles sont passés, alors la commande est valide
			return true;
		}

		// Sinon, par defaut, la commande n'est pas consideree comme valide
		return false;
	}

	/**
	 * Retourne vrai si la commande passée en paramètre est connue du systeme et faux dans le cas contraire
	 * @param commande
	 * @return
	 */
	private boolean isExist(String commande) {
		return Arrays.asList(Commandes.values()).stream()
				.map(c -> c.getValue().split(" ")[0])
				.collect(Collectors.toList())
				.contains(commande.split(" ")[0]);
	}

	// Getter / Setter
	
	public Map<String, String> getCommandesDef() {
		if (null == this.commandesDef) this.commandesDef = new HashMap<>();
		return this.commandesDef;
	}
	
	public void setCommandesDef(Map<String, String> commandesDef) {
		this.commandesDef = commandesDef;
	}

	public Producer<String, String> getProducer() {
		return producer;
	}

	public void setProducer(Producer<String, String> producer) {
		this.producer = producer;
	}

	public Consumer<String, String> getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer<String, String> consumer) {
		this.consumer = consumer;
	}
}
