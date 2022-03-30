package com.example.paymentmanager.ModelData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) // indica che l'id è un valore generato e sarà la chiave primaria della tabella
    private Integer idPayment;

    @NotNull
    private Integer idUser;

    @NotNull
    private Integer idCD;

    @NotNull
    private Integer nbpurchasedCD;

    private Integer totalPrice;

    private String state;

    private String reason;

    public Payment() {
    }

    public Integer getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(Integer idPayment) {
        this.idPayment = idPayment;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdCD() {
        return idCD;
    }

    public void setIdCD(Integer idCD) {
        this.idCD = idCD;
    }

    public Integer getNbpurchasedCD() {
        return nbpurchasedCD;
    }

    public void setNbpurchasedCD(Integer nbpurchasedCD) {
        this.nbpurchasedCD = nbpurchasedCD;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "idPayment=" + idPayment +
                ", idUser=" + idUser +
                ", idCD=" + idCD +
                ", nbpurchasedCD=" + nbpurchasedCD +
                ", totalPrice=" + totalPrice +
                ", state='" + state + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
