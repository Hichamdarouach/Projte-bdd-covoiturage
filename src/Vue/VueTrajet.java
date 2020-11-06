package Vue;

import javax.swing.*;

import BDD.BDDException;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.awt.GridBagConstraints;

import Controleur.ControleurCompte;
import Controleur.ControleurTrajet;
import Controleur.ControleurTroncon;
import Modele.Energie;
import Modele.Etat;
import Modele.Trajet;
import Modele.Troncon;
import Modele.TypeEnergie;
import Modele.Utilisateur;
import Modele.Vehicule;
import net.sourceforge.jdatepicker.impl.*;

public class VueTrajet extends JPanel {
    private Font f;

    private ArrayList<Troncon> tronconsCrees;
    private ControleurTrajet trajCtrl;
    private ControleurTroncon tronconCtrl;
    private ControleurCompte userCtrl;

    private JPanel panelTrajets;
    private JPanel panelTrajet;
    private JPanel panelTroncon;
    private JPanel panelTrajetCreate;
    private JPanel panelTronconCreate;

    // Liste des trajets
    private JLabel trajetsLbl;
    private JScrollPane trajetsScroll;
    private JPanel trajetsListPanel;
    private JButton trajetsCreateBtn;

    // Détails d'un trajet
    private JLabel trajetDestLbl;
    private JLabel trajetDateLbl;
    private JLabel trajetCarLbl;
    private JLabel trajetCarDataLbl;
    private JLabel trajetRoomLbl;
    private JLabel trajetRoomDataLbl;
    private JLabel trajetTronconsLbl;
    private JScrollPane trajetScroll;
    private JPanel trajetTronconPanel;
    private JButton trajetCancelBtn;

    // Création d'un trajet
    private JLabel createInfoLbl;
    private JLabel createDateLbl;
    private JLabel createTimeLbl;
    private JLabel createCarLbl;
    private JLabel createRoomLbl;
    private JLabel createTronconLbl;

    private JDatePickerImpl createDatePick;
    private JSpinner createTimeSpin;
    private JSpinner createRoomSpin;
    private JComboBox<Vehicule> createCarCbx;

    private JScrollPane createTrajetScroll;
    private JPanel createTrajetTronconsPanel;

    private JButton addTronconBtn;
    private JButton validateTrajBtn;
    private JButton cancelTrajBtn;

    // Création d'un tronçon
    private JLabel createEndCityLbl;
    private JLabel createStartCityLbl;
    private JLabel createLongLatStartLbl;
    private JLabel createLongLatEndLbl;
    private JLabel createDurationLbl;
    private JLabel createWaitLbl;
    
    private JTextField createStartCityTxt;
    private JTextField createEndCityTxt;
    private JTextField createLongStartTxt;
    private JTextField createLatStartTxt;
    private JTextField createLongEndTxt;
    private JTextField createLatEndTxt;
    private JSpinner estDurationSpin;
    private JSpinner createWaitSpin;
    private JCheckBox waitDurationCb;

    private JButton cancelTronconCreateBtn;
    private JButton validateTronconBtn;

    // Détails d'un tronçon
    private JLabel tronconStartLbl;
    private JLabel tronconEndLbl;
    private JLabel tronconDistLbl;
    private JLabel tronconDurationLbl;
    private JLabel tronconAttMaxLbl;
    private JLabel tronconVehiculeLbl;
    private JLabel tronconStateLbl;
    private JLabel tronconUserLbl;

    private JScrollPane tronconUserScroll;
    private JPanel tronconUserPanel;

    private JButton tronconPrevBtn;
    private JButton tronconNextBtn;
    private JButton tronconCancelBtn;
    private JButton tronconStepBtn;
    private JButton tronconBackBtn;

    public VueTrajet() {
        trajCtrl = new ControleurTrajet();
        tronconCtrl = new ControleurTroncon();
        setLayout(new GridBagLayout());

        initPanelTrajets();
        initPanelTrajet();
        initPanelTrajetCreate();
        initPanelTronconCreate();
        initPanelTroncon();

        showPanel(panelTroncon);
    }

    private void initPanelTrajets() {
        panelTrajets = new JPanel();
        panelTrajets.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        trajetsLbl = new JLabel("Trajets de Zblff ojifjo");
        trajetsLbl.setHorizontalAlignment(JLabel.CENTER);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;    
        c.weightx = 1;
        panelTrajets.add(trajetsLbl, c);

        trajetsListPanel = new JPanel();
        trajetsScroll = new JScrollPane(trajetsListPanel);
        trajetsScroll.setPreferredSize(new Dimension(10, 200));
        trajetsListPanel.setLayout(new GridBagLayout());

        c.gridy = 1;
        panelTrajets.add(trajetsScroll, c);

        trajetsCreateBtn = new JButton("Créer un nouveau trajet");
        trajetsCreateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                tronconsCrees = new ArrayList<Troncon>();
                showPanel(panelTrajetCreate);
            }
        });

        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        panelTrajets.add(trajetsCreateBtn, c);
    }

    private void initPanelTrajet(){
        panelTrajet = new JPanel();
        panelTrajet.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1;
        
        trajetDestLbl = new JLabel("");
        trajetDestLbl.setHorizontalAlignment(JLabel.CENTER);
        
        panelTrajet.add(trajetDestLbl, c);

        trajetDateLbl = new JLabel("");
        Font f = trajetDateLbl.getFont();
        trajetDateLbl.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        trajetDateLbl.setHorizontalAlignment(JLabel.CENTER);

        c.gridy = 1;
        panelTrajet.add(trajetDateLbl, c);

        trajetCarLbl = new JLabel("Voiture: ");
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0;
        panelTrajet.add(trajetCarLbl, c);

        trajetCarDataLbl = new JLabel("");
        trajetCarDataLbl.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        c.gridx = 1;
        c.weightx = 1;
        panelTrajet.add(trajetCarDataLbl, c);

        trajetRoomLbl = new JLabel("Places proposées: ");
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0;
        panelTrajet.add(trajetRoomLbl, c);

        trajetRoomDataLbl = new JLabel("");
        trajetRoomDataLbl.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        c.gridx = 1;
        c.weightx = 1;
        panelTrajet.add(trajetRoomDataLbl, c);

        trajetTronconsLbl = new JLabel("Tronçons composant le trajet");
        trajetTronconsLbl.setHorizontalAlignment(JLabel.CENTER);
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridx = 0;
        panelTrajet.add(trajetTronconsLbl, c);

        trajetTronconPanel = new JPanel();
        trajetScroll = new JScrollPane(trajetTronconPanel);
        trajetScroll.setPreferredSize(new Dimension(10, 200));
        trajetTronconPanel.setLayout(new GridBagLayout());

        c.gridy = 5;
        panelTrajet.add(trajetScroll, c);

        trajetCancelBtn = new JButton("Retour à la liste des trajets");
        trajetCancelBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelTrajets);
            }
        });
        c.gridy = 6;
        c.fill = GridBagConstraints.NONE;
        panelTrajet.add(trajetCancelBtn, c);
    }

    private void initPanelTrajetCreate(){
        panelTrajetCreate = new JPanel();
        panelTrajetCreate.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;

        createInfoLbl = new JLabel("Informations générales");
        createInfoLbl.setHorizontalAlignment(JLabel.CENTER);
        panelTrajetCreate.add(createInfoLbl, c);

        createDateLbl = new JLabel("Date de départ: ");
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        panelTrajetCreate.add(createDateLbl, c);

        SqlDateModel model = new SqlDateModel();        
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        createDatePick = new JDatePickerImpl(datePanel);
        c.gridx = 1;
        c.weightx = 1;
        panelTrajetCreate.add(createDatePick, c);

        createTimeLbl = new JLabel("Heure de départ: ");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        panelTrajetCreate.add(createTimeLbl, c);

        createTimeSpin = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(createTimeSpin, "HH:mm");
        createTimeSpin.setEditor(timeEditor);
        createTimeSpin.setValue(new java.util.Date());
        c.gridx = 1;
        c.weightx = 1;
        panelTrajetCreate.add(createTimeSpin, c);

        createCarLbl = new JLabel("Véhicule utilisé: ");
        c.gridx = 0;
        c.weightx = 0;
        c.gridy = 3;
        panelTrajetCreate.add(createCarLbl, c);

        Vehicule[] vehic = new Vehicule[]{new Vehicule("AMA", "BMW", "Vouature", 1.0f, 5, new Energie(TypeEnergie.Essence), null)};
        createCarCbx = new JComboBox<Vehicule>(vehic);
        c.gridx = 1;
        c.weightx = 1;
        panelTrajetCreate.add(createCarCbx, c);

        createRoomLbl = new JLabel("Nombre de places disponibles:");
        c.gridx = 0;
        c.weightx = 0;
        c.gridy = 4;
        panelTrajetCreate.add(createRoomLbl, c);

        createRoomSpin = new JSpinner();
        updateRoomSpinner();
        createCarCbx.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                updateRoomSpinner();
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelTrajetCreate.add(createRoomSpin, c);

        createTronconLbl = new JLabel("Tronçons constituant le trajet");
        createTronconLbl.setHorizontalAlignment(JLabel.CENTER);
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 5;
        panelTrajetCreate.add(createTronconLbl, c);

        createTrajetTronconsPanel = new JPanel();
        createTrajetScroll = new JScrollPane(createTrajetTronconsPanel);
        createTrajetScroll.setPreferredSize(new Dimension(10, 80));
        createTrajetTronconsPanel.setLayout(new GridBagLayout());

        c.gridy = 6;
        panelTrajetCreate.add(createTrajetScroll, c);

        addTronconBtn = new JButton("Ajouter un tronçon");
        addTronconBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelTronconCreate);
            }
        });
        c.gridy = 7;
        c.fill = GridBagConstraints.NONE;
        panelTrajetCreate.add(addTronconBtn, c);

        validateTrajBtn = new JButton("Valider le trajet");
        validateTrajBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                validateTraj();
            }
        });
        c.gridy = 8;
        panelTrajetCreate.add(validateTrajBtn, c);

        cancelTrajBtn = new JButton("Annuler");
        cancelTrajBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelTrajets);
            }
        });
        c.gridy = 9;
        panelTrajetCreate.add(cancelTrajBtn, c);
    }

    private void initPanelTronconCreate(){
        panelTronconCreate = new JPanel();
        panelTronconCreate.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        createStartCityLbl = new JLabel("Ville de départ: ");
        panelTronconCreate.add(createStartCityLbl, c);

        createStartCityTxt = new JTextField();
        createStartCityTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createStartCityTxt.getText().length() >= 20) {
                    // limit to 20
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        panelTronconCreate.add(createStartCityTxt, c);

        createLongLatStartLbl = new JLabel("Longitude, Latitude: ");
        f = createLongLatStartLbl.getFont();
        createLongLatStartLbl.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.gridwidth = 1;
        panelTronconCreate.add(createLongLatStartLbl, c);
        
        createLongStartTxt = new JTextField();
        createLongStartTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createLongStartTxt.getText().length() >= 20 || 
                   (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '.' &&
                    e.getKeyChar() != ',' && e.getKeyChar() !='+' && e.getKeyChar() != '-')) {
                    // limit to 20 and to float (roughly)
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        panelTronconCreate.add(createLongStartTxt, c);

        createLatStartTxt = new JTextField();
        createLatStartTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createLatStartTxt.getText().length() >= 20 || 
                   (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '.' &&
                    e.getKeyChar() != ',' && e.getKeyChar() !='+' && e.getKeyChar() != '-')) {
                    // limit to 20 and to float (roughly)
                    e.consume();
                }
            }
        });
        c.gridx = 2;
        panelTronconCreate.add(createLatStartTxt, c);

        createEndCityLbl = new JLabel("Ville d'arrivée: ");
        c.gridy = 2;
        c.gridx = 0;
        panelTronconCreate.add(createEndCityLbl, c);

        createEndCityTxt = new JTextField();
        createEndCityTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createEndCityTxt.getText().length() >= 20) {
                    // limit to 20
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        panelTronconCreate.add(createEndCityTxt, c);

        createLongLatEndLbl = new JLabel("Longitude, Latitude: ");
        createLongLatEndLbl.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.gridwidth = 1;
        panelTronconCreate.add(createLongLatEndLbl, c);
        
        createLongEndTxt = new JTextField();
        createLongEndTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createLongEndTxt.getText().length() >= 20 || 
                   (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '.' &&
                    e.getKeyChar() != ',' && e.getKeyChar() !='+' && e.getKeyChar() != '-')) {
                    // limit to 20 and to float (roughly)
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelTronconCreate.add(createLongEndTxt, c);

        createLatEndTxt = new JTextField();
        createLatEndTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createLatEndTxt.getText().length() >= 20 || 
                   (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '.' &&
                    e.getKeyChar() != ',' && e.getKeyChar() !='+' && e.getKeyChar() != '-')) {
                    // limit to 20 and to float (roughly)
                    e.consume();
                }
            }
        });
        c.gridx = 2;
        panelTronconCreate.add(createLatEndTxt, c);

        createDurationLbl = new JLabel("Durée estimée du tronçon:");
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0;
        panelTronconCreate.add(createDurationLbl, c);

        estDurationSpin = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(estDurationSpin, "HH:mm");
        estDurationSpin.setEditor(timeEditor);
        c.gridx = 1;
        panelTronconCreate.add(estDurationSpin, c);

        createWaitLbl = new JLabel("Durée d'attente max");
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0;
        panelTronconCreate.add(createWaitLbl, c);
        
        createWaitSpin = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        c.gridx = 1;
        createWaitSpin.setEnabled(false);
        panelTronconCreate.add(createWaitSpin, c);

        waitDurationCb = new JCheckBox("Attendre");
        waitDurationCb.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(waitDurationCb.isSelected()){
                    createWaitSpin.setEnabled(true);
                }
                else{
                    createWaitSpin.setEnabled(false);
                }
            }
        });
        c.gridx = 2;
        panelTronconCreate.add(waitDurationCb, c);

        validateTronconBtn = new JButton("Valider le tronçon");
        validateTronconBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                validateTroncon();
            }
        });
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.NONE;
        panelTronconCreate.add(validateTronconBtn, c);

        cancelTronconCreateBtn = new JButton("Annuler");
        cancelTronconCreateBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showPanel(panelTrajetCreate);
            }
        });
        c.gridy = 7;
        panelTronconCreate.add(cancelTronconCreateBtn, c);

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
        tronconVehiculeLbl = new JLabel("Peugeot Coccinelle - 3/4 places remplies");
        tronconStateLbl = new JLabel("Pas encore parti");
        tronconStateLbl.setHorizontalAlignment(JLabel.CENTER);

        f = f.deriveFont(f.getStyle() & ~Font.BOLD);
        tronconDistLbl.setFont(f);
        tronconDurationLbl.setFont(f);
        tronconAttMaxLbl.setFont(f);
        tronconVehiculeLbl.setFont(f);

        tronconUserLbl = new JLabel("Passagers");
        tronconUserLbl.setHorizontalAlignment(JLabel.CENTER);

        tronconNextBtn = new JButton("Troncon suivant");
        tronconPrevBtn = new JButton("Troncon précédent");
        tronconCancelBtn = new JButton("Annuler le troncon");
        tronconStepBtn = new JButton("Partir");
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

        tronconCancelBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cancelTroncon();
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
                showPanel(panelTrajet);
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
        panelTroncon.add(tronconVehiculeLbl, c);
        c.gridy++;
        panelTroncon.add(tronconUserLbl, c);

        tronconUserPanel = new JPanel();
        tronconUserPanel.setLayout(new GridBagLayout());
        tronconUserScroll = new JScrollPane(tronconUserPanel);
        tronconUserScroll.setPreferredSize(new Dimension(10, 100));
        
        c.gridy++;
        panelTroncon.add(tronconUserScroll, c);
        c.gridy++;
        panelTroncon.add(tronconStateLbl, c);

        c.gridwidth = 1;
        c.gridy++;
        c.weightx = 1;
        panelTroncon.add(tronconCancelBtn, c);
        c.gridx = 1;
        panelTroncon.add(tronconStepBtn, c);
        
        c.gridx = 0;
        c.gridy++;
        c.weightx = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        panelTroncon.add(tronconBackBtn, c);
    }

    private void updateRoomSpinner(){
        Vehicule v = (Vehicule) createCarCbx.getSelectedItem();
        if(v != null){
            createRoomSpin.setValue(1);
            createRoomSpin.setModel(new SpinnerNumberModel(1, 1, v.getNbPlaces() - 1, 1));
            createRoomSpin.setEnabled(true);
        }
        else{
            createRoomSpin.setModel(new SpinnerNumberModel(0, 0, 0, 1));
            createRoomSpin.setEnabled(false);
        }
    }

    private void genTrajetLines(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        if(userCtrl.getUtilisateur() != null){
            try{
                trajCtrl.chercherTrajets(userCtrl.getUtilisateur());
            }
            catch(BDDException ex){
                JOptionPane.showMessageDialog(null, "Impossible de récupérer les trajets de l'utilisateur");
            }
        }
        ArrayList<Trajet> trajets = trajCtrl.getTrajets();
        for(int i = 0; i < trajets.size(); i++) {
            c.gridy = i;
            trajetsListPanel.add(genTrajetLine(trajets.get(i)), c);    
        }
    }

    private JPanel genTrajetLine(Trajet traj){
        JPanel res = new JPanel();
        res.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        JLabel lblStartCity = new JLabel(traj.getVilleDep() + " - " + traj.getVilleArr());
        JLabel lblDateDep = new JLabel("Départ prévu le " + new SimpleDateFormat("dd/MM/yyyy").format(traj.getDateDep()) + " à " + new SimpleDateFormat("HH:mm").format(traj.getHeureDep()));
        lblDateDep.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));

        JButton btnDetails = new JButton("Afficher les détails");
        btnDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showDetails(traj);
            }
        });

        res.add(lblStartCity, c);
        c.gridy = 1;
        res.add(lblDateDep, c);
        c.gridy = 2;
        res.add(btnDetails, c);

        return res;
    }

    private void genTronconLines(){
        trajetTronconPanel.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        if(trajCtrl.getTrajetCourant() != null){
            ArrayList<Troncon> troncons = trajCtrl.getTrajetCourant().getTroncons();
            for(int i = 0; i < troncons.size(); i++) {
                c.gridy = i;
                trajetTronconPanel.add(genTronconLine(troncons.get(i)), c);    
            }
        }
    }

    private JPanel genTronconLine(Troncon troncon){
        JPanel res = new JPanel();
        res.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        JLabel lblDest = new JLabel(troncon.getVilleDep() + " - " + troncon.getVilleArr());
        JLabel lblDuration = new JLabel("Durée estimée: " + new SimpleDateFormat("HH:mm").format(troncon.getDureeEst()));
        lblDuration.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));

        JLabel lblPassagers = new JLabel(troncon.getPassagers().size() + " Passagers");
        lblPassagers.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        JButton btnDetails = new JButton("Détails du tronçon");
        btnDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showDetails(troncon);
            }
        });

        res.add(lblDest, c);
        c.gridy = 1;
        res.add(lblDuration, c);
        c.gridy = 2;
        res.add(lblPassagers, c);
        c.gridy = 3;
        c.fill = GridBagConstraints.NONE;
        res.add(btnDetails, c);
        
        return res;
    }

    private void genTronconCreateLines(){
        createTrajetTronconsPanel.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        for(int i = 0; i < tronconsCrees.size(); i++) {
            c.gridy = i;
            createTrajetTronconsPanel.add(genTronconCreateLine(tronconsCrees.get(i)), c);
        }
    }

    private JPanel genTronconCreateLine(Troncon troncon){
        JPanel res = new JPanel();
        res.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;

        JLabel lblDest = new JLabel(troncon.getVilleDep() + " - " + troncon.getVilleArr());
        JLabel lblDuration = new JLabel("Durée estimée: " + troncon.getDureeEst().getHours() + ":" + troncon.getDureeEst().getMinutes());
        lblDuration.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));

        res.add(lblDest, c);
        c.gridy = 1;
        res.add(lblDuration, c);
        
        return res;
    }

    private void genUserLines(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        if(tronconCtrl.getTronconCourant() != null){
            ArrayList<Utilisateur> users = tronconCtrl.getTronconCourant().getPassagers();
            for(int i = 0; i < users.size(); i++) {
                c.gridy = i;
                tronconUserPanel.add(genUserLine(users.get(i)), c);    
            }
        }
    }

    private JPanel genUserLine(Utilisateur user){
        JPanel res = new JPanel();
        res.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 0;

        JLabel lblNom = new JLabel(user.getPnom() + " " + user.getNom());
        JLabel lblMail = new JLabel(user.getEmail());
        lblNom.setHorizontalAlignment(JLabel.CENTER);
        lblMail.setHorizontalAlignment(JLabel.CENTER);
        lblMail.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));

        res.add(lblNom, c);
        c.gridy++;
        res.add(lblMail, c);

        return res;
    }

    private void showDetails(Trajet traj){
        if(traj != null){
            trajCtrl.setTrajetCourant(traj);
            showPanel(panelTrajet);
        }
    }

    private void showDetails(Troncon troncon){
        if(troncon != null){
            tronconCtrl.setTronconCourant(troncon);
            showPanel(panelTroncon);
        }
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

        Component prev = null;
        if(getComponents().length > 0){
            prev = getComponent(0);
        }
        removeAll();

        if(panel.equals(panelTrajets)){
            fillPanelTrajets();
        }
        else if(panel.equals(panelTrajet)){
            fillPanelTrajet();
        }
        else if(panel.equals(panelTrajetCreate)){
            if(prev != panelTronconCreate){
                tronconsCrees = new ArrayList<Troncon>();
                fillPanelTrajetCreate();
            }
            else{
                genTronconCreateLines();
            }
        }
        else if(panel.equals(panelTroncon)){
            fillPanelTroncon();
        }
        else if(panel.equals(panelTronconCreate)){
            if(tronconsCrees.size() > 0){
                Troncon tr = tronconsCrees.get(tronconsCrees.size() - 1);
                createStartCityTxt.setText(tr.getVilleArr());
                createLatStartTxt.setText(tr.getLatArr() + "");
                createLongStartTxt.setText(tr.getLongArr() + "");
                createLatStartTxt.setEnabled(false);
                createLongStartTxt.setEnabled(false);
                createStartCityTxt.setEnabled(false);
            }
            else{
                createStartCityTxt.setText("");
                createLatStartTxt.setText("");
                createLongStartTxt.setText("");
                createLatStartTxt.setEnabled(true);
                createLongStartTxt.setEnabled(true);
                createStartCityTxt.setEnabled(true);

            }
            createEndCityTxt.setText("");
            createLongEndTxt.setText("");
            createLatEndTxt.setText("");
            estDurationSpin.setValue(new Date(0, 0, 0, 00, 30));
            createWaitSpin.setValue(0);
        }

        add(panel, c);
        revalidate();
        repaint();
    }

    private void fillPanelTrajets(){
        Utilisateur user = userCtrl.getUtilisateur();
        if(user != null){
            trajetsLbl.setText("Trajets de " + user.getPnom() + " " + user.getNom());
            genTrajetLines();
        }
    }

    private void fillPanelTrajet(){
        Trajet traj = trajCtrl.getTrajetCourant();
        if(traj != null){
            trajetDestLbl.setText(traj.getVilleDep() + " - " + traj.getVilleArr());
            trajetDateLbl.setText(new SimpleDateFormat("dd/MM/yyyy").format(traj.getDateDep()) + ", " + new SimpleDateFormat("HH:mm").format(traj.getHeureDep()));
            trajetCarDataLbl.setText(traj.getVehicule().getMarque() + " " + traj.getVehicule().getModele());
            trajetRoomDataLbl.setText(traj.getNbPlaces() + " places proposées");
            genTronconLines();
        }
    }

    private void fillPanelTrajetCreate(){
        if(userCtrl.getUtilisateur() != null){
            createCarCbx.removeAllItems();
            genTronconCreateLines();

            for(Vehicule v : userCtrl.getUtilisateur().getVehicules()){
                createCarCbx.addItem(v);
            }
            updateRoomSpinner();
        }
    }

    private void fillPanelTroncon(){
        Troncon tr = tronconCtrl.getTronconCourant();
        if(tr != null){
            tronconStartLbl.setText("Départ: " + tr.getVilleDep() + " (" + tr.getLongDep() + "; " + tr.getLatDep() + ")");
            tronconEndLbl.setText("Arrivée: " + tr.getVilleArr() + " (" + tr.getLongArr() + "; " + tr.getLatArr() + ")");
            tronconDistLbl.setText("Distance: " + tr.getDistance() + " Km");
            tronconAttMaxLbl.setText("Attente max au départ: " + (tr.getAttente() == -1 ? "pas d'attente" : tr.getAttente()));
            
            String etat;
            tronconCancelBtn.setEnabled(false);
            tronconStepBtn.setEnabled(true);
            switch(tr.getEtat()) {
                case prevu:
                    tronconStepBtn.setText("Partir");
                    tronconCancelBtn.setEnabled(true);
                    etat = "Tronçon prévu";
                    break;
                case parti:
                    tronconStepBtn.setText("Arriver");
                    etat = "Tronçon en cours";
                    break;
                case arrive:
                    etat = "Tronçon terminé";
                    tronconStepBtn.setEnabled(false);
                    break;
                default:
                    etat = "Tronçon annulé";
                    tronconStepBtn.setEnabled(false);
            }
            tronconStateLbl.setText(etat);

            ArrayList<Troncon> troncons = tr.getTrajet().getTroncons();
            tronconPrevBtn.setEnabled(tr.getIdTroncon() != 0);
            tronconNextBtn.setEnabled(tr.getIdTroncon() != troncons.size() - 1);

            tronconDurationLbl.setText("Départ prévu le " + new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(tr.getDateDep()));

            tronconVehiculeLbl.setText(tr.getTrajet().getVehicule().getImmatriculation() + " - " + tr.getPassagers().size() + "/" + tr.getTrajet().getNbPlaces() + " places occupées");
            genUserLines();
        }
    }

    public void validateTroncon(){
        String msg = null;
        float longS = 0;
        float longE = 0;
        float latS = 0;
        float latE = 0;
        int wait = -1;
        Timestamp duration;

        if(createStartCityTxt.getText() == ""){
            msg = "Veuillez remplir la ville de départ";
        }
        else if(createEndCityTxt.getText() == ""){
            msg = "Veuillez remplir la ville d'arrivée";
        }
        else{
            try{
                longS = Float.parseFloat(createLongStartTxt.getText());
                if(longS > 180 && longS < -180){
                    throw new Exception();
                }

                longE = Float.parseFloat(createLongEndTxt.getText());
                if(longE > 180 && longE < -180){
                    throw new Exception();
                }
            }
            catch(Exception e){
                msg = "Veuillez saisir une longitude valide";
            }

            try{
                latS = Float.parseFloat(createLatStartTxt.getText());
                if(latS > 90 && latS < -90){
                    throw new Exception();
                }

                latE = Float.parseFloat(createLatEndTxt.getText());
                if(latE > 90 && latE < -90){
                    throw new Exception();
                }
            }
            catch(Exception e){
                msg = "Veuillez saisir une latitude valide";
            }
        }
        if(msg != null){
            JOptionPane.showMessageDialog(null, msg);
        }
        else{
            Date d = (Date)estDurationSpin.getValue();
            duration = new Timestamp(0, 0, 0, d.getHours(), d.getMinutes(), 0, 0);
            if(waitDurationCb.isSelected()){
                wait = ((Integer)createWaitSpin.getValue()).intValue();
            }
            tronconsCrees.add(new Troncon(-1, createStartCityTxt.getText(), longS, latS, 
                                          createEndCityTxt.getText(), longE, latE, duration, wait, null, null, Etat.prevu));
            showPanel(panelTrajetCreate);
        }
    }

    public void validateTraj(){
        String msg = null;
        if(createCarCbx.getSelectedItem() == null){
            msg = "Veuillez choisir un véhicule";
        }
        else if(createDatePick.getModel().getValue() == null){
            msg = "Veuillez choisir une date de départ";
        }
        else if(tronconsCrees.size() == 0){
            msg = "Impossible de créer un trajet sans tronçons";
        }
        if(msg == null){
            try{
                Timestamp time = new Timestamp(0, 0, 0, ((Date)createTimeSpin.getValue()).getHours(), ((Date)createTimeSpin.getValue()).getMinutes(), 0, 0);
                trajCtrl.nouveauTrajet((java.sql.Date)createDatePick.getModel().getValue(), time, ((Integer)createRoomSpin.getValue()).intValue(),
                              (Vehicule)createCarCbx.getSelectedItem(), userCtrl.getUtilisateur(), tronconsCrees);
                showPanel(panelTrajets);
            }
            catch(BDDException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        else{
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void stepTroncon(){
        tronconCtrl.etatSuivant();
        fillPanelTroncon();
    }

    public void cancelTroncon(){
        if(tronconCtrl.annulerTroncon()){
            fillPanelTroncon();
        }
        else{
            JOptionPane.showMessageDialog(null, "Impossible d'annuler le tronçon");
        }
    }

    public void nextTroncon(){
        tronconCtrl.nextTroncon();
        fillPanelTroncon();
    }

    public void prevTroncon(){
        tronconCtrl.prevTroncon();
        fillPanelTroncon();
    }

    public void showHome(){
        showPanel(panelTrajets);
    }

    public void setUserCtrl(ControleurCompte userCtrl){
        this.userCtrl = userCtrl;
    }
}