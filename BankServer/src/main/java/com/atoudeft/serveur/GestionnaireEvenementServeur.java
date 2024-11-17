package com.atoudeft.serveur;

import com.atoudeft.banque.*;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

import java.util.Arrays;
import java.util.Objects;


/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        ServeurBanque serveurBanque = (ServeurBanque)serveur;
        Banque banque;
        ConnexionBanque cnx;
        String msg, typeEvenement, argument, numCompteClient, nip;
        String[] t;

        if (source instanceof Connexion) {
            cnx = (ConnexionBanque) source;
            System.out.println("SERVEUR: Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            cnx.setTempsDerniereOperation(System.currentTimeMillis());
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    serveurBanque.enlever(cnx);
                    cnx.close();
                    break;
                case "CONNECT": //Connecte à un compte
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    numCompteClient = t[0];
                    nip = t[1];
                    //verifier qu'il n'ya pas un des connectés qui utilise deja ce compte
                    if (serveurBanque.list().contains(numCompteClient)){
                        cnx.envoyer("CONNECT NO");
                        break;
                    }
                    banque=((ServeurBanque) serveur).getBanque();
                    CompteClient compteClient= banque.getCompteClient(numCompteClient);
                    if (compteClient == null){
                    cnx.envoyer("CONNECT NO");

                    break;
                    }
                    if (!compteClient.verifierNIP(nip)){
                        cnx.envoyer("CONNECT NO");
                        break;
                    }


                    //inscrit le numéro du compte-client et le numéro de son compte
                    //chèque dans l’objet ConnexionBanque du client
                    cnx.setNumeroCompteClient(numCompteClient);
                    cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                    cnx.envoyer("CONNECT OK");
                    break;
                case "LIST": //Envoie la liste des numéros de comptes-clients connectés :
                    cnx.envoyer("LIST " + serveurBanque.list());
                    break;
                /******************* COMMANDES DE GESTION DE COMPTES *******************/
                case "NOUVEAU": //Crée un nouveau compte-client :
                    if (cnx.getNumeroCompteClient()!=null) {
                        cnx.envoyer("NOUVEAU NO deja connecte");
                        break;
                    }
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("NOUVEAU NO");
                    }
                    else {
                        numCompteClient = t[0];
                        nip = t[1];
                        banque = serveurBanque.getBanque();
                        if (banque.ajouter(numCompteClient,nip)) {
                            cnx.setNumeroCompteClient(numCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                            cnx.envoyer("NOUVEAU OK " + t[0] + " cree");
                        }
                        else
                            cnx.envoyer("NOUVEAU NO "+t[0]+" existe");
                    }
                    break;

                case "EPARGNE":
                    if (cnx.getNumeroCompteClient() == null) {
                        cnx.envoyer("EPARGNE NO pas connecte");
                    } else if (serveurBanque.getBanque().possedeCompteEpargne(cnx.getNumeroCompteClient())) {
                        cnx.envoyer("EPARGNE NO deja possede");
                    } else {
                        String numeroCompteEpargne;
                        boolean estUtilise;
                        banque = serveurBanque.getBanque();
                        do {
                            numeroCompteEpargne = CompteBancaire.genereNouveauNumero();
                            estUtilise = false;
                            for (CompteBancaire c : banque.getComptes()) {
                                if (Objects.equals(c.getNumero(), numeroCompteEpargne)) {
                                    estUtilise = true;
                                    break;
                                }
                            }
                        } while (estUtilise);

                        CompteEpargne compteEpargne = new CompteEpargne(numeroCompteEpargne, TypeCompte.EPARGNE, 5.0);
                        serveurBanque.getBanque().getCompteClient(cnx.getNumeroCompteClient()).ajouter( compteEpargne);
                        cnx.envoyer("EPARGNE OK " + numeroCompteEpargne);
                    }
                    break;

                case "SELECT":
                    banque = serveurBanque.getBanque();
                    if (cnx.getNumeroCompteClient() != null) {
                        argument = evenement.getArgument();
                        if (argument.equalsIgnoreCase("EPARGNE")){
                            cnx = (ConnexionBanque) source;
                            cnx.setNumeroCompteActuel(banque.getCompteClient(cnx.getNumeroCompteClient()).getComptes().get(1).getNumero());
                            System.out.println(cnx.getNumeroCompteActuel());
                            break;
                        } else if (argument.equalsIgnoreCase("CHEQUE")){
                            cnx = (ConnexionBanque) source;
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(cnx.getNumeroCompteClient()));
                            System.out.println(cnx.getNumeroCompteActuel());
                            break;
                        } else {
                            cnx.envoyer("ERREUR ");
                        }
                    } else {
                        cnx.envoyer("SELECT pas connecte");
                        break;
                    }

                    break;

                case "DEPOT":
                    if (cnx.getNumeroCompteClient() == null) {
                        cnx.envoyer("DEPOT NO pas connecte");
                    } else {
                        banque = serveurBanque.getBanque();
                        compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                        String montantStr = evenement.getArgument();
                        double montant = Double.parseDouble(montantStr);
                        CompteBancaire compte = null;
                        String numeroCompteActuel = cnx.getNumeroCompteActuel();
                        if (numeroCompteActuel.equals(compteClient.getCompteParType(TypeCompte.EPARGNE).getNumero())) {
                            compte = compteClient.getCompteParType(TypeCompte.EPARGNE);
                        } else if (numeroCompteActuel.equals(compteClient.getCompteParType(TypeCompte.CHEQUE).getNumero())) {
                            compte = compteClient.getCompteParType(TypeCompte.CHEQUE);
                        }
                        if (compte == null) {
                            cnx.envoyer("DEPOT NO compte non trouvé");
                        } else if (compte instanceof CompteCheque && ((CompteCheque) compte).crediter(montant)) {
                            cnx.envoyer("DEPOT OK " + montant);
                        } else if (compte instanceof CompteEpargne && ((CompteEpargne) compte).crediter(montant)) {
                            cnx.envoyer("DEPOT OK " + montant);
                        } else {
                            cnx.envoyer("DEPOT NO erreur");
                        }
                    }
                    break;

                case "RETRAIT":
                    if (cnx.getNumeroCompteClient() == null) {
                        cnx.envoyer("RETRAIT NO pas connecte");
                    } else {
                        banque = serveurBanque.getBanque();
                        compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                        String montantStr = evenement.getArgument();
                        double montant = Double.parseDouble(montantStr);
                        String numeroCompteActuel = cnx.getNumeroCompteActuel();
                        CompteBancaire compte = compteClient.getCompteParNumero(numeroCompteActuel);
                        if (compte == null) {
                            cnx.envoyer("RETRAIT NO pas de compte trouvé");
                        } else if (compte.getSolde() < montant) {
                            cnx.envoyer("RETRAIT NO solde insuffisant");
                        } else if (compte.debiter(montant)) {
                            cnx.envoyer("RETRAIT OK " + montant);
                        } else {
                            cnx.envoyer("RETRAIT NO erreur");
                        }
                    }
                    break;

                /******************* TRAITEMENT PAR DÉFAUT *******************/
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}