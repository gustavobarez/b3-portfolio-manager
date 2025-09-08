package br.com.gustavobarez.Itau_Tech_Challenge.Operation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import br.com.gustavobarez.Itau_Tech_Challenge.common.ApiResponse;

@RestController
@RequestMapping("/api/v1/operation")
public class OperationController {

    OperationService service;

    public OperationController(OperationService service) {
        this.service = service;
    }

    @GetMapping("/averagePrice")
    public ResponseEntity<ApiResponse<Map<Asset, BigDecimal>>> getAveragePrice(@RequestParam("id") Long userId) {
        var averagePrice = service.calculateAveragePrice(userId);
        var response = new ApiResponse<>(averagePrice, "get-average-price");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/totalBrokerageFee")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalBrokerageFee() {
        var totalBrokerageFee = service.calculateTotalBrokerageFee();
        var response = new ApiResponse<>(totalBrokerageFee, "get-total-brokerage-fee");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/topClientsByPosition")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTopClientsByPosition() {
        var top = service.getTop10ClientsByPosition();
        var response = new ApiResponse<>(top, "get-top-10-clients-by-position");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/topClientsByBrokerage")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTopClientsByBrokerage() {
        var top = service.getTop10ClientsByBrokerage();
        var response = new ApiResponse<>(top, "get-top-10-clients-by-brokerage");
        return ResponseEntity.ok(response);
    }

}
