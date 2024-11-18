package com.atoudeft.banque;

public class OperationTransferer extends Operation {
    private double montant;
    private String numeroCompteDestinataire;

    public OperationTransferer(double montant, String numeroCompteDestinataire) {
        super(TypeOperation.TRANSFER);
        this.montant = montant;
        this.numeroCompteDestinataire = numeroCompteDestinataire;
    }

    public double getMontant() {
        return montant;
    }

    public String getNumeroCompteDestinataire() {
        return numeroCompteDestinataire;
    }

    @Override
    public String toString() {
        return getDate() + " " + getType() + " " + montant + " " + numeroCompteDestinataire;
    }

    @Override
    public void afficherDetails() {
        System.out.println(toString());
    }
}
