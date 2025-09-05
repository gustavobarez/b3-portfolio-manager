package br.com.gustavobarez.Itau_Tech_Challenge.Quotation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuotationMessage {

    private Long asset;

    private BigDecimal unitPrice;

    private LocalDateTime dateTime;

    private String source;

    public QuotationMessage() {

    }

    public QuotationMessage(Long asset, BigDecimal unitPrice, LocalDateTime dateTime, String source) {
        this.asset = asset;
        this.unitPrice = unitPrice;
        this.dateTime = dateTime;
        this.source = source;
    }

    public Long getAsset() {
        return asset;
    }

    public void setAsset(Long asset) {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
