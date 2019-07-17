package com.test.sbt.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingId implements Serializable {
    @JsonView(Views.Id.class)
    private Long itemId;

    @JsonView(Views.Id.class)
    private Long shoppingListId;
}
