package com.atoudeft.banque;

import java.io.Serializable;

public class Noeud implements Serializable {
    private Object element;
    private Noeud suivant;
    private Noeud precedent;

    public Noeud(Object element) {
        this.element = element;
        this.suivant = null;
        this.precedent = null;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public Noeud getSuivant() {
        return suivant;
    }

    public void setSuivant(Noeud suivant) {
        this.suivant = suivant;
    }

    public Noeud getPrecedent() {
        return precedent;
    }

    public void setPrecedent(Noeud precedent) {
        this.precedent = precedent;
    }
}
