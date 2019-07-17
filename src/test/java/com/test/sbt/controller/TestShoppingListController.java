package com.test.sbt.controller;

import com.test.sbt.domain.ShoppingList;
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
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class TestShoppingListController {
    @LocalServerPort
    private int port;
    private String localhost = "http://localhost:";
    private ShoppingList shoppingList = new ShoppingList();
    private Long id = 5L;
    private LocalDateTime deadline = LocalDateTime.of(2019, 7, 13, 18, 00);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addListSuccess() throws MalformedURLException {
        ResponseEntity<ShoppingList> responseEntity = restTemplate.postForEntity(
                new URL(localhost + port + "/shopping-list").toString(),
                getTestList(),
                ShoppingList.class
        );
        Assert.assertEquals(getTestList(), responseEntity.getBody());
    }

    @Test
    public void getSuccess() throws MalformedURLException {
        ResponseEntity<ShoppingList> responseEntity = restTemplate.getForEntity(
                new URL(localhost + port + "/shopping-list/" + id).toString(),
                ShoppingList.class
        );

        Assert.assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void updateListSuccess() throws MalformedURLException {
        ResponseEntity<ShoppingList> responseEntity = restTemplate.postForEntity(
                new URL(localhost + port + "/shopping-list/" + id).toString(),
                getTestList(45),
                ShoppingList.class
        );
        Assert.assertEquals(getTestList(45), responseEntity.getBody());

        responseEntity = restTemplate.postForEntity(
                new URL(localhost + port + "/shopping-list/" + id).toString(),
                getTestList("Купить хлеб"),
                ShoppingList.class
        );
        Assert.assertEquals(getTestList("Купить хлеб"), responseEntity.getBody());

        responseEntity = restTemplate.postForEntity(
                new URL(localhost + port + "/shopping-list/" + id).toString(),
                getTestList(),
                ShoppingList.class
        );
        Assert.assertEquals(getTestList(), responseEntity.getBody());
    }

    @Test
    public void deleteListSuccess() throws MalformedURLException {
        restTemplate.delete(new URL(localhost + port + "/shopping-list/" + id).toString());
    }

    private ShoppingList getTestList () {
        return getTestList(
                "Купить молоко",
                30,
                deadline
        );
    }

    private ShoppingList getTestList (String name) {
        return getTestList(
                name,
                30,
                deadline
        );
    }

    private ShoppingList getTestList (int price) {
        return getTestList(
                "Купить молоко",
                price,
                deadline
        );
    }

    private ShoppingList getTestList(String name, long totalPrice, LocalDateTime deadlines) {
        shoppingList.setId(id);
        shoppingList.setName(name);
        shoppingList.setTotalPrice(totalPrice);

        return shoppingList;
    }
}
