package com.bockig.checkout;

import com.google.common.collect.Lists;

import java.util.List;

class TotalContainer {

    private final List<Item> singleItems;
    private final List<RuleApplied> itemsWithRuleApplied;

    private TotalContainer(List<Item> singleItems, List<RuleApplied> itemsWithRuleApplied) {
        this.singleItems = singleItems;
        this.itemsWithRuleApplied = itemsWithRuleApplied;
    }

    static TotalContainer create(List<Item> items) {
        return new TotalContainer(items, Lists.newArrayList());
    }

    Integer getTotal() {
        Integer singleItemTotal = singleItems.stream().mapToInt(Item::getUnitPrice).sum();
        Integer totalWithAppliedRules = itemsWithRuleApplied.stream().mapToInt(RuleApplied::getPrice).sum();
        return singleItemTotal + totalWithAppliedRules;
    }

    List<Item> getSingleItems() {
        return singleItems;
    }

    TotalContainer with(RuleApplied appliedRule, List<Item> itemsForDiscount) {
        List<Item> leftSingleItems = singleItemsWithout(itemsForDiscount);
        List<RuleApplied> addedDiscounts = Lists.newArrayList(itemsWithRuleApplied);
        addedDiscounts.add(appliedRule);
        return new TotalContainer(leftSingleItems, addedDiscounts);
    }

    private List<Item> singleItemsWithout(List<Item> toBeRemoved) {
        List<Item> left = Lists.newArrayList(singleItems);
        toBeRemoved.forEach(left::remove);
        return left;
    }
}
