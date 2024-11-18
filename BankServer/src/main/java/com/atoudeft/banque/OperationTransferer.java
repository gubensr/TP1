package com.atoudeft.banque;
import com.atoudeft.banque.*;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;


public class OperationTransferer extends Operation {
    private double montant;
    private CompteBancaire numeroCompteDestinataire;

    public OperationTransferer(double montant, CompteBancaire numeroCompteDestinataire) {
        super(TypeOperation.TRANSFER);
        this.montant = montant;
        this.numeroCompteDestinataire = numeroCompteDestinataire;
    }

    public double getMontant() {
        return montant;
    }

    public CompteBancaire getNumeroCompteDestinataire() {
        return numeroCompteDestinataire;
    }

    @Override
    public String toString() {
        return getDate() + " " + getType() + " " + getMontant() + " " + getNumeroCompteDestinataire();
    }

    @Override
    public void afficherDetails() {
        System.out.println(toString());
    }
}
