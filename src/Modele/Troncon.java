package Modele;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import BDD.BDDException;
import BDD.InsertBDD;
import BDD.UpdateBDD;
import java.lang.Math;
public class Troncon {

	public int getIdTroncon() {
		return idTroncon;
	}

	public String getVilleDep() {
		return villeDep;
	}

	public float getLongDep() {
		return longDep;
	}

	public float getLatDep() {
		return latDep;
	}

	public String getVilleArr() {
		return villeArr;
	}

	public float getLongArr() {
		return longArr;
	}

	public float getLatArr() {
		return latArr;
	}

	public float getDistance() {
		double longArrRad = Math.toRadians(longArr);
		double longDepRad = Math.toRadians(longDep);
		double latArrRad = Math.toRadians(latArr);
		double latDepRad = Math.toRadians(latDep);
		double deltaLong = longArrRad-longDepRad;
		double deltaLat = latArrRad - latDepRad;
		double a = Math.pow(Math.sin(deltaLat/2),2)+(Math.pow(Math.sin(deltaLong/2),2)*Math.cos(latArrRad)*Math.sin(latDepRad));
		double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		double d = 6371*c;
		return (float)d;
	}

	public Timestamp getDureeEst() {
		return dureeEst;
	}


	public int getDureeMaxAtt() {
		return dureeMaxAtt;
	}

	public Etat getEtat() {
		return etat;
	}

	public Vehicule getVehicule() {
		return vehicule;
	}

	public Trajet getTrajet(){
		return trajet;
	}

	public void setTrajet(Trajet traj){
		trajet = traj;
	}

	public void setAttente(int att){
		attente = att;
	}

	public EtatSuivi getSuivi(String email){
		return suivi_voyageur.get(email);
	}

	public int getAttente(){
		return attente;
	}

	public Utilisateur getUtilisateur(){
		if(trajet != null){
			return trajet.getUtilisateur();
		}
		else{
			return null;
		}
	}

	public void setIdTroncon(int i){
		this.idTroncon=i;
	}

	public boolean updateEtatSuivi(Utilisateur user, int idParcours, EtatSuivi etat){
		this.suivi_voyageur.put(user.getEmail(), etat);
		return UpdateBDD.updateEtatSuivi(this, idParcours, user.getEmail());
	}

	public ArrayList<Utilisateur> getPassagers(){
		return passagers;
	}

	private int idTroncon;
	private String villeDep;
	private float longDep;
	private float latDep;
	private String villeArr;
	private float longArr;
	private float latArr;
	private Timestamp dureeEst;
	private int dureeMaxAtt;
	private Etat etat;
	private Vehicule vehicule;
	private Trajet trajet;
	private int attente;
	private ArrayList<Utilisateur> passagers;
	private HashMap<String, EtatSuivi> suivi_voyageur;

	public Troncon(int idTroncon, String villeDep, float longDep, float latDep, String villeArr, float longArr, float latArr, Timestamp dureeEst, int dureeMaxAtt, Vehicule vehicule, Trajet trajet, Etat etat) {
		this.idTroncon = idTroncon;
		this.villeDep = villeDep.trim();
		this.longDep = longDep;
		this.latDep = latDep;
		this.villeArr = villeArr.trim();
		this.longArr = longArr;
		this.latArr = latArr;
		this.dureeEst = dureeEst;
		this.dureeMaxAtt = dureeMaxAtt;
		this.vehicule = vehicule;
		this.trajet = trajet;
		this.etat = etat;
		this.passagers = new ArrayList<Utilisateur>();
		this.suivi_voyageur = new HashMap<String, EtatSuivi>();
	}

	public void setPassagers(ArrayList<Utilisateur> passagers){
		this.passagers = passagers;
	}

	public void addSuivi(Utilisateur utilisateur, EtatSuivi etat){
		this.suivi_voyageur.put(utilisateur.getEmail(), etat);
	}
	
	public boolean addPassager(Utilisateur passager){
		if(passagers.size() < trajet.getNbPlaces()){
			passagers.add(passager);
			return true;
		}
		else{
			return false;
		}
	}

	public boolean update() {
		return UpdateBDD.update(this);
	}

	public boolean insert(int idTrajet) throws BDDException {
		return InsertBDD.insert(this, idTrajet);
	}

	public boolean updateState(Etat state) {
		Etat preced = etat;
		this.etat = state;
		if(this.update()){
			return true;
		}
		else{
			etat = preced;
			return false;
		}
	}

	public double PrixTroncon() {
		return (this.vehicule.getPuisFisc() * this.vehicule.getEnergie().alpha() * getDistance() * 0.1);
	}

	public HashMap<String, EtatSuivi> getSuivi_voyageur() {
		return suivi_voyageur;
	}

	public Timestamp getDateDep(){
		Date dat = getTrajet().getDateDep();
		Timestamp heu = getTrajet().getHeureDep();
		int y = dat.getYear();
		int m = dat.getMonth();
		int d = dat.getDate();
		int h = heu.getHours();
		int min = heu.getMinutes();

		for(int i = 0; i < getIdTroncon(); i++){
			Timestamp duree = getTrajet().getTroncons().get(i).getDureeEst();
			min += duree.getMinutes();
			h += duree.getHours();
			if(min > 59){
				h += min%60;
				min %= 60;
			}
			if(h > 23){
				d += h%24;
				h %= 24;
			}
		}

		Timestamp res = new Timestamp(y, m, d, h, min, 0, 0);
		return res;
	}
}
