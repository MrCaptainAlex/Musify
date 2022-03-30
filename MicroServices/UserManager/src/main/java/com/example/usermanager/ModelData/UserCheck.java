package com.example.usermanager.ModelData;

import java.io.Serializable;

public class UserCheck implements Serializable {


    private Integer idUser;
    private Integer idPayment;


    public Integer getIdUser() {
        return idUser;
    }

    public UserCheck setIdUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public Integer getIdPayment() {
        return idPayment;
    }

    public UserCheck setIdPayment(Integer idPayment) {
        this.idPayment = idPayment;
        return this;
    }
}
