package br.com.gustavobarez.Itau_Tech_Challenge.Position;

import java.math.BigDecimal;

public class GlobalPositionDTO {
    private Long userId;
    private BigDecimal totalProfitAndLoss;
    private Integer totalQuantity;
    private BigDecimal totalInvested;
    private Integer totalPositions;

    public GlobalPositionDTO(Long userId, BigDecimal totalProfitAndLoss, Integer totalQuantity,
            BigDecimal totalInvested, Integer totalPositions) {
        this.userId = userId;
        this.totalProfitAndLoss = totalProfitAndLoss;
        this.totalQuantity = totalQuantity;
        this.totalInvested = totalInvested;
        this.totalPositions = totalPositions;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getTotalProfitAndLoss() {
        return totalProfitAndLoss;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalInvested() {
        return totalInvested;
    }

    public Integer getTotalPositions() {
        return totalPositions;
    }

    public String getPositionStatus() {
        if (totalProfitAndLoss.compareTo(BigDecimal.ZERO) > 0) {
            return "LUCRO";
        } else if (totalProfitAndLoss.compareTo(BigDecimal.ZERO) < 0) {
            return "PREJUÍZO";
        } else {
            return "NEUTRO";
        }
    }
}