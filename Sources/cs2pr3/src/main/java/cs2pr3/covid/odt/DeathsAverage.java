package cs2pr3.covid.odt;

public class DeathsAverage {
	
	private int NombrePays;
	private float DeathsAverage;
	
	public DeathsAverage() {
		super();
	}

	public DeathsAverage(int nombrePays, float confirmedAverage) {
		super();
		NombrePays = nombrePays;
		DeathsAverage = confirmedAverage;
	}

	public int getNombrePays() {
		return NombrePays;
	}

	public void setNombrePays(int nombrePays) {
		NombrePays = nombrePays;
	}

	public float getDeathsAverage() {
		return DeathsAverage;
	}

	public void setDeathsAverage(float confirmedAverage) {
		DeathsAverage = confirmedAverage;
	}

	@Override
	public String toString() {
		return "DeathsAverage [NombrePays=" + NombrePays + ", DeathsAverage=" + DeathsAverage + "]";
	}
}
