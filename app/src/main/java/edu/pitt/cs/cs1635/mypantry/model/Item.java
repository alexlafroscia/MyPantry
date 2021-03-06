package edu.pitt.cs.cs1635.mypantry.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "ITEM".
 */
public class Item {

    private Long id;
    private String title;
    private Integer amount;
    private Boolean onGroceryList;

    public Item() {
    }

    public Item(Long id) {
        this.id = id;
    }

    public Item(Long id, String title, Integer amount, Boolean onGroceryList) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.onGroceryList = onGroceryList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getOnGroceryList() {
        return onGroceryList;
    }

    public void setOnGroceryList(Boolean onGroceryList) {
        this.onGroceryList = onGroceryList;
    }

}
