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
import br.com.gustavobarez.Itau_Tech_Challenge.common.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/operation")
public class OperationController {

    OperationService service;

    public OperationController(OperationService service) {
        this.service = service;
    }

    @GetMapping("/averagePrice")
    @Operation(summary = "Get average price", description = "Calculate weighted average price by user and asset")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Average price calculated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<Map<Asset, BigDecimal>>> getAveragePrice(@RequestParam("id") Long userId) {
        var averagePrice = service.calculateAveragePrice(userId);
        var response = new ApiResponseDTO<>(averagePrice, "get-average-price");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/totalBrokerageFee")
    @Operation(summary = "Get total brokerage fee", description = "Calculate total brokerage fee for all operations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Total brokerage fee calculated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<BigDecimal>> getTotalBrokerageFee() {
        var totalBrokerageFee = service.calculateTotalBrokerageFee();
        var response = new ApiResponseDTO<>(totalBrokerageFee, "get-total-brokerage-fee");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/topClientsByPosition")
    @Operation(summary = "Get top 10 clients by position", description = "Get top 10 clients with highest positions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Top clients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getTopClientsByPosition() {
        var top = service.getTop10ClientsByPosition();
        var response = new ApiResponseDTO<>(top, "get-top-10-clients-by-position");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/topClientsByBrokerage")
    @Operation(summary = "Get top 10 clients by brokerage", description = "Get top 10 clients who paid most brokerage fees")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Top clients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getTopClientsByBrokerage() {
        var top = service.getTop10ClientsByBrokerage();
        var response = new ApiResponseDTO<>(top, "get-top-10-clients-by-brokerage");
        return ResponseEntity.ok(response);
    }

}
