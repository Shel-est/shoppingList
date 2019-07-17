package com.test.sbt.domain;

public class Views {
    public interface Id {}

    public interface IdName extends Id {}

    public interface FullItem extends IdName {}

    public interface FullShoppingList extends IdName {}

    public interface FullProfile extends IdName {}
}
