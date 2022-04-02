package pr1.cron;

import java.io.IOException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import pr1.kafka.KafkaConstants;
import pr1.producer.ProducerCreator;
import pr1.producer.ProducerUtils;
import pr1.utils.Formater;
import pr1.utils.JsonReader;

public class TopicJob implements Job{

	public static final String API_URL = "https://api.covid19api.com/summary";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			sendToTopic(callCovidApi());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	
	private static String callCovidApi() throws JSONException, IOException {
		JSONObject json = JsonReader.readJsonFromUrl(API_URL);
		json = Formater.traitementJson(json);
		return json.toString();
	}
	
	private static void sendToTopic(String data) throws JSONException, IOException {
		Producer<String, String> producer = ProducerCreator.createProducer();
		
		final ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaConstants.TOPIC_1,
				data);
		
		ProducerUtils.sendRecord(producer, record);
	}
}
