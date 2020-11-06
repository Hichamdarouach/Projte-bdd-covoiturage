package Modele;

import BDD.UpdateBDD;
import java.util.ArrayList;

public class Vehicule {

	public String getImmatriculation() {
		return immatriculation;
	}

	public String getMarque() {
		return marque;
	}

	public String getModele() {
		return modele;
	}

	public int getNbPlaces() {
		return nbPlaces;
	}

	public ArrayList<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	private String immatriculation;
	private String marque;
	private String modele;
	private float puisFisc;

	private int nbPlaces;
	private Energie energie;
	private ArrayList<Utilisateur> utilisateurs;

	public Vehicule(String immatriculation, String marque, String modele, float puisFisc, int nbPlaces, Energie energie,
			Utilisateur utilisateur) {
		super();
		this.immatriculation = immatriculation;
		this.marque = marque.trim();
		this.modele = modele.trim();
		this.puisFisc = puisFisc;
		this.nbPlaces = nbPlaces;
		this.energie = energie;
		this.utilisateurs = new ArrayList<Utilisateur>();
		this.utilisateurs.add(utilisateur);
	}

	public void ajouterUtilisateur(Utilisateur user) {
		this.utilisateurs.add(user);
	}

	boolean update() {
		return UpdateBDD.update(this);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Vehicule) {
			Vehicule u = (Vehicule) other;
			return u.immatriculation == this.immatriculation;
		}
		return false;

	}

	public float getPuisFisc() {
		return puisFisc;
	}

	public Energie getEnergie() {
		return energie;
	}

	public String toString(){
		return this.marque + " "  + this.modele;
	}
}
