package com.atoudeft.banque;

public class OperationTransfer extends Operation {
    private double montant;
    private String numeroCompteDestinataire;

    public OperationTransfer(double montant, String numeroCompteDestinataire) {
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
    public void execute() {
    }

    @Override
    public String toString() {
        return getDate() + " " + getType() + " " + montant + " " + numeroCompteDestinataire;
    }
}
