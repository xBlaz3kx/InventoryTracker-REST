package com.bdular.inventorytracker.data.user.seller;

import com.bdular.inventorytracker.data.user.seller.data.Expense;
import com.bdular.inventorytracker.data.user.seller.data.Expenses;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expenses, String> {

    List<Expenses> findAllByTypeEqualsAndDateBetween(@NotNull Expense type, @PastOrPresent LocalDateTime date, @PastOrPresent LocalDateTime date2);

    List<Expenses> findAllBySeller_IDEqualsAndDateBetween(String seller_ID, @PastOrPresent LocalDateTime date, @PastOrPresent LocalDateTime date2);

    Expenses findBySeller_IDEqualsAndTypeEqualsAndDateBetween(String seller_ID, @NotNull Expense type, @PastOrPresent LocalDateTime date, @PastOrPresent LocalDateTime date2);
}
