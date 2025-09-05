package br.com.gustavobarez.Itau_Tech_Challenge.Quotation;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@EnableRetry
public class KafkaQuotationListener {

    private final QuotationService quotationService;
    private final ObjectMapper objectMapper;

    public KafkaQuotationListener(QuotationService quotationService, ObjectMapper objectMapper) {
        this.quotationService = quotationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "quotations", groupId = "quotation-service")
    public void consumeQuotation(String message) {
        try {
            System.out.println("Received quotation message: " + message);
            QuotationMessage quotationMessage = objectMapper.readValue(message, QuotationMessage.class);
            quotationService.processQuotation(quotationMessage);
        } catch (JsonProcessingException e) {
            System.out.println("Failed to parse quotation error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error processing error: {}" + e.getMessage());
        }
    }

}
