package model;

public class Partit {

	 private String sigles; 
     private String nom;
     
     public Partit() {
 	
 	}
     
	public Partit(String sigles, String nom) {
		this.sigles = sigles;
		this.nom = nom;
	}
	public String getSigles() {
		return sigles;
	}
	public void setSigles(String sigles) {
		this.sigles = sigles;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	} 
     
     
}
