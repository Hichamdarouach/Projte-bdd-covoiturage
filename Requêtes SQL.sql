-- Création de compte utilisateur : On ajoute dans des colonnes précises (pas encore de véhicule)


INSERT INTO Utilisateur (‘email’,’nom’,’pnom’,’villeRes’,’mdp’,’solde’) 
VALUES (‘email’,’nom’,’pnom’,’villeRes’,’mdp’,’0’);


-- Ajout d’un véhicule


INSERT INTO Vehicule VALUES(‘immatriculation’,’marque’,’modele’,’puisFisc’,’nbPlaces’,’typeEnergie’);


INSERT INTO Utilisateur (‘vehicule’) VALUES (‘immatriculation’);


-- Rechargement du porte-monnaie d’un utilisateur 
-- assert ajout > 0


UPDATE Utilisateur
SET solde = ‘solde+ajout’
WHERE email = ‘email’;


-- Ajout du trajet (et donc des tronçons) côté conducteur
-- assert nbPlaces < nbPlaces vehicule




INSERT INTO Trajet
VALUES (‘idTrajet’, ‘dateDep’, ‘heureDep’, ‘immatriculation’);




-- Boucle for selon les troncons définis par le conducteur
INSERT INTO Troncons
VALUES (‘idTroncon+i’,’villeDep’,’longDep’,’latDep’,’villeArr’,’longArr’,’latArr’,’distance’,’dureeEst’,’ordreTr+i’,’dureeMaxAtt’,’Prévu’);


-- Ajout du parcours côté voyageur


INSERT INTO Parcours
VALUES ();


-- Recherche tronçons côté voyageur
-- le voyageur entre : villeDep, villeArr, dateDep


SELECT (villeDep, villeArr,distance,dureeEst,dureeMaxAtt) FROM Troncon
WHERE villeDep = ‘villeDep’
AND villeArr = ‘villeArr’
AND dateDep > ‘dateDep’;


-- Confirmation départ côté conducteur


UPDATE Etat
SET nomEtat = ‘Parti’
WHERE idTroncon = ‘idTroncon’;


-- Confirmation montée/descente côté voyageur
-- if avec true pour montée et false pour descendre dans java
UPDATE SuiviVoyageurs
SET estMonte = ‘true’ || estDescendu = ‘true’
WHERE idParcours = ‘idParcours’
AND idTroncon = ‘idTroncon’;


-- Paiement du conducteur
UPDATE Utilisateur
SET solde = ‘solde + prixTrajet’
WHERE email = ‘email’;


SELECT *
FROM Troncon t1, Troncon t2
WHERE t1.idTrajet = t2.idTrajet
AND t1.villeDep = 'villeDep'
AND t2.villeArr = 'villeArr'
AND t1.ordreTr < t2.ordreTr;


-- Récupérer les passagers d'un tronçon
SELECT Parcours.emailUtilisateur, p.suiviVoyageur
FROM Parcours, ParContientTr p
WHERE p.idParcours = Parcours.idParcours
AND p.idTrajet = 0
AND p.idTroncon = 0 