package cs2pr3.covid.odt;

import java.util.List;

public class CountryDeathsList {
	
	private long TotalDeaths;
	private List<CountryDeathsAverage> Countries;
	
	public CountryDeathsList() {
		super();
	}

	public CountryDeathsList(long totalDeaths, List<CountryDeathsAverage> countries) {
		super();
		TotalDeaths = totalDeaths;
		Countries = countries;
	}

	public long getTotalDeaths() {
		return TotalDeaths;
	}

	public void setTotalDeaths(long totalDeaths) {
		TotalDeaths = totalDeaths;
	}

	public List<CountryDeathsAverage> getCountries() {
		return Countries;
	}

	public void setCountries(List<CountryDeathsAverage> countries) {
		Countries = countries;
	}

	@Override
	public String toString() {
		return "CountryDeathsList [TotalDeaths=" + TotalDeaths + ", Countries=" + Countries + "]";
	}	
}
