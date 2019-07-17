package com.test.sbt.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.test.sbt.domain.ShoppingList;
import com.test.sbt.domain.User;
import com.test.sbt.domain.Views;
import com.test.sbt.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shopping-list")
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("{id}")
    public ShoppingList get(@PathVariable("id") ShoppingList shoppingList) {
        return shoppingList;
    }

    @PostMapping
    @JsonView({Views.FullShoppingList.class})
    public ShoppingList create(@RequestBody ShoppingList shoppingList,
                               @AuthenticationPrincipal User user) {
        return shoppingListService.create(shoppingList, user);
    }

    @PutMapping("{id}")
    public ShoppingList update(@PathVariable("id") ShoppingList shoppingListFromDb,
                               @RequestBody ShoppingList shoppingList
    ) {
        return shoppingListService.update(shoppingListFromDb, shoppingList);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") ShoppingList shoppingList) {
        shoppingListService.delete(shoppingList);
    }

    @PostMapping("update-status/{id}")
    public ShoppingList updateStatus(@PathVariable("id")  ShoppingList shoppingList) {
        return shoppingListService.updateStatus(shoppingList);
    }
}
