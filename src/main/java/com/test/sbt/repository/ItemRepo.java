package com.test.sbt.repository;

import com.test.sbt.domain.Item;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {
    @EntityGraph(attributePaths = {"shoppingList"})
    List<Item> findAll();
}
