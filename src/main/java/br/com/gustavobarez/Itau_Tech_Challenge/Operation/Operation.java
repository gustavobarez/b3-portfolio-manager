package br.com.gustavobarez.Itau_Tech_Challenge.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import br.com.gustavobarez.Itau_Tech_Challenge.User.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    private int quantity;

    @Column(name = "unit_price", scale = 4, precision = 19)
    private BigDecimal unitPrice;

    @Column(name = "operation_type")
    private String operationType;

    @Column(name = "brokerage_fee", scale = 4, precision = 19)
    private BigDecimal brokerageFee;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public Operation() {
    }

    public Operation(User user, Asset asset, int quantity, BigDecimal unitPrice, String operationType,
            BigDecimal brokerageFee, LocalDateTime dateTime) {
        this.user = user;
        this.asset = asset;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.operationType = operationType;
        this.brokerageFee = brokerageFee;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getBrokerageFee() {
        return brokerageFee;
    }

    public void setBrokerageFee(BigDecimal brokerageFee) {
        this.brokerageFee = brokerageFee;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}