package com.test.sbt.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = { "id", "name" })
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class ShoppingList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.FullShoppingList.class)
    private String description;

    @JsonView(Views.FullShoppingList.class)
    @OneToMany(mappedBy = "shoppingList")
    private Set<Shopping> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullShoppingList.class)
    private User user;
    @JsonView(Views.FullShoppingList.class)
    private long totalPrice;
    @JsonView(Views.FullShoppingList.class)
    private boolean isPeriodic;

    @Enumerated(EnumType.ORDINAL)
    @JsonView(Views.FullShoppingList.class)
    private Period period;
    @JsonView(Views.FullShoppingList.class)
    private boolean isBought;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonView(Views.FullShoppingList.class)
    private LocalDate deadline;
}
