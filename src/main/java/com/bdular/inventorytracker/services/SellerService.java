package com.bdular.inventorytracker.services;

import com.bdular.inventorytracker.data.user.seller.ExpenseRepository;
import com.bdular.inventorytracker.data.user.seller.SellerRepository;
import com.bdular.inventorytracker.data.user.seller.data.Expenses;
import com.bdular.inventorytracker.data.user.seller.data.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static com.bdular.inventorytracker.data.user.seller.data.Expense.TRAVELLING;

@Service
@Validated
public class SellerService {

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    SellerRepository repository;

    public Seller getSeller(@NotNull String id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Seller> getSellers() {
        return repository.findAll();
    }

    public void insertSeller(@Valid Seller seller) {
        repository.insert(seller);
    }

    @Transactional
    public void addNewExpense(@NotNull String id, @Valid Expenses expense) {
        repository.findById(id).map(seller -> {
            if (expense.getType().equals(TRAVELLING)) {
                LocalDateTime weekBefore = LocalDateTime.parse(expense.getDate().toString()).minusDays(5);
                Expenses weeklyExpense = expenseRepository.findBySeller_IDEqualsAndTypeEqualsAndDateBetween(id,
                        TRAVELLING,
                        weekBefore,
                        LocalDateTime.now());
                if (weeklyExpense != null) {
                    HashMap<String, BigDecimal> currentDistances = weeklyExpense.getCost();
                    HashMap<String, BigDecimal> newDistances = expense.getCost();
                    if (!currentDistances.equals(expense.getCost())) {
                        newDistances.forEach((key, value) -> {
                            if (currentDistances.containsKey(key)) {
                                BigDecimal costValue = currentDistances.get(key);
                                if (!costValue.equals(value)) {
                                    if (costValue.compareTo(value) > 0) {
                                        currentDistances.replace(key, value);
                                    }
                                }
                            } else {
                                currentDistances.put(key, value);
                            }
                        });
                    }
                }
            }
            expenseRepository.save(expense);
            return repository.save(seller);
        }).orElseThrow();
    }

    public Seller getSellerByEmail(@Email String email) {
        return repository.findSellerByEmailEquals(email);
    }

}
