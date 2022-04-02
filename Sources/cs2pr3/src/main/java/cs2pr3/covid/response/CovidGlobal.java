package cs2pr3.covid.response;

import java.util.Date;

public class CovidGlobal {
	
	private Long NewConfirmed;
	private Long TotalConfirmed;
	private Long NewDeaths;
	private Long TotalDeaths;
	private int NewRecovered;
	private int TotalRecovered;
	private Date Date;
	
	public CovidGlobal() {
		super();
	}

	public CovidGlobal(Long newConfirmed, Long totalConfirmed, Long newDeaths, Long totalDeaths, int newRecovered,
			int totalRecovered, java.util.Date date) {
		super();
		NewConfirmed = newConfirmed;
		TotalConfirmed = totalConfirmed;
		NewDeaths = newDeaths;
		TotalDeaths = totalDeaths;
		NewRecovered = newRecovered;
		TotalRecovered = totalRecovered;
		Date = date;
	}
	
	public Long getNewConfirmed() {
		return NewConfirmed;
	}
	public void setNewConfirmed(Long newConfirmed) {
		NewConfirmed = newConfirmed;
	}
	public Long getTotalConfirmed() {
		return TotalConfirmed;
	}
	public void setTotalConfirmed(Long totalConfirmed) {
		TotalConfirmed = totalConfirmed;
	}
	public Long getNewDeaths() {
		return NewDeaths;
	}
	public void setNewDeaths(Long newDeaths) {
		NewDeaths = newDeaths;
	}
	public Long getTotalDeaths() {
		return TotalDeaths;
	}
	public void setTotalDeaths(Long totalDeaths) {
		TotalDeaths = totalDeaths;
	}
	public int getNewRecovered() {
		return NewRecovered;
	}
	public void setNewRecovered(int newRecovered) {
		NewRecovered = newRecovered;
	}
	public int getTotalRecovered() {
		return TotalRecovered;
	}
	public void setTotalRecovered(int totalRecovered) {
		TotalRecovered = totalRecovered;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	
	@Override
	public String toString() {
		return "CovidGlobal [NewConfirmed=" + NewConfirmed + ", TotalConfirmed=" + TotalConfirmed + ", NewDeaths="
				+ NewDeaths + ", TotalDeaths=" + TotalDeaths + ", NewRecovered=" + NewRecovered + ", TotalRecovered="
				+ TotalRecovered + ", Date=" + Date + "]";
	}
}
