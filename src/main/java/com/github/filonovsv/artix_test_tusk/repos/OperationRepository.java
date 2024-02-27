package com.github.filonovsv.artix_test_tusk.repos;

import com.github.filonovsv.artix_test_tusk.dto.Operation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OperationRepository extends CrudRepository<Operation, Long> {
    List<Operation> getOperationByBonusCardId(Long bonusCardId);
}
