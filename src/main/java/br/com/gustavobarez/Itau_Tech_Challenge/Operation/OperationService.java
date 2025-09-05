package br.com.gustavobarez.Itau_Tech_Challenge.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;

@Service
public class OperationService {

    OperationRepository repository;

    public OperationService(OperationRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<Map<Asset, BigDecimal>> calculateInvestmentTotalPerAsset(Long userId) {
        if (userId == null) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("User ID cannot be null."));
        }
        List<Operation> operations = repository.findByUserIdAndOperationType(userId, "BUY");

        if (operations == null || operations.isEmpty()) {
            return CompletableFuture.completedFuture(Collections.emptyMap());
        }

        var investimentTotal = operations.stream()
                .collect(Collectors.groupingBy(
                        Operation::getAsset,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                op -> op.getUnitPrice().multiply(
                                        BigDecimal.valueOf(op.getQuantity())),
                                BigDecimal::add)));

        return CompletableFuture.completedFuture(investimentTotal);
    }

    @Async
    public CompletableFuture<BigDecimal> calculateTotalBrokerageFee(Long userId) {
        if (userId == null) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("User ID cannot be null."));
        }
        List<Operation> operations = repository.findByUserId(userId);

        if (operations == null || operations.isEmpty()) {
            return CompletableFuture.completedFuture(BigDecimal.ZERO);
        }

        var totalBrokerageFee = operations.stream()
                .map(Operation::getBrokerageFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CompletableFuture.completedFuture(totalBrokerageFee);
    }

    public Map<Asset, BigDecimal> calculateAssetWeightedAverage(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        List<Operation> operations = repository.findByUserIdAndOperationType(userId, "BUY");

        if (operations == null || operations.isEmpty()) {
            return Collections.emptyMap();
        }

        var assetWeightedAverage = operations.stream()
                .collect(Collectors.groupingBy(
                        Operation::getAsset,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                ops -> {
                                    if (ops.isEmpty())
                                        return BigDecimal.ZERO;

                                    BigDecimal totalValue = ops.stream()
                                            .map(op -> op.getUnitPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                    Long totalQuantity = ops.stream()
                                            .mapToLong(Operation::getQuantity)
                                            .sum();

                                    return totalQuantity > 0
                                            ? totalValue.divide(BigDecimal.valueOf(totalQuantity), 2,
                                                    RoundingMode.HALF_UP)
                                            : BigDecimal.ZERO;
                                })));
        return assetWeightedAverage;
    }

    public Map<Asset, BigDecimal> calculateAveragePrice(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        List<Operation> operations = repository.findByUserIdAndOperationType(userId, "BUY");

        if (operations == null || operations.isEmpty()) {
            Collections.emptyList();
        }

        return operations.stream()
                .collect(Collectors.groupingBy(
                        Operation::getAsset,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                ops -> {
                                    if (ops.isEmpty())
                                        return BigDecimal.ZERO;
                                    BigDecimal totalValue = ops.stream()
                                            .map(op -> op.getUnitPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                                    Long totalQuantity = ops.stream()
                                            .mapToLong(Operation::getQuantity)
                                            .sum();
                                    return totalValue.divide(BigDecimal.valueOf(totalQuantity), RoundingMode.HALF_UP);
                                })));
    }
}
