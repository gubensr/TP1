package com.atoudeft.banque;

public class CompteCheque extends CompteBancaire{
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
            OperationDepot operation = new OperationDepot(montant);
            enregistrerOperation(operation);
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant) {
        if (montant > 0 && this.getSolde() >= montant) {
            this.setSolde(this.getSolde() - montant);
            OperationRetrait operation = new OperationRetrait(montant);
            enregistrerOperation(operation);
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        if (montant >= 0 && this.getSolde() >= montant) {
            this.setSolde(this.getSolde() - montant);
            OperationFacture operation = new OperationFacture(montant, numeroFacture, description);
            enregistrerOperation(operation);
            return true;
        }
        return false;
    }

    public boolean transferer(double montant, CompteBancaire compteDestinataire) {
            if (montant > 0 && this.getSolde() >= montant) {
                this.setSolde(this.getSolde() - montant);
                compteDestinataire.crediter(montant);
                OperationTransferer operation = new OperationTransferer(montant, compteDestinataire);
                enregistrerOperation(operation);
                return true;
            }
            return false;
        }
}
