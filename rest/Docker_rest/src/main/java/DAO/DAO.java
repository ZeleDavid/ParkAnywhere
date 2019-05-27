package DAO;

import VAO.ParkirnaHisa;
import VAO.Rezervacija;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class DAO {
	private static List<ParkirnaHisa> seznamParkirnihHis = new ArrayList<ParkirnaHisa>(Arrays.asList(
            new ParkirnaHisa("Na Koroški cesti", "Koroška cesta", 100, 63, 1.4, 46.560092, 15.631116, "Luka")
            , new ParkirnaHisa("Na Gosposvetski cesti", "Gosposvetska cesta", 150, 100, 1.8, 46.560530, 15.641231, "David")
            , new ParkirnaHisa("Na Smetanovi", "Smetanova ulica", 30, 25, 1.1, 46.560403, 15.635597, "Luka")

    ));
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
