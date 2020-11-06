package Controleur;

import BDD.*;
import Modele.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ControleurCompte {

    public Utilisateur utilisateurCourant;

    public ControleurCompte(Utilisateur utilisateur){
        this.utilisateurCourant = utilisateur;
    }
    public ControleurCompte(){};
    /**
     * Cree un nouvel utilisateur et l'envoie dans la BDD
     * @param email
     * @param nom
     * @param pnom
     * @param villeRes
     * @param mdp
     * @return true s'il n'y a pas eu d'erreur de BDD, false sinon
     * @throws NoSuchAlgorithmException
     * @throws BDDException
     */
    public boolean nouvelUtilisateur(String email, String nom, String pnom, String villeRes, String mdp) throws NoSuchAlgorithmException, BDDException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mdp.getBytes());
        
        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

        Utilisateur nouveau = new Utilisateur(email, nom, pnom, villeRes, sb.toString(), 0);
        boolean succes = nouveau.insert();
        if(succes){
            utilisateurCourant = nouveau;
        }
        return succes;
    }
    /**
     * Ajoute un vehicule au compte courant. Si le vehicule existe deja dans la BDD, il est update avec un nouvel utilisateur, 
     * sinon il est cree et insere dans la bdd
     * @param immatriculation
     * @param marque
     * @param modele
     * @param puisFisc
     * @param nbPlaces
     * @param energie
     * @return true si pas d'erreur avec la BDD, false ninon
     * @throws BDDException
     */
    public boolean ajouterVehicule(String immatriculation, String marque, String modele, float puisFisc, int nbPlaces,
            Energie energie) throws BDDException {
        boolean succesVehicule;
        Vehicule nouveau = new Vehicule(immatriculation, marque, modele, puisFisc, nbPlaces, energie, utilisateurCourant);
        this.utilisateurCourant.linkVehicule(nouveau);
        succesVehicule = InsertBDD.insert(nouveau);
        
        boolean succesConduit = InsertBDD.insert(nouveau, utilisateurCourant);
        
        return (succesVehicule && succesConduit);
    }

    public boolean ajouterVehiculeExistant(String immatriculation) throws BDDException{
        Vehicule fetchedVehicule = FetchBDD.fetchVehicule_withoutUser(immatriculation);
        if(fetchedVehicule==null){
            return false;
        }
        else {
            utilisateurCourant.linkVehicule(fetchedVehicule);
            boolean succesConduit = InsertBDD.insert(fetchedVehicule, utilisateurCourant);
            return succesConduit;
        }
    }
    
    
    public boolean recupererVehicules() throws BDDException{
        if(utilisateurCourant != null){
            return utilisateurCourant.fetchVehicules();
        }
        else{
            return false;
        }
    }

    /**
     * Ajoute la quantite solde au solde de l'utilisateur courant
     * @param solde
     * @return
     */
    public boolean crediter(float solde) {
        return utilisateurCourant.crediter(solde);
    }
    /**
     * Fonction pour que l'utilisateur se connecte en entrant son mail et son mdp
     * la fonctiondemande le hash du mdp associe a l'email, et le compare au hash du mdp entree
     * @param email
     * @param mdp
     * @return true si le mail existe et le mdp entre est correct, false sinon et si probleme de BDD
     * @throws NoSuchAlgorithmException
     * @throws BDDException
     */
    public boolean connexion(String email, String mdp) throws NoSuchAlgorithmException, BDDException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mdp.getBytes());
        
        byte[] byteData = md.digest();
        
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        
        String passStocke = FetchBDD.fetchMdp(email);
        if(passStocke==null){
            return false;
        }
        
        boolean passCorrect = sb.toString().equals(passStocke);
                
        if(!passCorrect){
            return false;
        }
        
        utilisateurCourant = FetchBDD.fetchUtilisateur(email);   
        return true;
    }

    public Utilisateur getUtilisateur(){
        return utilisateurCourant;
    }

    public void deconnexion(){
        utilisateurCourant = null;
    }
}