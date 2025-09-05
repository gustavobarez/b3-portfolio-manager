package br.com.gustavobarez.Itau_Tech_Challenge.Quotation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    
    List<Quotation> findByAssetId(Long assetId);

}
