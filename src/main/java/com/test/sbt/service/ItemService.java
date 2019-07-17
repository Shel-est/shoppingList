package com.test.sbt.service;

import com.test.sbt.domain.Item;
import com.test.sbt.domain.Shopping;
import com.test.sbt.domain.ShoppingList;
import com.test.sbt.repository.ItemRepo;
import com.test.sbt.repository.ShoppingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepo itemRepo;
    private final ShoppingRepo shoppingRepo;

    @Autowired
    public ItemService(ItemRepo itemRepo, ShoppingRepo shoppingRepo) {
        this.itemRepo = itemRepo;
        this.shoppingRepo = shoppingRepo;
    }

    public Item create(Item item, ShoppingList shoppingList) {
        Shopping shopping = new Shopping(item, shoppingList);
        item.getShoppingList().add(shopping);

        return itemRepo.save(item);
    }


    public Item update(Item itemFromDb, Item item) {
        itemFromDb.setName(item.getName());
        itemFromDb.setCount(item.getCount());
        itemFromDb.setPrice(item.getPrice());

        return itemRepo.save(itemFromDb);
    }

    public void delete(Item item) {
        itemRepo.delete(item);
    }

    public Shopping updateStatus(Item item, ShoppingList shoppingList) {
        Shopping shoppingItem = shoppingRepo.findByItemAndShoppingList(item, shoppingList);
        shoppingItem.setBought(!shoppingItem.isBought());

        return shoppingRepo.save(shoppingItem);
    }
}
