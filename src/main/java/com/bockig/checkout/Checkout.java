package com.bockig.checkout;

import java.util.ArrayList;
import java.util.List;

class Checkout {

    private final Rules rules;
    private final List<Item> scannedItems = new ArrayList<>();

    Checkout(Rules rules) {
        this.rules = rules;
    }

    void scan(Item item) {
        scannedItems.add(item);
    }

    Integer total() {
        return rules.createContainer(scannedItems).getTotal();
    }
}
