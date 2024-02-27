package com.github.filonovsv.artix_test_tusk.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("operations")
public record Operation(@Id Long id, Long bonusCardId, Long amountChange) {
}
