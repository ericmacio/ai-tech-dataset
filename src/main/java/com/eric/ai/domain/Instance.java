package com.eric.ai.domain;

import java.util.List;
import java.util.stream.Stream;

public class Instance {

    private String type;
    private String provider;
    private List<String> products;

    public Instance() {
    }

    public Instance(String type, String provider, List<String> products) {
        this.type = type;
        this.provider = provider;
        this.products = products;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public void displayProducts() {
        getProducts().forEach(p -> System.out.print("[" + p + "] "));

    }

    @Override
    public String toString() {
        return "Instance{" +
                "type='" + type + '\'' +
                ", provider='" + provider + '\'' +
                ", products=" + products +
                '}';
    }

    public Stream<String> getDataStream(String separator, String itemStr) {
        String masterStr = itemStr + separator + this.provider

                ;
        return this.products.stream()
                .map(p -> masterStr + separator + p);
    }
}
