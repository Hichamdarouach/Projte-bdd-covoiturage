package Vue;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Arrays;

import java.awt.event.*;


public class Menu extends JFrame {

    private JButton btnCompte;
    private JButton btnTrajets;
    private JButton btnParcours;

    private VueCompte panelCompte;
    private VueTrajet panelTrajets;
    private VueParcours panelParcours;

    public Menu() {
        super("VerbiageVoiture");

        WindowListener l = new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        panelCompte = new VueCompte();

        panelTrajets = new VueTrajet();

        panelParcours = new VueParcours();

        panelTrajets.setUserCtrl(panelCompte.getUserCtrl());
        panelParcours.setUserCtrl(panelCompte.getUserCtrl());

        JLabel prc = new JLabel("parcours");
        panelParcours.add(prc);

        btnCompte = new JButton("Compte");
        btnCompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPane(paneToShow.COMPTE);
            }
        });

        btnTrajets = new JButton("Conducteur");
        btnTrajets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPane(paneToShow.TRAJETS);
            }
        });

        btnParcours = new JButton("Passager");
        btnParcours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                showPane(paneToShow.PARCOURS);
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;

        add(btnCompte, c);

        c.gridx = 1;
        add(btnTrajets, c);

        c.gridx = 2;
        add(btnParcours, c);

        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.weighty = 1;
        add(panelCompte, c);

        addWindowListener(l);
        setSize(400, 400);
        setMinimumSize(getSize());
        setVisible(true);
    }

    private void showPane(paneToShow selected){
        if(panelCompte.getUserCtrl().getUtilisateur() == null){
            JOptionPane.showMessageDialog(null, "Veuillez vous connecter avant d'accéder à l'application");
            return;
        }
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.weighty = 1;

        if(Arrays.asList(getContentPane().getComponents()).contains(panelParcours)){
            getContentPane().remove(panelParcours);
        }
        else if(Arrays.asList(getContentPane().getComponents()).contains(panelCompte)){
            getContentPane().remove(panelCompte);
        }
        else if(Arrays.asList(getContentPane().getComponents()).contains(panelTrajets)){
            getContentPane().remove(panelTrajets);
        }


        switch(selected){
            case COMPTE:
                panelCompte.showHome();
                add(panelCompte, c);
                break;
            case TRAJETS:
                panelTrajets.showHome();
                add(panelTrajets, c);
                break;
            case PARCOURS:
                panelParcours.showHome();
                add(panelParcours, c);
                break;
            default:
                break;
        }
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static void main(String[] args){
        JFrame frame = new Menu();
    }

    enum paneToShow{
        COMPTE,
        TRAJETS,
        PARCOURS
    }
}