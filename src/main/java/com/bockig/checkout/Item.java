package com.bockig.checkout;

import java.util.Objects;

class Item {

    private String name;
    private Integer unitPrice;

    Item(String name, Integer unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    Integer getUnitPrice() {
        return unitPrice;
    }
}
