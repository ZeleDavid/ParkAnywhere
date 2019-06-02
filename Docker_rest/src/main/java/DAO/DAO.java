package DAO;

import VAO.ParkirnaHisa;
import VAO.Rezervacija;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class DAO {
	private static List<ParkirnaHisa> seznamParkirnihHis = new ArrayList<ParkirnaHisa>(Arrays.asList(
            new ParkirnaHisa("1","Na Koroški cesti", "Koroška cesta 11", 100, 63, 1.4, 46.560092, 15.631116, "Luka")
            , new ParkirnaHisa("2","Na Gosposvetski cesti", "Gosposvetska cesta 12", 150, 100, 1.8, 46.560530, 15.641231, "David")
            , new ParkirnaHisa("3","Na Smetanovi", "Smetanova ulica 24", 30, 25, 1.1, 46.560403, 15.635597, "Luka")
			, new ParkirnaHisa("4","Pri kliničnem", "Klikicna ulica 17", 117, 20, 1.2, 46.551372, 15.648532, "Marjan")
			, new ParkirnaHisa("5","Pod gradom", "Grajska ulica 1", 43, 2, 1.9, 46.560432, 15.648875, "Anja")
			, new ParkirnaHisa("6","Pri trgovini", "Trgovska ulica 12", 12, 1, 2.0, 46.564577, 15.642266, "Olga")
			, new ParkirnaHisa("7","Pod Europarkom", "Obdravska cesta 7", 220, 200, 0.1, 46.554353, 15.652094, "Bard")
			, new ParkirnaHisa("8","Pri Štuku", "Gosposvetska cesta 100", 77, 77, 1.5, 46.561863, 15.626516, "Gašper")
			, new ParkirnaHisa("9","Pod Kalvarijo", "Hribovska ulica 2", 90, 30, 4.3, 46.567469, 15.632417, "Timi")
			, new ParkirnaHisa("10","V centru", "Centreran trg 5", 80, 40, 12.1, 46.557791, 15.645592, "Žan")
			, new ParkirnaHisa("11","Pri bloku", "Mestna ulica 33", 44, 43, 1.0, 46.556345, 15.634606, "Ewa")


    ));
	private static List<Rezervacija> seznamRezervacij = new ArrayList<>();


	public static void addRezervacija(Rezervacija rezervacija){
		//dodaj rezervacijo v ark
		seznamRezervacij.add(rezervacija);
	}
	public static void addParkirnaHisa(ParkirnaHisa parkirnaHisa){
		//dodaj parkirno hio v ark
		seznamParkirnihHis.add(parkirnaHisa);
	}
	public static ParkirnaHisa getParkirnaHisa(String id){
		//pridobi parkirno hiso iz arka
		for(ParkirnaHisa p:seznamParkirnihHis){
			if(p.getIdParkirnaHisa()==id){
				return p;
			}
		}
		return null;
	}
	public static Rezervacija getRezervacija(long id){
		//pridobi rezervacijo iz arka
		for(Rezervacija r:seznamRezervacij){
			if(r.getIdRezervacija()==id){
				return r;
			}
		}
		return null;
	}
	public static List<ParkirnaHisa> getSeznamParkirnihHis() {
		//pridobi vse iz arka
		return seznamParkirnihHis;
	}

	public static List<Rezervacija> getSeznamRezervacij() {
		//pridobi vse iz arka
		return seznamRezervacij;
	}

	public static void deleteParkirnaHisa(ParkirnaHisa p) {
		//delete
	}

	public static void deleteRezervacija(Rezervacija r) {
		//delete
	}
}
