package cs2pr3.covid.response;

import java.util.Date;
import java.util.List;

public class CovidGlobalResponse {

	private CovidGlobal Global;
	private List<Country> Countries;
	private Date Date;
	
	public CovidGlobalResponse() {
		super();
	}

	public CovidGlobalResponse(CovidGlobal global, List<Country> countries, Date date) {
		super();
		Global = global;
		Countries = countries;
		Date = date;
	}

	public CovidGlobal getGlobal() {
		return Global;
	}
	public void setGlobal(CovidGlobal global) {
		Global = global;
	}
	public List<Country> getCountries() {
		return Countries;
	}
	public void setCountries(List<Country> countries) {
		Countries = countries;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	
	@Override
	public String toString() {
		return "CovidGlobalResponse [Global=" + Global + ", Countries="
				+ Countries + ", Date=" + Date + "]";
	}
}
