package com.atoudeft.banque;
import com.atoudeft.banque.*;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;


public class OperationRetrait extends Operation {
    private double montant;

    public OperationRetrait(double montant) {
        super(TypeOperation.RETRAIT);
        this.montant = montant;
    }

    public double getMontant() {
        return montant;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", getDate(), getType(), getMontant());
    }


    @Override
    public void afficherDetails() {
        System.out.println(toString());
    }
}
