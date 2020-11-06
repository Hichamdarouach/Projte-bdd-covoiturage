package Controleur;

import java.sql.Timestamp;

import Modele.*;

public class ControleurTroncon {
    private Troncon tronconCourant;

    public void setTronconCourant(Troncon troncon){
        tronconCourant = troncon;
    }

    public Troncon getTronconCourant(){
        return tronconCourant;
    }

    public boolean annulerTroncon(){
        if(tronconCourant.getEtat() == Etat.prevu){
            return tronconCourant.updateState(Etat.annule);
        }
        else{
            return false;
        }
    }

    public boolean etatSuivant(){
        boolean res = true;
        switch(tronconCourant.getEtat()){
            case prevu:
                res = tronconCourant.updateState(Etat.parti);
                break;
            case parti:
                res = tronconCourant.updateState(Etat.arrive);
                break;
            default:
                res = false;
        }

        return res;
    }

    public boolean updateSuivi(Utilisateur usr, int idParcours){
        boolean res = true;
        switch(tronconCourant.getSuivi(usr.getEmail())){
            case pasMonte:
                res = tronconCourant.updateEtatSuivi(usr, idParcours, EtatSuivi.Monte);
                break;
            case Monte:
                res = tronconCourant.updateEtatSuivi(usr, idParcours, EtatSuivi.Descendu);
                break;
            default:
                res = false;
        }
        return res;
    }


    public static Timestamp addTime(Timestamp t1, Timestamp t2){
        int minutes = t1.getMinutes() + t2.getMinutes();
        int hours = t1.getHours() + t2.getHours();
        if(minutes > 59){
            hours += minutes / 60;
            minutes %= 60;
        }
        hours %= 24;
    
        return new Timestamp(0, 0, 0, hours, minutes, 0, 0);
    }

    public void nextTroncon(){
        if(tronconCourant != null){
            int nextId = tronconCourant.getIdTroncon() + 1;
            if(nextId < tronconCourant.getTrajet().getTroncons().size()){
                tronconCourant = tronconCourant.getTrajet().getTroncons().get(nextId);
            }
        }
    }

    public void prevTroncon(){
        if(tronconCourant != null){
            int nextId = tronconCourant.getIdTroncon() - 1;
            if(nextId >= 0){
                tronconCourant = tronconCourant.getTrajet().getTroncons().get(nextId);
            }
        }
    }

    
}
