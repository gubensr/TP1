package com.atoudeft.banque;

import java.io.Serializable;

public class PileChainee implements Serializable {
    private Noeud sommet;
    private int taille;

    public PileChainee() {
        this.sommet = null;
        this.taille = 0;
    }

    // empile un element dans la pile
    public void empiler(Object element) {
        Noeud nouveauNoeud = new Noeud(element);
        if (sommet != null) {
            nouveauNoeud.setPrecedent(sommet);
        }
        sommet = nouveauNoeud;
        taille++;
    }

    // depile un element de la pile et @return l'element depile
    public Object depiler() {
        if (estVide()) {
            throw new IllegalStateException("La pile est vide");
        }
        Object element = sommet.getElement();
        sommet = sommet.getPrecedent();
        taille--;
        return element;
    }

    // retourne l'element au sommet de la pile SANS le depiler
    public Object sommet() {
        if (estVide()) {
            throw new IllegalStateException("La pile est vide.");
        }
        return sommet.getElement();
    }

    // verifie si la pile est vide duh
    public boolean estVide() {
        return taille == 0;
    }

    // retourne la taille de la pile duh
    public int getTaille() {
        return taille;
    }
}

