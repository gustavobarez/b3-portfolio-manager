package br.com.gustavobarez.Itau_Tech_Challenge.Asset;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findById(Long id);

}
