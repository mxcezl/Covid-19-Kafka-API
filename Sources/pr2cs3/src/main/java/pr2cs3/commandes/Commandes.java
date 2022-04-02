package pr2cs3.commandes;

public enum Commandes {
	HELP("help"),
	GET_GLOBAL_VALUES("get_global_values"),
	GET_COUNTRY_VALUES("get_country_values"),
	GET_CONFIRMED_AVG("get_confirmed_avg"),
	GET_DEATHS_AVG("get_deaths_avg"),
	GET_COUNTRIES_DEATHS_PERCENT("get_countries_deaths_percent"),
	EXPORT("export"),
	EXIT("exit");
	
	private String value;
	
	private Commandes (String _value) {
		this.value = _value;
	}
	
	public String getValue() {
		return this.value;
	}
}
