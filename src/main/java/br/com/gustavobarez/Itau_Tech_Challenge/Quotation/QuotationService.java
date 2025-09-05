package br.com.gustavobarez.Itau_Tech_Challenge.Quotation;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import br.com.gustavobarez.Itau_Tech_Challenge.Asset.Asset;
import br.com.gustavobarez.Itau_Tech_Challenge.Asset.AssetService;

@Service
public class QuotationService {

    QuotationRepository repository;
    AssetService assetService;

    public QuotationService(QuotationRepository repository, AssetService assetService) {
        this.repository = repository;
        this.assetService = assetService;
    }

    @Retryable(retryFor = { DataAccessException.class,
            TransientDataAccessException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void processQuotation(QuotationMessage message) {
        try {
            Asset asset = assetService.findById(message.getAsset())
                    .orElseThrow(() -> new RuntimeException("Asset not found: " + message.getAsset()));

            Quotation quotation = new Quotation(
                    asset,
                    message.getUnitPrice(),
                    message.getDateTime(),
                    message.getSource());

            repository.save(quotation);
            System.out.printf("\nQuotation saved successfully: asset=%d, price=%.2f, source=%s",
                    message.getAsset(), message.getUnitPrice(), message.getSource());

        } catch (DataIntegrityViolationException e) {
            System.out.printf("Quotation already exists, skipping: asset=%d, price=%.2f, source=%s",
                    message.getAsset(), message.getUnitPrice(), message.getSource());
        }
    }

    @Recover
    public void recover(Exception e, QuotationMessage message) {
        System.out.println("Failed to process after retries");
    }

}
