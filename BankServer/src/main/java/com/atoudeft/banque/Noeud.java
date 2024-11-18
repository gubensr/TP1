package com.atoudeft.banque;

import java.io.Serializable;

// Classe Noeud - represente un noeud dans une pile chainee
class Noeud<T> implements Serializable {
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

    @Override
    public String toString() {
        return valeur.toString();
    }
}