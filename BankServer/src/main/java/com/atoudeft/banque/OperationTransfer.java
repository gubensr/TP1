package com.atoudeft.banque;

public class OperationTransfer extends Operation {
    private double montant;
    private String compteDestinataire;

    public OperationTransfer(double montant, String compteDestinataire) {
        super(TypeOperation.TRANSFER);
        this.montant = montant;
        this.compteDestinataire = compteDestinataire;
    }

    public double getMontant() {
        return montant;
    }

    public String getCompteDestinataire() {
        return compteDestinataire;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Opération de Transfert : Montant = " + montant +
                ", Compte destinataire = " + compteDestinataire +
                ", Date = " + getDate());
    }
}
