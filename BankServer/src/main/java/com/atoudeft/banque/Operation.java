package com.atoudeft.banque;

import java.io.Serializable;
import java.util.Date;

public abstract class Operation implements Serializable {
    private static final long serialVersionUID = 1L;
    private TypeOperation type;
    private Date date;
    private double montant;

    public Operation(TypeOperation type) {
        this.type = type;
        this.date = new Date(System.currentTimeMillis());
    }

    public TypeOperation getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public double getMontant() {
        return montant;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", date, type, montant);
    }

    // Méthodes abstraites que les sous-classes devront implémenter
    public abstract void afficherDetails();
}

