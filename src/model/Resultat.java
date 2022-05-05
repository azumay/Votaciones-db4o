package model;

public class Resultat {

	private Partit partit;
    private Municipi municipi;
    private int vots;
    private double percent;
	
    public Resultat(Partit partit, Municipi municipi, int vots, double percent) {
		super();
		this.partit = partit;
		this.municipi = municipi;
		this.vots = vots;
		this.percent = percent;
	}
    
    
	public Partit getPartit() {
		return partit;
	}
	public void setPartit(Partit partit) {
		this.partit = partit;
	}
	public Municipi getMunicipi() {
		return municipi;
	}
	public void setMunicipi(Municipi municipi) {
		this.municipi = municipi;
	}
	public int getVots() {
		return vots;
	}
	public void setVots(int vots) {
		this.vots = vots;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
    
    
    
}
