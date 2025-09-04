package br.com.gustavobarez.Itau_Tech_Challenge.Operation;

import java.math.BigDecimal;
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

}
