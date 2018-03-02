package com.lostandfound_wc.demo;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min=1)
    private String itemName;

    @NotNull
    @Size(min=1)
    private String description;

    @NotNull
    @Size(min=1)
    private String dateLost;

    @URL
    private String image;

    private String found;

    private String itemCategory;

    private String addItem;





    @ManyToMany
    private Set<User> users;

    public Item(){
        this.users = new HashSet<>();
    }










    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateLost() {
        return dateLost;
    }

    public void setDateLost(String dateLost) {
        this.dateLost = dateLost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }




    public Set<User> getUsers(){
        return users;
    }

    public void setUsers(Set<User> users){
        this.users = users;
    }


    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", dateLost='" + dateLost + '\'' +
                ", image='" + image + '\'' +
                ", found='" + found + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                '}';
    }

    public String getAddItem() {
        return addItem;
    }

    public void setAddItem(String addItem) {
        this.addItem = addItem;
    }
}
