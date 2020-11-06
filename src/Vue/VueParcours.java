package Vue;

import javax.swing.*;

import BDD.BDDException;
import Modele.*;
import Modele.Parcours.Changement;

import java.awt.event.ActionEvent;
import Controleur.*;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.Font;

public class VueParcours extends JPanel {
    
    private Font nonBold;

    private ControleurCompte userCtrl = new ControleurCompte();
    private ControleurTroncon tronconCtrl = new ControleurTroncon();
    private ControleurParcours parcCtrl = new ControleurParcours();

    private JPanel panelListParcours;
    private JPanel panelSearchParcours;
    private JPanel panelTroncon;
    private JPanel panelDetailParcours;

    // List parcours
    private JLabel listParcoursLbl;
    private JScrollPane listParcoursScroll;
    private JPanel listParcoursPanel;
    private JButton btnSearchPar;

    // Search parcours
    private JLabel searchStartCityLbl;
    private JLabel searchEndCityLbl;
    private JLabel searchStartLongLatLbl;
    private JLabel searchEndLongLatLbl;

    private JTextField searchStartCityTxt;
    private JTextField searchEndCityTxt;
    private JTextField searchStartLongTxt;
    private JTextField searchEndLongTxt;
    private JTextField searchStartLatTxt;
    private JTextField searchEndLatTxt;

    private JButton searchLaunchBtn;
    private JButton searchBackBtn;

    private JScrollPane searchResultPane;
    private JPanel searchResultPanel;

    // Parcours details
    private JLabel parDetailStartLbl;
    private JLabel parDetailEndLbl;
    private JLabel parDetailDateLbl;
    private JLabel parDetailTronconsLbl;
    private JLabel parDetailPrixLbl;

    private JScrollPane parTronconListScroll;
    private JPanel parTronconListPanel;

    private JButton parDetailSubBtn;
    private JButton parDetailBackBtn;

    // Troncon details
    private JLabel tronconStartLbl;
    private JLabel tronconEndLbl;
    private JLabel tronconDistLbl;
    private JLabel tronconDurationLbl;
    private JLabel tronconAttMaxLbl;
    private JLabel tronconVehicLbl;
    private JLabel tronconStateLbl;
    private JLabel tronconDriverLbl;
    private JLabel tronconPriceLbl;
    
    private JButton tronconNextBtn;
    private JButton tronconPrevBtn;
    private JButton tronconStepBtn;
    private JButton tronconBackBtn;

    public VueParcours(){
        setLayout(new GridBagLayout());

        initPanelListParcours();
        initPanelSearchParcours();
        initPanelTroncon();
        initPanelDetailParcours();

        showPanel(panelListParcours);
    }

    private void initPanelListParcours(){
        panelListParcours = new JPanel();
        panelListParcours.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        listParcoursLbl = new JLabel("Parcours de Jean Némar");
        listParcoursLbl.setHorizontalAlignment(JLabel.CENTER);
        nonBold = listParcoursLbl.getFont();
        nonBold = nonBold.deriveFont(nonBold.getStyle() & ~Font.BOLD);

        listParcoursPanel = new JPanel();
        listParcoursPanel.setLayout(new GridBagLayout());
        listParcoursScroll = new JScrollPane(listParcoursPanel);
        listParcoursScroll.setPreferredSize(new Dimension(10, 200));

        btnSearchPar = new JButton("Chercher un nouveau parcours");
        btnSearchPar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelSearchParcours);
            }
        });

        panelListParcours.add(listParcoursLbl, c);
        c.gridy++;
        panelListParcours.add(listParcoursScroll, c);
        c.gridy++;
        c.fill = GridBagConstraints.NONE;
        panelListParcours.add(btnSearchPar, c);
    }

    private void initPanelSearchParcours(){
        panelSearchParcours = new JPanel();
        panelSearchParcours.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        searchStartCityLbl = new JLabel("Ville de départ: ");
        searchEndCityLbl = new JLabel("Ville d'arrivée: ");
        searchStartLongLatLbl = new JLabel("Longitude, Latitude");
        searchStartLongLatLbl.setFont(nonBold);
        searchEndLongLatLbl = new JLabel("Longitude, Latitude");
        searchEndLongLatLbl.setFont(nonBold);
        searchLaunchBtn = new JButton("Lancer la recherche");
        searchBackBtn = new JButton("Retour");

        searchLaunchBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                search();
            }
        });

        searchBackBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelListParcours);
            }
        });

        searchResultPanel = new JPanel();
        searchResultPanel.setLayout(new GridBagLayout());
        searchResultPane = new JScrollPane(searchResultPanel);
        searchResultPane.setPreferredSize(new Dimension(10, 200));

        searchStartCityTxt = new JTextField();
        searchEndCityTxt = new JTextField();
        searchStartLongTxt = new JTextField();
        searchStartLatTxt = new JTextField();
        searchEndLongTxt = new JTextField();
        searchEndLatTxt = new JTextField();


        panelSearchParcours.add(searchStartCityLbl, c);
        c.gridy++;
        panelSearchParcours.add(searchStartLongLatLbl, c);
        c.gridy++;
        panelSearchParcours.add(searchEndCityLbl, c);
        c.gridy++;
        panelSearchParcours.add(searchEndLongLatLbl, c);

        c.gridwidth = 3;
        c.fill = 0;
        c.gridy++;
        panelSearchParcours.add(searchLaunchBtn, c);
        c.fill = 2;
        c.gridy++;
        panelSearchParcours.add(searchResultPane, c);
        c.fill = 0;
        c.gridy++;
        panelSearchParcours.add(searchBackBtn, c);

        c.fill = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1;
        c.gridx = 1;
        panelSearchParcours.add(searchStartCityTxt, c);
        c.gridy = 2;
        panelSearchParcours.add(searchEndCityTxt, c);
        c.gridy = 1;
        c.gridwidth = 1;
        panelSearchParcours.add(searchStartLongTxt, c);
        c.gridx = 2;
        panelSearchParcours.add(searchStartLatTxt, c);
        c.gridy = 3;
        panelSearchParcours.add(searchEndLatTxt, c);
        c.gridx = 1;
        panelSearchParcours.add(searchEndLongTxt, c);

    }

    private void initPanelDetailParcours(){
        panelDetailParcours = new JPanel();
        panelDetailParcours.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        parDetailStartLbl = new JLabel("Départ: Saint-Pied-Sur-Glane (2.34, 2.23)");
        parDetailEndLbl = new JLabel("Arrivée: Saint-Martin-De-Ses-Morts (4.35, 23)");
        parDetailDateLbl = new JLabel("Départ le 12 juin 2019 à 20h40");
        parDetailDateLbl.setFont(nonBold);
        parDetailTronconsLbl = new JLabel("Tronçons prévus");
        parDetailTronconsLbl.setHorizontalAlignment(JLabel.CENTER);
        parDetailPrixLbl = new JLabel("Prix estimmé: Nonante-neuf dollars");
        parDetailPrixLbl.setHorizontalAlignment(JLabel.CENTER);

        parDetailBackBtn = new JButton("Retour");
        parDetailBackBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                backToList();
            }
        });

        parDetailSubBtn = new JButton("M'inscrire au trajet");
        parDetailSubBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                submitPar();
            }
        });

        parTronconListPanel = new JPanel();
        parTronconListPanel.setLayout(new GridBagLayout());
        parTronconListScroll = new JScrollPane(parTronconListPanel);
        parTronconListScroll.setPreferredSize(new Dimension(10, 200));

        panelDetailParcours.add(parDetailStartLbl, c);
        c.gridy++;
        panelDetailParcours.add(parDetailEndLbl, c);
        c.gridy++;
        panelDetailParcours.add(parDetailDateLbl, c);
        c.gridy++;
        panelDetailParcours.add(parDetailTronconsLbl, c);
        c.gridy++;
        panelDetailParcours.add(parTronconListScroll, c);
        c.gridy++;
        panelDetailParcours.add(parDetailPrixLbl, c);
        c.gridy++;
        c.fill = 0;
        panelDetailParcours.add(parDetailSubBtn, c);
        c.gridy++;
        panelDetailParcours.add(parDetailBackBtn, c);
    }
    
    private void initPanelTroncon(){
        panelTroncon = new JPanel();
        panelTroncon.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        tronconStartLbl = new JLabel("Départ: Grenoble (0.0 - 0.0)");
        tronconEndLbl = new JLabel("Arrivée: Paris (0.0) - (0.0)");
        tronconDistLbl = new JLabel("Distance: 0 Km");
        tronconDurationLbl = new JLabel("Départ prévu à 8:30, durée: 0:45");
        tronconAttMaxLbl = new JLabel("Attente max au départ: pas d'attente");
        tronconVehicLbl = new JLabel("Peugeot Coccinelle - 3/4 places remplies");
        tronconDriverLbl = new JLabel("Conducteur: Jean Marc");
        tronconStateLbl = new JLabel("Pas encore parti");
        tronconPriceLbl = new JLabel("Prix estimmé: 0€");
        tronconStateLbl.setHorizontalAlignment(JLabel.CENTER);  
        
        tronconDistLbl.setFont(nonBold);
        tronconDurationLbl.setFont(nonBold);
        tronconAttMaxLbl.setFont(nonBold);
        tronconVehicLbl.setFont(nonBold);
        tronconDriverLbl.setFont(nonBold);

        tronconNextBtn = new JButton("Troncon suivant");
        tronconPrevBtn = new JButton("Troncon précédent");
        tronconStepBtn = new JButton("Monter");
        tronconBackBtn = new JButton("Retour");

        tronconNextBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                nextTroncon();
            }
        });

        tronconPrevBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                prevTroncon();
            }
        });

        tronconStepBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                stepTroncon();
            }
        });

        tronconBackBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tronconCtrl.setTronconCourant(null);
                backToTraj();
            }
        });

        panelTroncon.add(tronconPrevBtn, c);
        c.gridx = 1;
        panelTroncon.add(tronconNextBtn, c);
        c.gridx = 0;
        c.weightx = 0;

        c.gridwidth = 2;
        c.gridy++;
        panelTroncon.add(tronconStartLbl, c);
        c.gridy++;
        panelTroncon.add(tronconEndLbl, c);
        c.gridy++;
        panelTroncon.add(tronconDistLbl, c);
        c.gridy++;
        panelTroncon.add(tronconDurationLbl, c);
        c.gridy++;
        panelTroncon.add(tronconAttMaxLbl, c);
        c.gridy++;
        panelTroncon.add(tronconVehicLbl, c);
        c.gridy++;
        panelTroncon.add(tronconDriverLbl, c);
        c.gridy++;
        panelTroncon.add(tronconPriceLbl, c);


        c.gridy++;
        panelTroncon.add(tronconStateLbl, c);

        c.fill = GridBagConstraints.NONE;
        c.gridy++;
        c.weightx = 1;
        panelTroncon.add(tronconStepBtn, c);
        
        c.gridx = 0;
        c.gridy++;
        c.weightx = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        panelTroncon.add(tronconBackBtn, c);
    }

    private void genParcoursLines(){
        listParcoursPanel.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1;

        try{
            parcCtrl.setParcoursUser(userCtrl.getUtilisateur());
        }
        catch(BDDException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        ArrayList<Parcours> parcours = parcCtrl.getParcoursUser();
        for(int i = 0; i < parcours.size(); i++){
            c.gridy = i;
            listParcoursPanel.add(genParcoursLine(parcours.get(i)), c);
        }
        listParcoursPanel.revalidate();
        listParcoursPanel.repaint();
    }

    private void genSearchResultLines(){
        searchResultPanel.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1;

        ArrayList<Parcours> parcours = parcCtrl.getSearchRes();
        
        for(int i = 0; i < parcours.size(); i++){
            c.gridy = i;
            searchResultPanel.add(genParcoursLine(parcours.get(i)), c);
        }
        searchResultPanel.revalidate();
        searchResultPanel.repaint();
    }

    private JPanel genParcoursLine(Parcours par) {
        JPanel res = new JPanel();
        res.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;

        JLabel lblDest = new JLabel(par.getVilleDep() + " - " + par.getVilleArr());
                
        JLabel lblConduc1 = new JLabel(par.getTroncon(0).getUtilisateur().getNom() + " "
                                       + par.getTroncon(0).getUtilisateur().getPnom() + " - "
                                       + par.getTroncon(0).getVehicule().getMarque() + " " 
                                       + par.getTroncon(0).getVehicule().getModele());
        lblConduc1.setFont(nonBold);
        
        res.add(lblDest, c);
        c.gridy++;
        res.add(lblConduc1, c);

        if(par.aChangement()){
            Changement cgt = par.getChangement();
            JLabel lblCgt = new JLabel("Changement entre " + cgt.first.getVilleArr() + " et " + cgt.second.getVilleDep());
            lblCgt.setFont(nonBold);
            JLabel lblConduc2 = new JLabel(par.getTroncon(par.getSize()).getUtilisateur().getNom() + " "
                                           + par.getTroncon(par.getSize()).getUtilisateur().getPnom() + " - "
                                           + par.getTroncon(par.getSize()).getVehicule().getMarque() + " " 
                                           + par.getTroncon(par.getSize()).getVehicule().getModele());
            lblConduc2.setFont(nonBold);

            c.gridy++;
            res.add(lblCgt, c);
            res.add(lblConduc2, c);
        }

        JButton btnDetails = new JButton("Détails");
        btnDetails.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                showDetails(par);
            }
        });

        c.fill = GridBagConstraints.NONE;
        c.gridy++;
        res.add(btnDetails, c);

        return res;
    }

    private void genTronconLines(){
        if(parcCtrl.getParcoursCourant() == null){
            return;
        }
        parTronconListPanel.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1;

        ArrayList<Troncon> troncons = parcCtrl.getParcoursCourant().getTroncons();
        for(int i = 0; i < troncons.size(); i++){
            c.gridy = i;
            parTronconListPanel.add(genTronconLine(troncons.get(i)), c);
        }
        parTronconListPanel.revalidate();
        parTronconListPanel.repaint();
    }

    private JPanel genTronconLine(Troncon tr) {
        JPanel res = new JPanel();
        res.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;

        JLabel lblDest = new JLabel(tr.getVilleDep() + " - " + tr.getVilleArr());
        JLabel lblDuration = new JLabel("Durée estimée: " + new SimpleDateFormat("HH:mm").format(tr.getDureeEst()));
        lblDuration.setFont(nonBold);

        JLabel lblConducteur = new JLabel("Conducteur: " + tr.getTrajet().getUtilisateur().getPnom() + " " + tr.getTrajet().getUtilisateur().getNom());
        JLabel lblPassagers = new JLabel(tr.getPassagers().size() + "/" +  tr.getTrajet().getNbPlaces() + " Passagers");
        lblPassagers.setFont(nonBold);
        JButton btnDetails = new JButton("Détails du tronçon");
        btnDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showDetails(tr);
            }
        });

        res.add(lblDest, c);
        c.gridy = 1;
        res.add(lblDuration, c);
        c.gridy = 2;
        res.add(lblConducteur, c);
        c.gridy = 3;
        res.add(lblPassagers, c);
        c.gridy = 4;
        c.fill = GridBagConstraints.NONE;
        res.add(btnDetails, c);
        return res;
    }

    private void fillParcoursList(){
        if(userCtrl != null && userCtrl.getUtilisateur() != null){
            listParcoursLbl.setText("Parcours de " + userCtrl.getUtilisateur().getPnom() + " " + userCtrl.getUtilisateur().getNom());
            genParcoursLines();
        }
    }

    private void fillDetailsParcours(){
        Parcours par = parcCtrl.getParcoursCourant();
        if(par != null){
            parDetailStartLbl.setText("Départ: " + par.getVilleDep() + " (" + par.getLongDep() + "; " + par.getLatDep() + ")");
            parDetailEndLbl.setText("Arrivée: " + par.getVilleArr() + " (" + par.getLongArr() + "; " + par.getLatArr() + ")");
            parDetailDateLbl.setText("Départ le " + new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(par.getDateDep()));
            genTronconLines();
            parDetailPrixLbl.setText("Prix estimmé: " + par.getPrix() + "€");
            if(par.getUtilisateur() == null){
                parDetailSubBtn.setVisible(true);
            }
            else{
                parDetailSubBtn.setVisible(false);
            }
        }
        else{
            showPanel(panelListParcours);
        }
    }

    private void fillDetailsTroncon(){
        Troncon tr = tronconCtrl.getTronconCourant();
        if(tr != null){
            tronconStartLbl.setText("Départ: " + tr.getVilleDep() + " (" + tr.getLongDep() + "; " + tr.getLatDep() + ")");
            tronconEndLbl.setText("Arrivée: " + tr.getVilleArr() + " (" + tr.getLongArr() + "; " + tr.getLatArr() + ")");
            tronconDistLbl.setText("Distance: " + tr.getDistance() + " Km");
            tronconAttMaxLbl.setText("Attente max au départ: " + (tr.getAttente() == -1 ? "pas d'attente" : new SimpleDateFormat("HH:mm").format(tr.getAttente())));
            tronconPriceLbl.setText("Prix du tronçon: " + tr.PrixTroncon());

            String etat;
            tronconStepBtn.setEnabled(true);
            switch(tr.getEtat()) {
                case prevu:
                    etat = "Tronçon prévu";
                    break;
                case parti:
                    etat = "Tronçon en cours";
                    break;
                case arrive:
                    etat = "Tronçon terminé";
                    break;
                default:
                    etat = "Tronçon annulé";
            }
            tronconStateLbl.setText(etat);

            ArrayList<Troncon> troncons = parcCtrl.getParcoursCourant().getTroncons();
            tronconPrevBtn.setEnabled(!troncons.get(0).equals(tr));
            tronconNextBtn.setEnabled(!troncons.get(troncons.size() - 1).equals(tr));

            tronconDurationLbl.setText("Départ prévu le " + new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(tr.getDateDep()));

            tronconVehicLbl.setText(tr.getTrajet().getVehicule().getImmatriculation() + " - " + tr.getPassagers().size() + "/" + tr.getTrajet().getNbPlaces() + " places occupées");

            boolean found = false;
            for(Utilisateur u : tr.getPassagers()){
                if(userCtrl.getUtilisateur().getEmail().equals(u.getEmail())){
                    found = true;
                }
            }

            tronconStepBtn.setEnabled(true);
            if(found){
                tronconStepBtn.setVisible(true);
                switch(tr.getSuivi(userCtrl.getUtilisateur().getEmail())){
                    case pasMonte:
                        tronconStepBtn.setText("Monter");
                        break;
                    case Descendu:
                        tronconStepBtn.setEnabled(false);
                    default:
                        tronconStepBtn.setText("Descendre");
                        break;
                }
                if(tr.getEtat().equals(Etat.annule)){
                    tronconStepBtn.setEnabled(false);
                }
            }
            else{
                tronconStepBtn.setVisible(false);
            }
        }
    }

    private void showDetails(Parcours p){
        parcCtrl.setParcoursCourant(p);
        showPanel(panelDetailParcours);
    }

    private void showDetails(Troncon tr){
        tronconCtrl.setTronconCourant(tr);
        showPanel(panelTroncon);
    }

    private void showPanel(JPanel panel){
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;

        removeAll();

        if(panel.equals(panelListParcours)){
            fillParcoursList();
        }else if(panel.equals(panelDetailParcours)){
            fillDetailsParcours();
        }
        else if(panel.equals(panelTroncon)){
            fillDetailsTroncon();
        }


        add(panel, c);

        revalidate();
        repaint();
    }

    public void showHome(){
        showPanel(panelListParcours);
    }

    public void setUserCtrl(ControleurCompte ctrl){
        userCtrl = ctrl;
    }

    public void submitPar(){
        try{
            if(!parcCtrl.inscriptionParcours(parcCtrl.getParcoursCourant(), userCtrl.getUtilisateur())){
                JOptionPane.showMessageDialog(null, "Solde insuffisant pour vous inscrire");
            }
            showPanel(panelSearchParcours);
        }
        catch(BDDException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void nextTroncon(){
        boolean found = false;
        int i;
        int idTraj = tronconCtrl.getTronconCourant().getTrajet().getIdTrajet();
        int idTron = tronconCtrl.getTronconCourant().getIdTroncon();
        for(i = 0; i < parcCtrl.getParcoursCourant().getSize() && !found;i++){
            found = idTraj == (parcCtrl.getParcoursCourant().getTroncon(i).getTrajet().getIdTrajet()) &&
                    idTron == (parcCtrl.getParcoursCourant().getTroncon(i).getIdTroncon());
        }
        if(i < parcCtrl.getParcoursCourant().getSize()){
            tronconCtrl.setTronconCourant(parcCtrl.getParcoursCourant().getTroncon(i));
        }
        fillDetailsTroncon();
    }

    public void prevTroncon(){
        boolean found = false;
        int i;
        int idTraj = tronconCtrl.getTronconCourant().getTrajet().getIdTrajet();
        int idTron = tronconCtrl.getTronconCourant().getIdTroncon();
        for(i = 0; i < parcCtrl.getParcoursCourant().getSize() && !found; i++){
            found = idTraj == (parcCtrl.getParcoursCourant().getTroncon(i).getTrajet().getIdTrajet()) &&
                    idTron == (parcCtrl.getParcoursCourant().getTroncon(i).getIdTroncon());
        }
        if(i != 0){
            tronconCtrl.setTronconCourant(parcCtrl.getParcoursCourant().getTroncon(i - 2));
        }
        fillDetailsTroncon();
    }

    public void stepTroncon(){
        if(tronconCtrl.updateSuivi(userCtrl.getUtilisateur(), parcCtrl.getParcoursCourant().getIdParcours())){
            fillDetailsTroncon();
        }
        else{
            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du passage à l'état suivant");
        }
    }

    public void backToList(){
        if(parcCtrl.getParcoursCourant().getUtilisateur() == null){
            parcCtrl.setParcoursCourant(null);
            showPanel(panelSearchParcours);
        }
        else{
            parcCtrl.setParcoursCourant(null);
            showPanel(panelListParcours);
        }
    }

    public void backToTraj(){
        tronconCtrl.setTronconCourant(null);
        showPanel(panelDetailParcours);
    }

    public void search(){
        String msg = null;

        if(searchStartCityTxt.getText().equals("")){
            msg = "Veuillez remplir la ville de départ";
        }
        else if(searchEndCityTxt.getText().equals("")){
            try{
            	System.out.println("ici");
                parcCtrl.RechercherParcours(searchStartCityTxt.getText(), userCtrl.getUtilisateur().getEmail());
                genSearchResultLines();
            }
            catch(BDDException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        else{
        	
        	try{
        		System.out.println("la");
                parcCtrl.RechercherParcours(searchStartCityTxt.getText(), searchEndCityTxt.getText(), userCtrl.getUtilisateur().getEmail());
                genSearchResultLines();
            }
            catch(BDDException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        	
            /*try{
                longS = Float.parseFloat(searchStartLongTxt.getText());
                if(longS > 180 && longS < -180){
                    throw new Exception();
                }

                longE = Float.parseFloat(searchEndLongTxt.getText());
                if(longE > 180 && longE < -180){
                    throw new Exception();
                }
            }
            catch(Exception e){
                msg = "Veuillez saisir une longitude valide";
            }

            try{
                latS = Float.parseFloat(searchStartLatTxt.getText());
                if(latS > 90 && latS < -90){
                    throw new Exception();
                }

                latE = Float.parseFloat(searchEndLatTxt.getText());
                if(latE > 90 && latE < -90){
                    throw new Exception();
                }
            }
            catch(Exception e){
                msg = "Veuillez saisir une latitude valide";
            }*/
        }
        if(msg != null){
            JOptionPane.showMessageDialog(null, msg);
        }
    }
}