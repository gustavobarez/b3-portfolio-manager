package br.com.gustavobarez.Itau_Tech_Challenge.Asset;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavobarez.Itau_Tech_Challenge.Quotation.Quotation;
import br.com.gustavobarez.Itau_Tech_Challenge.Quotation.QuotationService;
import br.com.gustavobarez.Itau_Tech_Challenge.common.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/assets")
@Tag(name = "Assets", description = "Assets management endpoints")
public class AssetController {

    QuotationService quotationService;

    public AssetController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @GetMapping("/{assetId}/quotes/latest")
    @Operation(summary = "Get last quotation", description = "Get the most recent quotation for an asset")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Last quotation retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid asset ID"),
            @ApiResponse(responseCode = "404", description = "Quotation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<Quotation>> getLastQuotation(@PathVariable Long assetId) {
        return quotationService.findLastQuotation(assetId)
                .map(quotation -> {
                    var response = new ApiResponseDTO<>(quotation, "get-last-quotation");
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
