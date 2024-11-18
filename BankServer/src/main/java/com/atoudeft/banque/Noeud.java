package com.atoudeft.banque;

import java.io.Serializable;

// Classe Noeud - représente un noeud dans une pile chaînée
class Noeud<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private T valeur;
    private Noeud<T> suivant;

    public Noeud(T valeur) {
        this.valeur = valeur;
        this.suivant = null;
    }

    public T getValeur() {
        return valeur;
    }

    public void setSuivant(Noeud<T> suivant) {
        this.suivant = suivant;
    }

    public Noeud<T> getSuivant() {
        return suivant;
    }
}