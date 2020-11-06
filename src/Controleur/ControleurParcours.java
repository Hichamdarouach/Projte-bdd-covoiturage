package Controleur;

import java.util.ArrayList;

import BDD.BDDException;
import BDD.FetchBDD;
import BDD.IntersectionParcours;
import Modele.*;

public class ControleurParcours {

    private Parcours parcoursCourant;
	private ArrayList<Parcours> parcours;
	private ArrayList<Parcours> searchResult;

    public ControleurParcours(){
		parcours = new ArrayList<Parcours>();
		searchResult = new ArrayList<Parcours>();
    }
	
    public void RechercherParcours(String villeDep, String villeArr, String emailUser) throws BDDException {
    	
    	ArrayList<Parcours> parcours_possibles = new ArrayList<Parcours>();
    	
    	//cas avec un trajet qui permet de faire le chemin
    	ArrayList<Integer> idTrajets = FetchBDD.fetch_parcours_oneTrajet(villeDep, villeArr, emailUser);
    	    	
    	for(Integer idTrajet : idTrajets) {
    	
    		ArrayList<Troncon> troncons_utiles = FetchBDD.fetch_troncons_from_oneTrajet(villeDep, villeArr, idTrajet);
    		
    		Parcours parcours_a_ajouter = new Parcours(troncons_utiles);
    		
    		parcours_a_ajouter.setIdParcours(-1);
    		
    		parcours_possibles.add(parcours_a_ajouter);
    		
    	}
    	
    	//cas avec deux trajets qui permettent de faire le chemin
    	
    	ArrayList<IntersectionParcours> intersectionParcour_s = FetchBDD.fetch_parcours_twoTrajet(villeDep, villeArr, emailUser);
    	
    	
    	for(IntersectionParcours intersectionParcour : intersectionParcour_s) {
    	
    		ArrayList<Troncon> troncons_utiles = FetchBDD.fetch_troncons_from_twoTrajet(villeDep, villeArr, intersectionParcour);
    		
    		Parcours parcours_a_ajouter = new Parcours(troncons_utiles);
    		
    		parcours_a_ajouter.setIdParcours(-1);
    		
    		parcours_possibles.add(parcours_a_ajouter);
    		
    	}
		    	
		searchResult = parcours_possibles;
		
	}
    
    public void RechercherParcours(String villeDep, String emailUser) throws BDDException {
    	
    	ArrayList<Parcours> parcours_possibles = new ArrayList<Parcours>();
    	
    	ArrayList<Integer> idTrajets = FetchBDD.fetch_parcours_villeDep(villeDep, emailUser);
    	
    	for(Integer idTrajet : idTrajets) {
        	
    		ArrayList<Troncon> troncons_utiles = FetchBDD.fetch_troncons_from_oneTrajet(villeDep, idTrajet);
    		
    		Parcours parcours_a_ajouter = new Parcours(troncons_utiles);
    		
    		parcours_a_ajouter.setIdParcours(-1);
    		
    		parcours_possibles.add(parcours_a_ajouter);
    		
    	}
    	
		searchResult = parcours_possibles;
		
	}
	
	public ArrayList<Parcours> getSearchRes(){
		return searchResult;
	}

	public void setParcoursCourant(Parcours par){
		parcoursCourant = par;
	}

	public Parcours getParcoursCourant(){
		return parcoursCourant;
	}
	
	public boolean inscriptionParcours(Parcours parcours, Utilisateur utilisateur) throws BDDException{
		boolean succesInsertParcours=false;
		if (utilisateur.getSolde()<parcours.getPrix()){
			return false;
		}
		else{
			for (Troncon troncon:parcours.getTroncons()){
				if(!troncon.addPassager(utilisateur)){
					throw new BDDException("Un des trajets n'est pas disponible");
				}
				else{
					troncon.addSuivi(utilisateur, EtatSuivi.pasMonte);
				}
			}
			parcours.setUtilisateur(utilisateur);
			parcours.autoSetId();
			succesInsertParcours = parcours.insert();
			return succesInsertParcours;
			
		}
	}

	public void setParcoursUser(Utilisateur user) throws BDDException{
		//this.parcours=fetchBDD.fetchUserParcours(user);
		this.parcours=FetchBDD.fetchParcours(user);
	}

	public ArrayList<Parcours> getParcoursUser(){
		return parcours;
	}
}
