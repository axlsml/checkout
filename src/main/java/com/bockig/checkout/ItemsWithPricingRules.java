package com.bockig.checkout;

import com.google.common.collect.Lists;

import java.util.List;

class ItemsWithPricingRules {

    private final List<Item> singleItems;
    private final List<AppliedPricingRule> itemsWithAppliedPricingRule;

    private ItemsWithPricingRules(List<Item> singleItems, List<AppliedPricingRule> itemsWithAppliedPricingRule) {
        this.singleItems = singleItems;
        this.itemsWithAppliedPricingRule = itemsWithAppliedPricingRule;
    }

    static ItemsWithPricingRules create(List<Item> items) {
        return new ItemsWithPricingRules(items, Lists.newArrayList());
    }

    Integer getTotal() {
        Integer singleItemTotal = singleItems.stream().mapToInt(Item::getUnitPrice).sum();
        Integer pricingRulesTotal = itemsWithAppliedPricingRule.stream().mapToInt(AppliedPricingRule::getPrice).sum();
        return singleItemTotal + pricingRulesTotal;
    }

    List<Item> getSingleItems() {
        return singleItems;
    }

    ItemsWithPricingRules with(AppliedPricingRule appliedRule) {
        List<Item> leftSingleItems = appliedRule.withoutIncludedItems(singleItems);
        List<AppliedPricingRule> addedDiscounts = appliedRule.addTo(itemsWithAppliedPricingRule);
        return new ItemsWithPricingRules(leftSingleItems, addedDiscounts);
    }

    int compareByTotal(ItemsWithPricingRules other) {
        return getTotal().compareTo(other.getTotal());
    }
}
