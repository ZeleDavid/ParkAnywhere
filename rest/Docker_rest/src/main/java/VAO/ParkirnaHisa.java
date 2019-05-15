package VAO;

public class ParkirnaHisa {

	private long idParkirnaHisa;
	private String naziv;
	private String naslov;
	private int stVsehMest;
	private int steZasedenihMest;
	private double cenaNaUro;

	public ParkirnaHisa(String naziv, String naslov, int stVsehMest, int steZasedenihMest, double cenaNaUro) {
		this.naziv = naziv;
		this.naslov = naslov;
		this.stVsehMest = stVsehMest;
		this.steZasedenihMest = steZasedenihMest;
		this.cenaNaUro = cenaNaUro;
	}

	public ParkirnaHisa() {
	}

	public long getIdParkirnaHisa() {
		return idParkirnaHisa;
	}

	public void setIdParkirnaHisa(long idParkirnaHisa) {
		this.idParkirnaHisa = idParkirnaHisa;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public int getStVsehMest() {
		return stVsehMest;
	}

	public void setStVsehMest(int stVsehMest) {
		this.stVsehMest = stVsehMest;
	}

	public int getSteZasedenihMest() {
		return steZasedenihMest;
	}

	public void setSteZasedenihMest(int steZasedenihMest) {
		this.steZasedenihMest = steZasedenihMest;
	}

	public double getCenaNaUro() {
		return cenaNaUro;
	}

	public void setCenaNaUro(double cenaNaUro) {
		this.cenaNaUro = cenaNaUro;
	}
}
