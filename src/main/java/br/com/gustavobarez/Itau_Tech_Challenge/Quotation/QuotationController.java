package br.com.gustavobarez.Itau_Tech_Challenge.Quotation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavobarez.Itau_Tech_Challenge.common.ApiResponse;

@RestController
@RequestMapping("/api/v1/quotation")
public class QuotationController {

    QuotationService service;

    public QuotationController(QuotationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Quotation>> getLastQuotation(@RequestParam("id") Long assetId) {
        var lastQuotation = service.findLastQuotation(assetId);
        var response = new ApiResponse<>(lastQuotation.get(), "get-last-operation");
        return ResponseEntity.ok(response);
    }

}
