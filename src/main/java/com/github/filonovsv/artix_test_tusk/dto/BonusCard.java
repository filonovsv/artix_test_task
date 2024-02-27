package com.github.filonovsv.artix_test_tusk.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("bonus_cards")
@Data
public class BonusCard {
    @Id
    Long id;
    Long amount;
}
