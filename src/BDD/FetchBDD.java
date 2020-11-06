package BDD;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import Modele.*;

public class FetchBDD {
	
	public static ResultSet fetchQuery(String query) throws BDDException {
		
		Connection conn = Connexion.getInstance();

		try {
			
			Statement stmt = conn.createStatement();
			
			System.out.println("query = " + query);
			
			ResultSet rs = stmt.executeQuery(query);
			
			return(rs);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new BDDException("SQL exception in fetchQuery");
		}

	}

	public static Utilisateur fetchUtilisateur(String email) throws BDDException {
		
		//email, nom, pnom, villeRes, mdp, solde
		
		String query = "SELECT email, nom, pnom, villeRes, mdp, solde FROM Utilisateur WHERE email = '" + email + "'";
		
		try {
			
			ResultSet rs = fetchQuery(query);	
			
			while(rs.next()) {
			
				String nom = rs.getString("nom");
				String pnom = rs.getString("pnom");
				String villeRes = rs.getString("villeRes");				
				String mdp = rs.getString("mdp");
				float solde = rs.getInt("solde");
				rs.close();
			return new Utilisateur(email, nom, pnom, villeRes, mdp, solde);
			
			}
			
			
			return null;
		} catch (Exception e){
			throw new BDDException("Utilisateur fetch failed");
		}
	}
	
	public static ArrayList<Trajet> fetchTrajets(String email) throws BDDException {
				
		String query = "SELECT idTrajet, dateDep, heureDep, nbPlaces, immaVehicule FROM Trajet WHERE Trajet.emailUtilisateur = " + "'" + email + "'";
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			ArrayList<Trajet> trajet_list = new ArrayList<Trajet>();
			
			while(rs.next()) {
				int idTrajet = rs.getInt("idTrajet");
				Date dateDep = rs.getDate("dateDep");
				Timestamp heureDep = rs.getTimestamp("heureDep");
				int nbPlaces = rs.getInt("nbPlaces");
				String immaVehicule = rs.getString("immaVehicule");
				
				ArrayList<Troncon> list_troncons = fetchTroncon(email, idTrajet);
				
				Vehicule vehicule = fetchVehicule(immaVehicule);
				
				Utilisateur user = fetchUtilisateur(email);
				
				Trajet nvTraj = new Trajet(idTrajet, dateDep, heureDep, nbPlaces, vehicule, user, list_troncons);
				nvTraj.setTrajToTronçons();
				
				for(Troncon troncon : nvTraj.getTroncons()) {
					troncon.setTrajet(nvTraj);
				}
				
				trajet_list.add(nvTraj);
				
			}
			rs.close();
			return(trajet_list);
			
		} catch (Exception e){
			throw new BDDException("fetchTrajet fail");
		}
	}
	
	
	public static int fetchAttente(int idTrajet, int idTroncon) {
		
		String query = "SELECT duree FROM Attente WHERE Attente.idTroncon='" + idTroncon + "' AND Attente.idTrajet='" + idTrajet + "'";
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
				int res = rs.getInt("duree");
				rs.close();
				return res;
			
			}
			
			return -1;
			
		} catch (Exception e) {
			return -1;
			
		}
	}
	
	
	public static ArrayList<Troncon> fetchTroncon(String email, int idTrajet) throws BDDException {
		
		String query = "SELECT idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, etatCourant FROM Troncon WHERE idTrajet = " + idTrajet;
		
		try {			
			ResultSet rs = fetchQuery(query);
			
			ArrayList<Troncon> list_troncon = new ArrayList<Troncon>();
			
			while(rs.next()) {
				int idTroncon = rs.getInt("idTroncon");
				String villeDep = rs.getString("villeDep");
				int longDep = rs.getInt("longDep");
				int latDep = rs.getInt("latDep");
				String villeArr = rs.getString("villeArr");
				int longArr = rs.getInt("longArr");
				int latArr = rs.getInt("latArr");
				Timestamp dureeEst = rs.getTimestamp("dureeEst");
				String state = rs.getString("etatCourant");
				Etat etat;
				
				if(state.startsWith("prevu")) {
					etat = Etat.prevu;
				} else if(state.startsWith("parti")) {
					etat = Etat.parti;
				} else if(state.startsWith("annule")) {
					etat = Etat.annule;
				} else if(state.startsWith("arrive")) {
					etat = Etat.arrive;
				} else {
					etat = null;
				}
				
				
				Vehicule vehicule = fetchVehicule(idTrajet);
				
				int attente = fetchAttente(idTrajet, idTroncon);

				Troncon newTron = new Troncon(idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, attente, vehicule, null, etat);

				if(attente >= 0){
					newTron.setAttente(attente);
				}

				fetchPassagersTroncon(idTrajet, newTron);
				
				list_troncon.add(newTron);
			}
			rs.close();
			return list_troncon;
			
		} catch (Exception e){
			System.out.println("query = " + query);
			e.printStackTrace();
			throw new BDDException("fetchTroncon email + idTrajet fail");
		}
		
	}
	
	
	public static ArrayList<Troncon> fetchTroncon(String email, Trajet trajet) throws BDDException {
		
		String query = "SELECT idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, etatCourant FROM Troncon WHERE idTrajet = " + trajet.getIdTrajet();
		
		try {			
			ResultSet rs = fetchQuery(query);
			
			ArrayList<Troncon> list_troncon = new ArrayList<Troncon>();
			
			while(rs.next()) {
				int idTroncon = rs.getInt("idTroncon");
				String villeDep = rs.getString("villeDep");
				int longDep = rs.getInt("longDep");
				int latDep = rs.getInt("latDep");
				String villeArr = rs.getString("villeArr");
				int longArr = rs.getInt("longArr");
				int latArr = rs.getInt("latArr");
				Timestamp dureeEst = rs.getTimestamp("dureeEst");
				String state = rs.getString("etatCourant");
				Etat etat;
				
				if(state.startsWith("prevu")) {
					etat = Etat.prevu;
				} else if(state.startsWith("parti")) {
					etat = Etat.parti;
				} else if(state.startsWith("annule")) {
					etat = Etat.annule;
				} else if(state.startsWith("arrive")) {
					etat = Etat.arrive;
				} else {
					etat = null;
				}
				
				
				Vehicule vehicule = fetchVehicule(trajet.getIdTrajet());
				
				int attente = fetchAttente(trajet.getIdTrajet(), idTroncon);

				Troncon newTron = new Troncon(idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, attente, vehicule, null, etat);

				if(attente >= 0){
					newTron.setAttente(attente);
				}

				fetchPassagersTroncon(trajet.getIdTrajet(), newTron);
				
				list_troncon.add(newTron);
			}
			rs.close();
			return list_troncon;
			
		} catch (Exception e){
			throw new BDDException("fetchTroncon email + idTrajet fail");
		}
		
	}
	
	
	private static EtatSuivi fetchEtatSuivi(String email) {
		
		String query = "SELECT ParContientTr.suiviVoyageur FROM Parcours, ParContientTr ";
		
		query += "WHERE Parcours.idParcours=ParContientTr.idParcours AND Parcours.emailUtilisateur='" + email + "'";
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			EtatSuivi resultat;
			
			while(rs.next()) {
				
				String etat = rs.getString("ParContientTr.suiviVoyageur");
			
				if(etat.equals("Descendu")) {
					resultat = EtatSuivi.Descendu;
				} else if(etat.equals("pasMonte")) {
					resultat = EtatSuivi.pasMonte;
				} else {
					resultat = EtatSuivi.Monte;
				}
				
				rs.close();
				return resultat;
			}
			rs.close();
			return EtatSuivi.pasMonte;
			
		} catch (Exception e) {
    		
			return EtatSuivi.pasMonte;
			
		}
	}

	public static ArrayList<Troncon> fetchTroncon_v2(int idTrajet) throws BDDException {
		
		String query = "SELECT idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, etatCourant FROM Troncon WHERE idTrajet = " + idTrajet;
		
		query += " ORDER BY idTroncon ASC ";
		
		try {			
			ResultSet rs = fetchQuery(query);
			
			ArrayList<Troncon> list_troncon = new ArrayList<Troncon>();
			
			while(rs.next()) {
				int idTroncon = rs.getInt("idTroncon");
				String villeDep = rs.getString("villeDep");
				int longDep = rs.getInt("longDep");
				int latDep = rs.getInt("latDep");
				String villeArr = rs.getString("villeArr");
				int longArr = rs.getInt("longArr");
				int latArr = rs.getInt("latArr");
				Timestamp dureeEst = rs.getTimestamp("dureeEst");
				String state = rs.getString("etatCourant").trim();
				Etat etat;
								
				if(state.startsWith("prevu")) {
					etat = Etat.prevu;
				} else if(state.startsWith("parti")) {
					etat = Etat.parti;
				} else if(state.startsWith("annule")) {
					etat = Etat.annule;
				} else if(state.startsWith("arrive")) {
					etat = Etat.arrive;
				} else {
					etat = null;
				}
				
				Vehicule vehicule = fetchVehicule(idTrajet);
				
				int attente = fetchAttente(idTrajet, idTroncon);
				
				Trajet trajet = fetchTrajet(idTrajet);
				
				Troncon newTron = new Troncon(idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, attente, vehicule, trajet, etat);

				
				
				if(attente >= 0){
					newTron.setAttente(attente);
				}

				fetchPassagersTroncon(idTrajet, newTron);

				list_troncon.add(newTron);
			}
			rs.close();
			return list_troncon;
			
		} catch (Exception e){
			throw new BDDException("fetchTroncon email + idTrajet fail");
		}
		
	}
	
	
	public static void fetchPassagersTroncon(int idTrajet, Troncon t) throws BDDException {
		
		String query = "SELECT Parcours.emailutilisateur, p.suiviVoyageur from Parcours, ParContientTr p ";
		
		query += "WHERE p.idTrajet = " + idTrajet + " AND p.idTroncon = " + t.getIdTroncon() + " ";
		
		query += "AND Parcours.idParcours=p.idParcours";
		
		ArrayList<Utilisateur> res = new ArrayList<Utilisateur>();

		try{
			ResultSet rs = fetchQuery(query);

			while(rs.next()){
				Utilisateur user = fetchUtilisateur(rs.getString("emailutilisateur"));
				String etat = rs.getString("suiviVoyageur").trim();
				EtatSuivi e = null;
				if(etat.equals("pasMonte")){
					e = EtatSuivi.pasMonte;
				}
				else if(etat.equals("Monte")){
					e = EtatSuivi.Monte;
				}
				else if(etat.equals("Descendu")){
					e = EtatSuivi.Descendu;
				}
				if(user != null){
					res.add(user);
					t.addSuivi(user, e);
				}
			}
			rs.close();
		}
		catch(Exception e){
			throw new BDDException("fetchPassagersTroncon"  + idTrajet  +"fail");
		}

		t.setPassagers(res);
	}

	
	public static Troncon fetchTroncon(int idTrajet, int idTroncon) throws BDDException {
		
		String query = "SELECT villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, etatCourant ";
		
		query += "FROM Troncon WHERE Troncon.idTrajet = ";
		
		query += idTrajet + " AND Troncon.idTroncon = " + idTroncon;
		
		query += " ORDER BY idTroncon ASC ";
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
			
				String villeDep = rs.getString("villeDep");
				int longDep = rs.getInt("longDep");
				int latDep = rs.getInt("latDep");
				String villeArr = rs.getString("villeArr");
				int longArr = rs.getInt("longArr");
				int latArr = rs.getInt("latArr");
				Timestamp dureeEst = rs.getTimestamp("dureeEst");
				
				String immatriculation = fetchImmatriculation_from_Trajet(idTrajet);
									
				Vehicule vehicule = fetchVehicule(immatriculation);
					
				int attente = fetchAttente(idTrajet, idTroncon);

				String state = rs.getString("etatCourant");
				Etat etat;

				if(state.startsWith("prevu")) {
					etat = Etat.prevu;
				} else if(state.startsWith("parti")) {
					etat = Etat.parti;
				} else if(state.startsWith("annule")) {
					etat = Etat.annule;
				} else if(state.startsWith("arrive")) {
					etat = Etat.arrive;
				} else {
					etat = null;
				}

				Troncon newTron = new Troncon(idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, attente, vehicule, null, etat);
							
				if(attente >= 0){
					newTron.setAttente(attente);
				}
				
				fetchPassagersTroncon(idTrajet, newTron);
				
				rs.close();
				return(newTron);
			}
			
		} catch (Exception e){
			throw new BDDException("fetchTroncon  + idTrajet fail");
		}
		
		return null;
		
	}
	
	
	
	public static Troncon fetchTroncon(Trajet trajet, int idTroncon) throws BDDException {
		
		String query = "SELECT villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, etatCourant, immatriculation ";
		
		query += "FROM Troncon WHERE Troncon.idTrajet = ";
		
		query += trajet.getIdTrajet() + " AND Troncon.idTroncon = " + idTroncon;
		
		query += " ORDER BY idTroncon ASC ";
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
			
				String villeDep = rs.getString("villeDep");
				int longDep = rs.getInt("longDep");
				int latDep = rs.getInt("latDep");
				String villeArr = rs.getString("villeArr");
				int longArr = rs.getInt("longArr");
				int latArr = rs.getInt("latArr");
				Timestamp dureeEst = rs.getTimestamp("dureeEst");
				
				String immatriculation = fetchImmatriculation_from_Trajet(trajet.getIdTrajet());
									
				Vehicule vehicule = fetchVehicule(immatriculation);
					
				int attente = fetchAttente(trajet.getIdTrajet(), idTroncon);

				String state = rs.getString("etatCourant");
				Etat etat;

				switch(state){
					case "prevu":
						etat = Etat.prevu;
						break;
					case "parti":
						etat = Etat.parti;
						break;
					case "annule":
						etat = Etat.annule;
						break;
					case "arrive":
						etat = Etat.arrive;
						break;
					default:
						etat = null; 
				}

				Troncon newTron = new Troncon(idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr, dureeEst, attente, vehicule, trajet, etat);
			
				if(attente >= 0){
					newTron.setAttente(attente);
				}
				
				rs.close();
				
				return(newTron);
			}
			
		} catch (Exception e){
			throw new BDDException("fetchTroncon  + idTrajet fail");
		}
		
		return null;
		
	}
	
	
	
	private static String fetchImmatriculation_from_Trajet(int idTrajet) throws BDDException {
		
		String query = "SELECT immaVehicule FROM Trajet WHERE Trajet.idTrajet=" + idTrajet;
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
				String imma = rs.getString("immaVehicule");
				rs.close();
				return imma;
				
			}
						
		} catch (Exception e) {
			throw new BDDException("fetchTroncon idParoucrs failed");
		}
		
		return null;
	}
	
	

	public static ArrayList<Troncon> fetchTroncon(int idParcours) throws BDDException {
		
		ArrayList<Troncon> list_troncon = new ArrayList<Troncon>();
		
		String query = "SELECT idTroncon, idTrajet FROM ParContientTr WHERE ParContientTr.idParcours = " + idParcours;
				
		try {
						
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
				
				int idTroncon = rs.getInt("idTroncon");
				
				int idTrajet = rs.getInt("idTrajet");
				
				Troncon troncon = fetchTroncon(idTrajet, idTroncon);
				
				troncon.setTrajet(fetchTrajet(idTrajet));
				
				list_troncon.add(troncon);
				
			}
			rs.close();
			return(list_troncon);
			
		} catch (Exception e) {
			throw new BDDException("fetchTroncon idParoucrs failed");
		}
		
	}
	
	public static ArrayList<Vehicule> fetchVehicules(String email) throws BDDException {
		
		 ArrayList<Vehicule> list_vehicules = new ArrayList<Vehicule>();
		 
		 String query = "SELECT immaVehicule FROM Conduit WHERE Conduit.emailUtilisateur='" + email + "'";
		 
		 try {
			 
			 ResultSet rs = fetchQuery(query);
			 
			 while(rs.next()) {
				 
				 String immatriculation = rs.getString("immaVehicule");
				 
				 list_vehicules.add(fetchVehicule(immatriculation));
				 
			 }
			 rs.close();
		 } catch (Exception e) {
			 throw new BDDException("fetchVehiculeS failed");
		 }
		 
		 return list_vehicules;
	}
	
	public static Vehicule fetchVehicule(String email, int idTrajet) throws BDDException {
		
		String query = "SELECT immaVehicule FROM Trajet WHERE Trajet.idTrajet=" + idTrajet + " AND Trajet.emailUtilisateur='" + email + "'";
		
		try {
			
			 ResultSet rs = fetchQuery(query);
			 
			 while(rs.next()) {
				 
				 String immatriculation = rs.getString("immaVahicule");
				 rs.close();
				 return fetchVehicule(immatriculation);
				 
			 }
			 rs.close();
		} catch (Exception e) {
			 throw new BDDException("fetchVehicule with email and idTrajet failed");
		}
		
		return null;
		
	}
	
	public static Parcours fetchParcours(int idParcours) throws BDDException{
		
		String query = "SELECT emailUtilisateur FROM Parcours WHERE Parcours.idParcours=" + idParcours;
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
			
				Utilisateur utilisateur = fetchUtilisateur(rs.getString("emailUtilisateur"));
				
				Parcours parcours = new Parcours(idParcours, utilisateur);
				
				parcours.setTroncons(fetchTroncon(idParcours));
								
				return parcours;
			
			}
			rs.close();
		} catch (Exception e) {
			throw new BDDException("fetch ParcourS with idParcours failed");
		}
				
		return null;
	}
	
	
	public static ArrayList<Parcours> fetchParcours(Utilisateur utilisateur) throws BDDException{
		
		String query = "SELECT idParcours FROM Parcours WHERE Parcours.emailUtilisateur='" + utilisateur.getEmail() + "'";
		
				
		ArrayList<Parcours> list_parcours = new ArrayList<Parcours>();
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
			
				int idParcours = rs.getInt("idParcours");
				
				Parcours parcours = new Parcours(idParcours, utilisateur);
				
				parcours.setTroncons(fetchTroncon(idParcours));
								
				list_parcours.add(parcours);
			
			}
			rs.close();
		} catch (Exception e) {
			throw new BDDException("fetch ParcourS with idParcours failed");
		}
		
		return list_parcours;
	}
	
	
	public static Vehicule fetchVehicule(String immatriculation) throws BDDException {
		
		String query = "SELECT marque, modele, puisFisc, nbPlaces, energieUtilisee FROM Vehicule WHERE Vehicule.immatriculation='" + immatriculation + "'";
		
		try {
						
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
							
				String marque = rs.getString("marque");
				String modele = rs.getString("modele");
				float puisFisc = rs.getFloat("puisFisc");
				int nbPlaces = rs.getInt("nbPlaces");
				String string_energie = rs.getString("energieUtilisee");
				
				String query0 = "SELECT emailUtilisateur FROM Conduit WHERE Conduit.immaVehicule='" + immatriculation + "'";
				
				rs.close();
				ResultSet rs2 = fetchQuery(query0);
				
				while(rs2.next()) {
									
					Utilisateur utilisateur = fetchUtilisateur(rs2.getString("emailUtilisateur"));
				
					Energie energie;
					
					if(string_energie.matches("Essence")) {
						energie = new Energie(TypeEnergie.Essence);
					} else if(string_energie.matches("Diesel")) {
						energie = new Energie(TypeEnergie.Diesel);
					} else {
						energie = new Energie(TypeEnergie.Electrique);
					}
					rs2.close();				
					return new Vehicule(immatriculation, marque, modele, puisFisc, nbPlaces, energie, utilisateur);
				}
			
			}
			
		} catch (Exception e) {
			throw new BDDException("fetch ParcourS with idParcours failed");
		}
		
		return null;
	}
	
	
	public static Vehicule fetchVehicule(int idTrajet) throws BDDException {
		
		String query = "SELECT immaVehicule FROM Trajet WHERE Trajet.idTrajet=" + idTrajet;
		
		try {
						
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
					Vehicule v = fetchVehicule(rs.getString("immaVehicule"));
					rs.close();
					return v;
				
			}
			
		} catch (Exception e) {
			throw new BDDException("fetch ParcourS with idParcours failed");
		}
		
		return null;
	}
	
	
	public static Vehicule fetchVehicule_withoutUser(String immatriculation) throws BDDException {
		
		String query = "SELECT marque, modele, puisFisc, nbPlaces, energieUtilisee FROM Vehicule WHERE Vehicule.immatriculation='" + immatriculation + "'";
		
		try {
						
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
							
				String marque = rs.getString("marque");
				String modele = rs.getString("modele");
				float puisFisc = rs.getFloat("puisFisc");
				int nbPlaces = rs.getInt("nbPlaces");
				String string_energie = rs.getString("energieUtilisee");
				
				Energie energie;
					
				if(string_energie.matches("Essence")) {
					energie = new Energie(TypeEnergie.Essence);
				} else if(string_energie.matches("Diesel")) {
					energie = new Energie(TypeEnergie.Diesel);
				} else {
					energie = new Energie(TypeEnergie.Electrique);
				}
				rs.close();
				return new Vehicule(immatriculation, marque, modele, puisFisc, nbPlaces, energie, null);
			}
			
			
		} catch (Exception e) {
			throw new BDDException("fetch ParcourS with idParcours failed");
		}
		
		return null;
	}
	
	
	public static String fetchMdp(String email) throws BDDException {
		
		String query = "SELECT mdp FROM Utilisateur WHERE Utilisateur.email='" + email + "'";
		
		try {
			
			ResultSet rs = fetchQuery(query);
			
			while(rs.next()) {
				String res = rs.getString("mdp");
				rs.close();	
				return(res);
				
			}
			
		} catch (Exception e) {
			throw new BDDException("fetchMdp failed");
		}
		
		return "";
		
	}

	public static int fetchMaxIdTrajet() throws BDDException {
		String query = "Select max(idTrajet) as idtrajet from Trajet";
		try {
			ResultSet rs = fetchQuery(query);
			while(rs.next()){
				int res = rs.getInt("idTrajet");
				return res;
			}
		}
		catch(Exception e){
			throw new BDDException("fetchMaxIdTrajet failed");
		}
		return 0;

	}
	
	
	
	public static Trajet fetchTrajet(int idTrajet) throws BDDException {
		
		String query = "SELECT dateDep, heureDep, nbPlaces, immaVehicule, emailUtilisateur FROM Trajet WHERE Trajet.idTrajet = " + idTrajet;
		
		try {
			
			ResultSet rs = fetchQuery(query);
						
			while(rs.next()) {
				
				Date dateDep = rs.getDate("dateDep");
				Timestamp heureDep = rs.getTimestamp("heureDep");
				int nbPlaces = rs.getInt("nbPlaces");
				String immaVehicule = rs.getString("immaVehicule");
				String emailUtilisateur = rs.getString("emailUtilisateur");
				
				ArrayList<Troncon> list_troncons = fetchTroncon(emailUtilisateur, idTrajet);
				
				Vehicule vehicule = fetchVehicule(immaVehicule);
				
				Utilisateur u = fetchUtilisateur(emailUtilisateur);

				Trajet nvTraj = new Trajet(idTrajet, dateDep, heureDep, nbPlaces, vehicule, u, list_troncons);
				nvTraj.setTrajToTronçons();
				
				for(Troncon troncon : nvTraj.getTroncons()) {
					troncon.setTrajet(nvTraj);
				}
				rs.close();
				return nvTraj;
			}
			
		} catch (Exception e){
			throw new BDDException("fetchTrajet fail");
		}
		
		return null;
	}
	
	
	
	public static ArrayList<Integer> fetch_parcours_oneTrajet(String villeDep, String villeArr, String emailUser) throws BDDException{
		
		String query = "SELECT t1.idTrajet FROM Troncon t1, Troncon t2, Trajet WHERE t1.idTrajet = t2.idTrajet AND t1.villeDep = '" + villeDep;
	    	
	    query += "' AND t2.villeArr = '" + villeArr + "' AND t1.idTroncon <= t2.idTroncon";
	    
	    query += " AND t1.idTrajet=Trajet.idTrajet AND Trajet.emailUtilisateur <> '" + emailUser + "'";
	    	    	
	    ArrayList<Integer> idTrajets = new ArrayList<Integer>();
	    	    
	    try {
				
	    	ResultSet rs = fetchQuery(query);
	    		    		
			while(rs.next()) {
								
				idTrajets.add(rs.getInt("t1.idTrajet"));
				
					
			}	
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BDDException("fetch_parcours_oneTrajet failed");
		}
		
	    return idTrajets;
			
	}
	
	
	public static ArrayList<Troncon> fetch_troncons_from_oneTrajet(String villeDep, String villeArr, int idTrajet) throws BDDException{
		
		ArrayList<Troncon> resultat = new ArrayList<Troncon>();
		
		ArrayList<Troncon> troncons =  fetchTroncon_v2(idTrajet);
		
		boolean flag_depart = false;
		
		boolean flag_arrivee = false;
		
		for(Troncon troncon : troncons) {
			
			if( (troncon.getVilleDep().equals(villeDep) | flag_depart) & !flag_arrivee) {
				
				flag_depart = true;
				
				resultat.add(troncon);
				
			} 
			
			if(troncon.getVilleArr().contentEquals(villeArr) & !flag_arrivee) {
				
				flag_arrivee = true;
				
			}
			
		}
		
		return resultat;
		
	}
	
	
	public static ArrayList<IntersectionParcours> fetch_parcours_twoTrajet(String villeDep, String villeArr, String emailUtilisateur) throws BDDException{
		
		
		String query = "SELECT t1.idTrajet as p1, t4.idTrajet as p2, t2.villeArr as p3 FROM Troncon t1, Troncon t2, Troncon t3, Troncon t4, Trajet tj1, Trajet tj2";
		
		query += " WHERE t1.idTrajet=t2.idTrajet AND t1.villeDep='" + villeDep + "' AND t2.villeArr=t3.villeDep AND t3.idTrajet=t4.idTrajet";
		
		query += " AND t4.villeArr='" + villeArr + "' AND t1.idTroncon <= t2.idTroncon AND t3.idTroncon <= t4.idTroncon";
	    	
	    query += " AND t1.idTroncon <= t2.idTroncon AND t1.idTrajet <> t4.idTrajet AND tj1.idTrajet=t1.idTrajet AND tj2.idTrajet=t4.idTrajet";
	    
	    query += " AND tj1.emailUtilisateur <> '" + emailUtilisateur + "' AND tj2.emailUtilisateur <> '" + emailUtilisateur + "'";
	    	
	    ArrayList<IntersectionParcours> intersectionParcours = new ArrayList<IntersectionParcours>();
	    	
	    try {
	    					
	    	ResultSet rs = fetchQuery(query);
	    		
			while(rs.next()) {
				
				intersectionParcours.add(new IntersectionParcours(rs.getInt("p1"), rs.getInt("p2"), rs.getString("p3")));
					
			}	
			rs.close();
		} catch (Exception e) {
			throw new BDDException("fetch_parcours_twoTrajet failed");
		}
		
	    return intersectionParcours;
	}
	
	
	public static ArrayList<Troncon> fetch_troncons_from_twoTrajet(String villeDep, String villeArr, IntersectionParcours intersection) throws BDDException{
		
		ArrayList<Troncon> resultat = fetch_troncons_from_oneTrajet(villeDep, intersection.getVilleIntersection(), intersection.getIdTrajet_1());
		
		resultat.addAll(fetch_troncons_from_oneTrajet(intersection.getVilleIntersection(), villeArr, intersection.getIdTrajet_2()));
		
		return resultat;
		
	}
	
	public static ArrayList<Parcours> fetchUserParcours(Utilisateur user) throws BDDException{
		String query = "SELECT idparcours from parcours where emailutilisateur ='" + user.getEmail() + "'";
		ArrayList<Parcours> listeParcours= new ArrayList<Parcours>();
		try{
			ResultSet rs = fetchQuery(query);
			while(rs.next()){
				int idparcours = rs.getInt("idparcours");
				Parcours newParcours = new Parcours(idparcours, user);
				String queryTroncon = "SELECT idtroncon, idtrajet from 	parcontienttr where idparcours =" + idparcours +" order by ordrepar";
				try{
					ResultSet resultTroncon = fetchQuery(queryTroncon);
					while(resultTroncon.next()){
						int idTrajet = resultTroncon.getInt("idtrajet");
						int idTroncon = resultTroncon.getInt("idtroncon");
						newParcours.getTroncons().add(fetchTroncon(idTrajet, idTroncon));

					}
					listeParcours.add(newParcours);
				}
				catch(Exception e){
					throw new BDDException("Erreur de recuperation des troncons d'un parcours");
				}
			}
			rs.close();
		}
		catch(Exception e){
			throw new BDDException("Erreur fetch parcours utilisateur");
		}
		return listeParcours;
	}

	public static int fetchMaxIdParcours() throws BDDException{
		String query = "Select max(idParcours) as idparcours from Parcours";
		try {
			ResultSet rs = fetchQuery(query);
			while(rs.next()){
				return rs.getInt("idParcours");
			}
		}
		catch(Exception e){
			throw new BDDException("fetchMaxIdParcours failed");
		}
		return 0;
	}
	
	
	public static ArrayList<Integer> fetch_parcours_villeDep(String villeDep, String emailUser) throws BDDException{
		
		String query = "SELECT Troncon.idTrajet FROM Troncon, Trajet WHERE Troncon.villeDep = '" + villeDep + "'";
		
		query += " AND Troncon.idTrajet=Trajet.idTrajet AND Trajet.emailUtilisateur <> '" + emailUser + "'";
	    		    	    	
	    ArrayList<Integer> idTrajets = new ArrayList<Integer>();
	    	
	    try {
				
	    	ResultSet rs = fetchQuery(query);
	    		
			while(rs.next()) {
				
				idTrajets.add(rs.getInt("idTrajet"));
					
			}	
			rs.close();
		} catch (Exception e) {
			throw new BDDException("fetch_parcours_oneTrajet failed");
		}
		
	    return idTrajets;
			
	}

	public static ArrayList<Troncon> fetch_troncons_from_oneTrajet(String villeDep, Integer idTrajet) throws BDDException {

		ArrayList<Troncon> resultat = new ArrayList<Troncon>();
		
		ArrayList<Troncon> troncons =  fetchTroncon_v2(idTrajet);
		
		boolean flag_depart = false;
				
		for(Troncon troncon : troncons) {
			
			if(troncon.getVilleDep().equals(villeDep) | flag_depart) {
				
				flag_depart = true;
				
				resultat.add(troncon);
				
			} 
			
		}
		
		return resultat;
	}
}
