package cs2pr3.covid.odt;

public class ConfirmedAverage {
	
	private int NombrePays;
	private float ConfirmedAverage;
	
	public ConfirmedAverage() {
		super();
	}

	public ConfirmedAverage(int nombrePays, float confirmedAverage) {
		super();
		NombrePays = nombrePays;
		ConfirmedAverage = confirmedAverage;
	}

	public int getNombrePays() {
		return NombrePays;
	}

	public void setNombrePays(int nombrePays) {
		NombrePays = nombrePays;
	}

	public float getConfirmedAverage() {
		return ConfirmedAverage;
	}

	public void setConfirmedAverage(float confirmedAverage) {
		ConfirmedAverage = confirmedAverage;
	}

	@Override
	public String toString() {
		return "ConfirmedAverage [NombrePays=" + NombrePays + ", ConfirmedAverage=" + ConfirmedAverage + "]";
	}
}
