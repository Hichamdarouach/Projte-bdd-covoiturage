package BDD;

public class IntersectionParcours {

	public int getIdTrajet_1() {
		return idTrajet_1;
	}

	public int getIdTrajet_2() {
		return idTrajet_2;
	}

	public String getVilleIntersection() {
		return villeIntersection;
	}

	private int idTrajet_1;
	private int idTrajet_2;
	private String villeIntersection;
	
	public IntersectionParcours(int idTrajet_1, int idTrajet_2, String villeIntersection) {
		this.idTrajet_1 = idTrajet_1;
		this.idTrajet_2 = idTrajet_2;
		this.villeIntersection = villeIntersection;
	}
	
}
