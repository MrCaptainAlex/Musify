package com.example.catalogmanager.ModelData;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Catalog {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) // indica che l'id è un valore generato e sarà la chiave primaria della tabella
    private Integer idCD;

    @NotEmpty
    @Column(unique=true)
    private String nameCD;

    @NotEmpty
    private String authorCD;

    @NotNull
    private Integer nbavailableCD=0;

    @NotNull
    private Integer priceCD=0;

    public Catalog() {
    }


    public Integer getIdCD() {
        return idCD;
    }

    public void setIdCD(Integer idCD) {
        this.idCD = idCD;
    }

    public String getnameCD() {
        return nameCD;
    }

    public void setnameCD(String nameCD) {
        this.nameCD = nameCD;
    }

    public String getauthorCD() {
        return authorCD;
    }

    public void setauthorCD(String authorCD) {
        this.authorCD = authorCD;
    }

    public Integer getnbavailableCD() {
        return nbavailableCD;
    }

    public void setnbavailableCD(Integer nbavailableCD) {
        this.nbavailableCD = nbavailableCD;
    }

    public Integer getpriceCD() {
        return priceCD;
    }

    public void setpriceCD(Integer priceCD) {
        this.priceCD = priceCD;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "idCD=" + idCD +
                ", nameCD='" + nameCD + '\'' +
                ", authorCD='" + authorCD + '\'' +
                ", nbavailableCD=" + nbavailableCD +
                ", priceCD=" + priceCD +
                '}';
    }
}
