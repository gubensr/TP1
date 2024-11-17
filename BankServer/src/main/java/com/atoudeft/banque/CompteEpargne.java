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
        double nouvSolde = this.getSolde();
        nouvSolde += montant;
        this.setSolde(nouvSolde);
        return true;
    }
    return false;
}

    @Override
    public boolean debiter(double montant) {
        if (montant > 0 && this.getSolde() >= montant) {
            double soldeApresRetrait = this.getSolde() - montant;
            if (soldeApresRetrait < LIMITE) {
                double soldeApresFrais = soldeApresRetrait - FRAIS;
                if (soldeApresFrais >= 0) {
                    soldeApresRetrait = soldeApresFrais;
                } else {
                }
            }
            this.setSolde(soldeApresRetrait);
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        if (montant >= 0 && this.getSolde() >= montant) {
            double soldeApresPaiement = this.getSolde() - montant;
            if (soldeApresPaiement < LIMITE) {
                double soldeApresFrais = soldeApresPaiement - FRAIS;
                if (soldeApresFrais < 0) {
                    return false;
                }
                soldeApresPaiement = soldeApresFrais;
            }
            this.setSolde(soldeApresPaiement);
            return true;
        }
        return false;
    }

        public boolean transferer(double montant, CompteBancaire compteDestinataire) {
            if (montant > 0 && this.getSolde() >= montant) {
                double nouveauSolde = this.getSolde() - montant;
                if (nouveauSolde < LIMITE) {
                    double soldeApresFrais = nouveauSolde - FRAIS;
                    if (soldeApresFrais < 0) {
                        return false;
                    }
                    nouveauSolde = soldeApresFrais;
                }

                this.setSolde(nouveauSolde);
                compteDestinataire.crediter(montant);
                return true;
            }
            return false;
        }

}
