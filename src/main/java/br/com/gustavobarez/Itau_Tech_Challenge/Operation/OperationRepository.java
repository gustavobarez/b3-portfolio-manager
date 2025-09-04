package br.com.gustavobarez.Itau_Tech_Challenge.Operation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByUserId(Long userId);

    List<Operation> findByUserIdAndOperationType(Long userId, String operationType);

}
