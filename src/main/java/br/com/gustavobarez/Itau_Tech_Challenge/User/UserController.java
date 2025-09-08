package br.com.gustavobarez.Itau_Tech_Challenge.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import br.com.gustavobarez.Itau_Tech_Challenge.Operation.OperationService;
import br.com.gustavobarez.Itau_Tech_Challenge.Position.PositionService;
import br.com.gustavobarez.Itau_Tech_Challenge.common.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Clients", description = "Clients management endpoints")
public class UserController {

    private final OperationService operationService;

    public UserController(OperationService operationService, PositionService positionService) {
        this.operationService = operationService;
    }

    @GetMapping("/{clientId}/average-price")
    @Operation(summary = "Get average price", description = "Calculate weighted average price by user and asset")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Average price calculated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<Map<Asset, BigDecimal>>> getAveragePrice(@PathVariable Long userId) {
        var averagePrice = operationService.calculateAveragePrice(userId);
        var response = new ApiResponseDTO<>(averagePrice, "get-average-price");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clients-by-position")
    @Operation(summary = "Get top 10 clients by position", description = "Get top 10 clients with highest positions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Top clients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getTopClientsByPosition() {
        var top = operationService.getTop10ClientsByPosition();
        var response = new ApiResponseDTO<>(top, "get-top-10-clients-by-position");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clients-by-brokerage")
    @Operation(summary = "Get top 10 clients by brokerage", description = "Get top 10 clients who paid most brokerage fees")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Top clients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getTopClientsByBrokerage() {
        var top = operationService.getTop10ClientsByBrokerage();
        var response = new ApiResponseDTO<>(top, "get-top-10-clients-by-brokerage");
        return ResponseEntity.ok(response);
    }

}
