package com.atoudeft.banque;

import java.io.Serializable;

// Classe PileChainee - représente une pile chaînée simple
public class PileChainee<T> implements Serializable {
    private Noeud<T> sommet;

    public PileChainee() {
        this.sommet = null;
    }

    // Empile un élément dans la pile
    public void empiler(T valeur) {
        Noeud<T> nouveauNoeud = new Noeud<>(valeur);
        nouveauNoeud.setSuivant(sommet);
        sommet = nouveauNoeud;
    }

    // Dépile un élément de la pile
    public T depiler() {
        if (estVide()) {
            throw new IllegalStateException("La pile est vide");
        }
        T valeur = sommet.getValeur();
        sommet = sommet.getSuivant();
        return valeur;
    }

    // Vérifie si la pile est vide
    public boolean estVide() {
        return sommet == null;
    }

    // Retourne l'élément au sommet sans le dépiler
    public T sommet() {
        if (estVide()) {
            throw new IllegalStateException("La pile est vide");
        }
        return sommet.getValeur();
    }

    public String toString() {
        StringBuilder chaineListe = new StringBuilder();

        Noeud courant = sommet;
        while (courant.getSuivant() != null) {
            chaineListe.append(courant.toString() + "\n");
            courant = courant.getSuivant();
        }
        chaineListe.append(courant.toString() + "\n");

        return chaineListe.toString();
    }
}