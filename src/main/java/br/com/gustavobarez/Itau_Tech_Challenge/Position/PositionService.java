package br.com.gustavobarez.Itau_Tech_Challenge.Position;

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
public class PositionService {

        PositionRepository repository;

        public PositionService(PositionRepository repository) {
                this.repository = repository;
        }

        @Async
        public CompletableFuture<Map<Asset, Position>> getPositionsPerAsset(Long userId) {
                if (userId == null) {
                        return CompletableFuture.failedFuture(new IllegalArgumentException("User ID cannot be null."));
                }
                List<Position> positions = repository.findByUserId(userId);

                if (positions == null || positions.isEmpty()) {
                        return CompletableFuture.completedFuture(Collections.emptyMap());
                }

                Map<Asset, Position> positionMap = positions.stream()
                                .collect(Collectors.toMap(
                                                Position::getAsset, position -> position));

                return CompletableFuture.completedFuture(positionMap);
        }

        @Async
        public CompletableFuture<GlobalPositionDTO> calculateGlobalPosition(Long userId) {
                if (userId == null) {
                        return CompletableFuture.failedFuture(new IllegalArgumentException("User ID cannot be null."));
                }
                List<Position> positions = repository.findByUserId(userId);

                if (positions == null || positions.isEmpty()) {
                        GlobalPositionDTO emptyGlobalPosition = new GlobalPositionDTO(userId, BigDecimal.ZERO, 0,
                                        BigDecimal.ZERO, 0);
                        return CompletableFuture.completedFuture(emptyGlobalPosition);
                }

                BigDecimal totalProfitAndLoss = positions.stream()
                                .map(Position::getProfitAndLoss)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                int totalQuantity = positions.stream()
                                .mapToInt(Position::getQuantity)
                                .sum();

                BigDecimal totalInvested = positions.stream()
                                .map(p -> p.getAveragePrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                GlobalPositionDTO globalPosition = new GlobalPositionDTO(userId, totalProfitAndLoss, totalQuantity,
                                totalInvested,
                                positions.size());

                return CompletableFuture.completedFuture(globalPosition);
        }

}
