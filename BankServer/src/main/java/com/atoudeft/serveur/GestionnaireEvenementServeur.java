package com.atoudeft.serveur;

import com.atoudeft.banque.Banque;
import com.atoudeft.banque.CompteClient;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;
import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.CompteEpargne;
import com.atoudeft.banque.TypeCompte;

import java.util.Arrays;


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
                        String numeroCompteEpargne = null;
                        boolean estUtilise;
                        do {
                            numeroCompteEpargne = CompteBancaire.genereNouveauNumero();
                            final String finalNumeroCompteEpargne = numeroCompteEpargne;
                            estUtilise = serveurBanque.getBanque().getComptes()
                                    .stream()
                                    .anyMatch(c -> c.getNumero().equals(finalNumeroCompteEpargne));
                        } while (estUtilise);

                        CompteEpargne compteEpargne = new CompteEpargne(numeroCompteEpargne, TypeCompte.EPARGNE, 5.0);
                        serveurBanque.getBanque().getCompteClient(cnx.getNumeroCompteClient()).ajouter((CompteBancaire) compteEpargne);
                        cnx.envoyer("EPARGNE OK " + numeroCompteEpargne);
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