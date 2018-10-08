package com.bockig.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


class Checkout {

    private final Rules rules;
    private final List<Item> items = new ArrayList<>();

    Checkout(Rules rules) {
        this.rules = rules;
    }

    Integer total() {
        TotalContainer total = TotalContainer.create(items);
        Optional<TotalContainer> withRuleApplied;
        do {
            withRuleApplied = rules.apply(total);
            if (withRuleApplied.isPresent()) {
                total = withRuleApplied.get();
            }
        } while (withRuleApplied.isPresent());
        return total.getTotal();
    }

    void scan(Item item) {
        items.add(item);
    }
}
