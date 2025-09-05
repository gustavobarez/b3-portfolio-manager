package br.com.gustavobarez.Itau_Tech_Challenge.Asset;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class AssetService {
    
    AssetRepository repository;

    public AssetService(AssetRepository repository) {
        this.repository = repository;
    }

    public Optional<Asset> findById(Long id) {
        var asset = repository.findById(id); 
        return asset;
    }

}
