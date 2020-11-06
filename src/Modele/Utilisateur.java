package Modele;

import java.util.ArrayList;

import BDD.BDDException;
import BDD.FetchBDD;
import BDD.InsertBDD;
import BDD.UpdateBDD;

public class Utilisateur {

	private String email;
	private String nom;
	private String pnom;
	private String villeRes;
	private String mdp;
	private float solde;
	private ArrayList<Vehicule> vehicules;

	public Utilisateur(String email, String nom, String pnom, String villeRes, String mdp, float solde) {
		this.email = email.trim();
		this.nom = nom.trim();
		this.pnom = pnom.trim();
		this.villeRes = villeRes.trim();
		this.mdp = mdp;
		this.solde = solde;
		this.vehicules = new ArrayList<Vehicule>();
	}

	public boolean crediter(float montant) {
		if(this.solde >= 0){
			float old = solde;
			solde += montant;
			if(update()){
				return true;
			}
			else{
				solde = old;
				return false;
			}
		}
		return false;
	}

	void debiter(float montant) {
		if (this.solde > montant) {
			this.solde -= montant;
			// TODO
			this.update();
		} else {
			// TODO
			// Erreur : solde insuffisant
		}
	}
	public void ajouterVehicule(Vehicule vehicule){
		this.vehicules.add(vehicule);
	}
	public void linkVehicule(Vehicule vehicule) {
		this.vehicules.add(vehicule);
		vehicule.ajouterUtilisateur(this);
	}

	boolean update() {
		return UpdateBDD.update(this);
	}

	public boolean insert() throws BDDException {
		return InsertBDD.insert(this);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Utilisateur) {
			Utilisateur u = (Utilisateur) other;
			return u.email == this.email;
		}
		return false;
	}

	public String getEmail() {
		return email;
	}

	public String getNom() {
		return nom;
	}

	public String getPnom() {
		return pnom;
	}

	public String getVilleRes() {
		return villeRes;
	}

	public String getMdp() {
		return mdp;
	}

	public float getSolde() {
		return solde;
	}

	public ArrayList<Vehicule> getVehicules() {
		return vehicules;
	}

	public boolean fetchVehicules() throws BDDException {
		ArrayList<Vehicule> vehiculesFetched = FetchBDD.fetchVehicules(getEmail());

		if(vehicules != null){
			vehicules = vehiculesFetched;
			return true;
		}
		else{
			return false;
		}
	}

}
