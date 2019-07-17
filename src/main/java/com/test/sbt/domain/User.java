package com.test.sbt.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = { "id", "name" })
public class User implements Serializable {
    @Id
    @JsonView(Views.Id.class)
    private String id;
    @JsonView(Views.IdName.class)
    private String name;
    private String email;
    @JsonView(Views.FullProfile.class)
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<ShoppingList> shoppingLists;
}
