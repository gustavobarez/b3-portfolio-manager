package br.com.gustavobarez.Itau_Tech_Challenge.Position;

import java.math.BigDecimal;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import br.com.gustavobarez.Itau_Tech_Challenge.User.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private int quantity;

    @Column(name = "average_price", scale = 4, precision = 19)
    private BigDecimal averagePrice;

    @Column(name = "profit_and_loss", scale = 4, precision = 19)
    private BigDecimal profitAndLoss;

    public Position() {
    }

    public Position(User user, Asset asset, int quantity, BigDecimal averagePrice, BigDecimal profitAndLoss) {
        this.user = user;
        this.asset = asset;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.profitAndLoss = profitAndLoss;
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

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(BigDecimal profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }
}