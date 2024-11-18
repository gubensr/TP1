package com.atoudeft.banque;
import com.atoudeft.banque.*;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

public class OperationFacture extends Operation {
    private double montant;
    private String numeroFacture;
    private String description;

    public OperationFacture(double montant, String numeroFacture, String description) {
        super(TypeOperation.FACTURE);
        this.montant = montant;
        this.numeroFacture = numeroFacture;
        this.description = description;
    }

    public double getMontant() {
        return montant;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getDate() + " " + getType() + " " + getMontant() + " " + getNumeroFacture() + " " + getDescription();
    }

    @Override
    public void afficherDetails() {
        System.out.println(toString());
    }
}
