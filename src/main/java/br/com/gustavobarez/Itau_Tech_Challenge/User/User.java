package br.com.gustavobarez.Itau_Tech_Challenge.User;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "brokerage_porcentage", scale = 5, precision = 10)
    private BigDecimal brokeragePorcentage;

    public User() {
    }

    public User(String name, String email, BigDecimal brokeragePorcentage) {
        this.name = name;
        this.email = email;
        this.brokeragePorcentage = brokeragePorcentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBrokeragePorcentage() {
        return brokeragePorcentage;
    }

    public void setBrokeragePorcentage(BigDecimal brokeragePorcentage) {
        this.brokeragePorcentage = brokeragePorcentage;
    }
}