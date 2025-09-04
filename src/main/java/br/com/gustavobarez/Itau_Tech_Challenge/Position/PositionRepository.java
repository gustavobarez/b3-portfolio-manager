package br.com.gustavobarez.Itau_Tech_Challenge.Position;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    List<Position> findByUserId(Long userId);

}
