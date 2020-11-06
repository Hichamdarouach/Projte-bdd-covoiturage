package Modele;

public class Energie {
	
	private TypeEnergie Carburant;

	public Energie(TypeEnergie Carburant){
		this.Carburant = Carburant;
	}
	
	float alpha() {
		if(this.Carburant == TypeEnergie.Essence ) {
			return(1);
		} if(this.Carburant == TypeEnergie.Diesel) {
			return(1.5f);
		} else {
			return(0.5f);
		}
	}

	public TypeEnergie getCarburant() {
		return Carburant;
	}

	@Override
	public String toString(){
		return Carburant.toString();
	}
}
