package Vue;

import javax.swing.*;

import BDD.BDDException;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Font;

import Controleur.ControleurCompte;
import Modele.Energie;
import Modele.TypeEnergie;
import Modele.Utilisateur;
import Modele.Vehicule;


public class VueCompte extends JPanel {

    private ControleurCompte userCtrl;

    // Login related elements
    private JTextField emailLoginField;
    private JPasswordField pwdLoginField;

    private JLabel emailLoginLbl;
    private JLabel pwdLoginLbl;

    private JButton loginBtn;
    private JButton createAccBtn;

    private JPanel panelLogin;

    // Account view related elements
    private JLabel accNameLbl;
    private JLabel accCityLbl;
    private JLabel accEmailLbl;
    private JLabel accVehicleListLbl;
    private JLabel accMoneyLbl;
    private JButton accVehicleAddBtn;

    private JScrollPane accVehiclePane;
    private JPanel accVehiclePanel;

    private JButton disconnectBtn;
    private JPanel panelAccount;
    private JButton moneyBtn;

    // Vehicle addition related elements
    private JButton vehicleCancelBtn;
    private JButton vehicleAddBtn;
    private JButton vehicleCreateBtn;

    private JPanel panelAddVehicle;
    private JLabel existingVehicleLbl;
    private JLabel newVehicleLbl;

    private JLabel immaExistLbl;
    private JLabel immaNewLbl;
    private JTextField immaExistTxt;
    private JTextField immaNewTxt;
    private JLabel brandLbl;
    private JTextField brandTxt;
    private JLabel modelLbl;
    private JTextField modelTxt;
    private JLabel roomLbl;
    private JSpinner roomSpin;
    private JLabel fiscLbl;
    private JSpinner fiscSpin;
    private JLabel energyLbl;
    private JComboBox<TypeEnergie> energyCbx;

    // Account creation related elements
    private JPanel panelCreateAcc;
    private JButton accCreateCancelBtn;
    private JButton accCreateBtn;

    private JLabel createEmailLbl;
    private JLabel createNameLbl;
    private JLabel createFNameLbl;
    private JLabel createCityLbl;
    private JLabel createPassLbl;
    private JLabel confirmPassLbl;

    private JTextField createEmailTxt;
    private JTextField createNameTxt;
    private JTextField createFNameTxt;
    private JTextField createCityTxt;
    private JPasswordField createPassTxt;
    private JPasswordField confirmPassTxt;

    // Crediting
    private JPanel panelCredit;
    private JLabel creditLbl;
    private JSpinner creditSpin;
    private JButton creditBtn;
    private JButton creditBackBtn;

    public VueCompte(){
        userCtrl = new ControleurCompte();
        setLayout(new GridBagLayout());

        initLoginPanel();
        initAccCreatePanel();
        initAccountPanel();
        initAddVehiclePanel();
        initCreditPanel();

        showPanel(panelLogin);
    }

    private void initLoginPanel(){
        panelLogin = new JPanel();
        panelLogin.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        emailLoginField = new JTextField();
        pwdLoginField = new JPasswordField();

        emailLoginLbl = new JLabel("email:");
        pwdLoginLbl = new JLabel("mot de passe:");
        emailLoginLbl.setHorizontalAlignment(JLabel.RIGHT);
        pwdLoginLbl.setHorizontalAlignment(JLabel.RIGHT);

        loginBtn = new JButton("Connexion");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                login();
            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        panelLogin.add(emailLoginLbl, c);

        c.gridx = 1;
        c.weightx = 1;
        panelLogin.add(emailLoginField, c);

        c.gridy = 1;
        panelLogin.add(pwdLoginField, c);

        c.gridx = 0;
        c.weightx = 0;
        panelLogin.add(pwdLoginLbl, c);

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        panelLogin.add(loginBtn, c);

        createAccBtn = new JButton("Créer un compte");
        createAccBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelCreateAcc);
            }
        });

        c.gridy = 3;
        panelLogin.add(createAccBtn, c);
    }

    private void initAccCreatePanel(){
        panelCreateAcc = new JPanel();
        panelCreateAcc.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        createEmailLbl = new JLabel("Email: ");
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridy = 0;
        c.ipady = 0;
        panelCreateAcc.add(createEmailLbl, c);

        createEmailTxt = new JTextField();
        createEmailTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createEmailTxt.getText().length() >= 40) {
                    // limit to 40
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelCreateAcc.add(createEmailTxt, c);

        createNameLbl = new JLabel("Nom: ");
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 0;
        panelCreateAcc.add(createNameLbl, c);

        createNameTxt = new JTextField();
        createNameTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createNameTxt.getText().length() >= 20) {
                    // limit to 20
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelCreateAcc.add(createNameTxt, c);

        createFNameLbl = new JLabel("Prénom: ");
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.ipady = 0;
        panelCreateAcc.add(createFNameLbl, c);

        createFNameTxt = new JTextField();
        createFNameTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createFNameTxt.getText().length() >= 20) {
                    // limit to 20
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelCreateAcc.add(createFNameTxt, c);

        createCityLbl = new JLabel("Ville de résidence: ");
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        c.ipady = 0;
        panelCreateAcc.add(createCityLbl, c);

        createCityTxt = new JTextField();
        createCityTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (createCityTxt.getText().length() >= 20) {
                    // limit to 20
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelCreateAcc.add(createCityTxt, c);

        createPassLbl = new JLabel("Mot de passe: ");
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        c.ipady = 0;
        panelCreateAcc.add(createPassLbl, c);

        createPassTxt = new JPasswordField();
        createPassTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (new String(createPassTxt.getPassword()).length() >= 20) {
                    // limit to 20
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelCreateAcc.add(createPassTxt, c);

        confirmPassLbl = new JLabel("Confirmer le MDP: ");
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        c.ipady = 0;
        panelCreateAcc.add(confirmPassLbl, c);

        confirmPassTxt = new JPasswordField();
        confirmPassTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (new String(confirmPassTxt.getPassword()).length() >= 20) {
                    // limit to 20
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelCreateAcc.add(confirmPassTxt, c);

        accCreateBtn = new JButton("Créer un compte");
        accCreateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                createAccount();
            }
        });

        c.gridy = 6;
        c.gridx = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        panelCreateAcc.add(accCreateBtn, c);

        accCreateCancelBtn = new JButton("Annuler");
        accCreateCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelLogin);
            }
        });

        c.gridy = 7;
        panelCreateAcc.add(accCreateCancelBtn, c);
    }

    private void initAccountPanel(){
        panelAccount = new JPanel();
        panelAccount.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        accNameLbl = new JLabel("François Polochon");
        Font f = accNameLbl.getFont();

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panelAccount.add(accNameLbl, c);

        accCityLbl = new JLabel("Grenoble");
        accCityLbl.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        c.gridy = 1;
        panelAccount.add(accCityLbl, c);

        accEmailLbl = new JLabel("email@info.fr");
        accCityLbl.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        c.gridy = 2;
        panelAccount.add(accEmailLbl, c);
        
        accVehicleListLbl = new JLabel("Véhicules");
        accVehicleListLbl.setHorizontalAlignment(JLabel.CENTER);
        c.gridy = 3;
        panelAccount.add(accVehicleListLbl, c);

        // Vehicle list
        accVehiclePanel = new JPanel();
        accVehiclePane = new JScrollPane(accVehiclePanel);
        accVehiclePane.setPreferredSize(new Dimension(10, 100));
        accVehiclePanel.setLayout(new GridBagLayout());

        c.gridy = 4;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        panelAccount.add(accVehiclePane, c);

        accVehicleAddBtn = new JButton("Ajouter un véhicule");
        accVehicleAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelAddVehicle);
            }
        });


        c.gridy = 5;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        panelAccount.add(accVehicleAddBtn, c);

        accMoneyLbl = new JLabel("Solde: 443€");
        c.gridy = 6;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panelAccount.add(accMoneyLbl, c);

        moneyBtn = new JButton("Créditer le compte");
        c.fill = 0;
        c.gridx = 1;
        panelAccount.add(moneyBtn, c);
        c.gridx = 0;
        moneyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelCredit);
            }
        });

        disconnectBtn = new JButton("Déconnexion");
        disconnectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                logout();
            }
        });
        c.gridy = 7;
        c.gridwidth = 2;
        panelAccount.add(disconnectBtn, c);
    }

    private void initAddVehiclePanel(){
        panelAddVehicle = new JPanel();
        panelAddVehicle.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        existingVehicleLbl = new JLabel("Véhicule existant");
        existingVehicleLbl.setHorizontalAlignment(JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.ipady = 20;
        c.fill = GridBagConstraints.HORIZONTAL;
        panelAddVehicle.add(existingVehicleLbl, c);

        immaExistLbl = new JLabel("Immatriculation: ");
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        c.ipady = 0;
        panelAddVehicle.add(immaExistLbl, c);

        immaExistTxt = new JTextField();
        immaExistTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (immaExistTxt.getText().length() >= 10 || !(Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyChar() == '-')) {
                    // limit to 10 alphanum characters
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelAddVehicle.add(immaExistTxt, c);

        vehicleAddBtn = new JButton("Ajouter");
        vehicleAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                addExistingVehicle();
            }
        });
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 2;
        c.weightx = 0;
        panelAddVehicle.add(vehicleAddBtn, c);

        newVehicleLbl = new JLabel("Créer un véhicule");
        newVehicleLbl.setHorizontalAlignment(JLabel.CENTER);
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        panelAddVehicle.add(newVehicleLbl, c);

        immaNewLbl = new JLabel("Immatriculation: ");
        c.gridy = 4;
        c.ipady = 0;
        c.gridwidth = 1;
        panelAddVehicle.add(immaNewLbl, c);

        immaNewTxt = new JTextField();
        immaNewTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (immaNewTxt.getText().length() >= 10 || !(Character.isLetterOrDigit(e.getKeyChar()) || e.getKeyChar() == '-')) {
                    // limit to 10 alphanum characters
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelAddVehicle.add(immaNewTxt, c);

        brandLbl = new JLabel("Marque:");
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0;
        panelAddVehicle.add(brandLbl, c);

        brandTxt = new JTextField();
        brandTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (brandTxt.getText().length() >= 20) {
                    // limit to 20 characters
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelAddVehicle.add(brandTxt, c);

        modelLbl = new JLabel("Modèle:");
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 0;
        panelAddVehicle.add(modelLbl, c);

        modelTxt = new JTextField();
        modelTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (modelTxt.getText().length() >= 20) {
                    // limit to 20 characters
                    e.consume();
                }
            }
        });
        c.gridx = 1;
        c.weightx = 1;
        panelAddVehicle.add(modelTxt, c);

        roomLbl = new JLabel("Places disponibles: ");
        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 0;
        panelAddVehicle.add(roomLbl, c);

        roomSpin = new JSpinner();
        roomSpin.setModel(new SpinnerNumberModel(4, 2, 10, 1));
        c.gridx = 1;
        c.fill = GridBagConstraints.NONE;
        panelAddVehicle.add(roomSpin, c);

        fiscLbl = new JLabel("Puissance fiscale: ");
        c.gridx = 0;
        c.gridy = 8;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panelAddVehicle.add(fiscLbl, c);

        fiscSpin = new JSpinner();
        fiscSpin.setModel(new SpinnerNumberModel(1.0, 0, 100, 0.1));
        c.gridx = 1;
        c.fill = GridBagConstraints.NONE;
        panelAddVehicle.add(fiscSpin, c);

        energyLbl = new JLabel("Type de carburant: ");
        c.gridx = 0;
        c.gridy = 9;
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panelAddVehicle.add(energyLbl, c);
        
        
        energyCbx = new JComboBox<TypeEnergie>(TypeEnergie.values());
        c.gridx = 1;
        c.fill = GridBagConstraints.NONE;
        panelAddVehicle.add(energyCbx, c);

        vehicleCancelBtn = new JButton("Annuler");
        vehicleCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelAccount);
            }
        });

        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 2;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        panelAddVehicle.add(vehicleCancelBtn, c);

        vehicleCreateBtn = new JButton("Créer");
        vehicleCreateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                createAndAddVehicle();
            }
        });

        c.gridy = 10;
        panelAddVehicle.add(vehicleCreateBtn, c);
    }

    private void initCreditPanel(){
        panelCredit = new JPanel();
        panelCredit.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 0;
        c.gridx = 0;
        c.gridy = 0;

        creditLbl = new JLabel("Montant: ");
        creditSpin = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 0.1));
        creditBtn = new JButton("Créditer");
        creditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                crediter();
            }
        });
        creditBackBtn = new JButton("Retour");
        creditBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPanel(panelAccount);
            }
        });

        panelCredit.add(creditLbl, c);
        c.gridx = 1;
        panelCredit.add(creditSpin, c);
        c.gridx = 2;
        panelCredit.add(creditBtn, c);

        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 1;
        panelCredit.add(creditBackBtn, c);
    }

    private void fillPanelAcc(){
        Utilisateur user = userCtrl.getUtilisateur();
        if(user == null){
            JOptionPane.showMessageDialog(null, "Aucun utilisateur connecté");
            showPanel(panelLogin);
        }
        else{
            accNameLbl.setText(user.getPnom() + " " + user.getNom());
            accEmailLbl.setText(user.getEmail());
            accCityLbl.setText(user.getVilleRes());
            accMoneyLbl.setText("Solde: " + user.getSolde() + "€");
            genVehicules();
        }
    }

    private void genVehicules(){
        try{
            if(!userCtrl.recupererVehicules()){
                JOptionPane.showMessageDialog(null, "La récupération des véhicules depuis la base a échoué");
            }
        }
        catch(BDDException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        ArrayList<Vehicule> vehicules = userCtrl.getUtilisateur().getVehicules();

        accVehiclePanel.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

        for(int i = 0; i < vehicules.size(); i++){
            c.gridy = i;
            accVehiclePanel.add(genVehicule(vehicules.get(i)), c);
        }

    }

    private JPanel genVehicule(Vehicule v){
        JPanel res = new JPanel();
        res.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        res.add(new JLabel(v.getMarque() + " " + v.getModele() + " " + v.getImmatriculation() + " - " + v.getNbPlaces() + " places"), c);
        JLabel energy = new JLabel(v.getEnergie().getCarburant() + " - Puissance fiscale: " + v.getPuisFisc());
        Font f = energy.getFont();
        energy.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));

        c.gridy = 1;
        res.add(energy, c);

        return res;
    }

    private void login(){
        if(emailLoginField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir votre email");
        }
        else if(pwdLoginField.getPassword().length == 0){
            JOptionPane.showMessageDialog(null, "Veuillez saisir votre mot de passe");
        }
        else{
            try{
                if(!userCtrl.connexion(emailLoginField.getText(), new String(pwdLoginField.getPassword()))){
                    JOptionPane.showMessageDialog(null, "Mauvais mot de passe!");
                }
                else{
                    showPanel(panelAccount);
                }
            }
            catch (BDDException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            catch (NoSuchAlgorithmException ex){
                JOptionPane.showMessageDialog(null, "Problème d'algorithme de chiffrement");
            }
        }
    }

    private void logout(){
        userCtrl.deconnexion();
        showPanel(panelLogin);
    }

    private void createAccount(){
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(createEmailTxt.getText());

        if(!matcher.matches()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir un email valide");
        }
        else if(createNameTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir votre nom");
        }
        else if(createFNameTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir votre prénom");
        }
        else if(createCityTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir votre ville de résidence");
        }
        else{
            String pass = new String(createPassTxt.getPassword());
            if(pass.equals(new String(confirmPassTxt.getPassword())) && pass.length() >= 4){
                
                try{
                    if(userCtrl.nouvelUtilisateur(createEmailTxt.getText(),
                                                    createNameTxt.getText(),
                                                    createFNameTxt.getText(),
                                                    createCityTxt.getText(),
                                                    pass)){
                        showPanel(panelAccount);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Il existe déjà quelqu'un ayant cet email");
                    }
                }
                catch(BDDException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                catch(NoSuchAlgorithmException ex){
                    JOptionPane.showMessageDialog(null, "Erreur d'algorithme de chiffrement");
                }
                
            }
            else{
                JOptionPane.showMessageDialog(null, "Les deux mots de passe saisis doivent être les mêmes et faire plus de 4 caractères");
            }
        }
    }

    private void addExistingVehicle(){
        if(immaExistTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir une immatriculation");
        }
        else{
            try{
                if(userCtrl.ajouterVehiculeExistant(immaExistTxt.getText())){
                    showPanel(panelAccount);
                }
                else{
                    JOptionPane.showMessageDialog(null, "L'immatriculation saisie n'a pas été trouvée");
                }
            }
            catch(BDDException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private void createAndAddVehicle(){
        if(immaNewTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir une immatriculation");
        }
        else if(brandTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir une marque");
        }
        else if(modelTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez saisir un modèle de voiture");
        }
        else{
            
            try{
                Double d = (Double)fiscSpin.getValue();
                if(userCtrl.ajouterVehicule(immaNewTxt.getText(), brandTxt.getText(),
                                            modelTxt.getText(), d.floatValue(),
                                            (int)(Integer)roomSpin.getValue(), new Energie((TypeEnergie) energyCbx.getSelectedItem()))){
                    showPanel(panelAccount);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue");
                }
            }
            catch(BDDException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            
        }
        System.out.println("Creating and adding vehicle");
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

        if(panel.equals(panelAccount)){
            fillPanelAcc();
        }
        else if(panel.equals(panelAddVehicle)){
            immaExistTxt.setText("");
            immaNewTxt.setText("");
            brandTxt.setText("");
            modelTxt.setText("");
            roomSpin.setValue(4);
            fiscSpin.setValue(3.12);
            energyCbx.setSelectedIndex(0);
        }else if(panel.equals(panelCreateAcc)){
            createEmailTxt.setText("");
            createCityTxt.setText("");
            createNameTxt.setText("");
            createFNameTxt.setText("");
            createPassTxt.setText("");
            confirmPassTxt.setText("");
        }else if(panel.equals(panelLogin)){
            emailLoginField.setText("");
            pwdLoginField.setText("");
        }
        else if(panel.equals(panelCredit)){
            creditSpin.setValue(0);
        }

        add(panel, c);
        revalidate();
        repaint();
    }

    private void crediter(){
        float f = ((Double)creditSpin.getValue()).floatValue();
        
        if(!userCtrl.crediter(f)){
            JOptionPane.showMessageDialog(null, "Erreur lors de l'opération de crédit");
        }
        else{
            showPanel(panelAccount);
        }
    }

    public ControleurCompte getUserCtrl(){
        return userCtrl;
    }

    public void showHome(){
        if(userCtrl.getUtilisateur() == null){
            showPanel(panelLogin);
        }
        else{
            showPanel(panelAccount);
        }
    }
}