package VAO;

public class ParkirnaHisa {

	private String idParkirnaHisa;
	private String naziv;
	private String naslov;
	private int stVsehMest;
	private int stZasedenihMest;
	private double cenaNaUro;
	private double lat;
	private double lng;
	private String lastnik;

	/*public ParkirnaHisa(String naziv, String naslov, int stVsehMest, int stZasedenihMest, double cenaNaUro, double lat, double lng, String lastnik) {
		this.naziv = naziv;
		this.naslov = naslov;
		this.stVsehMest = stVsehMest;
		this.stZasedenihMest = stZasedenihMest;
		this.cenaNaUro = cenaNaUro;
		this.lat=lat;
		this.lng=lng;
		this.lastnik=lastnik;
	}*/

	public ParkirnaHisa() {
	}

    public ParkirnaHisa(String idParkirnaHisa,String naziv, String naslov, int stVsehMest, int stZasedenihMest, double cenaNaUro, double lat, double lng, String lastnik) {
        this.idParkirnaHisa=idParkirnaHisa;
        this.naziv = naziv;
        this.naslov = naslov;
        this.stVsehMest = stVsehMest;
        this.stZasedenihMest = stZasedenihMest;
        this.cenaNaUro = cenaNaUro;
        this.lat=lat;
        this.lng=lng;
        this.lastnik=lastnik;
	}

    public String getIdParkirnaHisa() {
		return idParkirnaHisa;
	}

	public void setIdParkirnaHisa(String idParkirnaHisa) {
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

	public int getStZasedenihMest() {
		return stZasedenihMest;
	}

	public void setStZasedenihMest(int stZasedenihMest) {
		this.stZasedenihMest = stZasedenihMest;
	}

	public double getCenaNaUro() {
		return cenaNaUro;
	}

	public void setCenaNaUro(double cenaNaUro) {
		this.cenaNaUro = cenaNaUro;
	}

	public double getLat() {return lat;}

	public void setLat(double lat) {this.lat = lat;}

	public double getLng() {return lng;}

	public void setLng(double lng) {this.lng = lng;}

	public String getLastnik() {return lastnik;}

	public void setLastnik(String lastnik) {this.lastnik = lastnik;}
}
