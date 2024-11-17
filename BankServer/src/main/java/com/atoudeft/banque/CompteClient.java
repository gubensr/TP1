package com.atoudeft.banque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompteClient implements Serializable {
    private String numero;
    private String nip;
    private List<CompteBancaire> comptes;

    /**
     * Crée un compte-client avec un numéro et un nip.
     *
     * @param numero le numéro du compte-client
     * @param nip le nip
     */
    public CompteClient(String numero, String nip) {
        this.numero = numero;
        this.nip = nip;
        comptes = new ArrayList<>();
    }

    public CompteBancaire getCompteParNumero(String numero) {
        for (CompteBancaire compte : comptes) {
            if (compte.getNumero().equals(numero)) {
                return compte;
            }
        }
        return null;
    }


    /**
     * Ajoute un compte bancaire au compte-client.
     *
     * @param compte le compte bancaire
     * @return true si l'ajout est réussi
     */
    public boolean ajouter(CompteBancaire compte) {
        return this.comptes.add(compte);
    }

    //Ajouter par Gubens
    public List<CompteBancaire> getComptes() {
        return this.comptes;
    }
    public boolean verifierNIP(String nipEntree){
        return nipEntree.equals(this.nip);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompteClient that = (CompteClient) o;
        return Objects.equals(numero, that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    public CompteBancaire getCompteParType(TypeCompte type) {
        for (CompteBancaire compte : comptes) {
            if (compte.getType() == type) {
                return compte; }
        } return null;
    }
}

