package com.test.sbt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.test.sbt.domain.Item;
import com.test.sbt.domain.ShoppingList;
import com.test.sbt.domain.User;
import com.test.sbt.domain.Views;
import com.test.sbt.repository.ItemRepo;
import com.test.sbt.repository.ShoppingListRepo;
import com.test.sbt.repository.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
//    private final UserService userService;
    private final ShoppingListRepo shoppingListRepo;
    private final ItemRepo itemRepo;
    private final UserDetailsRepo userDetailsRepo;
    private final ObjectWriter shoppingListsWriter;
    private final ObjectWriter itemsWriter;
    private final ObjectWriter profileWriter;
    @Value("${spring.profiles.active:prod}")
    private String profile;


    @Autowired
    public MainController(/*UserService userService, */ShoppingListRepo shoppingListRepo, ItemRepo itemRepo, UserDetailsRepo userDetailsRepo, ObjectMapper mapper) {
        this.shoppingListRepo = shoppingListRepo;
        this.itemRepo = itemRepo;
        this.userDetailsRepo = userDetailsRepo;
//        this.userService = userService;
        shoppingListsWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullShoppingList.class);
        itemsWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullItem.class);
        profileWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model,
                       @AuthenticationPrincipal User user
    ) throws JsonProcessingException {
        List<Item> items = itemRepo.findAll();

        String itemsJson = itemsWriter.writeValueAsString(items);

        model.addAttribute("items", itemsJson);
        model.addAttribute("isDevMode", true);

        if (user != null) {
            User userFromDb = userDetailsRepo.findById(user.getId()).get();
            String profileJson = profileWriter.writeValueAsString(userFromDb);
            model.addAttribute("profile", profileJson);

            List<ShoppingList> shoppingLists = shoppingListRepo.findAllByUserId(userFromDb.getId());
            String listsJson = shoppingListsWriter.writeValueAsString(shoppingLists);
            model.addAttribute("shoppingLists", listsJson);
        } else {
            model.addAttribute("profile", "null");
            model.addAttribute("shoppingLists", "[]");
        }

        return "index";
    }
}
