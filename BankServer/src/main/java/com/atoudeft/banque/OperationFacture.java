package com.atoudeft.banque;

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
    public void afficherDetails() {
        System.out.println("Opération de Facture : Montant = " + montant +
                ", Numéro de Facture = " + numeroFacture +
                ", Description = " + description +
                ", Date = " + getDate());
    }
}
