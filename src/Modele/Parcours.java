package Modele;

import java.sql.Timestamp;
import java.util.ArrayList;

import BDD.BDDException;
import BDD.FetchBDD;
import BDD.InsertBDD;
import BDD.UpdateBDD;

public class Parcours {

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	private int idParcours;
	
	public void setIdParcours(int idParcours) {
		this.idParcours = idParcours;
	}

	private Utilisateur utilisateur;
	private ArrayList<Troncon> troncons;

	public Parcours(int idParcours, Utilisateur utilisateur) {
		this.idParcours = idParcours;
		this.utilisateur = utilisateur;
		this.troncons = new ArrayList<Troncon>();
	}

	public Parcours(ArrayList<Troncon> troncons){
		this.troncons = troncons;
	}
	
	public void autoSetId() throws BDDException{
		this.idParcours = FetchBDD.fetchMaxIdParcours()+1;
		
	}

	public void setUtilisateur(Utilisateur utilisateur){
		this.utilisateur = utilisateur;
	}

	public void setTroncons(ArrayList<Troncon> troncons){
		this.troncons = troncons;
	}

	public ArrayList<Troncon> getTroncons(){
		return troncons;
	}

	public int getSize(){
		return troncons != null ? troncons.size() : 0;
	}

	public Troncon getTroncon(int i){
		return (i >= 0 && i < getSize() ? troncons.get(i) : null);
	}

	public String getVilleDep(){
		if(troncons != null){
			if(troncons.size() > 0){
				return troncons.get(0).getVilleDep();
			}
		}
		return "Unavailable";
	}

	public String getVilleArr(){
		if(troncons != null){
			if(troncons.size() > 0){
				return troncons.get(troncons.size() - 1).getVilleArr();
			}
		}
		return "Unavailable";
	}

	boolean update() {
		return UpdateBDD.update(this);
	}

	public boolean insert() throws BDDException {
		return InsertBDD.insert(this);
	}

	public int getIdParcours() {
		return idParcours;
	}

	public boolean aChangement(){
		if(troncons != null){
			if(troncons.size() > 0){
				return troncons.get(0).getTrajet().getIdTrajet() != troncons.get(getSize() - 1).getTrajet().getIdTrajet();
			}
		}
		return false;
	}

	public Changement getChangement(){
		Changement res = null;
		if(aChangement()){
			res = new Changement();

			for(int i = 0; i < getSize() - 2; i++){
				if(troncons.get(i).getTrajet().getIdTrajet() != troncons.get(i+1).getTrajet().getIdTrajet()){
					res.first = troncons.get(i);
					res.second = troncons.get(i+1);
				}
			}
		}
		return res;
	}

	public float getLongArr(){
		float res = 0;
		if(getSize() > 0){
			res = troncons.get(getSize() - 1).getLongArr();
		}

		return res;
	}

	public float getLongDep(){
		float res = 0;
		if(getSize() > 0){
			res = troncons.get(0).getLongDep();
		}
		return res;
	}

	public float getLatArr(){
		float res = 0;
		if(getSize() > 0){
			res = troncons.get(getSize() - 1).getLatArr();
		}
		return res;
	}

	public float getLatDep(){
		float res = 0;
		if(getSize() > 0){
			res = troncons.get(0).getLatDep();
		}
		return res;
	}

	public Timestamp getDateDep(){
		if(getSize() < 0){
			return null;
		}
		Timestamp res = getTroncon(0).getDateDep();
		return res;
	}

	public float getPrix(){
		float res = 0;
		for(Troncon t : troncons){
			res += t.PrixTroncon();
		}
		return res;
	}

	public class Changement{
		public Troncon first;
		public Troncon second;
	}
}
