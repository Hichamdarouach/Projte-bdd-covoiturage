CREATE DATABASE autopartage;

CREATE TABLE Utilisateur (
    email char(40),
    nom char(20),
    pnom char(20),
    villeRes char(20),
    mdp char(64),
    solde float CHECK (solde >= 0),
    PRIMARY KEY (email)
);

CREATE TABLE Vehicule (
    immatriculation char(10),
    marque char(20),
    modele char(20),
    puisFisc float,
    nbPlaces int,
    energieUtilisee char(10) CHECK (energieUtilisee='Essence' OR energieUtilisee='Diesel' OR energieUtilisee='Electrique'),
    PRIMARY KEY (immatriculation)
);

CREATE TABLE Trajet (
    idTrajet int,
    dateDep DATE,
    heureDep TIMESTAMP,
    nbPlaces int CHECK (nbPlaces > 0),
    immaVehicule char(10),
    emailUtilisateur char(40),
    FOREIGN KEY (emailUtilisateur) REFERENCES Utilisateur(email),
    FOREIGN KEY (immaVehicule) REFERENCES Vehicule(immatriculation),
    PRIMARY KEY (idTrajet)
);

CREATE TABLE Troncon (
    idTroncon int,
    idTrajet int,
    villeDep char(20),
    longDep float,
    latDep float,
    villeArr char(20),
    longArr float,
    latArr float,
    dureeEst TIMESTAMP,
    etatCourant char(10) CHECK (etatCourant in ('prevu', 'parti', 'arrive', 'annule')),
    FOREIGN KEY (idTrajet) REFERENCES Trajet(idTrajet),
    PRIMARY KEY (idTroncon, idTrajet)
);

CREATE TABLE Parcours (
    idParcours int,
    emailUtilisateur char(40),
    FOREIGN KEY (emailUtilisateur) REFERENCES Utilisateur(email),
    PRIMARY KEY (idParcours)
);

CREATE TABLE Attente (
    duree int CHECK (duree < 60),
    idTroncon int,
    idTrajet int,
    FOREIGN KEY (idTroncon, idTrajet) REFERENCES Troncon(idTroncon, idTrajet),
    PRIMARY KEY (idTroncon, idTrajet)
);

CREATE TABLE Conduit (
    emailUtilisateur char(40),
    immaVehicule char(10),
    FOREIGN KEY (emailUtilisateur) REFERENCES Utilisateur(email),
    FOREIGN KEY (immaVehicule) REFERENCES Vehicule(immatriculation),
    PRIMARY KEY (immaVehicule, emailUtilisateur)
);

CREATE TABLE ParContientTr (
    idTroncon int,
    idTrajet int,
    idParcours int,
    ordrePar int,
    suiviVoyageur char(10) CHECK (suiviVoyageur in ('pasMonte', 'Monte', 'Descendu')),
    FOREIGN KEY (idTroncon, idTrajet) REFERENCES Troncon(idTroncon, idTrajet),
    FOREIGN KEY (idParcours) REFERENCES Parcours(idParcours),
    PRIMARY KEY (idTroncon, idTrajet, idParcours)
);

