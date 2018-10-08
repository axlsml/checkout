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
        ItemsWithPricingRules total = ItemsWithPricingRules.create(items);
        Optional<ItemsWithPricingRules> afterRuleWasApplied;
        do {
            afterRuleWasApplied = rules.apply(total);
            if (afterRuleWasApplied.isPresent()) {
                total = afterRuleWasApplied.get();
            }
        } while (afterRuleWasApplied.isPresent());

        return total.getTotal();
    }

    void scan(Item item) {
        items.add(item);
    }
}
