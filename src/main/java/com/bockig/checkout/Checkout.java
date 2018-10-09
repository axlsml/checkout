package com.bockig.checkout;

import java.util.ArrayList;
import java.util.List;

class Checkout {

    private final Rules rules;
    private final List<Item> items = new ArrayList<>();

    Checkout(Rules rules) {
        this.rules = rules;
    }

    void scan(Item item) {
        items.add(item);
    }

    Integer total() {
        return rules.applyTo(items).getTotal();
    }
}
