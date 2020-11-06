package BDD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import Modele.Parcours;
import Modele.Trajet;
import Modele.Troncon;
import Modele.Utilisateur;
import Modele.Vehicule;

public class InsertBDD {

	/**
	 * pour une connexion connect donnee, envoie la requete SQL d'insertion query
	 * a la base de donnee connectee
	 * @param query la requete d'insertion dans la base de donnees
	 * @param connect l'objet Connexion
	 * @return un booleen qui indique si l'insertion s'est bien effectuee
	 */
	public static boolean sendInsertQuery(String query) throws BDDException {
    	
    	try {
    		
    		Connection conn = Connexion.getInstance();
    		
    		// Creation de la requete
    		Statement stmt = conn.createStatement();
    		    		
    		// Execution de la requete
    		stmt.executeUpdate(query);
    		
    		return true;
    		
    	} catch (SQLException e) {
    		System.err.println("failed");
    		e.printStackTrace(System.err);
    		return false;
    	}
    }
    

    /**
     * Insere l'utilisateur utilisateur dans la base de donnees
     * @param utilisateur
     * @return un booleen qui indique si l'insertion s'est bien effectuee
     */
    public static boolean insert(Utilisateur utilisateur) throws BDDException {
    	
    	String query = "INSERT INTO Utilisateur (email,nom,pnom,villeRes,mdp,solde) VALUES (";
    	
    	query += "'" + utilisateur.getEmail() + "',";
    	
    	query += "'" + utilisateur.getNom() + "',";
    			 
    	query += "'" + utilisateur.getPnom() + "',";

    	query += "'" + utilisateur.getVilleRes() + "',";

    	query += "'" + utilisateur.getMdp() + "',";

    	query += "'0'";
    	
    	query += ")";
    	
    	return sendInsertQuery(query);
    }

    
    /**
     * Insert le vehicule vehicule dans la base de donnee
     * @param vehicule
     * @return un booleen qui indique si l'insertion s'est bien effectuee
     */
    public static boolean insert(Vehicule vehicule)  throws BDDException {
    	
    	String query1 = "INSERT INTO Vehicule VALUES(";
    			
    	query1 += "'" + vehicule.getImmatriculation() + "',";
    	
    	query1 += "'" + vehicule.getMarque() + "',";

    	query1 += "'" + vehicule.getModele() + "',";
    	
    	float puisFisc = vehicule.getPuisFisc();
    	
    	query1 += puisFisc + ",";
    	
    	query1 += vehicule.getNbPlaces() + ",";
    	
    	query1 += "'" + vehicule.getEnergie().getCarburant() + "')";
    	
    	return(sendInsertQuery(query1));
    }
    
    
    /**
     * Insere le parcours parcours dans la base de donnees
     * @param parcours
     * @return un booleen qui indique si l'insertion s'est bien effectuee
     */
    public static boolean insert(Parcours parcours) throws BDDException {
    	
    	String queryParcours = "INSERT INTO Parcours VALUES (";
    	
    	queryParcours += parcours.getIdParcours() + ", ";
    	
    	queryParcours += "'" + parcours.getUtilisateur().getEmail() +"')";
    	
		boolean insertParcours = sendInsertQuery(queryParcours);

		boolean insertTroncons = false;

		int i = 0;
		
		for(Troncon troncon : parcours.getTroncons()){
			String queryTroncon = "INSERT INTO parcontienttr VALUES (";
			queryTroncon += troncon.getIdTroncon() + ", ";
			queryTroncon += troncon.getTrajet().getIdTrajet() + ", ";
			queryTroncon += parcours.getIdParcours() + ", ";
			queryTroncon += i + ", ";
			i++;
			queryTroncon += "'"+troncon.getSuivi(parcours.getUtilisateur().getEmail()) + "')";
			if(!insertTroncons){
				insertTroncons |= sendInsertQuery(queryTroncon);
			}
			else{
				insertTroncons &= sendInsertQuery(queryTroncon);
			}
		}
		return insertParcours&&insertTroncons;
		
    }

    
    /**
     * Insere le trajet 'trajet' dans la base de donnees 
     * @param trajet
     * @return un booleen qui indique si l'insertion s'est bien effectuee
     */
    public static boolean insert(Trajet trajet) throws BDDException {
    	
    	String query = "INSERT INTO Trajet VALUES (";
    	
    	query +=  trajet.getIdTrajet() + ", ";
    	
		query += "TIMESTAMP '" + trajet.getHeureDep().toString() + "', ";
		
		query += trajet.getNbPlaces() + ", ";
		
		query += "'" + trajet.getVehicule().getImmatriculation() + "', ";
		
		query += "'" + trajet.getUtilisateur().getEmail() + "',";

		query += "DATE '" +trajet.getDateDep().toString() + "') ";
		
		if(sendInsertQuery(query)) {
		
			for( Troncon troncon : trajet.getTroncons()) {
				
				insert(troncon, trajet.getIdTrajet());
				
				if(troncon.getDureeMaxAtt() > 0){
					String query2 = "INSERT INTO Attente VALUES (";
	    		
					query2 +=  troncon.getDureeMaxAtt() + ", ";
					
					query2 +=  troncon.getIdTroncon() + ", ";	
					
					query2 += trajet.getIdTrajet() + ")";
					
					if(!sendInsertQuery(query2)) {
						return false;
					}
				}
			}
			
			return true;
		
		} else {
			return false;
		}
		
    }
    
    /**
     * Insere le troncon 'troncon' dans la base de donnees
     * @param troncon
     * @return un booleen qui indique si l'insertion s'est bien effectuee
     */
    public static boolean insert(Troncon troncon, int idTrajet) throws BDDException {
    	    	
    	String query = "INSERT INTO Troncon VALUES (";
    	
    	query +=  troncon.getIdTroncon() + ", ";
    	
    	query += idTrajet + ", ";
    	
    	query += "'"+troncon.getVilleDep()+"',";
    	
    	query +=  troncon.getLongDep()+ ", ";
    	
    	query += troncon.getLatDep() + ", ";
    	
    	query += "'" + troncon.getVilleArr() + "', ";
    	
    	query += troncon.getLongArr()+ ", " ;
    	
    	query += troncon.getLatArr() + ", ";
    	
    	query += "TIMESTAMP '" + troncon.getDureeEst() + "', ";
    	
    	query += "'" + troncon.getEtat() + "')";
    	
    	return(sendInsertQuery(query));
    	
    }
	
    
    public static boolean insert(Vehicule vehicule, Utilisateur user) throws BDDException {
    	
    	String query = "INSERT INTO Conduit VALUES (";
    	
    	query += "'" + user.getEmail() + "', ";
    	
    	query += "'" + vehicule.getImmatriculation() + "')";
    	
    	return(sendInsertQuery(query));
    	
    }
}
