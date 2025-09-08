package br.com.gustavobarez.Itau_Tech_Challenge.Operation;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

        List<Operation> findByUserId(Long userId);

        List<Operation> findByUserIdAndOperationType(Long userId, String operationType);

        @Query(value = "SELECT u.name as clientName, SUM(o.quantity * o.unit_price) as totalPosition " +
                        "FROM operations o JOIN users u ON o.user_id = u.id " +
                        "WHERE o.operation_type = 'BUY' " +
                        "GROUP BY u.id, u.name " +
                        "ORDER BY totalPosition DESC " +
                        "LIMIT 10", nativeQuery = true)
        List<Map<String, Object>> findTop10ClientsByPosition();

        @Query(value = "SELECT u.name as clientName, SUM(o.brokerage_fee) as totalBrokerage " +
                        "FROM operations o JOIN users u ON o.user_id = u.id " +
                        "GROUP BY u.id, u.name " +
                        "ORDER BY totalBrokerage DESC " +
                        "LIMIT 10", nativeQuery = true)
        List<Map<String, Object>> findTop10ClientsByBrokerage();

}
