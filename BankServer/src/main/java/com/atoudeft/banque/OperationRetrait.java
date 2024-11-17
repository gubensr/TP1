package com.atoudeft.banque;

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
    public void afficherDetails() {
        System.out.println("Opération de Retrait : Montant = " + montant + ", Date = " + getDate());
    }
}
