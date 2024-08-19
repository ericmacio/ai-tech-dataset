package com.eric.ai.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Item {

    private String category;
    private String domain;
    private String description;
    private String acronym;
    private List<Instance> instances;
    private final List<String> parents = new ArrayList<>();

    public Item() {
    }

    public Item(String description, String acronym, List<Instance> instances) {
        this.description = description;
        this.acronym = acronym;
        this.instances = instances;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String name) {
        this.description = name;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    public void addParents(List<String> parentsName) {
        parents.addAll(parentsName);
    }

    public List<String> getParents() {
        return parents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + description + '\'' +
                ", acronym='" + acronym + '\'' +
                ", instances=" + instances +
                ", parents=" + parents +
                '}';
    }

    public void display() {
        StringBuilder parentsStr = new StringBuilder();
        this.parents.forEach(p -> parentsStr.append("/").append(p));
        System.out.println("[" + parentsStr + "] " + this.description);
    }

    public Stream<String> getDataStream(String separator, String categoryAcronym) {
        StringBuilder dataStr = new StringBuilder();
        dataStr.append(this.parents.get(1));
        for(int i = 2; i < this.parents.size(); i++) {
            dataStr.append(separator).append(this.parents.get(i));
        }
        if(this.parents.size() < 3)
            dataStr.append(separator);
        dataStr.append(separator).append(this.domain).append(separator).append(this.description);
        return this.instances.stream()
                .flatMap(instance -> instance.getDataStream(separator, dataStr.toString()));
    }

    public Boolean instancesContainsWordIgnoreCase(String keyWord) {
        return this.instances.stream()
                .map(Instance::getProducts)
                .flatMap(Collection::stream)
                .anyMatch(s -> s.toLowerCase().contains(keyWord.toLowerCase()));
    }

    public Boolean containsInstancesForProvider(String provider) {
        return this.instances.stream()
                .map(Instance::getProvider)
                .anyMatch(s -> s.toLowerCase().contains(provider.toLowerCase()));
    }

    public Boolean parentsContainsWordIgnoreCase(String keyWord) {
        return this.parents.stream()
                .anyMatch(s -> s.toLowerCase().contains(keyWord.toLowerCase()));
    }

}
