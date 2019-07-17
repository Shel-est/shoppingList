package com.test.sbt.controller;

import com.test.sbt.domain.Item;
import com.test.sbt.domain.Shopping;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class TestItemController {
    @LocalServerPort
    private int port;
    private String localhost = "http://localhost:";
    private Item item = new Item();
    private Long id = 104L;
    private Long listId = 5L;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addItemSuccess() throws MalformedURLException {
        Item addItem = new Item();
        addItem.setName("молоко");
        addItem.setPrice(40);
        ResponseEntity<Item> responseEntity = restTemplate.postForEntity(
                new URL(localhost + port + "/shopping-list/" + listId + "/item").toString(),
                addItem,
                Item.class
        );
        Assert.assertEquals(getTestItem(), responseEntity.getBody());
    }

    @Test
    public void updateItemSuccess() throws MalformedURLException {
        ResponseEntity<Item> responseEntity = restTemplate.postForEntity(
                new URL(localhost + port + "/shopping-list/" + listId + "/item/" + id).toString(),
                getTestItem(45),
                Item.class
        );
        Assert.assertEquals(getTestItem(45), responseEntity.getBody());

        responseEntity = restTemplate.postForEntity(
                new URL(localhost + port + "/shopping-list/" + listId + "/item/" + id).toString(),
                getTestItem("хлеб"),
                Item.class
        );
        Assert.assertEquals(getTestItem("хлеб"), responseEntity.getBody());

        responseEntity = restTemplate.postForEntity(
                new URL(localhost + port + "/shopping-list/" + listId + "/item/" + id).toString(),
                getTestItem(),
                Item.class
        );
        Assert.assertEquals(getTestItem(), responseEntity.getBody());
    }

    @Test
    public void deleteItemSuccess() throws MalformedURLException {
        restTemplate.delete(new URL(localhost + port + "/shopping-list/" + listId + "/item/" + id).toString());
    }

    @Test
    public void updateStatusSuccess() throws MalformedURLException {
        restTemplate.getForEntity(new URL(localhost + port + "/shopping-list/" + listId + "/item/update-status/" + id).toString(),
                Shopping.class);
    }

    private Item getTestItem() {
        return getTestItem("молоко", 30);
    }

    private Item getTestItem(String name) {
        return getTestItem(name, 30);
    }

    private Item getTestItem(int price) {
        return getTestItem("молоко", price);
    }

    private Item getTestItem(String name, int price) {
        item.setId(id);
        item.setName(name);
        item.setPrice(price);
        item.setShoppingList(new HashSet<>());

        return item;
    }
}
