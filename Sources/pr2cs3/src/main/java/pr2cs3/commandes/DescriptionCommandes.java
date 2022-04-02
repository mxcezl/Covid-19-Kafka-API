package pr2cs3.commandes;

public enum DescriptionCommandes {
	HELP("Affiche les commandes disponibles dans le programe."),
	GET_GLOBAL_VALUES("Retourne les valeurs globales clés Global du fichier json."),
	GET_COUNTRY_VALUES("Retourne les valeurs du pays demandé ou V_PAYS est une chaine de caractère du pays demandé. (Eg: \"get_country_values FR\")"),
	GET_CONFIRMED_AVG("Retourne une moyenne des cas confirmés par pays."),
	GET_DEATHS_AVG("Retourne une moyenne des décès par pays."),
	GET_COUNTRIES_DEATHS_PERCENT("Retourne le pourcentage de décès par rapport aux cas confirmés."),
	EXPORT("Permet d’exporter les données de la base de données en XML."),
	EXIT("Ferme le programme");
	
	private String value;
	
	private DescriptionCommandes (String _value) {
		this.value = _value;
	}
	
	public String getValue() {
		return this.value;
	}
}
