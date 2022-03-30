package com.example.paymentmanager.ModelData;

import java.io.Serializable;

public class CatalogCheck implements Serializable {
    private Integer idCD;
    private Integer idPayment;
    private Integer nbCD;

    public Integer getIdCD() {
        return idCD;
    }

    public CatalogCheck setIdCD(Integer idCD) {
        this.idCD = idCD;
        return this;
    }

    public Integer getIdPayment() {
        return idPayment;
    }

    public CatalogCheck setIdPayment(Integer idPayment) {
        this.idPayment = idPayment;
        return this;
    }

    public Integer getNbCD() {
        return nbCD;
    }

    public CatalogCheck setNbCD(Integer nbCD) {
        this.nbCD = nbCD;
        return this;
    }
}
