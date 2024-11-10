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
            if (this.solde<CompteEpargne.LIMITE){
                this.solde-=CompteEpargne.FRAIS;
            }
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
