package cs2pr3.consumer;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

import cs2pr3.constants.Commandes;
import cs2pr3.constants.RetoursJSON;
import cs2pr3.covid.odt.ConfirmedAverage;
import cs2pr3.covid.odt.CountryDeathsAverage;
import cs2pr3.covid.odt.DeathsAverage;
import cs2pr3.covid.response.Country;
import cs2pr3.covid.response.CovidGlobalResponse;
import cs2pr3.postgres.PostgresUtils;
import cs2pr3.producer.ProducerUtils;

public class ConsumerUtils {

	private static final Logger LOGGER = Logger.getLogger(ConsumerUtils.class.getName());
	
	public static void runConsumer() {
		// Creation du consumer
		Consumer<String, String> consumer = ConsumerCreator.createConsumer();

		while (true) {
			@SuppressWarnings("deprecation") // consumer.poll(long) est déprécié
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			consumerRecords.forEach(record -> {
				
				LOGGER.info("Nouvelle commande utilisateur reçue : " + record.value());
				
				// Pour chaque commande recue
				// Nous traitons l'execution de la demande
				String retour = treatCommand(record.value());

				// Et nous ecrivons dans le topic 3 pour le retour a l'utilisateur
				try {
					LOGGER.info("Envoi de la réponse dans le topic Kafka n°3.");
					ProducerUtils.sendToTopic(retour);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			consumer.commitAsync();
		}
	}

	public static String treatCommand(String input) {

		// On vérifie que la commande est valide
		if(null != input && !input.isEmpty()) {

			Map<String,String> commandesMap = new HashMap<>();

			// En fonction de la commande recue, le traitement sera different
			// Si la commande est reconnue, une requete specifique sera executee
			// par la BDD Postgres.
			// Si elle n'est pas reconnue, un json specifique sera retourné
			input = input.toLowerCase();
			String commande = input.split(" ")[0];

			switch(commande) {
			case Commandes.GET_GLOBAL_VALUES:
				commandesMap.put(input, PostgresUtils.QUERY_GET_GLOBAL);
				break;

			case Commandes.GET_COUNTRY_VALUES:
				commandesMap.put(input, PostgresUtils.QUERY_COUNTRY.replace("$COUNTRY_CODE$", input.split(" ").length > 1 ? input.split(" ")[1].toUpperCase() : "FR"));
				break;

			case Commandes.GET_CONFIRMED_AVG:
			case Commandes.GET_DEATHS_AVG:
			case Commandes.GET_COUNTRIES_DEATHS_PERCENT:
			case Commandes.EXPORT:
				commandesMap.put(input, PostgresUtils.QUERY_ALL);
				break;

			default:
				return RetoursJSON.DEFAULT_RETURN_JSON; // Si la commande n'est pas reconnue
			}

			LOGGER.info("Interrogation de la base de données Postgres.");
			
			// Maintenant nous executons la requete et récuperons le resultat via la BDD Postgres
			Connection connection = PostgresUtils.createConnection(PostgresUtils.USERNAME, PostgresUtils.PASSWORD);

			String result = PostgresUtils.executeQuery(connection, commandesMap.get(input));

			LOGGER.info("Réponse de la base de données : " + result);

			// Ensuite nous récupérons le resultat et le formatons en objet Java en fonction de la demande
			if (!"NO_RESULT".equals(result)) {

				String retourJson = "{}";
				Gson gson = new Gson();
				CovidGlobalResponse cgr = null;
				long totalDeaths = 1;

				switch(input.split(" ")[0]) {
				case Commandes.GET_GLOBAL_VALUES:
				case Commandes.GET_COUNTRY_VALUES:
					return result;
					
				case Commandes.EXPORT:
					String xml = XML.toString(new JSONObject(result));
					retourJson = xml;
					break;

				case Commandes.GET_CONFIRMED_AVG:
					cgr = gson.fromJson(result, CovidGlobalResponse.class);
					ConfirmedAverage confirmedAverage = new ConfirmedAverage();

					confirmedAverage.setNombrePays(cgr.getCountries().size());

					long totalConfirmed = cgr.getGlobal().getTotalConfirmed();

					confirmedAverage.setConfirmedAverage(totalConfirmed / confirmedAverage.getNombrePays());

					retourJson = gson.toJson(confirmedAverage);
					break;

				case Commandes.GET_DEATHS_AVG:
					cgr = gson.fromJson(result, CovidGlobalResponse.class);
					totalDeaths = cgr.getGlobal().getTotalDeaths();

					DeathsAverage deathsAverage = new DeathsAverage();

					deathsAverage.setNombrePays(cgr.getCountries().size());
					deathsAverage.setDeathsAverage(totalDeaths / deathsAverage.getNombrePays());

					retourJson = gson.toJson(deathsAverage);
					break;

				case Commandes.GET_COUNTRIES_DEATHS_PERCENT:
					cgr = gson.fromJson(result, CovidGlobalResponse.class);
					List<CountryDeathsAverage> countryDeathsAverage = new ArrayList<>();

					for(Country country : cgr.getCountries()) {							
						CountryDeathsAverage cda = new CountryDeathsAverage();

						cda.setCountry(country.getCountry());
						cda.setCountryCode(country.getCountryCode());
						cda.setSlug(country.getSlug());
						cda.setDeathsAverage((country.getTotalDeaths() * 100)/ country.getTotalConfirmed());

						countryDeathsAverage.add(cda);
					}

					retourJson = gson.toJson(countryDeathsAverage);
					break;
				}

				return retourJson;
			} else {
				return RetoursJSON.ERREUR_EXECUTION_PG_JSON;
			}

		} else {
			return RetoursJSON.DEFAULT_RETURN_JSON;
		}
	}
}
