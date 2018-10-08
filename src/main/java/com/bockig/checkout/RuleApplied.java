package com.bockig.checkout;

import java.util.List;

class RuleApplied {

    private final List<Item> itemsIncluded;
    private final Rule rule;

    public RuleApplied(List<Item> itemsIncluded, Rule rule) {
        this.itemsIncluded = itemsIncluded;
        this.rule = rule;
    }

    int getPrice() {
        return rule.getPrice();
    }
}
