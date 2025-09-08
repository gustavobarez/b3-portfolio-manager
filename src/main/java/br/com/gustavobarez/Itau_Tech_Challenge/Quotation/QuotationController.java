package br.com.gustavobarez.Itau_Tech_Challenge.Quotation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavobarez.Itau_Tech_Challenge.common.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/quotation")
public class QuotationController {

    QuotationService service;

    public QuotationController(QuotationService service) {
        this.service = service;
    }

    @GetMapping("/last-quotation")
    @Operation(summary = "Get last quotation", description = "Get the most recent quotation for an asset")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Last quotation retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid asset ID"),
            @ApiResponse(responseCode = "404", description = "Quotation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<Quotation>> getLastQuotation(@RequestParam("id") Long assetId) {
        var lastQuotation = service.findLastQuotation(assetId);
        var response = new ApiResponseDTO<>(lastQuotation.get(), "get-last-operation");
        return ResponseEntity.ok(response);
    }

}
