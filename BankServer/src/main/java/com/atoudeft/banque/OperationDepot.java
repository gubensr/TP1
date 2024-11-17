package com.atoudeft.banque;

public class OperationDepot extends Operation {
    private double montant;

    public OperationDepot(double montant) {
        super(TypeOperation.DEPOT);
        this.montant = montant;
    }

    public double getMontant() {
        return montant;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Operation de Depot : Montant = " + montant + ", Date = " + getDate());
    }
}
