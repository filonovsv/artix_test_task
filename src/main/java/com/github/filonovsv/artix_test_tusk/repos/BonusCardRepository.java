package com.github.filonovsv.artix_test_tusk.repos;

import com.github.filonovsv.artix_test_tusk.dto.BonusCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BonusCardRepository extends CrudRepository<BonusCard, Long> {
    Optional<BonusCard> findById(Long id);
}
