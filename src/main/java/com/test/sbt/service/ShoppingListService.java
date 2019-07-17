package com.test.sbt.service;

import com.test.sbt.domain.Period;
import com.test.sbt.domain.ShoppingList;
import com.test.sbt.domain.User;
import com.test.sbt.repository.ShoppingListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListService {
    private final ShoppingListRepo shoppingListRepo;

    @Autowired
    public ShoppingListService(ShoppingListRepo shoppingListRepo) {
        this.shoppingListRepo = shoppingListRepo;
    }


    public ShoppingList create(ShoppingList shoppingList, User user) {
        shoppingList.setUser(user);
//        shoppingList.setPeriod(Period.valueOf());
        return shoppingListRepo.save(shoppingList);
    }

    public ShoppingList update(ShoppingList shoppingListFromDb, ShoppingList shoppingList) {
        shoppingListFromDb.setName(shoppingList.getName());
        shoppingListFromDb.setDescription(shoppingList.getDescription());
        shoppingListFromDb.setItems(shoppingList.getItems());
        shoppingListFromDb.setDeadline(shoppingList.getDeadline());
        shoppingListFromDb.setPeriodic(shoppingList.isPeriodic());
        shoppingListFromDb.setPeriod(shoppingList.getPeriod());

        return shoppingListRepo.save(shoppingListFromDb);
    }

    public void delete(ShoppingList shoppingList) {
        shoppingListRepo.delete(shoppingList);
    }

    public ShoppingList updateStatus(ShoppingList shoppingList) {
        shoppingList.setBought(!shoppingList.isBought());

        return shoppingListRepo.save(shoppingList);
    }
}
