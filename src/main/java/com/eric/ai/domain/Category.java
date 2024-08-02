package com.eric.ai.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Category {

    private String name;
    private String parent;
    private String acronym;
    private Integer level;
    private List<String> childNames = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private List<Category> childList = new ArrayList<>();
    private final List<String> parents = new ArrayList<>();

    public Category(String name, String parent, String acronym, Integer level, List<String> childNames, List<Item> items) {
        this.name = name;
        this.acronym = acronym;
        this.level = level;
        this.childNames.addAll(childNames);
        this.items.addAll(items);
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<String> getChildNames() {
        return childNames;
    }

    public void setChildNames(List<String> childNames) {
        this.childNames = childNames;
    }

    public List<Category> getChildList() {
        return childList;
    }

    public void setChildList(List<Category> childList) {
        this.childList = childList;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items.addAll(items);
    }

    public List<String> getParents() {
        return parents;
    }

    public void recursiveDisplay() {
        System.out.println("level [" + this.getLevel() + "] category name: " + this.getName());
        getItems().forEach(Item::display);
        for(Category childCategory : this.getChildList()) {
            childCategory.recursiveDisplay();
        }
    }

    public List<Item> getItemsRecursive() {
        List<Item> allItems = this.getItems();
        for(Category childCategory : this.getChildList()) {
            List<Item> childItems = childCategory.getItemsRecursive();
            allItems.addAll(childItems);
        }
        return allItems;
    }

    public void addChild(Category childCategory) {
        this.getChildList().add(childCategory);
    }

    public void setParents(List<String> parents) {
        this.parents.addAll(parents);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", childNames=" + childNames +
                ", items=" + items +
                '}';
    }
}
