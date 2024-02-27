package com.github.filonovsv.artix_test_tusk.controller;

import com.github.filonovsv.artix_test_tusk.dto.BonusCard;
import com.github.filonovsv.artix_test_tusk.dto.Operation;
import com.github.filonovsv.artix_test_tusk.repos.BonusCardRepository;
import com.github.filonovsv.artix_test_tusk.repos.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class Controller {
    private final BonusCardRepository bonusCardRepository;
    private final OperationRepository operationRepository;


    @GetMapping("balance/{cardId}")
    private ResponseEntity<BonusCard> getBalance(@PathVariable Long cardId, Authentication authentication) {
        Optional<BonusCard> optionalBonusCard = bonusCardRepository.findById(cardId);
        if (optionalBonusCard.isPresent()) {
            if (!authentication.getName().equals(optionalBonusCard.get().getOvner()) &&
                    !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(optionalBonusCard.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("change_balance")
    private ResponseEntity<BonusCard> addOrSubBonus(@RequestBody Operation operation) {
        Optional<BonusCard> optionalBonusCard = bonusCardRepository.findById(operation.bonusCardId());
        if (optionalBonusCard.isPresent()) {
            BonusCard bonusCard = optionalBonusCard.get();
            long amount = bonusCard.getAmount() + operation.amountChange();
            bonusCard.setAmount(amount);
            bonusCardRepository.save(bonusCard);
            operationRepository.save(operation);
            return ResponseEntity.ok(bonusCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("cancel_operation/{operationId}")
    private ResponseEntity<BonusCard> cancelOperation(@PathVariable Long operationId) {
        Optional<Operation> optionalOperation = operationRepository.findById(operationId);
        if (optionalOperation.isPresent()) {
            Operation operation = optionalOperation.get();
            Optional<BonusCard> optionalBonusCard = bonusCardRepository.findById(operation.bonusCardId());
            if (optionalBonusCard.isPresent()) {
                BonusCard bonusCard = optionalBonusCard.get();
                long amount = bonusCard.getAmount() - operation.amountChange();
                bonusCard.setAmount(amount);
                bonusCardRepository.save(bonusCard);
                operationRepository.delete(operation);
                return ResponseEntity.ok(bonusCard);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("history/{cardId}")
    private ResponseEntity<List<Operation>> getHistory(@PathVariable Long cardId, Authentication authentication) {
        Optional<BonusCard> optionalBonusCard = bonusCardRepository.findById(cardId);
        if (optionalBonusCard.isPresent()) {
            if (!authentication.getName().equals(optionalBonusCard.get().getOvner()) &&
                    !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return ResponseEntity.notFound().build();
            }
        }
        List<Operation> listOperation = operationRepository.getOperationByBonusCardId(cardId);
        return ResponseEntity.ok(listOperation);
    }
}
