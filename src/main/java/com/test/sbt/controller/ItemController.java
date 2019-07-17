package com.test.sbt.controller;

import com.test.sbt.domain.Item;
import com.test.sbt.domain.Shopping;
import com.test.sbt.domain.ShoppingList;
import com.test.sbt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shopping-list/{listId}/item")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public Item create(
            @RequestBody Item item,
            @PathVariable("listId") ShoppingList shoppingList
    ) {
        return itemService.create(item, shoppingList);
    }

    @PutMapping("{id}")
    public Item update(@PathVariable("id") Item itemFromDb,
                       @RequestBody Item item
    ) {
        return itemService.update(itemFromDb, item);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Item item) {
        itemService.delete(item);
    }

    @GetMapping("{id}/update-status")
    public Shopping updateStatus(@PathVariable("id") Item item,
                                 @PathVariable("listId") ShoppingList shoppingList
    ) {
        return itemService.updateStatus(item, shoppingList);
    }
}
