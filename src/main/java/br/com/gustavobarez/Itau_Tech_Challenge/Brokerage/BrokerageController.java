package br.com.gustavobarez.Itau_Tech_Challenge.Brokerage;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavobarez.Itau_Tech_Challenge.Operation.OperationService;
import br.com.gustavobarez.Itau_Tech_Challenge.common.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/brokerage")
@Tag(name = "Brokerage", description = "Brokerage management endpoints")
public class BrokerageController {

    OperationService operationService;

    public BrokerageController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/earnings")
    @Operation(summary = "Get total brokerage fee", description = "Calculate total brokerage fee for all operations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Total brokerage fee calculated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<BigDecimal>> getTotalBrokerageFee() {
        var totalBrokerageFee = operationService.calculateTotalBrokerageFee();
        var response = new ApiResponseDTO<>(totalBrokerageFee, "get-total-brokerage-fee");
        return ResponseEntity.ok(response);
    }

}
