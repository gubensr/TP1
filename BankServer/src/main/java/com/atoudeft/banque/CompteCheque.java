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
        this.solde=getSolde();
    }

    @Override
    public boolean crediter(double montant) {
        this.solde += montant;
        if (this.solde > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant) {
        this.solde -= montant;
        if (this.solde > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }
}
