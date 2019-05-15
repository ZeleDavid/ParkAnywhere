package DAO;

import VAO.ParkirnaHisa;
import VAO.Rezervacija;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DAO {
	private static List<ParkirnaHisa> seznamParkirnihHis = new ArrayList<>();
	private static List<Rezervacija> seznamRezervacij = new ArrayList<>();

	public static void addRezervacija(Rezervacija rezervacija){
		seznamRezervacij.add(rezervacija);
	}
	public static void addParkirnaHisa(ParkirnaHisa parkirnaHisa){
		seznamParkirnihHis.add(parkirnaHisa);
	}
	public static ParkirnaHisa getParkirnaHisa(long id){
		for(ParkirnaHisa p:seznamParkirnihHis){
			if(p.getIdParkirnaHisa()==id){
				return p;
			}
		}
		return null;
	}
	public static Rezervacija getRezervacija(long id){
		for(Rezervacija r:seznamRezervacij){
			if(r.getIdRezervacija()==id){
				return r;
			}
		}
		return null;
	}
	public static List<ParkirnaHisa> getSeznamParkirnihHis() {
		return seznamParkirnihHis;
	}

	public static List<Rezervacija> getSeznamRezervacij() {
		return seznamRezervacij;
	}
}
