package Controleur;

import BDD.*;
import Modele.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ControleurTrajet {
    private Trajet trajetCourant;
    private ArrayList<Trajet> trajets;

    public ControleurTrajet(){
        trajets = new ArrayList<Trajet>();
    }

    public boolean nouveauTrajet(Date dateDep, Timestamp heureDep, int nbPlaces, Vehicule vehicule, Utilisateur utilisateur, ArrayList<Troncon> troncons) throws BDDException {
        int idTrajet = FetchBDD.fetchMaxIdTrajet()+1;
        Trajet nouveauTrajet = new Trajet(idTrajet, dateDep, heureDep, nbPlaces, vehicule, utilisateur,troncons);
        boolean succes = nouveauTrajet.insert();
        return succes;
    }

    public ArrayList<Trajet> getTrajets(){
        return trajets;
    }

    public void setTrajetCourant(Trajet trajet){
        trajetCourant = trajet;
    }

    public Trajet getTrajetCourant(){
        return trajetCourant;
    }

    public void ajouterTroncon(String villeDep, int longDep, int latDep, String villeArr, int longArr,
            int latArr, Timestamp dureeEst, int dureeMaxAtt,
            Vehicule vehicule) throws BDDException {
        int idTroncon = this.trajetCourant.getTroncons().size();
        Troncon nouveauTroncon = new Troncon(idTroncon, villeDep, longDep, latDep, villeArr, longArr, latArr,
                dureeEst, dureeMaxAtt, vehicule, trajetCourant, Etat.prevu);
        this.trajetCourant.addTroncon(nouveauTroncon);
        
        //boolean successTroncon = nouveauTroncon.insert();
        //boolean successTrajet = trajetCourant.addTroncon(nouveauTroncon);
        //return successTroncon && successTrajet;
    }

    

    // TODO (à surcharger)
    public boolean chercherTrajets(Utilisateur user) throws BDDException {

        trajets = FetchBDD.fetchTrajets(user.getEmail());
        if(trajets != null){
            for(Trajet t : trajets){
                t.setUtilisateur(user);
            }
            return true;
        }
        else{
            return false;
        }
    }

}