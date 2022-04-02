package pr2cs3.commandes;

public enum DescriptionCommandes {
	HELP("Affiche les commandes disponibles dans le programe."),
	GET_GLOBAL_VALUES("Retourne les valeurs globales cl�s Global du fichier json."),
	GET_COUNTRY_VALUES("Retourne les valeurs du pays demand� ou V_PAYS est une chaine de caract�re du pays demand�. (Eg: \"get_country_values FR\")"),
	GET_CONFIRMED_AVG("Retourne une moyenne des cas confirm�s par pays."),
	GET_DEATHS_AVG("Retourne une moyenne des d�c�s par pays."),
	GET_COUNTRIES_DEATHS_PERCENT("Retourne le pourcentage de d�c�s par rapport aux cas confirm�s."),
	EXPORT("Permet d�exporter les donn�es de la base de donn�es en XML."),
	EXIT("Ferme le programme");
	
	private String value;
	
	private DescriptionCommandes (String _value) {
		this.value = _value;
	}
	
	public String getValue() {
		return this.value;
	}
}
