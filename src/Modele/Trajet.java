package Modele;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import BDD.BDDException;
import BDD.InsertBDD;
import BDD.UpdateBDD;

public class Trajet {

	private int idTrajet;
	private Date dateDep;
	private Timestamp heureDep;
	private int nbPlaces;
	private Vehicule vehicule;
	private ArrayList<Troncon> troncons;
	private Utilisateur utilisateur;

	public int getIdTrajet() {
		return idTrajet;
	}

	public Date getDateDep() {
		return dateDep;
	}

	public Timestamp getHeureDep(){
		return heureDep;
	}

	public int getNbPlaces() {
		return nbPlaces;
	}

	public ArrayList<Troncon> getTroncons() {
		return troncons;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur util){
		utilisateur = util;
	}

	public boolean addTronconAndUpdate(Troncon troncon) {
		// Ajout à la fin
		troncons.add(troncon);
		return this.update();
	}

	public void addTroncon(Troncon troncon){
		troncons.add(troncon);
	}




	public Trajet(int idTrajet, Date dateDep, Timestamp heureDep, int nbPlaces,  Vehicule vehicule, Utilisateur utilisateur, ArrayList<Troncon> troncons) {
		this.idTrajet = idTrajet;
		this.dateDep = dateDep;
		this.heureDep = heureDep;
		this.nbPlaces = nbPlaces;
		this.vehicule = vehicule;
		this.utilisateur = utilisateur;
		this.troncons = troncons;
		int i = 0;
        for (Troncon troncon : this.troncons) {
            troncon.setTrajet(this);
            troncon.setIdTroncon(i);
            i++;
        }
	}
	
	
	public Trajet(int idTrajet, Date dateDep, Timestamp heureDep, int nbPlaces,  Vehicule vehicule, Utilisateur utilisateur) {
		this.idTrajet = idTrajet;
		this.dateDep = dateDep;
		this.heureDep = heureDep;
		this.nbPlaces = nbPlaces;
		this.vehicule = vehicule;
		this.utilisateur = utilisateur;
		this.troncons = null;
	}


	public void setTroncons(ArrayList<Troncon> troncons){
		this.troncons=troncons;
	}

	boolean update() {
		return UpdateBDD.update(this);
	}

	public boolean insert() throws BDDException {
		return InsertBDD.insert(this);
	}

	public void setTrajToTronçons(){
		for(Troncon t : troncons){
			t.setTrajet(this);
		}
	}

	public String getVilleDep(){
		if(troncons.size() > 0){
			return troncons.get(0).getVilleDep();
		}
		else{
			return "Undefined";
		}
	}

	public String getVilleArr(){
		if(troncons.size() > 0){
			return troncons.get(troncons.size() - 1).getVilleArr();
		}
		else{
			return "Undefined";
		}
	}

	public Vehicule getVehicule() {
		return vehicule;
	}
}
