package br.com.gustavobarez.Itau_Tech_Challenge.Application;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import br.com.gustavobarez.Itau_Tech_Challenge.Operation.OperationService;
import br.com.gustavobarez.Itau_Tech_Challenge.Position.GlobalPositionDTO;
import br.com.gustavobarez.Itau_Tech_Challenge.Position.Position;
import br.com.gustavobarez.Itau_Tech_Challenge.Position.PositionService;

@Service
public class ApplicationService {

        private final OperationService operationService;
        private final PositionService positionService;

        public ApplicationService(OperationService operationService, PositionService positionService) {
                this.operationService = operationService;
                this.positionService = positionService;
        }

        public CompletableFuture<Map<Asset, BigDecimal>> investimentTotalPerAsset(Long userId) {
                return operationService.calculateInvestmentTotalPerAsset(userId);
        }

        public CompletableFuture<BigDecimal> totalBrokerageFee(Long userId) {
                return operationService.calculateTotalBrokerageFee(userId);
        }

        public CompletableFuture<Map<Asset, Position>> positionPerAsset(Long userId) {
                return positionService.getPositionsPerAsset(userId);
        }
        
}