package VAO;

import java.util.Calendar;

public class Rezervacija {
	private long idRezervacija;
	private long idUporabnik;
	private Calendar zacetek;
	private Calendar konec;
	private ParkirnaHisa parkirnaHisa;
	private double cena;

	public Rezervacija(long idUporabnik, Calendar zacetek, Calendar konec, ParkirnaHisa parkirnaHisa, double cena) {
		this.idUporabnik = idUporabnik;
		this.zacetek = zacetek;
		this.konec = konec;
		this.parkirnaHisa = parkirnaHisa;
		this.cena = cena;
	}

	public Rezervacija() {
	}

	public long getIdRezervacija() {
		return idRezervacija;
	}

	public void setIdRezervacija(long idRezervacija) {
		this.idRezervacija = idRezervacija;
	}

	public long getIdUporabnik() {
		return idUporabnik;
	}

	public void setIdUporabnik(long idUporabnik) {
		this.idUporabnik = idUporabnik;
	}

	public Calendar getZacetek() {
		return zacetek;
	}

	public void setZacetek(Calendar zacetek) {
		this.zacetek = zacetek;
	}

	public Calendar getKonec() {
		return konec;
	}

	public void setKonec(Calendar konec) {
		this.konec = konec;
	}

	public ParkirnaHisa getParkirnaHisa() {
		return parkirnaHisa;
	}

	public void setParkirnaHisa(ParkirnaHisa parkirnaHisa) {
		this.parkirnaHisa = parkirnaHisa;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
}
