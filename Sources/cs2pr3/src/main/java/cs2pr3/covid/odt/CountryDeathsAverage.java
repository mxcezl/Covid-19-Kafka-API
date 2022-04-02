package cs2pr3.covid.odt;

public class CountryDeathsAverage {
	
	private String Country;
	private String CountryCode;
	private String Slug;
	private double DeathsAverage;
	
	public CountryDeathsAverage() {
		super();
	}

	public CountryDeathsAverage(String country, String countryCode, String slug, double deathsAverage) {
		super();
		Country = country;
		CountryCode = countryCode;
		Slug = slug;
		DeathsAverage = deathsAverage;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCountryCode() {
		return CountryCode;
	}

	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}

	public String getSlug() {
		return Slug;
	}

	public void setSlug(String slug) {
		Slug = slug;
	}

	public double getDeathsAverage() {
		return DeathsAverage;
	}

	public void setDeathsAverage(double deathsAverage) {
		DeathsAverage = deathsAverage;
	}

	@Override
	public String toString() {
		return "CountryDeathsAverage [Country=" + Country + ", CountryCode=" + CountryCode + ", Slug=" + Slug
				+ ", DeathsAverage=" + DeathsAverage + "]";
	}
}
