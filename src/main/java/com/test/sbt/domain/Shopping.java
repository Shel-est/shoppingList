package com.test.sbt.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@NoArgsConstructor
public class Shopping implements Serializable {
    @EmbeddedId
    @JsonIgnore
    private ShoppingId id;

    @MapsId("itemId")
    @ManyToOne
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private Item item;
    @MapsId("shoppingListId")
    @ManyToOne
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private ShoppingList shoppingList;

    @JsonView(Views.IdName.class)
    private boolean isBought;

    public Shopping(Item item, ShoppingList shoppingList) {
        this.item = item;
        this.shoppingList = shoppingList;
        this.id = new ShoppingId(item.getId(), shoppingList.getId());
    }
}
