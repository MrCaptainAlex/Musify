package com.example.usermanager.ModelData;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) // indica che l'id è un valore generato e sarà la chiave primaria della tabella
    private Integer idUser;

    @NotEmpty
    @Column(unique=true) // serve a dire che l'email sarà univoca nel db
    private String username;

    @NotEmpty
    @Column(unique=true) // serve a dire che l'email sarà univoca nel db
    private String email;

    @NotEmpty
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY) // serve a dire che nel dialogo tra client e contesto non potrò leggerla, solo scriverla
    private String psw;


    private Integer nb_acquisti=0;


    public User() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Integer getNb_acquisti() {
        return nb_acquisti;
    }

    public void setNb_acquisti(Integer nb_acquisti) {
        this.nb_acquisti = nb_acquisti;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", psw='" + psw + '\'' +
                ", nb_acquisti=" + nb_acquisti +
                '}';
    }
}