package pr1.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class Formater {

	public static JSONObject traitementJson(JSONObject json) {
		json.remove("Message");
		json.remove("ID");
		
		JSONArray countriesJson = json.getJSONArray("Countries");
		for (int i = 0; i < countriesJson.length(); i++) {
		    JSONObject item = countriesJson.getJSONObject(i);
		    item.remove("Premium");
		    item.remove("ID");
		    countriesJson.put(i, item);
		}
		
		json.put("Countries", countriesJson);
		return json;
	}
}
