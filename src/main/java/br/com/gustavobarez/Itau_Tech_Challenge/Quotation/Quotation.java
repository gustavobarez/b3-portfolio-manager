package br.com.gustavobarez.Itau_Tech_Challenge.Quotation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "quotations")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "unit_price", scale = 4, precision = 19)
    private BigDecimal unitPrice;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public Quotation() {
    }

    public Quotation(Asset asset, BigDecimal unitPrice, LocalDateTime dateTime) {
        this.asset = asset;
        this.unitPrice = unitPrice;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}