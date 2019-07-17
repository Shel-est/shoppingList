package com.test.sbt.repository;

import com.test.sbt.domain.ShoppingList;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepo extends JpaRepository<ShoppingList, Long> {
    @EntityGraph(attributePaths = {"items"})
    Optional<ShoppingList> findById(Long l);

    @EntityGraph(attributePaths = {"items"})
    List<ShoppingList> findAll();


    @EntityGraph(attributePaths = {"items"})
    List<ShoppingList> findAllByUserId(String id);
}
