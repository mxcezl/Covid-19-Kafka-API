package pr1.producer;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerUtils {

	private static final Logger LOGGER = Logger.getLogger(ProducerUtils.class.getName());
	
	public static RecordMetadata sendRecord(Producer<String, String> producer, ProducerRecord<String, String> record) {
		RecordMetadata metadata = null;
		try {
			metadata = producer.send(record).get();
			LOGGER.info(new Date() + " - Enregistrement envoyé vers la partition " + metadata.partition() + " Et l'offset " + metadata.offset());
			LOGGER.info("La mise à jour envoyée dans le topic Kafka.");
		} catch (Exception e) {
			LOGGER.severe("Erreur dans l'envoi de l'enregistrement");
			e.printStackTrace();
		}		
		return metadata;
	}
}
