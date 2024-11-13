package com.atoudeft.banque;

import java.io.Serializable;

public class Noeud implements Serializable {

    private Operation data;
    private Noeud next;

    public Noeud(Operation data) {
        this.data = data;
        this.next = null;
    }

    public Operation getData() {
        return data;
    }

    public void setData(Operation data) {
        this.data = data;
    }

    public Noeud getNext() {
        return next;
    }

    public void setNext(Noeud next) {
        this.next = next;
    }
}
