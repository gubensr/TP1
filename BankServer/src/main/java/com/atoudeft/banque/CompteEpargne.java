package com.atoudeft.banque;

public class CompteEpargne extends CompteBancaire{
    private double solde;
    private static final double LIMITE=1000.0;
    private static final double FRAIS=2.0;
    private double tauxInteret;
    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     * @param type   type du compte
     */
    public CompteEpargne(String numero, TypeCompte type,double tauxInteret) {
        super(numero, type);
        this.solde=getSolde();
        this.tauxInteret =tauxInteret;
    }

    public void ajouterInterets(){
    double interets = this.solde * (tauxInteret / 100);
    this.solde += interets;
    }

    @Override
    public boolean crediter(double montant) {
        if (montant > 0) {
            this.solde += montant;
            this.setSolde(this.solde);
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant) {
        if (montant > 0 && this.solde >= montant) {
            this.solde -= montant;
            if (this.solde < LIMITE) {
                this.solde -= FRAIS;
            }
            this.setSolde(this.solde);
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
