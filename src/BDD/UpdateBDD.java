package BDD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import Modele.*;

public class UpdateBDD {
	
	/**
	 * pour une connexion connect donnee, envoie la requete SQL d'insertion query
	 * a la base de donnee connectee
	 * @param query la requete d'insertion dans la base de donnees
	 * @param connect l'objet Connexion
	 * @return un booleen qui indique si l'insertion s'est bien effectuee
	 */
	public static boolean updateQuery(String query) throws BDDException {
    	
    	try {
    		
    		Connection conn = Connexion.getInstance();
    		
    		// Creation de la requete
    		Statement stmt = conn.createStatement();
    		
    		// Execution de la requete
    		stmt.executeUpdate(query);
    		
    	} catch (SQLException e) {
    		e.printStackTrace(System.err);
    		return false;
    	}
    	
    	return true;
    }
    
	
    public static boolean update(Utilisateur utilisateur) {
        
    	String query = "UPDATE Utilisateur SET ";
    	
    	query += "nom='" + utilisateur.getNom() + "', ";
    	
    	query += "pnom='" + utilisateur.getPnom() + "', ";
    	
    	query += "villeRes='" + utilisateur.getVilleRes() + "', ";
    	
    	query += "mdp='" + utilisateur.getMdp() + "', ";
    	
    	query += "solde=" + utilisateur.getSolde();
    	
    	query += "WHERE Utilisateur.email='" + utilisateur.getEmail() + "'";
    	
    	try {
    		    		
    		updateQuery(query);
    		
    		return true;
    		
    	} catch(Exception e) {
    		
    		return false;
    	}
    }

    public static boolean update(Vehicule vehicule) {
    	
    	String query = "UPDATE Vehicule SET ";
    	
    	query += "marque='" + vehicule.getMarque() + "', ";
    	
    	query += "modele='" + vehicule.getModele() + "', ";
    	
    	query += "puisFisc=" + vehicule.getPuisFisc() + ", ";
    	
    	query += "nbPlaces=" + vehicule.getNbPlaces() + ", ";
    	
    	query += "energieUtilisee='" + vehicule.getEnergie().getCarburant() + "' ";
    	
    	query += "WHERE immatriculation='" + vehicule.getImmatriculation() + "'";
    	
    	try {
    		
    		updateQuery(query);
    		
    		return true;
    		
    	} catch(Exception e) {
    		
    		return false;
    	}
    }

    public static boolean update(Parcours parcours) {
        
    	String query = "UPDATE Parcours SET ";
    	
    	query += "emailUtilisateur='" + parcours.getUtilisateur().getEmail() + "' ";
    	
    	query += "WHERE Parcours.idParcours=" + parcours.getIdParcours();
    	
    	try {
    		
    		updateQuery(query);
    		
    		return true;
    		
    	} catch(Exception e) {
    		
    		return false;
    	}
    }

    public static boolean update(Trajet trajet) {
        
    	String query = "UPDATE Trajet SET ";
    	
    	query += "dateDep= DATE '" + trajet.getDateDep() + "', ";
    	
    	query += "heureDep= TIMESTAMP '" + trajet.getHeureDep() + "', ";
    	
    	query += "nbPlaces=" + trajet.getNbPlaces() + ", ";
    	
    	query += "immaVehicule='" + trajet.getVehicule().getImmatriculation() + "', ";
    	
    	query += "emailUtilisateur='" + trajet.getUtilisateur().getEmail() + "' ";
    	
    	query += "WHERE Trajet.idTrajet=" + trajet.getIdTrajet();
    	
    	try {
    		
    		updateQuery(query);
    		
    		for (Troncon troncon : trajet.getTroncons()) {
    			
    			update(troncon);
    			
    		}
    		
    		return true;
    		
    	} catch(Exception e) {
    		
    		return false;
    	}
    }

    public static boolean update(Troncon troncon) {
        
    	String query = "UPDATE Troncon SET ";
    	
    	query += "villeDep='" + troncon.getVilleDep() + "', ";
    	
    	query += "longDep=" + troncon.getLongDep() + ", ";
    	
    	query += "latDep=" + troncon.getLatDep() + ", ";
    	
    	query += "villeArr='" + troncon.getVilleArr() + "', ";
    	
    	query += "longArr=" + troncon.getLongArr() + ", ";
    	
    	query += "latArr=" + troncon.getLatArr() + ", ";
    	
    	query += "dureeEst= TIMESTAMP '" + troncon.getDureeEst() + "', ";
    	    	
    	query += "etatCourant='" + troncon.getEtat() + "' ";
    	
		query += "WHERE Troncon.idTroncon=" + troncon.getIdTroncon();
		
		query += "AND Troncon.idTrajet=" + troncon.getTrajet().getIdTrajet();
		
    	try {
    		    		
			updateQuery(query);
    		
    		return true;
    		
    	} catch(Exception e) {
    		
    		return false;
    	}
	}
	
	public static boolean updateEtatSuivi(Troncon t, int idParcours, String email){
		String query = "UPDATE ParContientTr SET ";
    	
		query += "suiviVoyageur='" + t.getSuivi(email) + "' ";
		query += "WHERE idTroncon=" + t.getIdTroncon();
		query += " AND idTrajet=" + t.getTrajet().getIdTrajet();
		query += " AND idParcours=" + idParcours;
				
    	try {
    		    		
    		updateQuery(query);
    		
    		return true;
    		
    	} catch(Exception e) {
    		
    		return false;
    	}
	}

}