package model;

import java.util.ArrayList;
import java.util.List;

public class Municipi {

	private String nom;
	private String provincia;
	private ArrayList<Resultat> resultats;

	public Municipi() {

	}

	public Municipi(String nom, String provincia) {

		this.nom = nom;
		this.provincia = provincia;
		resultats = new ArrayList<Resultat>();

	}

	public ArrayList<Resultat> getResultats() {
		return resultats;
	}

	public void setResultats(ArrayList<Resultat> resultats) {
		this.resultats = resultats;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}
