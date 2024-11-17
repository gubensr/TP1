package com.atoudeft.banque;

public class CompteCheque extends CompteBancaire{
    private double solde;
    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     * @param type   type du compte
     */

    public CompteCheque(String numero, TypeCompte type) {
        super(numero, type);
    }

    @Override
    public boolean crediter(double montant) {
        if (montant > 0) {
            this.setSolde(this.getSolde() + montant);
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant) {
        if (montant > 0 && this.getSolde() >= montant) {
            this.setSolde(this.getSolde() - montant);
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        if (montant >= 0 && this.getSolde() >= montant) {
            this.setSolde(this.getSolde() - montant);
            return true;
        }
        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }
}
