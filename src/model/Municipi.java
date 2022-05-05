package model;

import java.util.List;

public class Municipi {
	
	private String nom;
    private String provincia;
    private Resultat resultats;
    
    
	public Municipi(String nom, String provincia) {
		this.nom = nom;
		this.provincia = provincia;
		
	}
	public Municipi(Resultat resultats) {
		this.resultats = resultats;
		
	}
	public Municipi() {
		
		
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
	public Resultat getResultats() {
		return resultats;
	}
	public void setResultats(Resultat resultats) {
		this.resultats = resultats;
	}
	

}
