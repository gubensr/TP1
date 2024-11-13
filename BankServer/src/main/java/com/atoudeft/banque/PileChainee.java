package com.atoudeft.banque;

import java.io.Serializable;

public class PileChainee implements Serializable {
    private Noeud sommet;

    public PileChainee() {
        this.sommet = null;
    }

    public boolean estVide() {
        return sommet == null;
    }

    public void empiler(Operation operation) {
        Noeud nouveauNoeud = new Noeud(operation);
        nouveauNoeud.setNext(sommet);
        sommet = nouveauNoeud;
    }

    public Operation depiler() {
        if (estVide()) {
            throw new IllegalStateException("La pile est vide.");
        }
        Operation operation = sommet.getData();
        sommet = sommet.getNext();
        return operation;
    }

    public Operation sommet() {
        if (estVide()) {
            throw new IllegalStateException("La pile est vide.");
        }
        return sommet.getData();
    }
}

