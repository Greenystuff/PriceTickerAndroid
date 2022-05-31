package com.greedythings.priceticker.Models;

import java.io.Serializable;

public class ProductData implements Serializable {

    private String Id;

    private String Libelle;

    private String Prix;

    private boolean valideId;



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public boolean isValideId() {
        return valideId;
    }

    public void setValideId(boolean valideId) {
        this.valideId = valideId;
    }
}
