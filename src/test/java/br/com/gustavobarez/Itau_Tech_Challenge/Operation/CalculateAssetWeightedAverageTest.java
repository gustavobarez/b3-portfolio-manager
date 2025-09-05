package br.com.gustavobarez.Itau_Tech_Challenge.Operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import br.com.gustavobarez.Itau_Tech_Challenge.User.User;

@ExtendWith(MockitoExtension.class)
public class CalculateAssetWeightedAverageTest {

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private OperationService operationService;

    private Long userId;
    private User user;
    private Asset asset1;
    private Asset asset2;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        userId = 1L;
        user = new User("Test User", "test@example.com", new BigDecimal("0.05"));
        user.setId(userId);

        asset1 = new Asset("ITUB4", "ITAU UNIBANCO PN");
        asset1.setId(101L);

        asset2 = new Asset("PETR4", "PETROBRAS PN");
        asset2.setId(102L);

        testDateTime = LocalDateTime.now();
    }

    @Test
    @DisplayName("Should calculate weighted average for single asset with single operation")
    void shouldCalculateWeightedAverageForSingleAssetSingleOperation() {
        List<Operation> operations = Arrays.asList(
                new Operation(user, asset1, 100, new BigDecimal("10.00"), "BUY",
                        new BigDecimal("5.00"), testDateTime));

        when(operationRepository.findByUserIdAndOperationType(userId, "BUY")).thenReturn(operations);

        Map<Asset, BigDecimal> result = operationService.calculateAssetWeightedAverage(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        
        // TESTE MUTANTE - verifica o valor calculado
        assertEquals(0, new BigDecimal("10.00").compareTo(result.get(asset1)));
        
        // TESTE MUTANTE - verifica se o asset correto está no resultado
        assertTrue(result.containsKey(asset1));
        
        // TESTE MUTANTE - verifica se não tem outros assets
        assertFalse(result.containsKey(asset2));
    }

    @Test
    @DisplayName("Show calculate weighted average for single asset with multiple operations")
    void shouldCalculateWeightedAverageForSingleAssetMultipleOperation() {
        List<Operation> operations = Arrays.asList(
                new Operation(user, asset1, 100, new BigDecimal("10.00"), "BUY", new BigDecimal("5.00"), testDateTime),
                new Operation(user, asset1, 200, new BigDecimal("15.00"), "BUY", new BigDecimal("7.50"), testDateTime));

        when(operationRepository.findByUserIdAndOperationType(userId, "BUY")).thenReturn(operations);

        Map<Asset, BigDecimal> result = operationService.calculateAssetWeightedAverage(userId);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(0, new BigDecimal("13.33").compareTo(result.get(asset1)));
    }

    @Test
    @DisplayName("Should calculate weighted average for multiple assets")
    void shouldCalculateWeightedAverageForMultipleAssets() {
        List<Operation> operations = Arrays.asList(
                new Operation(user, asset1, 100, new BigDecimal("10.00"), "BUY",
                        new BigDecimal("5.00"), testDateTime),
                new Operation(user, asset1, 200, new BigDecimal("15.00"), "BUY",
                        new BigDecimal("7.50"), testDateTime),
                new Operation(user, asset2, 50, new BigDecimal("20.00"), "BUY",
                        new BigDecimal("10.00"), testDateTime),
                new Operation(user, asset2, 100, new BigDecimal("25.00"), "BUY",
                        new BigDecimal("12.50"), testDateTime));
        when(operationRepository.findByUserIdAndOperationType(userId, "BUY")).thenReturn(operations);

        Map<Asset, BigDecimal> result = operationService.calculateAssetWeightedAverage(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(0, new BigDecimal("13.33").compareTo(result.get(asset1)));
        assertEquals(0, new BigDecimal("23.33").compareTo(result.get(asset2)));
    }

    @Test
    @DisplayName("Should return empty map when no operations found")
    void shouldReturnEmptyMapWhenNoOperationsFound() {
        when(operationRepository.findByUserIdAndOperationType(userId, "BUY")).thenReturn(Collections.emptyList());

        Map<Asset, BigDecimal> result = operationService.calculateAssetWeightedAverage(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should handle zero quantity operations")
    void shouldHandleZeroQuantityOperations() {
        List<Operation> operations = Arrays.asList(
                new Operation(user, asset1, 0, new BigDecimal("10.00"), "BUY",
                        new BigDecimal("5.00"), testDateTime),
                new Operation(user, asset1, 100, new BigDecimal("15.00"), "BUY",
                        new BigDecimal("7.50"), testDateTime));
        when(operationRepository.findByUserIdAndOperationType(userId, "BUY")).thenReturn(operations);

        Map<Asset, BigDecimal> result = operationService.calculateAssetWeightedAverage(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(0, new BigDecimal("15.00").compareTo(result.get(asset1)));
    }

    @Test
    @DisplayName("Should return zero when all operations have zero quantity")
    void shouldReturnZeroWhenAllOperationsHaveZeroQuantity() {
        List<Operation> operations = Arrays.asList(
                new Operation(user, asset1, 0, new BigDecimal("10.00"), "BUY",
                        new BigDecimal("5.00"), testDateTime),
                new Operation(user, asset1, 0, new BigDecimal("15.00"), "BUY",
                        new BigDecimal("7.50"), testDateTime));
        when(operationRepository.findByUserIdAndOperationType(userId, "BUY")).thenReturn(operations);

        Map<Asset, BigDecimal> result = operationService.calculateAssetWeightedAverage(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(0, BigDecimal.ZERO.compareTo(result.get(asset1)));
    }

    @Test
    @DisplayName("Should throw exception when userId is null")
    void shouldThrowExceptionWhenUserIdIsNull() {
        Long nullUserId = null;

        assertThrows(IllegalArgumentException.class,
                () -> operationService.calculateAssetWeightedAverage(nullUserId));
    }

    @Test
    @DisplayName("Should throw exception when userId is invalid")
    void shouldThrowExceptionWhenUserIdIsInvalid() {
        Long invalidUserId = null;

        assertThrows(IllegalArgumentException.class,
                () -> operationService.calculateAssetWeightedAverage(invalidUserId));
    }

}
