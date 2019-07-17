package com.test.sbt.repository;

import com.test.sbt.domain.Item;
import com.test.sbt.domain.Shopping;
import com.test.sbt.domain.ShoppingId;
import com.test.sbt.domain.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingRepo extends JpaRepository<Shopping, ShoppingId> {
    Shopping findByItemAndShoppingList(Item item, ShoppingList shoppingList);
}
